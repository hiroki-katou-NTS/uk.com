package nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror.CreateFromUpdateAutoRunError.Require;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;

@RunWith(JMockit.class)
public class CreateFromUpdateAutoRunErrorTest {

	@Injectable
	private Require require;
	
	/**
	 * List<更新処理自動実行管理> NOT EMPTY
	 * 前回終了日時 EMPTY => CAN NOT SORT => FIND FIRST
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void CreateFromUpdateAutoRunErrorTest1() {
		
		//given
		List<ProcessExecutionLogManage> autorunItemsWithErrors = CreateFromUpdateAutoRunErrorHelper.mockR1LastExecEmpty();
		
		//[R-2]
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getAutorunItemsWithErrors(CreateFromUpdateAutoRunErrorHelper.CID);
				result = autorunItemsWithErrors;
			}
			{
				require.getListEmpID(CreateFromUpdateAutoRunErrorHelper.CID, CreateFromUpdateAutoRunErrorHelper.REFER_DATE);
				result = listEmpId;
			}
		};
		
		//when
		AtomTask result = CreateFromUpdateAutoRunError.create(require, CreateFromUpdateAutoRunErrorHelper.CID);
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						(List<TopPageAlarmImport>) any, Optional.empty());
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						(List<TopPageAlarmImport>) any, Optional.empty());
				times = 1;
			}
		};
	}
	
	/**
	 * List<更新処理自動実行管理> NOT EMPTY
	 * 前回終了日時 NOT EMPTY => CAN SORT
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void CreateFromUpdateAutoRunErrorTest2() {
		
		//given
		List<ProcessExecutionLogManage> autorunItemsWithErrors = CreateFromUpdateAutoRunErrorHelper.mockR1LastExecNotEmpty();
		
		//[R-2]
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getAutorunItemsWithErrors(CreateFromUpdateAutoRunErrorHelper.CID);
				result = autorunItemsWithErrors;
			}
			{
				require.getListEmpID(CreateFromUpdateAutoRunErrorHelper.CID, CreateFromUpdateAutoRunErrorHelper.REFER_DATE);
				result = listEmpId;
			}
		};
		
		//when
		AtomTask result = CreateFromUpdateAutoRunError.create(require, CreateFromUpdateAutoRunErrorHelper.CID);
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						(List<TopPageAlarmImport>) any, Optional.empty());
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						(List<TopPageAlarmImport>) any, Optional.empty());
				times = 1;
			}
		};
	}
	
	/**
	 * List<更新処理自動実行管理> EMPTY
	 */
	@Test
	public void CreateFromUpdateAutoRunErrorTest3() {
		
		//given
		new Expectations() {
			{
				require.getListEmpID(CreateFromUpdateAutoRunErrorHelper.CID, CreateFromUpdateAutoRunErrorHelper.REFER_DATE);
				result = CreateFromUpdateAutoRunErrorHelper.LIST_EMP_ID;
			}
		};
		
		//when
		AtomTask result = CreateFromUpdateAutoRunError.create(require, CreateFromUpdateAutoRunErrorHelper.CID);
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateAutoRunErrorHelper
								.mockDeleteInfoAlarmImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateAutoRunErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateAutoRunErrorHelper
								.mockDeleteInfoAlarmImport()));
				times = 1;
			}
		};
	}
}
