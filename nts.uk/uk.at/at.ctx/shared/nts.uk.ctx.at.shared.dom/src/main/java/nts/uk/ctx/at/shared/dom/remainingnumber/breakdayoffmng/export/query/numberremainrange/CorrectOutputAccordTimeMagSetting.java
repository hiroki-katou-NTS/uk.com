package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;

/**
 * @author thanh_nx
 *
 *         時間管理の設定に従って、outputの値を補正する
 */
public class CorrectOutputAccordTimeMagSetting {

	public static SubstituteHolidayAggrResult correct(Require require, String companyId,
			SubstituteHolidayAggrResult result) {

		//requireから「代休管理設定」を取得
		val setting = require.findComLeavComSet(companyId);

		//管理区分を確認して補正する。
		if (setting.getCompensatoryDigestiveTimeUnit().getIsManageByTime() == ManageDistinct.YES) {
			result.removeAllDayValue();
		} else {
			result.removeAllTimeValue();
		}
		return result;
	}

	public static interface Require {
		// CompensLeaveComSetRepository.find
		public CompensatoryLeaveComSetting findComLeavComSet(String companyId);
	}
}
