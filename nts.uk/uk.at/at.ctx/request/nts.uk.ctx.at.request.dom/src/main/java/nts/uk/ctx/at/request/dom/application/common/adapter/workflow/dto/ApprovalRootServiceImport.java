package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;

@AllArgsConstructor
@Getter
public class ApprovalRootServiceImport {
	
	//エラーフラグ
	private ErrorFlagImport errorFlagExport;
	
	//task
	private Optional<AtomTask> task;

}
