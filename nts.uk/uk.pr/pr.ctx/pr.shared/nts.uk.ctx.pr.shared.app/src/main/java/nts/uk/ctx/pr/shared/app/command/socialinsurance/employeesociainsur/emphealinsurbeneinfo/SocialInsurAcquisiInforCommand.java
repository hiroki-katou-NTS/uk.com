package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class SocialInsurAcquisiInforCommand
{

    private String companyId;
    /**
    * 社員ID
    */
    private String employeeId;

    /**
    * 70歳以上被用者
    */
    private Integer percentOrMore;

    /**
    * 備考その他
    */
    private Integer remarksOther;

    /**
    * 備考その他内容
    */
    private String remarksAndOtherContents;

    /**
    * 報酬月額（現物）
    */
    private Integer remunMonthlyAmountKind;

    /**
    * 報酬月額（金額）
    */
    private Integer remunMonthlyAmount;

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
    * 理由その他内容
    */
    private String reasonAndOtherContents;



    /**
    * 短期在留
    */
    private Integer shortStay;

    /**
    * 被扶養者届出区分
    */
    private Integer depenAppoint;


    /**
     * 資格取得区分
     */
    private Integer qualifiDistin;

    /**
     * 短時間労働者
     */
    private Integer shortTimeWorkers;

    /**
     * 退職後の継続再雇用者
     */
    private Integer continReemAfterRetirement;

    

}
