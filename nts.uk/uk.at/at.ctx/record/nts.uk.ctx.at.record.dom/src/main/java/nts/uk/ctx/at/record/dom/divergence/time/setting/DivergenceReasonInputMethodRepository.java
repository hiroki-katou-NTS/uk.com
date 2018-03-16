package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;

public interface DivergenceReasonInputMethodRepository {

	/**
	 * get all divergence time.
	 *
	 * @param companyId the company id
	 * @return the all div time
	 */
	List<DivergenceReasonInputMethod> getAllDivTime(String companyId);
	
	DivergenceReasonInputMethod getDivTimeInfo(String companyId, int divTimeNo);
	
	void update(DivergenceReasonInputMethod domain);
	

}
