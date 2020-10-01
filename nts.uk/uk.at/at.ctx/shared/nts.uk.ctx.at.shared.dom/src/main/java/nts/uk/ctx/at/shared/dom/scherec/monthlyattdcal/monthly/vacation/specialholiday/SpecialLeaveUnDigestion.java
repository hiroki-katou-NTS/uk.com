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
//@AllArgsConstructor
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
	
//	/**
//	 * コンストラクタ
//	 * @param days_in
//	 * @param times_in
//	 */
//	public SpecialLeaveUnDigestion(
//			SpecialLeaveRemainDay days_in, Optional<SpecialLeavaRemainTime> times_in){
//		days = days_in;
//		times = times_in;
//	}
}
