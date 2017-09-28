package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_HORI_CAL_DAYS_SET")
public class KscstHoriCalDaysSetItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstHoriCalDaysSetPK kscstHoriCalDaysSetPK;
	/** 半日カウント区分 **/
	@Column(name = "HALF_DAY_ATR")
	public Integer halfDay;
	/** 年休カウント区分 **/
	@Column(name = "YEAR_HD_ATR")
	public Integer yearHd;
	/** 特休カウント区分 **/
	@Column(name = "SPHD_ATR")
	public Integer specialHoliday;
	/** 積休カウント区分 **/
	@Column(name = "HAVY_HD_ATR")
	public Integer heavyHd;
	@Override
	protected Object getKey() {
		return kscstHoriCalDaysSetPK;
	}
}
