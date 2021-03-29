package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.List;
/**
 * Refactor5 
 * RQ693
 * @author hoangnd
 *
 */
public interface DivergenceReasonInputMethodI {
	
	public List<DivergenceReasonInputMethod> getData(String companyId, List<Integer> lstNo);
}
