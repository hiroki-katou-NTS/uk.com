package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtExecutionScope;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecution;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionPK;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaProcessExecutionRepository extends JpaRepository implements ProcessExecutionRepository {
	
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT pe FROM KfnmtProcessExecution pe ";
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pe.kfnmtProcExecPK.companyId = :companyId ORDER BY pe.kfnmtProcExecPK.execItemCd ASC";
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE pe.kfnmtProcExecPK.companyId = :companyId AND pe.kfnmtProcExecPK.execItemCd = :execItemCd ";
	
	@Override
	public List<UpdateProcessAutoExecution> getProcessExecutionByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_BY_CID, KfnmtProcessExecution.class)
				.setParameter("companyId", companyId)
				.getList(entity -> UpdateProcessAutoExecution.createFromMemento(companyId, entity));
	}

	@Override
	public Optional<UpdateProcessAutoExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_EXEC_CD, KfnmtProcessExecution.class)
				.setParameter("companyId", companyId)
				.setParameter("execItemCd", execItemCd)
				.getSingle(entity -> UpdateProcessAutoExecution.createFromMemento(companyId, entity));
	}
	
	@Override
	public void insert(UpdateProcessAutoExecution domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	@Override
	public void update(UpdateProcessAutoExecution domain) {
		KfnmtProcessExecution updateData = this.toEntity(domain);
		KfnmtProcessExecution oldData = this.queryProxy().find(updateData.kfnmtProcExecPK, KfnmtProcessExecution.class).get();
		oldData.execItemName = updateData.execItemName;
		oldData.execScope = setScope(oldData.execScope, updateData.execScope);
		oldData.execSetting = setSetting(oldData.execSetting, updateData.execSetting);
		oldData.executionType =updateData.executionType;
		this.commandProxy().update(oldData);
		
	}

	@Override
	public void remove(String companyId, String execItemCd) {
		KfnmtProcessExecutionPK kfnmtProcExecPK = new KfnmtProcessExecutionPK(companyId, execItemCd);
		this.commandProxy().remove(KfnmtProcessExecution.class,kfnmtProcExecPK);
	}
	
	private KfnmtExecutionScope setScope(KfnmtExecutionScope old, KfnmtExecutionScope update) {
		old.execScopeCls = update.execScopeCls;
		old.refDate = update.refDate;
		return old;
	}
	
	private KfnmtProcessExecutionSetting setSetting(KfnmtProcessExecutionSetting old, KfnmtProcessExecutionSetting update) {
		old.perScheduleCls = update.perScheduleCls;
		old.targetMonth = update.targetMonth;
		old.targetDate = update.targetDate;
		old.creationPeriod = update.creationPeriod;
		old.designatedYear = update.designatedYear;
		old.startMonthDay = update.startMonthDay;
		old.endMonthDay = update.endMonthDay;
		old.createNewEmpSched = update.createNewEmpSched;
		old.dailyPerfCls = update.dailyPerfCls;
		old.dailyPerfItem = update.dailyPerfItem;
		old.createNewEmpDailyPerf = update.createNewEmpDailyPerf;
		old.reflectResultCls = update.reflectResultCls;
		old.monthlyAggCls = update.monthlyAggCls;
		old.appRouteUpdateAtr = update.appRouteUpdateAtr;
		old.createNewEmpApp = update.createNewEmpApp;
		old.appRouteUpdateAtrMon = update.appRouteUpdateAtrMon;
		old.alarmAtr = update.alarmAtr;
		old.alarmCode = update.alarmCode;
		old.mailPrincipal = update.mailPrincipal;
		old.mailAdministrator = update.mailAdministrator;
		old.displayTpPrincipal = update.displayTpPrincipal;
		old.displayTpAdmin = update.displayTpAdmin;
		old.extOutputArt = update.extOutputArt;
		old.extAcceptanceArt = update.extAcceptanceArt;
		old.dataStorageArt = update.dataStorageArt;
		old.dataStorageCode = update.dataStorageCode;
		old.dataDeletionArt = update.dataDeletionArt;
		old.dataDeletionCode = update.dataDeletionCode;
		old.aggAnyPeriodArt = update.aggAnyPeriodArt;
		old.aggAnyPeriodCode = update.aggAnyPeriodCode;
		old.recreateChangeBus = update.recreateChangeBus;
		old.recreateTransfer = update.recreateTransfer;
		old.recreateLeaveSya = update.recreateLeaveSya;
		old.indexReorgArt = update.indexReorgArt;
		old.updStatisticsArt = update.updStatisticsArt;
		
		return old;
	}
	
	private KfnmtProcessExecution toEntity(UpdateProcessAutoExecution domain) {
		KfnmtProcessExecution entity = new KfnmtProcessExecution();
		domain.setMemento(AppContexts.user().contractCode(), entity);
		return entity;
	}
}
