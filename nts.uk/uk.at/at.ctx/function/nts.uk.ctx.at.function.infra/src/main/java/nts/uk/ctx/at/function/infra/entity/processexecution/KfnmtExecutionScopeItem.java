package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="KFNMT_EXEC_SCOPE_ITEM")
@NoArgsConstructor
public class KfnmtExecutionScopeItem extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The primary key
	 */
	@EmbeddedId
    public KfnmtExecutionScopeItemPK kfnmtExecScopeItemPK;

	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)})
	public KfnmtExecutionScope execScope;

	/**
	 * Gets primary key.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.kfnmtExecScopeItemPK;
	}

	public KfnmtExecutionScopeItem(KfnmtExecutionScopeItemPK kfnmtExecScopeItemPK) {
		super();
		this.kfnmtExecScopeItemPK = kfnmtExecScopeItemPK;
	}

	public KfnmtExecutionScopeItem(String companyId, String execItemCd, String wkpId) {
		super();
		this.kfnmtExecScopeItemPK = new KfnmtExecutionScopeItemPK(companyId, execItemCd, wkpId);
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId() {
		return this.kfnmtExecScopeItemPK.companyId;
	}

	/**
	 * Gets execution item code.
	 *
	 * @return the exec item code
	 */
	public String getExecItemCode() {
		return this.kfnmtExecScopeItemPK.execItemCd;
	}

	/**
	 * Gets workplace id.
	 *
	 * @return the workplace id
	 */
	public String getWorkplaceId() {
		return this.kfnmtExecScopeItemPK.wkpId;
	}

}
