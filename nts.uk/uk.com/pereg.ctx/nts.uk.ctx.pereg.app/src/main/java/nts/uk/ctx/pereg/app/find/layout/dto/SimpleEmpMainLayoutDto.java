/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout.dto;

import lombok.Data;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;

/**
 * @author danpv
 *
 */
@Data
public class SimpleEmpMainLayoutDto {

	private String companyId;

	private String layoutCode;

	private String layoutName;

	private String maintenanceLayoutID;

	public SimpleEmpMainLayoutDto() {

	}

	public SimpleEmpMainLayoutDto(String companyId, String layoutCode, String layoutName, String maintenanceLayoutID) {
		this.companyId = companyId;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
		this.maintenanceLayoutID = maintenanceLayoutID;
	}

	public static SimpleEmpMainLayoutDto fromDomain(MaintenanceLayout domain) {
		return new SimpleEmpMainLayoutDto(domain.getCompanyId(), domain.getLayoutCode().v(), domain.getLayoutName().v(),
				domain.getMaintenanceLayoutID());
	}

}
