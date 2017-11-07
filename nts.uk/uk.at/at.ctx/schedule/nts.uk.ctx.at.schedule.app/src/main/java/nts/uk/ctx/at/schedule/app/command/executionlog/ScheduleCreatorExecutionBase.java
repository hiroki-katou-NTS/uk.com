/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.Date;

import javax.inject.Inject;

import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.dom.adapter.ScClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.ScheduleManagementControlRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class ScheduleCreatorExecutionBase.
 */
public class ScheduleCreatorExecutionBase {
	/** The schedule execution log repository. */
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	/** The finder. */
	@Inject
	private ScheduleExecutionLogFinder finder;

	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;

	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	/** The cre set repository. */
	@Inject
	private PersonalWorkScheduleCreSetRepository creSetRepository;

	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	/** The control repository. */
	@Inject
	private ScheduleManagementControlRepository controlRepository;

	/** The content repository. */
	@Inject
	private ScheduleCreateContentRepository contentRepository;

	/** The internationalization. */
	@Inject
	private I18NResourcesForUK internationalization;

	/** The sc workplace adapter. */
	@Inject
	private ScWorkplaceAdapter scWorkplaceAdapter;

	/** The sc classification adapter. */
	@Inject
	private ScClassificationAdapter scClassificationAdapter;

	/** The classification basic work repository. */
	@Inject
	private ClassifiBasicWorkRepository classificationBasicWorkRepository;

	/** The company basic work repository. */
	@Inject
	private CompanyBasicWorkRepository companyBasicWorkRepository;

	/** The calendar class repository. */
	@Inject
	private CalendarClassRepository calendarClassRepository;

	/** The work place basic work repository. */
	@Inject
	private WorkplaceBasicWorkRepository workplaceBasicWorkRepository;

	/** The calendar work place repository. */
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;

	/** The calendar company repository. */
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;

	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

	/** The work time repository. */
	@Inject
	private WorkTimeRepository workTimeRepository;

	/** The work time set repository. */
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;

	/** The setter. */
	private TaskDataSetter setter;

	/** The command. */
	private ScheduleCreatorExecutionCommand command;

	/** The content. */
	private ScheduleCreateContent content;

	/** The to date. */
	private Date toDate;

	/** The company id. */
	private String companyId;

	/** The Constant DEFAULT_VALUE. */
	private static final Integer DEFAULT_VALUE = 0;

	/** The Constant TOTAL_RECORD. */
	private static final String TOTAL_RECORD = "TOTAL_RECORD";

	/** The Constant SUCCESS_CNT. */
	private static final String SUCCESS_CNT = "SUCCESS_CNT";

	/** The Constant FAIL_CNT. */
	private static final String FAIL_CNT = "FAIL_CNT";

	/** The Constant DEFAULT_CODE. */
	public static final String DEFAULT_CODE = "000";

	/** The Constant NEXT_DAY_MONTH. */
	public static final int NEXT_DAY_MONTH = 1;

	/** The Constant ZERO_DAY_MONTH. */
	public static final int ZERO_DAY_MONTH = 0;

	/** The Constant MUL_YEAR. */
	public static final int MUL_YEAR = 10000;

	/** The Constant MUL_MONTH. */
	public static final int MUL_MONTH = 100;

	/** The Constant SHIFT1. */
	public static final int SHIFT1 = 1;

	/** The Constant SHIFT2. */
	public static final int SHIFT2 = 2;
}
