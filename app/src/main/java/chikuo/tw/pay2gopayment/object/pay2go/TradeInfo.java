package chikuo.tw.pay2gopayment.object.pay2go;

/**
 * Created by chikuo on 2016/1/19.
 */
public class TradeInfo {

    private String Status ;
    private String Message ;
    private String Result ;
    private String MerchantID ;
    private String Amt ;
    private String TradeNo ;
    private String MerchantOrderNo ;
    private String TradeStatus ;
    private String PaymentType ;
    private String CreateTime ;
    private String PayTime ;
    private String FundTime ;
    private String CheckCode ;
    private String RespondCode ;
    private String Auth ;
    private String ECI ;
    private String CloseAmt ;
    private String CloseStatus ;
    private String BackBalance ;
    private String BackStatus ;
    private String RespondMsg ;
    private String Inst ;
    private String InstFirst ;
    private String InstEach ;
    private String Bonus ;
    private String RedAmt ;

    public String getAmt() {
        return Amt;
    }

    public void setAmt(String amt) {
        Amt = amt;
    }

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    public String getBackBalance() {
        return BackBalance;
    }

    public void setBackBalance(String backBalance) {
        BackBalance = backBalance;
    }

    public String getBackStatus() {
        return BackStatus;
    }

    public void setBackStatus(String backStatus) {
        BackStatus = backStatus;
    }

    public String getBonus() {
        return Bonus;
    }

    public void setBonus(String bonus) {
        Bonus = bonus;
    }

    public String getCheckCode() {
        return CheckCode;
    }

    public void setCheckCode(String checkCode) {
        CheckCode = checkCode;
    }

    public String getCloseAmt() {
        return CloseAmt;
    }

    public void setCloseAmt(String closeAmt) {
        CloseAmt = closeAmt;
    }

    public String getCloseStatus() {
        return CloseStatus;
    }

    public void setCloseStatus(String closeStatus) {
        CloseStatus = closeStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getECI() {
        return ECI;
    }

    public void setECI(String ECI) {
        this.ECI = ECI;
    }

    public String getFundTime() {
        return FundTime;
    }

    public void setFundTime(String fundTime) {
        FundTime = fundTime;
    }

    public String getInst() {
        return Inst;
    }

    public void setInst(String inst) {
        Inst = inst;
    }

    public String getInstEach() {
        return InstEach;
    }

    public void setInstEach(String instEach) {
        InstEach = instEach;
    }

    public String getInstFirst() {
        return InstFirst;
    }

    public void setInstFirst(String instFirst) {
        InstFirst = instFirst;
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getRedAmt() {
        return RedAmt;
    }

    public void setRedAmt(String redAmt) {
        RedAmt = redAmt;
    }

    public String getRespondCode() {
        return RespondCode;
    }

    public void setRespondCode(String respondCode) {
        RespondCode = respondCode;
    }

    public String getRespondMsg() {
        return RespondMsg;
    }

    public void setRespondMsg(String respondMsg) {
        RespondMsg = respondMsg;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTradeNo() {
        return TradeNo;
    }

    public void setTradeNo(String tradeNo) {
        TradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return TradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        TradeStatus = tradeStatus;
    }
}
