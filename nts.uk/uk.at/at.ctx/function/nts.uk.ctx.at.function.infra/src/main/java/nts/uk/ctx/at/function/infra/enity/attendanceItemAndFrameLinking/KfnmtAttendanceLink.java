package nts.uk.ctx.at.function.infra.enity.attendanceItemAndFrameLinking;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
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

	/*乖離時間名称*/
	@Column(name = "TYPE_OF_ITEM")
	public BigDecimal typeOfItem;
	
	/*乖離時間使用設定*/
	@Column(name = "FRAME_CATEGORY")
	public BigDecimal frameCategory;
	
	@Override
	protected Object getKey() {
		return kfnmtAttendanceLinkPK;
	}
}
