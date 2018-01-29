package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogHistory;

@Stateless
public class JpaProcessExecutionLogHistRepository extends JpaRepository
		implements ProcessExecutionLogHistRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT pelh FROM KfnmtProcessExecutionLogHistory pelh ";
	private static final String SELECT_All_BY_CID_EXECCD_EXECID = SELECT_ALL
			+ "WHERE pelh.kfnmtProcExecLogHstPK.companyId = :companyId "
			+ "AND pelh.kfnmtProcExecLogHstPK.execItemCd = :execItemCd "
			+ "AND pelh.kfnmtProcExecLogHstPK.execId = :execId ";
	
	@Override
	public Optional<ProcessExecutionLogHistory> getByExecId(String companyId, String execItemCd, String execId) {
		return this.queryProxy().query(SELECT_All_BY_CID_EXECCD_EXECID, KfnmtProcessExecutionLogHistory.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd)
				.setParameter("execId", execId).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void insert(ProcessExecutionLogHistory domain) {
		this.commandProxy().insert(KfnmtProcessExecutionLogHistory.toEntity(domain));
	}
	
	@Override
	public void update(ProcessExecutionLogHistory domain) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void remove(String companyId, String execItemCd) {
		// TODO Auto-generated method stub
		
	}
}
