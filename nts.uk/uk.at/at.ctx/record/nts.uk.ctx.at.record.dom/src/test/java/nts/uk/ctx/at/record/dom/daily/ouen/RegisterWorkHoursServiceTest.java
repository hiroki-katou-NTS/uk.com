package nts.uk.ctx.at.record.dom.daily.ouen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.CalculateAttendanceTimeBySupportWorkService.Require;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class RegisterWorkHoursServiceTest {

	@Injectable
	private RegisterWorkHoursService.Require require;

	@Injectable
	private RegisterOuenWorkTimeOfDailyService.Require require2;

	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();
	private String cid = "cid";
	private EditStateSetting editStateSetting = EnumAdaptor.valueOf(0, EditStateSetting.class);

	// if 作業詳細一覧.isNotPresent
	@Test
	public void test() {

		List<WorkDetailsParam> workDetailsParams = new ArrayList<>();

		new MockUp<RegisterOuenWorkTimeSheetOfDailyService>() {
			@Mock
			public AtomTask register(RegisterOuenWorkTimeSheetOfDailyService.Require require, String empId,
					GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys,
					EditStateSetting editStateSetting) {
				return AtomTask.bundle(new ArrayList<>());
			}
		};

		ManHourInputResult result = RegisterWorkHoursService.register(require, cid, empId, ymd, editStateSetting,
				workDetailsParams);

		assertThat(result.getIntegrationOfDaily().isPresent()).isFalse();

	}

	// if 作業詳細一覧.isNotPresent
	@Test
	public void test_1() {

		List<WorkDetailsParam> workDetailsParams = RegisterWorkHoursServiceHelper.get();

		new MockUp<RegisterOuenWorkTimeSheetOfDailyService>() {
			@Mock
			public AtomTask register(RegisterOuenWorkTimeSheetOfDailyService.Require require, String empId,
					GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys,
					EditStateSetting editStateSetting) {
				return AtomTask.bundle(new ArrayList<>());
			}
		};

		new MockUp<CreateAttendanceTimeZoneForEachSupportWork>() {
			@Mock
			public List<OuenWorkTimeSheetOfDailyAttendance> create(
					nts.uk.ctx.at.record.dom.daily.ouen.CreateAttendanceTimeZoneForEachSupportWork.Require require,
					String empId, GeneralDate ymd, List<WorkDetailsParam> workDetailsParams) {
				return RegisterWorkHoursServiceHelper.getListOuenWorkTime();
			}
		};

		ManHourInputResult result = RegisterWorkHoursService.register(require, cid, empId, ymd, editStateSetting,
				workDetailsParams);

		assertThat(result.getIntegrationOfDaily().isPresent()).isFalse();

	}

	// if $計算結果.isPresent
	// ifnot $アラーム対象日.isPresent
	@Test
	public void test_2() {

		List<WorkDetailsParam> workDetailsParams = RegisterWorkHoursServiceHelper.get();

		new MockUp<RegisterOuenWorkTimeSheetOfDailyService>() {
			@Mock
			public AtomTask register(RegisterOuenWorkTimeSheetOfDailyService.Require require, String empId,
					GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys,
					EditStateSetting editStateSetting) {
				return AtomTask.bundle(new ArrayList<>());
			}
		};

		new MockUp<CreateAttendanceTimeZoneForEachSupportWork>() {
			@Mock
			public List<OuenWorkTimeSheetOfDailyAttendance> create(
					nts.uk.ctx.at.record.dom.daily.ouen.CreateAttendanceTimeZoneForEachSupportWork.Require require,
					String empId, GeneralDate ymd, List<WorkDetailsParam> workDetailsParams) {
				return RegisterWorkHoursServiceHelper.getListOuenWorkTime();
			}
		};

		new MockUp<CalculateAttendanceTimeBySupportWorkService>() {
			@Mock
			public Optional<IntegrationOfDaily> calculate(Require require, String empId, GeneralDate ymd,
					List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance) {
				return Optional.of(RegisterWorkHoursServiceHelper.getIntegrationOfDailyEmpty());
			}
		};

		ManHourInputResult result = RegisterWorkHoursService.register(require, cid, empId, ymd, editStateSetting,
				workDetailsParams);

		assertThat(result.getIntegrationOfDaily().isPresent()).isTrue();
		NtsAssert.atomTask(() -> result.getAtomTask(), any -> require.delete(empId, ymd, "T001"));
	}

	// if $計算結果.isPresent
	// if $アラーム対象日.isPresent
	@Test
	public void test_3() {

		List<WorkDetailsParam> workDetailsParams = RegisterWorkHoursServiceHelper.get();
		EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(cid, empId, ymd, new ErrorAlarmWorkRecordCode("T001"), new ArrayList<>());

		new MockUp<RegisterOuenWorkTimeSheetOfDailyService>() {
			@Mock
			public AtomTask register(RegisterOuenWorkTimeSheetOfDailyService.Require require, String empId,
					GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailys,
					EditStateSetting editStateSetting) {
				return AtomTask.bundle(new ArrayList<>());
			}
		};

		new MockUp<CreateAttendanceTimeZoneForEachSupportWork>() {
			@Mock
			public List<OuenWorkTimeSheetOfDailyAttendance> create(
					nts.uk.ctx.at.record.dom.daily.ouen.CreateAttendanceTimeZoneForEachSupportWork.Require require,
					String empId, GeneralDate ymd, List<WorkDetailsParam> workDetailsParams) {
				return RegisterWorkHoursServiceHelper.getListOuenWorkTime();
			}
		};

		new MockUp<CalculateAttendanceTimeBySupportWorkService>() {
			@Mock
			public Optional<IntegrationOfDaily> calculate(Require require, String empId, GeneralDate ymd,
					List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance) {
				return Optional.of(RegisterWorkHoursServiceHelper.getIntegrationOfDailyNotEmpty());
			}
		};

		ManHourInputResult result = RegisterWorkHoursService.register(require, cid, empId, ymd, editStateSetting,
				workDetailsParams);

		assertThat(result.getIntegrationOfDaily().isPresent()).isTrue();
		NtsAssert.atomTask(() -> result.getAtomTask(), any -> require.insert(employeeDailyPerError));
	}
}
