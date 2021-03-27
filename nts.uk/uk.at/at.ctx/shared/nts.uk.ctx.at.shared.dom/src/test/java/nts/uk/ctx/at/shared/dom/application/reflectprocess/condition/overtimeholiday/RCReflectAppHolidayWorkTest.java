package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OverTimeShiftNightShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class RCReflectAppHolidayWorkTest {

	@Injectable
	private AppReflectOtHdWork.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →休日出勤申請を反映する（勤務実績）
	 * 
	 * →①休日出勤申請反映の処理を呼ぶ
	 * 
	 * →②時間外深夜時間を反映する
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[時間外深夜時間を反映する]＝する
	 * 
	 */
	@Test
	public void test1() {
		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		AppHolidayWorkShare holidayApp = createAppHoliday("005", // 勤務種類コード
				"006", // 就業時間帯コード
				1111// 申請就業外深夜時間.合計外深夜時間
		);
		createSetting(NotUseAtr.USE).process(require, "", holidayApp, dailyApp);

		// ①休日出勤申請反映の処理を呼ぶ
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");

		// ②時間外深夜時間を反映する
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(1111);

	}

	/*
	 * テストしたい内容
	 * 
	 * →休日出勤申請を反映する（勤務実績）
	 * 
	 * →休日出勤申請の反映
	 * 
	 * →時間外深夜時間を反映しない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[時間外深夜時間を反映する]＝しない
	 * 
	 */
	@Test
	public void test2() {

		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		AppHolidayWorkShare holidayApp = createAppHoliday("005", // 勤務種類コード
				"006", // 就業時間帯コード
				1111);// 申請就業外深夜時間.合計外深夜時間
		createSetting(NotUseAtr.NOT_USE).process(require, "", holidayApp, dailyApp);
		// ①休日出勤申請反映の処理を呼ぶ
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");

		// ②時間外深夜時間を反映しない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(0);

	}

	private AppReflectOtHdWork createSetting(NotUseAtr nightOver) {
		return new AppReflectOtHdWork("", new HdWorkAppReflect(new BeforeHdWorkAppReflect(NotUseAtr.USE), null), null,
				nightOver);// [時間外深夜時間を反映する]
	}

	private AppHolidayWorkShare createAppHoliday(String workTypeCode, String workTimeCode, int midNightOutSide) {
		List<TimeZoneWithWorkNo> breakTimeList = new ArrayList<>();

		List<TimeZoneWithWorkNo> workingTimeList = new ArrayList<>();

		val appTimeShare = new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.of(new OverTimeShiftNightShare(new ArrayList<>(), new AttendanceTime(midNightOutSide), // 合計外深夜時間
						new AttendanceTime(0))), //
				new ArrayList<>(), new ArrayList<>());

		return new AppHolidayWorkShare(
				ReflectApplicationHelper.createAppShare(ApplicationTypeShare.HOLIDAY_WORK_APPLICATION,
						PrePostAtrShare.PREDICT),
				new WorkInformation(workTypeCode, workTimeCode), // 勤務情報
				appTimeShare, //
				false, //
				false, //
				breakTimeList, // 休憩時間帯
				workingTimeList, // 勤務時間帯
				null);//
	}
}
