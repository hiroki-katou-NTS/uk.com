package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * @author phongtq
 * 月別実績の項目丸め設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_ROUNDING_MONTH_ITEM")
public class KshstRoundingMonthItem  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 主キー */
	@EmbeddedId
	public KshstRoundingMonthItemPK kshstRoundingMonthSetPK;
	
	/** 丸め */
	@Column(name = "UNIT")
	public int unit;
	
	/** 丸め */
	@Column(name = "ROUNDING")
	public int rounding;
	
	@Override
	protected Object getKey() {
		return kshstRoundingMonthSetPK;
	}
}
