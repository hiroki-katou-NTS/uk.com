package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getSEmpHistoryImport("DUMMY-SID", dummyDate);
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getSEmpHistoryImport("DUMMY-SID", dummyDate);
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
				result = Optional.of(new CompensatoryLeaveComSetting("DUMMT-CID",
						ManageDistinct.YES,
						null,
						null,
						new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
						ManageDistinct.YES));
			}
			{
				require.getSEmpHistoryImport("DUMMY-SID", dummyDate);
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
				require.compensatoryLeaveComSetting("DUMMY-CID");
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
