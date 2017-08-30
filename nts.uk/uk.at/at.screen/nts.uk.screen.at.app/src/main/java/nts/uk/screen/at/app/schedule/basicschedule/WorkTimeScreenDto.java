package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeScreenDto {
	private String siftCd;
	private String name;
	private String abName;
	private String symbol;
	private int dailyWorkAtr;
	private int methodAtr;
	private int displayAtr;
	private String note;
	private int amStartClock;
	private int pmEndClock;
	private int timeNumberCnt;
}
