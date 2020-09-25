package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@NoArgsConstructor
public class TimeLeavingWorkImport extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private int workNo;
	//出勤
	private Optional<TimeActualStampImport> attendanceStamp;
	//退勤
	private Optional<TimeActualStampImport> leaveStamp;
	
	public TimeLeavingWorkImport(int workNo, TimeActualStampImport attendanceStamp,
			TimeActualStampImport leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = Optional.ofNullable(attendanceStamp);
		this.leaveStamp = Optional.ofNullable(leaveStamp);
	}
	
	
	
}
