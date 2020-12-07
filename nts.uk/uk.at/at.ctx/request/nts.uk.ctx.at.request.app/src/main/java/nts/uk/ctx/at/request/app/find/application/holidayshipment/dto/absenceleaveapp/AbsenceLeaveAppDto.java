package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.WorkingHoursDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;

/**
 * @author sonnlb 振休申請Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AbsenceLeaveAppDto extends HolidayShipmentAppDto {

	/**
	 * 就業時間帯変更
	 */
	private int changeWorkHoursType;
	/**
	 * 勤務時間1
	 */
	private WorkingHoursDto wkTime1;
	/**
	 * 勤務時間2
	 */
	private WorkingHoursDto wkTime2;
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestionDto> subDigestions;

	public static AbsenceLeaveAppDto fromDomain(AbsenceLeaveApp domain, GeneralDate appDate) {

//		WorkingHoursDto WorkTime1 = WorkingHoursDto.createFromDomain(domain.getWorkTime1());
//
//		WorkingHoursDto WorkTime2 = WorkingHoursDto.createFromDomain(domain.getWorkTime2());
//
//		List<SubDigestionDto> subDigestions = domain.getSubDigestions().stream()
//				.map(x -> SubDigestionDto.createFromDomain(x)).collect(Collectors.toList());
//
//		AbsenceLeaveAppDto result = new AbsenceLeaveAppDto(domain.getAppID(), domain.getWorkTypeCD().v(),
//				domain.getChangeWorkHoursType().value, domain.getWorkTimeCD(), WorkTime1, WorkTime2,
//				domain.getSubTargetDigestions(), subDigestions, appDate);

		return null;
	}

	public AbsenceLeaveAppDto(String appID, String workTypeCD, int changeWorkHoursType, String workTimeCD,
			WorkingHoursDto workTime1, WorkingHoursDto workTime2, List<SubTargetDigestion> subTargetDigestions,
			List<SubDigestionDto> subDigestions, GeneralDate appDate) {
		super(appID, workTypeCD, appDate, subTargetDigestions, workTimeCD);
		this.setChangeWorkHoursType(changeWorkHoursType);
		this.setWkTime1(workTime1);
		this.setWkTime2(workTime2);
		this.setSubDigestions(subDigestions);
	}

}
