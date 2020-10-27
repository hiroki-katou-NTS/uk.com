package nts.uk.ctx.exio.infra.repository.exi.execlog;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.execlog.OiodtExAcExecLog;
import nts.uk.ctx.exio.infra.entity.exi.execlog.OiodtExAcExecLogPk;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaExacExeResultLogRepository extends JpaRepository implements ExacExeResultLogRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiodtExAcExecLog f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exacExeResultLogPk.cid =:cid AND  f.exacExeResultLogPk.conditionSetCd =:conditionSetCd AND  f.exacExeResultLogPk.externalProcessId =:externalProcessId ";
    private static final String SELECT_BY_PROCESS_ID = SELECT_ALL_QUERY_STRING + " WHERE f.exacExeResultLogPk.externalProcessId =:externalProcessId";

    @Override
    public List<ExacExeResultLog> getAllExacExeResultLog(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiodtExAcExecLog.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ExacExeResultLog> getExacExeResultLogById(String cid, String conditionSetCd, String externalProcessId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiodtExAcExecLog.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("externalProcessId", externalProcessId)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ExacExeResultLog domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ExacExeResultLog domain){
        OiodtExAcExecLog newExacExeResultLog = toEntity(domain);
        OiodtExAcExecLog updateExacExeResultLog = this.queryProxy().find(newExacExeResultLog.exacExeResultLogPk, OiodtExAcExecLog.class).get();
        if (null == updateExacExeResultLog) {
            return;
        }
        updateExacExeResultLog.version = newExacExeResultLog.version;
        updateExacExeResultLog.executorId = newExacExeResultLog.executorId;
        updateExacExeResultLog.userId = newExacExeResultLog.userId;
        updateExacExeResultLog.processStartDatetime = newExacExeResultLog.processStartDatetime;
        updateExacExeResultLog.standardAtr = newExacExeResultLog.standardAtr;
        updateExacExeResultLog.executeForm = newExacExeResultLog.executeForm;
        updateExacExeResultLog.targetCount = newExacExeResultLog.targetCount;
        updateExacExeResultLog.errorCount = newExacExeResultLog.errorCount;
        updateExacExeResultLog.fileName = newExacExeResultLog.fileName;
        updateExacExeResultLog.systemType = newExacExeResultLog.systemType;
        updateExacExeResultLog.resultStatus = newExacExeResultLog.resultStatus;
        updateExacExeResultLog.processEndDatetime = newExacExeResultLog.processEndDatetime;
        updateExacExeResultLog.processAtr = newExacExeResultLog.processAtr;
        this.commandProxy().update(updateExacExeResultLog);
    }

    @Override
    public void remove(String cid, String conditionSetCd, String externalProcessId){
        this.commandProxy().remove(OiodtExAcExecLog.class, new OiodtExAcExecLogPk(cid, conditionSetCd, externalProcessId)); 
    }

    private static ExacExeResultLog toDomain(OiodtExAcExecLog entity) {
        return ExacExeResultLog.createFromJavaType(entity.version, entity.exacExeResultLogPk.cid, entity.exacExeResultLogPk.conditionSetCd, entity.exacExeResultLogPk.externalProcessId, entity.executorId, entity.userId, entity.processStartDatetime, entity.standardAtr, entity.executeForm, entity.targetCount, entity.errorCount, entity.fileName, entity.systemType, entity.resultStatus, entity.processEndDatetime, entity.processAtr);
    }

    private OiodtExAcExecLog toEntity(ExacExeResultLog domain) {
        return new OiodtExAcExecLog(domain.getVersion(), new OiodtExAcExecLogPk(domain.getCid(), domain.getConditionSetCd(), domain.getExternalProcessId()), domain.getExecutorId(), domain.getUserId(), domain.getProcessStartDatetime(), domain.getStandardAtr(), domain.getExecuteForm(), domain.getTargetCount(), domain.getErrorCount(), domain.getFileName(), domain.getSystemType(), domain.getResultStatus(), domain.getProcessEndDatetime(), domain.getProcessAtr());
    }

	/* (non-Javadoc)
	 * @see nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository#getExacExeResultLogByProcessId(java.lang.String)
	 */
	@Override
	public List<ExacExeResultLog> getExacExeResultLogByProcessId(String externalProcessId) {
		 return this.queryProxy().query(SELECT_BY_PROCESS_ID, OiodtExAcExecLog.class)
				 .setParameter("externalProcessId", externalProcessId)
	                .getList(item -> toDomain(item));
	}
    

}
