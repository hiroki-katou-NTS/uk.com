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
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuTopPagePart;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenu;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenuPK;


@Stateless
public class JpaFlowMenuRepository extends JpaRepository implements FlowMenuRepository{
	
	private final String SELECT= "SELECT c FROM CcgmtFlowMenu c";
	private final String SELECT_SINGLE = "SELECT c FROM CcgmtFlowMenu c WHERE c.ccgmtFlowMenuPK.companyID = :companyID AND c.ccgmtFlowMenuPK.toppagePartID = :toppagePartID";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.ccgmtFlowMenuPK.companyID = :companyID";
	
	private final String SELECT_FLOWMENU = "SELECT m.ccgmtFlowMenuPK.toppagePartID,  m.fileName, m.defClassAtr, t.code, t.name, t.topPagePartType, t.width, t.height "
			+ "FROM CcgmtFlowMenu m "
			+ "INNER JOIN CcgmtTopPagePart t "
			+ "ON m.ccgmtFlowMenuPK.toppagePartID = t.ccgmtTopPagePartPK.topPagePartID "
			+ "AND m.ccgmtFlowMenuPK.companyID = :companyId";
	/**
	 * Get List FlowMenu
	 * @param companyID
	 * @return List of FlowMenu
	 */
	@Override
	public List<FlowMenuTopPagePart> findAll(String companyID) {
		return this.queryProxy()
				.query(SELECT_FLOWMENU, Object[].class)
				.setParameter("companyId", companyID)
				.getList(c -> {
					String toppagePartID = (String) c[0];
					String fileName = (String) c[1];
					int defClassAtr = Integer.valueOf(c[2].toString());
					String code = (String) c[3];
					String name = (String) c[4];
					int type = Integer.valueOf(c[5].toString());
					int widthSize = Integer.valueOf(c[6].toString());
					int heightSize = Integer.valueOf(c[7].toString());
					return FlowMenuTopPagePart.createFromJavaType(toppagePartID, fileName, defClassAtr, code, name, type, widthSize, heightSize);
				});
	}
	/**
	 * Get Optional FlowMenu
	 * @param companyID 
	 * @param toppagePartID
	 * @return Optinal FlowMenu
	 */
	@Override
	public Optional<FlowMenuTopPagePart> findByCode(String companyID, String toppagePartID) {
//		return this.queryProxy()
//				.query(SELECT_SINGLE, CcgmtFlowMenu.class)
//				.setParameter("companyID", companyID)
//				.setParameter("toppagePartID", toppagePartID)
//				.getSingle(c -> toDomain(c));
		return null;
	}
	/**
	 * Add
	 * @param flow FlowMenu
	 * @return
	 */
	@Override
	public void add(FlowMenu flow) {
		this.commandProxy().insert(toEntity(flow));
		
	}
	/**
	 * Update
	 * @param flow FlowMenu
	 * @return
	 */
	@Override
	public void update(FlowMenu flow) {
		CcgmtFlowMenu entity = toEntity(flow);
		this.commandProxy().update(entity);
	}
	/**
	 * Remove
	 * @param companyID
	 * @param toppagePartID
	 * @return
	 */
	@Override
	public void remove(String companyID, String toppagePartID) {
		this.commandProxy().remove(CcgmtFlowMenu.class, new CcgmtFlowMenuPK(companyID, toppagePartID));
		
	}
	
	private FlowMenu toDomain(CcgmtFlowMenu entity) {
		return FlowMenu.createFromJavaType(
				entity.ccgmtFlowMenuPK.companyID,
				entity.ccgmtFlowMenuPK.toppagePartID, 
				entity.fileID, 
				entity.fileName, 
				entity.defClassAtr);
	}
	
	private CcgmtFlowMenu toEntity(FlowMenu domain) {
		return new CcgmtFlowMenu(new CcgmtFlowMenuPK(domain.getCompanyID(), 
				domain.getToppagePartID()),
				domain.getFileID(), 
				domain.getFileName().v(),
				domain.getDefClassAtr().value);
	}

}
