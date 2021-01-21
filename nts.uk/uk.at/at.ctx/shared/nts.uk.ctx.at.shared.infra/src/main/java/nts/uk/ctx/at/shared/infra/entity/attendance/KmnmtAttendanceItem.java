package nts.uk.ctx.at.shared.infra.entity.attendance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KMNMT_ATTENDANCE_ITEM")
public class KmnmtAttendanceItem extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KmnmtAttendanceItemPK kmnmtAttendanceItemPK;
	/* 名称 */
	@Column(name = "NAME")
	public String attendanceItemName;
	/* 表示番号 */
	@Column(name = "DISPLAY_NUMBER")
	public int displayNumber;
	/* 使用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;
	/* 属性 */
	@Column(name = "ATTENDANCE_ATR")
	public int attendanceAtr;
	@Column(name = "USER_CAN_SET")
	public int userCanSet;
	@Column(name = "LINE_BREAK_POSITION")
	public int nameLineFeedPosition;

	@Override
	protected Object getKey() {
		return kmnmtAttendanceItemPK;
	}

}
