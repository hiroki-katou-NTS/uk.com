package nts.uk.ctx.at.record.pubimp.scheduletime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

public class ScheduleTimePubImpl implements ScheduleTimePub{

	@Override
	public ScheduleTimePubExport CalculationScheduleTime(ScheduleTimePubImport impTime) {
		return new ScheduleTimePubExport(impTime.getEmployeeId(),
				 GeneralDate.today(),
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0)
				 );
	}

}