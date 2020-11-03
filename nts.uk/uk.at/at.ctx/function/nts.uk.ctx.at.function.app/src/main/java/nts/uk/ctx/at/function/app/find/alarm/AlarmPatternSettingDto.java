package nts.uk.ctx.at.function.app.find.alarm;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Alarm pattern setting dto.<br>
 * Dto アラームリストパターン設定
 */
@Data
public class AlarmPatternSettingDto implements AlarmPatternSetting.MementoSetter {

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

	/**
	 * No args constructor.
	 */
	private AlarmPatternSettingDto() {
	}

	/**
	 * Sets check condition list.
	 *
	 * @param checkConList the check condition list
	 */
	@Override
	public void setCheckConList(List<CheckCondition> checkConList) {
		this.checkConList = checkConList.stream()
										.map(CheckConditionDto::createFromDomain)
										.collect(Collectors.toList());
	}

	/**
	 * Sets company id.
	 *
	 * @param companyID the company id
	 */
	@Override
	public void setCompanyID(String companyID) {
	}

	/**
	 * Sets alarm permission setting.
	 *
	 * @param alarmPerSet the alarm permission setting
	 */
	@Override
	public void setAlarmPerSet(AlarmPermissionSetting alarmPerSet) {
		this.alarmPerSet = AlarmPermissionSettingDto.createFromDomain(alarmPerSet);
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain アラームリストパターン設定
	 * @return the dto アラームリストパターン設定
	 */
	public static AlarmPatternSettingDto createFromDomain(AlarmPatternSetting domain) {
		if (domain == null) {
			return null;
		}
		AlarmPatternSettingDto dto = new AlarmPatternSettingDto();
		domain.setMemento(dto);
		return dto;
	}

}
