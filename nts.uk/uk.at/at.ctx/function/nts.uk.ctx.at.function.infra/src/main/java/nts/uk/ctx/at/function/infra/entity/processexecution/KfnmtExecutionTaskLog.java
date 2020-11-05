package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *  Entity UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行タスクログ
 */
@Entity
@Table(name="KFNMT_EXEC_TASK_LOG")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KfnmtExecutionTaskLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtExecutionTaskLogPK kfnmtExecTaskLogPK;

	/** The exclus ver. */
	@Version
	@Column(name = "EXCLUS_VER")
	private Long exclusVer;

	/** The Contract Code. */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/* 終了状態 */
	@Column(name = "STATUS")
	public Integer status;

	@Column(name = "LAST_EXEC_DATETIME")
	public GeneralDateTime lastExecDateTime;

	@Column(name = "LAST_END_EXEC_DATETIME")
	public GeneralDateTime lastEndExecDateTime;

	@Column(name = "ERROR_SYSTEM")
	public Integer errorSystem;

	@Column(name = "ERROR_BUSINESS")
	public Integer errorBusiness;

	/**
	 * The error system detail.
	 * システムエラー内容
	 **/
	@Column(name = "ERROR_SYSTEM_CONT")
	public String errorSystemDetail;

	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)})
	public KfnmtProcessExecutionLog procExecLogItem;

	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ID", referencedColumnName="EXEC_ID", insertable = false, updatable = false)})
	public KfnmtProcessExecutionLogHistory procExecLogHistItem;

	@Override
	protected Object getKey() {
		return this.kfnmtExecTaskLogPK;
	}

	public static KfnmtExecutionTaskLog toEntity(String companyId, String execItemCd, String execId, ExecutionTaskLog domain) {
		return KfnmtExecutionTaskLog.builder()
				.kfnmtExecTaskLogPK(new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, domain.getProcExecTask().value))
				.contractCode(AppContexts.user().contractCode())
				.errorBusiness(domain.getErrorBusiness().isPresent() ? (domain.getErrorBusiness().get() ? 1 : 0) : null)
				.errorSystem(domain.getErrorSystem().isPresent() ? (domain.getErrorSystem().get() ? 1 : 0) : null)
				.errorSystemDetail(domain.getSystemErrorDetails().orElse(null))
				.lastEndExecDateTime(domain.getLastEndExecDateTime().orElse(null))
				.lastExecDateTime(domain.getLastExecDateTime().orElse(null))
				.status(domain.getStatus().isPresent() ? domain.getStatus().get().value : null)
				.build();
	}

	public static List<KfnmtExecutionTaskLog> toListEntity(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> domains) {
		return domains.stream()
				.map(domain -> KfnmtExecutionTaskLog.builder()
						.kfnmtExecTaskLogPK(new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, domain.getProcExecTask().value))
						.status(domain.getStatus().isPresent() ? domain.getStatus().get().value : null)
						.errorBusiness(domain.getErrorBusiness().isPresent() ? (domain.getErrorBusiness().get() ? 1 : 0) : null)
						.errorSystem(domain.getErrorSystem().isPresent() ? (domain.getErrorSystem().get() ? 1 : 0) : null)
						.errorSystemDetail(domain.getSystemErrorDetails().orElse(null))
						.lastEndExecDateTime(domain.getLastEndExecDateTime().orElse(null))
						.lastExecDateTime(domain.getLastExecDateTime().orElse(null))
						.build())
				.collect(Collectors.toList());
	}

	public ExecutionTaskLog toDomain() {
		return ExecutionTaskLog.builder()
				.procExecTask(EnumAdaptor.valueOf(kfnmtExecTaskLogPK.taskId, ProcessExecutionTask.class))
				.status(Optional.ofNullable(EnumAdaptor.valueOf(status, EndStatus.class)))
				.lastExecDateTime(Optional.ofNullable(lastExecDateTime))
				.lastEndExecDateTime(Optional.ofNullable(lastEndExecDateTime))
				.errorSystem(Optional.of(errorSystem == 1))
				.errorBusiness(Optional.of(errorBusiness == 1))
				.systemErrorDetails(Optional.ofNullable(errorSystemDetail))
				.build();
	}
}
