package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.GetSubHolOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class TranferHdWorkCompensatoryTest {

	// @Injectable
	// private TranferHdWorkCompensatory.Require require;

	// private String cid = "1";

	// /*
	 // * テストしたい内容
	 // * 
	 // * 
	 // * →休日出勤時間の代休振替
	 // * 
	 // * 
	 // * 準備するデータ
	 // * 
	 // * → 代休振替処理から最大の時間.振替時間, 時間 = (999, 666)
	 // * 
	 // */
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test01(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked SubstituteTransferProcess sub) {

		// DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				// .createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);

		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = ReflectApplicationHelper.createRCWithTimeLeavOverTime();

				// SubstituteTransferProcess.process(require, anyString, (Optional<String>)any,
						// (CompensatoryOccurrenceDivision)any, (MaximumTimeZone) any,
						// (List<OvertimeHourTransfer>) any, (List<OvertimeHourTransfer>) any);
				// result = Arrays.asList(new OvertimeHourTransfer(1, new AttendanceTime(666), new AttendanceTime(999)));
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 666, 999));

	// }

	// /**
	 // * 1 代休に必要な時間->1日8ｈ 申請時間① 1 普通残業 8:00 4 深夜残業 6:00
	 // */
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test11(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// DailyRecordOfApplication dailyApp = createRCHdCase();

		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase();
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 0, 480), Tuple.tuple(2, 0, 360));

	// }

	申請時間①
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a1(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 690, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 0, 690), Tuple.tuple(2, 0, 120));

	// }

	申請時間②
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a2(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 330, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(480, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 330, 0), Tuple.tuple(2, 120, 0));

	// }

	申請時間③
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a3(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(3, 690, 0), create(4, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(3, 0, 690), Tuple.tuple(4, 0, 120));

	// }

	申請時間④
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a4(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(3, 330, 0), create(4, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(480, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(3, 330, 0), Tuple.tuple(4, 120, 0));

	// }

	申請時間⑤
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a5(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 450, 0), create(4, 360, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 0, 450), Tuple.tuple(4, 0, 360));

	// }

	申請時間⑥
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a6(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(3, 450, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(3, 0, 450), Tuple.tuple(2, 0, 120));

	// }

	申請時間①
	代休に必要な時間->1日0ｈ
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2b1(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 690, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 690, 0), Tuple.tuple(2, 120, 0));

	// }

	申請時間②
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2b2(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 330, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = ReflectApplicationHelper.createSubTrans(0, 0, 0,
						// SubHolTransferSetAtr.SPECIFIED_TIME_SUB_HOL);
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 330, 0), Tuple.tuple(2, 120, 0));

	// }

	申請時間⑦
	勤務種類の設定->代休を発生させない
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a7(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(3, 690, 0), create(4, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result = Optional.empty();
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(3, 690, 0), Tuple.tuple(4, 120, 0));

	// }

	申請時間⑧
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test2a8(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked GetSubHolOccurrenceSetting getSub) {

		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 690, 0), create(2, 120, 0)));
		// DailyRecordOfApplication dailyApp = createRCHdCase2(hdTimeWorkFrameTime);
		// new Expectations() {
			// {
				// CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
				// result = createRCHdCase2(hdTimeWorkFrameTime);
				// GetSubHolOccurrenceSetting.process(require, anyString, (Optional<String>) any,
						// (CompensatoryOccurrenceDivision) any);
				// result =  Optional.empty();
			// }
		// };

		// TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
		// assertThat(
				// dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						// .getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								// .extracting(x -> x.getHolidayFrameNo().v(),
										// x -> x.getHolidayWorkTime().get().getTime().v(),
										// x -> x.getTransferTime().get().getTime().v())
								// .contains(Tuple.tuple(1, 690, 0), Tuple.tuple(2, 120, 0));

	// }

	// public DailyRecordOfApplication createRCHdCase2(List<HolidayWorkFrameTime> lstFrameTime) {

		// List<HolidayWorkFrameTimeSheet> frameTimeSheetList = new ArrayList<HolidayWorkFrameTimeSheet>();
		// frameTimeSheetList.addAll(Arrays.asList(createTimeSpan(2, 300, 540, 0, 240),
				// createTimeSpan(1, 540, 1320, 450, 240), createTimeSpan(2, 1320, 1740, 420, 0)));
		// return ReflectApplicationHelper.createRCWithTimeLeavHoliday(ScheduleRecordClassifi.RECORD, 1, lstFrameTime,
				// frameTimeSheetList);
	// }

	// public DailyRecordOfApplication createRCHdCase() {
		// List<HolidayWorkFrameTime> hdTimeWorkFrameTime = new ArrayList<HolidayWorkFrameTime>();
		// hdTimeWorkFrameTime.addAll(Arrays.asList(create(1, 480, 0), create(2, 360, 0)));

		// List<HolidayWorkFrameTimeSheet> frameTimeSheetList = new ArrayList<HolidayWorkFrameTimeSheet>();
		// frameTimeSheetList.addAll(Arrays.asList(createTimeSpan(2, 300, 540, 0, 240),
				// createTimeSpan(1, 540, 1320, 450, 240), createTimeSpan(4, 1320, 1740, 420, 0)));
		// return ReflectApplicationHelper.createRCWithTimeLeavHoliday(ScheduleRecordClassifi.RECORD, 1,
				// hdTimeWorkFrameTime, frameTimeSheetList);
	// }

	// private HolidayWorkFrameTime create(int no, int over, int calc) {
		// return new HolidayWorkFrameTime(new HolidayWorkFrameNo(no),
				// Finally.of(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(over),
						// new AttendanceTime(0))),
				// Finally.of(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(0),
						// new AttendanceTime(0))),
				// Finally.of(new AttendanceTime(0)));
	// }

	// private HolidayWorkFrameTimeSheet createTimeSpan(int no, int start, int end, int overtimeCalc,
			// int overTimeTransfer) {
		// return new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(no),
				// new TimeSpanForCalc(new TimeWithDayAttr(start), new TimeWithDayAttr(end)),
				// new AttendanceTime(overtimeCalc), new AttendanceTime(overTimeTransfer));

	// }
}
