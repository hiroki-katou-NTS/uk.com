package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtAutoexecScope;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecution;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionPK;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionSetting;

@Stateless
public class JpaProcessExecutionRepository extends JpaRepository
		implements ProcessExecutionRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT pe FROM KfnmtProcessExecution pe ";
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pe.kfnmtAutoexecPK.companyId = :companyId ORDER BY pe.kfnmtAutoexecPK.execItemCd ASC";
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE pe.kfnmtAutoexecPK.companyId = :companyId AND pe.kfnmtAutoexecPK.execItemCd = :execItemCd ";
	
	@Override
	public List<ProcessExecution> getProcessExecutionByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_BY_CID, KfnmtProcessExecution.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
	}

	@Override
	public Optional<ProcessExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_EXEC_CD, KfnmtProcessExecution.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd).getSingle(c -> c.toDomain());
	}
	
	@Override
	public void insert(ProcessExecution domain) {
		this.commandProxy().insert(KfnmtProcessExecution.toEntity(domain));
	}

	@Override
	public void update(ProcessExecution domain) {
		KfnmtProcessExecution updateData = KfnmtProcessExecution.toEntity(domain);
		KfnmtProcessExecution oldData = this.queryProxy().find(updateData.kfnmtAutoexecPK, KfnmtProcessExecution.class).get();
		oldData.execItemName = updateData.execItemName;
		oldData.execScope = setScope(oldData.execScope, updateData.execScope);
		oldData.execSetting = setSetting(oldData.execSetting, updateData.execSetting);
		oldData.processExecType =updateData.processExecType;
		this.commandProxy().update(oldData);
		
	}

	@Override
	public void remove(String companyId, String execItemCd) {
		KfnmtProcessExecutionPK kfnmtAutoexecPK = new KfnmtProcessExecutionPK(companyId, execItemCd);
		this.commandProxy().remove(KfnmtProcessExecution.class,kfnmtAutoexecPK);
	}
	
	private KfnmtAutoexecScope setScope(KfnmtAutoexecScope old, KfnmtAutoexecScope update) {
		old.execScopeCls = update.execScopeCls;
		old.refDate = update.refDate;
		return old;
	}
	
	private KfnmtProcessExecutionSetting setSetting(KfnmtProcessExecutionSetting old, KfnmtProcessExecutionSetting update) {
		old.perScheduleCls = update.perScheduleCls;
		old.targetMonth = update.targetMonth;
		old.targetDate = update.targetDate;
		old.creationPeriod = update.creationPeriod;
		old.creationTarget = update.creationTarget;
		old.recreateWorkType = update.recreateWorkType;
		old.manualCorrection = update.manualCorrection;
		old.createEmployee = update.createEmployee;
		old.recreateTransfer = update.recreateTransfer;
		old.dailyPerfCls = update.dailyPerfCls;
		old.dailyPerfItem = update.dailyPerfItem;
		old.midJoinEmployee = update.midJoinEmployee;
		old.reflectResultCls = update.reflectResultCls;
		old.monthlyAggCls = update.monthlyAggCls;
		old.recreateTypeChangePerson = update.recreateTypeChangePerson; 
		old.recreateTransfers = update.recreateTransfers;
		old.appRouteUpdateAtr = update.appRouteUpdateAtr;
		old.createNewEmp = update.createNewEmp;
		old.appRouteUpdateAtrMon = update.appRouteUpdateAtrMon;
		old.alarmAtr = update.alarmAtr;
		old.alarmCode = update.alarmCode;
		old.mailPrincipal = update.mailPrincipal;
		old.mailAdministrator = update.mailAdministrator;
		old.designatedYear = update.designatedYear;
		old.startMonthDay = update.startMonthDay;
		old.endMonthDay = update.endMonthDay;
		return old;
	}
}
