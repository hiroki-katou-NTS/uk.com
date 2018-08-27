package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * @author sonnlb
 */
@Data
@NoArgsConstructor

public class HolidayShipmentAppDto {

	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private String workTypeCD;

	/**
	 * 基準日
	 */
	private GeneralDate appDate;

	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestionDto> subTargetDigestions;

	/**
	 * 就業時間帯
	 */
	private String workTimeCD;
	/**
	 * 名称
	 */
	private String workTimeName;

	private List<TimeZoneUseDto> TimeZoneUseDtos;

	public HolidayShipmentAppDto(String appID, String workTypeCD, GeneralDate appDate,
			List<SubTargetDigestion> subTargetDigestions, String workTimeCD) {
		this.setAppID(appID);
		this.setWorkTypeCD(workTypeCD);
		this.setWorkTimeCD(workTimeCD);
		this.setAppDate(appDate);
		this.setSubTargetDigestions(subTargetDigestions.stream().map(x -> SubTargetDigestionDto.createFromDomain(x))
				.collect(Collectors.toList()));
	}

	public void setFromAppDto(HolidayShipmentAppDto appDto) {
		this.appID = appDto.getAppID();
		this.workTimeCD = appDto.getWorkTimeCD();
		this.appDate = appDto.getAppDate();
		this.subTargetDigestions = appDto.getSubTargetDigestions();
		this.workTimeCD = appDto.getWorkTimeCD();
		this.workTimeName = appDto.workTimeName;
		this.TimeZoneUseDtos = appDto.getTimeZoneUseDtos();
	}

	public void updateFromPreTimeSet(PredetemineTimeSetting preTimeSet) {
		this.setTimeZoneUseDtos(preTimeSet.getPrescribedTimezoneSetting().getLstTimezone().stream()
				.map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList()));

	}

	public void updateFromWkTimeSet(WorkTimeSetting wkTimeSet) {
		this.setWorkTimeName(wkTimeSet.getWorkTimeDisplayName().getWorkTimeName().v());
	}
}
