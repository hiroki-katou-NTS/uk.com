package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

/**
 * 更新処理自動実行ログ
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessExecutionLog extends AggregateRoot {
	
	protected static final ProcessExecutionTask[] TASK_SETTINGS = {
			ProcessExecutionTask.SCH_CREATION,
			ProcessExecutionTask.DAILY_CREATION,
			ProcessExecutionTask.DAILY_CALCULATION,
			ProcessExecutionTask.RFL_APR_RESULT,
			ProcessExecutionTask.MONTHLY_AGGR,
			ProcessExecutionTask.AL_EXTRACTION,
			ProcessExecutionTask.APP_ROUTE_U_DAI,
			ProcessExecutionTask.APP_ROUTE_U_MON
	};

	/* コード */
	private ExecutionCode execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 各処理の期間 */
	private Optional<EachProcessPeriod> eachProcPeriod;
	
	/* 各処理の終了状態 */
	private List<ExecutionTaskLog> taskLogList = new ArrayList<>();
	
	/* 実行ID */
	private String execId;
	
	public ProcessExecutionLog(ExecutionCode execItemCd, String companyId,
			String execId) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.eachProcPeriod = Optional.empty();
		this.taskLogList = new ArrayList<>();
		this.execId = execId;
	}

	public void initTaskLogList() {
		this.taskLogList = processInitTaskLog();
	}
	
	public static List<ExecutionTaskLog> processInitTaskLog() {
		return Arrays.stream(TASK_SETTINGS)
				.map(item -> ProcessExecutionLog.processInitTaskLog(item, EndStatus.NOT_IMPLEMENT))
				.collect(Collectors.toList());
	}
	
	private static ExecutionTaskLog processInitTaskLog(ProcessExecutionTask task, EndStatus endStatus) {
		return ExecutionTaskLog.builder()
			.procExecTask(EnumAdaptor.valueOf(task.value, ProcessExecutionTask.class))
			.status(Optional.ofNullable(EnumAdaptor.valueOf(endStatus.value, EndStatus.class)))
			.build();
	}
	
	public void setExecItemCd(ExecutionCode execItemCd) {
		this.execItemCd = execItemCd;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public void setEachProcPeriod(EachProcessPeriod eachProcPeriod) {
		this.eachProcPeriod = Optional.ofNullable(eachProcPeriod);
	}


	public void setTaskLogList(List<ExecutionTaskLog> taskLogList) {
		this.taskLogList = taskLogList;
	}


	public void setExecId(String execId) {
		this.execId = execId;
	}
}