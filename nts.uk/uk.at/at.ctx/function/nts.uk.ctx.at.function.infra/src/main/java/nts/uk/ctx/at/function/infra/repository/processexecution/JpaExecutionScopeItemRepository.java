package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionScopeItemRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionScopeItem;

@Stateless
public class JpaExecutionScopeItemRepository extends JpaRepository
		implements ExecutionScopeItemRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT esi FROM KfnmtExecutionScopeItem esi ";
	private static final String SELECT_All_BY_CID_AND_EXECCD = SELECT_ALL
			+ "WHERE esi.kfnmtExecScopeItemPK.companyId = :companyId AND esi.kfnmtExecScopeItemPK.execItemCd = :execItemCd ";
	private static final String SELECT_BY_KEY = SELECT_ALL
			+ "WHERE esi.kfnmtExecScopeItemPK.companyId = :companyId "
			+ "AND esi.kfnmtExecScopeItemPK.execItemCd = :execItemCd "
			+ "AND esi.kfnmtExecScopeItemPK.wkpId IN :wkpIds ";

	/**
	 * insert
	 */
	@Override
	public void insert(String companyId, String execItemCd, List<String> wkpIds) {
		// Gets current data have workplace id in wkpIds
		List<KfnmtExecutionScopeItem> currentEntityList = this.queryProxy().query(SELECT_BY_KEY, KfnmtExecutionScopeItem.class)
																		   .setParameter("companyId", companyId)
																		   .setParameter("execItemCd", execItemCd)
																		   .setParameter("wkpIds", wkpIds)
																		   .getList();
		// Converts to current workplace id list
		List<String> currentWkpIdList = currentEntityList.stream()
														 .map(KfnmtExecutionScopeItem::getWorkplaceId)
														 .collect(Collectors.toList());
		// Filters new data from all input data (wkpList)
		List<KfnmtExecutionScopeItem> newEntityList = wkpIds.stream()
															.filter(workplaceId -> !currentWkpIdList.contains(workplaceId))
															.map(workplaceId -> new KfnmtExecutionScopeItem(companyId,
																											execItemCd,
																											workplaceId))
															.collect(Collectors.toList());
		// Inserts all new data
		this.commandProxy().insertAll(newEntityList);
	}

	/**
	 * remove all by key
	 */
	@Override
	public void removeAllByCidAndExecCd(String companyId, String execItemCd) {
		List<KfnmtExecutionScopeItem> entityList = this.queryProxy().query(SELECT_All_BY_CID_AND_EXECCD, KfnmtExecutionScopeItem.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd).getList();
		this.commandProxy().removeAll(entityList);
	}
}
