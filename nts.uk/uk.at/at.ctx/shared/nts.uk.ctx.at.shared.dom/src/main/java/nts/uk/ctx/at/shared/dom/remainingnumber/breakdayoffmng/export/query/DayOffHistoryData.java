package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;

/**
 * 代休履歴
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffHistoryData {
	/**	状態 */
	private MngHistDataAtr createAtr;
	/**
	 * 代休データID
	 */
	private String dayOffId;
	/**
	 * 代休日
	 */
	private CompensatoryDayoffDate dayOffDate;
	/**
	 * 必要日数
	 */
	private Double requeiredDays;
	/**
	 * 未相殺日数
	 */
	private Double unOffsetDays;
}
