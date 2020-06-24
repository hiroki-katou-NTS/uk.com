package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.CarryForwardDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         集計開始時点の残数取得
 * 
 *         Refactor
 */
public class AcquisitionRemainNumAtStartCount {

	/**
	 * 集計開始時点の残数取得
	 * 
	 * Refactor
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param startDateAggr
	 */
	public static CarryForwardDayTimes acquisition(Require require, String companyId, String employeeId,
			GeneralDate startDateAggr, boolean isMode, List<AccumulationAbsenceDetail> lstAccuAbsenDetail) {

		// まだ余ってる代休を持ってくる
		List<AccumulationAbsenceDetail> lstAccmuAbsen = GetUnbalanceLeaveFixed.getUnbalanceUnused(require, companyId,
				employeeId, startDateAggr);
		lstAccuAbsenDetail.addAll(lstAccmuAbsen);
		// まだ余ってる休出を持ってくる
		List<AccumulationAbsenceDetail> lstLeaveOccurrDetail = GetUnusedLeaveFixed.getUnbalanceUnused(require,
				companyId, employeeId, startDateAggr);
		lstAccuAbsenDetail.addAll(lstLeaveOccurrDetail);

		// 代休、休出から月初の繰越数を計算
		return CalculateCarryForwardNumber.process(require, companyId, employeeId, startDateAggr, lstAccuAbsenDetail,
				isMode);

	}

	public static interface Require
			extends GetUnbalanceLeaveFixed.Require, GetUnusedLeaveFixed.Require, CalculateCarryForwardNumber.Require {

	}

}
