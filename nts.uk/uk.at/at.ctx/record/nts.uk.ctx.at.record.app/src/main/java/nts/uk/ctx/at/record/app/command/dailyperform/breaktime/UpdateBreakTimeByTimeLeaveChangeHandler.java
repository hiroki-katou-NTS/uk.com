package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectBreakTimeOfDailyDomainService;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/** Event：休憩時間帯を補正する */
@Stateless
public class UpdateBreakTimeByTimeLeaveChangeHandler extends CommandHandler<UpdateBreakTimeByTimeLeaveChangeCommand> {

	private final static List<Integer> BREAK_TIME_ITEMS = Arrays.asList(157, 159, 160, 161, 163, 165, 166, 167, 169, 171, 172,
			173, 175, 177, 178, 179, 181, 183, 184, 185, 187, 189, 190, 191, 193, 195, 196, 197, 199, 201, 202, 203,
			205, 207, 208, 209, 211, 213, 214, 215);
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
	protected void handle(CommandHandlerContext<UpdateBreakTimeByTimeLeaveChangeCommand> context) {
		UpdateBreakTimeByTimeLeaveChangeCommand command = context.getCommand();
		workInfoRepo.find(command.getEmployeeId(), command.getWorkingDate()).ifPresent(wi -> {
			String companyId = AppContexts.user().companyId();
			workTypeRepo.findByPK(companyId, wi.getRecordInfo().getWorkTypeCode().v()).ifPresent(wt -> {
				if (wt.isWokingDay()) {
					BreakTimeOfDailyPerformance breakTime = getUpdateBreakTime(command.getEmployeeId(),
							command.getWorkingDate(), wi, companyId);
					if(breakTime != null){
						/** 「日別実績の休憩時間帯」を更新する */
						this.breakTimeRepo.update(breakTime);
					} else {
						/** 「日別実績の休憩時間帯」を削除する */
						this.breakTimeRepo.delete(command.getEmployeeId(), command.getWorkingDate());
					}
				} else {
					/** 「日別実績の休憩時間帯」を削除する */
					this.breakTimeRepo.delete(command.getEmployeeId(), command.getWorkingDate());
				}
			});
		});
	}

	/** 「補正した休憩時間帯」を取得する */
	private BreakTimeOfDailyPerformance getUpdateBreakTime(String employeeId, GeneralDate workingDate,
			WorkInfoOfDailyPerformance wi, String companyId) {
		Optional<TimeLeavingOfDailyPerformance> timeLeave = timeLeaveRepo.findByKey(employeeId, workingDate);
		BreakTimeOfDailyPerformance breakTime = reflectBreakTimeService.reflectBreakTime(companyId, employeeId,
				workingDate, null, timeLeave.orElse(null), wi);
		Optional<BreakTimeOfDailyPerformance> breakTimeRecord = breakTimeRepo.find(employeeId, workingDate,
				BreakType.REFER_WORK_TIME.value);
		if (breakTimeRecord.isPresent()) {
			return mergeWithEditStates(employeeId, workingDate, breakTime, breakTimeRecord.get());
		}
		return breakTime;
	}

	/** 取得したドメインモデル「編集状態」を見て、マージする */
	private BreakTimeOfDailyPerformance mergeWithEditStates(String employeeId, GeneralDate workingDate,
			BreakTimeOfDailyPerformance breakTime, BreakTimeOfDailyPerformance breakTimeRecord) {
		List<Integer> inputByHandItems = this.editStateRepo.findByItems(employeeId, workingDate, BREAK_TIME_ITEMS)
				.stream().filter(es -> isInputByHands(es.getEditStateSetting())).map(c -> c.getAttendanceItemId())
				.collect(Collectors.toList());
		if (!inputByHandItems.isEmpty()) {
			List<ItemValue> ipByHandValues = AttendanceItemUtil.toItemValues(BreakTimeDailyDto.getDto(breakTimeRecord),
					inputByHandItems);
			if(ipByHandValues != null){
				return AttendanceItemUtil.fromItemValues(BreakTimeDailyDto.getDto(breakTime), ipByHandValues.stream().filter(c -> c != null).collect(Collectors.toList()))
					.toDomain(employeeId, workingDate);
			}
		}
		return breakTime;
	}

	/** 手修正の勤怠項目を判断する */
	private boolean isInputByHands(EditStateSetting es) {
		return es == EditStateSetting.HAND_CORRECTION_MYSELF || es == EditStateSetting.HAND_CORRECTION_OTHER;
	}

}
