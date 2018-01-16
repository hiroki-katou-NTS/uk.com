package nts.uk.ctx.at.schedule.dom.schedule.setting.function.control;

/**
 * 
 * @author TanLV
 *
 */
public interface FunctionControlRepository {
	/**
	 * Get Schedule Function Control
	 * @param companyId
	 * @return
	 */
	ScheFuncControl getScheFuncControl(String companyId);
	
	/**
	 * Update Schedule Function Control
	 * @param scheFuncControl
	 */
	void updateScheFuncControl(ScheFuncControl scheFuncControl);
}
