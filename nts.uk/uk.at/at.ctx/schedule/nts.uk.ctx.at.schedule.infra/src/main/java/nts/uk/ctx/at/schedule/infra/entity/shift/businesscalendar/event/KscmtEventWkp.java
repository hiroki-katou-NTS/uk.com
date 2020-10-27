/**
 * 9:56:23 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event;

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
@Table(name = "KSCMT_EVENT_WKP")
public class KscmtEventWkp extends ContractUkJpaEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtEventWkpPK kscmtEventWkpPK;
	
	@Basic(optional = false)
	@Column(name = "EVENT_NAME")
	public String eventName;

	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.kscmtEventWkpPK;
	}
}
