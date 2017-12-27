package nts.uk.ctx.at.function.infra.entity.attendanceItemAndFrameLinking;

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

	/*乖離時間名称*/
	@Column(name = "TYPE_OF_ITEM")
	public BigDecimal typeOfItem;
	
	/*乖離時間使用設定*/
	@Column(name = "FRAME_CATEGORY")
	public BigDecimal frameCategory;
	
}
