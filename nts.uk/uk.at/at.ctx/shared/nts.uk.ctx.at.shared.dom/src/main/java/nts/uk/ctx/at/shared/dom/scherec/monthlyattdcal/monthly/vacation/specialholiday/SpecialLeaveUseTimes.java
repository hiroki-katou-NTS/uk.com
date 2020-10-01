package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;
/**
 * 特別休暇使用時間
 * @author do_dt
 *
 */

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeaveUseTimes extends DomainObject{
	/**
	 * 使用回数
	 */
	private UseNumber useNumber;
	/**
	 * 使用時間
	 */
	private SpecialLeavaRemainTime useTimes;
	/**
	 * 使用時間付与前
	 */
	private SpecialLeavaRemainTime beforeUseGrantTimes;
	/**
	 * 使用時間付与後
	 */
	private Optional<SpecialLeavaRemainTime> afterUseGrantTimes;
}
