package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLog;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogPK;

@Stateless
public class JpaProcessExecutionLogRepository extends JpaRepository
		implements ProcessExecutionLogRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT pel FROM KfnmtProcessExecutionLog pel ";
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pel.kfnmtProcExecLogPK.companyId = :companyId ";
	
	private static final String SELECT_BY_PK = SELECT_ALL
			+ "WHERE pel.kfnmtProcExecLogPK.companyId = :companyId "
			+ "AND pel.kfnmtProcExecLogPK.execItemCd = :execItemCd "
			+ "AND pel.kfnmtProcExecLogPK.execId = :execId ";
	
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE pel.kfnmtProcExecLogPK.companyId = :companyId "
			+ "AND pel.kfnmtProcExecLogPK.execItemCd = :execItemCd ";
	
	@Override
	public List<ProcessExecutionLog> getProcessExecutionLogByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_BY_CID, KfnmtProcessExecutionLog.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
	}
	
	@Override
	public Optional<ProcessExecutionLog> getLogByCIdAndExecCd(String companyId, String execItemCd, String execId) {
		return this.queryProxy().query(SELECT_BY_PK, KfnmtProcessExecutionLog.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd)
				.setParameter("execId", execId).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void insert(ProcessExecutionLog domain) {
		this.commandProxy().insert(KfnmtProcessExecutionLog.toEntity(domain));
	}
	
	@Override
	public void update(ProcessExecutionLog domain) {
		KfnmtProcessExecutionLog updateData = KfnmtProcessExecutionLog.toEntity(domain);
		KfnmtProcessExecutionLog oldData = this.queryProxy().find(updateData.kfnmtProcExecLogPK, KfnmtProcessExecutionLog.class).get();
		oldData.currentStatus = updateData.currentStatus;
		oldData.overallStatus = updateData.overallStatus;
		oldData.errorDetail = updateData.errorDetail;
		oldData.lastExecDateTime = updateData.lastExecDateTime;
		oldData.schCreateStart = updateData.schCreateStart;
		oldData.schCreateEnd = updateData.schCreateEnd;
		oldData.dailyCreateStart = updateData.dailyCreateStart;
		oldData.dailyCreateEnd = updateData.dailyCreateEnd;
		oldData.dailyCalcStart = updateData.dailyCalcStart;
		oldData.dailyCalcEnd = updateData.dailyCalcEnd;
		oldData.prevExecDateTimeEx = updateData.prevExecDateTimeEx;
		this.commandProxy().update(oldData);
	}
	
	@Override
	public void remove(String companyId, String execItemCd, String execId) {
		KfnmtProcessExecutionLogPK kfnmtProcExecPK = new KfnmtProcessExecutionLogPK(companyId, execItemCd, execId);
		this.commandProxy().remove(KfnmtProcessExecutionLog.class, kfnmtProcExecPK);
	}

	@Override
	public void removeList(String companyId, String execItemCd) {
		List<KfnmtProcessExecutionLog> logList = this.queryProxy().query(SELECT_BY_CID_AND_EXEC_CD, KfnmtProcessExecutionLog.class)
														.setParameter("companyId", companyId)
														.setParameter("execItemCd", execItemCd).getList();
		if (!CollectionUtil.isEmpty(logList)) {
			this.commandProxy().removeAll(logList);
		}
	}
}
