package nts.uk.ctx.at.record.app.find.monthly.finder;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ClosureDateDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.MonthlyFinderFacade;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class MonthlyRecordWorkFinder extends MonthlyFinderFacade {

	@Inject
	private AffiliationInfoOfMonthlyFinder affi;
	
	@Inject
	private AttendanceTimeOfMonthlyFinder attendanceTime;

	@Override
	@SuppressWarnings("unchecked")
	public MonthlyRecordWorkDto find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		MonthlyRecordWorkDto dto = new MonthlyRecordWorkDto();
		AffiliationInfoOfMonthlyDto afiDto = affi.find(employeeId, yearMonth, closureId, closureDate);
		AttendanceTimeOfMonthlyDto atDto = attendanceTime.find(employeeId, yearMonth, closureId, closureDate);
		dto.setClosureDate(ClosureDateDto.from(closureDate));
		dto.setClosureId(closureId.value);
		dto.setEmployeeId(employeeId);
		dto.setYearMonth(yearMonth);
		dto.setAffiliation(afiDto);
		dto.setAttendanceTime(atDto);
		return dto;
	}
}
