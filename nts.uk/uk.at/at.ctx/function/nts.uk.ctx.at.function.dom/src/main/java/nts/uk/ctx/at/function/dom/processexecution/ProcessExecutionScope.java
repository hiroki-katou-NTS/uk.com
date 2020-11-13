package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * 実行範囲
 */
@Getter
@AllArgsConstructor
public class ProcessExecutionScope extends DomainObject {

	/* 実行範囲区分 */
	private ExecutionScopeClassification execScopeCls;

	/* 基準日 */ //TODO
	private GeneralDate refDate;

	/* 職場実行範囲 */
	private List<ProcessExecutionScopeItem> workplaceIdList;

	/**
	 * Instantiates a new <code>ProcessExecutionScope</code>.
	 *
	 * @param execScopeCls    the exec scope cls
	 * @param refDate         the ref date
	 * @param workplaceIdList the workplace id list
	 */
	public ProcessExecutionScope(int execScopeCls, GeneralDate refDate, List<ProcessExecutionScopeItem> workplaceIdList) {
		this.execScopeCls = EnumAdaptor.valueOf(execScopeCls, ExecutionScopeClassification.class);
		this.refDate = refDate;
		this.workplaceIdList = workplaceIdList;
	}

}
