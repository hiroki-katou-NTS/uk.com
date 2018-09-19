package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays;

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
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計特定日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_SPEC_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrSpecDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrSpecDaysPK PK;
	
	/** 特定日数 */
	@Column(name = "SPECIFIC_DAYS")
	public double specificDays;
	
	/** 休出特定日数 */
	@Column(name = "HDWK_SPECIFIC_DAYS")
	public double holidayWorkSpecificDays;

	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
//	//テーブル結合用
//	public KrcdtMonTime krcdtMonTime;
	
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
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計特定日数
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateSpecificDays domain){
		
		this.PK = new KrcdtMonAggrSpecDaysPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
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
