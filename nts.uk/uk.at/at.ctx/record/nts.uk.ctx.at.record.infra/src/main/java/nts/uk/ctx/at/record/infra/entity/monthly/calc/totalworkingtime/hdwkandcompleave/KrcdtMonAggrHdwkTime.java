package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計休出時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_HDWK_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrHdwkTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrHdwkTimePK PK;

	/** 休出時間 */
	@Column(name = "HDWK_TIME")
	public int holidayWorkTime;

	/** 計算休出時間 */
	@Column(name = "CALC_HDWK_TIME")
	public int calcHolidayWorkTime;

	/** 事前休出時間 */
	@Column(name = "BEFORE_HDWK_TIME")
	public int beforeHolidayWorkTime;

	/** 振替時間 */
	@Column(name = "TRN_TIME")
	public int transferTime;

	/** 計算振替時間 */
	@Column(name = "CALC_TRN_TIME")
	public int calcTransferTime;

	/** 法定内休出時間 */
	@Column(name = "LEGAL_HDWK_TIME")
	public int legalHolidayWorkTime;

	/** 法定内振替休出時間 */
	@Column(name = "LEGAL_TRN_HDWK_TIME")
	public int legalTransferHolidayWorkTime;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false)
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
	 * @return 集計休出時間
	 */
	public AggregateHolidayWorkTime toDomain(){
		
		return AggregateHolidayWorkTime.of(
				new HolidayWorkFrameNo(this.PK.holidayWorkFrameNo),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.holidayWorkTime),
						new AttendanceTimeMonth(this.calcHolidayWorkTime)),
				new AttendanceTimeMonth(this.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.transferTime),
						new AttendanceTimeMonth(this.calcTransferTime)),
				new AttendanceTimeMonth(this.legalHolidayWorkTime),
				new AttendanceTimeMonth(this.legalTransferHolidayWorkTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計休出時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateHolidayWorkTime domain){
		
		this.PK = new KrcdtMonAggrHdwkTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getHolidayWorkFrameNo().v());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計休出時間
	 */
	public void fromDomainForUpdate(AggregateHolidayWorkTime domain){
		
		this.holidayWorkTime = domain.getHolidayWorkTime().getTime().v();
		this.calcHolidayWorkTime = domain.getHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime = domain.getBeforeHolidayWorkTime().v();
		this.transferTime = domain.getTransferTime().getTime().v();
		this.calcTransferTime = domain.getTransferTime().getCalcTime().v();
		this.legalHolidayWorkTime = domain.getLegalHolidayWorkTime().v();
		this.legalTransferHolidayWorkTime = domain.getLegalTransferHolidayWorkTime().v();
	}
}
