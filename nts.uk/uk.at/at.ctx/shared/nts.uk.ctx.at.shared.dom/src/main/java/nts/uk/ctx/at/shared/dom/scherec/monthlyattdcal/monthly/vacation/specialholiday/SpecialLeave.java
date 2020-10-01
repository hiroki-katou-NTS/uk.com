package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 特別休暇
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeave extends DomainObject{
	/**
	 * 残数
	 */
	private SpecialLeaveRemain remain;
	/**
	 * 残数付与前
	 */
	private SpecialLeaveRemain beforeRemainGrant;
	/**
	 * 使用数
	 */
	private SpecialLeaveUseNumber useNumber;
	/**
	 * 未消化数
	 */
	private SpecialLeaveUnDigestion unDegestionNumber;
	/**
	 * 残数付与後
	 */
	private Optional<SpecialLeaveRemain> afterRemainGrant;
}
