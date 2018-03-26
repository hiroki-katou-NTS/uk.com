package nts.uk.ctx.at.record.app.find.divergence.time;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;

/**
 * The Class DivergenceReasonInputMethodDto.
 */
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

	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {
		this.divergenceTimeNo = DivergenceTimeNo;

	}

	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;

	}

	@Override
	public void setDivergenceReasonInputed(boolean divergenceReasonInputed) {
		this.divergenceReasonInputed = divergenceReasonInputed;

	}

	@Override
	public void setDivergenceReasonSelected(boolean divergenceReasonSelected) {
		this.divergenceReasonSelected = divergenceReasonSelected;

	}

	@Override
	public void setReasons(List<DivergenceReasonSelect> reasons) {
		this.reasons = reasons;

	}

	@Override
	public boolean getDivergenceReasonInputed() {
		return divergenceReasonInputed;
	}

	@Override
	public boolean getDivergenceReasonSelected() {
		return divergenceReasonSelected;
	}

	@Override
	public int getDivergenceTimeNo() {
		return divergenceTimeNo;
	}

	@Override
	public String getCompanyId() {
		return companyId;
	}

	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return reasons;
	}

}
