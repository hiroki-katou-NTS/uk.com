package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHK_REFPMT")
public class KfnmtAlarmCheckConditionCategoryRole extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlarmCheckConditionCategoryRolePk pk;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false)
	})
	public KfnmtAlarmCheckConditionCategory condition;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public KfnmtAlarmCheckConditionCategoryRole(String companyId, int category, String code, String roleId) {
		super();
		this.pk = new KfnmtAlarmCheckConditionCategoryRolePk(companyId, category, code, roleId);
	}

}
