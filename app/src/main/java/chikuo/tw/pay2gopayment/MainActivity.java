package chikuo.tw.pay2gopayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import chikuo.tw.pay2gopayment.object.pay2go.CancelCredit;
import chikuo.tw.pay2gopayment.object.pay2go.Payment;
import chikuo.tw.pay2gopayment.object.pay2go.TradeInfo;

public class MainActivity extends AppCompatActivity {

    private String webHtml = "";
    private Payment payment;

    private String hashKey = "Up5QKRRuOBXAVJ3iofcY5HZhM7bTmJbh";
    private String hashIv = "9g7U8YoWAmxv6BOu";

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
        payment.setHashIV(hashIv);
        payment.setHashKey(hashKey);
        payment.setMerchantID("11375804");
        payment.setAmt("100");
        payment.setEmail(" ");
        payment.setItemDesc("小小兵便當");
        payment.setMerchantOrderNo("10011");

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

        // Application
        Button applicationCreditCardButton = (Button) findViewById(R.id.application_button);
        applicationCreditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationCreditCard();
            }
        });

        // Close
        Button closeCreditCardButton = (Button) findViewById(R.id.close_button);
        closeCreditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCreditCard();
            }
        });

    }

    private void closeCreditCard() {


    }

    private void applicationCreditCard() {

        // Set Version
        payment.setVersion("1.0");
        payment.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        // TODO

        String postData = null;
        String postDataBefore = "RespondType=JSON" +
                "&Version="+ payment.getVersion() +
                "&Amt=" + payment.getAmt() +
                "&MerchantOrderNo=" + payment.getMerchantOrderNo() +
                "&TimeStamp=" + payment.getTimeStamp() +
                "&IndexType=1" +
                "&CloseType=1" ;

        // Encrypt
        Crypto crypto = new Crypto(hashKey, hashIv);
        try {
            byte[] bytePostData = crypto.encrypt(postDataBefore.getBytes(Charset.forName("UTF-8")));
            postData = crypto.bytesToHex((bytePostData)).trim();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

//        Ion.with(MainActivity.this)
//                .load(API.HOST_POST_URL_CREDIT_CARD_CLOSE)
//                .setMultipartParameter("MerchantID_", payment.getMerchantID())
//                .setMultipartParameter("PostData_", postData )
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//
//                        if (e == null) {
//
//                            Gson gson = new Gson();
//
//                            try {
//                                JSONObject jsonArrayResult = new JSONObject(result);
//                                CancelCredit cancelCredit = gson.fromJson(jsonArrayResult.toString(), CancelCredit.class);
//
//                                // Show Result
//                                if (cancelCredit != null && cancelCredit.getMessage() != null ) {
//                                    Toast.makeText(MainActivity.this, cancelCredit.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//
//                    }
//                });

    }

    private void cancelCreditCard() {

        // Set Version
        payment.setVersion("1.0");
        payment.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        // TODO

        String postData = null;
        String postDataBefore = "RespondType=JSON" +
                            "&Version="+ payment.getVersion() +
                            "&Amt=" + payment.getAmt() +
                            "&MerchantOrderNo=" + payment.getMerchantOrderNo() +
                            "&IndexType=1" +
                            "&TimeStamp=" + payment.getTimeStamp();

        // Encrypt
        Crypto crypto = new Crypto(hashKey, hashIv);
        try {
            byte[] bytePostData = crypto.encrypt(postDataBefore.getBytes(Charset.forName("UTF-8")));
            postData = crypto.bytesToHex((bytePostData)).trim();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        Ion.with(MainActivity.this)
                .load(API.HOST_POST_URL_CREDIT_CARD_CANCEL)
                .setMultipartParameter("MerchantID_", payment.getMerchantID())
                .setMultipartParameter("PostData_", postData )
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        if (e == null) {

                            Gson gson = new Gson();

                            try {
                                JSONObject jsonArrayResult = new JSONObject(result);
                                CancelCredit cancelCredit = gson.fromJson(jsonArrayResult.toString(), CancelCredit.class);

                                // Show Result
                                if (cancelCredit != null && cancelCredit.getMessage() != null ) {
                                    Toast.makeText(MainActivity.this, cancelCredit.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                });

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
        String checkValue = Crypto.sha256(checkValueBefore).toUpperCase();

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
                                if (tradeInfo != null && tradeInfo.getMessage()!= null) {
                                    Toast.makeText(MainActivity.this, tradeInfo.getMessage(), Toast.LENGTH_LONG).show();
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
        String checkValue = Crypto.sha256(checkValueBefore).toUpperCase();

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
