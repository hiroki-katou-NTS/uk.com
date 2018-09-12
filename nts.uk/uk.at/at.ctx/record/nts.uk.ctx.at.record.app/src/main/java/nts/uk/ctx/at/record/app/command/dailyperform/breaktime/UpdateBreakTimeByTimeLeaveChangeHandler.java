package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.command.dailyperform.correctevent.DailyCorrectEventServiceCenter;
import nts.uk.ctx.at.record.app.command.dailyperform.correctevent.DailyCorrectEventServiceCenter.EventHandleAction;
import nts.uk.ctx.at.record.app.command.dailyperform.correctevent.DailyCorrectEventServiceCenter.EventHandleResult;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectBreakTimeOfDailyDomainService;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/** Event：休憩時間帯を補正する */
@Stateless
public class UpdateBreakTimeByTimeLeaveChangeHandler extends CommandHandlerWithResult<UpdateBreakTimeByTimeLeaveChangeCommand, EventHandleResult<BreakTimeOfDailyPerformance>> {

	@Inject
	private WorkInformationRepository workInfoRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeRepo;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;

	@Inject
	private ReflectBreakTimeOfDailyDomainService reflectBreakTimeService;

	@Inject
	private EditStateOfDailyPerformanceRepository editStateRepo;

	@Override
	/** 休憩時間帯を補正する */
	protected EventHandleResult<BreakTimeOfDailyPerformance> handle(CommandHandlerContext<UpdateBreakTimeByTimeLeaveChangeCommand> context) {
		UpdateBreakTimeByTimeLeaveChangeCommand command = context.getCommand();
		WorkInfoOfDailyPerformance wi = command.cachedWorkInfo.orElse(getDefaultWorkInfo(command));
		if(wi == null) {
			return EventHandleResult.withResult(EventHandleAction.ABORT, null);
		};
		String companyId = command.companyId.orElse(AppContexts.user().companyId());
		
		WorkType wt = command.cachedWorkType.orElse(getDefaultWorkType(wi.getRecordInfo().getWorkTypeCode().v(), companyId));
		if(wt == null) {
			return EventHandleResult.withResult(EventHandleAction.ABORT, null);
		}
		
		if (!wt.isWokingDay()) {
			/** 「日別実績の休憩時間帯」を削除する */
			if(!command.actionOnCache){
				this.breakTimeRepo.delete(command.getEmployeeId(), command.targetDate);
			}
			return EventHandleResult.withResult(EventHandleAction.DELETE, null);
		}
		
		BreakTimeOfDailyPerformance breakTime = getUpdateBreakTime(command, wi, companyId);
		if(breakTime != null){
			/** 「日別実績の休憩時間帯」を更新する */
			if(!command.actionOnCache){
				this.breakTimeRepo.update(breakTime);
			}
			return EventHandleResult.withResult(EventHandleAction.UPDATE, breakTime);
		}
		/** 「日別実績の休憩時間帯」を削除する */
		if(!command.actionOnCache){
			this.breakTimeRepo.delete(command.getEmployeeId(), command.targetDate);
		}
		return EventHandleResult.withResult(EventHandleAction.DELETE, null);
	}

	/** 「補正した休憩時間帯」を取得する */
	private BreakTimeOfDailyPerformance getUpdateBreakTime(UpdateBreakTimeByTimeLeaveChangeCommand command,
			WorkInfoOfDailyPerformance wi, String companyId) {
		TimeLeavingOfDailyPerformance timeLeave = command.cachedTimeLeave.orElse(getTimeLeaveDefault(command));
		
		BreakTimeOfDailyPerformance breakTime = reflectBreakTimeService.reflectBreakTime(companyId, command.employeeId,
				command.targetDate, null, timeLeave, wi);
		
		BreakTimeOfDailyPerformance breakTimeRecord = command.cachedBreackTime.orElse(getBreakTimeDefault(command.employeeId,
				command.targetDate));
		
		if (breakTimeRecord != null) {
			return mergeWithEditStates(command, breakTime, breakTimeRecord);
		}
		return breakTime;
	}

