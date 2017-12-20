package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_OVER_DAY_CALC_SET")
public class KshstOverDayCalcSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstOverDayCalcSetPK kshstOverDayCalcSetPK;
	
	/** 0時跨ぎ計算を行なう*/
	@Column(name = "CALC_OVER_DAY_END")
	public int calcOverDayEnd;
	
	/** 法定内休日 */
	@Column(name = "STATUTORY_HD")
	public int statutoryHd;
	
	/** 法定外休日 */
	@Column(name = "EXCESS_HD")
	public int excessHd;
	
	/** 法定外祝日*/
	@Column(name = "EXCESS_SPECIAL_HOLIDAY")
	public int excessSpecialHoliday;
	
	/** 平日 */
	@Column(name = "WEEK_DAY_STATUTORY_HD")
	public int weekDayStatutoryHd;
	
	/** 法定外休日*/
	@Column(name = "EXCESS_STATUTORY_HD")
	public int excessStatutoryHd;
	
	/** 法定外祝日 */
	@Column(name = "EXCESS_STATUTOR_SPHD")
	public int excessStatutorSphd;
	
	/** 平日*/
	@Column(name = "WEEK_DAY_LEGAL_HD")
	public int weekDayLegalHd;
	
	/** 法定外休日 */
	@Column(name = "EXCESS_LEGAL_HD")
	public int excessLegalHd;
	
	/** 法定外祝日 */
	@Column(name = "EXCESS_LEGAL_SPHD")
	public int excessLegalSphd;
	
	/** 平日 */
	@Column(name = "WEEK_DAY_PUBLIC_HD")
	public int weekDayPublicHd;
	
	/** 法定外休日 */
	@Column(name = "EXCESS_PUBLIC_HD")
	public int excessPublicHd;
	
	/** 法定外休日 */
	@Column(name = "EXCESS_PUBLIC_SPHD")
	public int excessPublicSphd;
	
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
//	public KshstWeekdayHd weekdayHd;
//	
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
//	public KshstOverdayHdAttSet overdayHdAttSet;
//	
//	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
//	public KshstOverDayHdSet overDayHdSet;
	
	@Override
	protected Object getKey() {
		return kshstOverDayCalcSetPK;
	}
}
