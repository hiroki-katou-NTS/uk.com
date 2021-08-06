package nts.uk.ctx.at.record.dom.daily.ouen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class CalculateAttendanceTimeBySupportWorkServiceTest {

	@Injectable
	private CalculateAttendanceTimeBySupportWorkService.Require require;

	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance = new ArrayList<>();
	private IntegrationOfDaily integrationOfDaily = CalculateAttendanceTimeBySupportWorkServiceHelper
			.getIntegrationOfDaily();
	private IntegrationOfDaily integrationOfDaily1 = CalculateAttendanceTimeBySupportWorkServiceHelper
			.getIntegrationOfDaily1();

	// Test $日別勤怠 isNotPersent
	@Test
	public void test() {

		new Expectations() {
			{
				require.get(empId, new DatePeriod(ymd, ymd));
			}
		};

		Optional<IntegrationOfDaily> result = CalculateAttendanceTimeBySupportWorkService.calculate(require, empId, ymd,
				ouenWorkTimeSheetOfDailyAttendance);

		assertThat((result).isPresent()).isFalse();
	}
//
//	// if $出退勤.出勤.isPresent AND $出退勤.退勤.isEmpty
	@Test	
	public void test1() {

		new Expectations() {
			{
				require.get(empId, new DatePeriod(ymd, ymd));
				result = Optional.of(integrationOfDaily);

				require.calculationIntegrationOfDaily(integrationOfDaily, ExecutionType.RERUN);
				result = integrationOfDaily;
			}
		};

		Optional<IntegrationOfDaily> result = CalculateAttendanceTimeBySupportWorkService.calculate(require, empId, ymd,
				ouenWorkTimeSheetOfDailyAttendance);

		assertThat((result).isPresent()).isTrue();
	}

	// ifnot $出退勤.出勤.isPresent AND !$出退勤.退勤.isEmpty
//	@Test
//	public void test2() {
//
//		new Expectations() {
//			{
//				require.get(empId, new DatePeriod(ymd, ymd));
//				result = Optional.of(integrationOfDaily1);
//
//				require.calculationIntegrationOfDaily(integrationOfDaily1, ExecutionType.RERUN);
//				result = integrationOfDaily1;
//			}
//		};
//
//		Optional<IntegrationOfDaily> result = CalculateAttendanceTimeBySupportWorkService.calculate(require, empId, ymd,
//				ouenWorkTimeSheetOfDailyAttendance);
//
////		assertThat((result).isPresent()).isTrue();
//	}
}
