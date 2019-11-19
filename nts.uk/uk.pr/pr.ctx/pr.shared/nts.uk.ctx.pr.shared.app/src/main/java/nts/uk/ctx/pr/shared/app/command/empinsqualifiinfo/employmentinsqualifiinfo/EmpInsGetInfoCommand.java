package nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Value;

@Value
public class EmpInsGetInfoCommand {
    /**
     * 会社ID
     */
    public String cId;
    /**
     * 社員ID
     */
    public String sId;
    /**
     * 労働時間
     */
    public Integer workingTime;

    /**
     * 取得区分
     */
    public Integer acquiAtr;

    /**
     * 契約期間の印刷区分
     */
    public Integer contrPeriPrintAtr;

    /**
     * 就職経路
     */
    public Integer jobPath;

    /**
     * 支払賃金
     */
    public Integer payWage;

    /**
     * 職種
     */
    public Integer jobAtr;

    /**
     * 被保険者原因
     */
    public Integer insCauseAtr;

    /**
     * 賃金支払態様
     */
    public Integer wagePaymentMode;

    /**
     * 雇用形態
     */
    public Integer employmentStatus;
    /**
     * ScreenMode
     */
    public Integer screenMode;
}
