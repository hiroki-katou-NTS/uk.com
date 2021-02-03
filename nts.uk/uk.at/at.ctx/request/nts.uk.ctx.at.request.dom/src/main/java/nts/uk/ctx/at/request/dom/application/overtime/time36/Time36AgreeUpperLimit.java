package nts.uk.ctx.at.request.dom.application.overtime.time36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 36協定上限時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Time36AgreeUpperLimit extends DomainObject {
	
	/*
	 * 申請時間
	 */
	private AttendanceTimeMonth applicationTime;
	
	/*
	 * 月間時間
	 */
	private Time36AgreeUpperLimitMonth agreeUpperLimitMonth;
	
	/*
	 * 複数月平均時間
	 */
	private Time36AgreeUpperLimitAverage agreeUpperLimitAverage;
	
	public Time36AgreeUpperLimit(){
		this.applicationTime = new AttendanceTimeMonth(0);
		this.agreeUpperLimitMonth = new Time36AgreeUpperLimitMonth();
		this.agreeUpperLimitAverage = new Time36AgreeUpperLimitAverage();
	}
	
	public void updateAppTime(Integer appTime){
		this.applicationTime = new AttendanceTimeMonth(appTime);
	}
}
