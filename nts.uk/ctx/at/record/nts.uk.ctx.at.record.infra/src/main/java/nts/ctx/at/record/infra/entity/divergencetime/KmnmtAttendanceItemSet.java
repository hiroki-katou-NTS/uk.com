package nts.ctx.at.record.infra.entity.divergencetime;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
@Entity
@Table(name="KMNMT_ATTENDANCE_ITEM_SET")
@AllArgsConstructor
@NoArgsConstructor
public class KmnmtAttendanceItemSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/*主キー*/
	@EmbeddedId
    public KmnmtAttendanceItemSetPK kmnmtAttendanceItemSetPK;
	
	@Override
	protected Object getKey() {
		return null;
	}

}
