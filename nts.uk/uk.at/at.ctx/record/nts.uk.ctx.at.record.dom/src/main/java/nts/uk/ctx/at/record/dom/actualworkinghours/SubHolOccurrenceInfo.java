package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.primitivevalue.SubHolOccurrenceDayNumber;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 代休発生情報
 *
 */
@Getter
public class SubHolOccurrenceInfo {

	//時間
	private AttendanceTime time;
	
	//代休発生日数
	private SubHolOccurrenceDayNumber subHolOccurrenceDayNumber; 
}
