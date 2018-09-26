package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.ChildCareScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleBreakSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleTimeZoneSaveCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.PersonFeeTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogWriter;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogRepository;

/**
 * The Class ScheBasicScheduleLogCorrectionHandler.
 */
@Stateless
public class ScheBasicScheduleLogCorrectionHandler {
	
	/** The data correction log writer. */
	@Inject
	private DataCorrectionLogWriter dataCorrectionLogWriter;
	
	/** The start page log repository. */
	@Inject
	private StartPageLogRepository startPageLogRepository;
	
	/** The log basic information writer. */
	@Inject
	private LogBasicInformationWriter logBasicInformationWriter;
	
	/** The sc employee adapter. */
	@Inject
	private SCEmployeeAdapter scEmployeeAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	/**
	 * 修正ログ惱を作�する.
	 *
	 * @param companyId the company id
	 * @param backupBasicSchedule the backup basic schedule
	 * @param basicScheduleSaveCommand the basic schedule save command
	 * @param lstScheduleItem the lst schedule item
	 * @param employeeId the employee id
	 * @param targetDate the target date
	 * @param isUpdate the is update
	 */
	public void addEditDetailsLog(String companyId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, List<ScheduleItem> lstScheduleItem, String employeeId, GeneralDate targetDate, boolean isUpdate) {
		String sid = AppContexts.user().employeeId();
		
		//勤務種類コー�
		Optional<ScheduleItem> optScheduleItemWorkType = lstScheduleItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.WORK_TYPE_CODE))).findFirst();
		
		// 就業時間帯コー�
		Optional<ScheduleItem> optScheduleItemWorkTime = lstScheduleItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.WORK_TIME_CODE))).findFirst();
		
		// 開始時刻1
		List<ScheduleItem> lstScheduleItemStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
			
		// 終亙�刻1
		List<ScheduleItem> lstScheduleItemEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 休�開始時刻1~10
		List<ScheduleItem> optScheduleItemBreakStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.BREAK_START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
			
		// 休�終亙�刻1~10
		List<ScheduleItem> optScheduleItemBreakEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.BREAK_END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 育児介護開始時刻 1~2
		List<ScheduleItem> optScheduleItemChildStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.CHILD_START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 育児介護終亙�刻 1~2
		List<ScheduleItem> optScheduleItemChildEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.CHILD_END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 人件費時間
		List<ScheduleItem> lstPersonalFeeTime = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.PERSONAL_EXPENSE_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 育児介護区分
		List<ScheduleItem> lstScheduleItemBounceAtr = lstScheduleItem.stream().filter(x -> IntStream.of(ScheduleItemConstants.BOUNCE_ATR).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 直行直帰区分
		Optional<ScheduleItem> optScheduleItemConfirmAtr = lstScheduleItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.CONFIRM_STATUS))).findFirst();
		
		List<StartPageLog> lstStartPageLog = startPageLogRepository.findBySid(sid);
		StartPageLog lastLog = lstStartPageLog.get(lstStartPageLog.size() - 1);
		
		// 「データ修正記録のパラメータ」を生�する
		List<DataCorrectionLog> lstDataCorrectionLog = new ArrayList<>();
		
		LogBasicInformation logBasicInformation = lastLog.getBasicInfo();
		String operationId = IdentifierUtil.randomUniqueId();
		
		// Recreate new log basic information using new operation id
		LogBasicInformation logBasicInformationNew = new LogBasicInformation(operationId, logBasicInformation.getCompanyId(), logBasicInformation.getUserInfo(), logBasicInformation.getLoginInformation(), GeneralDateTime.now(), logBasicInformation.getAuthorityInformation(), new ScreenIdentifier("KSU007", "B", ""), logBasicInformation.getNote());
		
		EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
		
		lstDataCorrectionLog.add(createWorkTypeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemWorkType));
		lstDataCorrectionLog.add(createWorkTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemWorkTime));
		lstDataCorrectionLog.add(createConfirmAtrLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemConfirmAtr));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItemStartTime, 0));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItemEndTime, 1));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemBreakStartTime, 2));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemBreakEndTime, 3));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemChildStartTime, 4));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemChildEndTime, 5));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstPersonalFeeTime, 6));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItemBounceAtr, 7));
		lstDataCorrectionLog.addAll(createWorkScheduleTimeLog(employeeDto, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItem));
		
		
		dataCorrectionLogWriter.save(lstDataCorrectionLog);
		
		logBasicInformationWriter.save(logBasicInformationNew);
	}
	
	/**
	 * Creates the work type correction log.
	 *
	 * @param employeeDto the employee dto
	 * @param targetDate the target date
	 * @param operationId the operation id
	 * @param backupBasicSchedule the backup basic schedule
	 * @param basicScheduleSaveCommand the basic schedule save command
	 * @param optScheduleItemWorkType the opt schedule item work type
	 * @return the data correction log
	 */
	private DataCorrectionLog createWorkTypeCorrectionLog(EmployeeDto employeeDto, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, Optional<ScheduleItem> optScheduleItemWorkType) {
		ScheduleItem workTypeItem = optScheduleItemWorkType.get(); // Supposely won't null
		LoginUserContext userContext = AppContexts.user();
		
		Optional<WorkType> backupWorkType = Optional.empty();
		Optional<WorkType> saveWorkType = Optional.empty();
		if (StringUtils.isNotEmpty(backupBasicSchedule.getWorkTypeCode())) {
			backupWorkType = workTypeRepository.findByPK(userContext.companyId(), backupBasicSchedule.getWorkTypeCode());
		}
		if (StringUtils.isNotEmpty(basicScheduleSaveCommand.getWorktypeCode())) {
			saveWorkType = workTypeRepository.findByPK(userContext.companyId(), basicScheduleSaveCommand.getWorktypeCode());
		}
		
		
		DataCorrectionLog log = new DataCorrectionLog(operationId,
				new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
				TargetDataType.SCHEDULE, 
				new TargetDataKey(CalendarKeyType.DATE, targetDate, workTypeItem.scheduleItemName), 
				CorrectionAttr.EDIT, 
				ItemInfo.create(workTypeItem.scheduleItemId, workTypeItem.scheduleItemName, DataValueAttribute.STRING, 
						backupWorkType.isPresent() ? backupWorkType.get().getWorkTypeCode().v() + " " + backupWorkType.get().getName().v() : null, 
						saveWorkType.isPresent() ? saveWorkType.get().getWorkTypeCode().v() + " " + saveWorkType.get().getName().v() : null),
				workTypeItem.getDispOrder());
		
		return log;
	}
	
	/**
	 * Creates the work time correction log.
	 *
	 * @param employeeDto the employee dto
	 * @param targetDate the target date
	 * @param operationId the operation id
	 * @param backupBasicSchedule the backup basic schedule
	 * @param basicScheduleSaveCommand the basic schedule save command
	 * @param optScheduleItemWorkTime the opt schedule item work time
	 * @return the data correction log
	 */
	private DataCorrectionLog createWorkTimeCorrectionLog(EmployeeDto employeeDto, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, Optional<ScheduleItem> optScheduleItemWorkTime) {
		ScheduleItem workTypeItem = optScheduleItemWorkTime.get(); // Supposely won't null
		LoginUserContext userContext = AppContexts.user();
		
		Optional<WorkTimeSetting> backupWorkTimeSetting = Optional.empty();
		Optional<WorkTimeSetting> saveWorkTimeSetting = Optional.empty();
		if (StringUtils.isNotEmpty(backupBasicSchedule.getWorkTimeCode())) {
			backupWorkTimeSetting = workTimeRepository.findByCode(userContext.companyId(), backupBasicSchedule.getWorkTimeCode());
		}
		if (StringUtils.isNotEmpty(basicScheduleSaveCommand.getWorktimeCode())) {
			saveWorkTimeSetting = workTimeRepository.findByCode(userContext.companyId(), basicScheduleSaveCommand.getWorktimeCode());
		}
		
		DataCorrectionLog log = new DataCorrectionLog(operationId,
				new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
				TargetDataType.SCHEDULE, 
				new TargetDataKey(CalendarKeyType.DATE, targetDate, workTypeItem.scheduleItemName), 
				CorrectionAttr.EDIT, 
				ItemInfo.create(workTypeItem.scheduleItemId, workTypeItem.scheduleItemName, DataValueAttribute.STRING, 
						backupWorkTimeSetting.isPresent() ? backupWorkTimeSetting.get().getWorktimeCode().v() + " " + backupWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v() : null, 
						saveWorkTimeSetting.isPresent() ? saveWorkTimeSetting.get().getWorktimeCode().v() + " " + saveWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v() : null),
				workTypeItem.getDispOrder());
		
		return log;
	}
	
	private DataCorrectionLog createConfirmAtrLog(EmployeeDto employeeDto, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, Optional<ScheduleItem> optScheduleItemConfirmAtr) {
		ScheduleItem confirmAtrItem = optScheduleItemConfirmAtr.get(); // Supposely won't null
		LoginUserContext userContext = AppContexts.user();
		
		ConfirmedAtr updateConfirmAtr = EnumAdaptor.valueOf(basicScheduleSaveCommand.getConfirmedAtr(), ConfirmedAtr.class);
		
		DataCorrectionLog log = new DataCorrectionLog(operationId,
				new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
				TargetDataType.SCHEDULE, 
				new TargetDataKey(CalendarKeyType.DATE, targetDate, confirmAtrItem.scheduleItemName), 
				CorrectionAttr.EDIT, 
				ItemInfo.create(confirmAtrItem.scheduleItemId, confirmAtrItem.scheduleItemName, DataValueAttribute.STRING, backupBasicSchedule.getConfirmedAtr().description, updateConfirmAtr.description),
				confirmAtrItem.getDispOrder());
		
		return log;
	}
	
