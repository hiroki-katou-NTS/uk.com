package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.functionalgorithm.setautomatic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.functionalgorithm.setautomatic.AddTimeZoneToAttendance.AttLeavAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * @author thanh_nx
 *
 *         出勤系時刻を丸める
 */
@Stateless
public class RoundAttendanceTime {

	@Inject
	private GetCommonSet getCommonSet;

	public int process(String companyId, int time, String workTimeCode, AttLeavAtr atr) {

		// クラス「就業時間帯の共通設定」を確認する (Xác nhận class 「就業時間帯の共通設定」)
		Optional<WorkTimezoneCommonSet> commonSet = Optional.empty();// getCommonSet.get(companyId, workTimeCode);

		if (!commonSet.isPresent())
			return time;
		// 【パラメータ】時刻を丸める (param thời gian làm tròn)
		// 出勤
		RoundingSet atendanceRoundingSet = commonSet.get().getStampSet().getRoundingSets().stream().filter(item -> item
				.getSection() == (atr == AttLeavAtr.GOING_TO_WORK ? Superiority.ATTENDANCE : Superiority.OFFICE_WORK))
				.findFirst().orElse(null);

		int attendanceTimeAfterRouding = atendanceRoundingSet != null
				? this.roudingTime(time, atendanceRoundingSet.getRoundingSet().getFontRearSection().value,
						new Integer(atendanceRoundingSet.getRoundingSet().getRoundingTimeUnit().description).intValue())
				: time;

		// 【返却】丸め後の時刻 ← 丸めた時刻 (thời gian đã làm tròn)
		return attendanceTimeAfterRouding;

	}

	private int roudingTime(int time, int fontRearSection, int roundingTimeUnit) {

		BigDecimal result = new BigDecimal(time).divide(new BigDecimal(roundingTimeUnit));

		if (!(result.signum() == 0 || result.scale() <= 0 || result.stripTrailingZeros().scale() <= 0)) {
			if (fontRearSection == 0) {
				result = result.setScale(0, RoundingMode.DOWN);
			} else if (fontRearSection == 1) {
				result = result.setScale(0, RoundingMode.UP);
				;
			}
		}
		return result.multiply(new BigDecimal(roundingTimeUnit)).intValue();
	}
}
