package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name="KFNMT_EXEC_TASK_LOG")
@NoArgsConstructor
public class KfnmtExecutionTaskLog extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtExecutionTaskLogPK kfnmtExecTaskLogPK;
	
	/* 終了状態 */
	@Column(name = "STATUS")
	public int status;
	
	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ID", referencedColumnName="EXEC_ID", insertable = false, updatable = false)})
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
	
	public KfnmtExecutionTaskLog(KfnmtExecutionTaskLogPK kfnmtExecTaskLogPK, int status) {
		super();
		this.kfnmtExecTaskLogPK = kfnmtExecTaskLogPK;
		this.status = status;
	}
	
	public ExecutionTaskLog toDomain() {
		return new ExecutionTaskLog(EnumAdaptor.valueOf(this.kfnmtExecTaskLogPK.taskId, ProcessExecutionTask.class),
				EnumAdaptor.valueOf(this.status, EndStatus.class));
		
	}
	
	public static KfnmtExecutionTaskLog toEntity(String companyId, String execItemCd, String execId, ExecutionTaskLog domain) {
		return new KfnmtExecutionTaskLog(
				new KfnmtExecutionTaskLogPK(companyId, execItemCd, execId, domain.getProcExecTask().value),
				domain.getStatus() == null ? null : domain.getStatus().value);
	}
}
