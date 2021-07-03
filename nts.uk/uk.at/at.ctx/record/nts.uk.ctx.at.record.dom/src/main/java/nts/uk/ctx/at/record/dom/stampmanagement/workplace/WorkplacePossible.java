package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 職場可能
 * @author tutk
 *
 */
@Value
public class WorkplacePossible implements DomainValue {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 職場ID
	 */
	private String workpalceId;

	public WorkplacePossible(String companyId, String workpalceId) {
		super();
		this.companyId = companyId;
		this.workpalceId = workpalceId;
	}

	
}
