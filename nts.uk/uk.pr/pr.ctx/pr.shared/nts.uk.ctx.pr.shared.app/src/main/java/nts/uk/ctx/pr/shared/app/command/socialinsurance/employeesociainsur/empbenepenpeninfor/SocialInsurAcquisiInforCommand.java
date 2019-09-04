package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.DepenNotiAttachCtg;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QualificationAcquiNoti;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.ReaForNotiOfQuatification;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.RemuneraMonthly;

import java.util.Optional;

@Value
public class SocialInsurAcquisiInforCommand {
    private String companyId;
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 70歳以上被用者
     */
    private int PercentOrMore;

    /**
     * 備考その他
     */
    private int remarksOther;

    /**
     * 備考その他内容
     */
    private String remarksAndOtherContents;

    /**
     * 報酬月額（現物）
     */
    private int remunMonthlyAmountKind;

    /**
     * 報酬月額（金額）
     */
    private int remunMonthlyAmount;

    /**
     * 報酬月額合計
     */
    private int totalMonthlyRemun;

    /**
     * 海外在住
     */
    private int livingAbroad;

    /**
     * 理由その他
     */
    private int reasonOther;

    /**
     * 理由その他内容
     */
    private String reasonAndOtherContents;

    /**
     * 短期在留
     */
    private int shortStay;

    /**
     * 被扶養者届出区分
     */
    private int depenAppoint;

    /**
     * 資格取得区分
     */
    private int qualifiDistin;

    /**
     * 短時間労働者
     */
    private int shortTimeWorkers;

    /**
     * 退職後の継続再雇用者
     */
    private int continReemAfterRetirement;
}
