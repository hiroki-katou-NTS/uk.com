package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 36協定上限時間
 * @author thanhnx
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Time36AgreeUpperLimitShare extends DomainObject {
	
	/*
	 * 申請時間
	 */
	private AttendanceTimeMonth applicationTime;
	
	/*
	 * 月間時間
	 */
	private Time36AgreeUpperLimitMonthShare agreeUpperLimitMonth;
	
	/*
	 * 複数月平均時間
	 */
	private Time36AgreeUpperLimitAverageShare agreeUpperLimitAverage;
	
	public Time36AgreeUpperLimitShare(){
		this.applicationTime = new AttendanceTimeMonth(0);
		this.agreeUpperLimitMonth = new Time36AgreeUpperLimitMonthShare();
		this.agreeUpperLimitAverage = new Time36AgreeUpperLimitAverageShare();
	}
	
	public void updateAppTime(Integer appTime){
		this.applicationTime = new AttendanceTimeMonth(appTime);
	}
}
