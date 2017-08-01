/**
 * 
 */
package find.maintenancelayout;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutCode;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutName;

/**
 * @author laitv
 *
 */
@Value
public class MaintenanceLayoutDto {

	String companyId;
	String layoutCode;
	String layoutName;
	String maintenanceLayoutID;

	public static MaintenanceLayoutDto fromDomain(MaintenanceLayout domain) {
		return new MaintenanceLayoutDto(domain.getCompanyId(), domain.getLayoutCode().v(), domain.getLayoutName().v(),
				domain.getMaintenanceLayoutID());
	}
}
