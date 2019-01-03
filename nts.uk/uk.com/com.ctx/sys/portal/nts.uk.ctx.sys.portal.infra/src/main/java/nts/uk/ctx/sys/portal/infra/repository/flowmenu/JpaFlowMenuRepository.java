package nts.uk.ctx.sys.portal.infra.repository.flowmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenu;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenuPK;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;

/**
 * author hieult
 */
@Stateless
public class JpaFlowMenuRepository extends JpaRepository implements FlowMenuRepository{
	
	private static final String SELECT_BASE = "SELECT m, t "
									+ "FROM CcgmtFlowMenu m JOIN CcgmtTopPagePart t "
									+ "ON m.ccgmtFlowMenuPK.topPagePartID = t.ccgmtTopPagePartPK.topPagePartID "
									+ "AND m.ccgmtFlowMenuPK.companyID = t.ccgmtTopPagePartPK.companyID ";
	private static final String SELECT_SINGLE = SELECT_BASE + " WHERE m.ccgmtFlowMenuPK.topPagePartID = :topPagePartID";
	private static final String SELECT_BY_COMPANY = SELECT_BASE + " WHERE m.ccgmtFlowMenuPK.companyID = :companyID";
	private static final String SELECT_IN = SELECT_BASE + " WHERE m.ccgmtFlowMenuPK.topPagePartID IN :topPagePartID";

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
		CcgmtFlowMenu newEntity = toEntity(flow);
		CcgmtFlowMenu entity = this.queryProxy().find(newEntity.ccgmtFlowMenuPK, CcgmtFlowMenu.class).get();
		entity.fileID = newEntity.fileID;
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyID, String topPagePartID) {
		this.commandProxy().remove(CcgmtFlowMenu.class, new CcgmtFlowMenuPK(companyID, topPagePartID));
		this.getEntityManager().flush();
	}
	
	/**
	 * Convert domain to Infra entity
	 * @param domain
	 * @return CcgmtFlowMenu
	 */
	private CcgmtFlowMenu toEntity(FlowMenu domain) {
		return new CcgmtFlowMenu(
				new CcgmtFlowMenuPK(domain.getCompanyID(), domain.getToppagePartID()),
				domain.getFileID(), 
				domain.getDefClassAtr().value
				);
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
				flowMenu.defClassAtr);
	}

	@Override
	public List<FlowMenu> findByCodes(String companyID, List<String> toppagePartID) {
		List<FlowMenu> resultList = new ArrayList<>();
		CollectionUtil.split(toppagePartID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_IN, Object[].class)
				.setParameter("topPagePartID", subList)
				.getList(c -> joinObjectToDomain(c)));
		});
		return resultList;
	}
}
