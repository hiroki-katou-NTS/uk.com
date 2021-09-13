package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.holiday.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OverTimeShiftNightShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         休日出勤申請の反映
 */
@RunWith(JMockit.class)
public class HdWorkAppReflectTest {

	@Injectable
	private HdWorkAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →事前休日出勤申請の反映
	 * 
	 * →①勤務情報を反映する
	 * 
	 * →②休憩・外出の申請反映がない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →事前事後区分=事前
	 * 
	 */
	@Test
	public void test1() {

		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		AppHolidayWorkShare holidayApp = createAppHoliday(PrePostAtrShare.PREDICT, // 事前事後区分=事前
				"005", // 勤務種類コード
				"006", // 就業時間帯コード
				1, // No
				600, // 休憩時間帯-開始時刻
				660);// 休憩時間帯-終了時刻
		val holidayWork = createSetting();
		holidayWork.process(require, "", holidayApp, dailyApp);

		// ①勤務情報を反映する
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");
		// ②休憩・外出の申請反映がない
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets())
				.extracting(x -> x.getBreakFrameNo().v(), x -> x.getStartTime().v(), // 休憩時間帯-開始時刻
						x -> x.getEndTime().v()) // 休憩時間帯-終了時刻
				.containsExactly(Tuple.tuple(1, 600, 660));

	}

	/*
	 * テストしたい内容
	 * 
	 * →事前休日出勤申請の反映
	 * 
	 * →①勤務情報を反映する
	 * 
	 * →②休憩・外出の申請反映がある
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →事前事後区分=事後
	 * 
	 */
	@Test
	public void test2() {

		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		AppHolidayWorkShare holidayApp = createAppHoliday(PrePostAtrShare.POSTERIOR, // 事前事後区分=事前
				"005", // 勤務種類コード
				"006", // 就業時間帯コード
				1, // No
				600, // 休憩時間帯-開始時刻
				660);// 休憩時間帯-終了時刻
		val holidayWork = createSetting();
		holidayWork.process(require, "", holidayApp, dailyApp);
		// ①勤務情報を反映する
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");
		// ②休憩・外出の申請反映がない
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets())
				.extracting(x -> x.getBreakFrameNo().v(), x -> x.getStartTime().v(), // 休憩時間帯-開始時刻
						x -> x.getEndTime().v()) // 休憩時間帯-終了時刻
				.containsExactly(Tuple.tuple(1, 600, 660));
	}

	private HdWorkAppReflect createSetting() {
		return new HdWorkAppReflect(new BeforeHdWorkAppReflect(NotUseAtr.NOT_USE),
				new AfterHdWorkAppReflect(new OthersReflect(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
						new BreakApplication(NotUseAtr.USE), // 休憩・外出を申請反映する
						NotUseAtr.USE));// 出退勤を反映する
	}

	private AppHolidayWorkShare createAppHoliday(PrePostAtrShare prePost, String workTypeCode, String workTimeCode,
			int no, int breakTimeStart, int breakTimeEnd) {
		List<TimeZoneWithWorkNo> breakTimeList = new ArrayList<>();
		breakTimeList.add(new TimeZoneWithWorkNo(no, breakTimeStart, breakTimeEnd));// 休憩

		List<TimeZoneWithWorkNo> workingTimeList = new ArrayList<>();

		val appTimeShare = new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.of(new OverTimeShiftNightShare(new ArrayList<>(), new AttendanceTime(0), // 合計外深夜時間
						new AttendanceTime(0))), //
				new ArrayList<>(), new ArrayList<>());

		return new AppHolidayWorkShare(
				ReflectApplicationHelper.createAppShare(ApplicationTypeShare.HOLIDAY_WORK_APPLICATION, prePost),
				new WorkInformation(workTypeCode, workTimeCode), // 勤務情報
				appTimeShare, //
				false, //
				false, //
				breakTimeList, // 休憩時間帯
				workingTimeList // 勤務時間帯
				);//
	}

}
