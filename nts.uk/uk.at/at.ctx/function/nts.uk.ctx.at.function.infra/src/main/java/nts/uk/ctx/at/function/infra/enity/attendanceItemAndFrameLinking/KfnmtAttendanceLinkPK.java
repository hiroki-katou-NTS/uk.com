package nts.uk.ctx.at.function.infra.enity.attendanceItemAndFrameLinking;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAttendanceLinkPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*乖離時間ID*/
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
	
	/* 枠NO */
	@Column(name = "FRAME_NO")
	public BigDecimal frameNo;
	
}
