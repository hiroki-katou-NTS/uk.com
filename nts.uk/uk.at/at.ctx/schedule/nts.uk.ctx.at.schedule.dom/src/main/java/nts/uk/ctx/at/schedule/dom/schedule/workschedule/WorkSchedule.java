package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskScheduleDetail;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * ???????????? root
 * UKDesign.?????????????????????."NittsuSystem.UniversalK".??????.contexts.????????????.????????????.????????????
 * @author HieuLT
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkSchedule implements DomainAggregate {

	/** ??????ID(employeeID) */
	private final String employeeID;

	/** ??????ID(?????????(YMD) */
	private final GeneralDate ymd;

	/** ???????????? */
	private ConfirmedATR confirmedATR;

	/** ???????????? */
	private WorkInfoOfDailyAttendance workInfo;

	/** ???????????? **/
	private AffiliationInforOfDailyAttd affInfo;
	
	/** ???????????????**/
	@Getter
	private BreakTimeOfDailyAttd lstBreakTime;
	
	/** ???????????? **/
	private List<EditStateOfDailyAttd> lstEditState;
	
	/** ???????????? **/
	private TaskSchedule taskSchedule;
	
	/** ???????????? **/
	private SupportSchedule supportSchedule;

	/** ????????? */
	private Optional<TimeLeavingOfDailyAttd> optTimeLeaving;

	/** ???????????? */
	private Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime;

	/** ??????????????? */
	private Optional<ShortTimeOfDailyAttd> optSortTimeWork;

	/** ??????????????? */
	private Optional<OutingTimeOfDailyAttd> outingTime;

	/**
	 * ??????
	 * @param require
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param date ?????????
	 * @param workinformation ????????????
	 * @return
	 */
	public static WorkSchedule create(
			Require require,
			String companyId,
			String employeeId,
			GeneralDate date,
			WorkInformation workInformation
			){
		
		if (! workInformation.checkNormalCondition(require, companyId) ) {
			throw new BusinessException("Msg_2119");
		}
		
		Optional<TimeLeavingOfDailyAttd> optTimeLeaving = Optional.empty();
		if ( workInformation.isAttendanceRate(require, companyId) ) {
			optTimeLeaving = Optional.of(
					TimeLeavingOfDailyAttd.createByPredetermineZone(require, companyId, workInformation) );
		}
		
		val isBackStraight = workInformation.isBackStraight(require, companyId) ? NotUseAttribute.Use : NotUseAttribute.Not_use;
		val isGoStraight = workInformation.isGoStraight(require, companyId) ? NotUseAttribute.Use : NotUseAttribute.Not_use;
			
		return new WorkSchedule(
				employeeId, 
				date, 
				ConfirmedATR.UNSETTLED, 
				WorkInfoOfDailyAttendance.create(
						require,
						companyId,
						workInformation, 
						CalculationState.No_Calculated, 
						isBackStraight, 
						isGoStraight, 
						DayOfWeek.convertFromCommonClass(date.dayOfWeekEnum())), 
				AffiliationInforOfDailyAttd.create(require, employeeId, date), 
				new BreakTimeOfDailyAttd(),
				new ArrayList<>(), 
				TaskSchedule.createWithEmptyList(),
				SupportSchedule.createWithEmptyList(),
				optTimeLeaving, 
				Optional.empty(), 
				Optional.empty(),
				Optional.empty() );
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @param employeeId ??????ID
	 * @param date ?????????
	 * @param workInformation ????????????
	 * @return
	 */
	public static WorkSchedule createByHandCorrectionWithWorkInformation(
			Require require,
			String companyId,
			String employeeId,
			GeneralDate date,
			WorkInformation workInformation
			) {
		WorkSchedule workSchedule = WorkSchedule.create(require, companyId, employeeId, date, workInformation);
		
		List<EditStateOfDailyAttd> editStateOfDailyAttdList = 
				Arrays.asList(
					WS_AttendanceItem.WorkType,
					WS_AttendanceItem.WorkTime,
					WS_AttendanceItem.StartTime1,
					WS_AttendanceItem.EndTime1,
					WS_AttendanceItem.StartTime2,
					WS_AttendanceItem.EndTime2)
				.stream()
				.map( item -> EditStateOfDailyAttd.createByHandCorrection(require, item.ID, employeeId))
				.collect(Collectors.toList());
		
		workSchedule.setLstEditState(editStateOfDailyAttdList);
		
		return workSchedule;
	}
	
	/**
	 * ????????????????????????????????????????????????
	 * ????????????ID???????????????????????????????????????
	 * @param require
	 * @param updateInfoMap ??????????????????Map
	 */
	public <T> void changeAttendanceItemValueByHandCorrection (
			Require require,
			Map<Integer, T > updateInfoMap) {
		
		updateInfoMap.forEach( (key, value) -> this.updateValueByHandCorrection(require, key, value));
	}
	
	/**
	 * ????????????
	 * @param updateAttendanceItemID ????????????ID
	 * @param value ???
	 * @return
	 */
	private <T> T updateValue(int updateAttendanceItemID, T value) {
		
		val updateAttendanceItem = WS_AttendanceItem.valueOf(updateAttendanceItemID);
		
		switch (updateAttendanceItem) {
			case StartTime1 :
				this.optTimeLeaving.get()
					.getAttendanceLeavingWork( 1 ).get()
					.getAttendanceStamp().get()
					.getStamp().get()
					.getTimeDay()
					.setTimeWithDay( Optional.of( (TimeWithDayAttr) value) );
				break;

			case EndTime1:
				this.optTimeLeaving.get()
					.getAttendanceLeavingWork( 1 ).get()
					.getLeaveStamp().get()
					.getStamp().get()
					.getTimeDay()
					.setTimeWithDay( Optional.of( (TimeWithDayAttr) value) );
				break;
			case StartTime2:
				this.optTimeLeaving.get()
					.getAttendanceLeavingWork( 2 ).get()
					.getAttendanceStamp().get()
					.getStamp().get()
					.getTimeDay()
					.setTimeWithDay( Optional.of( (TimeWithDayAttr) value) );
				break;

			case EndTime2:
				this.optTimeLeaving.get()
					.getAttendanceLeavingWork( 2 ).get()
					.getLeaveStamp().get()
					.getStamp().get()
					.getTimeDay()
					.setTimeWithDay( Optional.of( (TimeWithDayAttr) value) );
				break;
			case GoStraight:
				this.workInfo.setGoStraightAtr( (NotUseAttribute) value );
				break;
			case BackStraight:
				this.workInfo.setBackStraightAtr( (NotUseAttribute) value );
				break;
			default:
				break;
		}
		
		return value;
	}
	
	/**
	 * ????????????ID??????????????????
	 * @param updateAttendanceItemID ????????????ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getAttendanceItemValue(int updateAttendanceItemID) {
		
		val updateAttendanceItem = WS_AttendanceItem.valueOf(updateAttendanceItemID);
		
		switch (updateAttendanceItem) {
			case StartTime1:
				return (T) this.optTimeLeaving.get()
						.getAttendanceLeavingWork(1).get()
						.getAttendanceStamp().get()
						.getStamp().get()
						.getTimeDay().getTimeWithDay().get();
			case EndTime1:
				return (T) this.optTimeLeaving.get()
						.getAttendanceLeavingWork(1).get()
						.getLeaveStamp().get()
						.getStamp().get()
						.getTimeDay().getTimeWithDay().get();
			case StartTime2:
				return (T) this.optTimeLeaving.get()
						.getAttendanceLeavingWork(2).get()
						.getAttendanceStamp().get()
						.getStamp().get()
						.getTimeDay().getTimeWithDay().get();
			case EndTime2:
				return (T) this.optTimeLeaving.get()
						.getAttendanceLeavingWork(2).get()
						.getLeaveStamp().get()
						.getStamp().get()
						.getTimeDay().getTimeWithDay().get();
			case GoStraight:
				return (T) this.workInfo.getGoStraightAtr();
			case BackStraight:
				return (T) this.workInfo.getBackStraightAtr();
			default:
				return null;
		}

	}
	/**
	 * ????????????????????????
	 * @param require
	 * @param updateAttendanceItemID 
	 * @param value Optional<T> ???
	 * @return
	 * 
	 * note: ???????????????????????????????????????????????????????????? this attendance item doesn't accept work-type or work-time
	 */
	private <T> T updateValueByHandCorrection(Require require, int updateAttendanceItemID, T value) {
		
		T existValue = this.getAttendanceItemValue(updateAttendanceItemID);
		
		if ( existValue.equals(value) ) {
			return existValue;
		}
		
		lstEditState.removeIf( editState -> editState.getAttendanceItemId() == updateAttendanceItemID);
		lstEditState.add(EditStateOfDailyAttd.createByHandCorrection(require, updateAttendanceItemID, employeeID));
		
		return this.updateValue(updateAttendanceItemID, value);
		
	} 
	
	/**
	 * ????????????
	 */
	public void confirm() {
		this.confirmedATR = ConfirmedATR.CONFIRMED;
	}
	
	/**
	 * ?????????????????????
	 */
	public void removeConfirm() {
		this.confirmedATR = ConfirmedATR.UNSETTLED;
	}
	
	/**
	 * ????????????????????????
	 */
	public void removeHandCorrections() {
		this.lstEditState.removeIf( editState -> editState.isHandCorrect() );
	}

	/**
	 * ???????????????????????????
	 * @return
	 */
	public Map<TimezoneToUseHourlyHoliday, TimeVacation> getTimeVacation() {
		return GettingTimeVacactionService.get(this.optTimeLeaving, this.optAttendanceTime,	this.outingTime);
	}

	/**
	 * ?????????????????????????????????
	 * @param require
	 * @param newBreakTimeList ??????????????????
	 */
	public void handCorrectBreakTimeList(Require require, List<TimeSpanForCalc> newBreakTimeList) {
		
		List<TimeSpanForCalc> sortedBreakTimeList = newBreakTimeList.stream()
				.sorted(Comparator.comparingInt(TimeSpanForCalc::start))
				.collect(Collectors.toList());;
				
		List<BreakTimeSheet> newBreakTimeSheets = 
			IntStream.range(0, sortedBreakTimeList.size()).boxed()
				.map( index ->
						new BreakTimeSheet(
							new BreakFrameNo(index + 1),
							sortedBreakTimeList.get(index).getStart(),
							sortedBreakTimeList.get(index).getEnd() ))
				.collect(Collectors.toList());
		
		// update value of BreakTime
		this.lstBreakTime = new BreakTimeOfDailyAttd(newBreakTimeSheets);
		
		// update EditState of BreakTime(1...size)
		this.lstEditState.removeIf( editState -> WS_AttendanceItem.isBreakTime( editState.getAttendanceItemId() ) );
		
		List<WS_AttendanceItem> updatedAttendanceItemList;
		if ( newBreakTimeList.isEmpty() ) {
			updatedAttendanceItemList = new ArrayList<>(Arrays.asList( 
					WS_AttendanceItem.StartBreakTime1, 
					WS_AttendanceItem.EndBreakTime1) );
		} else {
			updatedAttendanceItemList = WS_AttendanceItem.getBreakTimeItemWithSize( newBreakTimeList.size() );
		}
		updatedAttendanceItemList.forEach( item -> this.lstEditState.add(
				EditStateOfDailyAttd.createByHandCorrection(require, item.ID, this.employeeID)));
	}
	
	/**
	 * ????????????Work???????????????
	 * @return
	 */
	public IntegrationOfDaily convertToIntegrationOfDaily() {
		return new IntegrationOfDaily(
				  this.employeeID
				, this.ymd
				, this.workInfo
				, CalAttrOfDailyAttd.createAllCalculate()
				, this.affInfo
				, Optional.empty()
				, Collections.emptyList()
				, this.outingTime
				, this.lstBreakTime
				, this.optAttendanceTime
				, this.optTimeLeaving
				, this.optSortTimeWork
				, Optional.empty()
				, Optional.empty()
				, Optional.empty()
				, this.lstEditState
				, Optional.empty()
				, Collections.emptyList()
				, Collections.emptyList()
				, Collections.emptyList()
				, Optional.empty());
	}
	
	/**
	 * ??????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @param newtaskSchedule ????????????
	 */
	public void updateTaskSchedule(Require require, String companyId, TaskSchedule newtaskSchedule ) {
		
		this.checkWhetherTaskScheduleIsCorrect(require, companyId, newtaskSchedule);
		
		this.taskSchedule = newtaskSchedule;
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @param taskCode ???????????????
	 */
	public void createTaskScheduleForWholeDay(Require require, String companyId, TaskCode taskCode) {
		
		List<TimeSpanForCalc> workingTimeSpanList = this.getWorkingTimeSpan(require, companyId);
		if ( workingTimeSpanList.isEmpty() ) {
			throw new BusinessException("Msg_2103");
		}
		
		List<TaskScheduleDetail> taskScheduleDetails = workingTimeSpanList.stream()
				.map( timeSpan -> new TaskScheduleDetail(taskCode, timeSpan))
				.collect(Collectors.toList());
		
		this.taskSchedule = TaskSchedule.create( taskScheduleDetails );
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @param targetTimeSpan ???????????????
	 * @param taskCode ???????????????
	 */
	public void addTaskScheduleWithTimeSpan(Require require, String companyId, TimeSpanForCalc targetTimeSpan, TaskCode taskCode) {
		
		List<TimeSpanForCalc> workingTimeSpanList = this.getWorkingTimeSpan(require, companyId);
		if ( workingTimeSpanList.isEmpty() ) {
			throw new BusinessException("Msg_2103");
		}
		
		List<TaskScheduleDetail> addingDetails = workingTimeSpanList.stream()
				.map( workingTimeSpan -> workingTimeSpan.getDuplicatedWith(targetTimeSpan))
				.flatMap(OptionalUtil::stream)
				.map( timeSpan -> new TaskScheduleDetail(taskCode, timeSpan) )
				.collect( Collectors.toList() );
		
		addingDetails.forEach( detail -> {
			this.taskSchedule = this.taskSchedule.addTaskScheduleDetail( detail );
		});
	}
	
	/**
	 * ???????????????????????????
	 * @param require
	 * @param supportTickets ???????????????????????????
	 */
	public void createSupportSchedule(Require require, List<SupportTicket> supportTickets) {
		
		supportTickets.stream().forEach( ticket -> this.checkWhetherCanUpdateSupportSchedule(ticket, false) );
		
		this.supportSchedule = SupportSchedule.createFromSupportTicketList(require, supportTickets);
		
		this.checkConsistencyOfSupportSchedule(require);
	}
	
	/**
	 * ???????????????????????????
	 * @param require
	 * @param ticket ??????????????????
	 */
	public void addSupportSchedule(Require require, SupportTicket ticket) {
		
		this.checkWhetherCanUpdateSupportSchedule(ticket, false);
		
		this.supportSchedule = this.supportSchedule.add(require, ticket);
		
		this.checkConsistencyOfSupportSchedule(require);
	}
	
	/**
	 * ???????????????????????????
	 * @param require
	 * @param beforeModify ?????????
	 * @param afterModify ?????????
	 */
	public void modifySupportSchedule(Require require, SupportTicket beforeModify, SupportTicket afterModify) {
		
		this.checkWhetherCanUpdateSupportSchedule(beforeModify, false);
		this.checkWhetherCanUpdateSupportSchedule(afterModify, false);
		
		this.supportSchedule = this.supportSchedule.update(require, beforeModify, afterModify);
		
		this.checkConsistencyOfSupportSchedule(require);
	}
	
	/**
	 * ???????????????????????????
	 * @param ticket ??????????????????
	 */
	public void removeSupportSchedule(SupportTicket ticket) {
		
		this.checkWhetherCanUpdateSupportSchedule(ticket, true);
		
		switch (ticket.getSupportType()) {
		case ALLDAY:
			this.taskSchedule = TaskSchedule.createWithEmptyList();
			break;
		case TIMEZONE:
			this.taskSchedule = this.taskSchedule.removeTaskScheduleDetailIn(ticket.getTimespan().get());
			break;
		default:
			throw new RuntimeException("support type is invalid!");
		}
		
		this.supportSchedule = this.supportSchedule.remove(ticket);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param require
	 * @return
	 */
	public SupportInfoOfEmployee getSupportInfoOfEmployee() {
		
		if ( ! this.supportSchedule.havePlanToSupport() ) {
			
			return SupportInfoOfEmployee.createWithoutSupport(
					new EmployeeId(this.employeeID), 
					this.ymd, 
					this.affInfo.getAffiliationOrg());
		}
		
		if ( this.supportSchedule.getSupportType().get() == SupportType.ALLDAY ) {
			return SupportInfoOfEmployee.createWithAllDaySupport(
					new EmployeeId(this.employeeID), 
					this.ymd,
					this.affInfo.getAffiliationOrg(), 
					this.supportSchedule.getDetails().get(0).getSupportDestination());
		} else {
			val recipientList = this.supportSchedule.getDetails().stream()
					.map(SupportScheduleDetail::getSupportDestination)
					.collect(Collectors.toList());
			
			return SupportInfoOfEmployee.createWithTimezoneSupport(
					new EmployeeId(this.employeeID),
					this.ymd,
					this.affInfo.getAffiliationOrg(),
					recipientList);
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @return
	 */
	private List<TimeSpanForCalc> getWorkingTimeSpan(Require require, String companyId) {
		
		if( !this.workInfo.isAttendanceRate(require, companyId) ) {
			return new ArrayList<>();
		}
		
		val timeLeavingSpanList = this.optTimeLeaving.get().getTimeOfTimeLeavingAtt();
		return timeLeavingSpanList.stream()
			.map( timeLeavingSpan -> this.getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan(require, timeLeavingSpan) )
			.flatMap( x -> x.stream())
			.collect(Collectors.toList());
	}
	
	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????
	 * @param require
	 * @param targetTimeSpan ???????????????
	 * @return
	 */
	private List<TimeSpanForCalc> getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan(
			Require require, 
			TimeSpanForCalc targetTimeSpan) {
		List<TimeSpanForCalc> notWorkingTimeSpanList = new ArrayList<>();
		
		// break time list ???????????????
		List<TimeSpanForCalc> breakTimeList = this.lstBreakTime.getBreakTimeSheets().stream()
				.map( sheet -> sheet.convertToTimeSpanForCalc())
				.collect(Collectors.toList());
		notWorkingTimeSpanList.addAll(breakTimeList);
		
		// short time list ????????????????????????
		if ( this.optSortTimeWork.isPresent() ){
			List<TimeSpanForCalc> shortTimeList = this.optSortTimeWork.get().getShortWorkingTimeSheets().stream()
							.map( sheet -> sheet.convertToTimeSpanForCalc())
							.collect(Collectors.toList());
			notWorkingTimeSpanList.addAll( shortTimeList );
		}
		
		// time vacation ????????????
		List<TimeSpanForCalc> timeVacationSpanList = this.getTimeVacation().values().stream()
				.map( timeVacation -> timeVacation.getTimeList())
				.flatMap( x -> x.stream() )
				.collect( Collectors.toList() );
		notWorkingTimeSpanList.addAll( timeVacationSpanList );
		
		return targetTimeSpan.subtract(notWorkingTimeSpanList, false);
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param require
	 * @param companyId ??????ID
	 * @param targetTaskSchedule ????????????
	 * @return
	 */
	private boolean checkWhetherTaskScheduleIsCorrect(Require require, String companyId, TaskSchedule targetTaskSchedule) {
		
		if( !this.workInfo.isAttendanceRate(require, companyId) ) {
			throw new BusinessException( "Msg_2103" );
		}
		
		List<TimeSpanForCalc> timeVacationList = this.getTimeVacation().values().stream()
								.map( value -> value.getTimeList())
								.flatMap( x -> x.stream())
								.collect(Collectors.toList());
		
		this.taskSchedule.getDetails().stream()
			.map( detail -> detail.getTimeSpan())
			.forEach( taskTimeSpan -> {
				
				if ( !this.optTimeLeaving.get().isIncludeInWorkTimeSpan(taskTimeSpan) ) {
					// if this.workInfo is attendance rate, this.optTimeLeaving will always be present.
					throw new BusinessException("Msg_2098");
				}
				
				if ( this.lstBreakTime.isDuplicatedWithBreakTime(taskTimeSpan) ) {
					throw new BusinessException("Msg_2099");
				}
				
				if ( this.optSortTimeWork.isPresent() && this.optSortTimeWork.get().isDuplicatedWithShortTime(taskTimeSpan) ) {
					throw new BusinessException("Msg_2100");
				}
				
				boolean isDuplicateWithTimeVacation = timeVacationList.stream()
						.anyMatch( timeVacation -> timeVacation.checkDuplication(taskTimeSpan).isDuplicated() );
				if ( isDuplicateWithTimeVacation ){
					throw new BusinessException("Msg_2101");
				}
				
			});
		
		this.checkConsistencyBetweenSupportScheduleAndTaskSchedule();
		
		return true;
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @param supportTicket ??????????????????
	 * @param isRemove ???????????????
	 */
	private void checkWhetherCanUpdateSupportSchedule(SupportTicket supportTicket, boolean isRemove) {
		
		if ( !supportTicket.getEmployeeId().v().equals(this.employeeID) || !supportTicket.getDate().equals(this.ymd) ) {
			throw new BusinessException("Msg_3254");
		}
		
		if ( this.confirmedATR == ConfirmedATR.CONFIRMED ) {
			throw new BusinessException("Msg_2268");
		}
		
		if ( isRemove ) {
			return;
		}
		
		switch (supportTicket.getSupportType()) {
		case ALLDAY:
			if ( this.taskSchedule.isTaskScheduleGranted() ) {
				throw new BusinessException("Msg_2271");
			}
			break;
		case TIMEZONE:
			if ( this.taskSchedule.isTaskScheduleGrantedIn(supportTicket.getTimespan().get()) ) {
				throw new BusinessException("Msg_2273");
			}
			break;	
		default:
			throw new RuntimeException("support type is invalid!");
		}
		
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param require
	 */
	public void checkConsistencyOfSupportSchedule(Require require) {
		
		val supportType = this.supportSchedule.getSupportType();
		if ( !supportType.isPresent() || supportType.get() == SupportType.ALLDAY ) {
			return;
		}
			
		// TODO resolve conflict, need to fix after
		String companyId = AppContexts.user().companyId();
		if ( !this.workInfo.isAttendanceRate(require, companyId) ) {
			
			throw new BusinessException("Msg_2275");
		}
		
		val workTimeSpans = this.optTimeLeaving.get().getTimeOfTimeLeavingAtt();
		if ( this.supportSchedule.getDetails().stream()
				.anyMatch(detail -> !detail.doesItFitInTheSpecifiedTimeSpan(workTimeSpans)) ) {
			
			throw new BusinessException("Msg_2276");
		}
		
		this.checkConsistencyBetweenSupportScheduleAndTaskSchedule();
		
	}
	
	/**
	 * ????????????????????????????????????????????????????????????
	 */
	private void checkConsistencyBetweenSupportScheduleAndTaskSchedule() {
		
		val supportTimeSpanList = this.supportSchedule.getSupportTimeSpanList();
		
		val existATaskWhichIsStraddled = supportTimeSpanList.stream().anyMatch( supportTimeSpan -> 
			this.taskSchedule.getDetails().stream()
				.anyMatch( task -> task.isDuplicateWith(supportTimeSpan) && !task.isContainedIn(supportTimeSpan))
		);
		
		if ( existATaskWhichIsStraddled ) {
			
			throw new BusinessException("Msg_3235");
		}
	}
	
	public static interface Require extends 
		WorkInfoOfDailyAttendance.Require, 
		AffiliationInforOfDailyAttd.Require,
		TimeLeavingOfDailyAttd.Require, 
		EditStateOfDailyAttd.Require,
		SupportSchedule.Require {
	
	}

}
