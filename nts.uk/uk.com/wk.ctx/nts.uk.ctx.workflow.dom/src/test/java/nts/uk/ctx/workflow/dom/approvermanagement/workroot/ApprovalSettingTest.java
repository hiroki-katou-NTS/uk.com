package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@RunWith(JMockit.class)
public class ApprovalSettingTest {

	private static final String CID = "cid";

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(new ApprovalSetting());
	}

	/**
	 * [C-1] 社員単位だけ利用するで作成する
	 */
	@Test
	public void testCreateForEmployee() {
		
		// when
		ApprovalSetting domain = ApprovalSetting.createForEmployee(CID);
		
		// then
		assertThat(domain.getPrinFlg()).isFalse();
		assertThat(domain.getApproverRegsterSet()).isEqualTo(ApproverRegisterSet.createForEmployee());
	}

	/**
	 * [1] 運用モードにより単位変更する
	 * 運用モード = 就業担当者が行う
	 */
	@Test
	public void testChangeUnitPersonInCharge() {
		
		// given
		ApproverRegisterSet approverRegisterSet = new ApproverRegisterSet(UseClassification.DO_USE,
				UseClassification.DO_NOT_USE, UseClassification.DO_USE);
		ApprovalSetting domain = new ApprovalSetting(CID, approverRegisterSet, true);
		
		// when
		domain.changeUnit(OperationMode.PERSON_IN_CHARGE);
		
		// then
		assertThat(domain.getApproverRegsterSet()).isEqualTo(approverRegisterSet);
	}
	
	/**
	 * [1] 運用モードにより単位変更する
	 * 運用モード = 上長・社員が行う
	 */
	@Test
	public void testChangeUnitSuperiorsEmployees() {
		
		// given
		ApproverRegisterSet approverRegisterSet = new ApproverRegisterSet(UseClassification.DO_USE,
				UseClassification.DO_NOT_USE, UseClassification.DO_USE);
		ApprovalSetting domain = new ApprovalSetting(CID, approverRegisterSet, true);
		
		// when
		domain.changeUnit(OperationMode.SUPERIORS_EMPLOYEE);
		
		// then
		assertThat(domain.getApproverRegsterSet()).isEqualTo(ApproverRegisterSet.createForEmployee());
	}
}
