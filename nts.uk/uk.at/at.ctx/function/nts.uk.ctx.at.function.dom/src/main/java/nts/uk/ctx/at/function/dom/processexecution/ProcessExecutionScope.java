package nts.uk.ctx.at.function.dom.processexecution;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

import java.util.Optional;
/**
 * The class Process execution scope.<br>
 * Domain 実行範囲
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessExecutionScope extends DomainObject {

	/**
	 * The Execution scope classification.<br>
	 * 実行範囲区分
	 */
	private ExecutionScopeClassification execScopeCls;

	/**
	 * The Reference date.<br>
	 * 基準日
	 */
	private Optional<GeneralDate> refDate;

	/**
	 * The Workplace id list.<br>
	 * 職場実行範囲
	 */
	private List<String> workplaceIdList;

	/**
	 * Instantiates a new <code>ProcessExecutionScope</code>.
	 *
	 * @param execScopeCls    the execution scope classification
	 * @param refDate         the reference date
	 * @param workplaceIdList the workplace id list
	 */
	public ProcessExecutionScope(int execScopeCls, GeneralDate refDate, List<String> workplaceIdList) {
		this.execScopeCls = EnumAdaptor.valueOf(execScopeCls, ExecutionScopeClassification.class);
		this.refDate = Optional.ofNullable(refDate);
		this.workplaceIdList = workplaceIdList;
	}

}
