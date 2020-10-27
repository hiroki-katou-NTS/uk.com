package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionScopeItemRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtAutoexecScopeItem;

@Stateless
public class JpaExecutionScopeItemRepository extends JpaRepository
		implements ExecutionScopeItemRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT esi FROM KfnmtAutoexecScopeItem esi ";
	private static final String SELECT_All_BY_CID_AND_EXECCD = SELECT_ALL
			+ "WHERE esi.kfnmtAutoexecScopeItemPK.companyId = :companyId AND esi.kfnmtAutoexecScopeItemPK.execItemCd = :execItemCd ";
	private static final String SELECT_BY_KEY = SELECT_ALL
			+ "WHERE esi.kfnmtAutoexecScopeItemPK.companyId = :companyId "
			+ "AND esi.kfnmtAutoexecScopeItemPK.execItemCd = :execItemCd "
			+ "AND esi.kfnmtAutoexecScopeItemPK.wkpId = :wkpId ";
	
	/**
	 * insert
	 */
	@Override
	public void insert(String companyId, String execItemCd, List<ProcessExecutionScopeItem> wkpList) {
		List<KfnmtAutoexecScopeItem> list =
				wkpList.stream()
						.map(c -> {
							Optional<KfnmtAutoexecScopeItem> opt = 
							this.queryProxy().query(SELECT_BY_KEY, KfnmtAutoexecScopeItem.class)
								.setParameter("companyId", companyId)
								.setParameter("execItemCd", execItemCd)
								.setParameter("wkpId", c.wkpId).getSingle();
							if (opt.isPresent()) {
								return null;
							}
							KfnmtAutoexecScopeItem entity = KfnmtAutoexecScopeItem.toEntity(companyId, execItemCd, c.wkpId);
							return entity;
						}).collect(Collectors.toList());
		this.commandProxy().insertAll(list);
	}
	
	/**
	 * remove all by key
	 */
	@Override
	public void removeAllByCidAndExecCd(String companyId, String execItemCd) {
		List<KfnmtAutoexecScopeItem> entityList = this.queryProxy().query(SELECT_All_BY_CID_AND_EXECCD, KfnmtAutoexecScopeItem.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd).getList();
		this.commandProxy().removeAll(entityList);
	}
}
