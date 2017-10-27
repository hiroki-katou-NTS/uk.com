/**
 * 
 */
package find.layout;

import java.util.List;

import find.layout.classification.LayoutPersonInfoClsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;


@AllArgsConstructor
@Data
public class MaintenanceLayoutDto {

	String companyId;
	String layoutCode;
	String layoutName;
	String maintenanceLayoutID;
	List<LayoutPersonInfoClsDto> listItemClsDto;

	public MaintenanceLayoutDto(String companyId, String layoutCode, String layoutName, String maintenanceLayoutID) {
		super();
		this.companyId = companyId;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
		this.maintenanceLayoutID = maintenanceLayoutID;
	}

	public static MaintenanceLayoutDto fromDomain(MaintenanceLayout domain) {
		return new MaintenanceLayoutDto(domain.getCompanyId(), domain.getLayoutCode().v(), domain.getLayoutName().v(),
				domain.getMaintenanceLayoutID());
	}
}
