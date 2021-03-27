package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
/**
 * 36協定年間時間
 * @author thanhnx
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Time36AgreeAnnualShare {
	
	/*
	 * 実績時間
	 */
	private AttendanceTimeYear actualTime;
	
	/*
	 * 限度時間
	 */
	private AgreementOneYearTime limitTime;
	
	public Time36AgreeAnnualShare(){
		this.actualTime = new AttendanceTimeYear(0);
		this.limitTime = new AgreementOneYearTime(0);
	}
	
}
