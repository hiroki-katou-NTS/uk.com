package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;

@Data
@AllArgsConstructor
public class ProcessExecutionScopeDto {
	/* 実行範囲区分 */
	private int execScopeCls;
	
	/* 基準日 */
	private GeneralDate refDate;
	
	/* 職場実行範囲 */
	private List<String> wkpIdList;
	
	public static ProcessExecutionScopeDto fromDomain(ProcessExecutionScope domain){
		List<String> wkpIdList = domain.getWorkplaceIdList().stream().map(x -> x.wkpId).collect(Collectors.toList());
		return new ProcessExecutionScopeDto(domain.getExecScopeCls().value, domain.getRefDate(), wkpIdList);
	}
}
