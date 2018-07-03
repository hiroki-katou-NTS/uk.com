package nts.uk.ctx.sys.portal.infra.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.infra.entity.layout.CcgmtLayout;
import nts.uk.ctx.sys.portal.infra.entity.layout.CcgmtLayoutPK;

/**
 * @author LamDT
 */
@Stateless
public class JpaLayoutRepository extends JpaRepository implements LayoutRepository {

	private static final String SELECT_SINGLE = "SELECT c FROM CcgmtLayout c WHERE c.ccgmtLayoutPK.layoutID = :layoutID";
	private static final String SELECT_ALL = "SELECT c FROM CcgmtLayout c WHERE c.ccgmtLayoutPK.companyID = :companyID";
	private static final String SELECT_BY_TYPE = "SELECT c FROM CcgmtLayout c WHERE c.ccgmtLayoutPK.companyID = :companyID AND c.pgType = :pgType";

	@Override
	public Optional<Layout> find(String layoutID) {
		return this.queryProxy().query(SELECT_SINGLE, CcgmtLayout.class)
				.setParameter("layoutID", layoutID)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public List<Layout> findAll(String companyID) {
		return this.queryProxy().query(SELECT_ALL, CcgmtLayout.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<Layout> findByType(String companyID, int pgType) {
		return this.queryProxy().query(SELECT_BY_TYPE, CcgmtLayout.class)
				.setParameter("companyID", companyID)
				.setParameter("pgType", pgType)
				.getList(c -> toDomain(c));
	}

	@Override
	public void remove(String companyID, String layoutID) {
		this.commandProxy().remove(CcgmtLayout.class, new CcgmtLayoutPK(companyID, layoutID));
		this.getEntityManager().flush();
	}

	@Override
	public void add(Layout layout) {
		this.commandProxy().insert(toEntity(layout));
	}

	@Override
	public void update(Layout layout) {
		CcgmtLayout newEntity = toEntity(layout);
		CcgmtLayout updatedEntity = this.queryProxy().find(newEntity.ccgmtLayoutPK, CcgmtLayout.class).get();
		updatedEntity.pgType = newEntity.pgType;
		this.commandProxy().update(updatedEntity);
	}

	/**
	 * Convert entity to domain
	 * 
	 * @param entity CcgmtLayout
	 * @return Layout instance
	 */
	private Layout toDomain(CcgmtLayout entity) {
		return Layout.createFromJavaType(entity.ccgmtLayoutPK.companyID, entity.ccgmtLayoutPK.layoutID, entity.pgType);
	}

	/**
	 * Convert domain to entity
	 * 
	 * @param domain Layout
	 * @return CcgmtLayout instance
	 */
	private CcgmtLayout toEntity(Layout domain) {
		return new CcgmtLayout(
			new CcgmtLayoutPK(domain.getCompanyID(), domain.getLayoutID()),
			domain.getPgType().value);
	}
	
}