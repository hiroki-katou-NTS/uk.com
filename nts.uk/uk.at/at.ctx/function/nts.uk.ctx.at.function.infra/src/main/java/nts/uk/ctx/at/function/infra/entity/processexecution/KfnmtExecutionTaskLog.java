package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * The Class KfnmtExecutionTaskLog.
 * 	更新処理自動実行タスクログ
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
		
		Integer status = null;
		if(domain.getStatus()!=null){
			if(domain.getStatus().isPresent()){
				status =domain.getStatus().get().value;
			}
		}
		return KfnmtExecutionTaskLog.builder()
				.kfnmtExecTaskLogPK(new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, domain.getProcExecTask().value))
				.contractCode(AppContexts.user().contractCode())
				.errorBusiness(domain.getErrorBusiness() == null ? null : (domain.getErrorBusiness() ? 1 : 0))
				.errorSystem(domain.getErrorSystem() == null ? null : (domain.getErrorSystem() ? 1 : 0))
				.errorSystemDetail(domain.getSystemErrorDetails())
				.lastEndExecDateTime(domain.getLastEndExecDateTime())
				.lastExecDateTime(domain.getLastExecDateTime())
				.status(status)
				.build();
	}
	
	
	public static List<KfnmtExecutionTaskLog> toEntity(String companyId, String execItemCd, String execId, List<ExecutionTaskLog> domains) {
		List<KfnmtExecutionTaskLog> datas = new ArrayList<>();
		for(ExecutionTaskLog executionTaskLog : domains) {
			Integer status = null;
			if(executionTaskLog.getStatus()!=null){
				if(executionTaskLog.getStatus().isPresent()){
					status =executionTaskLog.getStatus().get().value;
				}
			}
			KfnmtExecutionTaskLog kfnmtExecutionTaskLog = KfnmtExecutionTaskLog.builder()
					.kfnmtExecTaskLogPK(new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, executionTaskLog.getProcExecTask().value))
					.status(status)
					.lastEndExecDateTime(executionTaskLog.getLastEndExecDateTime())
					.lastExecDateTime(executionTaskLog.getLastExecDateTime())
					.errorSystem(executionTaskLog.getErrorSystem() == null ? null : (executionTaskLog.getErrorSystem() ? 1 : 0))
					.errorBusiness(executionTaskLog.getErrorBusiness() == null ? null : (executionTaskLog.getErrorBusiness() ? 1 : 0))
					.errorSystemDetail(executionTaskLog.getSystemErrorDetails())
					.build();
			datas.add(kfnmtExecutionTaskLog);
		}
		
		return datas;
	}
	
	public ExecutionTaskLog toDomain() {
		
		return new ExecutionTaskLog(EnumAdaptor.valueOf(this.kfnmtExecTaskLogPK.taskId, ProcessExecutionTask.class),
				(this.status!=null)?Optional.ofNullable(EnumAdaptor.valueOf(this.status.intValue(), EndStatus.class)):Optional.empty());
		
	}
	
	public ExecutionTaskLog toNewDomain() {
		
		return new ExecutionTaskLog(
				EnumAdaptor.valueOf(this.kfnmtExecTaskLogPK.taskId, ProcessExecutionTask.class),
				(this.status != null) ? Optional.ofNullable(EnumAdaptor.valueOf(this.status.intValue(), EndStatus.class)) : Optional.empty(),
				this.kfnmtExecTaskLogPK.execId,
				this.lastExecDateTime,
				this.lastEndExecDateTime,
				this.errorSystem == null ? null : (this.errorSystem == 1 ? true : false),
				this.errorBusiness == null ? null : (this.errorBusiness == 1 ? true : false),
				this.errorSystemDetail);
		
	}
	

	
}
