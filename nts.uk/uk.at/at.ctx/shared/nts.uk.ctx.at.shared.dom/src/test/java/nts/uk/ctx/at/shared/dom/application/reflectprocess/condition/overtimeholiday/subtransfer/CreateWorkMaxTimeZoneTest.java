package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

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
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class CreateWorkMaxTimeZoneTest {

	@Injectable
	private CreateWorkMaxTimeZone.Require require;

	private String cid = "1";

	/*
	 * テストしたい内容
	 * 
	 * →最大の時間帯でworkを作成
	 * 
	 * →勤務NO = 1と時間帯を作成するだけです
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * → 時間帯：勤務NO = 1と２
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test(@Mocked DailyRecordToAttendanceItemConverter converter) {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.addTimeLeavNo(2);// No1 and no2
		new Expectations() {
			{
				require.findByWorkTimeCode(anyString, anyString);
				result = Optional.of(create(666, 134));

				converter.setData((IntegrationOfDaily) any).toDomain();
				result = dailyApp.getDomain();

				require.calculateForRecord((CalculateOption) any, (List<IntegrationOfDaily>) any,
						(Optional<ManagePerCompanySet>) any, (ExecutionType) any);
				result = Arrays.asList(dailyApp.getDomain());
			}
		};

		CreateWorkMaxTimeZone.process(require, cid, dailyApp.getDomain());

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getStampOfAttendance().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getStampOfLeave().get().getTimeDay().getTimeWithDay().get().v())
				.contains(Tuple.tuple(1, 666, 800));

	}

	private PredetemineTimeSetting create(int startDateClock, int rangeTimeDay) {

		return new PredetemineTimeSetting(cid, new AttendanceTime(rangeTimeDay), new WorkTimeCode("001"),
				new PredetermineTime(), true, new PrescribedTimezoneSetting(), new TimeWithDayAttr(startDateClock),
				true);

	}

}
