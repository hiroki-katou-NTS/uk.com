package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.DailyExcessTotalTimeExpParam;

/**
 * RequestLIst No 193 
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyExcessTotalTimePubExport {
	
	Map<GeneralDate,DailyExcessTotalTimeExpParam> map;

	/**
	 * Constructor; 
	 */
	public DailyExcessTotalTimePubExport(Map<GeneralDate, DailyExcessTotalTimeExpParam> map) {
		super();
		this.map = map;
	}
	
}
