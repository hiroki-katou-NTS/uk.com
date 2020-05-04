package nts.uk.ctx.bs.employee.app.command.workplace.group;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkplaceGroupResult {
	/** 職場グループコード */
	private  Optional<String> WKPGRPCode;
	
	/** 職場グループ名称 */
	private Optional<String> WKPGRPName;
}
