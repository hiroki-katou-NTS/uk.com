package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 社会保険取得時情報
*/
@Getter
public class SocialInsurAcquisiInfor extends AggregateRoot {

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
    private Optional<Integer> PercentOrMore;

    /**
    * 備考その他
    */
    private Optional<Integer> remarksOther;
    
    /**
    * 備考その他内容
    */
    private Optional<QualificationAcquiNoti> remarksAndOtherContents;
    
    /**
    * 報酬月額（現物）
    */
    private Optional<RemuneraMonthly> remunMonthlyAmountKind;
    
    /**
    * 報酬月額（金額）
    */
    private Optional<RemuneraMonthly> remunMonthlyAmount;
    
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
    * 理由その他内容
    */
    private Optional<ReaForNotiOfQuatification> reasonAndOtherContents;
    
    /**
    * 短期在留
    */
    private Optional<Integer> shortStay;
    
    /**
    * 被扶養者届出区分
    */
    private Optional<DepenNotiAttachCtg> depenAppoint;
    
    /**
    * 資格取得区分
    */
    private Optional<SocialInsurQuaAcquiClass> qualifiDistin;
    
    /**
    * 短時間労働者
    */
    private Optional<Integer> shortTimeWorkers;
    
    /**
    * 退職後の継続再雇用者
    */
    private Optional<Integer> continReemAfterRetirement;
    
    public SocialInsurAcquisiInfor(
                                   String employeeId,
                                   Integer PercentOrMore,
                                   Integer remarksOther,
                                   String  remarksAndOtherContents,
                                   Integer remunMonthlyAmountKind,
                                   Integer remunMonthlyAmount,
                                   Integer totalMonthlyRemun,
                                   Integer livingAbroad,
                                   Integer reasonOther,
                                   String reasonAndOtherContents,
                                   Integer shortTimeWorkes,
                                   Integer shortStay,
                                   Integer depenAppoint,
                                   Integer qualifiDistin,
                                   Integer continReemAfterRetirement
    ) {

        this.employeeId = employeeId;
        this.remunMonthlyAmount = remunMonthlyAmount == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remunMonthlyAmount));
        this.remunMonthlyAmountKind = remunMonthlyAmountKind == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remunMonthlyAmountKind));
        this.totalMonthlyRemun = totalMonthlyRemun == null ? Optional.empty() : Optional.of(new RemuneraMonthly(totalMonthlyRemun));
        this.depenAppoint = depenAppoint == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(depenAppoint, DepenNotiAttachCtg.class));
        this.qualifiDistin = qualifiDistin == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(qualifiDistin, SocialInsurQuaAcquiClass.class));
        this.PercentOrMore = Optional.ofNullable(PercentOrMore);
        this.shortTimeWorkers = Optional.ofNullable(shortTimeWorkes);
        this.continReemAfterRetirement = Optional.ofNullable(continReemAfterRetirement);
        this.remarksOther = Optional.ofNullable(remarksOther);
        this.remarksAndOtherContents = remarksAndOtherContents == null ? Optional.empty() : Optional.of(new QualificationAcquiNoti(remarksAndOtherContents));
        this.livingAbroad = Optional.ofNullable(livingAbroad);
        this.shortStay = Optional.ofNullable(shortStay);
        this.reasonOther = Optional.ofNullable(reasonOther);
        this.reasonAndOtherContents = reasonAndOtherContents == null ? Optional.empty() : Optional.of(new ReaForNotiOfQuatification(reasonAndOtherContents));
    }


    public SocialInsurAcquisiInfor(
            String companyId,
            String employeeId,
            Integer PercentOrMore,
            Integer remarksOther,
            String  remarksAndOtherContents,
            Integer remunMonthlyAmountKind,
            Integer remunMonthlyAmount,
            Integer totalMonthlyRemun,
            Integer livingAbroad,
            Integer reasonOther,
            String reasonAndOtherContents,
            Integer shortTimeWorkes,
            Integer shortStay,
            Integer depenAppoint,
            Integer qualifiDistin,
            Integer continReemAfterRetirement
    ) {
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.remunMonthlyAmount = remunMonthlyAmount == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remunMonthlyAmount));
        this.remunMonthlyAmountKind = remunMonthlyAmountKind == null ? Optional.empty() : Optional.of(new RemuneraMonthly(remunMonthlyAmountKind));
        this.totalMonthlyRemun = totalMonthlyRemun == null ? Optional.empty() : Optional.of(new RemuneraMonthly(totalMonthlyRemun));
        this.depenAppoint = depenAppoint == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(depenAppoint, DepenNotiAttachCtg.class));
        this.qualifiDistin = qualifiDistin == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(qualifiDistin, SocialInsurQuaAcquiClass.class));
        this.PercentOrMore = Optional.ofNullable(PercentOrMore);
        this.shortTimeWorkers = Optional.ofNullable(shortTimeWorkes);
        this.continReemAfterRetirement = Optional.ofNullable(continReemAfterRetirement);
        this.remarksOther = Optional.ofNullable(remarksOther);
        this.remarksAndOtherContents = remarksAndOtherContents == null ? Optional.empty() : Optional.of(new QualificationAcquiNoti(remarksAndOtherContents));
        this.livingAbroad = Optional.ofNullable(livingAbroad);
        this.shortStay = Optional.ofNullable(shortStay);
        this.reasonOther = Optional.ofNullable(reasonOther);
        this.reasonAndOtherContents = reasonAndOtherContents == null ? Optional.empty() : Optional.of(new ReaForNotiOfQuatification(reasonAndOtherContents));
    }

    public SocialInsurAcquisiInfor( String companyId,
                                    String employeeId,
                                    Integer continReemAfterRetirement){
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.continReemAfterRetirement = Optional.ofNullable(continReemAfterRetirement);
    }

}
