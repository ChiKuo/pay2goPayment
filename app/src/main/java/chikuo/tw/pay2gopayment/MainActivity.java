package chikuo.tw.pay2gopayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
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
        payment.setMerchantOrderNo("10005");

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
            postDataBefore = encryptAES("abcdefghijklmnopqrstuvwxyzABCDEF");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String postData = Base64.encodeToString(postDataBefore, Base64.DEFAULT);
        String postData3 = Base64.encodeToString(postDataBefore, Base64.DEFAULT);
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
        String checkValue = sha256(checkValueBefore).toUpperCase();

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
                "&MerchantOrderNo="+ payment.getMerchantOrderNo() +
                "&TimeStamp="+ payment.getTimeStamp() +
                "&Version="+ payment.getVersion() +
                "&HashIV=" + payment.getHashIV() ;
        String checkValue = sha256(checkValueBefore).toUpperCase();

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


    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static byte[] encryptAES(String data) throws UnsupportedEncodingException {

        byte[] ivs = "1234567890123456".getBytes("UTF-8") ;
        byte[] key = "12345678901234567890123456789012".getBytes("UTF-8");

        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(ivs);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = null;
            mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mAlgorithmParameterSpec);

            return mCipher.doFinal(data.getBytes("UTF-8"));
        } catch (Exception ex) {
            return null;
        }
    }

}
