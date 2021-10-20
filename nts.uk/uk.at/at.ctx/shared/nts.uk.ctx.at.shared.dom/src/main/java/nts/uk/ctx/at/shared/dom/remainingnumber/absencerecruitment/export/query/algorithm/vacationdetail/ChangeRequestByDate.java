package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         年月日の変更要求
 */
@AllArgsConstructor
@Getter
public class ChangeRequestByDate {

	// 年月日
	private final GeneralDate date;

	// 明細
	private final VacationDetails vacDetail;
}
