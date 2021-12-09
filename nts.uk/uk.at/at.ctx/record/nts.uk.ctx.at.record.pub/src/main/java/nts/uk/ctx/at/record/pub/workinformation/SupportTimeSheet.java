package nts.uk.ctx.at.record.pub.workinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
@Setter
@NoArgsConstructor
public class SupportTimeSheet {
	//応援枠NO
	private int frameNo;
	
	//開始時刻
	private TimeWithDayAttr start;
	
	//終了時刻
	private TimeWithDayAttr end;
	
	//場所コード
	private String locationCd;
	
	//職場ID
	private String workplaceId;

}
