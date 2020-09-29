package nts.uk.ctx.at.record.pubimp.workrecord.alarmlist.fourweekfourdayoff;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.alarmlist.fourweekfourdayoff.AlarmExtractionValue4W4D;
import nts.uk.ctx.at.record.dom.workrecord.alarmlist.fourweekfourdayoff.InfoCheckNotRegisterDto;
import nts.uk.ctx.at.record.dom.workrecord.alarmlist.fourweekfourdayoff.W4D4CheckService;
import nts.uk.ctx.at.record.pub.workinformation.InfoCheckNotRegisterPubExport;
import nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff.AlarmExtractionValue4W4DExport;
import nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff.W4D4CheckPub;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Stateless
public class W4D4CheckPubImpl implements W4D4CheckPub {
	@Inject
	private W4D4CheckService checkService;

	@Override
	public Optional<AlarmExtractionValue4W4DExport> checkHoliday(String workplaceID, String employeeID,
			DatePeriod period,List<String> listHolidayWorkTypeCode,List<InfoCheckNotRegisterPubExport> listWorkInfoOfDailyPerformance) {
		Optional<AlarmExtractionValue4W4D> optAlarmExtract = checkService.checkHoliday(workplaceID, employeeID, period,listHolidayWorkTypeCode,
				listWorkInfoOfDailyPerformance.stream().map(c->convertToDto(c)).collect(Collectors.toList()));
		
		if (optAlarmExtract.isPresent()) {
			AlarmExtractionValue4W4D alarmExtract = optAlarmExtract.get();

			return Optional.of(new AlarmExtractionValue4W4DExport(workplaceID, employeeID, alarmExtract.getDatePeriod(),
					alarmExtract.getClassification(), alarmExtract.getAlarmItem(), alarmExtract.getAlarmValueMessage(),
					alarmExtract.getComment(),alarmExtract.getCheckedValue()));
		} else {

			return Optional.empty();
		}

	}
	
	@Override
	public 	List<AlarmExtractionValue4W4DExport> checkHolidayWithSchedule(String workplaceID, String employeeID, DatePeriod period,
			List<WorkType> legalHolidayWorkTypeCodes, List<WorkType> illegalHolidayWorkTypeCodes,
			List<InfoCheckNotRegisterPubExport> listWorkInfoOfDailyPerformance,
			List<InfoCheckNotRegisterPubExport> basicSchedules) {
		List<AlarmExtractionValue4W4D> alarmExtracts = checkService.checkHolidayWithSchedule(workplaceID, employeeID, period, 
				legalHolidayWorkTypeCodes,
				illegalHolidayWorkTypeCodes,
				listWorkInfoOfDailyPerformance.stream().map(c->convertToDto(c)).collect(Collectors.toList()),
				basicSchedules.stream().map(b -> convertToDto(b)).collect(Collectors.toList()));
		
		if (!alarmExtracts.isEmpty()) {
			return alarmExtracts.stream().map(a -> new AlarmExtractionValue4W4DExport(workplaceID, employeeID, a.getDatePeriod(),
					a.getClassification(), a.getAlarmItem(), a.getAlarmValueMessage(),
					a.getComment(), a.getCheckedValue()))
			.collect(Collectors.toList());
		}
		
		return Collections.EMPTY_LIST;
	}
	
	private InfoCheckNotRegisterDto convertToDto(InfoCheckNotRegisterPubExport export) {
		return new InfoCheckNotRegisterDto(
				export.getEmployeeId(),
				export.getWorkTimeCode(),
				export.getWorkTypeCode()
				);
	}

}
