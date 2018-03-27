package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.SubTargetDigestionDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;

/**
 * @author sonnlb
 * 振出申請Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentAppDto {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private String workTypeCD;
	/**
	 * 勤務場所コード
	 */
	private String workLocationCD;
	/**
	 * 就業時間帯
	 */
	private String workTimeCD;

	/**
	 * 勤務時間1
	 */
	private RecruitmentWorkingHourDto workTime1;
	/**
	 * 勤務時間2
	 */
	private RecruitmentWorkingHourDto workTime2;

	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestionDto> subTargetDigestions;

	/**
	 * 基準日
	 */
	private GeneralDate appDate;

	public static RecruitmentAppDto fromDomain(RecruitmentApp domain, GeneralDate appDate) {
		RecruitmentWorkingHourDto workTime1 = new RecruitmentWorkingHourDto(domain.getWorkTime1().getStartTime().v(),
				domain.getWorkTime1().getStartUseAtr().value, domain.getWorkTime1().getEndTime().v(),
				domain.getWorkTime1().getEndUseAtr().value);
		RecruitmentWorkingHourDto workTime2 = new RecruitmentWorkingHourDto(domain.getWorkTime2().getStartTime().v(),
				domain.getWorkTime2().getStartUseAtr().value, domain.getWorkTime2().getEndTime().v(),
				domain.getWorkTime2().getEndUseAtr().value);
		List<SubTargetDigestionDto> subTargetDigestions = new ArrayList<SubTargetDigestionDto>();
		domain.getSubTargetDigestions().forEach(x -> {
			subTargetDigestions.add(new SubTargetDigestionDto(x.getRecAppID(), x.getAbsenceLeaveAppID(),
					x.getHoursUsed(), x.getLeaveMngDataID(), x.getBreakOutDate(), x.getRestState().value));
		});
		return new RecruitmentAppDto(domain.getAppID(), domain.getWorkTypeCD(), domain.getWorkLocationCD().v(),
				domain.getWorkTimeCD().v(), workTime1, workTime2, subTargetDigestions, appDate);

	}
}
