package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;

/**
 * @author ThanhNX
 *
 *         暫定データを取得
 */
public class GetTemporaryData {

	private GetTemporaryData() {
	};

	// 暫定データを取得
	public static List<AccumulationAbsenceDetail> process(Require require, BreakDayOffRemainMngRefactParam param) {

		// 当月以降の消化を取得
		List<AccumulationAbsenceDetail> lstAccuAbsenceDetail = GetUnbalancedLeaveTemporary.process(require, param);

		// 当月以降の発生を取得
		lstAccuAbsenceDetail.addAll(GetUnusedLeaveTemporary.process(require, param));

		return lstAccuAbsenceDetail;

	}

	public static interface Require extends GetUnbalancedLeaveTemporary.Require, GetUnusedLeaveTemporary.Require {

	}

}
