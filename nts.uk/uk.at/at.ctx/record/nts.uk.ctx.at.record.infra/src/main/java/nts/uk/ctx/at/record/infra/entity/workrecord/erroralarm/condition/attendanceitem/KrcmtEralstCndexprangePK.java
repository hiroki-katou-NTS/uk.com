/**
 * 10:52:01 AM Dec 6, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtEralstCndexprangePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 36)
	@Column(name = "CONDITION_GROUP_ID")
	public String conditionGroupId;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ATD_ITEM_CON_NO")
	public int atdItemConNo;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
	
}
