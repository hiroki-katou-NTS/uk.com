package nts.uk.ctx.at.function.app.command.alarm;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Add alarm pattern setting command.<br>
 * Command アラームリストパターン設定
 */
@Data
public class AddAlarmPatternSettingCommand implements AlarmPatternSetting.MementoGetter {

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
	private AlarmPermissionSettingCommand alarmPerSet;

	/**
	 * チェック条件
	 */
	private List<CheckConditionCommand> checkConditionList;

	/**
	 * Gets check condition list.
	 *
	 * @return the check condition list
	 */
	@Override
	public List<CheckCondition> getCheckConList() {
		return this.checkConditionList.stream().map(CheckConditionCommand::toDomain).collect(Collectors.toList());
	}

	/**
	 * Gets alarm permission setting.
	 *
	 * @return the alarm permission setting
	 */
	@Override
	public AlarmPermissionSetting getAlarmPerSet() {
		return AlarmPermissionSettingCommand.toDomain(this.alarmPerSet);
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyID() {
		return null;
	}

}
