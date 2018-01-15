package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

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
@Table(name = "KSHST_ATD_ITEM_CONTROL")
public class KshstControlOfAttendanceItems extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstControlOfAttendanceItemsPK kshstControlOfAttendanceItemsPK;
	@Column(name = "TIME_INPUT_UNIT")
	public BigDecimal inputUnitOfTimeItem;
	@DBCharPaddingAs(BonusPaySettingCode.class)
	@Column(name = "HEADER_BACKGROUND_COLOR")
	public String headerBackgroundColorOfDailyPerformance;
	@Column(name = "LINE_BREAK_POSITION")
	public BigDecimal nameLineFeedPosition;

	@Override
	protected Object getKey() {
		return kshstControlOfAttendanceItemsPK;
	}
	

}
