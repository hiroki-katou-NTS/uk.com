package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

	@RunWith(JMockit.class)
	public class TranferHdWorkCompensatoryTest {

		@Injectable
		private TranferHdWorkCompensatory.Require require;

		private String cid = "1";

		/*
		 * テストしたい内容
		 * 
		 * 
		 * →休日出勤時間の代休振替
		 * 
		 * 
		 * 準備するデータ
		 * 
		 * → 代休振替処理から最大の時間.振替時間, 時間 = (999, 666)
		 * 
		 */
		@SuppressWarnings("unchecked")
		@Test
		public void test(@Mocked CreateWorkMaxTimeZone createMaxTime, @Mocked SubstituteTransferProcess sub) {

			DailyRecordOfApplication dailyApp = ReflectApplicationHelper
					.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);

			new Expectations() {
				{
					CreateWorkMaxTimeZone.process(require, cid, (IntegrationOfDaily) any);
					result = ReflectApplicationHelper.createRCWithTimeLeavOverTime();

					SubstituteTransferProcess.process((MaximumTimeZone) any, (List<MaximumTime>) any,
							(List<MaximumTime>) any);
					result = Arrays.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(999)));
				}
			};

			TranferHdWorkCompensatory.process(require, cid, dailyApp.getDomain());
			assertThat(
					dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
							.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
									.extracting(x -> x.getHolidayFrameNo().v(), x -> x.getHolidayWorkTime().get().getTime().v(),
											x -> x.getTransferTime().get().getTime().v())
									.contains(Tuple.tuple(1, 666, 999));

		}

	}
