/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.infra.repository.flowmenu;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.Flowmenu.CcgmtFlowMenu;


@Stateless
public class JpaFlowMenuRepository extends JpaRepository implements FlowMenuRepository{
	
	private final String SELECT= "SELECT c FROM CcgmtFlowMenu c";
	private final String SELECT_SINGLE = "SELECT c FROM CcgmtFlowMenu c WHERE c.ccgmtFlowMenuPK.companyID = :companyID AND c.ccgmtFlowMenuPK.toppagePartID = :toppagePartID";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.ccgmtFlowMenuPK.companyID = :companyID";
	/**
	 * Get List FlowMenu
	 * @param companyID
	 * @return List of FlowMenu
	 */
	@Override
	public List<FlowMenu> findAll(String companyID) {
		return this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, CcgmtFlowMenu.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}
	/**
	 * Get Optional FlowMenu
	 * @param companyID 
	 * @param toppagePartID
	 * @return Optinal FlowMenu
	 */
	@Override
	public Optional<FlowMenu> findByCode(String companyID, String toppagePartID) {
		return this.queryProxy()
				.query(SELECT_SINGLE, CcgmtFlowMenu.class)
				.setParameter("companyID", companyID)
				.setParameter("toppagePartID", toppagePartID)
				.getSingle(c -> toDomain(c));
	}
	/**
	 * Add
	 * @param companyID
	 * @param toppagePartID
	 * @return
	 */
	@Override
	public void add(FlowMenu flow) {
		this.commandProxy().insert(CcgmtFlowMenu.class);
		
	}
	/**
	 * Update
	 * @param companyID
	 * @param toppagePartID
	 * @return
	 */
	@Override
	public void update(String companyID, String toppagePartID) {
		this.commandProxy().update(CcgmtFlowMenu.class);
		
	}
	/**
	 * Remove
	 * @param companyID
	 * @param toppagePartID
	 * @return
	 */
	@Override
	public void remove(String companyID, String toppagePartID) {
		this.commandProxy().remove(CcgmtFlowMenu.class);
		
	}
	private FlowMenu toDomain(CcgmtFlowMenu entity) {
		return FlowMenu.createFromJavaType(
				entity.ccgmtFlowMenuPK.companyID,
				entity.ccgmtFlowMenuPK.toppagePartID, 
				entity.fileID, 
				entity.fileName, 
				entity.defClassAtr);
	}

}
