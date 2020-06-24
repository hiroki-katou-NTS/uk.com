package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.CarryForwardDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TotalRemainUndigestNumber.RemainUndigestResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

/**
 * @author ThanhNX
 *
 *         繰越数を計算する
 */
public class CalculateCarryForwardNumber {

	public static CarryForwardDayTimes process(Require require, String companyId, String employeeID, GeneralDate baseDate,
			List<AccumulationAbsenceDetail> lstAccuAbsence, boolean isMode) {
		// アルゴリズム「6.残数と未消化数を集計する」を実行
		RemainUndigestResult remainUndigestResult = TotalRemainUndigestNumber.process(require, companyId, employeeID,
				baseDate.addDays(-1), lstAccuAbsence, isMode);

		// 取得した「残日数」「残時間数」を返す
		return new CarryForwardDayTimes(remainUndigestResult.getRemainingDay(),
				remainUndigestResult.getRemainingTime());
	}
	
	public static interface Require extends TotalRemainUndigestNumber.Require{
		
	}

}
