package nts.uk.ctx.at.record.pub.application.divergence;

import java.util.List;

// DivergenceReasonInputMethod at record , so do not use at request
public interface DivergenceReasonInputMethodPub {
	
	public List<DivergenceReasonInputMethodExport> getData(String companyId, List<Integer> lstNo);
}
