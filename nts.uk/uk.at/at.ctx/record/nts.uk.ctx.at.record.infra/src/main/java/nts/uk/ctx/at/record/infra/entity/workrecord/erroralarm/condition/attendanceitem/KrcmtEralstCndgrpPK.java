/**
 * 6:25:48 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtEralstCndgrpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name ="CONDITION_GROUP_ID")
	public String conditionGroupId;
	
	@Basic(optional = false)
	@NotNull
	@Column(name ="ATD_ITEM_CON_NO")
	public int atdItemConNo;
	
}
