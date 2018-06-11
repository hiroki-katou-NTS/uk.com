package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;
/**
 * 振出の未使用
 * @author do_dt
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.HolidayAtr;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnUseOfRec {
	/**
	 * 使用期限日
	 */
	private GeneralDate expirationDate;
	/**
	 * 振出データID
	 */
	private String recMngId;
	/**
	 * 発生日数
	 */
	private double occurrenceDays;
	/**
	 * 法定内外区分
	 */
	private HolidayAtr statutoryAtr;
	/**
	 * 未使用日数
	 */
	private double unUseDays;
	
}
