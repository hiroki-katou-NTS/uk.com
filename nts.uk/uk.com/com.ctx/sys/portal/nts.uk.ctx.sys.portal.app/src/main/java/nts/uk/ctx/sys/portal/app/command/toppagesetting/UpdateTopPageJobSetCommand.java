package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
public class UpdateTopPageJobSetCommand {
	private String jobId;
	private String topMenuCd;
	private String loginMenuCd;
	private int personPermissionSet;
	private int system;
	private int menuClassification;

	public TopPageJobSet toDomain(String companyId) {
		TopPageJobSet domain = TopPageJobSet.createFromJavaType(companyId, this.topMenuCd, this.loginMenuCd, this.jobId,
				this.personPermissionSet, this.system, this.menuClassification);
		return domain;
	}
}
