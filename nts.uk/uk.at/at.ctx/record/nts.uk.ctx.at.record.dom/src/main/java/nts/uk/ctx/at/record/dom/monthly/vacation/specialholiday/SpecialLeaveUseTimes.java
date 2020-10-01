package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;
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

/**
 * 未使用　神野
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeaveUseTimes extends DomainObject{

//	/**
//	 * 使用回数
//	 */
//	private UseNumber useNumber;
	/**
	 * 使用時間
	 */
	private SpecialLeavaRemainTime useTimes;
	
//	/**
//	 * 使用時間付与前
//	 */
//	private SpecialLeavaRemainTime beforeUseGrantTimes;
//	/**
//	 * 使用時間付与後
//	 */
//	private Optional<SpecialLeavaRemainTime> afterUseGrantTimes;
	
	/**
	 * 日数を使用日数に加算する
	 */
	public void addUseTimes(SpecialLeaveUseTimes useTimes){
		this.useTimes.addMinutes(useTimes.getUseTimes().v());
	}
	
	/**
	 * 使用時間を加算する
	 */
	public void addUsedNumber(SpecialLeaveUseTimes useTimes){
		this.useTimes.addMinutes(useTimes.getUseTimes().v());		
	}
	
	@Override
	public SpecialLeaveUseTimes clone() {
		SpecialLeaveUseTimes cloned = new SpecialLeaveUseTimes();
		try {
			cloned.useTimes = this.useTimes.clone();
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveUseTimes clone error.");
		}
		return cloned;
	}
	
}
