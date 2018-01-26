package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtLastExecDateTime;

@Stateless
public class JpaLastExecDateTimeRepository extends JpaRepository
		implements LastExecDateTimeRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT ledt FROM KfnmtLastExecDateTime ledt ";
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE ledt.kfnmtLastDateTimePK.companyId = :companyId AND ledt.kfnmtLastDateTimePK.execItemCd = :execItemCd ";
	
	@Override
	public Optional<LastExecDateTime> get(String companyId, String execItemCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_EXEC_CD, KfnmtLastExecDateTime.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void insert(LastExecDateTime domain) {
		this.commandProxy().insert(KfnmtLastExecDateTime.toEntity(domain));
	}
}
