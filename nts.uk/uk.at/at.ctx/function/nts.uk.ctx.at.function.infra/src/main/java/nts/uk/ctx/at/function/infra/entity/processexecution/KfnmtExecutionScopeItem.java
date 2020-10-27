package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@Table(name="KFNMT_EXEC_SCOPE_ITEM")
@NoArgsConstructor
public class KfnmtExecutionScopeItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtExecutionScopeItemPK kfnmtExecScopeItemPK;
	
	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)})
	public KfnmtExecutionScope execScope;
	
	@Override
	protected Object getKey() {
		return this.kfnmtExecScopeItemPK;
	}
	
	public KfnmtExecutionScopeItem(KfnmtExecutionScopeItemPK kfnmtExecScopeItemPK) {
		super();
		this.kfnmtExecScopeItemPK = kfnmtExecScopeItemPK;
	}
	
	public static KfnmtExecutionScopeItem toEntity(String companyId, String execItemCd, String wkpId) {
		return new KfnmtExecutionScopeItem(new KfnmtExecutionScopeItemPK(companyId, execItemCd, wkpId));
	}
}
