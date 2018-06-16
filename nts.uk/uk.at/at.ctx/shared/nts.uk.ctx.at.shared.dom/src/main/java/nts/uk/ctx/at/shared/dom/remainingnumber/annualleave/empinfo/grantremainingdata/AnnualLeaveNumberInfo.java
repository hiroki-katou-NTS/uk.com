package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;

@Getter
@AllArgsConstructor
public class AnnualLeaveNumberInfo {

	/**
	 * 付与数
	 */
	private AnnualLeaveGrantNumber grantNumber;

	/**
	 * 使用数
	 */
	private AnnualLeaveUsedNumber usedNumber;

	/**
	 * 残数
	 */
	@Setter
	private AnnualLeaveRemainingNumber remainingNumber;

	/**
	 * 使用率
	 */
	private AnnualLeaveUsedPercent usedPercent;

	public AnnualLeaveNumberInfo(){
		this.grantNumber = AnnualLeaveGrantNumber.createFromJavaType(0.0, null);
		this.usedNumber = AnnualLeaveUsedNumber.createFromJavaType(0.0, null, null);
		this.remainingNumber = AnnualLeaveRemainingNumber.createFromJavaType(0.0, null);
		this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(0));
	}
	
	public AnnualLeaveNumberInfo(double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes,
			Double stowageDays, double remainDays, Integer remainMinutes, double usedPercent) {
		this.grantNumber = AnnualLeaveGrantNumber.createFromJavaType(grantDays, grantMinutes);
		this.usedNumber = AnnualLeaveUsedNumber.createFromJavaType(usedDays, usedMinutes, stowageDays);
		this.remainingNumber = AnnualLeaveRemainingNumber.createFromJavaType(remainDays, remainMinutes);
		this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(0));
		if (grantDays != 0){
			String usedPer = new DecimalFormat("#.#").format(usedDays/grantDays);
			this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(usedPer));
		}
	}

	/**
	 * 付与数を設定する
	 * @param setValue 付与数
	 */
	// 2018.4.23 add shuichi_ishida
	public void setGrantNumber(AnnualLeaveGrantNumber setValue){
		this.grantNumber = setValue;
		val grantDays = this.grantNumber.getDays().v();
		val usedDays = this.usedNumber.getDays().v();
		if (grantDays != 0){
			String usedPer = new DecimalFormat("#.##").format(usedDays/grantDays);
			this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(usedPer));
		}
	}
	
	/**
	 * 使用数を設定する
	 * @param setValue 使用数
	 */
	// 2018.4.23 add shuichi_ishida
	public void setUsedNumber(AnnualLeaveUsedNumber setValue){
		this.usedNumber = setValue;
		val grantDays = this.grantNumber.getDays().v();
		val usedDays = this.usedNumber.getDays().v();
		if (grantDays != 0){
			String usedPer = new DecimalFormat("#.##").format(usedDays/grantDays);
			this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(usedPer));
		}
	}
}
