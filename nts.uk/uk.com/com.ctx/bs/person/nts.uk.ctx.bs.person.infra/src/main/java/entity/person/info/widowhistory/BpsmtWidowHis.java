/**
 * 
 */
package entity.person.info.widowhistory;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "BPSMT_WIDOW_HIS")
public class BpsmtWidowHis extends JpaEntity {

	@Id
	@Column(name = "OLDER_WIDOW_ID")
	public String olderWidowId;

	@Column(name = "START_DATE")
	public Date startDate;

	@Column(name = "END_DATE")
	public Date endDate;

	@Column(name = "WIDOW_TYPE_ATR")
	public int widowTypeAtr;

	@Override
	protected Object getKey() {
		return olderWidowId;
	}

}
