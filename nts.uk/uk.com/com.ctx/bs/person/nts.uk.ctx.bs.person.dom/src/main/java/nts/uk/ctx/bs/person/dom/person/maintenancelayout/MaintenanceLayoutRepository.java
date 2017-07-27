/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.newlayout.LayoutID;

/**
 * @author laitv
 *
 */
public interface MaintenanceLayoutRepository {
	
	void add(MaintenanceLayout maintenanceLayout);
	
	void update(MaintenanceLayout maintenanceLayout);
	
	void remove(LayoutID maintenanceLayoutID);
	
	Optional<MaintenanceLayout> findSingleLayout(LayoutID layoutID);
	
}
