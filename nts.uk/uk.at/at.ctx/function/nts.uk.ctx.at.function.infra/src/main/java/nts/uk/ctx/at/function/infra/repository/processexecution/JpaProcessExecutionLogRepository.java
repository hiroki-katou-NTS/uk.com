package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
//import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLog;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionTaskLogPK;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLog;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogPK;
//import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogManage;
//import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogPK;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaProcessExecutionLogRepository extends JpaRepository
		implements ProcessExecutionLogRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT pel FROM KfnmtProcessExecutionLog pel ";
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pel.kfndtAutoexecLogPK.companyId = :companyId ORDER BY pel.kfndtAutoexecLogPK.execItemCd ASC ";
	
	private static final String SELECT_BY_PK = SELECT_ALL
			+ "WHERE pel.kfndtAutoexecLogPK.companyId = :companyId "
			+ "AND pel.kfndtAutoexecLogPK.execItemCd = :execItemCd "
			+ "AND pel.kfndtAutoexecLogPK.execId = :execId ";
	
	private static final String SELECT_BY_KEY = SELECT_ALL 
			+ "WHERE pel.kfndtAutoexecLogPK.companyId = :companyId "
			+ "AND pel.kfndtAutoexecLogPK.execItemCd = :execItemCd ";
	
	private static final String SELECT_TASK_LOG = "SELECT k FROM KfnmtExecutionTaskLog k"+ 
	" WHERE k.kfndtAutoexecTaskLogPK.companyId = :companyId " + " AND k.kfndtAutoexecTaskLogPK.execItemCd= :execItemCd ";
	
	
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE pel.kfndtAutoexecLogPK.companyId = :companyId "
			+ "AND pel.kfndtAutoexecLogPK.execItemCd = :execItemCd ";
	
	private static final String SELECT_BY_KEY_NATIVE = "SELECT * FROM KFNDT_AUTOEXEC_LOG as pel WITH (READUNCOMMITTED)"
			+ "WHERE pel.CID = ? "
			+ "AND pel.EXEC_ITEM_CD = ? ";
	private static final String DELETE_BY_EXEC_CD = " DELETE FROM KfnmtProcessExecutionLog c "
			+ "WHERE c.kfndtAutoexecLogPK.companyId = :companyId "
			+ "AND c.kfndtAutoexecLogPK.execItemCd = :execItemCd ";
	private static final String SELECT_TASK_LOG_BY_JDBC = "SELECT * FROM KFNDT_AUTOEXEC_TASK_LOG "
			+ "WHERE CID = ? "
			+ "AND EXEC_ITEM_CD = ? ";
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void update(ProcessExecutionLog domain) {
		KfnmtProcessExecutionLog updateData = KfnmtProcessExecutionLog.toEntity(domain);
//		Optional<KfnmtProcessExecutionLog> oldDataOtp = this.queryProxy().find(updateData.kfndtAutoexecLogPK, KfnmtProcessExecutionLog.class);
//		if(!oldDataOtp.isPresent())
//			return;
//		KfnmtProcessExecutionLog oldData = oldDataOtp.get();
//		oldData.schCreateStart = updateData.schCreateStart;
//		oldData.schCreateEnd = updateData.schCreateEnd;
//		oldData.dailyCreateStart = updateData.dailyCreateStart;
//		oldData.dailyCreateEnd = updateData.dailyCreateEnd;
//		oldData.dailyCalcStart = updateData.dailyCalcStart;
//		oldData.dailyCalcEnd = updateData.dailyCalcEnd;
//		oldData.reflectApprovalResultStart = updateData.reflectApprovalResultStart;
//		oldData.reflectApprovalResultEnd = updateData.reflectApprovalResultEnd;
//		oldData.taskLogList = updateData.taskLogList;
//		this.commandProxy().update(oldData);
		try {
			String updateTableSQL = " UPDATE KFNDT_AUTOEXEC_LOG SET"
					+ " SCH_CREATE_START = ?" 
					+ " ,SCH_CREATE_END = ? "
					+ " ,DAILY_CREATE_START = ? " 
					+ " ,DAILY_CREATE_END = ?" 
					+ " ,DAILY_CALC_START = ?"
					+ " ,DAILY_CALC_END = ?"
					+ " ,RFL_APPR_START = ?"
					+ " ,RFL_APPR_END = ?"
					+ " WHERE CID = ? AND EXEC_ITEM_CD = ? AND EXEC_ID = ? ";
			try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(updateTableSQL))) {
				ps.setDate(1, updateData.schCreateStart ==null?null: Date.valueOf(updateData.schCreateStart.localDate()));
				ps.setDate(2, updateData.schCreateEnd ==null?null: Date.valueOf(updateData.schCreateEnd.localDate()));
				ps.setDate(3, updateData.dailyCreateStart ==null?null: Date.valueOf(updateData.dailyCreateStart.localDate()));
				ps.setDate(4, updateData.dailyCreateEnd ==null?null: Date.valueOf(updateData.dailyCreateEnd.localDate()));
				ps.setDate(5, updateData.dailyCalcStart ==null?null: Date.valueOf(updateData.dailyCalcStart.localDate()));
				ps.setDate(6, updateData.dailyCalcEnd ==null?null: Date.valueOf(updateData.dailyCalcEnd.localDate()));
				ps.setDate(7, updateData.reflectApprovalResultStart ==null?null: Date.valueOf(updateData.reflectApprovalResultStart.localDate()));
				ps.setDate(8, updateData.reflectApprovalResultEnd ==null?null: Date.valueOf(updateData.reflectApprovalResultEnd.localDate()));
				ps.setString(9, domain.getCompanyId());
				ps.setString(10, domain.getExecItemCd().v());
				ps.setString(11, domain.getExecId());
				ps.executeUpdate();
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			for(KfnmtExecutionTaskLog kfnmtExecutionTaskLog : updateData.taskLogList) {
				String updateTableSQL = " UPDATE KFNDT_AUTOEXEC_TASK_LOG SET"
						+ " STATUS = ?"
						+ " ,LAST_EXEC_DATETIME = ?"
						+ " ,LAST_END_EXEC_DATETIME = ?"
						+ " ,ERROR_SYSTEM = ?"
						+ " ,ERROR_BUSINESS = ?"
						+ " WHERE CID = ? AND EXEC_ITEM_CD = ? AND EXEC_ID = ? AND TASK_ID = ? ";
				try (PreparedStatement ps = this.connection().prepareStatement(JDBCUtil.toUpdateWithCommonField(updateTableSQL))) {
					ps.setString(1, kfnmtExecutionTaskLog.status ==null?null:kfnmtExecutionTaskLog.status.toString());
					ps.setString(2, kfnmtExecutionTaskLog.lastExecDateTime ==null?null:kfnmtExecutionTaskLog.lastExecDateTime.toString());
					ps.setString(3, kfnmtExecutionTaskLog.lastEndExecDateTime ==null?null:kfnmtExecutionTaskLog.lastEndExecDateTime.toString());
					ps.setString(4, kfnmtExecutionTaskLog.errorSystem == null?null:(kfnmtExecutionTaskLog.errorSystem ==1?"1":"0"));
					ps.setString(5, kfnmtExecutionTaskLog.errorBusiness == null?null:(kfnmtExecutionTaskLog.errorBusiness ==1?"1":"0"));
					ps.setString(6, kfnmtExecutionTaskLog.kfndtAutoexecTaskLogPK.companyId);
					ps.setString(7, kfnmtExecutionTaskLog.kfndtAutoexecTaskLogPK.execItemCd);
					ps.setString(8, kfnmtExecutionTaskLog.kfndtAutoexecTaskLogPK.execId);
					ps.setInt(9, kfnmtExecutionTaskLog.kfndtAutoexecTaskLogPK.taskId);
					ps.executeUpdate();
				}
			}
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void remove(String companyId, String execItemCd, String execId) {
		this.getEntityManager().createQuery(DELETE_BY_EXEC_CD, KfnmtProcessExecutionLog.class)
		.setParameter("companyId", companyId)
		.setParameter("execItemCd", execItemCd)
		.executeUpdate();
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

	@Override
	public Optional<ProcessExecutionLog> getLog(String companyId, String execItemCd) {
//		 List<KfnmtExecutionTaskLog> list = this.queryProxy().query(SELECT_TASK_LOG, KfnmtExecutionTaskLog.class)
//				.setParameter("companyId", companyId).setParameter("execItemCd", execItemCd).getList();
		List<KfnmtExecutionTaskLog> list = new ArrayList<>();
		try (PreparedStatement statement1 = this.connection().prepareStatement(SELECT_TASK_LOG_BY_JDBC)) {
			statement1.setString(1, companyId);
			statement1.setString(2, execItemCd);
			list.addAll(new NtsResultSet(statement1.executeQuery()).getList(rec -> {
				KfnmtExecutionTaskLogPK pk = new KfnmtExecutionTaskLogPK();
						pk.setCompanyId(rec.getString("CID"));
						pk.setExecItemCd(rec.getString("EXEC_ITEM_CD"));
						pk.setExecId(rec.getString("EXEC_ID"));
						pk.setTaskId(rec.getInt("TASK_ID"));
						KfnmtExecutionTaskLog entity = new KfnmtExecutionTaskLog();
						entity.setKfndtAutoexecTaskLogPK(pk);
						entity.setStatus(rec.getInt("STATUS"));
						entity.setLastExecDateTime(rec.getGeneralDateTime("LAST_EXEC_DATETIME"));
						entity.setLastEndExecDateTime(rec.getGeneralDateTime("LAST_END_EXEC_DATETIME"));
						entity.setErrorBusiness(rec.getInt("ERROR_BUSINESS"));
						entity.setErrorSystem(rec.getInt("ERROR_SYSTEM"));
						entity.setUpdDate(rec.getGeneralDateTime("UPD_DATE"));
						return entity;
					}));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(!list.isEmpty()){
			 List<KfnmtProcessExecutionLog> listKfnmtProcessExecutionLog= getProcessExecutionLog(companyId, execItemCd);
			 if(!listKfnmtProcessExecutionLog.isEmpty()){
				 KfnmtProcessExecutionLog kfnmtProcessExecutionLog = listKfnmtProcessExecutionLog.get(0);
				 return Optional.ofNullable(kfnmtProcessExecutionLog.toDomainMaxDate(list));
			 }else{
				 return Optional.empty();
			 }
		}else{
			 List<ProcessExecutionLog> lstProcessExecutionLog = getProcessExecutionLog(companyId, execItemCd).stream().map(c -> c.toDomainMaxDate()).collect(Collectors.toList());
				 if(lstProcessExecutionLog!=null && !lstProcessExecutionLog.isEmpty()){
					return Optional.ofNullable(lstProcessExecutionLog.get(0));
				 }
				 return Optional.empty();
		}
	}
	
	@Override
	public Optional<ProcessExecutionLog> getLogReadUncommit(String companyId, String execItemCd){
		Query query = this.getEntityManager().createNativeQuery(SELECT_BY_KEY_NATIVE, KfnmtProcessExecutionLog.class)
				.setParameter(1, companyId).setParameter(2, execItemCd);
		@SuppressWarnings("unchecked")
		List<KfnmtProcessExecutionLog> resultList = query.getResultList();
		List<ProcessExecutionLog> lstProcessExecutionLog = new ArrayList<ProcessExecutionLog>();
		resultList.forEach(x->{
			lstProcessExecutionLog.add(x.toDomainMaxDate());
		});
		 if(lstProcessExecutionLog!=null && !lstProcessExecutionLog.isEmpty()){
				return Optional.ofNullable(lstProcessExecutionLog.get(0));
			 }
		 return Optional.empty();
	}
	
	private List<KfnmtProcessExecutionLog> getProcessExecutionLog(String companyId,String execItemCd){
		List<KfnmtProcessExecutionLog> data = new ArrayList<>();
		String selectData = " SELECT * FROM KFNDT_AUTOEXEC_LOG "
				+ " WHERE CID = ? AND EXEC_ITEM_CD = ? ";
		try (PreparedStatement statement = this.connection().prepareStatement(selectData)) {
			statement.setString(1, companyId);
			statement.setString(2, execItemCd);
			data =  new NtsResultSet(statement.executeQuery()).getList(rec -> {
				KfnmtProcessExecutionLog entity = new KfnmtProcessExecutionLog();
				entity.kfndtAutoexecLogPK = new KfnmtProcessExecutionLogPK(rec.getString("CID"), 
						rec.getString("EXEC_ITEM_CD"), rec.getString("EXEC_ID"));
				entity.schCreateStart = rec.getGeneralDate("SCH_CREATE_START");
				entity.schCreateEnd = rec.getGeneralDate("SCH_CREATE_END");
				entity.dailyCreateStart = rec.getGeneralDate("DAILY_CREATE_START");
				entity.dailyCreateEnd = rec.getGeneralDate("DAILY_CREATE_END");
				entity.dailyCalcStart = rec.getGeneralDate("DAILY_CALC_START");
				entity.dailyCalcEnd = rec.getGeneralDate("DAILY_CALC_END");
				entity.reflectApprovalResultStart = rec.getGeneralDate("RFL_APPR_START");
				entity.reflectApprovalResultEnd = rec.getGeneralDate("RFL_APPR_END");
				return entity;
			});
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return data;
	}
	
}

