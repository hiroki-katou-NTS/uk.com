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
@Table(name="KFNMT_AUTOEXEC_SCOPE_ITEM")
@NoArgsConstructor
public class KfnmtAutoexecScopeItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtAutoexecScopeItemPK kfnmtAutoexecScopeItemPK;
	
	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)})
	public KfnmtAutoexecScope execScope;
	
	@Override
	protected Object getKey() {
		return this.kfnmtAutoexecScopeItemPK;
	}
	
	public KfnmtAutoexecScopeItem(KfnmtAutoexecScopeItemPK kfnmtAutoexecScopeItemPK) {
		super();
		this.kfnmtAutoexecScopeItemPK = kfnmtAutoexecScopeItemPK;
	}
	
	public static KfnmtAutoexecScopeItem toEntity(String companyId, String execItemCd, String wkpId) {
		return new KfnmtAutoexecScopeItem(new KfnmtAutoexecScopeItemPK(companyId, execItemCd, wkpId));
	}
}
