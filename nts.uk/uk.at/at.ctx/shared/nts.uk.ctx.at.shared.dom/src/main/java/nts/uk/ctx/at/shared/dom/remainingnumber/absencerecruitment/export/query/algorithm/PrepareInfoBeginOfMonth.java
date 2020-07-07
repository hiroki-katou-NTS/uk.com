package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         月初時点の情報を整える
 */
public class PrepareInfoBeginOfMonth {

	private PrepareInfoBeginOfMonth() {
	};

	// 月初時点の情報を整える
	public static AbsDaysRemain prepare(Require require, String companyId, String employeeId, GeneralDate ymd,
			boolean isMode, List<AccumulationAbsenceDetail> result) {

		// アルゴリズム「未相殺の振休(確定)を取得する」を実行する
		result.addAll(GetUnbalanceSuspension.process(require, companyId, employeeId, ymd));

		// アルゴリズム「未使用の振出(確定)を取得する」を実行する
		result.addAll(GetUnusedCompen.process(require, companyId, employeeId, ymd));

		// 繰越数を計算する
		return CalcCarryForwardNumber.calc(result, ymd, isMode);

	}

	public static interface Require extends GetUnbalanceSuspension.Require, GetUnusedCompen.Require {

	}
}
