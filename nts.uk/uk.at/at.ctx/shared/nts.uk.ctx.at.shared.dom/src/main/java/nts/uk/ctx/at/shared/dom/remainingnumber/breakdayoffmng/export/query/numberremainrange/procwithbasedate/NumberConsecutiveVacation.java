package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.procwithbasedate.MonthVacationRemainDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;

/**
 * @author thanh_nx
 *
 *         逐次発生の休暇の休暇数
 */
@AllArgsConstructor
@Data
public class NumberConsecutiveVacation {

	// 残日数
	private MonthVacationRemainDays days;

	// 残時間
	private RemainingMinutes remainTime;

}
