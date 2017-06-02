/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class TopPageBaseCommand {
	
	/** The top page code. */
	private String topPageCode;
	
	/** The layout id. */
	private String layoutId;
	
	/** The top page name. */
	private String topPageName;
	
	/** The Language number. */
	private Integer languageNumber;
	
	public TopPage toDomain(){
		String companyId = AppContexts.user().companyId();
		return  TopPage.createFromJavaType(companyId,topPageCode,layoutId, topPageName, languageNumber);
	}
}
