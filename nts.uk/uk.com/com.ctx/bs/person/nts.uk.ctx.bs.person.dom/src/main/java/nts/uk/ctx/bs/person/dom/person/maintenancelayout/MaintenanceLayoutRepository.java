/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.maintenancelayout;

import java.util.List;
import java.util.Optional;

/**
 * @author laitv
 *
 */
public interface MaintenanceLayoutRepository {

	void add(MaintenanceLayout maintenanceLayout);

	void update(MaintenanceLayout maintenanceLayout);

	void remove(String maintenanceLayoutID);

	// Optional<MaintenanceLayout> checkExit( String layoutID);

	boolean checkExit(String cpmpanyId , String layoutCode);

	List<MaintenanceLayout> getAllMaintenanceLayout();

}
