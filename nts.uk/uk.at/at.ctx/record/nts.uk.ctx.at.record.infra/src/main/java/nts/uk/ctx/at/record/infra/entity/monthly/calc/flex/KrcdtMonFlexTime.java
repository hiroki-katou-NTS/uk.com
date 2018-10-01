package nts.uk.ctx.at.record.infra.entity.monthly.calc.flex;

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
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.ExcessFlexAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績のフレックス時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_FLEX_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonFlexTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** フレックス時間 */
	@Column(name = "FLEX_TIME")
	public int flexTime; 
	
	/** 計算フレックス時間 */
	@Column(name = "CALC_FLEX_TIME")
	public int calcFlexTime; 
	
	/** 事前フレックス時間 */
	@Column(name = "BEFORE_FLEX_TIME")
	public int beforeFlexTime; 
	
	/** 法定内フレックス時間 */
	@Column(name = "LEGAL_FLEX_TIME")
	public int legalFlexTime; 
	
	/** 法定外フレックス時間 */
	@Column(name = "ILLEGAL_FLEX_TIME")
	public int illegalFlexTime; 
	
	/** フレックス超過時間 */
	@Column(name = "FLEX_EXCESS_TIME")
	public int flexExcessTime; 
	
	/** フレックス不足時間 */
	@Column(name = "FLEX_SHORTAGE_TIME")
	public int flexShortageTime; 
	
	/** フレックス繰越時間 */
	@Column(name = "FLEX_CRYFWD_TIME")
	public int flexCarryforwardTime; 
	
	/** フレックス繰越勤務時間 */
	@Column(name = "FLEX_CRYFWD_WORK_TIME")
	public int flexCarryforwardWorkTime; 
	
	/** フレックス繰越不足時間 */
	@Column(name = "FLEX_CRYFWD_SHT_TIME")
	public int flexCarryforwardShortageTime; 
	
	/** 超過フレ区分 */
	@Column(name = "EXCESS_FLEX_ATR")
	public int excessFlexAtr; 
	
	/** 原則時間 */
	@Column(name = "PRINCIPLE_TIME")
	public int principleTime; 
	
	/** 便宜上時間 */
	@Column(name = "CONVENIENCE_TIME")
	public int forConvenienceTime; 

	/** 年休控除日数 */
	@Column(name = "ANNUAL_DEDUCT_DAYS")
	public double annualLeaveDeductDays; 
	
	/** 欠勤控除時間 */
	@Column(name = "ABSENCE_DEDUCT_TIME")
	public int absenceDeductTime; 
	
	/** 控除前のフレックス不足時間 */
	@Column(name = "SHORT_TIME_BFR_DEDUCT")
	public int shotTimeBeforeDeduct; 
	
	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績のフレックス時間
	 */
	public FlexTimeOfMonthly toDomain(){
		
		return FlexTimeOfMonthly.of(
				FlexTime.of(
						new TimeMonthWithCalculationAndMinus(
								new AttendanceTimeMonthWithMinus(this.flexTime),
								new AttendanceTimeMonthWithMinus(this.calcFlexTime)),
						new AttendanceTimeMonth(this.beforeFlexTime),
						new AttendanceTimeMonthWithMinus(this.legalFlexTime),
						new AttendanceTimeMonthWithMinus(this.illegalFlexTime)),
				new AttendanceTimeMonth(this.flexExcessTime),
				new AttendanceTimeMonth(this.flexShortageTime),
				FlexCarryforwardTime.of(
						new AttendanceTimeMonth(this.flexCarryforwardWorkTime),
						new AttendanceTimeMonth(this.flexCarryforwardTime),
						new AttendanceTimeMonth(this.flexCarryforwardShortageTime)),
				FlexTimeOfExcessOutsideTime.of(
						EnumAdaptor.valueOf(this.excessFlexAtr, ExcessFlexAtr.class),
						new AttendanceTimeMonth(this.principleTime),
						new AttendanceTimeMonth(this.forConvenienceTime)),
				FlexShortDeductTime.of(
						new AttendanceDaysMonth(this.annualLeaveDeductDays),
						new AttendanceTimeMonth(this.absenceDeductTime),
						new AttendanceTimeMonth(this.shotTimeBeforeDeduct)));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績のフレックス時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, FlexTimeOfMonthly domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績のフレックス時間
	 */
	public void fromDomainForUpdate(FlexTimeOfMonthly domain){
		
		val flexTime = domain.getFlexTime();
		val flexCarryForwardTime = domain.getFlexCarryforwardTime();
		val flexTimeOfExcessOutsideTime = domain.getFlexTimeOfExcessOutsideTime();
		val flexShortDeductTime = domain.getFlexShortDeductTime();
		
		this.flexTime = flexTime.getFlexTime().getTime().v();
		this.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		this.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		this.legalFlexTime = flexTime.getLegalFlexTime().v();
		this.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		this.flexExcessTime = domain.getFlexExcessTime().v();
		this.flexShortageTime = domain.getFlexShortageTime().v();
		this.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		this.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		this.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		this.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		this.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		this.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		this.annualLeaveDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays().v();
		this.absenceDeductTime = flexShortDeductTime.getAbsenceDeductTime().v();
		this.shotTimeBeforeDeduct = flexShortDeductTime.getFlexShortTimeBeforeDeduct().v();
	}
}
