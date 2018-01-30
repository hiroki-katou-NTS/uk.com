package nts.uk.ctx.at.function.dom.processexecution;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 実行範囲
 */
@Getter
@AllArgsConstructor
public class ProcessExecutionScope {
	/* 実行範囲区分 */
	private ExecutionScopeClassification execScopeCls;
	
	/* 基準日 */ //TODO
	private GeneralDate refDate;
	
	/* 職場実行範囲 */
	private List<ProcessExecutionScopeItem> workplaceIdList;
}
