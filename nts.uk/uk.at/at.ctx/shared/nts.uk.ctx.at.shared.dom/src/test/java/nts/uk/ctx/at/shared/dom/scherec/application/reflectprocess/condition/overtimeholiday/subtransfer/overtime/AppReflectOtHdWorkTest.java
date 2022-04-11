package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.subtransfer.overtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 * 残業申請を反映する（勤務実績）
 */
@RunWith(JMockit.class)
public class AppReflectOtHdWorkTest {

	@Injectable
	private AppReflectOtHdWork.RequireRC require;

	private String cid = "1";

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →時間外深夜時間の反映ができます
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[申請反映条件.時間外深夜時間を反映する] = する
	 * 
	 */
	@Test
	public void test() {
		AppOverTimeShare app = ReflectApplicationHelper.createOverTimeApp(111);// 合計外深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		// AppReflectOtHdWork
		AppReflectOtHdWork reflectOvertimeSet = reflectOvertimeSet(NotUseAtr.USE);

		reflectOvertimeSet.processOverRc(require, app, dailyApp);// 法定外深夜時間.時間 = 0

		// 法定外深夜時間.時間
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(111);
	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →時間外深夜時間の反映ができない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[申請反映条件.時間外深夜時間を反映する] = しない
	 * 
	 */
	@Test
	public void test2() {
		AppOverTimeShare app = ReflectApplicationHelper.createOverTimeApp(111);// 合計外深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().setBeforeApplicationTime(new AttendanceTime(113));
		// AppReflectOtHdWork
		AppReflectOtHdWork reflectOvertimeSet = reflectOvertimeSet(NotUseAtr.NOT_USE);

		reflectOvertimeSet.processOverRc(require, app, dailyApp);// 法定外深夜時間.時間 = 0

		// 法定外深夜時間.時間
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(113);
	}

	private AppReflectOtHdWork reflectOvertimeSet(NotUseAtr reflectLateNight) {

		return new AppReflectOtHdWork(cid,
				new HdWorkAppReflect(new BeforeHdWorkAppReflect(NotUseAtr.NOT_USE),
						AfterHdWorkAppReflect.create(0, 0, 0, 0)), // 休日出勤申請の反映 = しない
				OtWorkAppReflect.create(0, 0, 0, 0, 0, 0, 0), // 残業・休日出勤.残業申請 = しない
				reflectLateNight);
	}
}
