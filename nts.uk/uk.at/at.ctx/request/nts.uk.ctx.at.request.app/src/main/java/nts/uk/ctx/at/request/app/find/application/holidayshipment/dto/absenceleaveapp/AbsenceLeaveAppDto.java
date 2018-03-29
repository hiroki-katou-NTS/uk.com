package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;

/**
 * @author sonnlb 振休申請Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AbsenceLeaveAppDto {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private String workTypeCD;
	/**
	 * 就業時間帯変更
	 */
	private int changeWorkHoursType;
	/**
	 * 就業時間帯
	 */
	private String workTimeCD;
	/**
	 * 勤務時間1
	 */
	private AbsenceLeaveWorkingHourDto wkTime1;
	/**
	 * 勤務時間2
	 */
	private AbsenceLeaveWorkingHourDto wkTime2;
	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestionDto> subTargetDigestions;
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestionDto> subDigestions;

	/**
	 * 基準日
	 */
	private GeneralDate appDate;

	public static AbsenceLeaveAppDto fromDomain(AbsenceLeaveApp domain, GeneralDate appDate) {
		AbsenceLeaveWorkingHourDto WorkTime1 = new AbsenceLeaveWorkingHourDto(domain.getWorkTime1().getStartTime().v(),
				domain.getWorkTime1().getEndTime().v());
		AbsenceLeaveWorkingHourDto WorkTime2 = new AbsenceLeaveWorkingHourDto(domain.getWorkTime2().getStartTime().v(),
				domain.getWorkTime2().getEndTime().v());
		List<SubTargetDigestionDto> subTargetDigestions = new ArrayList<SubTargetDigestionDto>();
		domain.getSubTargetDigestions().forEach(x -> {
			subTargetDigestions.add(new SubTargetDigestionDto(x.getAppID(), x.getHoursUsed(), x.getLeaveMngDataID(),
					x.getBreakOutDate(), x.getRestState().value, x.getUnknownDate()));
		});

		List<SubDigestionDto> subDigestions = new ArrayList<SubDigestionDto>();
		domain.getSubDigestions().forEach(x -> {
			subDigestions.add(new SubDigestionDto(x.getAbsenceLeaveAppID(), x.getDaysUsedNo().value,
					x.getPayoutMngDataID(), x.getPickUpState().value, x.getOccurrenceDate(), x.getUnknownDate()));
		});
		AbsenceLeaveAppDto result = new AbsenceLeaveAppDto(domain.getAppID(), domain.getWorkTypeCD(),
				domain.getChangeWorkHoursType().value, domain.getWorkTimeCD().v(), WorkTime1, WorkTime2,
				subTargetDigestions, subDigestions, appDate);

		return result;
	}

}
