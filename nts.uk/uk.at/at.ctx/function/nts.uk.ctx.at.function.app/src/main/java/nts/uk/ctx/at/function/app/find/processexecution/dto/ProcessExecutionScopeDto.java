package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;

/**
 * The class Process execution scope dto.<br>
 * Dto 実行範囲
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class ProcessExecutionScopeDto {

	/**
	 * The Execution scope classification<br>
	 * 実行範囲区分
	 */
	private int execScopeCls;

	/**
	 * The Reference date<br>
	 * 基準日
	 */
	private GeneralDate refDate;

	/**
	 * The Workplace id list<br>
	 * 職場実行範囲
	 */
	private List<String> wkpIdList;

	/**
	 * Converts domain <code>ProcessExecutionScope</code> to dto.
	 *
	 * @param domain the domain
	 * @return the Process execution scope dto
	 */
	public static ProcessExecutionScopeDto fromDomain(ProcessExecutionScope domain) {
		if (domain == null) {
			return null;
		}
		return new ProcessExecutionScopeDto(domain.getExecScopeCls().value,
											domain.getRefDate().orElse(null),
											domain.getWorkplaceIdList());
	}

	/**
	 * Converts <code>ProcessExecutionScopeDto</code> to domain.
	 *
	 * @return the domain Process execution scope
	 */
	public ProcessExecutionScope toDomain() {
		return new ProcessExecutionScope(this.execScopeCls, this.refDate, this.wkpIdList);
	}

}
