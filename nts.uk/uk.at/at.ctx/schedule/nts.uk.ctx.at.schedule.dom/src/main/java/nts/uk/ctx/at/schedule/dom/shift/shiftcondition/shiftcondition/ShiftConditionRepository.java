package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import java.util.List;

/**
 * 
 * @author Trung Tran
 *
 */
public interface ShiftConditionRepository {
	/**
	 * get shift condition
	 */
	public List<ShiftCondition> getListShiftCondition(String companyId);

	
}
