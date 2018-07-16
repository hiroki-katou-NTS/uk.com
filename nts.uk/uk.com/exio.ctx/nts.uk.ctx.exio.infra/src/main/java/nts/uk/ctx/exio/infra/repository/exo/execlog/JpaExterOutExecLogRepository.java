package nts.uk.ctx.exio.infra.repository.exo.execlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLog;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLogPk;

@Stateless
public class JpaExterOutExecLogRepository extends JpaRepository implements ExterOutExecLogRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExterOutExecLog f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.exterOutExecLogPk.cid =:cid AND  f.exterOutExecLogPk.outProcessId =:outProcessId ";
	private static final String SEARCH_BY_CID_PROCESS_PERIOD_EXEC_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.exterOutExecLogPk.cid =:cid "
			+ " AND  f.processStartDatetime >=:processStartDatetime "
			+ " AND  f.processEndDatetime <=:processEndDatetime "
			+ " AND  f.execId =:execId "
			+ " ORDER BY f.processStartDatetime DESC ";
	private static final String SEARCH_BY_CID_PROCESS_PERIOD_EXEC_ID_CODE_SET_COND = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.exterOutExecLogPk.cid =:cid "
			+ " AND  f.processStartDatetime >=:processStartDatetime "
			+ " AND  f.processEndDatetime <=:processEndDatetime "
			+ " AND  f.execId =:execId "
			+ " AND  f.codeSetCond =:codeSetCond "
			+ " ORDER BY f.processStartDatetime DESC ";

	@Override
	public List<ExterOutExecLog> getAllExterOutExecLog() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExterOutExecLog.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ExterOutExecLog> getExterOutExecLogById(String cid, String outProcessId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExterOutExecLog.class).setParameter("cid", cid)
				.setParameter("outProcessId", outProcessId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ExterOutExecLog domain) {
		this.commandProxy().insert(OiomtExterOutExecLog.toEntity(domain));
	}

	@Override
	public void update(ExterOutExecLog domain) {
		this.commandProxy().update(OiomtExterOutExecLog.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outProcessId) {
		this.commandProxy().remove(OiomtExterOutExecLog.class, new OiomtExterOutExecLogPk(cid, outProcessId));
	}

	@Override
	public void update(String cid, String outProcessId,long fileZise) {
		Optional<OiomtExterOutExecLog> entityOpt = this.queryProxy()
				.query(SELECT_BY_KEY_STRING, OiomtExterOutExecLog.class).setParameter("cid", cid)
				.setParameter("outProcessId", outProcessId).getSingle();
		entityOpt.ifPresent(entity -> {
			entity.fileSize = fileZise;
			this.commandProxy().update(entity);
		});
	}
	
	private static ExterOutExecLog toDomain(OiomtExterOutExecLog entity){
		return new ExterOutExecLog(entity.exterOutExecLogPk.cid, entity.exterOutExecLogPk.outProcessId, entity.uid,
				entity.totalErrCount, entity.totalCount, entity.fileId, entity.fileSize, entity.delFile, entity.fileName,
				entity.roleType, entity.processUnit, entity.processEndDatetime, entity.processStartDatetime, entity.stdClass,
				entity.execForm, entity.execId, entity.designatedReferDate, entity.specifiedEndDate, entity.specifiedStartDate,
				entity.codeSetCond, entity.resultStatus, entity.nameSetting);
	}

	@Override
	public List<ExterOutExecLog> searchExterOutExecLog(String cid, GeneralDate startDate, GeneralDate endDate,
			String userId, Optional<String> condSetCd) {
		if(condSetCd.isPresent()){
			return this.queryProxy().query(SEARCH_BY_CID_PROCESS_PERIOD_EXEC_ID_CODE_SET_COND, OiomtExterOutExecLog.class)
					.setParameter("cid", cid)
					.setParameter("processStartDatetime", startDate)
					.setParameter("processEndDatetime", endDate)
					.setParameter("execId", userId)
					.setParameter("codeSetCond", condSetCd.get())
					.getList(c -> toDomain(c));
		}
		return this.queryProxy().query(SEARCH_BY_CID_PROCESS_PERIOD_EXEC_ID, OiomtExterOutExecLog.class)
				.setParameter("cid", cid)
				.setParameter("processStartDatetime", startDate)
				.setParameter("processEndDatetime", endDate)
				.setParameter("execId", userId)
				.getList(c -> toDomain(c));
	}
}