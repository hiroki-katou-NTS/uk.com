package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;

import java.util.Optional;

/**
* 厚生年金保険取得時情報
*/
@Getter
public class InforOnWelfPenInsurAcc extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 健保同一区分
    */
    private HealInsurSameCtg healInsurDis;
    
    /**
    * 理由その他内容
    */
    private ReaForNotiOfQuatification reasonAndOContent;
    
    /**
    * 70歳以上被用者
    */
    private Optional<Integer> YearsOldOrOlder;
    
    /**
    * 備考その他
    */
    private Optional<Integer> remarksOther;
    
    /**
    * 報酬月額（現物）
    */
    private Optional<RemuneraMonthly> remuMonthlyKind;
    
    /**
    * 報酬月額（金額）
    */
    private Optional<RemuneraMonthly> remuMonthlyAmount;
    
    /**
    * 報酬月額合計
    */
    private Optional<RemuneraMonthly> totalMonthlyRemun;
    
    /**
    * 海外在住
    */
    private Optional<Integer> livingAbroad;
    
    /**
    * 理由その他
    */
    private Optional<Integer> reasonOther;
    
    /**
    * 短期在留
    */
    private Optional<Integer> shortTermResidence;
    
    /**
    * 被扶養者届出区分
    */
    private Optional<DepenNotiAttachCtg> dependentNotiClass;
    
    /**
    * 資格取得備考
    */
    private Optional<RemarkForQuaCompany> quaAcquiRemarks;
    
    /**
    * 資格取得区分
    */
    private Optional<Integer> qualifiClass;
    
    /**
    * 退職後の継続再雇用者
    */
    private Optional<Integer> contiReemAfRetirement;
    
    /**
    * 短時間労働者
    */
    private Optional<Integer> shortTimeWorker;
    
    /**
    * 備考その他内容
    */
    private Optional<QualificationAcquiNoti> reOtherContents;
    
    public InforOnWelfPenInsurAcc(String employeeId,
                                  int healInsurDis,
                                  String reaAndOtherCont,
                                  Integer yearsOlder,
                                  Integer remarksOther,
                                  String reOtherContent,
                                  Integer remuMontKind,
                                  Integer remuMontAmount,
                                  Integer totalMontAmount,
                                  Integer livingAbroad,
                                  Integer reasonOther,
                                  Integer shortTimeWorker,
                                  Integer shortTermResidence,
                                  Integer depenNotiClass,
                                  String quaAcquiRemarks,
                                  Integer qualifiClass,
                                  Integer contiReemAfRetirement) {
        this.employeeId = employeeId;
        this.healInsurDis = EnumAdaptor.valueOf(healInsurDis, HealInsurSameCtg.class);
        this.remuMonthlyAmount = remuMontAmount == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remuMontAmount));
        this.remuMonthlyKind = remuMontKind == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remuMontKind));
        this.totalMonthlyRemun = totalMontAmount == null ? Optional.empty() : Optional.of(new RemuneraMonthly(totalMontAmount));
        this.dependentNotiClass = depenNotiClass == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(depenNotiClass, DepenNotiAttachCtg.class));
        this.quaAcquiRemarks = quaAcquiRemarks == null ? Optional.empty() : Optional.of(new RemarkForQuaCompany(quaAcquiRemarks));
        this.qualifiClass = Optional.ofNullable(qualifiClass);
        this.YearsOldOrOlder = Optional.ofNullable(yearsOlder);
        this.shortTimeWorker = Optional.ofNullable(shortTimeWorker);
        this.contiReemAfRetirement = Optional.ofNullable(contiReemAfRetirement);
        this.remarksOther = Optional.ofNullable(remarksOther);
        this.reOtherContents = reOtherContent  == null ? Optional.empty() : Optional.of(new QualificationAcquiNoti(reOtherContent));
        this.livingAbroad = Optional.ofNullable(livingAbroad);
        this.shortTermResidence = Optional.ofNullable(shortTermResidence);
        this.reasonOther = Optional.ofNullable(reasonOther);
        this.reasonAndOContent = new ReaForNotiOfQuatification(reaAndOtherCont);
    }
    
}
