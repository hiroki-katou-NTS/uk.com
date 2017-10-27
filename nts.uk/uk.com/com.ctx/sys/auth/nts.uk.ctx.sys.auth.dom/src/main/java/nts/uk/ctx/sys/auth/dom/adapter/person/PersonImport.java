/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.adapter.person;

import lombok.Builder;
import lombok.Data;

/**
 * The Class PersonImport.
 */
// 個人
@Data
@Builder
public class PersonImport {

	/** The person id. */
	private String personId;

	/** The person name. */
	private String personName;
	
	/** The mail address. */
	private MailAddress mailAddress;
	
}
