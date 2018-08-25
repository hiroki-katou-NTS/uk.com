package nts.uk.ctx.at.record.infra.entity.byperiod;

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
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodKey;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計休出時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_HDWK_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrHdwkTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrHdwkTimePK PK;

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
	
	/** マッチング：任意期間別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "FRAME_CODE", referencedColumnName = "FRAME_CODE", insertable = false, updatable = false)
	})
	public KrcdtAnpAttendanceTime krcdtAnpAttendanceTime;
	
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
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 集計休出時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateHolidayWorkTime domain){
		
		this.PK = new KrcdtAnpAggrHdwkTimePK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
	}
}
