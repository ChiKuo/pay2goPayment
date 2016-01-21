package chikuo.tw.pay2gopayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import chikuo.tw.pay2gopayment.object.pay2go.Payment;
import chikuo.tw.pay2gopayment.object.pay2go.TradeInfo;

public class MainActivity extends AppCompatActivity {

    private String webHtml = "";
    private Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Create payment
        payment = new Payment();
        payment.setHashIV("9g7U8YoWAmxv6BOu");
        payment.setHashKey("Up5QKRRuOBXAVJ3iofcY5HZhM7bTmJbh");
        payment.setMerchantID("11375804");
        payment.setAmt("100");
        payment.setEmail(" ");
        payment.setItemDesc("小小兵便當");
        payment.setMerchantOrderNo("10006");

        // Payment
        Button paymentButton = (Button) findViewById(R.id.payment_button);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
            }
        });
        
        // Check Trade Info
        Button checkPaymentInfoButton = (Button) findViewById(R.id.check_trade_info_button);
        checkPaymentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTradeInfo();
            }
        });

        // Cancel Credit Card
        Button cancelCreditCardButton = (Button) findViewById(R.id.cancel_credit_card_button);
        cancelCreditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCreditCard();
            }
        });

    }

    private void cancelCreditCard() {

        // TODO


        byte[] postDataBefore = new byte[0];
        try {
//            postDataBefore = Encrypt.encryptAES("abcdefghijklmnopqrstuvwxyzABCDEF");
            postDataBefore = Encrypt.encryptAES("RespondType=JSON&Version=1.0&Amt=250&TradeNo=16011912514676001&MerchantOrderNo=10002&IndexType=1&TimeStamp=1400137200");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // PKCS5Padding  =  f27a3ade8d0bb22b55b5b8605c77176be62d3ffcec7cc8cd974d7730c22345ae6fc5403e762114f34aaf46a86e6eb72e47a2ee5de66341c6d9ff0eafc023ed87b2c8628b1013c5f95de564b95464fda697ad1a3cf5463a15b5e98a4b1242137449d84f02c6e448e305f30efaa2360b941d923df18c701ea546ba3ce31141aa83ca1ca08734e891992aa96604adcfcac7
        // PKCS7Padding  =  f27a3ade8d0bb22b55b5b8605c77176be62d3ffcec7cc8cd974d7730c22345ae6fc5403e762114f34aaf46a86e6eb72e47a2ee5de66341c6d9ff0eafc023ed87b2c8628b1013c5f95de564b95464fda697ad1a3cf5463a15b5e98a4b1242137449d84f02c6e448e305f30efaa2360b941d923df18c701ea546ba3ce31141aa83ca1ca08734e891992aa96604adcfcac7
        // correct       =  f27a3ade8d0bb22b55b5b8605c77176be62d3ffcec7cc8cd974d7730c22345ae21398b5c65655f4113b975652cabbc53a7af84e63290ad795ed7aa8c08b996e9629467871ffbcf72b88153293d2ea36e49762f9492ac2f7b87d6c0e3f5b9e7eb
        String postData = Encrypt.bytesToHex(postDataBefore);
//        String postData2 = Base64.encodeToString(postDataBefore, Base64.DEFAULT);
        Log.d("", "");

    }

    private void checkTradeInfo() {

        // Set Version
        payment.setVersion("1.1");
        payment.setTimeStamp(String.valueOf(System.currentTimeMillis()));

        // Create checkValue
        String checkValueBefore = "IV=" + payment.getHashIV() +
                "&Amt=" + payment.getAmt() +
                "&MerchantID="+ payment.getMerchantID() +
                "&MerchantOrderNo="+ payment.getMerchantOrderNo() +
                "&Key=" + payment.getHashKey() ;
        String checkValue = Encrypt.sha256(checkValueBefore).toUpperCase();

        Ion.with(MainActivity.this)
                .load(API.HOST_POST_URL_TRADE_INFO)
                .setMultipartParameter("MerchantID", payment.getMerchantID())
                .setMultipartParameter("RespondType", "JSON")
                .setMultipartParameter("CheckValue", checkValue)
                .setMultipartParameter("TimeStamp", payment.getTimeStamp())
                .setMultipartParameter("Version", payment.getVersion())
                .setMultipartParameter("MerchantOrderNo", payment.getMerchantOrderNo())
                .setMultipartParameter("Amt", payment.getAmt())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        if (e == null) {

                            Gson gson = new Gson();

                            try {
                                JSONObject jsonArrayResult = new JSONObject(result);
                                TradeInfo tradeInfo = gson.fromJson(jsonArrayResult.toString(), TradeInfo.class);

                                // Show Result
                                if (tradeInfo != null && tradeInfo.getStatus().equals("SUCCESS")) {
                                    Toast.makeText(MainActivity.this, tradeInfo.getStatus(), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                });
    }

    private void payment() {

        // Set Version
        payment.setVersion("1.2");
        payment.setTimeStamp(String.valueOf(System.currentTimeMillis()));

        // Create checkValue
        String checkValueBefore = "HashKey=" + payment.getHashKey() +
                "&Amt=" + payment.getAmt() +
                "&MerchantID="+ payment.getMerchantID() +
                "&MerchantOrderNo=" + payment.getMerchantOrderNo() +
                "&TimeStamp="+ payment.getTimeStamp() +
                "&Version="+ payment.getVersion() +
                "&HashIV=" + payment.getHashIV() ;
        String checkValue = Encrypt.sha256(checkValueBefore).toUpperCase();

        // Call API
        Ion.with(MainActivity.this)
                .load(API.HOST_POST_URL_MPG)
                .setMultipartParameter("MerchantID", payment.getMerchantID())
                .setMultipartParameter("RespondType", "String")
                .setMultipartParameter("CheckValue", checkValue)
                .setMultipartParameter("TimeStamp", payment.getTimeStamp())
                .setMultipartParameter("Version", payment.getVersion())
                .setMultipartParameter("MerchantOrderNo", payment.getMerchantOrderNo())
                .setMultipartParameter("Amt", payment.getAmt())
                .setMultipartParameter("ItemDesc", payment.getItemDesc())
                .setMultipartParameter("Email", payment.getEmail())
                .setMultipartParameter("LoginType", "0")
                .setMultipartParameter("CREDIT", "1")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        if (e == null) {
                            webHtml = result;

                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("webHtml", webHtml);
                            startActivity(intent);
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
