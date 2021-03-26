package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.otheritem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;

/**
 * @author thanh_nx
 *
 *         特別日加給時間
 */
public class SpecialDaySalaryTime {

	public static List<Integer> process(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 日別勤怠(work）の該当する[特定日加給時間]をチェック

		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return lstId;
		}

		List<BonusPayTime> salaryTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
				.getAutoCalRaisingSalarySettings();
		// // 該当の枠NOをキーにした[特定日加給時間]を作成する
		Optional<BonusPayTime> salaryTimeOpt = salaryTime.stream()
				.filter(x -> x.getBonusPayTimeItemNo() == applicationTime.getFrameNo().v()).findFirst();

		// 加給NOをキーにして特定日加給時間をセットする
		if (salaryTimeOpt.isPresent()) {
			salaryTimeOpt.get().setBonusPayTime(applicationTime.getApplicationTime());
		} else {
			BonusPayTime bt = BonusPayTime.createDefaultWithNo(applicationTime.getFrameNo().v());
			bt.setBonusPayTime(applicationTime.getApplicationTime());
			salaryTime.add(bt);
		}
		lstId.add(CancelAppStamp.createItemId(366, applicationTime.getFrameNo().v(), 1));
		return lstId;

	}
}
