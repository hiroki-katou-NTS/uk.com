package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 勤怠打刻
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkStampImport extends DomainObject{
	
	/*
	 * 丸め後の時刻 TimeWithDayAttr
	 */
	private int afterRoundingTime;
	
	/*
	 * 時刻
	 */
	private WorkTimeInformationImport timeDay;
	
	/*
	 * 場所コード WorkLocationCD
	 */
	private String locationCode;

	public WorkStampImport(int afterRoundingTime, WorkTimeInformationImport timeDay, String locationCode) {
		super();
		this.afterRoundingTime = afterRoundingTime;
		this.timeDay = timeDay;
		this.locationCode = locationCode;
	}

}
