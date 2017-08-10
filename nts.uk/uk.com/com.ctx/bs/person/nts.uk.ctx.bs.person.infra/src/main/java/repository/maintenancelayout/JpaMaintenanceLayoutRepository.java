/**
 * 
 */
package repository.maintenancelayout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;

import entity.maintenencelayout.PpemtMaintenanceLayout;
import entity.maintenencelayout.PpemtMaintenanceLayoutPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.bs.person.dom.person.layout.MaintenanceLayout;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaMaintenanceLayoutRepository extends JpaRepository implements IMaintenanceLayoutRepository {

	private String getAllMaintenanceLayout = "select c FROM  PpemtMaintenanceLayout c";

	private String getDetailLayout = getAllMaintenanceLayout + " Where c.ppemtMaintenanceLayoutPk.layoutId = :layoutId";

	private static final String IS_DUPLICATE_LAYOUTCODE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" FROM PpemtMaintenanceLayout e");
		builderString.append(" WHERE e.layoutCode = :layoutCode");
		builderString.append(" AND  e.companyId = :companyId");
		IS_DUPLICATE_LAYOUTCODE = builderString.toString();

	}

	private static MaintenanceLayout toDomain(PpemtMaintenanceLayout entity) {
		val domain = MaintenanceLayout.createFromJavaType(entity.companyId, entity.ppemtMaintenanceLayoutPk.layoutId,
				entity.layoutCode, entity.layoutName);
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
		this.commandProxy().insert(toEntity(maintenanceLayout));
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
	public boolean checkExit(String companyId, String layoutCode) {
		return this.queryProxy().query(IS_DUPLICATE_LAYOUTCODE, long.class).setParameter("layoutCode", layoutCode)
				.setParameter("companyId", companyId).getSingle().isPresent();
	}

	@Override
	public Optional<MaintenanceLayout> getById(String layoutId) {

		PpemtMaintenanceLayout entity = this.queryProxy().query(getDetailLayout, PpemtMaintenanceLayout.class)
				.setParameter("layoutId", layoutId).getSingleOrNull();
		if(entity == null) {
			return Optional.empty();
		}else {
			return Optional.of(toDomain(entity));
		}

	}
}
