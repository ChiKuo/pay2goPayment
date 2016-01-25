package chikuo.tw.pay2gopayment.object.pay2go;

import java.util.List;

/**
 * Created by chikuo on 2016/1/22.
 */
public class CreditActionResult {

    private String Status ;
    private String Message ;
    private ResultItem Result ;

    private class ResultItem {
        private String MerchantID ;
        private String Amt ;
        private String MerchantOrderNo ;
        private String TradeNo ;
        private String CheckCode ;

        public String getAmt() {
            return Amt;
        }

        public void setAmt(String amt) {
            Amt = amt;
        }

        public String getCheckCode() {
            return CheckCode;
        }

        public void setCheckCode(String checkCode) {
            CheckCode = checkCode;
        }

        public String getMerchantID() {
            return MerchantID;
        }

        public void setMerchantID(String merchantID) {
            MerchantID = merchantID;
        }

        public String getMerchantOrderNo() {
            return MerchantOrderNo;
        }

        public void setMerchantOrderNo(String merchantOrderNo) {
            MerchantOrderNo = merchantOrderNo;
        }

        public String getTradeNo() {
            return TradeNo;
        }

        public void setTradeNo(String tradeNo) {
            TradeNo = tradeNo;
        }
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ResultItem getResult() {
        return Result;
    }

    public void setResult(ResultItem result) {
        Result = result;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
