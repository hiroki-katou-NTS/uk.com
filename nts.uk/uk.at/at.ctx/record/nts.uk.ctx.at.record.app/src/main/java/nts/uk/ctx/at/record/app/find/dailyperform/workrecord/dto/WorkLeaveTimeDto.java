package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

@Data
/** 出退勤時刻 */
@AllArgsConstructor
@NoArgsConstructor
public class WorkLeaveTimeDto implements ItemConst, AttendanceItemDataGate {

	private int no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ATTENDANCE)
	private WithActualTimeStampDto working;
	
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LEAVE)
	private WithActualTimeStampDto leave;
	
	@Override
	public WorkLeaveTimeDto clone() {
		return new WorkLeaveTimeDto(no, working == null ? null : working.clone(), leave == null ? null : leave .clone());
	}
	
	public static TimeLeavingWork toDomain(WorkLeaveTimeDto c) {
		return c == null ? new TimeLeavingWork(new WorkNo(1), null, null) 
				: new TimeLeavingWork(new WorkNo(c.getNo()), toTimeActualStamp(c.getWorking()),
															toTimeActualStamp(c.getLeave()));
	}

	private static TimeActualStamp toTimeActualStamp(WithActualTimeStampDto c) {
		return c == null ? null : c.toDomain();
	}

	@Override
	public boolean isNo(int idx) {
		return idx == no;
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case ATTENDANCE:
			return Optional.ofNullable(working);
		case LEAVE:
			return Optional.ofNullable(leave);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case ATTENDANCE:
			working = (WithActualTimeStampDto) value;
			break;
		case LEAVE:
			leave = (WithActualTimeStampDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case ATTENDANCE:
		case LEAVE:
			return new WithActualTimeStampDto();
		default:
			return null;
		}
	}
}
