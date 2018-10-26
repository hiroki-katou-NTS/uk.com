package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 等級毎標準月額
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_HEALTH_STD_GRA_MON")
public class QpbmtHealthInsuranceStandardGradePerMonth extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtHealthInsuranceStandardGradePerMonthPk healthStdGraMonPk;

    /**
     * 開始年月
     */
    @Basic(optional = false)
    @Column(name = "TARGET_START_YM")
    public int targetStartYm;

    /**
     * 終了年月
     */
    @Basic(optional = false)
    @Column(name = "TARGET_END_YM")
    public int targetEndYm;

    /**
     * 標準月額
     */
    @Basic(optional = false)
    @Column(name = "STANDARD_MONTHLY_FEE")
    public int standardMonthlyFee;

    /**
     * 報酬月額下限
     */
    @Basic(optional = false)
    @Column(name = "REWARD_MONTHLY_LOWER_LIMIT")
    public int rewardMonthlyLowerLimit;

    /**
     * 報酬月額上限
     */
    @Basic(optional = false)
    @Column(name = "REWARD_MONTHLY_UPPER_LIMIT")
    public int rewardMonthlyUpperLimit;

    @Override
    protected Object getKey()
    {
        return healthStdGraMonPk;
    }

}