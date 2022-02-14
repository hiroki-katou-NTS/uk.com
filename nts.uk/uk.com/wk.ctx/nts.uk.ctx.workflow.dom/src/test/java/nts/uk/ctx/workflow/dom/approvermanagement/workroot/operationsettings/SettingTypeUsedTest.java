package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class SettingTypeUsedTest {
	
	/**
	 * Test getter
	 */
	@Test
	public void testGetter() {
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createDefault();
		NtsAssert.invokeGetters(domain);
	}

	/**
	 * Test [C-1] 申請種類で作成する
	 */
	@Test
	public void testCreateByAppType() {
		ApplicationType expAppType = ApplicationType.ABSENCE_APPLICATION;
		
		SettingTypeUsed domain = new SettingTypeUsed(expAppType);
		
		assertThat(domain.getEmploymentRootAtr()).isEqualTo(EmploymentRootAtr.APPLICATION);
		assertThat(domain.getApplicationType().get()).isEqualTo(expAppType);
		assertThat(domain.getConfirmRootType()).isEmpty();
		assertThat(domain.getNotUseAtr()).isEqualTo(NotUseAtr.NOT_USE);
	}
	
	/**
	 * Test [C-2] 確認ルート種類で作成する	
	 */
	@Test
	public void testCreateWithconfirmRootType() {
		ConfirmationRootType expConfirmRootType = ConfirmationRootType.DAILY_CONFIRMATION;
		
		SettingTypeUsed domain = new SettingTypeUsed(expConfirmRootType);
		
		assertThat(domain.getEmploymentRootAtr()).isEqualTo(EmploymentRootAtr.CONFIRMATION);
		assertThat(domain.getApplicationType()).isEmpty();
		assertThat(domain.getConfirmRootType().get()).isEqualTo(expConfirmRootType);
		assertThat(domain.getNotUseAtr()).isEqualTo(NotUseAtr.NOT_USE);
	}
	
	/**
	 * Test [S-1] 全て種類利用しないで作成する: Case 1
	 */
	@Test
	public void testCreateWithoutUsingAttr() {
		List<ApplicationType> applicationTypes = Arrays.asList(ApplicationType.values());
		List<ConfirmationRootType> confirmationRootTypes = Arrays.asList(ConfirmationRootType.values());
		List<SettingTypeUsed> createdByAppTypes = applicationTypes.stream()
			.map(appType -> new SettingTypeUsed(appType))
			.collect(Collectors.toList());
		List<SettingTypeUsed> createdConfirmRootTypes = confirmationRootTypes.stream()
			.map(appType -> new SettingTypeUsed(appType))
			.collect(Collectors.toList());
		
		List<SettingTypeUsed> expSettingTypeUseds = Stream.of(createdByAppTypes, createdConfirmRootTypes)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		List<SettingTypeUsed> settingTypeUseds = SettingTypeUsed.createWithoutUsingAttr();
		
		assertThat(settingTypeUseds).hasSameSizeAs(expSettingTypeUseds);
		for (int i = 0; i < settingTypeUseds.size(); i++) {
			assertThat(settingTypeUseds.get(i)).isEqualToComparingFieldByField(expSettingTypeUseds.get(i));
		}
	}
	
	/**
	 * Test [1] 申請種類が利用するか判断する
	 * Case 1: ＠承認ルート区分＝＝「申請」 && @利用する == する
	 */
	@Test
	public void testDetermineAppTypeIsUsed1() {
		ApplicationType expect = ApplicationType.ANNUAL_HOLIDAY_APPLICATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithApplicationAndUse(expect);
		
		val actual = domain.determineAppTypeIsUsed();
		assertThat(actual.get()).isEqualTo(expect);
	}
	
	/**
	 * Test [1] 申請種類が利用するか判断する
	 * Case 2: ＠承認ルート区分＝＝「申請」 && @利用する == しない
	 */
	@Test
	public void testDetermineAppTypeIsUsed2() {
		ApplicationType applicationType = ApplicationType.ANNUAL_HOLIDAY_APPLICATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithApplicationAndNotUse(applicationType);
		
		val actual = domain.determineAppTypeIsUsed();
		assertThat(actual).isEmpty();
	}
	
	/**
	 * Test [1] 申請種類が利用するか判断する
	 * Case 3: ＠承認ルート区分!＝「申請」 && @利用する == ない
	 */
	@Test
	public void testDetermineAppTypeIsUsed3() {
		ApplicationType applicationType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithAnyAndUse(applicationType);
		
		val actual = domain.determineAppTypeIsUsed();
		assertThat(actual).isEmpty();
	}
	
	/**
	 * Test [1] 申請種類が利用するか判断する
	 * Case 4: ＠承認ルート区分!＝「申請」 && @利用する == しない
	 */
	@Test
	public void testDetermineAppTypeIsUsed4() {
		ApplicationType applicationType = ApplicationType.WORK_CHANGE_APPLICATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithAnyAndNotUse(applicationType);
		
		val actual = domain.determineAppTypeIsUsed();
		assertThat(actual).isEmpty();
	}
	
	/**
	 * Test [2] 確認ルート種類が利用するか判断する
	 * Case 1: ＠承認ルート区分＝＝「確認」 && @利用する == する	
	 */
	@Test
	public void testdDetermineConfirmRootTypeIsUsed1() {
		ConfirmationRootType expect = ConfirmationRootType.DAILY_CONFIRMATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithConfirmAndUse(expect);
		
		val actual = domain.determineConfirmRootTypeIsUsed();
		assertThat(actual.get()).isEqualTo(expect);
	}
	
	/**
	 * Test [2] 確認ルート種類が利用するか判断する
	 * Case 2: ＠承認ルート区分＝＝「確認」 && @利用する == しない
	 */
	@Test
	public void testdDetermineConfirmRootTypeIsUsed2() {
		ConfirmationRootType confirmRootType = ConfirmationRootType.MONTHLY_CONFIRMATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithConfirmAndNotUse(confirmRootType);
		
		val actual = domain.determineConfirmRootTypeIsUsed();
		assertThat(actual).isEmpty();
	}
	
	/**
	 * Test [2] 確認ルート種類が利用するか判断する
	 * Case 3: ＠承認ルート区分!＝「確認」 && @利用する == しない
	 */
	@Test
	public void testdDetermineConfirmRootTypeIsUsed3() {
		ConfirmationRootType confirmRootType = ConfirmationRootType.MONTHLY_CONFIRMATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithAnyAndNotUse(confirmRootType);
		
		val actual = domain.determineConfirmRootTypeIsUsed();
		assertThat(actual).isEmpty();
	}
	
	/**
	 * Test [2] 確認ルート種類が利用するか判断する
	 * Case 4: ＠承認ルート区分!＝「確認」 && @利用する == ない
	 */
	@Test
	public void testdDetermineConfirmRootTypeIsUsed4() {
		ConfirmationRootType confirmRootType = ConfirmationRootType.MONTHLY_CONFIRMATION;
		SettingTypeUsed domain = SettingTypeUsedTestHelper.createWithAnyAndUse(confirmRootType);
		
		val actual = domain.determineConfirmRootTypeIsUsed();
		assertThat(actual).isEmpty();
	}
}
