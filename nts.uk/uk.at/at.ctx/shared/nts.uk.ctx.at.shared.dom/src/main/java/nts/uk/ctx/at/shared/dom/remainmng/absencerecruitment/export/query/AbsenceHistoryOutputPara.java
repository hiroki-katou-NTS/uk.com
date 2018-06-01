package nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.CreaterAtr;
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
	private MngDataAtr createAtr;
	/**	振休データID */
	private String absId;
	/**	振休日 */
	private GeneralDate absDate;
	/**	必要日数 */
	private Double requeiredDays;
	/**	未相殺日数 */
	private Double unOffsetDays;
}
