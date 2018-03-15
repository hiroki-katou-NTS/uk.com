package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.PerServiceLengthTableCD;

@Getter
public class AnnualLeaveGrantRule {
	
	/**
	 * 付与テーブルコード
	 */
	private PerServiceLengthTableCD grantTableCode;
	
	/**
	 * 付与基準日
	 */
	private GeneralDate grantStandardDate;
	
}
