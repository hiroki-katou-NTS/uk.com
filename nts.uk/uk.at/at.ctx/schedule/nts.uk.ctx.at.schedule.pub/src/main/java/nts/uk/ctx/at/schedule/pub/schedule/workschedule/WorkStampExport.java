package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

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
public class WorkStampExport extends DomainObject{
	
	/*
	 * 時刻
	 */
	private WorkTimeInformationExport timeDay;
	
	/*
	 * 場所コード WorkLocationCD
	 */
	private String locationCode;

	public WorkStampExport(WorkTimeInformationExport timeDay, String locationCode) {
		super();
		this.timeDay = timeDay;
		this.locationCode = locationCode;
	}

}
