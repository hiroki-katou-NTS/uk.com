package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
/**
 * 実特別休暇
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualSpecialLeave extends DomainObject{
	/**
	 * 残数
	 */
	private ActualSpecialLeaveRemain remain;
	/**
	 * 残数付与前
	 */
	private ActualSpecialLeaveRemain beforRemainGrant;
	/**
	 * 使用数
	 */
	private SpecialLeaveUseNumber useNumber;
	/**
	 * 残数付与後
	 */
	private Optional<ActualSpecialLeaveRemain> afterRemainGrant;
}
