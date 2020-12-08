package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
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
public class KscstHoriCalDaysSetItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstHoriCalDaysSetPK kscstHoriCalDaysSetPK;
	/** 半日カウント区分 **/
	@Column(name = "HALF_DAY_ATR")
	public int halfDay;
	/** 年休カウント区分 **/
	@Column(name = "YEAR_HD_ATR")
	public int yearHd;
	/** 特休カウント区分 **/
	@Column(name = "SPHD_ATR")
	public int specialHoliday;
	/** 積休カウント区分 **/
	@Column(name = "HAVY_HD_ATR")
	public int heavyHd;
	// one cal day set to one eval order
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "CATEGORY_CD", referencedColumnName = "CATEGORY_CD", insertable = false, updatable = false),
		@JoinColumn(name = "TOTAL_ITEM_NO", referencedColumnName = "TOTAL_ITEM_NO", insertable = false, updatable = false)
	})
	public KscmtTotalEvalOrderItem kscmtTotalEvalOrderItem;
	
	@Override
	protected Object getKey() {
		return kscstHoriCalDaysSetPK;
	}
}
