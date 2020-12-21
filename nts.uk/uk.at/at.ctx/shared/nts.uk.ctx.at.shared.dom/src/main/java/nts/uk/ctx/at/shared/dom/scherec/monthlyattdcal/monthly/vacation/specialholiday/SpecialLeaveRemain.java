package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 特別休暇残数
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SpecialLeaveRemain {
	/**
	 * 日数
	 */
	private SpecialLeaveRemainDay days;
	/**
	 * 時間
	 */
	private Optional<SpecialLeavaRemainTime> time;
	
	/**
	 * データをクリア
	 */
	public void clear(){
		days = new SpecialLeaveRemainDay(0.0);
		time = Optional.empty();
	}
	
	/**
	 * クローン
	 */
	@Override
	public SpecialLeaveRemain clone() {
		SpecialLeaveRemain cloned = new SpecialLeaveRemain();
		try {
			cloned.days = new SpecialLeaveRemainDay(days.v());
			if (time.isPresent()){
				cloned.time = Optional.of(new SpecialLeavaRemainTime(time.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveUsedInfo clone error.");
		}
		return cloned;
	}
}
