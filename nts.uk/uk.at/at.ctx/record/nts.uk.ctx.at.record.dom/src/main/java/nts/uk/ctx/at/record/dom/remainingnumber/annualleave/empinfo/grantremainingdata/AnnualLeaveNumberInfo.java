package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
	private AnnualLeaveRemainingNumber remainingNumber;

	/**
	 * 使用率
	 */
	private AnnualLeaveUsedPercent usedPercent;

	public AnnualLeaveNumberInfo(double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes,
			Double stowageDays, double remainDays, Integer remainMinutes, double usedPercent) {
		this.grantNumber = AnnualLeaveGrantNumber.createFromJavaType(grantDays, grantMinutes);
		this.usedNumber = AnnualLeaveUsedNumber.createFromJavaType(usedDays, usedMinutes, stowageDays);
		this.remainingNumber = AnnualLeaveRemainingNumber.createFromJavaType(remainDays, remainMinutes);
		this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(0));
		if (grantDays != 0){
			String usedPer = new DecimalFormat("#.##").format(usedDays/grantDays);
			this.usedPercent = new AnnualLeaveUsedPercent(new BigDecimal(usedPer));
		}
		
	}

}
