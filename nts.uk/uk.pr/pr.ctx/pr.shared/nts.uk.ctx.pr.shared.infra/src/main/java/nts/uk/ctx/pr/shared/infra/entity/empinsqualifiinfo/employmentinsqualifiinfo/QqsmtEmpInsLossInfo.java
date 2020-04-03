package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
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
    public Integer workingTime;

    /**
     * 喪失原因
     */
    @Basic(optional = true)
    @Column(name = "CAUSE_OF_LOSS")
    public Integer causeOfLoss;

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
    public Integer scheReplenAtr;

    /**
     * 離職票交付希望
     */
    @Basic(optional = true)
    @Column(name = "REQ_ISSU_ATR")
    public Integer requestForIssuance;

    @Override
    protected Object getKey()
    {
        return empInsLossInfoPk;
    }

    public EmpInsLossInfo toDomain() {
        return new EmpInsLossInfo(
                this.empInsLossInfoPk.cId,
                this.empInsLossInfoPk.sId,
                this.causeOfLoss,
                this.requestForIssuance,
                this.scheReplenAtr,
                this.causeOfLostEmpIns,
                this.workingTime);
    }
    public static QqsmtEmpInsLossInfo toEntity(EmpInsLossInfo domain) {
        return new QqsmtEmpInsLossInfo(new QqsmtEmpInsLossInfoPk(domain.getCId(), domain.getSId()),
                domain.getScheduleWorkingHourPerWeek().map(PrimitiveValueBase::v).orElse(null),
                domain.getCauseOfLoss().isPresent() ? domain.getCauseOfLoss().get().value : null,
                domain.getCauseOfLossEmpInsurance().map(PrimitiveValueBase::v).orElse(null),
                domain.getScheduleForReplenishment().isPresent() ? domain.getScheduleForReplenishment().get().value : null,
                domain.getRequestForIssuance().isPresent() ? domain.getRequestForIssuance().get().value : null
        );
    }

}
