package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.DomainObject;

/**
 * 職場::職場ID
 */
@Data
@AllArgsConstructor
public class ProcessExecutionScopeItem extends DomainObject {
	/* 会社ID */
	public String companyId;
	
	/* コード */
	public String execItemCd;
	
	/* 職場実行範囲 */
	public String wkpId;
	
	public static ProcessExecutionScopeItem createSimpleFromJavaType(String companyId, String execItemCd, String wkpId) {
		return new ProcessExecutionScopeItem(companyId, execItemCd, wkpId);
	}
}
