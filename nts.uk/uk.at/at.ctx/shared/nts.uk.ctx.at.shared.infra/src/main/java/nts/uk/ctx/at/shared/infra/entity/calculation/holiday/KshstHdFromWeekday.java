package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

/**
 * @author phongtq
 * 休日から平日への0時跨ぎ設定
 */
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_HD_FROM_WEEKDAY ")
public class KshstHdFromWeekday extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstHdFromWeekdayPK kshstOverdayHdAttSetPK;

	/** 変更後の残業枠NO */
	@Column(name = "OVERTIME_FRAME_NO")
	public BigDecimal overtimeFrameNo;

	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KshstZeroTimeSet overDayCalcSet;

	@Override
	protected Object getKey() {
		return kshstOverdayHdAttSetPK;
	}
}
