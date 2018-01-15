package nts.uk.ctx.at.record.dom.shorttimework;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績の短時間勤務時間帯 - root
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortTimeOfDailyPerformance extends AggregateRoot {
	
	/** 社員ID: 社員ID*/
	private String employeeId;
	
	/** 時間帯: 短時間勤務時間帯 */
	private List<ShortWorkingTimeSheet> shortWorkingTimeSheets;
	
	/** 年月日: 年月日*/
	private GeneralDate ymd;

}
