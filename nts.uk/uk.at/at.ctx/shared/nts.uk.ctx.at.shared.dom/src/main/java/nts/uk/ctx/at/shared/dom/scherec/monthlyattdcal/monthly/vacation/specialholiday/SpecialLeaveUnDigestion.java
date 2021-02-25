package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
/**
 * 特別休暇未消化数
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUnDigestion extends DomainObject{
	/**
	 * 日数
	 */
	private SpecialLeaveRemainDay days;
	/**
	 * 時間
	 */
	private Optional<SpecialLeavaRemainTime> times;
}
