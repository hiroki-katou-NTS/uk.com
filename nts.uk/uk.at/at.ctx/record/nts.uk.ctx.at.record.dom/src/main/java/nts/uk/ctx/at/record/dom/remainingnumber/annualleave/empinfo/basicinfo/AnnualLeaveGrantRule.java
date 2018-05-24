package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.PerServiceLengthTableCD;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveGrantRule {
	
	/**
	 * 付与テーブルコード
	 */
	private PerServiceLengthTableCD grantTableCode;
	
	/**
	 * 付与基準日
	 */
	private GeneralDate grantStandardDate;
	
	public String nextTimeGrantDate() {
		return GeneralDate.max().toString();
	}
	
	public String nextTimeGrantDays() {
		return "0.0日";
	}
	
	public String nextTimeMaxTime() {
		return "0:00";
	}
	
}
