package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.overtime.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.groups.Tuple;
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
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事前残業申請の反映（勤務実績）
 */
@RunWith(JMockit.class)
public class BeforeOtWorkAppReflectTest {

	@Injectable
	private BeforeOtWorkAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →残業時間を実績項目へ反映する
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[残業時間を実績項目へ反映する]の設定 = する
	 * 
	 */
	@Test
	public void test3() {

		val overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 残業休出申請.申請時間
																											// = 120
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		val reflectOvertimeBeforeSet = BeforeOtWorkAppReflect.create(0, NotUseAtr.USE.value, 0);
		reflectOvertimeBeforeSet.processRC(require, "", overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()
				.getOverTimeWorkFrameTime().get(0).getOverTimeWork().getTime().v()).isEqualTo(120);

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →残業時間を実績項目へ反映しない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →[残業時間を実績項目へ反映する]の設定 = しない
	 * 
	 */
	@Test
	public void test4() {

		val overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 残業休出申請.申請時間
																											// = 120
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime().get(0)
				.getOverTimeWork().setTime(new AttendanceTime(113));
		val reflectOvertimeBeforeSet = BeforeOtWorkAppReflect.create(0, NotUseAtr.NOT_USE.value, 0);
		reflectOvertimeBeforeSet.processRC(require, "", overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()
				.getOverTimeWorkFrameTime().get(0).getOverTimeWork().getTime().v()).isEqualTo(113);

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
	public void test5() {

		//①フレックス時間を反映する
		AppOverTimeShare overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 
		overTimeApp.getApplicationTime().setFlexOverTime(Optional.of(new AttendanceTimeOfExistMinus(111)));
		
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
		.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setOnlyFlexTime(new AttendanceTimeOfExistMinus(123));
		
		BeforeOtWorkAppReflect reflectOvertimeBeforeSet = BeforeOtWorkAppReflect.create(0, NotUseAtr.NOT_USE.value, 0);
		reflectOvertimeBeforeSet.processRC(require, "", overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime().v()).isEqualTo(111);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().v()).isEqualTo(123);
		

		//②フレックス時間を反映しない
		overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.NORMALOVERTIME, 120);// 
		overTimeApp.getApplicationTime().setFlexOverTime(Optional.empty());
		
		dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 勤務情報 = ("001", "001")
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
		.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setBeforeApplicationTime(new AttendanceTime(123));
		
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
		.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().setOnlyFlexTime(new AttendanceTimeOfExistMinus(123));
		
		reflectOvertimeBeforeSet = BeforeOtWorkAppReflect.create(0, NotUseAtr.NOT_USE.value, 0);
		reflectOvertimeBeforeSet.processRC(require, "", overTimeApp, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getBeforeApplicationTime().v()).isEqualTo(123);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getTime().v()).isEqualTo(123);

	}
}
