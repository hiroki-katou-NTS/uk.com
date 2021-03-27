package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

@RunWith(JMockit.class)
public class SCBeforeReflectAppHolidayWorkTest {

	@Injectable
	private BeforeHdWorkAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →事前休日出勤申請の反映(勤務予定）
	 * 
	 * →勤務情報の反映ができます
	 * 
	 * →出退勤の反映ができます
	 * 
	 * →休憩・外出の申請反映ができます
	 * 
	 * 準備するデータ
	 * 
	 * →休日出勤申請データ
	 * 
	 */
	@Test
	public void test1() {

		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);

		AppHolidayWorkShare holidayApp = createAppHoliday("005", // 勤務種類コード
				"006", // 就業時間帯コード
				1, // No
				488, // 勤務時間帯-開始時刻
				1088, // 勤務時間帯-終了時刻
				600, // 休憩時間帯-開始時刻
				660);// 休憩時間帯-終了時刻
		(new BeforeHdWorkAppReflect()).processSC(require, holidayApp, dailyApp);
		// 勤務情報の反映ができます
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");

		// 出退勤の反映ができます
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(), // 勤務時間帯-開始時刻
						x -> x.getStampOfAttendance().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans(), // 時刻変更理由
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v(), // 勤務時間帯-終了時刻
						x -> x.getStampOfLeave().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())// 時刻変更理由
				.containsExactly(Tuple.tuple(1, 488, TimeChangeMeans.APPLICATION, 1088, TimeChangeMeans.APPLICATION));

		// 休憩・外出の申請反映ができます
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets())
				.extracting(x -> x.getBreakFrameNo().v(), 
								 x -> x.getStartTime().v(), //休憩時間帯-開始時刻
								 x -> x.getEndTime().v()) // 休憩時間帯-終了時刻
				.containsExactly(Tuple.tuple(1, 600, 660));
	}

	private AppHolidayWorkShare createAppHoliday(String workTypeCode, String workTimeCode, int no, int att, int leav,
			int breakTimeStart, int breakTimeEnd) {
		List<TimeZoneWithWorkNo> breakTimeList = new ArrayList<>();
		breakTimeList.add(new TimeZoneWithWorkNo(no, breakTimeStart, breakTimeEnd));

		List<TimeZoneWithWorkNo> workingTimeList = new ArrayList<>();
		workingTimeList.add(new TimeZoneWithWorkNo(no, att, leav));

		return new AppHolidayWorkShare(
				ReflectApplicationHelper.createAppShare(ApplicationTypeShare.HOLIDAY_WORK_APPLICATION,
						PrePostAtrShare.PREDICT),
				new WorkInformation(workTypeCode, workTimeCode), // 勤務情報
				null, //
				false, //
				false, //
				breakTimeList, // 休憩時間帯
				workingTimeList, // 勤務時間帯
				null);//
	}

}
