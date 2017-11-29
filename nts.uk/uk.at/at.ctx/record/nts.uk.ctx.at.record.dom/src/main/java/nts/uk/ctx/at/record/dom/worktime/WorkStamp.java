package nts.uk.ctx.at.record.dom.worktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 勤怠打刻
 *
 */
@Getter
@AllArgsConstructor
public class WorkStamp extends DomainObject{
	
	//丸め後の時刻
	private TimeWithDayAttr AfterRoundingTime;
	
	//時刻
	private TimeWithDayAttr timeWithDay;
	
	//場所コード
	private WorkLocationCD locationCode;
	
	//打刻元情報
	private StampSourceInfo stampSourceInfo;


}
