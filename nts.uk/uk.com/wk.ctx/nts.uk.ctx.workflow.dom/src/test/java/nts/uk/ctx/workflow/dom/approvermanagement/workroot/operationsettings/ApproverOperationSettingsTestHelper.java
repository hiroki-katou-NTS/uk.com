package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;

public class ApproverOperationSettingsTestHelper {

	public static ApproverOperationSettings createDefaultWithNullAttr() {
		OperationMode operationMode = null;
		ApprovalLevelNo approvalLevelNo = null;
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<>();
		ApproverSettingScreenInfor approverSettingScreenInfor = null;
		
		return new ApproverOperationSettings(operationMode,
				approvalLevelNo,
				settingTypeUseds,
				approverSettingScreenInfor);
	}
	
	public static ApproverOperationSettings createByLevelAndSetting(ApprovalLevelNo approvalLevelNo, ItemNameInformation itemNames) {
		OperationMode operationMode = null;
		List<SettingTypeUsed> settingTypeUseds = new ArrayList<>();
		ApproverSettingScreenInfor approverSettingScreenInfor = new ApproverSettingScreenInfor(itemNames);
		
		return new ApproverOperationSettings(operationMode,
				approvalLevelNo,
				settingTypeUseds,
				approverSettingScreenInfor);
	}
	
	public static ItemNameInformation createDefaultItemName() {
		return new ItemNameInformation(new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createNullName1() {
		return new ItemNameInformation(null,
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createEmptyName1() {
		return new ItemNameInformation(new ApproverItemName(""),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createNullName2() {
		return new ItemNameInformation(
				new ApproverItemName("name 1"),
				null,
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createEmptyName2() {
		return new ItemNameInformation(new ApproverItemName("name 1"),
				new ApproverItemName(""),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createNullName3() {
		return new ItemNameInformation(
				new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				null,
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createEmptyName3() {
		return new ItemNameInformation(
				new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName(""),
				new ApproverItemName("name 4"),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createNullName4() {
		return new ItemNameInformation(
				new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				null,
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createEmptyName4() {
		return new ItemNameInformation(new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName(""),
				new ApproverItemName("name 5"));
	}
	
	public static ItemNameInformation createNullName5() {
		return new ItemNameInformation(
				new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				null);
	}
	
	public static ItemNameInformation createEmptyName5() {
		return new ItemNameInformation(new ApproverItemName("name 1"),
				new ApproverItemName("name 2"),
				new ApproverItemName("name 3"),
				new ApproverItemName("name 4"),
				new ApproverItemName(""));
	}
	
	/**
	 * Create List<ApprovalPhase> with 5 phases
	 */
	public static List<ApprovalPhase> createApprovalPhases() {
		List<ApprovalPhase> approvalPhases = new ArrayList<>();
		approvalPhases.add(ApprovalPhase.createSimpleFromJavaType("exp-approvalId", 1, "exp-approverId1"));
		approvalPhases.add(ApprovalPhase.createSimpleFromJavaType("exp-approvalId", 2, "exp-approverId2"));
		approvalPhases.add(ApprovalPhase.createSimpleFromJavaType("exp-approvalId", 3, "exp-approverId3"));
		approvalPhases.add(ApprovalPhase.createSimpleFromJavaType("exp-approvalId", 4, "exp-approverId4"));
		approvalPhases.add(ApprovalPhase.createSimpleFromJavaType("exp-approvalId", 5, "exp-approverId5"));
		return approvalPhases;
	}
	
}
