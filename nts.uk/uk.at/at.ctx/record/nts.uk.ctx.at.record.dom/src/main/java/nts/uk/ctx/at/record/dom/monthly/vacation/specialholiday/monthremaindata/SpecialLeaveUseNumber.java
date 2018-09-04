package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 特別休暇使用数
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUseNumber extends DomainObject {
	/**
	 * 使用日数
	 */
	private SpecialLeaveUseDays useDays;
	/**
	 * 使用時間
	 */
	private Optional<SpecialLeaveUseTimes> useTimes;
	
	public SpecialLeaveUseNumber(SpecialLeaveUseDays useDays, SpecialLeaveUseTimes useTimes) {
		this(useDays, Optional.ofNullable(useTimes));
	}
}
