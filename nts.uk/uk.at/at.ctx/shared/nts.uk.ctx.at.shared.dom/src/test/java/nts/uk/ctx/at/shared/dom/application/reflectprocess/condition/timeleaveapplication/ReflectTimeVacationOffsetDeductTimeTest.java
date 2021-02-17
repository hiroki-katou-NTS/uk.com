package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ReflectTimeVacationOffsetDeductTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →時間休暇時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →予定実績区分＝実績 or 予定
	 * 
	 * →実績の時間帯へ反映する ＝ する
	 */
	@Test
	public void testCase1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);
		List<TimeLeaveApplicationDetailShare> appTimeLeavDetail = createAppTimeLeav(AppTimeType.ATWORK, 1);
		TimeLeaveAppReflectCondition condition = createCondi();
		List<Integer> lstResult = ReflectTimeVacationOffsetDeductTime.process(appTimeLeavDetail, dailyApp, condition,
				NotUseAtr.USE);
		assertThat(lstResult).isEqualTo(Arrays.asList(595, 596, 597, 1123, 1124, 31));
	}

	/*
	 * テストしたい内容
	 * 
	 * →時間休暇時間帯を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * →予定実績区分＝実績
	 * 
	 * →実績の時間帯へ反映する ＝ しない
	 * 
	 */@Test
	public void testCase２() {
			DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
					1, true);
			List<TimeLeaveApplicationDetailShare> appTimeLeavDetail = createAppTimeLeav(AppTimeType.ATWORK, 1);
			TimeLeaveAppReflectCondition condition = createCondi();
			List<Integer> lstResult = ReflectTimeVacationOffsetDeductTime.process(appTimeLeavDetail, dailyApp, condition,
					NotUseAtr.NOT_USE);
			assertThat(lstResult).isEqualTo(Arrays.asList(595, 596, 597, 1123, 1124));
	}

	private List<TimeLeaveApplicationDetailShare> createAppTimeLeav(AppTimeType appTimeType, int no) {

		AttendanceTime timeCommon = new AttendanceTime(60);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon,
				timeCommon, timeCommon, timeCommon, Optional.of(1));
		TimeZoneWithWorkNo timeZone = new TimeZoneWithWorkNo(no, 480, 1080);
		TimeLeaveApplicationDetailShare detail = new TimeLeaveApplicationDetailShare(appTimeType,
				Arrays.asList(timeZone), digest);
		return Arrays.asList(detail);
	}

	private TimeLeaveAppReflectCondition createCondi() {
		return new TimeLeaveAppReflectCondition(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}
}
