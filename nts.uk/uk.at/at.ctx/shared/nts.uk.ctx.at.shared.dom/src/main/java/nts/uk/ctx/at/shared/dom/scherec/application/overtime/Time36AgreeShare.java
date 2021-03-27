package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
/**
 * 36協定時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Time36AgreeShare extends DomainObject {
	
	/*
	 * 申請時間
	 */
	private AttendanceTimeMonth applicationTime;
	
	/*
	 * 月間時間
	 */
	private Time36AgreeMonthShare agreeMonth;
	
	/*
	 * 年間時間
	 */
	private Time36AgreeAnnualShare agreeAnnual;
	
	public Time36AgreeShare(){
		this.applicationTime = new AttendanceTimeMonth(0);
		this.agreeMonth = new Time36AgreeMonthShare();
		this.agreeAnnual = new Time36AgreeAnnualShare();
	}
	
	public void updateAppTime(Integer appTime){
		this.applicationTime = new AttendanceTimeMonth(appTime);
	}
}
