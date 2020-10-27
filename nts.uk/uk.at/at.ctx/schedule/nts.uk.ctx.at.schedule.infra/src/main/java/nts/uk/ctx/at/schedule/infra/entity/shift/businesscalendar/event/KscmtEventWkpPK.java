/**
 * 9:56:29 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtEventWkpPK implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "WKPID")
	public String workplaceId;

	@Basic(optional = false)
	@Column(name = "YMD_K")
	public GeneralDate date;
}
