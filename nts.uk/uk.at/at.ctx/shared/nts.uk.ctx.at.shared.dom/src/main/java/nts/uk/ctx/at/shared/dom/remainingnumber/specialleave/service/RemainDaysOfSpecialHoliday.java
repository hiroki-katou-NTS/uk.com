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
	 * 付与前明細
	 */
	private SpecialHolidayRemainInfor grantDetailBefore;
	/**
	 * 未消化数
	 */
	private double unDisgesteDays;
	/**
	 * 付与後明細
	 */
	private Optional<SpecialHolidayRemainInfor> grantDetailAfter;
	
}
