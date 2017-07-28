package nts.uk.ctx.at.shared.infra.entity.attendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KSHST_ATD_ITEM_SET")
public class KshstDAIControlOfAttendanceItems extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstDAIControlOfAttendanceItemsPK kshstDAIControlOfAttendanceItemsPK;
	@Column(name = "USER_CAN_SET")
	public BigDecimal youCanChangeIt;
	@Column(name = "CHANGED_BY_OTHERS")
	public BigDecimal canBeChangedByOthers;
	@Column(name = "USE")
	public BigDecimal use;
	
	@Override
	protected Object getKey() {
		return kshstDAIControlOfAttendanceItemsPK;
	}

}
