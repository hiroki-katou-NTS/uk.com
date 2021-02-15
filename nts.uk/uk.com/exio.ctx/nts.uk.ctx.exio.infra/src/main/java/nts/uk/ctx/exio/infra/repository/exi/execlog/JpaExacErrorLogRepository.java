package nts.uk.ctx.exio.infra.repository.exi.execlog;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.execlog.OiodtExAcErrLog;
import nts.uk.ctx.exio.infra.entity.exi.execlog.OiomtExacErrorLogPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.execlog.ErrorOccurrenceIndicator;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaExacErrorLogRepository extends JpaRepository implements ExacErrorLogRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiodtExAcErrLog f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exacErrorLogPk.logSeqNumber =:logSeqNumber AND  f.exacErrorLogPk.cid =:cid AND  f.exacErrorLogPk.externalProcessId =:externalProcessId ";
    private static final String SELECT_BY_PROCESS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.exacErrorLogPk.externalProcessId =:externalProcessId ORDER BY f.exacErrorLogPk.logSeqNumber";
    
    @Override
    public List<ExacErrorLog> getAllExacErrorLog(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiodtExAcErrLog.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ExacErrorLog> getExacErrorLogById(int logSeqNumber, String cid, String externalProcessId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiodtExAcErrLog.class)
        .setParameter("logSeqNumber", logSeqNumber)
        .setParameter("cid", cid)
        .setParameter("externalProcessId", externalProcessId)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ExacErrorLog domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ExacErrorLog domain){
        OiodtExAcErrLog newExacErrorLog = toEntity(domain);
        OiodtExAcErrLog updateExacErrorLog = this.queryProxy().find(newExacErrorLog.exacErrorLogPk, OiodtExAcErrLog.class).get();
        if (null == updateExacErrorLog) {
            return;
        }
        updateExacErrorLog.version = newExacErrorLog.version;
        updateExacErrorLog.csvErrorItemName = newExacErrorLog.csvErrorItemName;
        updateExacErrorLog.csvAcceptedValue = newExacErrorLog.csvAcceptedValue;
        updateExacErrorLog.errorContents = newExacErrorLog.errorContents;
        updateExacErrorLog.recordNumber = newExacErrorLog.recordNumber;
        updateExacErrorLog.logRegDateTime = newExacErrorLog.logRegDateTime;
        updateExacErrorLog.itemName = newExacErrorLog.itemName;
        updateExacErrorLog.errorAtr = newExacErrorLog.errorAtr;
        this.commandProxy().update(updateExacErrorLog);
    }

    @Override
    public void remove(int logSeqNumber, String cid, String externalProcessId){
        this.commandProxy().remove(OiodtExAcErrLog.class, new OiomtExacErrorLogPk(logSeqNumber, cid, externalProcessId)); 
    }

	private static ExacErrorLog toDomain(OiodtExAcErrLog entity) {
		ExacErrorLog domain = new ExacErrorLog(entity.exacErrorLogPk.logSeqNumber,
				entity.exacErrorLogPk.cid,
				entity.exacErrorLogPk.externalProcessId,
				Optional.ofNullable(entity.csvErrorItemName),
				Optional.ofNullable(entity.csvAcceptedValue),
				Optional.ofNullable(entity.errorContents), 
				new AcceptanceLineNumber(entity.recordNumber),
				entity.logRegDateTime,
				Optional.ofNullable(entity.itemName),
				EnumAdaptor.valueOf(entity.errorAtr, ErrorOccurrenceIndicator.class));
		return domain;
	}

	private OiodtExAcErrLog toEntity(ExacErrorLog domain) {
		return new OiodtExAcErrLog(domain.getVersion(),
				new OiomtExacErrorLogPk(domain.getLogSeqNumber(), domain.getCid(), domain.getExternalProcessId()),
				AppContexts.user().contractCode(),
				domain.getCsvErrorItemName().get(), domain.getCsvAcceptedValue().get(), domain.getErrorContents().get(),
				domain.getRecordNumber().v(), domain.getLogRegDateTime(), domain.getItemName().get(), domain.getErrorAtr().value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository#getExacErrorLogByProcessId(java.lang.String)
	 */
	@Override
	public List<ExacErrorLog> getExacErrorLogByProcessId(String externalProcessId) {
		return  this.queryProxy().query(SELECT_BY_PROCESS_ID, OiodtExAcErrLog.class)
		.setParameter("externalProcessId", externalProcessId)
        .getList(item -> toDomain(item));
	}

	@Override
	public void addList(List<ExacErrorLog> domain) {
		if(domain.isEmpty()) return;
		this.commandProxy().insertAll(domain.stream().map(x -> toEntity(x)).collect(Collectors.toList())); 
		this.getEntityManager().flush();
	}

}
