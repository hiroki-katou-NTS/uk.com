/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutCode;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutName;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLayout extends AggregateRoot {

	private String companyId;
	private String maintenanceLayoutID;
	private LayoutCode layoutCode;
	private LayoutName layoutName;

	public static MaintenanceLayout createFromJavaType(String companyId, String maintenanceLayoutID, String layoutCode,
			String layoutName) {
		return new MaintenanceLayout(companyId, maintenanceLayoutID, new LayoutCode(layoutCode),
				new LayoutName(layoutName));
	}

}
