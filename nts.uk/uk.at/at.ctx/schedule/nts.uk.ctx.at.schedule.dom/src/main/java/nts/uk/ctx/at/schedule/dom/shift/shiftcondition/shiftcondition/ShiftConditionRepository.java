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

	/**
	 * get list shift condition by companyId and list conditionNo
	 * 
	 * @param companyId
	 *            companyId
	 * @param conditionNos
	 *            list conditionNo
	 * @return list shift condition
	 */
	public List<ShiftCondition> getShiftCondition(String companyId, List<Integer> conditionNos);

}
