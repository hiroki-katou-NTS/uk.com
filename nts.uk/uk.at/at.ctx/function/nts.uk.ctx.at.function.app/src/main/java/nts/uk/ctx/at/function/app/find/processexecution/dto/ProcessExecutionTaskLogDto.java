package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;

@Data
public class ProcessExecutionTaskLogDto {

	/* 更新処理 */
	public int taskId;
	
	public String taskName;
	
	/* 終了状態 */
	public Integer statusCd;
	
	/* 終了状態 */
	public String status;
	
	public ProcessExecutionTaskLogDto() {
		super();
	}

	public ProcessExecutionTaskLogDto(int taskId, String taskName, Integer statusCd, String status) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.statusCd = statusCd;
		this.status = status;
	}
	
	
	public static ProcessExecutionTaskLogDto fromDomain(ExecutionTaskLog domain) {
		return new ProcessExecutionTaskLogDto(
				domain.getProcExecTask().value,
				EnumAdaptor.valueOf(domain.getProcExecTask().value, ProcessExecutionTask.class).name,
				(domain.getStatus()!=null && domain.getStatus().isPresent())?domain.getStatus().get().value:null ,
						(domain.getStatus()!=null && domain.getStatus().isPresent())?EnumAdaptor.valueOf(domain.getStatus().get().value , EndStatus.class).name:null);
				
	}






}
