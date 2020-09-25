package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectleavingwork;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ActualStampAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectAttendanceClock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;

/**
 * 退勤を反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectLeavingWork {
	@Inject
	private CheckRangeReflectLeavingWork checkRangeReflectLeavingWork;
	
	@Inject
	private ReflectAttendanceClock reflectAttendanceClock;
	
	public ReflectStampOuput reflectLeaving(Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		ReflectStampOuput reflectStampOuput = ReflectStampOuput.NOT_REFLECT;
		//退勤打刻の反映範囲か確認する
		OutputCheckRangeReflectAttd outputCheckRangeReflectAttd = checkRangeReflectLeavingWork
				.checkRangeReflectAttd(stamp, stampReflectRangeOutput, integrationOfDaily);
		if (outputCheckRangeReflectAttd == OutputCheckRangeReflectAttd.FIRST_TIME) {
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.LEAVING_WORK, ActualStampAtr.STAMP_REAL, 1,
					integrationOfDaily);
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.LEAVING_WORK, ActualStampAtr.STAMP, 1,
					integrationOfDaily);

			// 2回目勤務の出勤打刻反映範囲内 (出勤打刻反映範囲内 của worktype lần 2)
		} else if (outputCheckRangeReflectAttd == OutputCheckRangeReflectAttd.SECOND_TIME) {
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.LEAVING_WORK, ActualStampAtr.STAMP_REAL, 2,
					integrationOfDaily);
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.LEAVING_WORK, ActualStampAtr.STAMP, 2,
					integrationOfDaily);
		}
		return reflectStampOuput;
	}

}
