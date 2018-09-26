package nts.uk.ctx.at.record.infra.entity.byperiod.verticaltotal.workdays;

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
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.record.infra.entity.byperiod.KrcdtAnpAttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計特定日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_SPEC_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrSpecDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrSpecDaysPK PK;
	
	/** 特定日数 */
	@Column(name = "SPECIFIC_DAYS")
	public double specificDays;
	/** 休出特定日数 */
	@Column(name = "HDWK_SPECIFIC_DAYS")
	public double holidayWorkSpecificDays;

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
	 * @return 集計特定日数
	 */
	public AggregateSpecificDays toDomain(){
		
		return AggregateSpecificDays.of(
				new SpecificDateItemNo(this.PK.specificDayItemNo),
				new AttendanceDaysMonth(this.specificDays),
				new AttendanceDaysMonth(this.holidayWorkSpecificDays));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 集計特定日数
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateSpecificDays domain){
		
		this.PK = new KrcdtAnpAggrSpecDaysPK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
				domain.getSpecificDayItemNo().v());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計特定日数
	 */
	public void fromDomainForUpdate(AggregateSpecificDays domain){
		
		this.specificDays = domain.getSpecificDays().v();
		this.holidayWorkSpecificDays = domain.getHolidayWorkSpecificDays().v();
	}
}
