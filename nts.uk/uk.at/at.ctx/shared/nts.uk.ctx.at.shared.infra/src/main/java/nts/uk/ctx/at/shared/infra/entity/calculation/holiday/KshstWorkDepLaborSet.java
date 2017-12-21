package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;
/**
 * @author phongtq
 * 変形労働勤務の加算設定
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WORK_DEF_LABOR_SET")
public class KshstWorkDepLaborSet  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstWorkDepLaborSetPK kshstWorkDepLaborSetPK;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERATION_1")
	public int calcActualOperation1;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "EXEMPT_TAX_TIME_1")
	public int exemptTaxTime1;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "INC_CHILD_NURSING_CARE_1")
	public int incChildNursingCare1;
	
	/** フレックスの所定超過時 */
	@Column(name = "PREDETERMINED_Overtime1")
	public int predeterminedOvertime1;
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_1")
	public int additionTime1;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_1")
	public int notDeductLateleave1;
	
	/** 通常、変形の所定超過時 */
	@Column(name = "DEFORMAT_EXC_VALUE_1")
	public int deformatExcValue;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "EXEMPT_TAX_TIME_2")
	public int exemptTaxTime2;
	
	/** 欠勤時間をマイナスする*/
	@Column(name = "MINUS_ABSENCE_TIME_2")
	public int minusAbsenceTime2;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERA_2")
	public int calcActualOperation2;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "INC_CHILD_NURSING_CARE_2")
	public int incChildNursingCare2;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_2")
	public int notDeductLateleave2;
	
	/**フレックスの所定不足時*/
	@Column(name = "PREDETERMIN_DEFICIENCY2")
	public int predeterminDeficiency2;	
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_2")
	public int additionTime2;
	
	@OneToOne(optional = false)
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false)
	public KshstHolidayAdditionSet holidayAddtimeSet;
	@Override
	protected Object getKey() {
		return kshstWorkDepLaborSetPK;
	}
}
