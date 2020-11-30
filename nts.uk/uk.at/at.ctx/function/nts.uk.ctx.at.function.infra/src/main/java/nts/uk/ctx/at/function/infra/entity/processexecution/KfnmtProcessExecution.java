package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity 更新処理自動実行
 */
@Data
@Entity
@Table(name = "KFNMT_PROC_EXEC")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class KfnmtProcessExecution extends UkJpaEntity
		implements UpdateProcessAutoExecution.MementoGetter, UpdateProcessAutoExecution.MementoSetter, Serializable {

	public static final long serialVersionUID = 1L;

	/**
	 * Column 排他バージョン
	 */
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	/**
	 * The contract code<br>
	 * Column 契約コード
	 */
	@Column(name = "CONTRACT_CD")
	private String contractCode;

	/**
	 * The primary key
	 */
	@EmbeddedId
	private KfnmtProcessExecutionPK kfnmtProcExecPK;

	/**
	 * 名称
	 */
	@Column(name = "EXEC_ITEM_NAME")
	private String execItemName;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_EXECUTION_SCOPE")
	private KfnmtExecutionScope execScope;

	@OneToOne(mappedBy = "procExec", cascade = CascadeType.ALL, orphanRemoval = true)
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
	 * Creates new entity from domain and use memento.
	 *
	 * @param contractCode the contract code require <code>not null</code>
	 * @param domain       the domain require <code>not null</code>
	 */
	public KfnmtProcessExecution(@NonNull String contractCode, @NonNull UpdateProcessAutoExecution domain) {
		domain.setMemento(this);
		this.contractCode = contractCode;
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
	 * Sets execution setting.
	 *
	 * @param execSetting the domain execution setting
	 */
	@Override
	public void setExecSetting(ProcessExecutionSetting execSetting) {
		this.execSetting = KfnmtProcessExecutionSetting.createFromDomain(this.getCompanyId(),
																		 this.getExecItemCode(),
																		 this.contractCode,
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
		this.execSetting.setRecreateChangeBus(reExecCondition.getRecreatePersonChangeWkt().value);
		this.execSetting.setRecreateTransfer(reExecCondition.getRecreateTransfer().value);
		this.execSetting.setRecreateLeaveSya(reExecCondition.getRecreateLeave().value);
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
	 * Gets execution scope.
	 *
	 * @return the domain execution scope
	 */
	@Override
	public ProcessExecutionScope getExecScope() {
		List<String> workplaceIdList = this.execScope.workplaceIdList
													 .stream()
													 .map(KfnmtExecutionScopeItem::getWorkplaceId)
													 .collect(Collectors.toList());
		return new ProcessExecutionScope(this.execScope.execScopeCls, this.execScope.refDate, workplaceIdList);
	}

	/**
	 * Gets execution setting.
	 *
	 * @return the domain execution setting
	 */
	public ProcessExecutionSetting getExecSetting() {
		return this.execSetting.toDomain();
	}

	/**
	 * Gets re-execution condition.
	 *
	 * @return the domain re-execution condition
	 */
	@Override
	public ReExecutionCondition getReExecCondition() {
		return new ReExecutionCondition(this.execSetting.getRecreateChangeBus(),
										this.execSetting.getRecreateTransfer(),
										this.execSetting.getRecreateLeaveSya());
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

	/**
	 * Clone from other entity.
	 *
	 * @param newEntity the new <code>KfnmtProcessExecution</code> entity require <code>not null</code>
	 */
	public void cloneFromOtherEntity(KfnmtProcessExecution newEntity) {
		if (newEntity != null) {
			this.execItemName = newEntity.execItemName;
			this.execScope = newEntity.execScope;
			this.execSetting = newEntity.execSetting;
			this.executionType = newEntity.executionType;
			this.cloudCreFlag = newEntity.cloudCreFlag;
		}
	}

}
