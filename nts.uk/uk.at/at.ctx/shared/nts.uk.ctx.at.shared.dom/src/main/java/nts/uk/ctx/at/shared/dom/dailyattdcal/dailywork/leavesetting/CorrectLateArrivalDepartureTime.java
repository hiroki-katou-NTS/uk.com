package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.leavesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.OtherEmTimezoneLateEarlySetRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         ジャスト遅刻早退時刻を補正する
 */
@Stateless
public class CorrectLateArrivalDepartureTime {

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private OtherEmTimezoneLateEarlySetRepository otherEmTimezoneLateRepo;

//	public void process(String companyId, String workTimeCode, List<TimeLeavingWork> lstTimeLeavingWork) {
//
//		// ドメインモデル「就業時間帯の設定」を取得する
//		Optional<WorkTimeSetting> workTimeOpt = this.workTimeSettingRepository.findByCode(companyId, workTimeCode);
//
//		if (!workTimeOpt.isPresent())
//			return;
//
//		// ドメインモデル「就業時間帯の遅刻・早退別設定」を取得する
//		boolean exacly = otherEmTimezoneLateRepo.getStampExaclyByKey(companyId, workTimeCode,
//				workTimeOpt.get().getWorkTimeDivision().getWorkTimeDailyAtr().value,
//				workTimeOpt.get().getWorkTimeDivision().getWorkTimeMethodSet().value, LateEarlyAtr.LATE.value);
//
//		lstTimeLeavingWork.stream().forEach(x -> {
//			justLateCorrection(exacly, x);
//		});
//
//	}

	// ジャスト遅刻補正をする
	// ジャスト早退補正をする
//	public void justLateCorrection(boolean exacly, TimeLeavingWork data) {
//		if (exacly && data.getAttendanceStamp().isPresent() && data.getAttendanceStamp().get().getStamp().isPresent()
//				&& data.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
//			data.getAttendanceStamp().get().getStamp().get().getTimeDay()
//					.setTimeWithDay(Optional.of(new TimeWithDayAttr(
//							data.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v()
//									- 1)));
//			data.getAttendanceStamp().get().getStamp().get().setAfterRoundingTime(new TimeWithDayAttr(
//					data.getAttendanceStamp().get().getStamp().get().getAfterRoundingTime().v() - 1));
//		}
//
//		if (exacly && data.getLeaveStamp().isPresent() && data.getLeaveStamp().get().getStamp().isPresent()
//				&& data.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
//			data.getLeaveStamp().get().getStamp().get().getTimeDay().setTimeWithDay(Optional.of(new TimeWithDayAttr(
//					data.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v() - 1)));
//			data.getLeaveStamp().get().getStamp().get().setAfterRoundingTime(
//					new TimeWithDayAttr(data.getLeaveStamp().get().getStamp().get().getAfterRoundingTime().v() - 1));
//		}
//
//	}
}
