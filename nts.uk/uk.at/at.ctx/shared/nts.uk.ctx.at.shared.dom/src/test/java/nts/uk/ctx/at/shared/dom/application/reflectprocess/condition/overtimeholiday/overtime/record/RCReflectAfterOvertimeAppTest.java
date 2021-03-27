package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.record;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.AfterOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

/**
 * @author thanh_nx
 *
 *事後残業申請の反映（勤務実績）
 */
@RunWith(JMockit.class)
public class RCReflectAfterOvertimeAppTest {

	@Injectable
	private AfterOtWorkAppReflect.RequireAfter require;

	private String cid = "1";

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →出退勤の反映が出来る
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[出退勤を反映する] = する
	 * 
	 */
	@Test
	public void test() {

		val overTimeApp = ReflectApplicationHelper.createOverTimeAppWorkHours(481, 1021);// 時間帯
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// 勤務情報 = ("001", "001")
		val reflectOvertimeBeforeSet = AfterOtWorkAppReflect.create(1, 0, 0, 0);//[出退勤を反映する] = する
		reflectOvertimeBeforeSet.processAfter(require, cid, overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceTime().get().v())
				.isEqualTo(481);
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveTime().get().v())
				.isEqualTo(1021);

	}
	
	/*
	 * テストしたい内容
	 * 
	 * 
	 * →出退勤の反映が出来ない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[出退勤を反映する] = しない
	 * 
	 */
	@Test
	public void test2() {

		val overTimeApp = ReflectApplicationHelper.createOverTimeAppWorkHours(481, 1021);// 時間帯
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// 時間帯= (480, 1200)
		val reflectOvertimeBeforeSet = AfterOtWorkAppReflect.create(0, 0, 0, 0);//[出退勤を反映する] = しない
		reflectOvertimeBeforeSet.processAfter(require, cid, overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceTime().get().v())
				.isEqualTo(480);
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveTime().get().v())
				.isEqualTo(1200);

	}

}
