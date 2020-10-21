package nts.uk.ctx.sys.portal.dom.notice.adapter;

import lombok.Builder;
import lombok.Data;

/**
 * Class RoleImport
 */
@Data
@Builder
public class RoleImport {
	
	/** The company Id. */
	public String companyId;

	/** The role id. */
	public String roleId;

	/** The role code. */
	public String roleCode;

	/** The role name. */
	public String roleName;
	
	/** The assign atr. */
	private Integer assignAtr; 
	
	private Integer employeeReferenceRange;
}
