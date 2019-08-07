package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAcc;

/**
* 厚生年金保険取得時情報: DTO
*/
@AllArgsConstructor
@Value
public class InforOnWelfPenInsurAccDto
{
    
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
    private Integer yearsOldOrOlder;
    
    /**
    * 備考その他
    */
    private Integer remarksOther;
    
    /**
    * 備考その他
    */
    private String reString;
    
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
    
    
    public static InforOnWelfPenInsurAccDto fromDomain(InforOnWelfPenInsurAcc domain)
    {
//        return new InforOnWelfPenInsurAccDto(domain.getEmployeeId(),
//                domain.getHealInsurDis().value,
//                domain.getReasonAndOContent().v(),
//                domain.getYearsOldOrOlder(),
//                domain.getRemarksOther(),
//                domain.getRemarksOther(),
//                domain.getRemuMonthlyKind().map(i->i.v()).orElse(null),
//                domain.getRemuMonthlyAmount().map(i->i.v()).orElse(null),
//                domain.getTotalMonthlyRemun().map(i->i.v()).orElse(null),
//                domain.getLivingAbroad(),
//                domain.getReasonOther(),
//                domain.getReOtherContents(),
//                domain.getShortTermResidence(),
//                domain.getDependentNotiClass().map(i->i.value).orElse(null),
//                domain.getQuaAcquiRemarks().map(i->i.v()).orElse(null),
//                domain.getQualifiClass(),
//                domain.getContiReemAfRetirement()
//        );
        return null;
    }
    
}
