package nts.uk.ctx.at.record.app.command.midnight;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author yennh
 *
 */
@Data
@AllArgsConstructor
public class MidnightTimeSheetCommand {
	/*開始時刻*/
	private TimeWithDayAttr start;
	
	/*終了時刻*/
	private TimeWithDayAttr end;
	
	public MidNightTimeSheet toDomain(String cid){
		return new MidNightTimeSheet(cid, start, end);
	}

}
