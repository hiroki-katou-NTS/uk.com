/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenu;
import nts.uk.shr.com.context.AppContexts;

@Value
public class UpdateTitleMenuCommand {
	private String titleMenuCD;

	private String name;

	private String layoutID;

	public TitleMenu toDomain() {
		return TitleMenu.createFromJavaType(
				AppContexts.user().companyID(),
				this.titleMenuCD,
				this.name,
				this.layoutID);
	}
}
