package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock.ReflectAttendanceClock;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.timestamp.ChangeAttendanceTimeStamp;

/**
 * @author thanh_nx
 *
 *         勤怠打刻を変更する
 */
@Stateless
public class ChangeAttendanceTimeStampImpl implements ChangeAttendanceTimeStamp {

	@Inject
	private ReflectAttendanceClock reflectAttendanceClock;

	@Override
	public void change(String companyId, WorkStamp workStampOld, WorkStamp workStampNew) {

		// アルゴリズム「時刻を変更してもいいか判断する」を実行する
		boolean change = reflectAttendanceClock.isCanChangeTime(companyId, Optional.ofNullable(workStampOld),
				workStampNew.getTimeDay().getReasonTimeChange());
		if (!change)
			return;

		// 返自身の「勤怠打刻」を更新する
		workStampOld.setTimeDay(workStampNew.getTimeDay());
		workStampOld.setLocationCode(workStampNew.getLocationCode());

		return;
	}

}
