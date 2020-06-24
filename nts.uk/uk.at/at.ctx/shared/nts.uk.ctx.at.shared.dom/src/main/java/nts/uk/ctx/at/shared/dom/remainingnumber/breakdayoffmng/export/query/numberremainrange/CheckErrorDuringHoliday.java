package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;

/**
 * @author ThanhNX
 *
 *         代休のエラーチェックをする
 */
public class CheckErrorDuringHoliday {

	public static void check(SubstituteHolidayAggrResult result) {

		List<DayOffError> lstError = new ArrayList<>();
		if (result.getRemainDay().v() < 0) {
			lstError.add(DayOffError.DAYERROR);
		}
		if (result.getRemainTime().v() < 0) {
			lstError.add(DayOffError.TIMEERROR);
		}

		result.setDayOffErrors(lstError);
	}

}
