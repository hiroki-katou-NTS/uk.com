package nts.uk.ctx.at.shared.infra.entity.attendanceitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KdwstControlOfAttendanceItemsPK  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "ATTENDANCE_ITEM_ID")
	public String attandanceTimeId;
}
