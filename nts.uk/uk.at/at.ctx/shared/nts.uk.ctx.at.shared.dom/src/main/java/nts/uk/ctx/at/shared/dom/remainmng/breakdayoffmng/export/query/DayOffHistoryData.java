package nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.export.query.MngDataAtr;

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
	private MngDataAtr createAtr;
	/**
	 * 代休データID
	 */
	private String dayOffId;
	/**
	 * 代休日
	 */
	private GeneralDate dayOffDate;
	/**
	 * 必要日数
	 */
	private Double requeiredDays;
	/**
	 * 未相殺日数
	 */
	private Double unOffsetDays;
}
