package nts.uk.ctx.at.shared.dom.adapter.workschedule;

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
public class TimeLeavingWorkSharedImport extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private int workNo;
	//出勤
	private Optional<TimeActualStampSharedImport> attendanceStamp;
	//退勤
	private Optional<TimeActualStampSharedImport> leaveStamp;
	
	public TimeLeavingWorkSharedImport(int workNo, TimeActualStampSharedImport attendanceStamp,
			TimeActualStampSharedImport leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = Optional.ofNullable(attendanceStamp);
		this.leaveStamp = Optional.ofNullable(leaveStamp);
	}
	
	
	
}
