/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.maintainlayout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtMaintenanceLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtMaintenanceLayoutPk;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaMaintenanceLayoutRepository extends JpaRepository implements IMaintenanceLayoutRepository {

	private static final String getAllMaintenanceLayout = "select c FROM  PpemtMaintenanceLayout c Where c.companyId = :companyId ORDER BY c.layoutCode ASC";

	private static final String getDetailLayout = "select c FROM  PpemtMaintenanceLayout c Where c.ppemtMaintenanceLayoutPk.layoutId = :layoutId AND c.companyId = :companyId";

	private static final String getDetailLayoutByCode = "select c FROM  PpemtMaintenanceLayout c Where c.layoutCode = :layoutCode  AND c.companyId = :companyId";

	private static final String IS_DUPLICATE_LAYOUTCODE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT e");
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
	public List<MaintenanceLayout> getAllMaintenanceLayout(String companyId) {
		return this.queryProxy().query(getAllMaintenanceLayout, PpemtMaintenanceLayout.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

	@Override
	public void add(MaintenanceLayout maintenanceLayout) {
		this.commandProxy().insert(toEntity(maintenanceLayout));
	}

	@Override
	public void update(MaintenanceLayout maintenanceLayout) {
		// TODO Auto-generated method stub
		this.commandProxy().update(toEntity(maintenanceLayout));

	}

	@Override
	public void remove(MaintenanceLayout domain) {
		PpemtMaintenanceLayoutPk pk = new PpemtMaintenanceLayoutPk(domain.getMaintenanceLayoutID());
		this.commandProxy().remove(PpemtMaintenanceLayout.class, pk);
	}

	@Override
	public Optional<MaintenanceLayout> getById(String companyId, String layoutId) {

		PpemtMaintenanceLayout entity = this.queryProxy().query(getDetailLayout, PpemtMaintenanceLayout.class)
				.setParameter("layoutId", layoutId).setParameter("companyId", companyId).getSingleOrNull();
		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entity));
		}

	}

	@Override
	public Optional<MaintenanceLayout> getByCode(String companyId, String layoutCode) {
		PpemtMaintenanceLayout entity = this.queryProxy().query(getDetailLayoutByCode, PpemtMaintenanceLayout.class)
				.setParameter("layoutCode", layoutCode).setParameter("companyId", companyId).getSingleOrNull();
		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entity));
		}
	}

	@Override
	public boolean checkExit(String companyId, String layoutCode) {
		return this.queryProxy().query(IS_DUPLICATE_LAYOUTCODE, long.class).setParameter("layoutCode", layoutCode)
				.setParameter("companyId", companyId).getSingle().isPresent();
	}

	@Override
	public boolean isNewLayout(String cpmpanyId, String layoutId) {
		PpemtMaintenanceLayout entity = this.queryProxy().query(getDetailLayout, PpemtMaintenanceLayout.class)
				.setParameter("layoutId", layoutId).setParameter("companyId", cpmpanyId).getSingleOrNull();
		return entity.getUpdDate() == null ? true: false;
	}

}
