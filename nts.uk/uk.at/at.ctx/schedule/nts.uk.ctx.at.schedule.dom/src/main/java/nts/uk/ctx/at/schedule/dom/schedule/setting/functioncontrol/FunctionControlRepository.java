package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import java.util.Optional;

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
	Optional<ScheFuncControl> getScheFuncControl(String companyId);
	
	/**
	 * Add Schedule Function Control
	 * @param scheFuncControl
	 */
	void addScheFuncControl(ScheFuncControl scheFuncControl);
	
	/**
	 * Update Schedule Function Control
	 * @param scheFuncControl
	 */
	void updateScheFuncControl(ScheFuncControl scheFuncControl);
}
