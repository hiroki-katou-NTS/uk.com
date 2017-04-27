package nts.uk.ctx.sys.portal.infra.repository.placement;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.placement.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.placement.primitive.Column;
import nts.uk.ctx.sys.portal.dom.placement.primitive.Row;
import nts.uk.ctx.sys.portal.infra.entity.placement.CcgmtPlacement;
import nts.uk.ctx.sys.portal.infra.entity.placement.CcgmtPlacementPK;

public class JpaPlacementRepository extends JpaRepository implements PlacementRepository {
	private final String SELECT_SINGLE = "SELECT c FROM CcgmtPlacement c WHERE c.placementID = :placementID";
	private final String SELECT_BY_LAYOUT = "SELECT c FROM CcgmtPlacement c WHERE c.layoutID = :layoutID";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.placement.PlacementRepository#find(String placementID)
	 */
	@Override
	public Optional<Placement> find(String placementID) {
		return this.queryProxy().query(SELECT_SINGLE, CcgmtPlacement.class)
				.setParameter("placementID", placementID)
				.getSingle(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.placement.PlacementRepository#findByType(String companyID, int pgType)
	 */
	@Override
	public List<Placement> findByLayout(String layoutID) {
		return this.queryProxy().query(SELECT_BY_LAYOUT, CcgmtPlacement.class)
				.setParameter("layoutID", layoutID)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.placement.PlacementRepository#remove(String companyID, String placementID)
	 */
	@Override
	public void remove(String companyID, String placementID) {
		this.commandProxy().remove(CcgmtPlacement.class, new CcgmtPlacementPK(companyID, placementID));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.placement.PlacementRepository#add(nts.uk.ctx.sys.portal.dom.placement.Placement)
	 */
	@Override
	public void add(Placement placement) {
		this.commandProxy().insert(placement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.placement.PlacementRepository#update(nts.uk.ctx.sys.portal.dom.placement.Placement)
	 */
	@Override
	public void update(Placement placement) {
		this.commandProxy().update(placement);
	}

	/**
	 * Convert entity to domain
	 * 
	 * @param CcgmtPlacement entity
	 * @return placement
	 */
	private Placement toDomain(CcgmtPlacement entity) {
		return new Placement(
			entity.ccgmtPlacementPK.companyID, entity.ccgmtPlacementPK.placementID, entity.layoutID, entity.topPagePartID,
			new Column(entity.column), new Row(entity.row),
			ExternalUrl.createFromJavaType(entity.externalUrl, entity.width, entity.height));
	}
}
