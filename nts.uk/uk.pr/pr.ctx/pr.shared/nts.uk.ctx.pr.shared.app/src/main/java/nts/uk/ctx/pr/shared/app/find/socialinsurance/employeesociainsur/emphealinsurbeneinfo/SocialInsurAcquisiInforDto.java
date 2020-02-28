package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;


/**
* 社会保険取得時情報: DTO
*/
@AllArgsConstructor
@Value
public class SocialInsurAcquisiInforDto {
    /**
     * 会社ID
     */
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


    private Integer shortTimeWorkers;
    
    /**
    * 被扶養者届出区分
    */
    private Integer depenAppoint;


    /**
     * 退職後の継続再雇用者
     */
    private Integer continReemAfterRetirement;
    
    
    public static SocialInsurAcquisiInforDto fromDomain(SocialInsurAcquisiInfor domain)
    {
        return new SocialInsurAcquisiInforDto(
                domain.getCompanyId(),
                domain.getEmployeeId(),
                domain.getPercentOrMore().map(i->i.intValue()).orElse(null),
                domain.getRemarksOther().map(i->i.intValue()).orElse(null),
                domain.getRemarksAndOtherContents().map(i->i.v()).orElse(null),
                domain.getRemunMonthlyAmountKind().map(i->i.v()).orElse(null),
                domain.getRemunMonthlyAmount().map(i->i.v()).orElse(null),
                domain.getTotalMonthlyRemun().map(i->i.v()).orElse(null),
                domain.getLivingAbroad().map(i->i.intValue()).orElse(null),
                domain.getReasonOther().map(i->i.intValue()).orElse(null),
                domain.getReasonAndOtherContents().map(i->i.v()).orElse(null),
                domain.getShortStay().map(i->i.intValue()).orElse(null),
                domain.getShortTimeWorkers().map(i -> i.intValue()).orElse(null),
                domain.getDepenAppoint().map(i->i.value).orElse(null),
                //domain.getDepenAppoint() == null ? null : domain.getDepenAppoint().get().value,
                domain.getContinReemAfterRetirement().map(i->i.intValue()).orElse(null));
    }
    
}
