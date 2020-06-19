package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * 
 * @author nampt
 * 日別実績の短時間勤務時間帯 - root
 *
 */
@Getter
@NoArgsConstructor
public class ShortTimeOfDailyPerformance extends AggregateRoot {
	
	/** 社員ID: 社員ID*/
	private String employeeId;
	
	/** 年月日: 年月日*/
	private GeneralDate ymd;
	
	/** 時間帯*/
	private ShortTimeOfDailyAttd timeZone;
	
	public ShortTimeOfDailyPerformance(String employeeId, List<ShortWorkingTimeSheet> shortWorkingTimeSheets,
			GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = new ShortTimeOfDailyAttd(shortWorkingTimeSheets);
	}

	public ShortTimeOfDailyPerformance(String employeeId, GeneralDate ymd, ShortTimeOfDailyAttd timeZone) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = timeZone;
	}
	
}
