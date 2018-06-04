package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.service;

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
	private Double useDays;
	/**
	 * 付与前明細: 残数
	 */
	private Double beforeRemainDays;
	/**
	 * 付与前明細: 付与数
	 */
	private Double beforeGrantDays;
	/**
	 * 付与後明細: 残数
	 */
	private Optional<Double> afterRemainDays;
	/**
	 * 付与後明細: 付与数
	 */
	private Optional<Double> afterGrantDays;
}
