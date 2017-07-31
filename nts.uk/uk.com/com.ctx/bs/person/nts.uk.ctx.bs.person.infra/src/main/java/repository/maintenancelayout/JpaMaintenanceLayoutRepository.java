/**
 * 
 */
package repository.maintenancelayout;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
public class JpaMaintenanceLayoutRepository extends JpaRepository implements MaintenanceLayoutRepository{

	@Override
	public void add(MaintenanceLayout maintenanceLayout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MaintenanceLayout maintenanceLayout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String maintenanceLayoutID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<MaintenanceLayout> getDetailMaintenanceLayout(String layoutID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MaintenanceLayout> getAllMaintenanceLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
