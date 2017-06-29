package nts.uk.ctx.sys.portal.app.command.layout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author LamDT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortalLayoutCommand {

	/** TopPage Code */
	private String parentCode;
	
	/** Layout GUID */
	private String layoutID;

	/** Enum PG Type */ 
	private int pgType;

	/** Convert to Domain  */
	public Layout toDomain() {
		return toDomain(this.layoutID);
	}

	/** Convert to Domain with given Id */
	public Layout toDomain(String layoutID) {
		String companyID = AppContexts.user().companyId();
		return Layout.createFromJavaType(companyID, layoutID, this.pgType);
	}

}