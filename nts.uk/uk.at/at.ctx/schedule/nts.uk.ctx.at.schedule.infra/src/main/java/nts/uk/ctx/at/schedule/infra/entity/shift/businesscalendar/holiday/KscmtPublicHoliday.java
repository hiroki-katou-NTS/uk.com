/**
 * 9:47:12 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.holiday;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PUBLIC_HOLIDAY")
public class KscmtPublicHoliday extends ContractUkJpaEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtPublicHolidayPK kscmtPublicHolidayPK;

	@Basic(optional = false)
	@Column(name = "HOLIDAY_NAME")
	public String holidayName;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.kscmtPublicHolidayPK;
	}
}
