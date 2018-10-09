package nts.uk.ctx.exio.infra.repository.exo.execlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.execlog.ExecutionForm;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLog;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLogPk;

@Stateless
public class JpaExterOutExecLogRepository extends JpaRepository implements ExterOutExecLogRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExterOutExecLog f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.exterOutExecLogPk.cid =:cid AND  f.exterOutExecLogPk.outProcessId =:outProcessId";
	
	private static final String SELECT_ALL_EXEC_LOG_AND_COND_SET = SELECT_ALL_QUERY_STRING
			+ " WHERE f.exterOutExecLogPk.cid =:cid"
			+ " AND  f.processStartDatetime >=:processStartDatetime"
			+ " AND  f.processEndDatetime <=:processEndDatetime";
	private static final String SEARCH_BY_EXEC_ID = " AND  f.execId =:execId";
	private static final String SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_OR_EXEC_FORM2 = " AND (f.execId =:execId"
			+ " OR f.execForm =:execForm1"
			+ " OR f.execForm =:execForm2)";
	private static final String SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_CTG_ID_OR_EXEC_FORM2 = " AND (f.execId =:execId"
			+ " OR (f.execForm =:execForm1 AND f.categoryId IN :ctgIdList)"
			+ " OR f.execForm =:execForm2)";
	private static final String SEARCH_BY_COND_CD = " AND f.codeSetCond =:codeSetCond";
	private static final String ORDER_BY_START_DATE_TIME = " ORDER BY f.processStartDatetime DESC";
	
	private static final String SEARCH_STD_GENERAL_AUTHORITY = SELECT_ALL_EXEC_LOG_AND_COND_SET 
			+ SEARCH_BY_EXEC_ID
			+ ORDER_BY_START_DATE_TIME;
	private static final String SEARCH_STD_GENERAL_AUTHORITY_HAS_COND_CD = SELECT_ALL_EXEC_LOG_AND_COND_SET
			+ SEARCH_BY_EXEC_ID 
			+ SEARCH_BY_COND_CD 
			+ ORDER_BY_START_DATE_TIME;
	private static final String SEARCH_STD_INCHARGE_AUTHORITY = SELECT_ALL_EXEC_LOG_AND_COND_SET
			+ SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_OR_EXEC_FORM2 
			+ ORDER_BY_START_DATE_TIME;
	private static final String SEARCH_STD_INCHARGE_AUTHORITY_HAS_CTG = SELECT_ALL_EXEC_LOG_AND_COND_SET
			+ SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_CTG_ID_OR_EXEC_FORM2 
			+ ORDER_BY_START_DATE_TIME;
	private static final String SEARCH_STD_INCHARGE_AUTHORITY_HAS_COND_CD  = SELECT_ALL_EXEC_LOG_AND_COND_SET
			+ SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_OR_EXEC_FORM2 
			+ SEARCH_BY_COND_CD 
			+ ORDER_BY_START_DATE_TIME;
	private static final String SEARCH_STD_INCHARGE_AUTHORITY_HAS_COND_CD_CTG = SELECT_ALL_EXEC_LOG_AND_COND_SET
			+ SEARCH_BY_EXEC_ID_OR_EXEC_FORM1_CTG_ID_OR_EXEC_FORM2 
			+ SEARCH_BY_COND_CD 
			+ ORDER_BY_START_DATE_TIME;

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
				entity.categoryId, entity.processUnit, entity.processEndDatetime, entity.processStartDatetime, entity.stdClass,
				entity.execForm, entity.execId, entity.designatedReferDate, entity.specifiedEndDate, entity.specifiedStartDate,
				entity.codeSetCond, entity.resultStatus, entity.nameSetting);
	}

	@Override
	public List<ExterOutExecLog> searchExterOutExecLogGeneral(String cid, GeneralDateTime startDate, GeneralDateTime endDate,
			String userId, Optional<String> condSetCd) {
		if(condSetCd.isPresent()){
			return this.queryProxy().query(SEARCH_STD_GENERAL_AUTHORITY_HAS_COND_CD, OiomtExterOutExecLog.class)
					.setParameter("cid", cid)
					.setParameter("processStartDatetime", startDate)
					.setParameter("processEndDatetime", endDate)
					.setParameter("execId", userId)
					.setParameter("codeSetCond", condSetCd.get())
					.getList(c -> toDomain(c));
		}
		return this.queryProxy().query(SEARCH_STD_GENERAL_AUTHORITY, OiomtExterOutExecLog.class)
				.setParameter("cid", cid)
				.setParameter("processStartDatetime", startDate)
				.setParameter("processEndDatetime", endDate)
				.setParameter("execId", userId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<ExterOutExecLog> searchExterOutExecLogInchage(String cid, GeneralDateTime startDate,
			GeneralDateTime endDate, String userId, Optional<String> condSetCd, List<Integer> exOutCtgIdList) {
		if(condSetCd.isPresent()){
			if(exOutCtgIdList == null || exOutCtgIdList.isEmpty()){
				return this.queryProxy().query(SEARCH_STD_INCHARGE_AUTHORITY_HAS_COND_CD, OiomtExterOutExecLog.class)
						.setParameter("cid", cid)
						.setParameter("processStartDatetime", startDate)
						.setParameter("processEndDatetime", endDate)
						.setParameter("execId", userId)
						.setParameter("codeSetCond", condSetCd.get())
						.setParameter("execForm1", ExecutionForm.MANUAL_EXECUTION.value)
						.setParameter("execForm2", ExecutionForm.AUTOMATIC_EXECUTION.value)
						.getList(c -> toDomain(c));
			}
			else{
				return this.queryProxy().query(SEARCH_STD_INCHARGE_AUTHORITY_HAS_COND_CD_CTG, OiomtExterOutExecLog.class)
						.setParameter("cid", cid)
						.setParameter("processStartDatetime", startDate)
						.setParameter("processEndDatetime", endDate)
						.setParameter("execId", userId)
						.setParameter("codeSetCond", condSetCd.get())
						.setParameter("execForm1", ExecutionForm.MANUAL_EXECUTION.value)
						.setParameter("ctgIdList", exOutCtgIdList)
						.setParameter("execForm2", ExecutionForm.AUTOMATIC_EXECUTION.value)
						.getList(c -> toDomain(c));
			}
		}else{
			if(exOutCtgIdList == null || exOutCtgIdList.isEmpty()){
				return this.queryProxy().query(SEARCH_STD_INCHARGE_AUTHORITY, OiomtExterOutExecLog.class)
						.setParameter("cid", cid)
						.setParameter("processStartDatetime", startDate)
						.setParameter("processEndDatetime", endDate)
						.setParameter("execId", userId)
						.setParameter("execForm1", ExecutionForm.MANUAL_EXECUTION.value)
						.setParameter("execForm2", ExecutionForm.AUTOMATIC_EXECUTION.value)
						.getList(c -> toDomain(c));
			}
			else{
				return this.queryProxy().query(SEARCH_STD_INCHARGE_AUTHORITY_HAS_CTG, OiomtExterOutExecLog.class)
						.setParameter("cid", cid)
						.setParameter("processStartDatetime", startDate)
						.setParameter("processEndDatetime", endDate)
						.setParameter("execId", userId)
						.setParameter("execForm1", ExecutionForm.MANUAL_EXECUTION.value)
						.setParameter("ctgIdList", exOutCtgIdList)
						.setParameter("execForm2", ExecutionForm.AUTOMATIC_EXECUTION.value)
						.getList(c -> toDomain(c));
			}
		}
	}
}