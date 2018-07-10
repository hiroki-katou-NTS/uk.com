package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 時間休暇使用情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VacationTimeInfor {
	/**	60H超休使用時間 */
	private Integer hChoukyuTime;
	/**	時間休種類 */
	private AppTimeType timeType;
	/**	時間代休使用時間 */
	private Integer kyukaTime;
	/**	時間年休使用時間 */
	private Integer nenkyuTime;
}
