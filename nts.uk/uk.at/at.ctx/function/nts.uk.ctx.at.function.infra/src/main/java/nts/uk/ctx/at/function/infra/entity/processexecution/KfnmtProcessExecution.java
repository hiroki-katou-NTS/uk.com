package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.*;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.*;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Entity 更新処理自動実行
 */
@Data
@Entity
@Table(name = "KFNMT_PROC_EXEC")
@EqualsAndHashCode(callSuper = true)
public class KfnmtProcessExecution extends UkJpaEntity
		implements UpdateProcessAutoExecution.MementoGetter, UpdateProcessAutoExecution.MementoSetter, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Column 排他バージョン
	 */
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	/**
	 * The Primary key
	 */
	@EmbeddedId
	private KfnmtProcessExecutionPK kfnmtProcExecPK;

	/**
	 * 名称
	 */
	@Column(name = "EXEC_ITEM_NAME")
	private String execItemName;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_EXECUTION_SCOPE")
	private KfnmtExecutionScope execScope;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_PROC_EXEC_SETTING")
	private KfnmtProcessExecutionSetting execSetting;

	/**
	 * 実行種別
	 */
	@Column(name = "PROCESS_EXEC_TYPE")
	private int executionType;

	/**
	 * クラウド作成フラグ
	 */
	@Column(name = "CLOUD_CRE_FLAG")
	private int cloudCreFlag;

	/**
	 * Gets primary key.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.kfnmtProcExecPK;
	}

	/**
	 * Convert entity to domain
	 *
	 * @return WorkplaceManager object
	 */
	public UpdateProcessAutoExecution toDomain() {
		List<ProcessExecutionScopeItem> workplaceIdList = this.execScope.workplaceIdList.stream()
				.map(x -> ProcessExecutionScopeItem.createSimpleFromJavaType(x.kfnmtExecScopeItemPK.companyId,
						x.kfnmtExecScopeItemPK.execItemCd, x.kfnmtExecScopeItemPK.wkpId))
				.collect(Collectors.toList());

		ProcessExecutionScope execScope = new ProcessExecutionScope(
				EnumAdaptor.valueOf(this.execScope.execScopeCls, ExecutionScopeClassification.class),
				this.execScope.refDate, workplaceIdList);



		return new UpdateProcessAutoExecution(
				this.kfnmtProcExecPK.companyId,
				new ExecutionCode(this.kfnmtProcExecPK.execItemCd),
				new ExecutionName(this.execItemName),
				execScope,
				execSetting,
				EnumAdaptor.valueOf(this.executionType, ProcessExecType.class),
				(this.execSetting.cloudCreFlag == 1));
	}

	public static KfnmtProcessExecution toEntity(UpdateProcessAutoExecution domain) {
		KfnmtProcessExecutionPK kfnmtProcExecPK = new KfnmtProcessExecutionPK(domain.getCompanyId(),
				domain.getExecItemCode().v());
		List<KfnmtExecutionScopeItem> wkpList = domain.getExecScope().getWorkplaceIdList().stream()
				.map(x -> KfnmtExecutionScopeItem.toEntity(x.getCompanyId(), x.getExecItemCd(), x.getWkpId()))
				.collect(Collectors.toList());
		KfnmtExecutionScope execScope = new KfnmtExecutionScope(
				new KfnmtExecutionScopePK(domain.getCompanyId(), domain.getExecItemCode().v()),
				domain.getExecScope().getExecScopeCls().value, domain.getExecScope().getRefDate(), wkpList);
		KfnmtProcessExecutionSetting execSetting = new KfnmtProcessExecutionSetting(
				new KfnmtProcessExecutionSettingPK(domain.getCompanyId(), domain.getExecItemCode().v()),
				domain.getVersion(),
				AppContexts.user().contractCode(),
				domain.getExecSetting().getPerScheduleCreation().isPerSchedule() ? 1 : 0,
				domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getTargetMonth().value,
				/*
				 * domain.getExecSetting().getPerSchedule().getPeriod().
				 * getTargetDate() == null ? null :
				 */domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getTargetDate().v(),
				/*
				 * domain.getExecSetting().getPerSchedule().getPeriod().
				 * getCreationPeriod() == null ? null :
				 */domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getCreationPeriod().v(),
				!domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getDesignatedYear().isPresent()
						? null
						: domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getDesignatedYear().get().value,
				!domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getStartMonthDay().isPresent()
						? null
						: (domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getStartMonthDay().get().getMonth() * 100
						+ domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getStartMonthDay().get().getDay()),
				!domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getEndMonthDay().isPresent()
						? null
						: (domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getEndMonthDay().get().getMonth() * 100
						+ domain.getExecSetting().getPerScheduleCreation().getPerSchedulePeriod().getEndMonthDay().get().getDay()),
				domain.getExecSetting().getPerScheduleCreation().getTarget().getTargetSetting().isCreateEmployee() ? 1 : 0,
				domain.getExecSetting().getDailyPerf().isDailyPerfCls() ? 1 : 0,
				domain.getExecSetting().getDailyPerf().getDailyPerfItem().value,
				domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isMidJoinEmployee() ? 1 : 0,
				domain.getExecSetting().isReflectResultCls() ? 1 : 0, domain.getExecSetting().isMonthlyAggCls() ? 1 : 0,
				domain.getExecSetting().getAppRouteUpdateDaily().getAppRouteUpdateAtr().value,
				domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmpApp().isPresent()
						? domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmpApp().get().value
						: null,
				domain.getExecSetting().getAppRouteUpdateMonthly().value,
				domain.getExecSetting().getAlarmExtraction().isAlarmAtr() ? 1 : 0,
				domain.getExecSetting().getAlarmExtraction().getAlarmCode().isPresent()
						? domain.getExecSetting().getAlarmExtraction().getAlarmCode().get().v()
						: null,
				domain.getExecSetting().getAlarmExtraction().getMailPrincipal().isPresent()
						? (domain.getExecSetting().getAlarmExtraction().getMailPrincipal().get() ? 1 : 0)
						: null,
				domain.getExecSetting().getAlarmExtraction().getMailAdministrator().isPresent()
						? (domain.getExecSetting().getAlarmExtraction().getMailAdministrator().get() ? 1 : 0)
						: null,
				domain.getExecSetting().getAlarmExtraction().getDisplayOnTopPagePrincipal().isPresent()
						? domain.getExecSetting().getAlarmExtraction().getDisplayOnTopPagePrincipal().get() ? 1 : 0
						: null,
				domain.getExecSetting().getAlarmExtraction().getDisplayOnTopPageAdministrator().isPresent()
						? domain.getExecSetting().getAlarmExtraction().getDisplayOnTopPageAdministrator().get() ? 1 : 0
						: null,
				domain.getExecSetting().getExternalOutput().getExtOutputCls().value,
				domain.getExecSetting().getExternalAcceptance().getExtAcceptCls().value,
				domain.getExecSetting().getSaveData().getSaveDataCls().value,
				domain.getExecSetting().getSaveData().getPatternCode().isPresent()
						? domain.getExecSetting().getSaveData().getPatternCode().get().v()
						: null,
				domain.getExecSetting().getDeleteData().getDataDelCls().value,
				domain.getExecSetting().getDeleteData().getPatternCode().isPresent()
						? domain.getExecSetting().getDeleteData().getPatternCode().get().v()
						: null,
				domain.getExecSetting().getAggrAnyPeriod().getAggAnyPeriodAttr().value,
				domain.getExecSetting().getAggrAnyPeriod().getAggrFrameCode().isPresent()
						? domain.getExecSetting().getAggrAnyPeriod().getAggrFrameCode().get().v()
						: null,
				domain.getExecSetting().getPerScheduleCreation().getTarget().getTargetSetting().isRecreateWorkType() ? 1 : 0,
				domain.getExecSetting().getPerScheduleCreation().getTarget().getTargetSetting().isRecreateTransfer() ? 1 : 0,
				1,//TODO QA111576
//				this.recreLeaveSya = recreLeaveSya;
				domain.getExecSetting().getIndexReconstruction().getIndexReorgAttr().value,
				domain.getExecSetting().getIndexReconstruction().getUpdateStatistics().value,
				domain.getCloudCreationFlag() ? 1 : 0);
		return new KfnmtProcessExecution(kfnmtProcExecPK, domain.getExecItemName().v(), execScope, execSetting, domain.getExecutionType().value);
	}

	/**
	 * Sets company id.
	 *
	 * @param companyId the company id
	 */
	@Override
	public void setCompanyId(String companyId) {
		if (this.kfnmtProcExecPK == null) {
			this.kfnmtProcExecPK = new KfnmtProcessExecutionPK();
		}
		this.kfnmtProcExecPK.companyId = companyId;
	}

	/**
	 * Sets execution item code.
	 *
	 * @param execItemCode the execution item code
	 */
	@Override
	public void setExecItemCode(String execItemCode) {
		if (this.kfnmtProcExecPK == null) {
			this.kfnmtProcExecPK = new KfnmtProcessExecutionPK();
		}
		this.kfnmtProcExecPK.execItemCd = execItemCode;
	}

	/**
	 * Sets execution scope.
	 *
	 * @param execScope the domain execution scope
	 */
	@Override
	public void setExecScope(ProcessExecutionScope execScope) {
		this.execScope = KfnmtExecutionScope.createFromDomain(this.getCompanyId(), this.getExecItemCode(), execScope);
	}

	/**
	 * Sets contract code.
	 *
	 * @param contractCode the contract code
	 */
	@Override
	public void setContractCode(String contractCode) {
		if (this.execSetting == null) {
			this.execSetting = new KfnmtProcessExecutionSetting();
		}
//		this.execSetting.getContractCode() = contractCode;
	}

	/**
	 * Sets execution setting.
	 *
	 * @param execSetting the domain execution setting
	 */
	@Override
	public void setExecSetting(ProcessExecutionSetting execSetting) {
		this.execSetting = KfnmtProcessExecutionSetting.createFromDomain(this.getCompanyId(),
																		 this.getExecItemCode(),
																		 AppContexts.user().contractCode(),
																		 null,
																		 execSetting);
	}

	/**
	 * Sets execution type.
	 *
	 * @param executionType the execution type
	 */
	@Override
	public void setExecutionType(int executionType) {
		this.executionType = executionType;
	}

	/**
	 * Sets re-execution condition.
	 *
	 * @param reExecCondition the domain re-execution condition
	 */
	@Override
	public void setReExecCondition(ReExecutionCondition reExecCondition) {

	}

	/**
	 * Sets cloud creation flag.
	 *
	 * @param cloudCreFlag the cloud creation flag
	 */
	@Override
	public void setCloudCreFlag(boolean cloudCreFlag) {
		this.cloudCreFlag = cloudCreFlag ? 1 : 0;
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.kfnmtProcExecPK.companyId;
	}

	/**
	 * Gets execution item code.
	 *
	 * @return the execution item code
	 */
	@Override
	public String getExecItemCode() {
		return this.kfnmtProcExecPK.execItemCd;
	}

	/**
	 * Gets execution setting.
	 *
	 * @return the domain execution setting
	 */
	public ProcessExecutionSetting getExecSetting() {
		return this.execSetting KfnmtProcessExecutionSetting.
	}

	/**
	 * Gets re-execution condition.
	 *
	 * @return the domain re-execution condition
	 */
	@Override
	public ReExecutionCondition getReExecCondition() {
		return this.execSetting;
	}

	/**
	 * Gets cloud creation flag.
	 *
	 * @return {@code false} if {@link #cloudCreFlag} equals 0, {@code true} if {@link #cloudCreFlag} equals 1
	 */
	@Override
	public boolean getCloudCreFlag() {
		return this.cloudCreFlag == 1;
	}

}
