package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_REGULAR_WORK_SET")
public class KshstRegularWorkSet  extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstRegularWorkSetPK kshstRegularWorkSetPK;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERATION_PRE")
	public int calcActualOperationPre;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "CALC_INTERVAL_TIME_PRE")
	public int calcIntervalTimePre;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "CALC_INCLUD_CARE_PRE")
	public int calcIncludCarePre;
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_PRE")
	public int additionTimePre;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_PRE")
	public int notDeductLateleavePre;
	
	/** 通常、変形の所定超過時 */
	@Column(name = "DEFORMAT_EXC_VALUE_PRE")
	public int deformatExcValuePre;
	
	/** インターバル免除時間を含めて計算する */
	@Column(name = "CALC_INTERVAL_TIME_WORK")
	public int calsIntervalTimeWork;
	
	/** 欠勤時間をマイナスする*/
	@Column(name = "MINUS_ABSENCE_TIME_WORK")
	public int minusAbsenceTimeWork;
	
	/** 実働のみで計算する */
	@Column(name = "CALC_ACTUAL_OPERA_WORK")
	public int calcActualOperaWork;
	
	/** 育児・介護時間を含めて計算する */
	@Column(name = "CALC_INCLUD_CARE_WORK")
	public int calcIncludCareWork;
	
	/** 遅刻・早退を控除しない */
	@Column(name = "NOT_DEDUCT_LATELEAVE_WORK")
	public int notDeductLateleaveWork;
	
	/** 加算する */
	@Column(name = "ADDITION_TIME_WORK")
	public int additionTimeWork;
	
//	@OneToOne(optional = false)
//	@JoinColumns({
//		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false)
//	})
//	public KshstHolidayAddtimeSet holidayAddtimeSet;
	
	@Override
	protected Object getKey() {
		return kshstRegularWorkSetPK;
	}
}
