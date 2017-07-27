/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutCode;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutID;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutName;

/**
 * @author laitv
 *
 */
public class MaintenanceLayout extends AggregateRoot {

	private String companyId;
	private LayoutCode layoutCode;
	private LayoutName layoutName;
	private LayoutID maintenanceLayoutID;

	public MaintenanceLayout(String companyId, LayoutID maintenanceLayoutID, LayoutCode layoutCode,
			LayoutName layoutName) {
		super();
		this.companyId = companyId;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
		this.maintenanceLayoutID = maintenanceLayoutID;
	}

}
