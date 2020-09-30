package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
