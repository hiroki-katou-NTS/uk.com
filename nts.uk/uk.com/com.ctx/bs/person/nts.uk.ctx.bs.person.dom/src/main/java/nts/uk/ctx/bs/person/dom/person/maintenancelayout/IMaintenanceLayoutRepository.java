/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import java.util.List;

/**
 * @author laitv
 * 
 */
public interface IMaintenanceLayoutRepository {

	void add(MaintenanceLayout maintenanceLayout);

	void update(MaintenanceLayout maintenanceLayout);

	void remove(String maintenanceLayoutID);

	boolean checkExit(String cpmpanyId , String layoutCode);

	List<MaintenanceLayout> getAllMaintenanceLayout();

}
