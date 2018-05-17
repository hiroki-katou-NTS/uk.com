package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の休出時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_HDWK_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonHdwkTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 休出合計時間 */
	@Column(name = "TOTAL_HDWK_TIME")
	public int totalHolidayWorkTime;
	
	/** 計算休出合計時間 */
	@Column(name = "CALC_TOTAL_HDWK_TIME")
	public int calcTotalHolidayWorkTime;
	
	/** 事前休出時間 */
	@Column(name = "BEFORE_HDWK_TIME")
	public int beforeHolidayWorkTime;
	
	/** 振替合計時間 */
	@Column(name = "TOTAL_TRN_TIME")
	public int totalTransferTime;
	
	/** 計算振替合計時間 */
	@Column(name = "CALC_TOTAL_TRN_TIME")
	public int calcTotalTransferTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
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
	 * @param krcdtMonAggrHdwkTimes 集計休出時間
	 * @return 月別実績の休出時間
	 */
	public HolidayWorkTimeOfMonthly toDomain(List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimes){
		
		if (krcdtMonAggrHdwkTimes == null) krcdtMonAggrHdwkTimes = new ArrayList<>();
		
		return HolidayWorkTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalHolidayWorkTime),
						new AttendanceTimeMonth(this.calcTotalHolidayWorkTime)),
				new AttendanceTimeMonth(this.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalTransferTime),
						new AttendanceTimeMonth(this.calcTotalTransferTime)),
				krcdtMonAggrHdwkTimes.stream()
					.map(c -> c.toDomain()).collect(Collectors.toList()));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の休出時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, HolidayWorkTimeOfMonthly domain){
		
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
	 * @param domain 月別実績の休出時間
	 */
	public void fromDomainForUpdate(HolidayWorkTimeOfMonthly domain){
		
		this.totalHolidayWorkTime = domain.getTotalHolidayWorkTime().getTime().v();
		this.calcTotalHolidayWorkTime = domain.getTotalHolidayWorkTime().getCalcTime().v();
		this.beforeHolidayWorkTime = domain.getBeforeHolidayWorkTime().v();
		this.totalTransferTime = domain.getTotalTransferTime().getTime().v();
		this.calcTotalTransferTime = domain.getTotalTransferTime().getCalcTime().v();
	}
}
