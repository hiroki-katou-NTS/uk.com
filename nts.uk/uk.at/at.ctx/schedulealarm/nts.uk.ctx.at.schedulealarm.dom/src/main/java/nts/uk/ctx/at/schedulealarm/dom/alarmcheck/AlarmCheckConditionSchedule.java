package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 勤務予定のアラームチェック条件
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.勤務予定のアラームチェック条件
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class AlarmCheckConditionSchedule implements DomainAggregate{
	/** コード  */
	private final AlarmCheckConditionCode code;
	
	/** 条件名 */
	private final String conditionName;
	
	/** 医療オプション */
	private final boolean medicalOpt ;
	
	/** サブ条件リスト */
	private List<SubCondition> subConditionLst;
	
	/**
	 * メッセージを変更する
	 * @param subCode サブコード
	 * @param message メッセージ
	 */
	public void updateMessage(SubCode subCode, AlarmCheckMessage message) {
		val subCondition = subConditionLst.stream().filter(c -> c.getSubCode().v().equals(subCode.v())).findFirst().get();
		val newNessageContent = new AlarmCheckMsgContent(subCondition.getMessage().getDefaultMsg(), message);
		val newSubCondition = new SubCondition(subCondition.getSubCode(), newNessageContent, subCondition.getExplanation());
		subConditionLst.remove(subCondition);
		subConditionLst.add(newSubCondition);
		subConditionLst.sort((a, b) -> {
			return a.getSubCode().v().compareTo(b.getSubCode().v());
		});
		
	}

}
