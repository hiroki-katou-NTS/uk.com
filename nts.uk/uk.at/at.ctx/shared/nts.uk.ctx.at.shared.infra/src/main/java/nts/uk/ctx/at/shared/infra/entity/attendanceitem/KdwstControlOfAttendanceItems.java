package nts.uk.ctx.at.shared.infra.entity.attendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KDWST_ATD_ITEM_CONTROL")
public class KdwstControlOfAttendanceItems extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KdwstControlOfAttendanceItemsPK kdwstControlOfAttendanceItemsPK;
	@Column(name = "TIME_INPUT_UNIT")
	public BigDecimal inputUnitOfTimeItem;
	@DBCharPaddingAs(BonusPaySettingCode.class)
	@Column(name = "HEADER_BACKGROUND_COLOR")
	public String headerBackgroundColorOfDailyPerformance;

	@Override
	protected Object getKey() {
		return kdwstControlOfAttendanceItemsPK;
	}
	

}
