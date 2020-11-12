package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationTarget;

/**
 * The class Personal schedule creation target dto.<br>
 * Dto 個人スケジュール作成対象社員
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class PersonalScheduleCreationTargetDto {

	/**
	 * 作成対象
	 */
	private int creationTarget;

	/**
	 * 作成対象詳細設定
	 */
	private TargetSettingDto targetSetting;

	/**
	 * No args constructor.
	 */
	private PersonalScheduleCreationTargetDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Personal schedule creation target dto
	 */
	public static PersonalScheduleCreationTargetDto createFromDomain(PersonalScheduleCreationTarget domain) {
		if (domain == null) {
			return null;
		}
		PersonalScheduleCreationTargetDto dto = new PersonalScheduleCreationTargetDto();
		dto.creationTarget = domain.getCreationTarget().value;
		dto.targetSetting = TargetSettingDto.createFromDomain(domain.getTargetSetting());
		return dto;
	}

}
