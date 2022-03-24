package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.SetOperationModeDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.SetOperationModeDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ItemNameInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@RunWith(JMockit.class)
public class SetOperationModeDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = true 
	 * $自分の承認者の運用設定.isPresent() = true
	 */
	@Test
	public void testUpdate1() {

		// given
		ItemNameInformation itemNameInformation = DomainServiceTestHelper.mockItemNameInformation();
		new Expectations() {
			{
				require.getApprovalSetting(DomainServiceTestHelper.CID);
				result = Optional.of(DomainServiceTestHelper.mockApprovalSetting());
			};
			{
				require.getOperationSetting(DomainServiceTestHelper.CID);
				result = Optional.of(DomainServiceTestHelper.mockApproverOperationSettings());
			};
		};

		// when
		List<AtomTask> atomTasks = SetOperationModeDomainService.update(require, DomainServiceTestHelper.CID,
				OperationMode.PERSON_IN_CHARGE, Optional.of(itemNameInformation));
		
		// then
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.updateApprovalSetting(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.updateOperationSetting(any.get()));
	}
	
	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = false 
	 * $自分の承認者の運用設定.isPresent() = true
	 */
	@Test
	public void testUpdate2() {

		// given
		ItemNameInformation itemNameInformation = DomainServiceTestHelper.mockItemNameInformation();
		new Expectations() {
			{
				require.getOperationSetting(DomainServiceTestHelper.CID);
				result = Optional.of(DomainServiceTestHelper.mockApproverOperationSettings());
			};
		};

		// when
		List<AtomTask> atomTasks = SetOperationModeDomainService.update(require, DomainServiceTestHelper.CID,
				OperationMode.PERSON_IN_CHARGE, Optional.of(itemNameInformation));
		
		// then
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.insertApprovalSetting(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.updateOperationSetting(any.get()));
	}
	
	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = true 
	 * $自分の承認者の運用設定.isPresent() = false && 項目の名称情報.isEmpty() = false
	 */
	@Test
	public void testUpdate3() {

		// given
		ItemNameInformation itemNameInformation = DomainServiceTestHelper.mockItemNameInformation();
		new Expectations() {
			{
				require.getApprovalSetting(DomainServiceTestHelper.CID);
				result = Optional.of(DomainServiceTestHelper.mockApprovalSetting());
			};
		};

		// when
		List<AtomTask> atomTasks = SetOperationModeDomainService.update(require, DomainServiceTestHelper.CID,
				OperationMode.PERSON_IN_CHARGE, Optional.of(itemNameInformation));
		
		// then
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.updateApprovalSetting(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.insertOperationSetting(any.get()));
	}
	
	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = true 
	 * $自分の承認者の運用設定.isPresent() = false && 項目の名称情報.isEmpty() = true
	 */
	@Test
	public void testUpdate4() {

		// given
		new Expectations() {
			{
				require.getApprovalSetting(DomainServiceTestHelper.CID);
				result = Optional.of(DomainServiceTestHelper.mockApprovalSetting());
			};
		};

		// then
		NtsAssert.businessException("Msg_3311", () -> SetOperationModeDomainService.update(require,
				DomainServiceTestHelper.CID, OperationMode.PERSON_IN_CHARGE, Optional.empty()));
	}
	
	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = false 
	 * $自分の承認者の運用設定.isPresent() = false && 項目の名称情報.isEmpty() = false
	 */
	@Test
	public void testUpdate5() {

		// given
		ItemNameInformation itemNameInformation = DomainServiceTestHelper.mockItemNameInformation();
		// when
		List<AtomTask> atomTasks = SetOperationModeDomainService.update(require, DomainServiceTestHelper.CID,
				OperationMode.PERSON_IN_CHARGE, Optional.of(itemNameInformation));
		
		// then
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.insertApprovalSetting(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.insertOperationSetting(any.get()));
	}
	
	/**
	 * [1]変更する
	 * 
	 * $承認設定.isPresent() = false 
	 * $自分の承認者の運用設定.isPresent() = false && 項目の名称情報.isEmpty() = true
	 */
	@Test
	public void testUpdate6() {

		// then
		NtsAssert.businessException("Msg_3311", () -> SetOperationModeDomainService.update(require,
				DomainServiceTestHelper.CID, OperationMode.PERSON_IN_CHARGE, Optional.empty()));
	}
}
