package nts.uk.ctx.sys.portal.infra.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.ctx.sys.portal.dom.layout.enums.PGType;
import nts.uk.ctx.sys.portal.infra.entity.layout.CcgmtLayout;
import nts.uk.ctx.sys.portal.infra.entity.layout.CcgmtLayoutPK;

/**
 * @author LamDT
 */
@Stateless
public class JpaLayoutRepository extends JpaRepository implements LayoutRepository {

	private final String SELECT_SINGLE = "SELECT c FROM CcgmtLayout c WHERE c.layoutID = :layoutID";
	private final String SELECT_ALL = "SELECT c FROM CcgmtLayout c WHERE c.CcgmtLayoutPK.companyID = :companyID";
	private final String SELECT_BY_TYPE = "SELECT c FROM CcgmtLayout c WHERE c.CcgmtLayoutPK.companyID = :companyID AND c.pgType = :pgType";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#find(String layoutID)
	 */
	@Override
	public Optional<Layout> find(String layoutID) {
		return this.queryProxy().query(SELECT_SINGLE, CcgmtLayout.class)
				.setParameter("layoutID", layoutID)
				.getSingle(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#findAll(String companyID)
	 */
	@Override
	public List<Layout> findAll(String companyID) {
		return this.queryProxy().query(SELECT_ALL, CcgmtLayout.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#findByType(String companyID, int pgType)
	 */
	@Override
	public List<Layout> findByType(String companyID, int pgType) {
		return this.queryProxy().query(SELECT_BY_TYPE, CcgmtLayout.class)
				.setParameter("companyID", companyID)
				.setParameter("pgType", pgType)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#remove(String companyID, String layoutID)
	 */
	@Override
	public void remove(String companyID, String layoutID) {
		this.commandProxy().remove(CcgmtLayout.class, new CcgmtLayoutPK(companyID, layoutID));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#add(nts.uk.ctx.sys.portal.dom.layout.Layout)
	 */
	@Override
	public void add(Layout layout) {
		this.commandProxy().insert(layout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.layout.LayoutRepository#update(nts.uk.ctx.sys.portal.dom.layout.Layout)
	 */
	@Override
	public void update(Layout layout) {
		this.commandProxy().update(layout);
	}

	/**
	 * Convert entity to domain
	 * 
	 * @param CcgmtLayout entity
	 * @return layout
	 */
	private Layout toDomain(CcgmtLayout entity) {
		return new Layout(entity.ccgmtLayoutPK.companyID, entity.ccgmtLayoutPK.layoutID, EnumAdaptor.valueOf(entity.pgType, PGType.class));
	}

}