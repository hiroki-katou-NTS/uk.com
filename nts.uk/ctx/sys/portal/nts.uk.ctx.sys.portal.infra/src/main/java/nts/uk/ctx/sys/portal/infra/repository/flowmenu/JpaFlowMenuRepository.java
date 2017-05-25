package nts.uk.ctx.sys.portal.infra.repository.flowmenu;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenu;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenuPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePartPK;

/**
 * author hieult
 */
@Stateless
public class JpaFlowMenuRepository extends JpaRepository implements FlowMenuRepository{
	
	private final String SELECT_BASE = "SELECT m, t"
									+ " FROM CcgmtFlowMenu m JOIN CcgmtTopPagePart t"
									+ " ON m.ccgmtFlowMenuPK.topPagePartID = t.ccgmtTopPagePartPK.topPagePartID";
	private final String SELECT_SINGLE = SELECT_BASE + " WHERE m.ccgmtFlowMenuPK.topPagePartID = :topPagePartID";
	private final String SELECT_BY_COMPANY = SELECT_BASE + " WHERE m.ccgmtFlowMenuPK.companyID = :companyID";
	private final String FLOWMENU_INFOR = "SELECT c from CcgmtFlowMenu c "
										+ " WHERE c.ccgmtFlowMenuPK.companyID = :companyID "
										+ " AND c.ccgmtFlowMenuPK.topPagePartID = :topPagePartID";

	@Override
	public List<FlowMenu> findAll(String companyID) {
		return this.queryProxy().query(SELECT_BY_COMPANY, Object[].class)
				.setParameter("companyID", companyID)
				.getList(c -> joinObjectToDomain(c));
	}

	@Override
	public Optional<FlowMenu> findByCode(String companyID, String topPagePartID) {
		return this.queryProxy().query(SELECT_SINGLE, Object[].class)
				.setParameter("topPagePartID", topPagePartID)
				.getSingle(c -> joinObjectToDomain(c));
	}

	@Override
	public void add(FlowMenu flow) {
		this.commandProxy().insert(toEntity(flow));
	}

	@Override
	public void update(FlowMenu flow) {
		CcgmtFlowMenu entity = toEntity(flow);
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyID, String topPagePartID) {
		this.commandProxy().remove(CcgmtFlowMenu.class, new CcgmtFlowMenuPK(companyID, topPagePartID));
		this.getEntityManager().flush();
	}
	
	@Override
	public Optional<FlowMenu> getFlowMenu(String companyID, String topPagePartID) {
		return this.queryProxy()
				.query(FLOWMENU_INFOR, CcgmtFlowMenu.class)
				.setParameter("companyID", companyID)
				.setParameter("topPagePartID", topPagePartID)
				.getSingle(c ->  toDomain(c));
	}
	
	/**
	 * Convert domain to Infra entity
	 * @param domain
	 * @return CcgmtFlowMenu
	 */
	private CcgmtFlowMenu toEntity(FlowMenu domain) {
		CcgmtTopPagePart topPagePart = new CcgmtTopPagePart(
			new CcgmtTopPagePartPK(domain.getCompanyID(), domain.getToppagePartID()),
			domain.getCode().v(), domain.getName().v(), domain.getType().value,
			domain.getWidth().v(), domain.getHeight().v(), null
		);
		return new CcgmtFlowMenu(
				new CcgmtFlowMenuPK(domain.getCompanyID(), domain.getToppagePartID()),
				domain.getFileID(), 
				domain.getFileName().v(),
				domain.getDefClassAtr().value,
				topPagePart);
	}
	
	/**
	 * Convert Infra entity to domain
	 * @param entity
	 * @return FlowMenu
	 */
	private FlowMenu toDomain(CcgmtFlowMenu entity){
		return FlowMenu.createFromJavaType(
				entity.ccgmtFlowMenuPK.companyID, entity.ccgmtFlowMenuPK.topPagePartID,
				entity.topPagePart.code, entity.topPagePart.name,
				entity.topPagePart.topPagePartType, entity.topPagePart.width, entity.topPagePart.height,
				entity.fileID, 
				entity.fileName, 
				entity.defClassAtr);
	}
	
	/**
	 * Convert Join FlowMenu & TopPagePart entity to domain
	 * @param entity
	 * @return FlowMenu
	 */
	private FlowMenu joinObjectToDomain(Object[] entity) {
		CcgmtFlowMenu flowMenu = (CcgmtFlowMenu) entity[0];
		CcgmtTopPagePart topPagePart = (CcgmtTopPagePart) entity[1];
		return FlowMenu.createFromJavaType(
				flowMenu.ccgmtFlowMenuPK.companyID, flowMenu.ccgmtFlowMenuPK.topPagePartID,
				topPagePart.code, topPagePart.name,
				topPagePart.topPagePartType, topPagePart.width, topPagePart.height,
				flowMenu.fileID, 
				flowMenu.fileName, 
				flowMenu.defClassAtr);
	}
}
