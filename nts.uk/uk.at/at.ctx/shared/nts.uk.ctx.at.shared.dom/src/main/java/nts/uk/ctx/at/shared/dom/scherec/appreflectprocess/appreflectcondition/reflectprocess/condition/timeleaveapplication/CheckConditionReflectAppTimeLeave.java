package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.timeleaveapplication;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         時間休暇の申請反映条件チェックo
 */
public class CheckConditionReflectAppTimeLeave {

	public static TimeDigestApplicationShare check(TimeDigestApplicationShare timeDigestApplication,
			TimeLeaveAppReflectCondition condition) {

		// 反映する時間消化申請（work）の初期化
		TimeDigestApplicationShare timeDigestInit = new TimeDigestApplicationShare(new AttendanceTime(0),
				new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0), Optional.empty());

		// [時間年休]をチェック
		if (condition.getAnnualVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeAnnualLeave(timeDigestApplication.getTimeAnnualLeave());
		}

		// [時間代休]をチェック
		if (condition.getSubstituteLeaveTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeOff(timeDigestApplication.getTimeOff());
		}

		// [時間特別休暇]をチェック
		if (condition.getSpecialVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeSpecialVacation(timeDigestApplication.getTimeSpecialVacation());
			timeDigestInit.setSpecialVacationFrameNO(timeDigestApplication.getSpecialVacationFrameNO());
		}

		// [60H超休]をチェック
		if (condition.getSuperHoliday60H() == NotUseAtr.USE) {
			timeDigestInit.setOvertime60H(timeDigestApplication.getOvertime60H());
		}

		// [子看護]をチェック
		if (condition.getChildNursing()== NotUseAtr.USE) {
			timeDigestInit.setChildTime(timeDigestApplication.getChildTime());
		}

		// [介護]をチェック
		if (condition.getNursing() == NotUseAtr.USE) {
			timeDigestInit.setNursingTime(timeDigestApplication.getNursingTime());
		}

		return timeDigestInit;
	}
}
