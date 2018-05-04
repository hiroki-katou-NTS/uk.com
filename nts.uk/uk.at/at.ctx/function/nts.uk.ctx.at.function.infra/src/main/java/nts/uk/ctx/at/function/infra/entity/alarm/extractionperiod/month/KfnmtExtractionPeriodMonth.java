package nts.uk.ctx.at.function.infra.entity.alarm.extractionperiod.month;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name = "KFNMT_EXTRACT_PER_MONTH")
@NoArgsConstructor

public class KfnmtExtractionPeriodMonth extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtExtractionPeriodMonthPK extractionPeriodMonthPK;

	@Column(name = "STR_PREVIOUS_MONTH", nullable = true)
	public Integer strPreviousMonth;

	@Column(name = "STR_CURRENT_MONTH", nullable = true)
	public Integer strCurrentMonth;

	@Column(name = "STR_MONTH", nullable = true)
	public Integer strMonth;
	
	@Column(name = "END_PREVIOUS_MONTH", nullable = true)
	public Integer endPreviousMonth;

	@Column(name = "END_CURRENT_MONTH", nullable = true)
	public Integer endCurrentMonth;

	@Column(name = "END_MONTH", nullable = true)
	public Integer endMonth;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return extractionPeriodMonthPK;
	}
}
