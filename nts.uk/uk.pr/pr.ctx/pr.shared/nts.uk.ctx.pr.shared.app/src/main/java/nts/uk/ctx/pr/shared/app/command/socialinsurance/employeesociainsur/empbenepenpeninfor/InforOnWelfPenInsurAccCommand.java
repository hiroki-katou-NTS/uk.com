package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Value;

@Value
public class InforOnWelfPenInsurAccCommand {

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 健保同一区分
     */
    private int healInsurDis;

    /**
     * 理由その他内容
     */
    private String reasonAndOContent;

    /**
     * 70歳以上被用者
     */
    private Integer YearsOldOrOlder;

    /**
     * 備考その他
     */
    private Integer remarksOther;

    /**
     * 備考その他内容
     */
    private String reOtherContents;

    /**
     * 報酬月額（現物）
     */
    private Integer remuMonthlyKind;

    /**
     * 報酬月額（金額）
     */
    private Integer remuMonthlyAmount;

    /**
     * 報酬月額合計
     */
    private Integer totalMonthlyRemun;

    /**
     * 海外在住
     */
    private Integer livingAbroad;

    /**
     * 理由その他
     */
    private Integer reasonOther;

    /**
     * 坑内員区分
     */
    private Integer undergroundDivision;

    /**
     * 短期在留
     */
    private Integer shortTermResidence;

    /**
     * 被扶養者届出区分
     */
    private Integer dependentNotiClass;

    /**
     * 資格取得備考
     */
    private String quaAcquiRemarks;

    /**
     * 資格取得区分
     */
    private Integer qualifiClass;

    /**
     * 退職後の継続再雇用者
     */
    private Integer contiReemAfRetirement;

    /**
     * 短時間労働者
     */
    private Integer shortTimeWorker;


}
