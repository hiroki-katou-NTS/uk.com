package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting.RequireM7;

@RunWith(JMockit.class)
public class CompensatoryLeaveComSettingTest {

	@Injectable
	private RequireM7 require;
	
	@Test
	public void getters() {
		CompensatoryLeaveComSetting comSetting = new CompensatoryLeaveComSetting("000000000003-0006", ManageDistinct.NO,
				CompensatoryLeaveComSettingHelper.createCompensatoryAcquisitionUse(),
				CompensatoryLeaveComSettingHelper.createSubstituteHolidaySetting(),
				CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(), ManageDistinct.NO);
		NtsAssert.invokeGetters(comSetting);
	}

	/**
	 * Test [3] 代休に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItemsSubHolidays() {
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting();
		List<Integer> lstId = comSetting.getDailyAttendanceItemsSubHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(541, 542, 505, 517, 597, 603, 609, 615);
	}

	/**
	 * Test [4] 代休に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsSubHolidays() {
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting();
		List<Integer> lstId = comSetting.getMonthlyAttendanceItemsSubHolidays();
		assertThat(lstId).extracting(d -> d).containsExactly(1260, 1262, 1264, 1266, 1268, 188, 1666, 1667, 1668, 1669,
				1670);
	}

	/**
	 * Test [5] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItems() {
		// ========== CASE 1
		// 時間代休の消化単位.管理区分 = 管理しない
		TimeVacationDigestUnit timeUnit = CompensatoryLeaveComSettingHelper
				.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		
		new Expectations() {
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		
		// 管理区分 = 管理しない
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper
				.createCompensatoryLeaveComSetting(ManageDistinct.NO, timeUnit);
		List<Integer> lstId = comSetting.getDailyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理しない
		assertThat(lstId).extracting(d -> d).containsExactly(541, 542, 505, 517, 597, 603, 609, 615);

		// ========== CASE 2
		// 時間代休の消化単位.管理区分 = 管理する
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.YES);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getDailyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理する  && 管理区分 = 管理する
		assertThat(lstId.isEmpty()).isTrue();
		
		// ========== CASE 3
		// 時間代休の消化単位.管理区分 = 管理しない
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getDailyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理する
		assertThat(lstId).extracting(d -> d).containsExactly(505, 517, 597, 603, 609, 615);
	}
	
	/**
	 * Test [6] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlylyAttendanceItems() {
		// ========== CASE 1
		// 時間代休の消化単位.管理区分 = 管理しない
		TimeVacationDigestUnit timeUnit = CompensatoryLeaveComSettingHelper
				.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		
		new Expectations() {
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		
		// 管理区分 = 管理しない
		CompensatoryLeaveComSetting comSetting = CompensatoryLeaveComSettingHelper
				.createCompensatoryLeaveComSetting(ManageDistinct.NO, timeUnit);
		List<Integer> lstId = comSetting.getMonthlyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理しない
		assertThat(lstId).extracting(d -> d).containsExactly(1260, 1262, 1264, 1266, 1268, 188, 1666, 1667, 1668, 1669, 1670);

		// ========== CASE 2
		// 時間代休の消化単位.管理区分 = 管理する
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.YES);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getMonthlyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理する  && 管理区分 = 管理する
		assertThat(lstId.isEmpty()).isTrue();
		
		// ========== CASE 3
		// 時間代休の消化単位.管理区分 = 管理しない
		timeUnit = CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		// 管理区分 = 管理する
		comSetting = CompensatoryLeaveComSettingHelper.createCompensatoryLeaveComSetting(ManageDistinct.YES, timeUnit);
		lstId = comSetting.getMonthlyAttendanceItems(require);

		// Case 時間代休の消化単位.管理区分 = 管理しない && 管理区分 = 管理する
		assertThat(lstId).extracting(d -> d).containsExactly(188, 1666, 1667, 1668, 1669, 1670);
	}
	
	
	/**
	 * Test [2] 時間代休を管理するかどうか判断する
	 * Case 1: $Option.就業.時間休暇 = true
	 */
	@Test
	public void testIsManagedTime1() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean isManagedTime = domain.isManagedTime(require);
		assertThat(isManagedTime).isTrue();
	}
	
	/**
	 * Test [2] 時間代休を管理するかどうか判断する
	 * Case 2: $Option.就業.時間休暇 = false
	 */
	@Test
	public void testIsManagedTime2() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(false);
			}
		};
		boolean isManagedTime = domain.isManagedTime(require);
		assertThat(isManagedTime).isFalse();
	}
	
	/**
	 * Test [7] 利用する休暇時間の消化単位をチェックする: No1
	 */
	@Test
	public void testCheckVacationTimeUnitUsed1() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.empty();
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, "DUMMY-CID", new AttendanceTime(600), "DUMMY-SID", GeneralDate.today());
		assertThat(checkVacationTimeUnitUsed).isTrue();
	}
	
	/**
	 * Test [7] 利用する休暇時間の消化単位をチェックする: No2
	 */
	@Test
	public void testCheckVacationTimeUnitUsed2() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getEmploymentHis("DUMMY-SID", dummyDate);
				result = Optional.of(new SEmpHistoryImport("DUMMY-SID", "DUMMY-SCD", "DUMMY-SNAME", new DatePeriod(dummyDate, dummyDate)));
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, "DUMMY-CID", new AttendanceTime(600), "DUMMY-SID", dummyDate);
		assertThat(checkVacationTimeUnitUsed).isTrue();
	}
	
	/**
	 * Test [7] 利用する休暇時間の消化単位をチェックする:
	 * Case 3: 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed3() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getEmploymentHis("DUMMY-SID", dummyDate);
				result = Optional.of(new SEmpHistoryImport("DUMMY-SID", "DUMMY-SCD", "DUMMY-SNAME", new DatePeriod(dummyDate, dummyDate)));
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, "DUMMY-CID", new AttendanceTime(11), "DUMMY-SID", dummyDate);
		assertThat(checkVacationTimeUnitUsed).isFalse();
	}
	
	/**
	 * Test [8]雇用設定に従う時間代休を管理するかどうか判断する: No1
	 */
	@Test
	public void testManageTimeOffAccordingEmpSettings1() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.empty();
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean manageTimeOffAccordingEmpSettings = domain.manageTimeOffAccordingEmpSettings(require, "DUMMY-CID", "DUMMY-SID", dummyDate);
		assertThat(manageTimeOffAccordingEmpSettings).isFalse();
	}
	
	/**
	 * Test [8]雇用設定に従う時間代休を管理するかどうか判断する: No2
	 */
	@Test
	public void testManageTimeOffAccordingEmpSettings2() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getEmploymentHis("DUMMY-SID", dummyDate);
				result = Optional.of(new SEmpHistoryImport("DUMMY-SID", "DUMMY-SCD", "DUMMY-SNAME", new DatePeriod(dummyDate, dummyDate)));
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean manageTimeOffAccordingEmpSettings = domain.manageTimeOffAccordingEmpSettings(require, "DUMMY-CID", "DUMMY-SID", dummyDate);
		assertThat(manageTimeOffAccordingEmpSettings).isTrue();
	}
	
	/**
	 * Test [8]雇用設定に従う時間代休を管理するかどうか判断する
	 * Case 3: 管理区分 = 管理しない
	 */
	@Test
	public void testManageTimeOffAccordingEmpSettings3() {
		val domain = CompensatoryLeaveComSettingTestHelper.create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.NO,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour),
						ManageDistinct.NO));
			}
			{
				require.getOptionLicense();
				result = CompensatoryLeaveComSettingTestHelper.getOptionLicense(true);
			}
		};
		boolean manageTimeOffAccordingEmpSettings = domain.manageTimeOffAccordingEmpSettings(require, "DUMMY-CID", "DUMMY-SID", dummyDate);
		assertThat(manageTimeOffAccordingEmpSettings).isFalse();
	}
}
