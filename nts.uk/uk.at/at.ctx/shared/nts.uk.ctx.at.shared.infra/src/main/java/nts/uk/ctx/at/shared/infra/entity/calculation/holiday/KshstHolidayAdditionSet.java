package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 休暇加算時間設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_HOLIDAY_ADDTION_SET")
public class KshstHolidayAdditionSet  extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstHolidayAdditionSetPK kshstHolidayAddtimeSetPK;
	
	/** 会社単位の休暇時間を参照する */
	@Column(name = "REFER_COM_HOLIDAY_TIME")
	public int referComHolidayTime;
	
	/** 1日 */
	@Column(name = "ONE_DAY")
	public BigDecimal oneDay;
	
	/** 午前 */
	@Column(name = "MORNING")  
	public BigDecimal morning;
	
	/** 午後 */
	@Column(name = "AFTERNOON")
	public BigDecimal afternoon;
	
	/** 実績の就業時間帯を参照する */
	@Column(name = "REFER_ACTUAL_WORK_HOURS")
	public int referActualWorkHours;
	
	/** 実績を参照しない場合の参照先*/
	@Column(name = "NOT_REFERRING_ACH")
	public int notReferringAch;
	
	/** 年休 */
	@Column(name = "ANNUAL_HOLIDAY")
	public int annualHoliday;
	
	/** 特別休暇 */
	@Column(name = "SPECIAL_HOLIDAY")
	public int specialHoliday;
	
	/** 積立年休 */
	@Column(name = "YEARLY_RESERVED")
	public int yearlyReserved;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="holidayAddtimeSet", orphanRemoval = true)
	public KshstWorkRegularSet regularWorkSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="holidayAddtimeSet", orphanRemoval = true)
	public KshstWorkFlexSet flexWorkSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="holidayAddtimeSet", orphanRemoval = true)
	public KshstWorkDepLaborSet irregularWorkSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="holidayAddtimeSet", orphanRemoval = true)
	public KshstHourPayAaddSet hourPayAaddSet;
	
	/** 加算方法 */
	@Column(name = "ADDING_METHOD1")
	public int addingMethod1;
	
	/* 勤務区分*/
	@Column(name = "WORK_CLASS1")
	public int workClass1;
	/** 加算方法 */
	
	@Column(name = "ADDING_METHOD2")
	public int addingMethod2;
	
	/* 勤務区分*/
	@Column(name = "WORK_CLASS2")
	public int workClass2;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="holidayAddtimeSet", orphanRemoval = true)
//	@OneToOne
//	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID")
	public KshstAddSetManWKHour addSetManWKHour;
	
	@Override
	protected Object getKey() {
		return kshstHolidayAddtimeSetPK;
	}
}
