package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 特別休暇の残数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RemainDaysOfSpecialHoliday {
	/**
	 * 使用数
	 */
	private double useDays;
	/**
	 * 付与前明細: 残数
	 */
	private double beforeRemainDays;
	/**
	 * 付与前明細: 付与数
	 */
	private double beforeGrantDays;
	/**
	 * 付与後明細: 残数
	 */
	private Optional<Double> afterRemainDays;
	/**
	 * 付与後明細: 付与数
	 */
	private Optional<Double> afterGrantDays;
}
