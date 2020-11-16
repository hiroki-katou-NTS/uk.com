package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Personal schedule creation dto.<br>
 * Dto 個人スケジュール作成
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class PersonalScheduleCreationDto {

	/**
	 * 作成期間
	 */
	private PersonalScheduleCreationPeriodDto period;

	/**
	 * 個人スケジュール作成区分
	 */
	private boolean perSchedule;

	/**
	 * 対象社員
	 */
	private PersonalScheduleCreationTargetDto target;

	/**
	 * No args constructor.
	 */
	private PersonalScheduleCreationDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Personal schedule creation dto
	 */
	public static PersonalScheduleCreationDto createFromDomain(PersonalScheduleCreation domain) {
		if (domain == null) {
			return null;
		}
		PersonalScheduleCreationDto dto = new PersonalScheduleCreationDto();
		dto.period = PersonalScheduleCreationPeriodDto.createFromDomain(domain.getPerSchedulePeriod());
		dto.perSchedule = domain.getPerScheduleCls().equals(NotUseAtr.USE);
		dto.target = PersonalScheduleCreationTargetDto.createFromDomain(domain.getTarget());
		return dto;
	}

}
