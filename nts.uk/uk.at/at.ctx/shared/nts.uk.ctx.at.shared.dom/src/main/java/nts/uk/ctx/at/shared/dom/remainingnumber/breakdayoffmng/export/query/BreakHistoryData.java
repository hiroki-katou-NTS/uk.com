package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;
/**
 * 休出履歴
 * @author do_dt
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BreakHistoryData {
	/**	休出データID */
	private String breakMngId;
	/**
	 * 休出日
	 */
	private CompensatoryDayoffDate breakDate;
	/**
	 * 使用期限日
	 */
	private GeneralDate expirationDate;
	/**
	 * 消滅済
	 */
	private boolean chkDisappeared;
	/**
	 *  管理データ状態区分
	 */
	private MngHistDataAtr mngAtr;
	/**
	 * 発生日数
	 */
	private Double occurrenceDays;
	/**
	 * 未使用日数
	 */
	private Double unUseDays;
}
