package nts.uk.ctx.at.record.dom.divergencetime_new;

import java.util.Optional;

import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Interface CompanyDivergenceReferenceTimeSetMemento.
 */
public interface CompanyDivergenceReferenceTimeSetMemento {
	
	/**
	 * Sets the divergence time no.
	 *
	 * @param divergenceTimeNo the new divergence time no
	 */
	void setDivergenceTimeNo(DivergenceType divergenceTimeNo);
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the not use atr.
	 *
	 * @param notUseAtr the new not use atr
	 */
	void setNotUseAtr(NotUseAtr notUseAtr);
	
	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(String historyId);
	
	/**
	 * Sets the divergence reference time value.
	 *
	 * @param divergenceReferenceTimeValue the new divergence reference time value
	 */
	void setDivergenceReferenceTimeValue(Optional<DivergenceReferenceTimeValue> divergenceReferenceTimeValue);
}
