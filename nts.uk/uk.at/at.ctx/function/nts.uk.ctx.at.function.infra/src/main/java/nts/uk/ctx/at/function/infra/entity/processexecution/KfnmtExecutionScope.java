package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity 実行範囲
 */
@Entity
@Table(name = "KFNMT_EXECUTION_SCOPE")
@NoArgsConstructor
public class KfnmtExecutionScope extends UkJpaEntity implements Serializable {

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
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "EXEC_ITEM_CD", referencedColumnName = "EXEC_ITEM_CD", insertable = false, updatable = false)
	})
	public KfnmtProcessExecution procExec;

	@OneToMany(mappedBy = "execScope", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_EXEC_SCOPE_ITEM")
	public List<KfnmtExecutionScopeItem> workplaceIdList;

	/**
	 * Gets primary key.
	 *
	 * @return the primary key
	 */
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

	/**
	 * Creates from domain.
	 *
	 * @param companyId    the company id
	 * @param execItemCode the exec item code
	 * @param domain       the domain
	 * @return the entity kfnmt execution scope
	 */
	public static KfnmtExecutionScope createFromDomain(String companyId, String execItemCode, ProcessExecutionScope domain) {
		if (domain == null) {
			return null;
		}
		List<KfnmtExecutionScopeItem> workplaceIdList = domain.getWorkplaceIdList()
															  .stream()
															  .map(procExecScopeItem -> KfnmtExecutionScopeItem.toEntity(
																							procExecScopeItem.getCompanyId(),
																							procExecScopeItem.getExecItemCd(),
																							procExecScopeItem.getWkpId()))
															  .collect(Collectors.toList());
		return new KfnmtExecutionScope(new KfnmtExecutionScopePK(companyId, execItemCode),
									   domain.getExecScopeCls().value,
									   domain.getRefDate(),
									   workplaceIdList);
	}

}
