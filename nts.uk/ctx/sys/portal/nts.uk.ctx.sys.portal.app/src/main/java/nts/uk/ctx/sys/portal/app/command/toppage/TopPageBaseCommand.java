package nts.uk.ctx.sys.portal.app.command.toppage;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;

@Data
public class TopPageBaseCommand {
	
	/** The Company id. */
	public String companyId;
	
	/** The top page code. */
	public String topPageCode;
	
	/** The top page name. */
	public String topPageName;
	
	/** The Language number. */
	public Integer languageNumber;
	
	public TopPage toDomain(){
		return  TopPage.createFromJavaType(companyId, topPageCode,"", topPageName, languageNumber);
	}
}
