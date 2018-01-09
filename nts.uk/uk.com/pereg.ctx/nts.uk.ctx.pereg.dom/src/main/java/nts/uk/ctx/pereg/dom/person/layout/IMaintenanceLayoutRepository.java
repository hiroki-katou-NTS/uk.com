/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.layout;

import java.util.List;
import java.util.Optional;

/**
 * @author laitv
 * 
 */
public interface IMaintenanceLayoutRepository {

	void add(MaintenanceLayout maintenanceLayout);

	void update(MaintenanceLayout maintenanceLayout);

	void remove(MaintenanceLayout maintenanceLayout);

	boolean checkExit(String cpmpanyId, String layoutCode);
	
	boolean isNewLayout(String cpmpanyId, String layoutId);
	
	List<MaintenanceLayout> getAllMaintenanceLayout(String cid);

	Optional<MaintenanceLayout> getById(String cid, String id);

	Optional<MaintenanceLayout> getByCode(String cid, String id);

}
