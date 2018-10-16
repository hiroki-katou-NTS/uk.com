package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyCorrectEventServiceCenter;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyCorrectEventServiceCenter.EventHandleAction;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyCorrectEventServiceCenter.EventHandleResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectWorkInforDomainService;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.context.AppContexts;

/** Event：出退勤時刻を補正する */
@Stateless
public class TimeLeaveUpdateByWorkInfoChangeHandler extends CommandHandlerWithResult<TimeLeaveUpdateByWorkInfoChangeCommand, EventHandleResult<TimeLeavingOfDailyPerformance>> {

	@Inject
	private WorkInformationRepository workInfoRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;

	@Inject
	private WorkingConditionItemRepository workConditionRepo;

	@Inject
	private ReflectWorkInforDomainService reflectService;

	@Inject
	private EditStateOfDailyPerformanceRepository editStateRepo;

	@Override
	protected EventHandleResult<TimeLeavingOfDailyPerformance> handle(CommandHandlerContext<TimeLeaveUpdateByWorkInfoChangeCommand> context) {
		TimeLeaveUpdateByWorkInfoChangeCommand command = context.getCommand();

		WorkInfoOfDailyPerformance wi = getWithDefaul(command.cachedWorkInfo, () -> getDefaultWorkInfo(command));
		if(wi == null) {
			return EventHandleResult.withResult(EventHandleAction.ABORT, null);
		};
		
		String companyId = getWithDefaul(command.companyId, () -> AppContexts.user().companyId());
		
		WorkType wt = getWithDefaul(command.cachedWorkType, () -> getDefaultWorkType(wi.getRecordInfo().getWorkTypeCode().v(), companyId));
		if(wt == null) {
			return EventHandleResult.withResult(EventHandleAction.ABORT, null);
		}
		
		/** 取得したドメインモデル「勤務種類．一日の勤務．勤務区分」をチェックする */
		if (wt.isWokingDay()) {
			val wts = wt.getWorkTypeSetAvailable();
			if (wts.getAttendanceTime() == WorkTypeSetCheck.CHECK  || wts.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
				TimeLeavingOfDailyPerformance tlo = getWithDefaul(command.cachedTimeLeave, () -> getTimeLeaveDefault(command));
				TimeLeavingOfDailyPerformance tl = null;
				if (tlo != null) {
					tl = mergeWithEditStates(command, tlo, wts);
				}
				return EventHandleResult.withResult(EventHandleAction.UPDATE, updateTimeLeave(companyId, wi, tl, command));
			} else {
				return EventHandleResult.withResult(EventHandleAction.ABORT, getWithDefaul(command.cachedTimeLeave, () -> getTimeLeaveDefault(command)));
			}
		}
		
		/** どちらか一方が 年休 or 特別休暇 の場合 */
		if (wt.getDailyWork().isHalfDayAnnualOrSpecialHoliday()) {
			return EventHandleResult.withResult(EventHandleAction.UPDATE, deleteTimeLeave(true, command));
		}
		return EventHandleResult.withResult(EventHandleAction.UPDATE, deleteTimeLeave(false, command));
	}

	/** 取得したドメインモデル「編集状態」を見て、マージする */
	private TimeLeavingOfDailyPerformance mergeWithEditStates(TimeLeaveUpdateByWorkInfoChangeCommand command,
			TimeLeavingOfDailyPerformance timeLeave, WorkTypeSet wts) {
		List<Integer> inputByReflect = getEditStateByItems(command).stream()
																	.filter(es -> isInputByReflect(es.getEditStateSetting()))
																	.map(c -> c.getAttendanceItemId())
																	.collect(Collectors.toList());
		if (wts.getAttendanceTime() == WorkTypeSetCheck.CHECK) {
			/** 「所定勤務の設定．打刻の扱い方．出勤時刻を直行とする」＝ TRUE */
			inputByReflect.removeIf(id -> DailyCorrectEventServiceCenter.ATTENDANCE_ITEMS.contains(id));
		}
		if (wts.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			/** 「所定勤務の設定．打刻の扱い方．退勤時刻を直行とする」＝ TRUE */
			inputByReflect.removeIf(id -> DailyCorrectEventServiceCenter.LEAVE_ITEMS.contains(id));
		}
		if (!inputByReflect.isEmpty()) {
			removeAttendanceLeave(timeLeave, inputByReflect, 0);
			removeAttendanceLeave(timeLeave, inputByReflect, 1);
		}
		return timeLeave;
	}

	private List<EditStateOfDailyPerformance> getEditStateByItems(TimeLeaveUpdateByWorkInfoChangeCommand command) {
		List<Integer> needCheckItems = mergeItems();
		return getWithDefaul(command.cachedEditState, () -> getDefaultEditStates(command, needCheckItems))
											.stream().filter(e -> needCheckItems.contains(e.getAttendanceItemId()))
											.collect(Collectors.toList());
	}

	/** 「日別実績の出退勤．出退勤の打刻」を削除する */
	private void removeAttendanceLeave(TimeLeavingOfDailyPerformance timeLeave, List<Integer> inputByReflect, int index) {
		if (inputByReflect.contains(DailyCorrectEventServiceCenter.LEAVE_ITEMS.get(index)) 
				|| inputByReflect.contains(DailyCorrectEventServiceCenter.ATTENDANCE_ITEMS.get(index))) {
			timeLeave.getAttendanceLeavingWork(index + 1).ifPresent(alw -> {
				alw.getAttendanceStamp().ifPresent(as -> {
					if (inputByReflect.contains(DailyCorrectEventServiceCenter.ATTENDANCE_ITEMS.get(index)) 
							&& as.getStamp().isPresent() && !as.getStamp().get().isFromSPR()) {
						as.removeStamp();
					}
				});
				alw.getLeaveStamp().ifPresent(ls -> {
					if (inputByReflect.contains(DailyCorrectEventServiceCenter.LEAVE_ITEMS.get(index)) 
							&& ls.getStamp().isPresent() && !ls.getStamp().get().isFromSPR()) {
						ls.removeStamp();
					}
				});
			});
		}
	}

