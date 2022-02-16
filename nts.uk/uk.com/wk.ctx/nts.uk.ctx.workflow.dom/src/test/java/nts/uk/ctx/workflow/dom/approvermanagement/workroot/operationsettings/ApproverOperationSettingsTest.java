package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;

public class ApproverOperationSettingsTest {
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる 
	 * Case 0: 利用するレベル = null
	 */
	@Test
	public void testItemNameInforMatchLevel() {
		
		ApprovalLevelNo approvalLevelNo = null;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isFalse();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる 
	 * Case 1: 利用するレベル = 1 && 項目1の名称 not empty
	 */
	@Test
	public void testItemNameInforMatchLevel1() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.ONE_LEVEL;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isTrue();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 2: 利用するレベル = 1 && 項目1の名称 is empty
	 */
	@Test
	public void testItemNameInforMatchLevel2() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.ONE_LEVEL;
		ItemNameInformation itemNameInformation1 = ApproverOperationSettingsTestHelper.createNullName1();
		ItemNameInformation itemNameInformation2 = ApproverOperationSettingsTestHelper.createEmptyName1();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual1 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation1);
		boolean actual2 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation2);
		assertThat(actual1).isFalse();
		assertThat(actual2).isFalse();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 3: 利用するレベル = 2 && 項目1の名称, 項目2の名称 not empty
	 */
	@Test
	public void testItemNameInforMatchLevel3() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.TWO_LEVEL;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isTrue();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 4: 利用するレベル = 2 &&  項目1の名称|項目2の名称  is empty
	 */
	@Test
	public void testItemNameInforMatchLevel4() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.TWO_LEVEL;
		ItemNameInformation itemNameInformation1 = ApproverOperationSettingsTestHelper.createNullName1();
		ItemNameInformation itemNameInformation2 = ApproverOperationSettingsTestHelper.createEmptyName1();
		ItemNameInformation itemNameInformation3 = ApproverOperationSettingsTestHelper.createNullName2();
		ItemNameInformation itemNameInformation4 = ApproverOperationSettingsTestHelper.createEmptyName2();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual1 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation1);
		boolean actual2 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation2);
		boolean actual3 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation3);
		boolean actual4 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation4);
		
		assertThat(actual1).isFalse();
		assertThat(actual2).isFalse();
		assertThat(actual3).isFalse();
		assertThat(actual4).isFalse();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 5: 利用するレベル = 3 && 項目1の名称, 項目2の名称, 項目3の名称  not empty
	 */
	@Test
	public void testItemNameInforMatchLevel5() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.THREE_LEVEL;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isTrue();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 6: 利用するレベル = 3 && 項目1の名称|項目2の名称|項目3の名称  is empty
	 */
	@Test
	public void testItemNameInforMatchLevel6() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.THREE_LEVEL;
		ItemNameInformation itemNameInformation1 = ApproverOperationSettingsTestHelper.createNullName1();
		ItemNameInformation itemNameInformation2 = ApproverOperationSettingsTestHelper.createEmptyName1();
		ItemNameInformation itemNameInformation3 = ApproverOperationSettingsTestHelper.createNullName2();
		ItemNameInformation itemNameInformation4 = ApproverOperationSettingsTestHelper.createEmptyName2();
		ItemNameInformation itemNameInformation5 = ApproverOperationSettingsTestHelper.createNullName3();
		ItemNameInformation itemNameInformation6 = ApproverOperationSettingsTestHelper.createEmptyName3();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual1 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation1);
		boolean actual2 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation2);
		boolean actual3 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation3);
		boolean actual4 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation4);
		boolean actual5 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation5);
		boolean actual6 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation6);
		
		assertThat(actual1).isFalse();
		assertThat(actual2).isFalse();
		assertThat(actual3).isFalse();
		assertThat(actual4).isFalse();
		assertThat(actual5).isFalse();
		assertThat(actual6).isFalse();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 7: 利用するレベル = 4 && 項目1の名称, 項目2の名称, 項目3の名称, 項目4の名称  not empty
	 */
	@Test
	public void testItemNameInforMatchLevel7() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.FOUR_LEVEL;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isTrue();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 8: 利用するレベル = 4 && 項目1の名称|項目2の名称|項目3の名称|項目4の名称  is empty
	 */
	@Test
	public void testItemNameInforMatchLevel8() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.FOUR_LEVEL;
		ItemNameInformation itemNameInformation1 = ApproverOperationSettingsTestHelper.createNullName1();
		ItemNameInformation itemNameInformation2 = ApproverOperationSettingsTestHelper.createEmptyName1();
		ItemNameInformation itemNameInformation3 = ApproverOperationSettingsTestHelper.createNullName2();
		ItemNameInformation itemNameInformation4 = ApproverOperationSettingsTestHelper.createEmptyName2();
		ItemNameInformation itemNameInformation5 = ApproverOperationSettingsTestHelper.createNullName3();
		ItemNameInformation itemNameInformation6 = ApproverOperationSettingsTestHelper.createEmptyName3();
		ItemNameInformation itemNameInformation7 = ApproverOperationSettingsTestHelper.createNullName4();
		ItemNameInformation itemNameInformation8 = ApproverOperationSettingsTestHelper.createEmptyName4();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual1 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation1);
		boolean actual2 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation2);
		boolean actual3 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation3);
		boolean actual4 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation4);
		boolean actual5 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation5);
		boolean actual6 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation6);
		boolean actual7 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation7);
		boolean actual8 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation8);
		
		assertThat(actual1).isFalse();
		assertThat(actual2).isFalse();
		assertThat(actual3).isFalse();
		assertThat(actual4).isFalse();
		assertThat(actual5).isFalse();
		assertThat(actual6).isFalse();
		assertThat(actual7).isFalse();
		assertThat(actual8).isFalse();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 9: 利用するレベル = 5 && 項目1の名称, 項目2の名称, 項目3の名称, 項目4の名称, 項目5の名称  not empty
	 */
	@Test
	public void testItemNameInforMatchLevel9() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.FIVE_LEVEL;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual = NtsAssert.Invoke.privateMethod(
				instance,
				"isItemNameInforMatchLevel",
				approvalLevelNo,
				itemNameInformation);
		
		assertThat(actual).isTrue();
	}
	
	/**
	 * Test inv-1 項目の名称情報は利用するレベルに合わせる
	 * Case 10: 利用するレベル = 5 && 項目1の名称|項目2の名称|項目3の名称|項目4の名称|項目5の名称  is empty
	 */
	@Test
	public void testItemNameInforMatchLevel10() {
		
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.FIVE_LEVEL;
		ItemNameInformation itemNameInformation1 = ApproverOperationSettingsTestHelper.createNullName1();
		ItemNameInformation itemNameInformation2 = ApproverOperationSettingsTestHelper.createEmptyName1();
		ItemNameInformation itemNameInformation3 = ApproverOperationSettingsTestHelper.createNullName2();
		ItemNameInformation itemNameInformation4 = ApproverOperationSettingsTestHelper.createEmptyName2();
		ItemNameInformation itemNameInformation5 = ApproverOperationSettingsTestHelper.createNullName3();
		ItemNameInformation itemNameInformation6 = ApproverOperationSettingsTestHelper.createEmptyName3();
		ItemNameInformation itemNameInformation7 = ApproverOperationSettingsTestHelper.createNullName4();
		ItemNameInformation itemNameInformation8 = ApproverOperationSettingsTestHelper.createEmptyName4();
		ItemNameInformation itemNameInformation9 = ApproverOperationSettingsTestHelper.createEmptyName5();
		ItemNameInformation itemNameInformation10 = ApproverOperationSettingsTestHelper.createNullName5();
		ApproverOperationSettings instance = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		
		boolean actual1 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation1);
		boolean actual2 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation2);
		boolean actual3 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation3);
		boolean actual4 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation4);
		boolean actual5 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation5);
		boolean actual6 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation6);
		boolean actual7 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation7);
		boolean actual8 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation8);
		boolean actual9 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation9);
		boolean actual10 = NtsAssert.Invoke.privateMethod(instance, "isItemNameInforMatchLevel", approvalLevelNo, itemNameInformation10);
		
		assertThat(actual1).isFalse();
		assertThat(actual2).isFalse();
		assertThat(actual3).isFalse();
		assertThat(actual4).isFalse();
		assertThat(actual5).isFalse();
		assertThat(actual6).isFalse();
		assertThat(actual7).isFalse();
		assertThat(actual8).isFalse();
		assertThat(actual9).isFalse();
		assertThat(actual10).isFalse();
	}
	
	/**
	 * Test [C-1] 新規作成する
	 * Case 1: inv-1 = false
	 */
	@Test
	public void testFirstConstruct1() {
		OperationMode operationMode = OperationMode.SUPERIORS_EMPLOYEE;
		ItemNameInformation itemNameInformation = ApproverOperationSettingsTestHelper.createEmptyName1();
		
		NtsAssert.businessException("Msg_3311", () -> new ApproverOperationSettings(operationMode, itemNameInformation));
	}
	
	/**
	 * Test [C-1] 新規作成する
	 * Case 2: inv-1 = true
	 */
	@Test
	public void testFirstConstruct2() {
		OperationMode expOperationMode = OperationMode.SUPERIORS_EMPLOYEE;
		ItemNameInformation expItemNameInformation = ApproverOperationSettingsTestHelper.createDefaultItemName();
		ApproverSettingScreenInfor expApproverSettingScreenInfor = new ApproverSettingScreenInfor(expItemNameInformation);
		List<SettingTypeUsed> expSettingTypes = SettingTypeUsed.createWithoutUsingAttr();
		
		ApproverOperationSettings domain = new ApproverOperationSettings(expOperationMode, expItemNameInformation);
		
		assertThat(domain.getOperationMode()).isEqualTo(expOperationMode);
		assertThat(domain.getApprovalLevelNo()).isEqualTo(ApprovalLevelNo.FIVE_LEVEL);
		assertThat(domain.getApproverSettingScreenInfor()).isEqualToComparingFieldByField(expApproverSettingScreenInfor);
		assertThat(domain.getSettingTypeUseds()).hasSameSizeAs(expSettingTypes);
		
		for (int i = 0; i< domain.getSettingTypeUseds().size(); i++) {
			assertThat(domain.getSettingTypeUseds().get(i)).isEqualToComparingFieldByField(expSettingTypes.get(i));
		}
	}
	
	/**
	 * Test [1] 利用する承認フェーズを整理する 
	 */
	@Test
	public void testOrganizeApprovalPhaseToBeUsed() {
		List<ApprovalPhase> approvalPhases = ApproverOperationSettingsTestHelper.createApprovalPhases();
		OperationMode operationMode = OperationMode.SUPERIORS_EMPLOYEE;
		ApprovalLevelNo approvalLevelNo = ApprovalLevelNo.THREE_LEVEL;
		List<ApprovalPhase> expect = Arrays.asList(approvalPhases.get(2), approvalPhases.get(3), approvalPhases.get(4));
		
		ApproverOperationSettings domain = ApproverOperationSettingsTestHelper.createDefaultWithNullAttr();
		domain.setOperationMode(operationMode);
		domain.setApprovalLevelNo(approvalLevelNo);
		domain.setSettingTypeUseds(new ArrayList<SettingTypeUsed>());
		domain.setApproverSettingScreenInfor(null);
		
		List<ApprovalPhase> actual = domain.organizeApprovalPhaseToBeUsed(approvalPhases);
		
		assertThat(actual).isEqualTo(expect);
	}
	
	/**
	 * Test [2] 利用する申請を取得する
	 * Case 1: 申請種類が利用するか判断する is present
	 */
	@Test
	public void testGetApplicationToUse1() {
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<SettingTypeUsed>();
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithApplicationAndUse(ApplicationType.ANNUAL_HOLIDAY_APPLICATION));
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithApplicationAndUse(ApplicationType.ABSENCE_APPLICATION));
	
		ApproverOperationSettings domain = new ApproverOperationSettings(
				OperationMode.SUPERIORS_EMPLOYEE,
				ApprovalLevelNo.FIVE_LEVEL,
				new ArrayList<SettingTypeUsed>(),
				null);
		domain.setSettingTypeUseds(settingTypeUseds);
		
		List<ApplicationType> actual = domain.getApplicationToUse();
		
		assertThat(actual)
			.extracting(d -> d)
			.containsExactly(
					ApplicationType.ANNUAL_HOLIDAY_APPLICATION,
					ApplicationType.ABSENCE_APPLICATION);
	}
	
	/**
	 * Test [2] 利用する申請を取得する
	 * Case 2: 申請種類が利用するか判断する is empty
	 */
	@Test
	public void testGetApplicationToUse2() {
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<SettingTypeUsed>();
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithApplicationAndUse(ApplicationType.ANNUAL_HOLIDAY_APPLICATION));
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithAnyAndUse(ApplicationType.ABSENCE_APPLICATION));
	
		ApproverOperationSettings domain = new ApproverOperationSettings(
				OperationMode.SUPERIORS_EMPLOYEE,
				ApprovalLevelNo.FIVE_LEVEL,
				new ArrayList<SettingTypeUsed>(),
				null);
		domain.setSettingTypeUseds(settingTypeUseds);
		
		List<ApplicationType> actual = domain.getApplicationToUse();
		
		assertThat(actual)
			.extracting(d -> d)
			.containsExactly(
					ApplicationType.ANNUAL_HOLIDAY_APPLICATION);
	}
	
	/**
	 * Test [3] 利用する確認を取得する
	 * Case 1: 確認ルート種類が利用するか判断する is present
	 */
	@Test
	public void testGetConfirmationToUse1() {
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<SettingTypeUsed>();
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithConfirmAndUse(ConfirmationRootType.DAILY_CONFIRMATION));
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithConfirmAndUse(ConfirmationRootType.MONTHLY_CONFIRMATION));
		
		ApproverOperationSettings domain = new ApproverOperationSettings(
				OperationMode.SUPERIORS_EMPLOYEE,
				ApprovalLevelNo.FIVE_LEVEL,
				new ArrayList<SettingTypeUsed>(),
				null);
		domain.setSettingTypeUseds(settingTypeUseds);
		
		List<ConfirmationRootType> actual = domain.getConfirmationToUse();
		
		assertThat(actual)
			.extracting(d -> d)
			.containsExactly(
					ConfirmationRootType.DAILY_CONFIRMATION,
					ConfirmationRootType.MONTHLY_CONFIRMATION);
	}
	
	/**
	 * Test [3] 利用する確認を取得する
	 * Case 2: 確認ルート種類が利用するか判断する is empty
	 */
	@Test
	public void testGetConfirmationToUse2() {
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<SettingTypeUsed>();
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithConfirmAndUse(ConfirmationRootType.DAILY_CONFIRMATION));
		settingTypeUseds.add(SettingTypeUsedTestHelper.createWithConfirmAndNotUse(ConfirmationRootType.DAILY_CONFIRMATION));
		
		ApproverOperationSettings domain = new ApproverOperationSettings(
				OperationMode.SUPERIORS_EMPLOYEE,
				ApprovalLevelNo.FIVE_LEVEL,
				new ArrayList<SettingTypeUsed>(),
				null);
		domain.setSettingTypeUseds(settingTypeUseds);
		
		List<ConfirmationRootType> actual = domain.getConfirmationToUse();
		
		assertThat(actual)
		.extracting(d -> d)
		.containsExactly(ConfirmationRootType.DAILY_CONFIRMATION);
	}

}
