package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_AL_CHECK_COND_CATE")
public class KfnmtAlarmCheckConditionCategory extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlarmCheckConditionCategoryPk pk;
	
	@Basic
	@Column(name = "NAME")
	public String name;
	
	@Basic
	@Column(name = "EXTRACT_TARGET_COND_ID")
	public String targetConditionId;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="alarmCheckConditionByCategory", orphanRemoval = true)
	public KfnmtAlarmCheckTargetCondition targetCondition;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="condition", orphanRemoval = true)
	public List<KfnmtAlarmCheckConditionCategoryRole> listAvailableRole;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KfnmtAlarmCheckConditionCategory(String companyId, int category, String code, String name,
			String targetConditionId) {
		super();
		this.pk = new KfnmtAlarmCheckConditionCategoryPk(companyId, category, code);
		this.name = name;
		this.targetConditionId = targetConditionId;
	}
	
	

}
