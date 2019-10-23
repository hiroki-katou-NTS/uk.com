package nts.uk.ctx.at.request.dom.application.overtime.time36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;
/**
 * 36協定年間時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class Time36AgreeAnnual extends DomainObject {
	
	/*
	 * 実績時間
	 */
	private AttendanceTimeYear actualTime;
	
	/*
	 * 限度時間
	 */
	private LimitOneYear limitTime;
	
	public Time36AgreeAnnual(){
		this.actualTime = new AttendanceTimeYear(0);
		this.limitTime = new LimitOneYear(0);
	}
	
	public void setActualTime(Integer actualTime){
		this.actualTime = new AttendanceTimeYear(actualTime);
	}
	
	public void setLimitTime(Integer limitTime){
		this.limitTime = new LimitOneYear(limitTime);
	}
	
}