	/** 取得したドメインモデル「編集状態」を見て、マージする */
	private BreakTimeOfDailyPerformance mergeWithEditStates(UpdateBreakTimeByTimeLeaveChangeCommand command,
			BreakTimeOfDailyPerformance breakTime, BreakTimeOfDailyPerformance breakTimeRecord) {
		List<EditStateOfDailyPerformance> editStates = getEditStateByItems(command);
		if(CollectionUtil.isEmpty(editStates)){
			return breakTime;
		}
		List<Integer> itemsToMerge = getItemsToMerge(editStates);
		if (!itemsToMerge.isEmpty()) {
			List<ItemValue> ipByHandValues = AttendanceItemUtil.toItemValues(BreakTimeDailyDto.getDto(breakTime), itemsToMerge);
			
			return AttendanceItemUtil.fromItemValues(BreakTimeDailyDto.getDto(breakTimeRecord), ipByHandValues.stream()
						.filter(c -> c != null).collect(Collectors.toList())).toDomain(command.employeeId, command.targetDate);
		}
		
		return breakTimeRecord;
	}
	
	private List<Integer> getItemsToMerge(List<EditStateOfDailyPerformance> editStates) {
		List<Integer> endItemsToMerge = getItemBys(editStates, DailyCorrectEventServiceCenter.END_BREAK_TIME_CLOCK_ITEMS)
											.stream().sorted().collect(Collectors.toList());
		List<Integer> startItemsToMerge = getItemBys(editStates, DailyCorrectEventServiceCenter.START_BREAK_TIME_CLOCK_ITEMS)
											.stream().sorted().collect(Collectors.toList());
		
		List<Integer> result = new ArrayList<>();
		
		for(int i = 0; i < startItemsToMerge.size(); i++){
			int itemNo = DailyCorrectEventServiceCenter.START_BREAK_TIME_CLOCK_ITEMS.indexOf(startItemsToMerge.get(i));
			
			if(endItemsToMerge.size() > i && endItemsToMerge.get(i) == DailyCorrectEventServiceCenter.END_BREAK_TIME_CLOCK_ITEMS.get(itemNo)){
				result.add(startItemsToMerge.get(i));
				result.add(endItemsToMerge.get(i));
			}
		}
		
		return result;
	}

	private List<Integer> getItemBys(List<EditStateOfDailyPerformance> editStates, List<Integer> toGet) {
		
		List<Integer> edited = editStates.stream().filter(e -> toGet.contains(e.getAttendanceItemId()) && isInputByHands(e.getEditStateSetting()))
												.map(c -> c.getAttendanceItemId()) 
												.collect(Collectors.toList());
		return toGet.stream().filter(i -> !edited.contains(i)).collect(Collectors.toList());
	}

	/** 手修正の勤怠項目を判断する */
	private boolean isInputByHands(EditStateSetting es) {
		
		return es == EditStateSetting.HAND_CORRECTION_MYSELF || es == EditStateSetting.HAND_CORRECTION_OTHER;
	}

	private List<EditStateOfDailyPerformance> getEditStateByItems(UpdateBreakTimeByTimeLeaveChangeCommand command) {
		
		return command.cachedEditState.orElse(getDefaultEditStates(command, getBreakTimeClockItems()));
	}

	private List<Integer> getBreakTimeClockItems() {
		return Stream.concat(DailyCorrectEventServiceCenter.START_BREAK_TIME_CLOCK_ITEMS.stream(), 
							DailyCorrectEventServiceCenter.END_BREAK_TIME_CLOCK_ITEMS.stream())
                	.collect(Collectors.toList());
	}

	private BreakTimeOfDailyPerformance getBreakTimeDefault(String employeeId, GeneralDate workingDate) {
		return breakTimeRepo.find(employeeId, workingDate, BreakType.REFER_WORK_TIME.value).orElse(null);
	}

	private TimeLeavingOfDailyPerformance getTimeLeaveDefault(UpdateBreakTimeByTimeLeaveChangeCommand command) {
		return timeLeaveRepo.findByKey(command.employeeId, command.targetDate).orElse(null);
	}

	private WorkType getDefaultWorkType(String workTypeCode, String companyId) {
		return workTypeRepo.findByPK(companyId, workTypeCode).orElse(null);
	}

	private WorkInfoOfDailyPerformance getDefaultWorkInfo(UpdateBreakTimeByTimeLeaveChangeCommand command) {
		return this.workInfoRepo.find(command.employeeId, command.targetDate).orElse(null);
	}

	private List<EditStateOfDailyPerformance> getDefaultEditStates(UpdateBreakTimeByTimeLeaveChangeCommand command,
			List<Integer> needCheckItems) {
		return this.editStateRepo.findByItems(command.employeeId, command.targetDate, needCheckItems);
	}
}
