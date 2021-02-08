package nts.uk.ctx.at.request.dom.application.overtime.time36;

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
public class Time36Agree extends DomainObject {
	
	/*
	 * 申請時間
	 */
	private AttendanceTimeMonth applicationTime;
	
	/*
	 * 月間時間
	 */
	private Time36AgreeMonth agreeMonth;
	
	/*
	 * 年間時間
	 */
	private Time36AgreeAnnual agreeAnnual;
	
	public Time36Agree(){
		this.applicationTime = new AttendanceTimeMonth(0);
		this.agreeMonth = new Time36AgreeMonth();
		this.agreeAnnual = new Time36AgreeAnnual();
	}
	
	public void updateAppTime(Integer appTime){
		this.applicationTime = new AttendanceTimeMonth(appTime);
	}
}
