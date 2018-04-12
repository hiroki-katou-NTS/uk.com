package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class Kmw006cDto {

	private int closureId;
	
	private String closureName;
	
	private int yearMonth;
	
	private GeneralDateTime executeDT;
	
	private int totalEmployee;
	
	private int alarmCount;
	
	private int errorCount;
	
}
