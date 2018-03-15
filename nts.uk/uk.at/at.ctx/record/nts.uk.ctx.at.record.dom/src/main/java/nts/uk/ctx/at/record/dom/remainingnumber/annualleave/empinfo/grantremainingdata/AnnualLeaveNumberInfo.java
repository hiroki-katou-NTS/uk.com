package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;

@Getter
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

}
