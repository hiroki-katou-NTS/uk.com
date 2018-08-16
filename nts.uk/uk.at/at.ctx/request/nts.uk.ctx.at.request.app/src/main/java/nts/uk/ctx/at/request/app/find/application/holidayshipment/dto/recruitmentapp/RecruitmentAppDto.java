package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentAppDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;

/**
 * @author sonnlb 振出申請Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecruitmentAppDto extends HolidayShipmentAppDto {

	/**
	 * 勤務時間1
	 */
	private RecruitmentWorkingHourDto wkTime1;
	/**
	 * 勤務時間2
	 */
	private RecruitmentWorkingHourDto wkTime2;

	public static RecruitmentAppDto fromDomain(RecruitmentApp domain, GeneralDate appDate) {
		RecruitmentWorkingHourDto workTime1 = RecruitmentWorkingHourDto.createFromDomain(domain.getWorkTime1());

		RecruitmentWorkingHourDto workTime2 = RecruitmentWorkingHourDto.createFromDomain(domain.getWorkTime2());

		return new RecruitmentAppDto(domain.getAppID(), domain.getWorkTypeCD().v(), domain.getWorkTimeCD().v(), workTime1,
				workTime2, domain.getSubTargetDigestions(), appDate);

	}
	
	

	public RecruitmentAppDto(String appID, String workTypeCD, String workTimeCD, RecruitmentWorkingHourDto workTime1,
			RecruitmentWorkingHourDto workTime2, List<SubTargetDigestion> subTargetDigestions, GeneralDate appDate) {

		super(appID, workTypeCD, appDate, subTargetDigestions, workTimeCD);
		this.setWkTime1(workTime1);
		this.setWkTime2(workTime2);
	}

}
