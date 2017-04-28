package nts.uk.ctx.sys.portal.infra.repository.toppagepart;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePartPK;

/**
 * @author LamDT
 */
@Stateless
public class JpaTopPagePartRepository extends JpaRepository implements TopPagePartRepository {

	private final String SELECT_SINGLE = "SELECT c FROM CcgmtTopPagePart c WHERE c.topPagePartID = :topPagePartID";
	private final String SELECT_BY_LAYOUT = "SELECT c FROM CcgmtTopPagePart c WHERE c.layoutID = :layoutID";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository#find(String
	 * topPagePartID)
	 */
	@Override
	public Optional<TopPagePart> find(String topPagePartID) {
		return this.queryProxy().query(SELECT_SINGLE, CcgmtTopPagePart.class)
				.setParameter("topPagePartID", topPagePartID)
				.getSingle(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository#findByLayout(
	 * String layoutID)
	 */
	@Override
	public List<TopPagePart> findByLayout(String layoutID) {
		return this.queryProxy().query(SELECT_BY_LAYOUT, CcgmtTopPagePart.class)
				.setParameter("layoutID", layoutID)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository#remove(String
	 * companyID, String topPagePartID)
	 */
	@Override
	public void remove(String companyID, String topPagePartID) {
		this.commandProxy().remove(CcgmtTopPagePart.class, new CcgmtTopPagePartPK(companyID, topPagePartID));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository#add(
	 * TopPagePart)
	 */
	@Override
	public void add(TopPagePart topPagePart) {
		this.commandProxy().insert(topPagePart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository#update(
	 * TopPagePart)
	 */
	@Override
	public void update(TopPagePart topPagePart) {
		this.commandProxy().update(topPagePart);
	}

	/**
	 * Convert entity to domain
	 * 
	 * @param CcgmtPlacement entity
	 * @return placement
	 */
	private TopPagePart toDomain(CcgmtTopPagePart entity) {
		return TopPagePart.createFromJavaType(
			entity.ccgmtTopPagePartPK.companyID, entity.ccgmtTopPagePartPK.topPagePartID,
			entity.code, entity.name,
			entity.topPagePartType,
			entity.width, entity.height);
	}

}
