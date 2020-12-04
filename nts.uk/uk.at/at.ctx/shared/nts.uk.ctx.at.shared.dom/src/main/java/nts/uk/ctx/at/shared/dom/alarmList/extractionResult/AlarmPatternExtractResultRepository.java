package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import java.util.Optional;

public interface AlarmPatternExtractResultRepository {
	/**
	 * アラームリストパターンの抽出結果を取得
	 * @param runCode　自動実行
	 * @param patternCode　パターンコード
	 * @param chkAlarmCode　
	 * @param lstCategoryCond
	 * @return
	 */
	Optional<AlarmPatternExtractResult> optAlarmPattern(String cid,	String runCode,	String patternCode);
	/**
	 * アラームリストパターンの抽出結果を追加
	 * @param alarmPattern
	 */
	void addAlarmPattern(AlarmPatternExtractResult alarmPattern);
	/**
	 * アラームリストパターンの抽出結果を削除
	 * @param alarmPattern
	 */
	void deleteAlarmPattern(AlarmPatternExtractResult alarmPattern);
}
