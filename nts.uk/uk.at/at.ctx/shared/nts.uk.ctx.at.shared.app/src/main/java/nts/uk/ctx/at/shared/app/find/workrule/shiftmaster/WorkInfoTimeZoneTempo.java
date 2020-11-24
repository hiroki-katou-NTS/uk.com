package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDivisionDto;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkInfoTimeZoneTempo {

	private WorkTypeShiftDto workType;

	private WorkTimeSettingsDto workTime;

	private List<TimeZonesDto> listTimeZone;

	public static WorkInfoTimeZoneTempo toDto(WorkInfoAndTimeZone zone) {

		List<WorkTypeSetDto> worktypeSet = zone.getWorkType().getWorkTypeSetList().stream().map(mapper-> new WorkTypeSetDto(mapper.getWorkTypeCd().v(), mapper.getWorkAtr().value, mapper.getDigestPublicHd().value,
				mapper.getHolidayAtr().value, mapper.getCountHodiday().value, mapper.getCloseAtr().value, mapper.getSumAbsenseNo(), mapper.getSumSpHodidayNo(), mapper.getTimeLeaveWork().value,
				mapper.getAttendanceTime().value, mapper.getGenSubHodiday().value, mapper.getDayNightTimeAsk().value)).collect(Collectors.toList());

		WorkTypeShiftDto workType = new WorkTypeShiftDto(zone.getWorkType().getWorkTypeCode().v(), zone.getWorkType().getName().v(), zone.getWorkType().getAbbreviationName().v(),
				zone.getWorkType().getSymbolicName().v(), zone.getWorkType().getAttendanceHolidayAttr().value, zone.getWorkType().getMemo().v(), zone.getWorkType().getDailyWork().getWorkTypeUnit().value,
				zone.getWorkType().getDailyWork().getOneDay().value, zone.getWorkType().getDailyWork().getMorning().value,
				zone.getWorkType().getDailyWork().getAfternoon().value, zone.getWorkType().getCalculateMethod().value,
				worktypeSet,
				zone.getWorkType().getDispOrder());
		WorkTimeSettingsDto workTimeDto = null;
		if(zone.getWorkTime().isPresent()) {

		WorkTimeDivisionDto divisionDto = new WorkTimeDivisionDto(zone.getWorkTime().get().getWorkTimeDivision().getWorkTimeDailyAtr().value,
				zone.getWorkTime().get().getWorkTimeDivision().getWorkTimeMethodSet().value);
		WorkTimeDisplayNameDto displayNameDto = new WorkTimeDisplayNameDto(zone.getWorkTime().get().getWorkTimeDisplayName().getWorkTimeName().v(),
				zone.getWorkTime().get().getWorkTimeDisplayName().getWorkTimeAbName().v(), zone.getWorkTime().get().getWorkTimeDisplayName().getWorkTimeSymbol().v());
		workTimeDto = new WorkTimeSettingsDto(zone.getWorkTime().get().getCompanyId(), zone.getWorkTime().get().getWorktimeCode().v(), divisionDto,
				zone.getWorkTime().get().getAbolishAtr().value, zone.getWorkTime().get().getColorCode().v(), displayNameDto,
				zone.getWorkTime().get().getMemo().v(), zone.getWorkTime().get().getNote().v());
		}

		List<TimeZonesDto> lstTimeZone = zone.getTimeZones().stream().map(x-> new TimeZonesDto(x.getStart().v(), x.getEnd().v())).collect(Collectors.toList());

		return new WorkInfoTimeZoneTempo(workType, workTimeDto, lstTimeZone);

	}
}
