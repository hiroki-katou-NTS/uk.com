package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeaveUpdateByWorkInfoChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeaveUpdateByWorkInfoChangeHandler;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
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
	
	public final static List<Integer> WORK_INFO_ITEMS = Arrays.asList(28, 29);
	
	public final static List<Integer> LEAVE_ITEMS = Arrays.asList(31, 41);
	
	public final static List<Integer> ATTENDANCE_ITEMS = Arrays.asList(34, 44);
	
	public final static List<Integer> BREAK_TIME_ITEMS = Arrays.asList(157, 159, 160, 161, 163, 165, 166, 167, 169, 171, 172,
			173, 175, 177, 178, 179, 181, 183, 184, 185, 187, 189, 190, 191, 193, 195, 196, 197, 199, 201, 202, 203,
			205, 207, 208, 209, 211, 213, 214, 215);
	
	public List<DailyRecordWorkCommand> correctTimeLeaveAndBreakTime(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(false).triggerBreakTime(true).triggerTimeLeave(true).build();
		return prepareSourceAndFireEvent(mapShouldTriggerSources(sources, eventBus), 
				companyId, eventBus);
	}
	
	public List<DailyRecordWorkCommand> correctTimeLeave(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(true).triggerBreakTime(false).triggerTimeLeave(true).build();
		return prepareSourceAndFireEvent(mapShouldTriggerSources(sources, eventBus), companyId, eventBus);
	}
	
	public List<DailyRecordWorkCommand> correctBreakTime(List<DailyRecordWorkCommand> sources, String companyId){
		EventTrigger eventBus = EventTrigger.builder().isTriggerRelatedEvent(true).triggerBreakTime(true).triggerTimeLeave(false).build();
		return prepareSourceAndFireEvent(mapShouldTriggerSources(sources, eventBus), companyId, eventBus);
	}

	private Map<DailyRecordWorkCommand, EventTriggerBus> mapShouldTriggerSources(List<DailyRecordWorkCommand> sources, EventTrigger eventBus) {
		List<Integer> timeLeaveItems = mergeItems(ATTENDANCE_ITEMS, LEAVE_ITEMS);
		return sources.stream().collect(Collectors.toMap(s -> s, s -> {
			boolean workInfoChanged = s.itemValues().stream().anyMatch(i -> WORK_INFO_ITEMS.contains(i.itemId()));
			boolean timeLeaveChanged = s.itemValues().stream().anyMatch(i -> timeLeaveItems.contains(i.itemId()));
			return EventTriggerBus.builder().workInfoChanged(workInfoChanged).timeLeaveChanged(timeLeaveChanged).build();
		}));
	}
	
	@SafeVarargs
	private final List<Integer> mergeItems(List<Integer>... sources) {
		return Stream.of(sources).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<DailyRecordWorkCommand> prepareSourceAndFireEvent(Map<DailyRecordWorkCommand, EventTriggerBus> sources, 
			String companyId, EventTrigger eventBus){
		if(sources.isEmpty()){
			return new ArrayList<>();
		}
		if(!sources.values().stream().anyMatch(c -> c.shouldTriggerEvent())){
			return new ArrayList<>(sources.keySet()); 
		}
		Set<String> workTypeCode = new HashSet<>();
		sources.keySet().stream().forEach(s -> {
			workTypeCode.add(s.getWorkInfo().getData().getRecordInfo().getWorkTypeCode().v());
		});
		Map<String, Set<GeneralDate>> employeeIds = sources.keySet().stream().collect(Collectors.groupingBy(c -> c.getEmployeeId(), 
					Collectors.collectingAndThen(Collectors.toList(), 
						c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toSet()))));
		
		Map<WorkTypeCode, WorkType> workTypes = workTypeRepo.getPossibleWorkType(companyId, new ArrayList<>(workTypeCode))
				.stream().collect(Collectors.toMap(wt -> wt.getWorkTypeCode(), wt -> wt));

		return triggerEvent(sources, companyId, workTypes, getWorkCondition(eventBus, employeeIds), eventBus);
	}

	private Map<String, Map<GeneralDate, WorkingConditionItem>> getWorkCondition(EventTrigger eventBus,
			Map<String, Set<GeneralDate>> employeeIds) {
		return !eventBus.triggerTimeLeave ? null : workConditionRepo.getBySidAndPeriod(employeeIds);
	}

	private List<DailyRecordWorkCommand> triggerEvent(Map<DailyRecordWorkCommand, EventTriggerBus> sources, String companyId,
			Map<WorkTypeCode, WorkType> workTypes, Map<String, Map<GeneralDate, WorkingConditionItem>> workCondition,
			EventTrigger eventTriggerBus) {
		return sources.entrySet().stream().map(c -> {
			DailyRecordWorkCommand dailyRecord = c.getKey();
			WorkInfoOfDailyPerformance wi = dailyRecord.getWorkInfo().getData();
			
			if(c.getValue().shouldCorrectTimeLeave() && eventTriggerBus.triggerTimeLeave){
				TimeLeaveUpdateByWorkInfoChangeCommand timeLeaveEvent = (TimeLeaveUpdateByWorkInfoChangeCommand) TimeLeaveUpdateByWorkInfoChangeCommand
						.builder()
						.cachedWorkCondition(getBySidAndDate(workCondition, wi))
						.employeeId(dailyRecord.getEmployeeId())
						.targetDate(dailyRecord.getWorkDate())
						.companyId(companyId)
						.cachedEditState(dailyRecord.getEditState().getData())
						.cachedWorkInfo(wi)
						.cachedTimeLeave(dailyRecord.getTimeLeaving().getData().orElse(null))
						.actionOnCache(true)
						.isTriggerRelatedEvent(eventTriggerBus.isTriggerRelatedEvent)
						.cachedWorkType(workTypes.get(wi.getRecordInfo().getWorkTypeCode())).build();
				EventHandleResult<TimeLeavingOfDailyPerformance> timeLeaveCorrected = timeLeaveCorrectHandler.handle(timeLeaveEvent);
				if(timeLeaveCorrected.action != EventHandleAction.ABORT){
					dailyRecord.getTimeLeaving().updateDataO(Optional.ofNullable(timeLeaveCorrected.data));
					dailyRecord.getBreakTime().shouldDeleteIfNull();
				}
			}
			
			if(c.getValue().shouldCorreactBreakTime() && eventTriggerBus.triggerBreakTime){
				UpdateBreakTimeByTimeLeaveChangeCommand breakTimeEvent = (UpdateBreakTimeByTimeLeaveChangeCommand) UpdateBreakTimeByTimeLeaveChangeCommand
						.builder()
						.cachedBreackTime(dailyRecord.getBreakTime().getData()
								.stream().filter(b -> b.getBreakType() == BreakType.REFER_WORK_TIME).findFirst().orElse(null))
						.employeeId(dailyRecord.getEmployeeId())
						.targetDate(dailyRecord.getWorkDate())
						.companyId(companyId)
						.cachedEditState(dailyRecord.getEditState().getData())
						.cachedWorkInfo(wi)
						.actionOnCache(true)
						.cachedTimeLeave(dailyRecord.getTimeLeaving().getData().orElse(null))
						.cachedWorkType(workTypes.get(wi.getRecordInfo().getWorkTypeCode()))
						.isTriggerRelatedEvent(eventTriggerBus.isTriggerRelatedEvent)
						.build();
				EventHandleResult<BreakTimeOfDailyPerformance>  breakTimeCorrected = breakTimeCorrectHandler.handle(breakTimeEvent);
				if(breakTimeCorrected.action == EventHandleAction.DELETE){
					dailyRecord.getBreakTime().getData().removeIf(b -> b.getBreakType() == BreakType.REFER_WORK_TIME);
					dailyRecord.getBreakTime().shouldDeleteIfNull();
				} else if(breakTimeCorrected.action == EventHandleAction.INSERT || breakTimeCorrected.action == EventHandleAction.UPDATE) {
					dailyRecord.getBreakTime().updateData(breakTimeCorrected.data);
				}
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
	
	@AllArgsConstructor
	public static class EventHandleResult<T> {
		
		EventHandleAction action;
		
		T data;
		
		public static <T> EventHandleResult<T> withResult(EventHandleAction action, T data){
			return new EventHandleResult<T>(action, data);
		}
	}
	
	@AllArgsConstructor
	public enum EventHandleAction {
		
		DELETE(1, "DELETE"),
		UPDATE(2, "UPDATE"),
		INSERT(3, "INSERT"),
		ABORT(4, "ABORT");
		
		final int value;
		
		final String name;
	}
	
	@Builder
	public static class EventTrigger {
		
		boolean isTriggerRelatedEvent;
		
		boolean triggerTimeLeave;
		
		boolean triggerBreakTime;
	}
	
	@Builder
	private static class EventTriggerBus {
		
		boolean workInfoChanged;
		
		boolean timeLeaveChanged;
		
		boolean shouldCorreactBreakTime() { 
			return timeLeaveChanged || shouldCorrectTimeLeave();
		}
		
		boolean shouldCorrectTimeLeave() { 
			return workInfoChanged;
		}
		
		boolean shouldTriggerEvent() {
			return shouldCorreactBreakTime();
		}
	}
	
}
