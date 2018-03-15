package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrt;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaCompanyDivergenceReferenceTimeGetMemento.
 */
public class JpaCompanyDivergenceReferenceTimeGetMemento implements CompanyDivergenceReferenceTimeGetMemento {

	/** The entity. */
	@Setter
	private KrcstDrt krcstDrt;

	public JpaCompanyDivergenceReferenceTimeGetMemento() {
	}

	public JpaCompanyDivergenceReferenceTimeGetMemento(KrcstDrt krcstDrt) {
		this.krcstDrt = krcstDrt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public Integer getDivergenceTimeNo() {
		return this.krcstDrt.getId().getDvgcTimeNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeGetMemento#getNotUseAtr()
	 */
	@Override
	public NotUseAtr getNotUseAtr() {
		return NotUseAtr.valueOf(this.krcstDrt.getDvgcTimeUseSet().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.krcstDrt.getId().getHistId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeGetMemento#getDivergenceReferenceTimeValue()
	 */
	@Override
	public Optional<DivergenceReferenceTimeValue> getDivergenceReferenceTimeValue() {
		BigDecimal alarmTime = this.krcstDrt.getAlarmTime();
		BigDecimal errorTime = this.krcstDrt.getErrorTime();

		return Optional.of(new DivergenceReferenceTimeValue(
				new DivergenceReferenceTime(alarmTime == null ? 0 : alarmTime.intValue()),
				new DivergenceReferenceTime(errorTime == null ? 0 : errorTime.intValue())));
	}

}
