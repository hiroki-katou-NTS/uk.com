package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.WorkingHoursDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentWorkingHour;

/**
 * @author sonnlb 勤務時間Dto
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RecruitmentWorkingHourDto extends WorkingHoursDto {

	/**
	 * 直行
	 */
	private Integer startUseAtr;

	/**
	 * 直帰
	 */
	private Integer endUseAtr;

	public RecruitmentWorkingHourDto(Integer startTime, Integer startUseAtr, Integer endTime, Integer endUseAtr) {
		super(startTime, endTime);
		this.setStartUseAtr(startUseAtr);
		this.setEndUseAtr(endUseAtr);
	}

	public static RecruitmentWorkingHourDto createFromDomain(RecruitmentWorkingHour domain) {

		return new RecruitmentWorkingHourDto(domain.getStartTime().v(), domain.getStartUseAtr().value,
				domain.getEndTime().v(), domain.getEndUseAtr().value);
	}

}
