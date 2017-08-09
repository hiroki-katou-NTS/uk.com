package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.ConditionValue;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author TanLV
 *
 */

@Entity
@Table(name="KSHST_GRANTING_CONDITION")
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantingCondition extends UkJpaEntity {
	/*主キー*/
	@EmbeddedId
    public KshstGrantingConditionPK kshstGrantingConditionPK;
	
	/* 条件値 */
	@Column(name = "CONDITION_VALUE")
	public ConditionValue conditionValue;
	
	/* 条件利用区分 */
	@Column(name = "USE_CONDITION_CLS")
	public int useConditionClassification;
	
	@Override
	protected Object getKey() {
		return kshstGrantingConditionPK;
	}

}
