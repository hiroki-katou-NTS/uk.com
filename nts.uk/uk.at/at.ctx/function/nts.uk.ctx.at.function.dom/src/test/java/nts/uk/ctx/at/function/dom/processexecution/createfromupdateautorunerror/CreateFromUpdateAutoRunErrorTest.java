package nts.uk.ctx.at.function.dom.processexecution.createfromupdateautorunerror;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.AtomTaskAssert;
import nts.arc.time.GeneralDate;
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
	@Test
	public void CreateFromUpdateAutoRunErrorTest1() {
		//given
		String cid = "cid";
		GeneralDate referenceDate = GeneralDate.today();
		
		//[R-1]
		List<ProcessExecutionLogManage> autorunItemsWithErrors = CreateFromUpdateAutoRunErrorHelper.mockR1LastExecEmpty();
		
		//[R-2]
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getAutorunItemsWithErrors(cid);
				result = autorunItemsWithErrors;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> CreateFromUpdateAutoRunError.create(require, cid);
		
		 //then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * List<更新処理自動実行管理> NOT EMPTY
	 * 前回終了日時 NOT EMPTY => CAN SORT
	 */
	@Test
	public void CreateFromUpdateAutoRunErrorTest2() {
		//given
		String cid = "cid";
		GeneralDate referenceDate = GeneralDate.today();
		
		//[R-1]
		List<ProcessExecutionLogManage> autorunItemsWithErrors = CreateFromUpdateAutoRunErrorHelper.mockR1LastExecNotEmpty();
		
		//[R-2]
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getAutorunItemsWithErrors(cid);
				result = autorunItemsWithErrors;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> CreateFromUpdateAutoRunError.create(require, cid);
		
		 //then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * List<更新処理自動実行管理> EMPTY
	 */
	@Test
	public void CreateFromUpdateAutoRunErrorTest3() {
		//given
		String cid = "cid";
		GeneralDate referenceDate = GeneralDate.today();

		//[R-2]
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> CreateFromUpdateAutoRunError.create(require, cid);
		
		 //then
		AtomTaskAssert.atomTask(result);
	}
}
