package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 特別休暇の利用情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeaveNumberInfoService {
	/**	残数 */
	private double remainDays;
	/**	使用数 */
	private double useDays;
	/**	付与数 */
	private double grantDays;
	/**	残時間数 */
	private Optional<Integer> remainTimes;
	/**	使用時間数 */
	private Optional<Integer> useTimes;
	/**	上限超過消滅日数 */
	private Optional<LimitTimeAndDays> limitDays;
	/**	付与時間数 */
	private Optional<Integer> grantTimes;
}
