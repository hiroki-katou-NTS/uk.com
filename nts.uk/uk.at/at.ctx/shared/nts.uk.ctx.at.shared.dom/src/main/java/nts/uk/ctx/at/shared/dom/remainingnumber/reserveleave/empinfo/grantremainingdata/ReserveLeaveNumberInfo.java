package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;

@Getter
public class ReserveLeaveNumberInfo extends LeaveNumberInfo implements Serializable {

//	/**
//	 * 付与数
//	 */
//	private ReserveLeaveGrantDayNumber grantNumber;
//
//	/**
//	 * 使用数
//	 */
//	private ReserveLeaveUsedNumber usedNumber;
//
//	/**
//	 * 残数
//	 */
//	@Setter
//	private ReserveLeaveRemainingDayNumber remainingNumber;

	private static final long serialVersionUID = 1L;

	public ReserveLeaveNumberInfo(
			double grantDays,
			Integer grantMinutes,
			double usedDays,
			Integer usedMinutes,
			Double stowageDays,
			Double numberOverDays,
			Integer timeOver,
			double remainDays,
			Integer remainMinutes,
			double usedPercent) {

		super(grantDays, grantMinutes, usedDays, usedMinutes,
			stowageDays, numberOverDays, timeOver, remainDays, remainMinutes,usedPercent);
	}

	public ReserveLeaveNumberInfo(double grantDays, double usedDays,
			Double overLimitDays, double remainDays) {
		this.grantNumber = LeaveGrantNumber.createFromJavaType(grantDays, 0);
		this.usedNumber = new LeaveUsedNumber(usedDays, 0);
		this.remainingNumber = new LeaveRemainingNumber(remainDays, 0);
	}

//	/**
//	 * 使用数に日数を加算する
//	 * @param days　日数
//	 */
//	// 2018.7.15 add shuichi_ishida
//	public void addDaysToUsedNumber(double days){
//		double usedDays = this.usedNumber.getDays().v() + days;
//		Double overLimitDays = null;
//		if (this.usedNumber.getOverLimitDays().isPresent()) overLimitDays = this.usedNumber.getOverLimitDays().get().v();
//		this.usedNumber = new ReserveLeaveUsedNumber(usedDays, overLimitDays);
//	}
//
//	/**
//	 * 残日数に日数を加算する
//	 * @param days　日数
//	 */
//	// 2018.7.15 add shuichi_ishida
//	public void addDaysToRemainingNumber(double days){
//		this.remainingNumber = new LeaveRemainingNumber(this.remainingNumber.v() + days);
//	}
//
//	/**
//	 * 上限超過消滅日数に日数を加算する
//	 * @param days　日数
//	 */
//	// 2018.7.15 add shuichi_ishida
//	public void addDaysToOverLimitDays(double days){
//		double overLimitDays = 0.0;
//		if (this.usedNumber.getOverLimitDays().isPresent()) {
//			overLimitDays += this.usedNumber.getOverLimitDays().get().v();
//		}
//		overLimitDays += days;
//		this.usedNumber = new ReserveLeaveUsedNumber(this.usedNumber.getDays().v(), overLimitDays);
//	}
}