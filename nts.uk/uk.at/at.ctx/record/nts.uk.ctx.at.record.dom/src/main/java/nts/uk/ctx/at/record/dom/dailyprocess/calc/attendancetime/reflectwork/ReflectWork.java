package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ActualStampAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.AttendanceAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectAttendanceClock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectStampOuput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;

/**
 * 出勤を反映する (new_2020)
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectWork {
	@Inject
	private CheckRangeReflectAttd checkRangeReflectAttd;
	
	@Inject
	private ReflectAttendanceClock reflectAttendanceClock;
	
	public ReflectStampOuput reflectWork(Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		ReflectStampOuput reflectStampOuput = ReflectStampOuput.NOT_REFLECT;
		// 出勤打刻の反映範囲か確認する (Xác nhận 反映範囲 của 出勤打刻)
		OutputCheckRangeReflectAttd outputCheckRangeReflectAttd = checkRangeReflectAttd.checkRangeReflectAttd(stamp,
				stampReflectRangeOutput, integrationOfDaily);
		// 1回目勤務の出勤打刻反映範囲内 (出勤打刻反映範囲内 của worktype lần 1)
		if (outputCheckRangeReflectAttd == OutputCheckRangeReflectAttd.FIRST_TIME) {
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.GOING_TO_WORK, ActualStampAtr.STAMP_REAL, 1,
					integrationOfDaily);
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.GOING_TO_WORK, ActualStampAtr.STAMP, 1,
					integrationOfDaily);

			// 2回目勤務の出勤打刻反映範囲内 (出勤打刻反映範囲内 của worktype lần 2)
		} else if (outputCheckRangeReflectAttd == OutputCheckRangeReflectAttd.SECOND_TIME) {
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.GOING_TO_WORK, ActualStampAtr.STAMP_REAL, 2,
					integrationOfDaily);
			reflectStampOuput = reflectAttendanceClock.reflect(stamp, AttendanceAtr.GOING_TO_WORK, ActualStampAtr.STAMP, 2,
					integrationOfDaily);
		}
		return reflectStampOuput;
		
	}

}
