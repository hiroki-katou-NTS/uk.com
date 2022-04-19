package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnLeaUseService;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnualLeaveUse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 年休取得義務
 */
@Getter
public class ObligedUseAnnualLeave {

	/** 前回の年休付与日数が指定した日数以上の人のみチェックする */
	private boolean onlyPreviousGrantDaysOver;

	/** 前回の年休付与日数 */
	private Optional<Integer> previousGrantDays;

	/** 前回付与までの期間が１年未満の場合、期間按分する */
	private boolean proportionalDistributionLessThanOneYear;

	/** 年休取得義務日数 */
	private AnnualLeaveUsedDayNumber obligedDays;

	@Inject
	private ObligedAnnLeaUseService obligedAnnLeaUseService;

	/**
	 * 取得義務を満たしているか
	 * @param employeeId
	 * @return
	 */
	public boolean check(Require require, String employeeId) {

		// 年休付与残数データの取得
		val annualLeaveGrantRemain = new ArrayList<AnnualLeaveGrantRemainingData>();
		if(onlyPreviousGrantDaysOver){
			// 指定した付与日数の付与残数データを取得する
			annualLeaveGrantRemain.addAll(require.findAnnualLeaveGrantRemain(employeeId,
					Optional.of(LeaveGrantNumber.of(new LeaveGrantDayNumber(previousGrantDays.get().doubleValue()), Optional.empty()))));
		}else{
			// すべての付与残数データを取得する
			annualLeaveGrantRemain.addAll(require.findAnnualLeaveGrantRemain(employeeId, Optional.empty()));
		}

		// 年休使用義務を生成
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(employeeId, proportionalDistributionLessThanOneYear, ReferenceAtr.APP_AND_SCHE, obligedDays, annualLeaveGrantRemain);

		// 年休使用義務日数の取得
		val optObligedUseDays = obligedAnnLeaUseService.getObligedUseDays(GeneralDate.today(), obligedAnnualLeaveUse);
		if(!optObligedUseDays.isPresent()){
			// 年休使用義務日数が取得できなかった場合はチェックしない
			return true;
		}

		// 義務日数計算期間内の年休使用数を取得
		val annualLeaveUsedDays = obligedAnnLeaUseService.getAnnualLeaveUsedDays(GeneralDate.today(), obligedAnnualLeaveUse);

		// 年休使用日数が使用義務日数を満たしているかチェックする
		return obligedAnnLeaUseService.checkObligedUseDays(optObligedUseDays.get(), annualLeaveUsedDays);
	}

	public interface Require {

		List<AnnualLeaveGrantRemainingData> findAnnualLeaveGrantRemain(String employeeId, Optional<LeaveGrantNumber> grantDays);

	}
}
