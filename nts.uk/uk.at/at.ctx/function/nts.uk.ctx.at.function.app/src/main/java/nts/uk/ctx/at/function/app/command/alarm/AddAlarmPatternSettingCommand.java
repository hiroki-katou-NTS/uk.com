package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;

import lombok.Data;

@Data
public class AddAlarmPatternSettingCommand {
	
	/**
	 * アラームリストパターンコード
	 */
	private String alarmPatternCD;
	/**
	 * アラームリストパターン名称
	 */
	private String alarmPatterName;
	/**
	 * アラームリスト権限設定
	 */
	private AlarmPermissionSettingDto alarmPerSet; 
	/**
	 * チェック条件
	 */
	private List<CheckConditionDto> checkConditonList;
	
}
