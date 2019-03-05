package nts.uk.ctx.at.request.dom.application.overtime.time36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
/**
 * 36協定上限月間時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class Time36AgreeUpperLimitMonth extends DomainObject {
	
	/*
	 * 時間外時間
	 */
	private AttendanceTimeMonth overTime;
	
	/*
	 * 上限時間
	 */
	private LimitOneMonth upperLimitTime;
	
	public Time36AgreeUpperLimitMonth(){
		this.overTime = new AttendanceTimeMonth(0);
		this.upperLimitTime = new LimitOneMonth(0);
	}
	
	public void updateOverTime(Integer overTime){
		this.overTime = new AttendanceTimeMonth(overTime);
	}
	
	public void updateUpperLimitTime(Integer upperLimitTime){
		this.upperLimitTime = new LimitOneMonth(upperLimitTime);
	}
}
