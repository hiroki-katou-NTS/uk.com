package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseTimes;

@Getter
@Setter
// 特別休暇使用数
public class SpecialLeaveUsedNumber{

	/**
	 * 使用日数
	 */
	private SpecialLeaveUseDays useDays;

	/**
	 * 使用時間
	 */
	private Optional<SpecialLeaveUseTimes> useTimes;
	
	/**
	 * 積み崩し日数
	 */
	private Optional<SpecialLeaveUseDays> useSavingDay;
	
	
	/**
	 * 積み崩し日数
	 */
	private Optional<SpecialLeaveOverNumber> specialLeaveOverLimitNumber;
	

	public SpecialLeaveUsedNumber() {
		useDays = new SpecialLeaveUseDays(0.0);
		useTimes = Optional.empty();
		this.useSavingDay = Optional.empty();
		this.specialLeaveOverLimitNumber = Optional.empty();
	}

}
