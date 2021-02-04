package nts.uk.ctx.at.shared.dom.adapter.holidaymanagement;

import lombok.Builder;
import lombok.Data;

/**
 * The Class CompanyInfo.
 */
@Data
@Builder
public class CompanyInfo {
	
	/**  会社コード. */
	private String companyCode;
	
	/**  会社名. */
	private String companyName;
	
	/**  会社ID. */
	private String companyId;
	
	/**  廃止区分. */
	private int isAbolition;
}
