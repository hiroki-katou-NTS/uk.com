/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;

/**
 * @author danpv
 *
 */
@Data
public class EmpMaintLayoutDto {

	private String companyId;

	private String maintenanceLayoutID;

	private String layoutCode;

	private String layoutName;

	private List<EmpPersonInfoClassDto> classificationItems;

	public static EmpMaintLayoutDto createFromDomain(MaintenanceLayout domain) {
		EmpMaintLayoutDto dto = new EmpMaintLayoutDto();
		dto.setCompanyId(domain.getCompanyId());
		dto.setLayoutCode(domain.getLayoutCode().v());
		dto.setLayoutName(domain.getLayoutName().v());
		dto.setMaintenanceLayoutID(domain.getMaintenanceLayoutID());
		return dto;
	}

}
