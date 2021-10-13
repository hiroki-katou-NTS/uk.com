package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.overtime.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.AfterOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *事後残業申請の反映（勤務実績）
 */
@RunWith(JMockit.class)
public class AfterOtWorkAppReflectTest {

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
	
	/*
	 * テストしたい内容
	 * 
	 * →input.残業申請.申請時間.フレックス超過時間 <> empty →①フレックス時間を反映する
	 * 
	 * →input.残業申請.申請時間.フレックス超過時間  empty →②フレックス時間を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * 
	 */
	@Test
	public void test3() {

		//①フレックス時間を反映する
		AppOverTimeShare overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 
		overTimeApp.getApplicationTime().setFlexOverTime(Optional.of(new AttendanceTimeOfExistMinus(111)));
		
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime()
				.setBeforeApplicationTime(new AttendanceTime(123));

		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setOnlyFlexTime(new AttendanceTimeOfExistMinus(123));
		
		AfterOtWorkAppReflect reflectOvertimeBeforeSet = AfterOtWorkAppReflect.create(0, 0, 0, 0);//[出退勤を反映する] = しない
		reflectOvertimeBeforeSet.processAfter(require, cid, overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime().v()).isEqualTo(123);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().v()).isEqualTo(111);
		

		//②フレックス時間を反映しない
		overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 
		overTimeApp.getApplicationTime().setFlexOverTime(Optional.empty());
		
		dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
		.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setBeforeApplicationTime(new AttendanceTime(123));
		
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
		.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setOnlyFlexTime(new AttendanceTimeOfExistMinus(123));
		
		reflectOvertimeBeforeSet = AfterOtWorkAppReflect.create(0, 0, 0, 0);//[出退勤を反映する] = しない
		reflectOvertimeBeforeSet.processAfter(require, cid, overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime().v()).isEqualTo(123);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().v()).isEqualTo(123);
		

	}

}
