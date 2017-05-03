package nts.uk.ctx.sys.portal.app.command.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.shr.com.context.AppContexts;

@Data
public class TopPageBaseCommand {
	
	/** The top page code. */
	public String topPageCode;
	
	/** The layout id. */
	public String layoutId;
	
	/** The top page name. */
	public String topPageName;
	
	/** The Language number. */
	public Integer languageNumber;
	
	public TopPage toDomain(){
		String companyId = AppContexts.user().companyID();
		return  TopPage.createFromJavaType(companyId,topPageCode,layoutId, topPageName, languageNumber);
	}
}
