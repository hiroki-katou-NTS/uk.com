package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

@Getter
public class ReserveLeaveNumberInfo {
	
	/**
	 * 付与日数
	 */
	private ReserveLeaveGrantDayNumber grantNumber;
	
	/**
	 * 使用数
	 */
	private ReserveLeaveUsedNumber usedNumber;
	
	/**
	 * 残日数
	 */
	@Setter
	private ReserveLeaveRemainingDayNumber remainingNumber;
	
	public ReserveLeaveNumberInfo(double grantDays, double usedDays,
			Double overLimitDays, double remainDays) {
		this.grantNumber = new ReserveLeaveGrantDayNumber(grantDays);
		this.usedNumber = new ReserveLeaveUsedNumber(usedDays, overLimitDays);
		this.remainingNumber = new ReserveLeaveRemainingDayNumber(remainDays);
	}

	/**
	 * 使用数に日数を加算する
	 * @param days　日数
	 */
	// 2018.7.15 add shuichi_ishida
	public void addDaysToUsedNumber(double days){
		double usedDays = this.usedNumber.getDays().v() + days;
		Double overLimitDays = null;
		if (this.usedNumber.getOverLimitDays().isPresent()) overLimitDays = this.usedNumber.getOverLimitDays().get().v();
		this.usedNumber = new ReserveLeaveUsedNumber(usedDays, overLimitDays);
	}
	
	/**
	 * 残日数に日数を加算する
	 * @param days　日数
	 */
	// 2018.7.15 add shuichi_ishida
	public void addDaysToRemainingNumber(double days){
		this.remainingNumber = new ReserveLeaveRemainingDayNumber(this.remainingNumber.v() + days);
	}
	
	/**
	 * 上限超過消滅日数に日数を加算する
	 * @param days　日数
	 */
	// 2018.7.15 add shuichi_ishida
	public void addDaysToOverLimitDays(double days){
		double overLimitDays = 0.0;
		if (this.usedNumber.getOverLimitDays().isPresent()) {
			overLimitDays += this.usedNumber.getOverLimitDays().get().v();
		}
		overLimitDays += days;
		this.usedNumber = new ReserveLeaveUsedNumber(this.usedNumber.getDays().v(), overLimitDays);
	}
}