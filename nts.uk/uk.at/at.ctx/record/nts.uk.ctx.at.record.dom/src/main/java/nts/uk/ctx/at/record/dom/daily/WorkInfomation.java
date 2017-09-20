package nts.uk.ctx.at.record.dom.daily;

import java.util.List;

import lombok.Value;

/**
 * 日別実績の勤務情報
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkInfomation {
	private String syainID;
	private List<ScheduleTimeSheet> workScheduleTimeSheet;
	
	
	
	
	
}
