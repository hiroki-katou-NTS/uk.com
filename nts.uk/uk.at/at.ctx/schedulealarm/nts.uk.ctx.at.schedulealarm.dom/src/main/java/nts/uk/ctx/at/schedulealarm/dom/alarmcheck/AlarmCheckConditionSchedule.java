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
	 * 作成する
	 * @param code コード
	 * @param conditionName 条件名
	 * @param medicalOpt 医療オプション
 	 * @param subConditions サブ条件リスト
	 * @return
	 */
	public static AlarmCheckConditionSchedule create(AlarmCheckConditionScheduleCode code, String conditionName
			, boolean medicalOpt, List<SubCondition> subConditions) {

		sortSubConditions(subConditions);

		return new AlarmCheckConditionSchedule(code, conditionName, medicalOpt, subConditions);

	}


	/**
	 * メッセージを変更する
	 * @param subCode サブコード
	 * @param message メッセージ
	 */
	public void updateMessage(SubCode subCode, AlarmCheckMessage message) {
		// ※指定されたサブコードは常に存在する
		val subCond = this.subConditions.stream()
						.filter(c -> c.getSubCode().equals(subCode))
						.findFirst().get();

		val newSubCond = new SubCondition(
						subCond.getSubCode()
					,	new AlarmCheckMsgContent(subCond.getMessage().getDefaultMsg(), message)
					,	subCond.getExplanation()
				);

		this.subConditions.remove(subCond);
		this.subConditions.add(newSubCond);
		sortSubConditions(this.subConditions);
	}

	/**
	 * サブ条件リストをソートする
	 * @param subConditions サブ条件リスト
	 */
	private static void sortSubConditions(List<SubCondition> subConditions) {
		subConditions.sort( (a, b) -> a.getSubCode().compareTo(b.getSubCode()) );
	}

}
