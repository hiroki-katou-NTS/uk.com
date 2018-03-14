package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;

public interface DivergenceReasonInputMethodSetMemento {
	/**
	 * Sets the new divergence time no.
	 *
	 * @param the new divergence time no
	 */
	void setDivergenceTimeNo(int DivergenceTimeNo);
	/**
	 * Sets the new companyid.
	 *
	 * @param the new companyId
	 */
	void setCompanyId(String companyId);
	/**
	 * Sets the new divergence reason input
	 *
	 * @param the new divergence reason input
	 */
	void setDivergenceReasonInputed(boolean divergenceReasonInputed);
	/**
	 * Sets the new divergence reason select
	 *
	 * @param the new divergence reason select
	 */
	void setDivergenceReasonSelected(boolean divergenceReasonSelected);
	/**
	 * Sets the new divergence reasons
	 *
	 * @param the new divergence reasons
	 */
	void setReasons(List<DivergenceReasonSelect> reason);
}
