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
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting.RequireM7;
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class CompensatoryLeaveComSettingTest {

	@Injectable
	private RequireM7 require;
	
	public CompensatoryLeaveComSetting create() {
		return new CompensatoryLeaveComSetting("DUMMY-CID",
				ManageDistinct.YES,
				new CompensatoryAcquisitionUse(ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW, DeadlCheckMonth.ONE_MONTH, TermManagement.MANAGE_BASED_ON_THE_DATE),
				new SubstituteHolidaySetting(new HolidayWorkHourRequired(false, new TimeSetting()), new OvertimeHourRequired(false, new TimeSetting())),
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
				ManageDistinct.YES);
	}
	
	/**
	 * Test 時間代休を管理するか判断する
	 */
	@Test
	public void testIsManagedTime() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean isManagedTime = domain.isManagedTime(require);
		assertThat(isManagedTime);
	}
	
	/**
	 * Test [7] 利用する休暇時間の消化単位をチェックする: No1
	 */
	@Test
	public void testCheckVacationTimeUnitUsed1() {
		val domain = create();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.empty();
			}
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, "DUMMY-CID", AttendanceTime.ZERO, "DUMMY-SID", GeneralDate.today());
		assertThat(checkVacationTimeUnitUsed);
	}
	
	/**
	 * Test [7] 利用する休暇時間の消化単位をチェックする: No2
	 */
	@Test
	public void testCheckVacationTimeUnitUsed2() {
		val domain = create();
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
				result = new OptionLicense() {};
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, "DUMMY-CID", AttendanceTime.ZERO, "DUMMY-SID", dummyDate);
		assertThat(checkVacationTimeUnitUsed);
	}
	
	/**
	 * Test [8]雇用設定に従う時間代休を管理するかどうか判断する: No1
	 */
	@Test
	public void testManageTimeOffAccordingEmpSettings1() {
		val domain = create();
		val dummyDate = GeneralDate.today();
		new Expectations() {
			{
				require.getCmpLeaveComSet("DUMMY-CID");
				result = Optional.empty();
			}
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean manageTimeOffAccordingEmpSettings = domain.manageTimeOffAccordingEmpSettings(require, "DUMMY-CID", "DUMMY-SID", dummyDate);
		assertThat(manageTimeOffAccordingEmpSettings);
	}
	
	/**
	 * Test [8]雇用設定に従う時間代休を管理するかどうか判断する: No2
	 */
	@Test
	public void testManageTimeOffAccordingEmpSettings2() {
		val domain = create();
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
				result = new OptionLicense() {};
			}
		};
		boolean manageTimeOffAccordingEmpSettings = domain.manageTimeOffAccordingEmpSettings(require, "DUMMY-CID", "DUMMY-SID", dummyDate);
		assertThat(manageTimeOffAccordingEmpSettings);
	}
}
