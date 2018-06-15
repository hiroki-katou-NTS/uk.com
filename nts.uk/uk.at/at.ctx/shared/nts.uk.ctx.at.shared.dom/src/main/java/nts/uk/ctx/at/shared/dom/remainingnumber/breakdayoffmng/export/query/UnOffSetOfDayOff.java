package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代休の未相殺
 * @author do_dt
 *
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UnOffSetOfDayOff {
	/**
	 * 代休データID
	 */
	private String dayOffId;
	/**
	 * 必要時間数
	 */
	private Integer requiredTime;
	/**必要日数	 */
	private double requiredDay;
	/**	未相殺時間 */
	private Integer unOffsetTimes;
	/**	未相殺日数 */
	private double unOffsetDay;
}
