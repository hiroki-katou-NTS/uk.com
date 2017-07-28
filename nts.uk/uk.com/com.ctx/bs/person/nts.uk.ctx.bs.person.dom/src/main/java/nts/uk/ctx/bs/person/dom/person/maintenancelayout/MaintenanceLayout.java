/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutCode;
import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutName;

/**
 * @author laitv
 *
 */
public class MaintenanceLayout extends AggregateRoot {

	@Getter
	private String companyId;
	@Getter
	private LayoutCode layoutCode;
	@Getter
	private LayoutName layoutName;
	@Getter
	private String maintenanceLayoutID;

	/**
	 * @param companyId
	 * @param maintenanceLayoutID
	 * @param layoutCode
	 * @param layoutName
	 */
	public MaintenanceLayout(String companyId, String maintenanceLayoutID, LayoutCode layoutCode,
			LayoutName layoutName) {
		super();
		this.companyId = companyId;
		this.layoutCode = layoutCode;
		this.layoutName = layoutName;
		this.maintenanceLayoutID = maintenanceLayoutID;
	}

	public static MaintenanceLayout createFromJavaType(String companyId, String maintenanceLayoutID, String layoutCode,
			String layoutName) {
		return new MaintenanceLayout(companyId, maintenanceLayoutID, new LayoutCode(layoutCode),
				new LayoutName(layoutName));
	}

}
