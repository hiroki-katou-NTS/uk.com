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
@Table(name = "QQSDT_SYAHO_GET_INFO")
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
    @Column(name = "OVER_70_ATR")
    public Integer percentOrMore;
    
    /**
    * 備考その他
    */
    @Basic(optional = true)
    @Column(name = "BIKO_SONOTA_ATR")
    public Integer remarksOther;
    
    /**
    * 備考その他内容
    */
    @Basic(optional = true)
    @Column(name = "BIKO_SONOTA_REASON")
    public String remarksAndOtherContents;
    
    /**
    * 報酬月額（現物）
    */
    @Basic(optional = true)
    @Column(name = "HOSYU_IN_KIND")
    public Integer remunMonthlyAmountKind;
    
    /**
    * 報酬月額（金額）
    */
    @Basic(optional = true)
    @Column(name = "HOSYU_CURR")
    public Integer remunMonthlyAmount;
    
    /**
    * 報酬月額合計
    */
    @Basic(optional = true)
    @Column(name = "HOSYU_MONTHLY")
    public Integer totalMonthlyRemun;
    
    /**
    * 海外在住
      短期在留
      理由その他
    */
    @Basic(optional = true)
    @Column(name = "NO_MYNUM_ATR")
    public Integer livingAbroad;

    /**
    * 理由その他内容
    */
    @Basic(optional = true)
    @Column(name = "NO_MYNUM_REASON")
    public String reasonAndOtherContents;
    
    /**
    * 短時間労働者

     */
    @Basic(optional = true)
    @Column(name = "SHORTTIME_WORKERS_ATR")
    public Integer shortTimeWorkes;
    
    /**
    * 被扶養者届出区分
    */
    @Basic(optional = true)
    @Column(name = "RPT_SUBMIT_ATR")
    public Integer depenAppoint;
    
    /**
    * 社会保険資格取得区分
    */
    @Basic(optional = true)
    @Column(name = "SYAHO_GET_ATR")
    public Integer qualifiDistin;
    
    /**
    * 退職後の継続再雇用者
    */
    @Basic(optional = true)
    @Column(name = "CONTINUE_REEMPLOYED_ATR")
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
                this.livingAbroad != null && this.livingAbroad == 1 ? 1 : 0,
                this.livingAbroad != null && this.livingAbroad == 2 ? 1 : 0,
                this.reasonAndOtherContents,
                this.shortTimeWorkes,
                this.livingAbroad != null && this.livingAbroad == 3 ? 1 : 0,
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
                this.livingAbroad != null && this.livingAbroad == 1 ? 1 : 0,
                this.livingAbroad != null && this.livingAbroad == 3 ? 1 : 0,
                this.reasonAndOtherContents,
                this.shortTimeWorkes,
                this.livingAbroad != null && this.livingAbroad == 2 ? 1 : 0,
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
                QqsmtSocIsacquisiInfo.getLiving(domain),
                domain.getReasonAndOtherContents().isPresent() ? domain.getReasonAndOtherContents().get().v() : null,
                domain.getShortTimeWorkers().isPresent() ? domain.getShortTimeWorkers().get() : null,
                domain.getDepenAppoint().isPresent() ? domain.getDepenAppoint().get().value : null,
                domain.getQualifiDistin().isPresent() ? domain.getQualifiDistin().get().value : null,
                domain.getContinReemAfterRetirement().isPresent() ? domain.getContinReemAfterRetirement().get() : null
        );

    }

    public static Integer getLiving(SocialInsurAcquisiInfor domain){
        if(domain.getLivingAbroad().isPresent() && domain.getLivingAbroad().get() == 1){
            return 1;
        }

        if(domain.getReasonOther().isPresent() && domain.getReasonOther().get() == 1) {
            return 3;
        }

        if(domain.getShortStay().isPresent() && domain.getShortStay().get() == 1) {
            return 2;
        }
        return 0;
    }

}
