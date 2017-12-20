package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

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
@Table(name = "KSHST_WORK_FLEX_SET")
public class KshstWorkFlexSet  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstWorkFlexSetPK kshstFlexWorkSetPK;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERATION_1")
	public int calcActualOperationPre;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "EXEMPT_TAX_TIME_1")
	public int calcIntervalTimePre;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "INC_CHILD_NURSING_CARE_1")
	public int calcIncludCarePre;
	
	/** フレックスの所定超過時 */
	@Column(name = "PREDETERMINED_OVERTIME_1")
	public int predExcessTimeflexPre;
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_1")
	public int additionTimePre;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_1")
	public int notDeductLateleavePre;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "EXEMPT_TAX_TIME_2")
	public int calsIntervalTimeWork;
	
	/** 欠勤時間をマイナスする*/
	@Column(name = "MINUS_ABSENCE_TIME_2")
	public int minusAbsenceTimeWork;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERATION_2")
	public int calcActualOperaWork;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "INC_CHILD_NURSING_CARE_2")
	public int calcIncludCareWork;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_2")
	public int notDeductLateleaveWork;
	
	/** フレックスの所定不足時 */
	@Column(name = "PREDETERMIN_DEFICIENCY_2")
	public int preDef2;
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_2")
	public int additionTimeWork;
	
//	@OneToOne(optional = false)
//		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false)
//	public KshstHolidayAddtimeSet holidayAddtimeSet;
	
	@Override
	protected Object getKey() {
		return kshstFlexWorkSetPK;
	}
}
