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
@Table(name = "KSHST_ZERO_TIME_SET")
public class KshstZeroTimeSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstZeroTimeSetPK kshstOverDayCalcSetPK;
	
	/** 0時跨ぎ計算を行なう*/
	@Column(name = "CALC_FROM_ZERO_TIME")
	public int calcOverDayEnd;
	
	/** 法定内休日 */
	@Column(name = "LEGAL_HD")
	public int statutoryHd;
	
	/** 法定外休日 */
	@Column(name = "NON_LEGAL_HD")
	public int excessHd;
	
	/** 法定外祝日*/
	@Column(name = "NON_LEGAL_PUBLIC_HD")
	public int excessSpecialHoliday;
	
	/** 平日 */
	@Column(name = "WEEKDAY_1")
	public int weekDayStatutoryHd;
	
	/** 法定外休日*/
	@Column(name = "NON_LEGAL_HD_1")
	public int excessStatutoryHd;
	
	/** 法定外祝日 */
	@Column(name = "NON_LEGAL_PUBLIC_HD_1")
	public int excessStatutorSphd;
	
	/** 平日*/
	@Column(name = "WEEKDAY_2")
	public int weekDayLegalHd;
	
	/** 法定外休日 */
	@Column(name = "LEGAL_HD_2")
	public int excessLegalHd;
	
	/** 法定外祝日 */
	@Column(name = "NON_LEGAL_HD_2")
	public int excessLegalSphd;
	
	/** 平日 */
	@Column(name = "WEEKDAY_3")
	public int weekDayPublicHd;
	
	/** 法定外休日 */
	@Column(name = "LEGAL_HD_3")
	public int excessPublicHd;
	
	/** 法定外休日 */
	@Column(name = "NON_LEGAL_PUBLIC_HD_3")
	public int excessPublicSphd;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public KshstWeekdayHd weekdayHd;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public KshstOverdayHdAttSet overdayHdAttSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="overDayCalcSet", orphanRemoval = true)
	public KshstOverDayHdSet overDayHdSet;
	
	@Override
	protected Object getKey() {
		return kshstOverDayCalcSetPK;
	}
}
