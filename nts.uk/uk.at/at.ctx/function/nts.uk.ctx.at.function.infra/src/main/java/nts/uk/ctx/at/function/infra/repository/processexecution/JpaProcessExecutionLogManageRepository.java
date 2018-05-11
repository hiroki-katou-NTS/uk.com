package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogManage;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogManagePK;
@Stateless
public class JpaProcessExecutionLogManageRepository extends JpaRepository
implements ProcessExecutionLogManageRepository{
	
	private static final String SELECT_ALL = "SELECT pel FROM KfnmtProcessExecutionLogManage pel ";
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pel.kfnmtProcExecLogPK.companyId = :companyId ORDER BY pel.kfnmtProcExecLogPK.execItemCd ASC ";
	
	private static final String SELECT_BY_PK = SELECT_ALL
			+ "WHERE pel.kfnmtProcExecLogPK.companyId = :companyId "
			+ "AND pel.kfnmtProcExecLogPK.execItemCd = :execItemCd ";
	
	private static final String SELECT_All_BY_CID_NATIVE = " SELECT * FROM KFNMT_PRO_EXE_LOG_MANAGE as pel WITH (READUNCOMMITTED)"
			+ "WHERE pel.CID = ? ORDER BY pel.EXEC_ITEM_CD ASC ";
	
	/*
	private static final String SELECT_All_BY_CID1 = SELECT_ALL
			+ " WITH (READUNCOMMITTED) WHERE pel.kfnmtProcExecLogPK.companyId = :companyId ORDER BY pel.kfnmtProcExecLogPK.execItemCd ASC ";
		*/
	

	@Override
	public List<ProcessExecutionLogManage> getProcessExecutionLogByCompanyId(String companyId) {
		 List<ProcessExecutionLogManage> lstProcessExecutionLogManage = this.queryProxy().query(SELECT_All_BY_CID, KfnmtProcessExecutionLogManage.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
		return lstProcessExecutionLogManage;
	}
	@Override
	public List<ProcessExecutionLogManage> getProcessExecutionReadUncommit(String companyId) {
		EntityManager entityManager = this.getEntityManager();
		//this.getEntityManager().clear();
		//TypedQuery<KfnmtProcessExecutionLogManage> setParameter = entityManager.createQuery(SELECT_All_BY_CID1, KfnmtProcessExecutionLogManage.class)
		//.setParameter("companyId", companyId);
		//setParameter.setLockMode(LockModeType.PESSIMISTIC_READ);
			/*
		java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
		try {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
		Query setParameter2 = entityManager.createNativeQuery(SELECT_All_BY_CID_NATIVE, KfnmtProcessExecutionLogManage.class)
				.setParameter(1, companyId);
				//setParameter.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		//EntityManager merge = entityManager.merge(entityManager);
		//entityManager.lock(merge, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		List<KfnmtProcessExecutionLogManage> resultList =  setParameter2.getResultList();
		List<ProcessExecutionLogManage> lstProcessExecutionLogManage = new ArrayList<ProcessExecutionLogManage>();
		resultList.forEach(x->{
			lstProcessExecutionLogManage.add(x.toDomain());
		});
		
		return lstProcessExecutionLogManage;
	}

	@Override
	public Optional<ProcessExecutionLogManage> getLogByCIdAndExecCd(String companyId, String execItemCd) {
		 Optional<ProcessExecutionLogManage> processExecutionLogManageOpt = this.queryProxy().query(SELECT_BY_PK, KfnmtProcessExecutionLogManage.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd)
				.getSingle(c -> c.toDomain());
		return processExecutionLogManageOpt;
	}

	@Override
	public void insert(ProcessExecutionLogManage domain) {
		//this.commandProxy().insert(KfnmtProcessExecutionLogManage.toEntity(domain));
			this.getEntityManager().persist(KfnmtProcessExecutionLogManage.toEntity(domain));
		//this.getEntityManager().lock(this.getEntityManager(), LockModeType.NONE);
			KfnmtProcessExecutionLogManagePK kfnmtProcExecPK = new KfnmtProcessExecutionLogManagePK(domain.getCompanyId(), domain.getExecItemCd().v());
			KfnmtProcessExecutionLogManage find = this.getEntityManager().find(KfnmtProcessExecutionLogManage.class, kfnmtProcExecPK);
		//	LockModeType lockMode = this.getEntityManager().getLockMode(find);
	//	this.getEntityManager().lock(find, LockModeType.PESSIMISTIC_WRITE);
			this.getEntityManager().flush();
	}

	@Override
	public void update(ProcessExecutionLogManage domain) {
			KfnmtProcessExecutionLogManage updateData = KfnmtProcessExecutionLogManage.toEntity(domain);
			//KfnmtProcessExecutionLogManage oldData = this.queryProxy().find(updateData.kfnmtProcExecLogPK, KfnmtProcessExecutionLogManage.class).get();
			KfnmtProcessExecutionLogManage oldData = this.getEntityManager().find(KfnmtProcessExecutionLogManage.class,updateData.kfnmtProcExecLogPK,LockModeType.NONE );
			oldData.currentStatus = updateData.currentStatus;
			oldData.overallStatus = updateData.overallStatus;
			oldData.errorDetail = updateData.errorDetail;
			oldData.lastExecDateTime = updateData.lastExecDateTime;
			oldData.prevExecDateTimeEx = updateData.prevExecDateTimeEx;
			//this.commandProxy().update(oldData);
			this.getEntityManager().merge(oldData);
			KfnmtProcessExecutionLogManagePK kfnmtProcExecPK = new KfnmtProcessExecutionLogManagePK(domain.getCompanyId(), domain.getExecItemCd().v());
			//KfnmtProcessExecutionLogManage find = this.getEntityManager().find(KfnmtProcessExecutionLogManage.class, kfnmtProcExecPK);
			//LockModeType lockMode = this.getEntityManager().getLockMode(find);
		//	this.getEntityManager().lock(find, LockModeType.PESSIMISTIC_WRITE);
			this.getEntityManager().flush();
	}

	@Override
	public void remove(String companyId, String execItemCd) {
		KfnmtProcessExecutionLogManagePK kfnmtProcExecPK = new KfnmtProcessExecutionLogManagePK(companyId, execItemCd);
		//this.commandProxy().remove(KfnmtProcessExecutionLogManage.class, kfnmtProcExecPK);
		//KfnmtProcessExecutionLogManage find = this.getEntityManager().find(KfnmtProcessExecutionLogManage.class, kfnmtProcExecPK);
		KfnmtProcessExecutionLogManage find = this.getEntityManager().find(KfnmtProcessExecutionLogManage.class,kfnmtProcExecPK,LockModeType.NONE );
		this.getEntityManager().remove(find);
		//LockModeType lockMode = this.getEntityManager().getLockMode(find);
		//this.getEntityManager().lock(find, LockModeType.PESSIMISTIC_WRITE);
		this.getEntityManager().flush();
	}

}
