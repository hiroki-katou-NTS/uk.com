package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@Table(name="KFNMT_EXECUTION_SCOPE")
@NoArgsConstructor
public class KfnmtExecutionScope extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtExecutionScopePK kfnmtExecScopePK;
	
	/* 実行範囲区分 */
	@Column(name = "EXEC_SCOPE_CLS")
	public int execScopeCls;
	
	/* 基準日 */
	@Column(name = "REF_DATE")
	public GeneralDate refDate;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)
	})
	public KfnmtProcessExecution procExec;
	
	@OneToMany(mappedBy = "execScope", cascade = CascadeType.ALL)
    @JoinTable(name = "KFNMT_EXEC_SCOPE_ITEM")
    public List<KfnmtExecutionScopeItem> workplaceIdList;

	
	@Override
	protected Object getKey() {
		return this.kfnmtExecScopePK;
	}

	public KfnmtExecutionScope(KfnmtExecutionScopePK kfnmtExecScopePK, int execScopeCls, GeneralDate refDate,
			List<KfnmtExecutionScopeItem> workplaceIdList) {
		super();
		this.kfnmtExecScopePK = kfnmtExecScopePK;
		this.execScopeCls = execScopeCls;
		this.refDate = refDate;
		this.workplaceIdList = workplaceIdList;
	}
}
