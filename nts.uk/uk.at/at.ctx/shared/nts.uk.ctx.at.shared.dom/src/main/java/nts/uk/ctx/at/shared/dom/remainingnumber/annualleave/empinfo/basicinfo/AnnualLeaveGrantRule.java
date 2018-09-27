package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.PerServiceLengthTableCD;

@Getter
@Setter
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
	
	
	private GeneralDate nextGrantDate;
	
	private Double nextGrantDay;
	
	private Optional<Integer> nextMaxTime;
	
	
	public String nextTimeGrantDate() {
		if (nextGrantDate == null){
			return null;
		}
		return this.nextGrantDate.toString("yyyy/MM/dd");
	}
	
	public String nextTimeGrantDays() {
		if (nextGrantDay == null){
			return null;
		}
		return this.nextGrantDay +"日";
	}
	
	public String nextTimeMaxTime() {
		if (!nextMaxTime.isPresent()){
			return null;
		}
		Integer hours = nextMaxTime.get() / 60 ;
		Integer minute = nextMaxTime.get() % 60;
		
		return  ((hours == 0 && minute <0) ? ("-" + hours) : hours ) + ":" + (Math.abs(minute) < 10 ? ("0" + Math.abs(minute)) : (Math.abs(minute) + ""));
	}

	public AnnualLeaveGrantRule(PerServiceLengthTableCD grantTableCode, GeneralDate grantStandardDate) {
		super();
		this.grantTableCode = grantTableCode;
		this.grantStandardDate = grantStandardDate;
	}
	
	
	
}
