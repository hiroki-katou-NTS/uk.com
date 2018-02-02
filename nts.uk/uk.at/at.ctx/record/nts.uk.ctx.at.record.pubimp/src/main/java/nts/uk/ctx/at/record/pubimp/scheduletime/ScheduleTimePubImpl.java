package nts.uk.ctx.at.record.pubimp.scheduletime;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Stateless
public class ScheduleTimePubImpl implements ScheduleTimePub{

	@Override
	public ScheduleTimePubExport calculationScheduleTime(ScheduleTimePubImport impTime) {
		return new ScheduleTimePubExport(impTime.getEmployeeId(),
					impTime.getTargetDate(),
					new AttendanceTime(0),
					new AttendanceTime(0),
					new AttendanceTime(0),
					new AttendanceTime(0),
				 	new AttendanceTime(0),
				 	new AttendanceTime(0),
				 	new AttendanceTime(0));
	}

}