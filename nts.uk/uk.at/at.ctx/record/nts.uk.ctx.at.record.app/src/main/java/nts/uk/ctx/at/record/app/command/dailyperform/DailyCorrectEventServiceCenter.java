package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Builder;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeaveUpdateByWorkInfoChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeaveUpdateByWorkInfoChangeHandler;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class DailyCorrectEventServiceCenter {

	@Inject
	private TimeLeaveUpdateByWorkInfoChangeHandler timeLeaveCorrectHandler;
	
	@Inject
	private UpdateBreakTimeByTimeLeaveChangeHandler breakTimeCorrectHandler;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkingConditionItemRepository workConditionRepo;
	
	public final static List<Integer> LEAVE_ITEMS = Arrays.asList(31, 41);
	
	public final static List<Integer> ATTENDANCE_ITEMS = Arrays.asList(34, 44);
	
	public final static List<Integer> BREAK_TIME_ITEMS = Arrays.asList(157, 159, 160, 161, 163, 165, 166, 167, 169, 171, 172,
			173, 175, 177, 178, 179, 181, 183, 184, 185, 187, 189, 190, 191, 193, 195, 196, 197, 199, 201, 202, 203,
			205, 207, 208, 209, 211, 213, 214, 215);
	
	public List<IntegrationOfDaily> correctTimeLeaveAndBreakTime(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(false).triggerBreakTime(true).triggerTimeLeave(true).build();
		return prepareSourceAndFireEvent(sources, companyId, eventBus);
	}
	
	public List<IntegrationOfDaily> correctTimeLeave(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(true).triggerBreakTime(false).triggerTimeLeave(true).build();
		return prepareSourceAndFireEvent(sources, companyId, eventBus);
	}
	
	public List<IntegrationOfDaily> correctBreakTime(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(true).triggerBreakTime(true).triggerTimeLeave(false).build();
		return prepareSourceAndFireEvent(sources, companyId, eventBus);
	}
	
	private List<IntegrationOfDaily> prepareSourceAndFireEvent(List<DailyRecordWorkCommand> sources, String companyId, EventTrigger eventBus){
		if(sources.isEmpty()){
			return new ArrayList<>();
		}
		Set<String> workTypeCode = new HashSet<>();
		sources.stream().forEach(s -> {
			workTypeCode.add(s.getWorkInfo().getData().getRecordInfo().getWorkTypeCode().v());
		});
		Map<String, Set<GeneralDate>> employeeIds = sources.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId(), 
					Collectors.collectingAndThen(Collectors.toList(), 
						c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toSet()))));
		
		Map<WorkTypeCode, WorkType> workTypes = workTypeRepo.getPossibleWorkType(companyId, new ArrayList<>(workTypeCode))
				.stream().collect(Collectors.toMap(wt -> wt.getWorkTypeCode(), wt -> wt));

		return triggerEvent(sources, companyId, workTypes, getWorkCondition(eventBus, employeeIds), eventBus);
	}

	private Map<String, Map<GeneralDate, WorkingConditionItem>> getWorkCondition(EventTrigger eventBus,
			Map<String, Set<GeneralDate>> employeeIds) {
		return eventBus.triggerTimeLeave ? null : workConditionRepo.getBySidAndPeriod(employeeIds);
	}

	private List<IntegrationOfDaily> triggerEvent(List<DailyRecordWorkCommand> sources, String companyId,
			Map<WorkTypeCode, WorkType> workTypes, Map<String, Map<GeneralDate, WorkingConditionItem>> workCondition,
			EventTrigger eventTriggerBus) {
		return sources.stream().map(c -> {
			IntegrationOfDaily dailyRecord = c.toDomain();
			WorkInfoOfDailyPerformance wi = dailyRecord.getWorkInformation();
			
			if(eventTriggerBus.triggerTimeLeave){
				TimeLeaveUpdateByWorkInfoChangeCommand timeLeaveEvent = TimeLeaveUpdateByWorkInfoChangeCommand
						.builder().employeeId(c.getEmployeeId())
						.targetDate(c.getWorkDate())
						.companyId(Optional.of(companyId))
						.cachedEditState(Optional.of(dailyRecord.getEditState()))
						.cachedWorkCondition(Optional.of(getBySidAndDate(workCondition, wi)))
						.cachedWorkInfo(Optional.of(wi))
						.cachedTimeLeave(dailyRecord.getAttendanceLeave())
						.isTriggerRelatedEvent(eventTriggerBus.isTriggerRelatedEvent)
						.cachedWorkType(Optional.ofNullable(workTypes.get(wi.getRecordInfo().getWorkTypeCode()))).build();
				TimeLeavingOfDailyPerformance  timeLeaveCorrected = timeLeaveCorrectHandler.handle(timeLeaveEvent);
				dailyRecord.setAttendanceLeave(Optional.ofNullable(timeLeaveCorrected));
			}
			
			if(eventTriggerBus.triggerBreakTime){
				UpdateBreakTimeByTimeLeaveChangeCommand breakTimeEvent = UpdateBreakTimeByTimeLeaveChangeCommand
						.builder().employeeId(c.getEmployeeId())
						.workingDate(c.getWorkDate())
						.companyId(Optional.of(companyId))
						.cachedEditState(Optional.of(dailyRecord.getEditState()))
						.cachedWorkInfo(Optional.of(wi))
						.cachedTimeLeave(dailyRecord.getAttendanceLeave())
						.cachedWorkType(Optional.ofNullable(workTypes.get(wi.getRecordInfo().getWorkTypeCode())))
						.isTriggerRelatedEvent(eventTriggerBus.isTriggerRelatedEvent)
						.cachedBreackTime(dailyRecord.getBreakTime().stream().filter(b -> b.getBreakType() == BreakType.REFER_WORK_TIME).findFirst())
						.build();
				BreakTimeOfDailyPerformance  breakTimeCorrected = breakTimeCorrectHandler.handle(breakTimeEvent);
				dailyRecord.getBreakTime().removeIf(b -> b.getBreakType() == breakTimeCorrected.getBreakType());
				dailyRecord.getBreakTime().add(breakTimeCorrected);
			}
			
			return dailyRecord;
		}).collect(Collectors.toList());
	}

	private WorkingConditionItem getBySidAndDate(Map<String, Map<GeneralDate, WorkingConditionItem>> workCondition,
			WorkInfoOfDailyPerformance wi) {
		if(workCondition.get(wi.getEmployeeId()) == null){
			return null;
		}
		return workCondition.get(wi.getEmployeeId()).get(wi.getYmd());
	}
	
	@Builder
	public static class EventTrigger {
		
		boolean isTriggerRelatedEvent;
		
		boolean triggerTimeLeave;
		
		boolean triggerBreakTime;
	}
}
