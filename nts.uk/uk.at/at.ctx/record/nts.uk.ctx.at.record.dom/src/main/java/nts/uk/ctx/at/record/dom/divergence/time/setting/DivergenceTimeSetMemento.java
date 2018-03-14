package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;

public interface DivergenceTimeSetMemento {

	/**
	 * Sets the new divergence time no.
	 *
	 * @param the new divergence type
	 */
	void setDivergenceTimeNo(int DivergenceTimeNo);
	/**
	 * Sets the new Company id.
	 *
	 * @param the new Company id.
	 */
	void setCompanyId(String CompanyId);
	/**
	 * Sets the new divergence time Usage Set
	 *
	 * @param the new divergence time Usage Set
	 */
	void setDivTimeUseSet(DivergenceTimeUseSet divTimeUset);
	/**
	 * Sets divergence time name.
	 *
	 * @param divergence time name
	 */
	void setDivTimeName(DivergenceTimeName divTimeName);
	/**
	 * Sets the new divergence type.
	 *
	 * @param the new divergence type
	 */
	void setDivType(DivergenceType divType);
	
	/**
	 * Sets the new error cancel method.
	 *
	 * @param the new error cancel method
	 */
	void setErrorCancelMedthod(DivergenceTimeErrorCancelMethod errorCancelMedthod);
	
	/**
	 * Sets tarset items
	 *
	 * @param the new tarset items
	 */
	void setTarsetItems(List<Double> targetItems);
}
