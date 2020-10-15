package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 特別休暇残数
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SpecialLeaveRemain {
	/**
	 * 日数
	 */
	private SpecialLeaveRemainDay days;
	/**
	 * 時間
	 */
	private Optional<SpecialLeavaRemainTime> time;
}
