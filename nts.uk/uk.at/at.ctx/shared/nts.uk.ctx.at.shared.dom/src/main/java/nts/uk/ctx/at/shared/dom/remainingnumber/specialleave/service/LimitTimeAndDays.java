package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 特別休暇上限超過消滅数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LimitTimeAndDays {
	/**
	 * 日数
	 */
	private double days;
	/**
	 * 時間
	 */
	private Optional<Integer> times;
}
