package nts.uk.pub.spr.dailystatus.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DailyStatusSpr {
	
	private GeneralDate date;
	
	private Integer status1;
	
	private Integer status2;
}
