package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime;

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
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計加給時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_BNSPY_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrBnspyTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrBnspyTimePK PK;
	
	/** 加給時間 */
	@Column(name = "BONUS_PAY_TIME")
	public int bonusPayTime;
	
	/** 特定加給時間 */
	@Column(name = "SPEC_BONUS_PAY_TIME")
	public int specificBonusPayTime;
	
	/** 休出加給時間 */
	@Column(name = "HDWK_BONUS_PAY_TIME")
	public int holidayWorkBonusPayTime;
	
	/** 休出特定加給時間 */
	@Column(name = "HDWK_SPEC_BNSPAY_TIME")
	public int holidayWorkSpecificBonusPayTime;

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
	 * @return 集計加給時間
	 */
	public AggregateBonusPayTime toDomain(){
		
		return AggregateBonusPayTime.of(
				this.PK.bonusPayFrameNo,
				new AttendanceTimeMonth(this.bonusPayTime),
				new AttendanceTimeMonth(this.specificBonusPayTime),
				new AttendanceTimeMonth(this.holidayWorkBonusPayTime),
				new AttendanceTimeMonth(this.holidayWorkSpecificBonusPayTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計加給時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateBonusPayTime domain){
		
		this.PK = new KrcdtMonAggrBnspyTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getBonusPayFrameNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計加給時間
	 */
	public void fromDomainForUpdate(AggregateBonusPayTime domain){
		
		this.bonusPayTime = domain.getBonusPayTime().v();
		this.specificBonusPayTime = domain.getSpecificBonusPayTime().v();
		this.holidayWorkBonusPayTime = domain.getHolidayWorkBonusPayTime().v();
		this.holidayWorkSpecificBonusPayTime = domain.getHolidayWorkSpecificBonusPayTime().v();
	}
}
