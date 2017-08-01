/**
 * 
 */
package repository.maintenancelayout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.maintenencelayout.PpemtMaintenanceLayout;
import entity.maintenencelayout.PpemtMaintenanceLayoutPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaMaintenanceLayoutRepository extends JpaRepository implements MaintenanceLayoutRepository {

	private String getAllMaintenanceLayout = "select c FROM  PpemtMaintenanceLayout c";

	private String getDetailLayout = getAllMaintenanceLayout + " Where c.ppemtMaintenanceLayoutPk.layoutId =: layoutId";

	private static MaintenanceLayout toDomain(PpemtMaintenanceLayout entity) {
		val domain = MaintenanceLayout.createFromJavaType(
				entity.companyId,
				entity.ppemtMaintenanceLayoutPk.layoutId,
				entity.layoutCode, 
				entity.layoutName);
		return domain;
	}

	private static PpemtMaintenanceLayout toEntity(MaintenanceLayout domain) {
		PpemtMaintenanceLayout entity = new PpemtMaintenanceLayout();
		entity.ppemtMaintenanceLayoutPk = new PpemtMaintenanceLayoutPk(domain.getMaintenanceLayoutID());
		entity.companyId = domain.getCompanyId();
		entity.layoutCode = domain.getLayoutCode().v();
		entity.layoutName = domain.getLayoutName().v();
		return entity;
	}

	@Override
	public List<MaintenanceLayout> getAllMaintenanceLayout() {
		return this.queryProxy().query(getAllMaintenanceLayout, PpemtMaintenanceLayout.class).getList(c -> toDomain(c));
	}

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

}
