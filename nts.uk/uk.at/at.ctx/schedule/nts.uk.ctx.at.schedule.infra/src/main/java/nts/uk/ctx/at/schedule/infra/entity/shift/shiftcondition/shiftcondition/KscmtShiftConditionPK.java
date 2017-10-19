package nts.uk.ctx.at.schedule.infra.entity.shift.shiftcondition.shiftcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtShiftConditionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "CONDITION_NO")
	public int conditionNo;

}