	private List<Integer> mergeItems() {
		return Stream.of(DailyCorrectEventServiceCenter.LEAVE_ITEMS, DailyCorrectEventServiceCenter.ATTENDANCE_ITEMS)
				.flatMap(List::stream).collect(Collectors.toList());
	}

	/** 申請反映の勤怠項目を判断する */
	private boolean isInputByReflect(EditStateSetting es) {
		return es == EditStateSetting.REFLECT_APPLICATION;
	}

	/** 日別実績の出退勤を更新する */
	private TimeLeavingOfDailyPerformance updateTimeLeave(String companyId, WorkInfoOfDailyPerformance workInfo,
			TimeLeavingOfDailyPerformance timeLeave, TimeLeaveUpdateByWorkInfoChangeCommand command) {
		/** 自動打刻セットする */
		timeLeave = reflectService.createStamp(companyId, workInfo, getWorkConditionOrDefault(command),
				timeLeave, command.employeeId, command.targetDate, null);
		if(timeLeave != null) {
			timeLeave.setWorkTimes(new WorkTimes(countTime(timeLeave)));
			if(!command.actionOnCache){
				this.timeLeaveRepo.update(timeLeave);
			}
			
			if(command.isTriggerRelatedEvent) {
				/** <<Event>> 実績の出退勤が変更されたイベントを発行する　*/
				timeLeave.timeLeavesChanged();
			}
		}
		return timeLeave;
	}

	/** 出退勤回数の計算 */
	private int countTime(TimeLeavingOfDailyPerformance timeLeave) {
		return timeLeave.getTimeLeavingWorks().stream()
				.filter(tl -> tl.getAttendanceStamp().isPresent()
						&& tl.getAttendanceStamp().get().getStamp().isPresent() && tl.getLeaveStamp().isPresent()
						&& tl.getLeaveStamp().get().getStamp().isPresent())
				.mapToInt(tl -> 1).sum();
	}

	/** 日別実績の出退勤を削除する */
	private TimeLeavingOfDailyPerformance deleteTimeLeave(boolean isSPR, TimeLeaveUpdateByWorkInfoChangeCommand command) {
		TimeLeavingOfDailyPerformance tl = getWithDefaul(command.cachedTimeLeave, () -> getTimeLeaveDefault(command));
		if(tl != null) {
			if (isSPR) {
				tl.getTimeLeavingWorks().stream().forEach(tlw -> {
					tlw.getAttendanceStamp().ifPresent(as -> {
						if (as.getStamp().isPresent() && !as.getStamp().get().isFromSPR()) {
							as.removeStamp();
						}
					});
					tlw.getLeaveStamp().ifPresent(as -> {
						if (as.getStamp().isPresent() && !as.getStamp().get().isFromSPR()) {
							as.removeStamp();
						}
					});
				});
			} else {
				tl.getTimeLeavingWorks().stream().forEach(tlw -> {
					tlw.getAttendanceStamp().ifPresent(as -> {
						as.getStamp().ifPresent(ass -> {
							if(isRemoveStamp(ass)){
								as.removeStamp();
							}
						});
					});

					tlw.getLeaveStamp().ifPresent(as -> {
						as.getStamp().ifPresent(ass -> {
							if(isRemoveStamp(ass)){
								as.removeStamp();
							}
						});
					});
				});
			}

			if(!command.actionOnCache){
				this.timeLeaveRepo.update(tl);
			}

			if(command.isTriggerRelatedEvent) {
				/** <<Event>> 実績の出退勤が変更されたイベントを発行する　*/
				tl.timeLeavesChanged();
			}
		}
		return tl;
	}

	private boolean isRemoveStamp(WorkStamp ass) {
		return ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT ||
				ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION ||
				ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON || 
				ass.getStampSourceInfo() == StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO;
	}

	private Optional<WorkingConditionItem> getWorkConditionOrDefault(TimeLeaveUpdateByWorkInfoChangeCommand command) {
		if(command.cachedWorkCondition.isPresent()){
			return command.cachedWorkCondition;
		}
		return workConditionRepo.getBySidAndStandardDate(command.employeeId, command.targetDate);
	}

	private TimeLeavingOfDailyPerformance getTimeLeaveDefault(TimeLeaveUpdateByWorkInfoChangeCommand command) {
		return timeLeaveRepo.findByKey(command.employeeId, command.targetDate).orElse(null);
	}

	private WorkType getDefaultWorkType(String workTypeCode, String companyId) {
		return workTypeRepo.findByPK(companyId, workTypeCode).orElse(null);
	}

	private WorkInfoOfDailyPerformance getDefaultWorkInfo(TimeLeaveUpdateByWorkInfoChangeCommand command) {
		return this.workInfoRepo.find(command.employeeId, command.targetDate).orElse(null);
	}

	private List<EditStateOfDailyPerformance> getDefaultEditStates(TimeLeaveUpdateByWorkInfoChangeCommand command,
			List<Integer> needCheckItems) {
		return this.editStateRepo.findByItems(command.employeeId, command.targetDate, needCheckItems);
	}

	private <T> T getWithDefaul(Optional<T> target, Supplier<T> defaultVal){
		if(target.isPresent()){
			return target.get();
		}
		return defaultVal.get();
	}
}
