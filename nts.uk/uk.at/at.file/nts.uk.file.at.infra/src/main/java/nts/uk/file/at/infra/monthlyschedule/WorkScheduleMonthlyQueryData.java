package nts.uk.file.at.infra.monthlyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;

/**
 * @author HoangNDH
 * Instantiates a new work schedule query data.
 */
@Data
public class WorkScheduleMonthlyQueryData {
	
	/** The lst workplace config info. */
	List<WorkplaceConfigInfo> lstWorkplaceConfigInfo;
	
	/** The date period. */
	List<GeneralDate> datePeriod;
	
	/** The lst display item. */
	List<AttendanceItemsDisplay> lstDisplayItem;
	
	/** The lst attendance result import. */
	List<AttendanceResultImport> lstAttendanceResultImport;
	
	/** The lst workplace import. */
	List<WkpHistImport> lstWorkplaceImport = new ArrayList<>();
}
