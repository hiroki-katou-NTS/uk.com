package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;
/**
 * @author phongtq
 * 休日から休日への0時跨ぎ設定
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_OVER_DAY_HD_SET ")
public class KshstOverDayHdSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstOverDayHdSetPK kshstOverDayHdSetPK;
	
	/** 変更後の法定内休出NO*/
	@Column(name = "WEEKDAY_NO")
	public int calcOverDayEnd;
	
	/** 変更後の法定外休出NO */
	@Column(name = "EXCESS_HOLIDAY_NO")
	public int statutoryHd;
	
	/** 変更後の祝日休出NO */
	@Column(name = "EXCESS_SPHD_NO")
	public int excessHd;
	
	@Override
	protected Object getKey() {
		return kshstOverDayHdSetPK;
	}
}
