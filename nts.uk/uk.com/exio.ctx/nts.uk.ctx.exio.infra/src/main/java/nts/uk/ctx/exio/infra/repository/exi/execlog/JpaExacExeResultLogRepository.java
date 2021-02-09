package nts.uk.ctx.exio.infra.repository.exi.execlog;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.execlog.OiomtExacExeResultLog;
import nts.uk.ctx.exio.infra.entity.exi.execlog.OiomtExacExeResultLogPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExtExecutionMode;
import nts.uk.ctx.exio.dom.exi.execlog.ExtResultStatus;
import nts.uk.ctx.exio.dom.exi.execlog.ProcessingFlg;
import nts.uk.ctx.exio.dom.exi.execlog.StandardFlg;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaExacExeResultLogRepository extends JpaRepository implements ExacExeResultLogRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExacExeResultLog f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.pk.cid =:cid"
    		+ " AND  f.pk.conditionSetCd =:conditionSetCd"
    		+ " AND  f.pk.externalProcessId =:externalProcessId ";
    private static final String SELECT_BY_PROCESS_ID = SELECT_ALL_QUERY_STRING + " WHERE f.pk.externalProcessId =:externalProcessId";

    @Override
    public List<ExacExeResultLog> getAllExacExeResultLog(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExacExeResultLog.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ExacExeResultLog> getExacExeResultLogById(String cid, String conditionSetCd, String externalProcessId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExacExeResultLog.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("externalProcessId", externalProcessId)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ExacExeResultLog domain){
        this.commandProxy().insert(toEntity(domain));
        this.getEntityManager().flush();
    }

    @Override
    public void update(ExacExeResultLog domain){
        OiomtExacExeResultLog newExacExeResultLog = toEntity(domain);
        OiomtExacExeResultLog updateExacExeResultLog = this.queryProxy().find(newExacExeResultLog.pk,
        		OiomtExacExeResultLog.class).get();
        if (null == updateExacExeResultLog) {
            return;
        }
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
        this.commandProxy().remove(OiomtExacExeResultLog.class, new OiomtExacExeResultLogPk(cid, conditionSetCd, externalProcessId)); 
    }

    private static ExacExeResultLog toDomain(OiomtExacExeResultLog entity) {
    	ExacExeResultLog domain = new ExacExeResultLog(entity.pk.cid,
    			entity.pk.conditionSetCd,
    			entity.pk.externalProcessId,
    			entity.executorId,
    			entity.userId,
    			entity.processStartDatetime,
    			EnumAdaptor.valueOf(entity.standardAtr, StandardFlg.class),
    			EnumAdaptor.valueOf(entity.executeForm, ExtExecutionMode.class),
    			entity.targetCount,
    			entity.errorCount,
    			entity.fileName,
    			EnumAdaptor.valueOf(entity.systemType, SystemType.class),
    			Optional.ofNullable(entity.resultStatus == null ? null : EnumAdaptor.valueOf(entity.resultStatus, ExtResultStatus.class)),
    			Optional.ofNullable(entity.processEndDatetime),
    			EnumAdaptor.valueOf(entity.processAtr, ProcessingFlg.class));
    	return domain;
    }

    private OiomtExacExeResultLog toEntity(ExacExeResultLog domain) {
    	OiomtExacExeResultLogPk pk = new OiomtExacExeResultLogPk(domain.getCid(),
    			domain.getConditionSetCd(),
    			domain.getExternalProcessId());
    	OiomtExacExeResultLog entity = new OiomtExacExeResultLog(pk,
    			AppContexts.user().contractCode(),
    			domain.getExecutorId(),
    			domain.getUserId(),
    			domain.getProcessStartDatetime(),
    			domain.getStandardAtr().value,
    			domain.getExecuteForm().value,
    			domain.getTargetCount(),
    			domain.getErrorCount(),
    			domain.getFileName(),
    			domain.getSystemType().value,
    			domain.getResultStatus().isPresent() ? domain.getResultStatus().get().value : null,
    			domain.getProcessEndDatetime().isPresent() ? domain.getProcessEndDatetime().get() : null,
    			domain.getProcessAtr().value);
        return entity;
    }

	/* (non-Javadoc)
	 * @see nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository#getExacExeResultLogByProcessId(java.lang.String)
	 */
	@Override
	public List<ExacExeResultLog> getExacExeResultLogByProcessId(String externalProcessId) {
		 return this.queryProxy().query(SELECT_BY_PROCESS_ID, OiomtExacExeResultLog.class)
				 .setParameter("externalProcessId", externalProcessId)
	                .getList(item -> toDomain(item));
	}
    

}
