package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_INS_LOSS_INFO")
public class QqsmtEmpInsLossInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtEmpInsLossInfoPk empInsLossInfoPk;

    /**
     * 労働時間
     */
    @Basic(optional = true)
    @Column(name = "WORKING_TIME")
    public int workingTime;

    /**
     * 喪失原因
     */
    @Basic(optional = true)
    @Column(name = "CAUSE_OF_LOSS_ATR")
    public int causeOfLossAtr;

    /**
     * 雇用保険喪失原因
     */
    @Basic(optional = true)
    @Column(name = "CAUSE_OF_LOST_EMP_INS")
    public String causeOfLostEmpIns;

    /**
     * 補充予定
     */
    @Basic(optional = true)
    @Column(name = "SCHE_REPLEN_ATR")
    public int scheReplenAtr;

    /**
     * 離職票交付希望
     */
    @Basic(optional = true)
    @Column(name = "REQ_ISSU_ATR")
    public int reqIssuAtr;

    @Override
    protected Object getKey()
    {
        return empInsLossInfoPk;
    }

    public EmpInsLossInfo toDomain() {
        return new EmpInsLossInfo(
                this.empInsLossInfoPk.cId,
                this.empInsLossInfoPk.sId,
                this.causeOfLossAtr,
                this.reqIssuAtr,
                this.scheReplenAtr,
                this.causeOfLostEmpIns,
                this.workingTime);
    }
    public static QqsmtEmpInsLossInfo toEntity(EmpInsLossInfo domain) {
        return new QqsmtEmpInsLossInfo(
                new QqsmtEmpInsLossInfoPk(domain.getCId(), domain.getSId()),
                domain.getScheduleWorkingHourPerWeek().map( i -> i.v()).orElse(null),
                domain.getCauseOfLossAtr().map( i -> i.value).orElse(null),
                domain.getCauseOfLossEmpInsurance().map( i -> i.toString()).orElse(null),
                domain.getScheduleForReplenishment().map( i -> i.value).orElse(null),
                domain.getRequestForIssuance().map( i -> i.value).orElse(null)
        );
    }

}
