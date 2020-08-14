package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class AffWorkplaceGroupImport {
	/** 職場グループID */
	private final String WKPGRPID;
	
	/** 職場ID */
	private String WKPID;
	
	
}
