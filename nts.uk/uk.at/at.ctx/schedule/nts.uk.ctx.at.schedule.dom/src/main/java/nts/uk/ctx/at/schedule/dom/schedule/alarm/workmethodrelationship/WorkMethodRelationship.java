package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 勤務方法の関係性
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.勤務方法の関係性
 * @author lan_lt
 *
 */
@Value
public class WorkMethodRelationship implements DomainValue{
	
	/** 前日の勤務方法 */
	private final WorkMethod prevWorkMethod;
	
	/** 当日の勤務方法リスト*/
	private final List<WorkMethod> currentWorkMethodList;
	
	/** 指定方法 */
	private RelationshipSpecifiedMethod specifiedMethod;

	/**
	 * 作成する
	 * @param prevWorkMethod 前日の勤務方法
	 * @param currentWorkMethodList 当日の勤務方法リスト
	 * @param specifiedMethod 指定方法
	 * @return
	 */
	public static WorkMethodRelationship create(WorkMethod prevWorkMethod, 
			List<WorkMethod> currentWorkMethodList,
			RelationshipSpecifiedMethod specifiedMethod) {
		
		if(currentWorkMethodList.isEmpty()) {
			throw new BusinessException("Msg_1720");
		}
		
		if(prevWorkMethod.getWorkMethodClassification() == WorkMethodClassfication.CONTINUOSWORK) {
			throw new BusinessException("Msg_1877");
		}
		
		return new WorkMethodRelationship(prevWorkMethod, currentWorkMethodList, 
				specifiedMethod);
	}
	
//	/**
//	 * [prv-1] 許可されていない勤務方法か判定する
//	 * @param require
//	 * @param workInfo
//	 * @return
//	 */
//	private boolean isNotAllowed(WorkMethod.Require require, WorkInformation workInfo) {
//		return !currentWorkMethodList.stream().anyMatch(c -> c.determineIfApplicable(require, workInfo));
//	}
// 
//	/**
//	 * [prv-2] 禁止されている勤務方法か判定する
//	 * @param require
//	 * @param workInfo
//	 * @return
//	 */
//	private boolean isBan(WorkMethod.Require require, WorkInformation workInfo) {
//		return currentWorkMethodList.stream().anyMatch(c -> c.determineIfApplicable(require, workInfo));
//	}
	
}
