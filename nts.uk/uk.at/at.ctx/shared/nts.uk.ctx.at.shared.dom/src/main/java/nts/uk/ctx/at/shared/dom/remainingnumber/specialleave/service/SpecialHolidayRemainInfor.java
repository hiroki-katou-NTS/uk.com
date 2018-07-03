package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 特別休暇の残数情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialHolidayRemainInfor {
	/**	残数 */
	private double remainDays;
	/**	使用数 */
	private double useDays;
	/**	付与数 */
	private double grantDays;
}
