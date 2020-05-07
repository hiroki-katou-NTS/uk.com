package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public abstract class LeaveNumberInfo {

	/**
	 * 付与数
	 */
	protected LeaveGrantNumber grantNumber;

	/**
	 * 使用数
	 */
	@Setter
	protected LeaveUsedNumber usedNumber;

	/**
	 * 残数
	 */
	@Setter
	protected LeaveRemainingNumber remainingNumber;
	
	/**
	 * 使用率
	 */
	protected LeaveUsedPercent usedPercent;

	public LeaveNumberInfo(){
//		this.grantNumber = LeaveGrantNumber.createFromJavaType(0.0, null);
//		this.usedNumber = LeaveUsedNumber.createFromJavaType(0.0, null, null);
//		this.remainingNumber = LeaveRemainingNumber.createFromJavaType(0.0, null);
//		this.usedPercent = new LeaveUsedPercent(new BigDecimal(0));
	}
	
	public LeaveNumberInfo(double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes,
			Double stowageDays, double remainDays, Integer remainMinutes, double usedPercent) {
//		this.grantNumber = LeaveGrantNumber.createFromJavaType(grantDays, grantMinutes);
//		this.usedNumber = LeaveUsedNumber.createFromJavaType(usedDays, usedMinutes, stowageDays);
//		this.remainingNumber = LeaveRemainingNumber.createFromJavaType(remainDays, remainMinutes);
//		this.usedPercent = new LeaveUsedPercent(new BigDecimal(0));
//		if (grantDays != 0){
//			String usedPer = new DecimalFormat("#.#").format(usedDays/grantDays);
//			this.usedPercent = new LeaveUsedPercent(new BigDecimal(usedPer));
//		}
	}
}
