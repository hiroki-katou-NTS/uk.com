package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代休残数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemainUnDigestedDayTimes {
	/**	残時間 */
	private Integer remainTimes;
	/**	残日数 */
	private double remainDays;
	/**	未消化時間 */
	private Integer unDigestedTimes;
	/**	未消化日数 */
	private double unDigestedDays;
}
