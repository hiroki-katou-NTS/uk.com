package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.AlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateDaily;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionName;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionScopeClassification;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecType;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationTarget;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetDate;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetMonth;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetSetting;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_PROC_EXEC")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecution extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KfnmtProcessExecutionPK kfnmtProcExecPK;

	/* 名称 */
	@Column(name = "EXEC_ITEM_NAME")
	public String execItemName;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_EXECUTION_SCOPE")
	public KfnmtExecutionScope execScope;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_PROC_EXEC_SETTING")
	public KfnmtProcessExecutionSetting execSetting;

	/* 実行種別 */
	@Column(name = "PROCESS_EXEC_TYPE")
	public int processExecType;
	
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecPK;
	}

	/**
	 * Convert entity to domain
	 * 
	 * @return WorkplaceManager object
	 */
	public ProcessExecution toDomain() {
		List<ProcessExecutionScopeItem> workplaceIdList = this.execScope.workplaceIdList.stream()
				.map(x -> ProcessExecutionScopeItem.createSimpleFromJavaType(x.kfnmtExecScopeItemPK.companyId,
						x.kfnmtExecScopeItemPK.execItemCd, x.kfnmtExecScopeItemPK.wkpId))
				.collect(Collectors.toList());

		ProcessExecutionScope execScope = new ProcessExecutionScope(
				EnumAdaptor.valueOf(this.execScope.execScopeCls, ExecutionScopeClassification.class),
				this.execScope.refDate, workplaceIdList);

		AlarmExtraction alarmExtraction =new AlarmExtraction(
				this.execSetting.alarmAtr ==1?true:false,
				this.execSetting.alarmCode == null?null:(new AlarmPatternCode(this.execSetting.alarmCode)),
				this.execSetting.mailPrincipal == null?null:(this.execSetting.mailPrincipal ==1 ?true:false),
				this.execSetting.mailAdministrator == null?null:(this.execSetting.mailAdministrator ==1 ?true:false)
				);
		
		PersonalScheduleCreationPeriod period = new PersonalScheduleCreationPeriod(
				new CreationPeriod(this.execSetting.creationPeriod), new TargetDate(this.execSetting.targetDate),
				EnumAdaptor.valueOf(this.execSetting.targetMonth, TargetMonth.class));

		PersonalScheduleCreationTarget target = new PersonalScheduleCreationTarget(
				EnumAdaptor.valueOf(this.execSetting.creationTarget, TargetClassification.class),
				new TargetSetting(this.execSetting.recreateWorkType == 1 ? true : false,
						this.execSetting.manualCorrection == 1 ? true : false,
						this.execSetting.createEmployee == 1 ? true : false,
						this.execSetting.recreateTransfer == 1 ? true : false));
		PersonalScheduleCreation perSchCreation = new PersonalScheduleCreation(period,
				this.execSetting.perScheduleCls == 1 ? true : false, target);
		DailyPerformanceCreation dailyPerfCreation = new DailyPerformanceCreation(
				this.execSetting.dailyPerfCls == 1 ? true : false,
				EnumAdaptor.valueOf(this.execSetting.dailyPerfItem, DailyPerformanceItem.class), new TargetGroupClassification(this.execSetting.recreateTypeChangePerson == 1 ? true : false,
						this.execSetting.midJoinEmployee == 1 ? true : false, this.execSetting.recreateTransfers == 1 ? true : false));

		ProcessExecutionSetting execSetting = new ProcessExecutionSetting(alarmExtraction, perSchCreation,
				dailyPerfCreation, this.execSetting.reflectResultCls == 1 ? true : false,
				this.execSetting.monthlyAggCls == 1 ? true : false,
				new AppRouteUpdateDaily(
						EnumAdaptor.valueOf(this.execSetting.appRouteUpdateAtr, NotUseAtr.class) ,
						this.execSetting.createNewEmp==null?null:EnumAdaptor.valueOf(this.execSetting.createNewEmp, NotUseAtr.class)),
				EnumAdaptor.valueOf(this.execSetting.appRouteUpdateAtrMon, NotUseAtr.class)
				);

		return new ProcessExecution(this.kfnmtProcExecPK.companyId, new ExecutionCode(this.kfnmtProcExecPK.execItemCd),
				new ExecutionName(this.execItemName), execScope, execSetting,
				EnumAdaptor.valueOf(this.processExecType, ProcessExecType.class)
				);
	}

	public static KfnmtProcessExecution toEntity(ProcessExecution domain) {
		KfnmtProcessExecutionPK kfnmtProcExecPK = new KfnmtProcessExecutionPK(domain.getCompanyId(),
				domain.getExecItemCd().v());
		List<KfnmtExecutionScopeItem> wkpList = domain.getExecScope().getWorkplaceIdList().stream()
				.map(x -> KfnmtExecutionScopeItem.toEntity(x.getCompanyId(), x.getExecItemCd(), x.getWkpId()))
				.collect(Collectors.toList());
		KfnmtExecutionScope execScope = new KfnmtExecutionScope(
				new KfnmtExecutionScopePK(domain.getCompanyId(), domain.getExecItemCd().v()),
				domain.getExecScope().getExecScopeCls().value, domain.getExecScope().getRefDate(), wkpList);
		KfnmtProcessExecutionSetting execSetting = new KfnmtProcessExecutionSetting(
				new KfnmtProcessExecutionSettingPK(domain.getCompanyId(), domain.getExecItemCd().v()),
				domain.getExecSetting().getPerSchedule().isPerSchedule() ? 1 : 0,
				domain.getExecSetting().getPerSchedule().getPeriod().getTargetMonth().value,
				/*
				 * domain.getExecSetting().getPerSchedule().getPeriod().
				 * getTargetDate() == null ? null :
				 */domain.getExecSetting().getPerSchedule().getPeriod().getTargetDate().v(),
				/*
				 * domain.getExecSetting().getPerSchedule().getPeriod().
				 * getCreationPeriod() == null ? null :
				 */domain.getExecSetting().getPerSchedule().getPeriod().getCreationPeriod().v(),
				domain.getExecSetting().getPerSchedule().getTarget().getCreationTarget().value,
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType() ? 1 : 0,
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isManualCorrection() ? 1 : 0,
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isCreateEmployee() ? 1 : 0,
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isRecreateTransfer() ? 1 : 0,
				domain.getExecSetting().getDailyPerf().isDailyPerfCls() ? 1 : 0,
				domain.getExecSetting().getDailyPerf().getDailyPerfItem().value,
				domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isMidJoinEmployee() ? 1 : 0,
				domain.getExecSetting().isReflectResultCls() ? 1 : 0, domain.getExecSetting().isMonthlyAggCls() ? 1 : 0,
				domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson()?1:0,domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTransfer()?1:0,
				domain.getExecSetting().getAppRouteUpdateDaily().getAppRouteUpdateAtr().value,
				domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmp().get()==null?null:domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmp().get().value,
				domain.getExecSetting().getAppRouteUpdateMonthly().value,
				domain.getExecSetting().getAlarmExtraction().isAlarmAtr()?1:0,
				domain.getExecSetting().getAlarmExtraction().getAlarmCode().isPresent()?
						domain.getExecSetting().getAlarmExtraction().getAlarmCode().get().v():null,
				!domain.getExecSetting().getAlarmExtraction().getMailPrincipal().isPresent()?null:
						domain.getExecSetting().getAlarmExtraction().getMailPrincipal().get()?1:0,
				!domain.getExecSetting().getAlarmExtraction().getMailAdministrator().isPresent()?null:
						domain.getExecSetting().getAlarmExtraction().getMailAdministrator().get()?1:0
				);
		return new KfnmtProcessExecution(kfnmtProcExecPK, domain.getExecItemName().v(), execScope, execSetting,domain.getProcessExecType().value);
	}
}
