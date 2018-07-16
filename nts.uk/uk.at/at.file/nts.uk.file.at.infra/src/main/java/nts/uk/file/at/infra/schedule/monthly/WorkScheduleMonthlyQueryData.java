package nts.uk.file.at.infra.schedule.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyRecordValuesExport;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleQuery;

/**
 * @author HoangNDH
 * Instantiates a new work schedule query data.
 */
@Data
public class WorkScheduleMonthlyQueryData {
	
	/** The lst workplace config info. */
	List<WorkplaceConfigInfo> lstWorkplaceConfigInfo;
	
	/** The date period. */
	List<YearMonth> monthPeriod;
	
	/** The lst display item. */
	List<MonthlyAttendanceItemsDisplay> lstDisplayItem;
	
	/** The lst attendance result import. */
	List<MonthlyRecordValuesExport> lstAttendanceResultImport;
	
	/** The lst workplace import. */
	List<WkpHistImport> lstWorkplaceImport = new ArrayList<>();
	
	/** The query data. */
	MonthlyWorkScheduleQuery query;
}
