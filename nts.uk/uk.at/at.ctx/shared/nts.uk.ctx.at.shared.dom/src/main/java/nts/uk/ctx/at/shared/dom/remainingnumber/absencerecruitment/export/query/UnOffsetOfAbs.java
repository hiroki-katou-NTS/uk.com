package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 振休の未相殺
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UnOffsetOfAbs {
	/**
	 * 振休データID
	 */
	private String absMngId;
	/**
	 * 必要日数
	 */
	private double requestDays;
	/**
	 * 未相殺日数
	 */
	private double unOffSetDays;
}
