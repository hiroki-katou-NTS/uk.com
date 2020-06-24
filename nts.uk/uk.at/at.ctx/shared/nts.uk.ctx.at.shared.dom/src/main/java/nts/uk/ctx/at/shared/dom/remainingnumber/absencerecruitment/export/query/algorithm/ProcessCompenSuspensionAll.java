package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         今から処理が必要な振出振休を全て集める
 */
public class ProcessCompenSuspensionAll {

	// 今から処理が必要な振出振休を全て集める
	public static List<AccumulationAbsenceDetail> process(Require require, AbsRecMngInPeriodRefactParamInput input) {

		// アルゴリズム「未相殺の振休(暫定)を取得する」を実行する
		List<AccumulationAbsenceDetail> lstAccAbsen = GetUnbalanceSuspensionTemporary.process(require, input);

		// アルゴリズム「未使用の振出(暫定)を取得する」を実行する
		List<AccumulationAbsenceDetail> lstAccAbsRec = GetUnusedCompenTemporary.process(require, input);

		lstAccAbsen.addAll(lstAccAbsRec);
		return lstAccAbsen;
	}

	public static interface Require extends GetUnbalanceSuspensionTemporary.Require, GetUnusedCompenTemporary.Require {

	}

}
