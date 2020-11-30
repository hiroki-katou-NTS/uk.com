package nts.uk.ctx.at.function.app.find.alarm;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmPatternSettingDto {
	/**
	 * アラームリストパターンコード
	 */
	private String alarmPatternCD;
	/**
	 * アラームリストパターン名称
	 */
	private String alarmPatternName;

	/**
	 * アラームリスト権限設定
	 */
	private AlarmPermissionSettingDto alarmPerSet;

	/**
	 * チェック条件
	 */
	private List<CheckConditionDto> checkConList;
}
