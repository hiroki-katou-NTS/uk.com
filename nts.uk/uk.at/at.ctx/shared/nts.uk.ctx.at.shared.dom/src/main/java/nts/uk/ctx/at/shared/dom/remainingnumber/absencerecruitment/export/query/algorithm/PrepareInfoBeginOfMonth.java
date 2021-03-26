package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.AccumulationAbsenceDetailComparator;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

/**
 * @author ThanhNX
 *
 *         月初時点の情報を整える
 */
public class PrepareInfoBeginOfMonth {

	private PrepareInfoBeginOfMonth() {
	};

	// 月初時点の情報を整える
	public static AbsDaysRemain prepare(Require require, String companyId, String employeeId, GeneralDate startDate,
			GeneralDate endDate, boolean isMode, List<AccumulationAbsenceDetail> result,
			FixedManagementDataMonth fixManaDataMonth) {

		// アルゴリズム「未相殺の振休(確定)を取得する」を実行する
		result.addAll(GetUnbalanceSuspension.process(require, companyId, employeeId, startDate, fixManaDataMonth));

		// アルゴリズム「未使用の振出(確定)を取得する」を実行する
		result.addAll(GetUnusedCompen.process(require, companyId, employeeId, startDate));

		// 「逐次発生の休暇明細」をソートする
		result.sort(new AccumulationAbsenceDetailComparator());

		// 月初時点での相殺処理
		CompenSuspensionOffsetProcess.process(require, companyId, employeeId, endDate, result);

		// 繰越数を計算する
		return CalcCarryForwardNumber.calc(result, startDate, isMode);

	}

	public static interface Require
			extends GetUnbalanceSuspension.Require, GetUnusedCompen.Require, CompenSuspensionOffsetProcess.Require {

	}
}
