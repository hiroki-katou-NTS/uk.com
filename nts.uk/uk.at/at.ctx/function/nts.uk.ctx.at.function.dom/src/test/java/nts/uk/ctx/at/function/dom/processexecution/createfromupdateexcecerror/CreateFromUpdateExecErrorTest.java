package nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror;

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
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror.CreateFromUpdateExecError.Require;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

@RunWith(JMockit.class)
public class CreateFromUpdateExecErrorTest {

	@Injectable
	private Require require;
	
	/**
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時 NOT EMPTY
	 * [R-3] 過去の実行平均時間を超過しているか = FALSE
	 */
	@Test
	public void CreateFromUpdateExecErrorTest1() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningNotEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.isPassAverageExecTimeExceeded(cid, ugpdateExecItemsWithErrors.get(0).getExecItemCd(), ugpdateExecItemsWithErrors.get(0).getLastExecDateTime().get());
				result = false;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時 NOT EMPTY
	 * [R-3] 過去の実行平均時間を超過しているか = TRUE
	 */
	@Test
	public void CreateFromUpdateExecErrorTest2() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningNotEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.isPassAverageExecTimeExceeded(cid, ugpdateExecItemsWithErrors.get(0).getExecItemCd(), ugpdateExecItemsWithErrors.get(0).getLastExecDateTime().get());
				result = true;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時  EMPTY
	 * [R-3] 過去の実行平均時間を超過しているか = TRUE
	 */
	@Test
	public void CreateFromUpdateExecErrorTest3() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 = システム日時
	 */
	@Test
	public void CreateFromUpdateExecErrorTest4() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now();
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 > システム日時
	 */
	@Test
	public void CreateFromUpdateExecErrorTest5() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now().addDays(1);
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 NULL
	 */
	@Test
	public void CreateFromUpdateExecErrorTest6() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = null;
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 < システム日時
	 */
	@Test
	public void CreateFromUpdateExecErrorTest7() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now().addDays(-1);
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * 現在の実行状態  NOT PRESENT
	 */
	@Test
	public void CreateFromUpdateExecErrorTest8() {
		
		//given
		String cid = "cid";
		
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-5]
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listEmpId = new ArrayList<String>();
		listEmpId.add("sid");
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(cid);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(cid);
				result = executionTaskSettings;
			}
			{
				require.getListEmpID(cid, referenceDate);
				result = listEmpId;
			}
		};
	
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> CreateFromUpdateExecError.create(require, cid));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
}
