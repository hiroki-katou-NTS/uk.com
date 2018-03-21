package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonSelect;


public class DivergenceReasonInputMethodDto
		implements DivergenceReasonInputMethodSetMemento, DivergenceReasonInputMethodGetMemento {

	/** The divergence time no. */

	private int divergenceTimeNo;

	/** The companyId. */

	private String companyId;

	/** The divergence reason inputed. */

	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */

	private boolean divergenceReasonSelected;

	/** The reason list. */

	private List<DivergenceReasonSelect> reasons;

	/**
	 * Instantiates a new divergence reason input method dto.
	 *
	 * @param DivergenceTimeNo
	 *            the new divergence time no
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceTimeNo(int)
	 */
	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {
		this.divergenceTimeNo = DivergenceTimeNo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceReasonInputed(boolean)
	 */
	@Override
	public void setDivergenceReasonInputed(boolean divergenceReasonInputed) {
		this.divergenceReasonInputed = divergenceReasonInputed;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceReasonSelected(
	 * boolean)
	 */
	@Override
	public void setDivergenceReasonSelected(boolean divergenceReasonSelected) {
		this.divergenceReasonSelected = divergenceReasonSelected;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setReasons(java.util.List)
	 */
	@Override
	public void setReasons(List<DivergenceReasonSelect> reasons) {
		this.reasons = reasons;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceReasonInputed()
	 */
	@Override
	public boolean getDivergenceReasonInputed() {
		return divergenceReasonInputed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceReasonSelected()
	 */
	@Override
	public boolean getDivergenceReasonSelected() {
		return divergenceReasonSelected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public int getDivergenceTimeNo() {
		return divergenceTimeNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getReasons()
	 */
	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return reasons;
	}

}
