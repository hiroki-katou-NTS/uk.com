package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 特別休暇使用日数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialLeaveUseDays extends DomainObject {
	/**
	 * 使用日数
	 */
	private SpecialLeaveRemainDay useDays;
	/**
	 * 使用日数付与前
	 */
	private SpecialLeaveRemainDay beforeUseGrantDays;
	/**
	 * 使用日数付与後
	 */
	private Optional<SpecialLeaveRemainDay> afterUseGrantDays;
	
}
