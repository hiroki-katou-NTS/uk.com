package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;


/**
* 社会保険取得時情報: DTO
*/
@AllArgsConstructor
@Value
public class SocialInsurAcquisiInforDto
{

    private String cid;

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

    
    
    public static SocialInsurAcquisiInforDto fromDomain(SocialInsurAcquisiInfor domain)
    {
        return new SocialInsurAcquisiInforDto(
                domain.getCompanyId(),
                domain.getEmployeeId(),
                domain.getPercentOrMore().isPresent() ? domain.getPercentOrMore().get() : null,
                domain.getRemarksOther().isPresent() ? domain.getRemarksOther().get() : null,
                domain.getRemarksAndOtherContents().isPresent() ? domain.getRemarksAndOtherContents().get().v() :null,
                domain.getRemunMonthlyAmountKind().isPresent() ? domain.getRemunMonthlyAmountKind().get().v() :null,
                domain.getRemunMonthlyAmount().isPresent() ? domain.getRemunMonthlyAmount().get().v() : null,
                domain.getTotalMonthlyRemun().isPresent()  ? domain.getTotalMonthlyRemun().get().v() : null,
                domain.getLivingAbroad().isPresent() ? domain.getLivingAbroad().get() : null,
                domain.getReasonOther().isPresent() ? domain.getReasonOther().get() : null,
                domain.getReasonAndOtherContents().isPresent() ? domain.getReasonAndOtherContents().get().v() : null,
                domain.getShortStay().isPresent() ? domain.getShortStay().get() : null,
                domain.getDepenAppoint().isPresent() ? domain.getDepenAppoint().get().value : null,
                domain.getQualifiDistin().isPresent() ? domain.getQualifiDistin().get().value : null,
                domain.getShortTimeWorkers().isPresent() ? domain.getShortTimeWorkers().get() : null,
                domain.getContinReemAfterRetirement().isPresent() ? domain.getContinReemAfterRetirement().get() : null


        );
    }
    
}
