package nts.uk.ctx.at.function.app.command.alarm;

import lombok.Data;

@Data
public class DeleteAlarmPatternSettingCommand {
	/**
	 * アラームリストパターンコード
	 */
	private String alarmPatternCD;
}
