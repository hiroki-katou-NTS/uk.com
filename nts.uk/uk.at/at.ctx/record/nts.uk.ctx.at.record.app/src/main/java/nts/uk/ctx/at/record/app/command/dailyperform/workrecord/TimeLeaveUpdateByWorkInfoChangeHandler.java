package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectWorkInforDomainService;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.context.AppContexts;

/** Event：出退勤時刻を補正する */
@Stateless
public class TimeLeaveUpdateByWorkInfoChangeHandler extends CommandHandler<TimeLeaveUpdateByWorkInfoChangeCommand> {

	private final static List<Integer> LEAVE_ITEMS = Arrays.asList(31, 41);
	private final static List<Integer> ATTENDANCE_ITEMS = Arrays.asList(34, 44);

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
	protected void handle(CommandHandlerContext<TimeLeaveUpdateByWorkInfoChangeCommand> context) {
		TimeLeaveUpdateByWorkInfoChangeCommand command = context.getCommand();

		this.workInfoRepo.find(command.getEmployeeId(), command.getTargetDate()).ifPresent(wi -> {
			String companyId = AppContexts.user().companyId();
			workTypeRepo.findByPK(companyId, wi.getRecordInfo().getWorkTypeCode().v()).ifPresent(wt -> {
				/** 取得したドメインモデル「勤務種類．一日の勤務．勤務区分」をチェックする */
				if (wt.isWokingDay()) {
					val wts = wt.getWorkTypeSetAvailable();
					if (wts.getAttendanceTime() == WorkTypeSetCheck.CHECK  || wts.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
						val tlo = timeLeaveRepo.findByKey(command.getEmployeeId(), command.getTargetDate());
						TimeLeavingOfDailyPerformance tl = null;
						if (tlo.isPresent()) {
							tl = mergeWithEditStates(command.getEmployeeId(), command.getTargetDate(), tlo.get(), wts);
						}
						updateTimeLeave(companyId, wi, tl, command.getEmployeeId(), command.getTargetDate());
					}
				} else {
					/** どちらか一方が 年休 or 特別休暇 の場合 */
					if (wt.getDailyWork().isHalfDayAnnualOrSpecialHoliday()) {
						deleteTimeLeave(true, command.getEmployeeId(), command.getTargetDate());
						return;
					}
					deleteTimeLeave(false, command.getEmployeeId(), command.getTargetDate());
				}
			});
		});
	}

	/** 取得したドメインモデル「編集状態」を見て、マージする */
	private TimeLeavingOfDailyPerformance mergeWithEditStates(String employeeId, GeneralDate workingDate,
			TimeLeavingOfDailyPerformance timeLeave, WorkTypeSet wts) {
		List<Integer> inputByReflect = this.editStateRepo.findByItems(employeeId, workingDate, mergeItems()).stream()
				.filter(es -> isInputByReflect(es.getEditStateSetting())).map(c -> c.getAttendanceItemId())
				.collect(Collectors.toList());
		if (wts.getAttendanceTime() == WorkTypeSetCheck.CHECK) {
			/** 「所定勤務の設定．打刻の扱い方．出勤時刻を直行とする」＝ TRUE */
			inputByReflect.removeIf(id -> ATTENDANCE_ITEMS.contains(id));
		}
		if (wts.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			/** 「所定勤務の設定．打刻の扱い方．退勤時刻を直行とする」＝ TRUE */
			inputByReflect.removeIf(id -> LEAVE_ITEMS.contains(id));
		}
		if (!inputByReflect.isEmpty()) {
			removeAttendanceLeave(timeLeave, inputByReflect, 0);
			removeAttendanceLeave(timeLeave, inputByReflect, 1);
		}
		return timeLeave;
	}

	/** 「日別実績の出退勤．出退勤の打刻」を削除する */
	private void removeAttendanceLeave(TimeLeavingOfDailyPerformance timeLeave, List<Integer> inputByReflect,
			int index) {
		if (inputByReflect.contains(LEAVE_ITEMS.get(index)) || inputByReflect.contains(ATTENDANCE_ITEMS.get(index))) {
			timeLeave.getAttendanceLeavingWork(index + 1).ifPresent(alw -> {
				alw.getAttendanceStamp().ifPresent(as -> {
					if (inputByReflect.contains(ATTENDANCE_ITEMS.get(index)) && as.getStamp().isPresent() 
							&& !as.getStamp().get().isFromSPR()) {
						as.removeStamp();
					}
				});
				alw.getLeaveStamp().ifPresent(ls -> {
					if (inputByReflect.contains(LEAVE_ITEMS.get(index)) && ls.getStamp().isPresent() 
							&& !ls.getStamp().get().isFromSPR()) {
						ls.removeStamp();
					}
				});
			});
		}
	}

	private List<Integer> mergeItems() {
		return Stream.of(LEAVE_ITEMS, ATTENDANCE_ITEMS, Arrays.asList(157)).flatMap(List::stream).collect(Collectors.toList());
	}

	/** 申請反映の勤怠項目を判断する */
	private boolean isInputByReflect(EditStateSetting es) {
		return es == EditStateSetting.REFLECT_APPLICATION;
	}

	/** 日別実績の出退勤を更新する */
	private void updateTimeLeave(String companyId, WorkInfoOfDailyPerformance workInfo,
			TimeLeavingOfDailyPerformance timeLeave, String employeeID, GeneralDate day) {
		/** 自動打刻セットする */
		timeLeave = reflectService.createStamp(companyId, workInfo, workConditionRepo.getBySidAndStandardDate(employeeID, day),
				timeLeave, employeeID, day, null);
		if(timeLeave != null) {
			timeLeave.setWorkTimes(new WorkTimes(countTime(timeLeave)));
			this.timeLeaveRepo.update(timeLeave);
			/** <<Event>> 実績の出退勤が変更されたイベントを発行する　*/
			timeLeave.timeLeavesChanged();
		}
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
	private void deleteTimeLeave(boolean isSPR, String employeeId, GeneralDate date) {
		timeLeaveRepo.findByKey(employeeId, date).ifPresent(tl -> {
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

			this.timeLeaveRepo.update(tl);
			tl.timeLeavesChanged();
		});
	}

	private boolean isRemoveStamp(WorkStamp ass) {
		return ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT ||
				ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION ||
				ass.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON || 
				ass.getStampSourceInfo() == StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO;
	}

}
