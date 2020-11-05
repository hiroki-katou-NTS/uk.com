package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.DAILY_CREATION.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.DAILY_CREATION.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.DAILY_CALCULATION.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.RFL_APR_RESULT.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.MONTHLY_AGGR.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.AL_EXTRACTION.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.APP_ROUTE_U_DAI.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
		this.taskLogList.add(ExecutionTaskLog.builder()
						.procExecTask(EnumAdaptor.valueOf(ProcessExecutionTask.APP_ROUTE_U_MON.value, ProcessExecutionTask.class))
						.status(Optional.ofNullable(EnumAdaptor.valueOf(EndStatus.NOT_IMPLEMENT.value, EndStatus.class)))
						.build()
		);
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