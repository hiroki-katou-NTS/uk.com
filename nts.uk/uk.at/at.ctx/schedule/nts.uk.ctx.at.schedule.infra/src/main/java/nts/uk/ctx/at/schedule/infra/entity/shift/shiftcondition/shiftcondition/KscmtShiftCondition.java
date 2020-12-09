package nts.uk.ctx.at.schedule.infra.entity.shift.shiftcondition.shiftcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * シフト条件
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SHIFT_CONDITION")
public class KscmtShiftCondition extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtShiftConditionPK kscmntShiftConditionPk;

	@Column(name = "CONDITION_NAME")
	public String conditionName;

	@Column(name = "CONDITION_ERROR_MESSAGE")
	public String conditionErrorMessage;

	@Column(name = "CATEGORY_NO")
	public int categoryNo;

	@Column(name = "CONDITION_DETAIL_NO")
	public int conditionDetailNo;

	@Override
	protected Object getKey() {
		return this.kscmntShiftConditionPk;
	}

}
