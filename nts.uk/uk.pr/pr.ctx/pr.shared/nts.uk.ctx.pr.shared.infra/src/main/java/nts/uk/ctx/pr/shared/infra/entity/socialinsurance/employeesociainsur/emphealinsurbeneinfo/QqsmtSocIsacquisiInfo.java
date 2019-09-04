package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社会保険取得時情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_SOC_ISACQUISI_INFO")
public class QqsmtSocIsacquisiInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtSocIsacquisiInfoPk socIsacquisiInfoPk;
    
    /**
    * 70歳以上被用者
    */
    @Basic(optional = true)
    @Column(name = "PERCENT_OR_MORE")
    public Integer percentOrMore;
    
    /**
    * 備考その他
    */
    @Basic(optional = true)
    @Column(name = "REMARKS_OTHER")
    public Integer remarksOther;
    
    /**
    * 備考その他内容
    */
    @Basic(optional = true)
    @Column(name = "REMARKS_AND_OTHER_CONTENTS")
    public String remarksAndOtherContents;
    
    /**
    * 報酬月額（現物）
    */
    @Basic(optional = true)
    @Column(name = "REMUN_MONTHLY_AMOUNT_KIND")
    public Integer remunMonthlyAmountKind;
    
    /**
    * 報酬月額（金額）
    */
    @Basic(optional = true)
    @Column(name = "REMUN_MONTHLY_AMOUNT")
    public Integer remunMonthlyAmount;
    
    /**
    * 報酬月額合計
    */
    @Basic(optional = true)
    @Column(name = "TOTAL_MONTHLY_REMUN")
    public Integer totalMonthlyRemun;
    
    /**
    * 海外在住
    */
    @Basic(optional = true)
    @Column(name = "LIVING_ABROAD")
    public Integer livingAbroad;
    
    /**
    * 理由その他
    */
    @Basic(optional = true)
    @Column(name = "REASON_OTHER")
    public Integer reasonOther;
    
    /**
    * 理由その他内容
    */
    @Basic(optional = true)
    @Column(name = "REASON_AND_OTHER_CONTENTS")
    public String reasonAndOtherContents;
    
    /**
    * 社員ID
    */
    @Basic(optional = true)
    @Column(name = "SHORT_TIME_WORKES")
    public Integer shortTimeWorkes;
    
    /**
    * 短期在留
    */
    @Basic(optional = true)
    @Column(name = "SHORT_STAY")
    public Integer shortStay;
    
    /**
    * 被扶養者届出区分
    */
    @Basic(optional = true)
    @Column(name = "DEPEN_APPOINT")
    public Integer depenAppoint;
    
    /**
    * 社会保険資格取得区分
    */
    @Basic(optional = true)
    @Column(name = "QUALIFI_DISTIN")
    public Integer qualifiDistin;
    
    /**
    * 退職後の継続再雇用者
    */
    @Basic(optional = true)
    @Column(name = "CONTIN_REEM_AFTER_RETIREMENT")
    public Integer continReemAfterRetirement;
    
    @Override
    protected Object getKey()
    {
        return socIsacquisiInfoPk;
    }

    public SocialInsurAcquisiInfor toDomain() {
        return new SocialInsurAcquisiInfor(this.socIsacquisiInfoPk.employeeId,
                this.percentOrMore,
                this.remarksOther,
                this.remarksAndOtherContents,
                this.remunMonthlyAmountKind,
                this.remunMonthlyAmount,
                this.totalMonthlyRemun,
                this.livingAbroad,
                this.reasonOther,
                this.reasonAndOtherContents,
                this.shortTimeWorkes,
                this.shortStay,
                this.depenAppoint,
                this.qualifiDistin,
                this.continReemAfterRetirement
                );
    }

    public SocialInsurAcquisiInfor toDomains() {
        return new SocialInsurAcquisiInfor(
                this.socIsacquisiInfoPk.companyId,
                this.socIsacquisiInfoPk.employeeId,
                this.percentOrMore,
                this.remarksOther,
                this.remarksAndOtherContents,
                this.remunMonthlyAmountKind,
                this.remunMonthlyAmount,
                this.totalMonthlyRemun,
                this.livingAbroad,
                this.reasonOther,
                this.reasonAndOtherContents,
                this.shortTimeWorkes,
                this.shortStay,
                this.depenAppoint,
                this.qualifiDistin,
                this.continReemAfterRetirement
        );
    }



    public static QqsmtSocIsacquisiInfo toEntity(SocialInsurAcquisiInfor domain) {
         return new QqsmtSocIsacquisiInfo(
                 new QqsmtSocIsacquisiInfoPk(domain.getCompanyId(),domain.getEmployeeId()),
                 domain.getPercentOrMore().isPresent() ? domain.getPercentOrMore().get() : null,
                 domain.getRemarksOther().isPresent() ? domain.getRemarksOther().get(): null,
                 domain.getRemarksAndOtherContents().isPresent() ? domain.getRemarksAndOtherContents().get().v(): null,
                 domain.getRemunMonthlyAmountKind().isPresent() ? domain.getRemunMonthlyAmountKind().get().v() : null,
                 domain.getRemunMonthlyAmount().isPresent() ? domain.getRemunMonthlyAmount().get().v() : null,
                 domain.getTotalMonthlyRemun().isPresent() ? domain.getTotalMonthlyRemun().get().v() : null,
                 domain.getLivingAbroad().isPresent() ? domain.getLivingAbroad().get() : null,
                 domain.getReasonOther().isPresent() ? domain.getReasonOther().get() : null,
                 domain.getReasonAndOtherContents().isPresent() ? domain.getReasonAndOtherContents().get().v() : null,
                 domain.getShortTimeWorkers().isPresent() ? domain.getShortTimeWorkers().get() : null,
                 domain.getShortStay().isPresent() ? domain.getShortStay().get() : null,
                 domain.getDepenAppoint().isPresent() ? domain.getDepenAppoint().get().value : null,
                 domain.getQualifiDistin().isPresent() ? domain.getQualifiDistin().get().intValue() : null,
                 domain.getContinReemAfterRetirement().isPresent() ? domain.getContinReemAfterRetirement().get() : null
         );

    }

}
