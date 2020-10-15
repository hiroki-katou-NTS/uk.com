package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm.CorrectWorkTimeByWorkType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;

/**
 * @author ThanhNX
 *
 *         勤怠変更後の補正（日別実績の補正処理）
 */
@Stateless
public class CorrectionAfterTimeChange {

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepo;
	@Inject
	private WorkingConditionRepository workingConditionRepo;

	@Inject
	private CorrectWorkTimeByWorkType correctWorkTimeByWorkType;

	@Inject
	private AutoCorrectStampOfTimeZone autoCorrectStamp;

	@Inject
	private ClearConflictTimeWithDay clearConflictTimeWithDay;

	@Inject
	private ClearConflictTime clearConflictTime;

//	@Inject
//	private CorrectRestTime correctRestTime;

	public IntegrationOfDaily corection(String companyId, IntegrationOfDaily domainDaily) {

		String employeeId = domainDaily.getEmployeeId();

		GeneralDate date = domainDaily.getYmd();

		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workCondOpt = WorkingConditionService.findWorkConditionByEmployee(
				createImp(), employeeId, date);
		
		if (!workCondOpt.isPresent())
			return domainDaily;

		// 勤務種類に応じて就業時間帯の補正を行う
		correctWorkTimeByWorkType.correct(workCondOpt.get(), domainDaily.getWorkInformation());

		// 自動打刻セットの時間帯補正
		//// 直行直帰による、戻り時刻補正
		domainDaily = autoCorrectStamp.autoCorrect(companyId, domainDaily, workCondOpt.get());

		// 矛盾した時刻をクリアする
		clearConflictTimeWithDay.clear(companyId, employeeId, date, workCondOpt.get(), domainDaily.getWorkInformation(),
				domainDaily.getAttendanceLeave(), domainDaily.getEditState());

		// TODO: 休憩時間帯をセットし直す dang thiet ke 設計中
		// correctRestTime.correct(domainDaily);

		// 矛盾した時間をクリアする
		clearConflictTime.process(domainDaily);

		return domainDaily;
	}
	
	private WorkingConditionService.RequireM1 createImp() {
		
		return new WorkingConditionService.RequireM1() {
			
			@Override
			public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
				return workingConditionItemRepo.getByHistoryId(historyId);
			}
			
			@Override
			public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
				return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
			}
		};
	}

}
