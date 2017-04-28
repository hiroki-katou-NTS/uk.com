package nts.uk.ctx.sys.portal.app.command.layout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Getter
@Setter
@AllArgsConstructor
public class CreatePortalLayoutCommand {

	/** Enum PG Type */
	private int pgType;

	public Layout toDomain() {
		String companyID = AppContexts.user().companyID();
		return Layout.createFromJavaType(companyID, IdentifierUtil.randomUniqueId(), this.pgType);
	}
}