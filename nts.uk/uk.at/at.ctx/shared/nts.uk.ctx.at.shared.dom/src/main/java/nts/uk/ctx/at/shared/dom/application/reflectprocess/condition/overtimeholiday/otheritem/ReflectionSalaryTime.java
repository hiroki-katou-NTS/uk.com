package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;

/**
 * @author thanh_nx
 *
 *         加給時間の反映
 */
public class ReflectionSalaryTime {

	public static List<Integer> process(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();

		// 日別勤怠(work）の該当する[加給時間]をチェック
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return lstId;
		}

		List<BonusPayTime> salaryTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
				.getRaisingSalaryTimes();
		// 該当の枠NOをキーにした[加給時間]を作成する
		Optional<BonusPayTime> salaryTimeOpt = salaryTime.stream()
				.filter(x -> x.getBonusPayTimeItemNo() == applicationTime.getFrameNo().v()).findFirst();

		// 加給NOをキーにして加給時間をセットする
		if (salaryTimeOpt.isPresent()) {
			salaryTimeOpt.get().setBonusPayTime(applicationTime.getApplicationTime());
		} else {
			BonusPayTime bt = BonusPayTime.createDefaultWithNo(applicationTime.getFrameNo().v());
			bt.setBonusPayTime(applicationTime.getApplicationTime());
			salaryTime.add(bt);
		}
		lstId.add(CancelAppStamp.createItemId(316, applicationTime.getFrameNo().v(), 1));
		return lstId;
	}

}
