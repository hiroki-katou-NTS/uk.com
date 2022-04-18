package nts.uk.ctx.workflow.pub.service.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;

@AllArgsConstructor
@Getter
public class ApprovalRootServiceExport {
	
	//エラーフラグ
	private ErrorFlagExport errorFlagExport;
	
	//task
	private Optional<AtomTask> task;

}
