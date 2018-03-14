package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;

public interface DivergenceReasonInputMethodRepository {
	
	/**
	 * get all divergence time
	 * @param companyId
	 * @return
	 */
	List<DivergenceReasonInputMethod> getAllDivTime(String companyId);

}
