package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 職場::職場ID
 */
@Getter
@Setter
@AllArgsConstructor
public class ProcessExecutionScopeItem extends DomainObject {

	/* 会社ID */
	public String companyId;
	
	/* コード */
	public String execItemCd;
	
	/* 職場実行範囲 */
	public String wkpId;

}
