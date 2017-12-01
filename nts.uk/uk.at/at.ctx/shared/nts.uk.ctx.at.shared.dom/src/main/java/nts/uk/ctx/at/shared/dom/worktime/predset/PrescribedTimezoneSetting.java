package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

//所定時間帯設定
@Builder
@Getter
public class PrescribedTimezoneSetting extends DomainObject{

	/** The morning end time. */
	//午前終了時刻
	private TimeWithDayAttr morningEndTime;
	
	/** The afternoon start time. */
	//午後開始時刻
	private TimeWithDayAttr afternoonStartTime;
	
	/** The timezone. */
	//時間帯
	private List<Timezone> timezone;
}
