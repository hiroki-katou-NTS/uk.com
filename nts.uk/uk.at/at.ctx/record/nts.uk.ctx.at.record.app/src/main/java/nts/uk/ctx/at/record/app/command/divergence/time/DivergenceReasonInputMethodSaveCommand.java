package nts.uk.ctx.at.record.app.command.divergence.time;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
public class DivergenceReasonInputMethodSaveCommand implements DivergenceReasonInputMethodGetMemento {

	/** The divergence time no. */
	private int divergenceTimeNo;

	/** The divergence reason inputed. */
	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */
	private boolean divergenceReasonSelected;

	@Override
	public int getDivergenceTimeNo() {
		return this.divergenceTimeNo;
	}

	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	@Override
	public boolean getDivergenceReasonInputed() {
		return this.divergenceReasonInputed;
	}

	@Override
	public boolean getDivergenceReasonSelected() {
		return this.divergenceReasonSelected;
	}

	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return null;
	}

}
