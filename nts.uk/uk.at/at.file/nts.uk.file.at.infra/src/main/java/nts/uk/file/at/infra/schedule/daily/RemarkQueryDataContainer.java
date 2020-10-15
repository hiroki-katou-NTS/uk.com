package nts.uk.file.at.infra.schedule.daily;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarksContentChoice;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation.WorkInfoOfDailyPerformanceDetailDto;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * Container for remark input content.
 * @author HoangNDH
 *
 */
@Data
@Stateless
public class RemarkQueryDataContainer {
	/** The edit state finder. */
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	
	/** The daily performance repo. */
	@Inject
	private DailyPerformanceScreenRepo dailyPerformanceRepo;
	
	// List print remark content. It's needed to be initialize manually from UI.
	List<PrintRemarksContent> lstRemarkContent;
	
	// Edit state
	List<EditStateOfDailyPerformanceDto> editStateDto;
	
	// Daily performance work info
	List<WorkInfoOfDailyPerformanceDetailDto> dailyPerformanceList;
	
	// Employee error alerm list 
	List<EmployeeDailyPerError> errorList;
	
	
	/**
	 * Init data, not all data will be inited
	 * Data that must set manually:
	 * - List<EmployeeDailyPerError> errorList
	 * @param employeeIds
	 * @param dateReriod
	 */
	public void initData(List<String> employeeIds, DatePeriod dateReriod) {
		List<RemarksContentChoice> lstRemarkEnable = lstRemarkContent.stream().filter(x -> x.isUsedClassification()).map(x -> {
			return x.getPrintItem();
		}).collect(Collectors.toList());
		if (lstRemarkEnable.contains(RemarksContentChoice.MANUAL_INPUT) || lstRemarkEnable.contains(RemarksContentChoice.ACKNOWLEDGMENT))
			editStateDto = editStateFinder.find(employeeIds, dateReriod);
		if (lstRemarkEnable.contains(RemarksContentChoice.NOT_CALCULATED))
			dailyPerformanceList = dailyPerformanceRepo.find(employeeIds, new DateRange(dateReriod.start(), dateReriod.end()));
	}
}
