package nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Entity 任意集計期間
 *
 * @author nws-minhnb
 */
@Data
@Entity
@Table(name = "KRCMT_OPTIONALAGGR_PERIOD")
@EqualsAndHashCode(callSuper = true)
public class KrcmtOptionalAggrPeriod extends UkJpaEntity
		implements AnyAggrPeriod.MementoGetter, AnyAggrPeriod.MementoSetter, Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcmtOptionalAggrPeriodPK krcmtOptionalAggrPeriodPK;

	@Column(name = "OPTIONAL_AGGR_NAME")
	private String optionalAggrName;

	@Column(name = "START_DATE")
	private GeneralDate startDate;

	@Column(name = "END_DATE")
	private GeneralDate endDate;

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.krcmtOptionalAggrPeriodPK;
	}

	/**
	 * Sets company id.
	 *
	 * @param companyId the company id
	 */
	@Override
	public void setCompanyId(String companyId) {
		if (this.krcmtOptionalAggrPeriodPK == null) {
			this.krcmtOptionalAggrPeriodPK = new KrcmtOptionalAggrPeriodPK();
		}
		this.krcmtOptionalAggrPeriodPK.companyId = companyId;
	}

	/**
	 * Sets aggr frame code.
	 *
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void setAggrFrameCode(String aggrFrameCode) {
		if (this.krcmtOptionalAggrPeriodPK == null) {
			this.krcmtOptionalAggrPeriodPK = new KrcmtOptionalAggrPeriodPK();
		}
		this.krcmtOptionalAggrPeriodPK.aggrFrameCode = aggrFrameCode;
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.krcmtOptionalAggrPeriodPK.companyId;
	}

	/**
	 * Gets aggr frame code.
	 *
	 * @return the aggr frame code
	 */
	@Override
	public String getAggrFrameCode() {
		return this.krcmtOptionalAggrPeriodPK.aggrFrameCode;
	}
}
