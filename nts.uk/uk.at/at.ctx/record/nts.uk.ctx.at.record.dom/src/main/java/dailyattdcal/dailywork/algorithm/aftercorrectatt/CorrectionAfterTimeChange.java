package dailyattdcal.dailywork.algorithm.aftercorrectatt;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dailyattdcal.dailywork.workinfo.algorithm.CorrectWorkTimeByWorkType;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;

/**
 * @author ThanhNX
 *
 *         勤怠変更後の補正（日別実績の補正処理）
 */
@Stateless
public class CorrectionAfterTimeChange {

	@Inject
	private WorkingConditionService workingConditionService;

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

		String employeeId = domainDaily.getWorkInformation().getEmployeeId();

		GeneralDate date = domainDaily.getWorkInformation().getYmd();

		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workCondOpt = workingConditionService.findWorkConditionByEmployee(employeeId,
				date);
		if (!workCondOpt.isPresent())
			return domainDaily;

		// 勤務種類に応じて就業時間帯の補正を行う
		correctWorkTimeByWorkType.correct(workCondOpt.get(), domainDaily.getWorkInformation());

		// 自動打刻セットの時間帯補正
		//// 直行直帰による、戻り時刻補正
		domainDaily = autoCorrectStamp.autoCorrect(companyId, domainDaily, workCondOpt.get());

		// 矛盾した時刻をクリアする
		clearConflictTimeWithDay.clear(companyId, workCondOpt.get(), domainDaily.getWorkInformation(),
				domainDaily.getAttendanceLeave());

		// TODO: 休憩時間帯をセットし直す dang thiet ke 設計中
		// correctRestTime.correct(domainDaily);

		// 矛盾した時間をクリアする
		clearConflictTime.process(domainDaily);

		return domainDaily;
	}

}
