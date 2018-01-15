package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import java.util.List;

/**
 * 
 * @author Trung Tran
 *
 */
public interface ShiftConditionCategoryRepository {
	/**
	 * get shift condition category
	 */
	public List<ShiftConditionCategory> getListShifConditionCategory(String companyId);
}
