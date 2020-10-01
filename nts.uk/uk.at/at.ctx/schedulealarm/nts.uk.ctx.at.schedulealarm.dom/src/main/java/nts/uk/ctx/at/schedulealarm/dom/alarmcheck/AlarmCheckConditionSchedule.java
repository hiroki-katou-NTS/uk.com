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
	private final AlarmCheckConditionScheduleCode code;
	
	/** 条件名 */
	private final String conditionName;
	
	/** 医療オプション */
	private final boolean medicalOpt ;
	
	/** サブ条件リスト */
	private List<SubCondition> subConditions;
	
	/**
	 * メッセージを変更する
	 * @param subCode サブコード
	 * @param message メッセージ
	 */
	public void updateMessage(SubCode subCode, AlarmCheckMessage message) {
		/** 実は　filter(subCode) データはいつもある*/
		val subCond = subConditions.stream().filter(c -> c.getSubCode().v().equals(subCode.v())).findFirst().get();
		val newMsgContent = new AlarmCheckMsgContent(subCond.getMessage().getDefaultMsg(), message);
		val newSubCond = new SubCondition(subCond.getSubCode(), newMsgContent, subCond.getExplanation());
		subConditions.remove(subCond);
		subConditions.add(newSubCond);
		subConditions.sort((a, b) -> {
			return a.getSubCode().v().compareTo(b.getSubCode().v());
		});
		
	}

}
