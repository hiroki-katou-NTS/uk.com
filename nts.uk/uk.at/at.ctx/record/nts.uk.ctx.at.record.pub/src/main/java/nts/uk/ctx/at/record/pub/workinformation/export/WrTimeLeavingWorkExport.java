package nts.uk.ctx.at.record.pub.workinformation.export;

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
public class WrTimeLeavingWorkExport extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private int workNo;
	//出勤
	private Optional<WrTimeActualStampExport> attendanceStamp;
	//退勤
	private Optional<WrTimeActualStampExport> leaveStamp;
	
	public WrTimeLeavingWorkExport(int workNo, WrTimeActualStampExport attendanceStamp,
			WrTimeActualStampExport leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = Optional.ofNullable(attendanceStamp);
		this.leaveStamp = Optional.ofNullable(leaveStamp);
	}
	
	
	
}