//	private List<DataCorrectionLog> createLog() {
//		List<DataCorrectionLog> lstLog = new ArrayList<>();
//		return lstLog;
//	}
	
	/**
	 * Creates the work schedule time log.
	 *
	 * @param employeeDto the employee dto
	 * @param targetDate the target date
	 * @param operationId the operation id
	 * @param backupBasicSchedule the backup basic schedule
	 * @param basicScheduleSaveCommand the basic schedule save command
	 * @param lstScheItem the lst sche item
	 * @return the list
	 */
	private List<DataCorrectionLog> createWorkScheduleTimeLog(EmployeeDto employeeDto, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, List<ScheduleItem> lstScheItem) {
		List<DataCorrectionLog> lstLog = new ArrayList<>();
		LoginUserContext userContext = AppContexts.user();
		
		Optional<ScheduleItem> optTotalWorkTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.TOTAL_WORK_TIME))).findFirst();
		Optional<ScheduleItem> optPreTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.PRE_TIME))).findFirst();
		Optional<ScheduleItem> optWorkingTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.WORKING_TIME))).findFirst();
		Optional<ScheduleItem> optScheWeekdayTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.WEEKDAY_TIME))).findFirst();
		Optional<ScheduleItem> optBreakTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.BREAK_TIME))).findFirst();
		Optional<ScheduleItem> optChildTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.CHILD_TIME))).findFirst();
		Optional<ScheduleItem> optCareTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.CARE_TIME))).findFirst();
		Optional<ScheduleItem> optFlexTime = lstScheItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(ScheduleItemConstants.FLEX_TIME))).findFirst();
		
		Optional<WorkScheduleTime> optWorkScheTime = basicScheduleSaveCommand.getWorkScheduleTime();
		Optional<WorkScheduleTime> optOldWorkScheTime = backupBasicSchedule.getWorkScheduleTime();
		DataCorrectionLog logTotalWorkTime, logPreTime, logWorkingTime, logBreakTime, logCareTime, logChildTime, logFlexTime, logWeekdayTime;
		if (optWorkScheTime.isPresent() && !optOldWorkScheTime.isPresent()) {
			WorkScheduleTime workScheTime = optWorkScheTime.get();
			logTotalWorkTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optTotalWorkTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optTotalWorkTime.get().scheduleItemId, optTotalWorkTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getTotalLaborTime().v()),
					optTotalWorkTime.get().getDispOrder());
			logPreTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optPreTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optPreTime.get().scheduleItemId, optPreTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getPredetermineTime().v()),
					optPreTime.get().getDispOrder());
			logWorkingTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optWorkingTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optWorkingTime.get().scheduleItemId, optWorkingTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getWorkingTime().v()),
					optWorkingTime.get().getDispOrder());
			logBreakTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optBreakTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optBreakTime.get().scheduleItemId, optBreakTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getBreakTime().v()),
					optBreakTime.get().getDispOrder());
			logCareTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optCareTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optCareTime.get().scheduleItemId, optCareTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getCareTime().v()),
					optCareTime.get().getDispOrder());
			logChildTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optChildTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optChildTime.get().scheduleItemId, optChildTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getChildTime().v()),
					optChildTime.get().getDispOrder());
			logFlexTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optFlexTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optFlexTime.get().scheduleItemId, optFlexTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getFlexTime().v()),
					optFlexTime.get().getDispOrder());
			logWeekdayTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optScheWeekdayTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optScheWeekdayTime.get().scheduleItemId, optScheWeekdayTime.get().scheduleItemName, DataValueAttribute.TIME, null, workScheTime.getWeekdayTime().v()),
					optScheWeekdayTime.get().getDispOrder());
			lstLog.add(logTotalWorkTime);
			lstLog.add(logPreTime);
			lstLog.add(logWorkingTime);
			lstLog.add(logBreakTime);
			lstLog.add(logCareTime);
			lstLog.add(logChildTime);
			lstLog.add(logFlexTime);
			lstLog.add(logWeekdayTime);
		}
		else if (!optWorkScheTime.isPresent() && optOldWorkScheTime.isPresent()) {
			WorkScheduleTime oldWorkScheTime = optWorkScheTime.get();
			logTotalWorkTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optTotalWorkTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optTotalWorkTime.get().scheduleItemId, optTotalWorkTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getTotalLaborTime().v(), null),
					optTotalWorkTime.get().getDispOrder());
			logPreTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optPreTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optPreTime.get().scheduleItemId, optPreTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getPredetermineTime().v(), null),
					optPreTime.get().getDispOrder());
			logWorkingTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optWorkingTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optWorkingTime.get().scheduleItemId, optWorkingTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getWorkingTime().v(), null),
					optWorkingTime.get().getDispOrder());
			logBreakTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optBreakTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optBreakTime.get().scheduleItemId, optBreakTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getBreakTime().v(), null),
					optBreakTime.get().getDispOrder());
			logCareTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optCareTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optCareTime.get().scheduleItemId, optCareTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getCareTime().v(), null),
					optCareTime.get().getDispOrder());
			logChildTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optChildTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optChildTime.get().scheduleItemId, optChildTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getChildTime().v(), null),
					optChildTime.get().getDispOrder());
			logFlexTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optFlexTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optFlexTime.get().scheduleItemId, optFlexTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getFlexTime().v(), null),
					optFlexTime.get().getDispOrder());
			logWeekdayTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optScheWeekdayTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optScheWeekdayTime.get().scheduleItemId, optScheWeekdayTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getWeekdayTime().v(), null),
					optScheWeekdayTime.get().getDispOrder());
			lstLog.add(logTotalWorkTime);
			lstLog.add(logPreTime);
			lstLog.add(logWorkingTime);
			lstLog.add(logBreakTime);
			lstLog.add(logCareTime);
			lstLog.add(logChildTime);
			lstLog.add(logFlexTime);
			lstLog.add(logWeekdayTime);
		}
		else if (optWorkScheTime.isPresent() && optOldWorkScheTime.isPresent()) {
			WorkScheduleTime workScheTime = optWorkScheTime.get();
			WorkScheduleTime oldWorkScheTime = optWorkScheTime.get();
			logTotalWorkTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optTotalWorkTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optTotalWorkTime.get().scheduleItemId, optTotalWorkTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getTotalLaborTime().v(), workScheTime.getTotalLaborTime().v()),
					optTotalWorkTime.get().getDispOrder());
			logPreTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optPreTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optPreTime.get().scheduleItemId, optPreTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getPredetermineTime().v(), workScheTime.getPredetermineTime().v()),
					optPreTime.get().getDispOrder());
			logWorkingTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optWorkingTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optWorkingTime.get().scheduleItemId, optWorkingTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getWorkingTime().v(), workScheTime.getWorkingTime().v()),
					optWorkingTime.get().getDispOrder());
			logBreakTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optBreakTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optBreakTime.get().scheduleItemId, optBreakTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getBreakTime().v(), workScheTime.getBreakTime().v()),
					optBreakTime.get().getDispOrder());
			logCareTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optCareTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optCareTime.get().scheduleItemId, optCareTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getCareTime().v(), workScheTime.getCareTime().v()),
					optCareTime.get().getDispOrder());
			logChildTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optChildTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optChildTime.get().scheduleItemId, optChildTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getChildTime().v(), workScheTime.getChildTime().v()),
					optChildTime.get().getDispOrder());
			logFlexTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optFlexTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optFlexTime.get().scheduleItemId, optFlexTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getFlexTime().v(), workScheTime.getFlexTime().v()),
					optFlexTime.get().getDispOrder());
			logWeekdayTime = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, optScheWeekdayTime.get().scheduleItemName), 
					CorrectionAttr.EDIT, 
					ItemInfo.create(optScheWeekdayTime.get().scheduleItemId, optScheWeekdayTime.get().scheduleItemName, DataValueAttribute.TIME, oldWorkScheTime.getWeekdayTime().v(), workScheTime.getWeekdayTime().v()),
					optScheWeekdayTime.get().getDispOrder());
			lstLog.add(logTotalWorkTime);
			lstLog.add(logPreTime);
			lstLog.add(logWorkingTime);
			lstLog.add(logBreakTime);
			lstLog.add(logCareTime);
			lstLog.add(logChildTime);
			lstLog.add(logFlexTime);
			lstLog.add(logWeekdayTime);
		}
		
		return lstLog;
	}
	
	/**
	 * Creates the time correction log.
	 *
	 * @param employeeDto the employee dto
	 * @param targetDate the target date
	 * @param operationId the operation id
	 * @param backupBasicSchedule the backup basic schedule
	 * @param basicScheduleSaveCommand the basic schedule save command
	 * @param lstScheduleItemStartTime the lst schedule item start time
	 * @param timeType the time type
	 * @return the list
	 */
	private List<DataCorrectionLog> createTimeCorrectionLog(EmployeeDto employeeDto, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, List<ScheduleItem> lstScheduleItemStartTime, int timeType) {
		LoginUserContext userContext = AppContexts.user();
		
		List<DataCorrectionLog> lstLog = new ArrayList<>();
		
		// Get backup basic schedule data
		List<WorkScheduleTimeZone> lstOldScheTimeZone = backupBasicSchedule.getWorkScheduleTimeZones();
		List<WorkScheduleBreak> lstOldBreakTime = backupBasicSchedule.getWorkScheduleBreaks();
		List<ChildCareSchedule> lstOldChildTime = backupBasicSchedule.getChildCareSchedules();
		Optional<WorkScheduleTime> optOldWorkScheduleTime = backupBasicSchedule.getWorkScheduleTime();
		List<BounceAtr> lstOldAtr = backupBasicSchedule.getWorkScheduleTimeZones().stream().map(x -> x.getBounceAtr()).collect(Collectors.toList());
		List<PersonFeeTime> lstOldPersonFeeTime = new ArrayList<>();
		
		int lstOldScheTimeZoneCount = lstOldScheTimeZone.size();
		int lstOldBreakTimeCount = lstOldBreakTime.size();
		int lstOldChildTimeCount = lstOldChildTime.size();
		int lstOldWorkScheduleTimeCount = 0;
		if (optOldWorkScheduleTime.isPresent()) {
			lstOldPersonFeeTime = optOldWorkScheduleTime.get().getPersonFeeTime();
			lstOldWorkScheduleTimeCount = lstOldPersonFeeTime.size();
		}
		int lstOldAtrCount = lstOldAtr.size();
		
		// Get update basic schedule data
		List<WorkScheduleTimeZoneSaveCommand> lstSaveScheTimeZone = basicScheduleSaveCommand.getWorkScheduleTimeZones();
		List<WorkScheduleBreakSaveCommand> lstSaveBreakTime = basicScheduleSaveCommand.getWorkScheduleBreaks();
		List<ChildCareScheduleSaveCommand> lstSaveChildTime = basicScheduleSaveCommand.getChildCareSchedules();
		Optional<WorkScheduleTime> optSaveWorkScheduleTime = basicScheduleSaveCommand.getWorkScheduleTime();
		List<BounceAtr> lstUpdateAtr = basicScheduleSaveCommand.getWorkScheduleTimeZones().stream().map(x -> x.getBounceAtr()).collect(Collectors.toList());
		List<PersonFeeTime> lstSavePersonFeeTime = new ArrayList<>();
		int lstSaveScheTimeZoneCount = lstSaveScheTimeZone.size();
		int lstSaveBreakTimeCount = lstSaveBreakTime.size();
		int lstSaveChildTimeCount = lstSaveChildTime.size();
		int lstSaveWorkScheduleTimeCount = 0;
		if (optSaveWorkScheduleTime.isPresent()) {
			lstSavePersonFeeTime = optSaveWorkScheduleTime.get().getPersonFeeTime();
			lstSaveWorkScheduleTimeCount = lstSavePersonFeeTime.size();
		}
		int lstUpdateAtrCount = lstUpdateAtr.size();
		
		for (int i = 0; i < lstScheduleItemStartTime.size(); i++) {
			ScheduleItem item = lstScheduleItemStartTime.get(i);
			ItemInfo itemInfo = null;
			
			switch(timeType) {
			case 0:
				if (i >= lstOldScheTimeZoneCount && i >= lstSaveScheTimeZoneCount) break;
				else if (i >= lstOldScheTimeZoneCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveScheTimeZone.get(i).getScheduleStartClock().v());
				else if (i >= lstSaveScheTimeZoneCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleStartClock().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleStartClock().v(), lstSaveScheTimeZone.get(i).getScheduleStartClock().v());
				break;
			case 1:
				if (i >= lstOldScheTimeZoneCount && i >= lstSaveScheTimeZoneCount) break;
				else if (i >= lstOldScheTimeZoneCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveScheTimeZone.get(i).getScheduleEndClock().v());
				else if (i >= lstSaveScheTimeZoneCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleEndClock().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleEndClock().v(), lstSaveScheTimeZone.get(i).getScheduleEndClock().v());
				break;
			case 2:
				if (i >= lstOldBreakTimeCount && i >= lstSaveBreakTimeCount) break;
				else if (i >= lstOldBreakTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveBreakTime.get(i).getScheduledStartClock().v());
				else if (i >= lstSaveBreakTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledStartClock().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledStartClock().v(), lstSaveBreakTime.get(i).getScheduledStartClock().v());
				break;
			case 3:
				if (i >= lstOldBreakTimeCount && i >= lstSaveBreakTimeCount) break;
				else if (i >= lstOldBreakTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveBreakTime.get(i).getScheduledEndClock().v());
				else if (i >= lstSaveBreakTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledEndClock().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledEndClock().v(), lstSaveBreakTime.get(i).getScheduledEndClock().v());
				break;
			case 4:
				if (i >= lstOldChildTimeCount && i >= lstSaveChildTimeCount) break;
				else if (i >= lstOldChildTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveChildTime.get(i).getChildCareScheduleStart().v());
				else if (i >= lstSaveChildTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleStart().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleStart().v(), lstSaveChildTime.get(i).getChildCareScheduleStart().v());
				break;
			case 5:
				if (i >= lstOldChildTimeCount && i >= lstSaveChildTimeCount) break;
				else if (i >= lstOldChildTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveChildTime.get(i).getChildCareScheduleEnd().v());
				else if (i >= lstSaveChildTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleEnd().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleEnd().v(), lstSaveChildTime.get(i).getChildCareScheduleEnd().v());
				break;
			case 6: // 勤務予定時間
				if (i >= lstOldWorkScheduleTimeCount && i >= lstSaveWorkScheduleTimeCount) break;
				else if (i >= lstOldWorkScheduleTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSavePersonFeeTime.get(i).getPersonFeeTime().v());
				else if (i >= lstSaveWorkScheduleTimeCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldPersonFeeTime.get(i).getPersonFeeTime().v(), null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldPersonFeeTime.get(i).getPersonFeeTime().v(), lstSavePersonFeeTime.get(i).getPersonFeeTime().v());
				break;
			case 7:
				if (i >= lstOldAtrCount && i >= lstUpdateAtrCount) break;
				else if (i >= lstOldAtrCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.STRING, null, lstUpdateAtr.get(i).description);
				else if (i >= lstUpdateAtrCount)
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.STRING, lstOldAtr.get(i).description, null);
				else
					itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.STRING, lstOldAtr.get(i).description, lstUpdateAtr.get(i).description);
				break;
			}
			
			if (itemInfo == null) break;
			
			DataCorrectionLog log = new DataCorrectionLog(operationId,
					new UserInfo(userContext.userId(), employeeDto.getEmployeeId(), employeeDto.getEmployeeName()),
					TargetDataType.SCHEDULE, 
					new TargetDataKey(CalendarKeyType.DATE, targetDate, item.scheduleItemName), 	
					CorrectionAttr.EDIT, 
					itemInfo,
					item.getDispOrder());
			lstLog.add(log);
		}
		
		return lstLog;
	}
}
