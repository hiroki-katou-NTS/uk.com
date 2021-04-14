package nts.uk.ctx.at.function.dom.processexecution.createfromupdateexcecerror;

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
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
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
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningNotEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.isPassAverageExecTimeExceeded(CreateFromUpdateExecErrorHelper.CID, ugpdateExecItemsWithErrors.get(0).getExecItemCd(), ugpdateExecItemsWithErrors.get(0).getLastExecDateTime().get());
				result = false;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時 NOT EMPTY
	 * [R-3] 過去の実行平均時間を超過しているか = TRUE
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void CreateFromUpdateExecErrorTest2() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningNotEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.isPassAverageExecTimeExceeded(CreateFromUpdateExecErrorHelper.CID, ugpdateExecItemsWithErrors.get(0).getExecItemCd(), ugpdateExecItemsWithErrors.get(0).getLastExecDateTime().get());
				result = true;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						(List<TopPageAlarmImport>) any,
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						(List<TopPageAlarmImport>) any,
						Optional.empty());
				times = 1;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 実行中
	 * 前回実行日時  EMPTY
	 * [R-3] 過去の実行平均時間を超過しているか = TRUE
	 */
	@Test
	public void CreateFromUpdateExecErrorTest3() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusRunningEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();

		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 = システム日時
	 */
	@Test
	public void CreateFromUpdateExecErrorTest4() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now();
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 > システム日時
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void CreateFromUpdateExecErrorTest5() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now().addDays(1);
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						(List<TopPageAlarmImport>) any,
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						(List<TopPageAlarmImport>) any,
						Optional.empty());
				times = 1;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 NULL
	 */
	@Test
	public void CreateFromUpdateExecErrorTest6() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = null;
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
	
	/**
	 * 更新処理自動実行項目の状態 = 待機中
	 * $次回実行日時 < システム日時
	 */
	@Test
	public void CreateFromUpdateExecErrorTest7() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusWaiting();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		//[R-4]
		ExecutionTaskSetting execTaskSet = executionTaskSettings.get(0);
		GeneralDateTime r4Result = GeneralDateTime.now().addDays(-1);
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.processNextExecDateTimeCreation(execTaskSet);
				result = r4Result;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
	
	/**
	 * 現在の実行状態  NOT PRESENT
	 */
	@Test
	public void CreateFromUpdateExecErrorTest8() {
		
		//given
		//[R-1]
		List<ProcessExecutionLogManage> ugpdateExecItemsWithErrors = CreateFromUpdateExecErrorHelper.mockR1StatusEmpty();
		
		//[R-2]
		List<ExecutionTaskSetting> executionTaskSettings = CreateFromUpdateExecErrorHelper.mockR2();
		
		new Expectations() {
			{
				require.getUpdateExecItemsWithErrors(CreateFromUpdateExecErrorHelper.CID);
				result = ugpdateExecItemsWithErrors;
			}
			{
				require.getByCid(CreateFromUpdateExecErrorHelper.CID);
				result = executionTaskSettings;
			}
			{
				require.getListEmpID(CreateFromUpdateExecErrorHelper.CID, CreateFromUpdateExecErrorHelper.REFER_DATE);
				result = CreateFromUpdateExecErrorHelper.SIDS;
			}
		};
	
		//when
		AtomTask result = AtomTask.of(() -> CreateFromUpdateExecError.create(require, CreateFromUpdateExecErrorHelper.CID));
		
		// Before
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 0;
			}
		};

		// Execute
		result.run();

		// After
		new Verifications() {
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						CreateFromUpdateExecErrorHelper.mockListTopPageAlarmInport(),
						Optional.empty());
				times = 0;
			}
			{
				require.createAlarmData(CreateFromUpdateExecErrorHelper.CID, 
						Collections.emptyList(), 
						Optional.ofNullable(CreateFromUpdateExecErrorHelper.mockDelInfoImport()));
				times = 1;
			}
		};
	}
}
