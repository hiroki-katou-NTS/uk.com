package nts.uk.ctx.at.function.app.find.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPermissionSettingDto;
import nts.uk.ctx.at.function.app.find.alarm.CheckConditionDto;

import java.util.List;

@Data
@AllArgsConstructor
public class WkpAlarmPatternSettingDto {
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
