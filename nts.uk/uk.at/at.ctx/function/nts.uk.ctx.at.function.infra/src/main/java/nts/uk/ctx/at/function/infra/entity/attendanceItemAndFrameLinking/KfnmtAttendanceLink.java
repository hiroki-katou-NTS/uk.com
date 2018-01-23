package nts.uk.ctx.at.function.infra.entity.attendanceItemAndFrameLinking;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_ATTENDANCE_LINK")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class KfnmtAttendanceLink extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KfnmtAttendanceLinkPK kfnmtAttendanceLinkPK;
	
	@Override
	protected Object getKey() {
		return kfnmtAttendanceLinkPK;
	}
}
