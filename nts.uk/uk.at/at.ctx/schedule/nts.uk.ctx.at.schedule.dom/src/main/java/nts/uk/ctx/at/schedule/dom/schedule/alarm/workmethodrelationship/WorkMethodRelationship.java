package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
/**
 * 勤務方法の関係性
 * @author lan_lt
 *
 */
@Value
public class WorkMethodRelationship implements DomainValue{
	//前日の勤務方法
	private final WorkingMethod workingMethodOfTheDayBefore;
	
	//当日の勤務方法リスト
	private final List<WorkingMethod> workingMethodOnTheDayLst;
	
	//指定方法
	private MethodSpecifyRelationship methodSpecify;

	/**
	 * [C-1] 作成する
	 * @param workingMethodOfTheDayBefore
	 * @param workingMethodOnTheDayLst
	 * @param methodSpecify
	 * @return
	 */
	public static WorkMethodRelationship create(WorkingMethod workingMethodOfTheDayBefore, 
			List<WorkingMethod> workingMethodOnTheDayLst,
			MethodSpecifyRelationship methodSpecify) {
		
		if(workingMethodOnTheDayLst.isEmpty()) {
			throw new BusinessException("Msg_1720");
		}
		
		if(workingMethodOfTheDayBefore.getWorkTypeClassification() == WorkTypeClassification.ContinuousWork) {
			throw new BusinessException("Msg_1877");
		}
		
		return new WorkMethodRelationship(workingMethodOfTheDayBefore, workingMethodOnTheDayLst, 
				methodSpecify);
	}
	
	/**
	 * [prv-1] 許可されていない勤務方法か判定する
	 * @param require
	 * @param workInfor
	 * @return
	 */
	private boolean isNotAllowed(WorkingMethod.Require require, WorkInformation workInfor) {
		return !workingMethodOnTheDayLst.stream().anyMatch(c -> c.determineIfApplicable(require, workInfor));
	}
 
	/**
	 * [prv-2] 禁止されている勤務方法か判定する
	 * @param require
	 * @param workInfor
	 * @return
	 */
	private boolean isBan(WorkingMethod.Require require, WorkInformation workInfor) {
		return workingMethodOnTheDayLst.stream().anyMatch(c -> c.determineIfApplicable(require, workInfor));
	}
}
