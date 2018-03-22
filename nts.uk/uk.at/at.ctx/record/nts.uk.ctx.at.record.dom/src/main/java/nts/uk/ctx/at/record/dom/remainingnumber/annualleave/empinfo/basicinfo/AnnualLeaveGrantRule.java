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
	
	public GeneralDate nextTimeGrantDate() {
		return GeneralDate.max();
	}
	
	public Double nextTimeGrantDays() {
		return 0d;
	}
	
	public int nextTimeMaxTime() {
		return 0;
	}
	
}
