/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import lombok.Getter;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.shr.com.context.AppContexts;
@Getter
public class CreateTitleMenuCommand {

	private String titleMenuCD;
	
	private String name;
	
	private String layoutID;
	
	public TitleMenu toDomain () {
		return TitleMenu.createFromJavaType(AppContexts.user().companyID(),
				this.titleMenuCD,
				this.name,
				this.layoutID);
	}
	}
