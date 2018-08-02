package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.MngHistDataAtr;
/**
 * 振休履歴
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceHistoryOutputPara {
	/**	状態 */
	private MngHistDataAtr createAtr;
	/**	振休データID */
	private String absId;
	/**	振休日 */
	private CompensatoryDayoffDate absDate;
	/**	必要日数 */
	private Double requeiredDays;
	/**	未相殺日数 */
	private Double unOffsetDays;
}
