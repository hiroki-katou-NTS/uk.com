package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.DomainObject;

/**
 * 職場::職場ID
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class ProcessExecutionScopeItem extends DomainObject {

	/* 会社ID */
	public String companyId;
	
	/* コード */
	public String execItemCd;
	
	/* 職場実行範囲 */
	public String wkpId;

}
