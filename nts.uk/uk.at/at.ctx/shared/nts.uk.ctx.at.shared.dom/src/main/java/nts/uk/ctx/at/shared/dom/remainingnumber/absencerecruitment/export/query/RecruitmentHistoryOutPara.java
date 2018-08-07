package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.MngHistDataAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
/**
 * 振出履歴
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecruitmentHistoryOutPara {
	/**使用期限日	 */
	private GeneralDate expirationDate;
	/**消滅済	 */
	private boolean chkDisappeared;
	/**	状態 */
	private MngHistDataAtr dataAtr;
	/**	振出データID */
	private String recId;
	/**	振出日 */
	private CompensatoryDayoffDate recDate;
	/**発生日数	 */
	private Double occurrenceDays;
	/**法定内外区分	 */
	private int holidayAtr;
	/**	未使用日数 */
	private Double unUseDays;
	
}
