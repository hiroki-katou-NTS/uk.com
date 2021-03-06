package nts.uk.ctx.at.record.dom.applicationcancel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectTimeStamp.ReflectTimeStampResult;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.EndStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class ReflectTimeStampTest {

	@Injectable
	private ReflectTimeStamp.Require require;

	/*
	 * ????????????????????????
	 * 
	 * ??? ?????????????????????????????????
	 * 
	 * ?????????????????????
	 * 
	 * ??? ???????????? = ??????
	 * 
	 */
	@Test
	public void test() {

		DailyRecordOfApplication dailyRecordApp = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.RECORD);

		Stamp stamp = createStamp();

		OutputTimeReflectForWorkinfo outTime = createInput();

		// 1??????????????????????????????????????????
		new Expectations() {
			{
				require.checkRangeReflectAttd((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.FIRST_TIME;
			}
		};

		ReflectTimeStampResult actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime,
				new AttendanceClock(0), new TimeWithDayAttr(0), EngraveShareAtr.ATTENDANCE, Optional.of(stamp));

		assertThat(actualResult.getWorkNoReflect().v()).isEqualTo(1);
		assertThat(actualResult.isReflect()).isTrue();

		// 2??????????????????????????????????????????
		new Expectations() {
			{
				require.checkRangeReflectAttd((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.SECOND_TIME;
			}
		};

		actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime, new AttendanceClock(0),
				new TimeWithDayAttr(0), EngraveShareAtr.ATTENDANCE, Optional.of(stamp));

		assertThat(actualResult.getWorkNoReflect().v()).isEqualTo(2);
		assertThat(actualResult.isReflect()).isTrue();

		// ?????????
		new Expectations() {
			{
				require.checkRangeReflectAttd((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.OUT_OF_RANGE;
			}
		};

		actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime, new AttendanceClock(0),
				new TimeWithDayAttr(0), EngraveShareAtr.ATTENDANCE, Optional.of(stamp));

		assertThat(actualResult.isReflect()).isFalse();

	}

	/*
	 * ????????????????????????
	 * 
	 * ??? ?????????????????????????????????
	 * 
	 * ?????????????????????
	 * 
	 * ??? ???????????? = ??????
	 * 
	 */
	@Test
	public void testLeav() {

		DailyRecordOfApplication dailyRecordApp = ReflectApplicationHelper
				.createDailyRecord(ScheduleRecordClassifi.RECORD);

		Stamp stamp = createStamp();

		OutputTimeReflectForWorkinfo outTime = createInput();

		// 1??????????????????????????????????????????
		new Expectations() {
			{
				require.checkRangeReflectOut((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.FIRST_TIME;
			}
		};

		ReflectTimeStampResult actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime,
				new AttendanceClock(0), new TimeWithDayAttr(0), EngraveShareAtr.OFFICE_WORK, Optional.of(stamp));

		assertThat(actualResult.getWorkNoReflect().v()).isEqualTo(1);
		assertThat(actualResult.isReflect()).isTrue();

		// 2??????????????????????????????????????????
		new Expectations() {
			{
				require.checkRangeReflectOut((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.SECOND_TIME;
			}
		};

		actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime, new AttendanceClock(0),
				new TimeWithDayAttr(0), EngraveShareAtr.OFFICE_WORK, Optional.of(stamp));

		assertThat(actualResult.getWorkNoReflect().v()).isEqualTo(2);
		assertThat(actualResult.isReflect()).isTrue();

		// ?????????
		new Expectations() {
			{
				require.checkRangeReflectOut((Stamp) any, (StampReflectRangeOutput) any, (IntegrationOfDaily) any);
				result = OutputCheckRangeReflectAttd.OUT_OF_RANGE;
			}
		};

		actualResult = ReflectTimeStamp.reflect(require, dailyRecordApp, outTime, new AttendanceClock(0),
				new TimeWithDayAttr(0), EngraveShareAtr.OFFICE_WORK, Optional.of(stamp));

		assertThat(actualResult.isReflect()).isFalse();

	}

	public static Stamp createStamp() {
		return new Stamp(new ContractCode("1"), // ???????????????
				new StampNumber("1"), // ???????????????
				GeneralDateTime.ymdhms(2020, 01, 01, 10, 0, 0), // ????????????
				new Relieve(AuthcMethod.ID_AUTHC, // ??????????????????. ????????????
						StampMeans.NAME_SELECTION), /// ??????????????????. ????????????
				new StampType(false, GoingOutReason.valueOf(1), // ????????????
						SetPreClockArt.NONE, // ???????????????????????????
						ChangeClockAtr.GOING_TO_WORK, // ??????????????????
						ChangeCalArt.NONE), // ????????????????????????
				new RefectActualResult(null, null, null, null), Optional.empty());
	}

	private OutputTimeReflectForWorkinfo createInput() {
		return new OutputTimeReflectForWorkinfo(EndStatus.NORMAL, new StampReflectRangeOutput(),new ArrayList<>());
	}
}
