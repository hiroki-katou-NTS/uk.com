/**
 * 4:34:55 PM Aug 28, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_SYAIN_DP_ER_LIST")
public class KrcdtSyainDpErList extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtSyainDpErListPK krcdtSyainDpErListPK;

	@Column(name ="ATTENDANCE_ITEM_ID")
	public BigDecimal attendanceItemId;
	
	@Column(name ="ERROR_CANCELABLE")
	public BigDecimal errorCancelable;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.krcdtSyainDpErListPK;
	}
}
