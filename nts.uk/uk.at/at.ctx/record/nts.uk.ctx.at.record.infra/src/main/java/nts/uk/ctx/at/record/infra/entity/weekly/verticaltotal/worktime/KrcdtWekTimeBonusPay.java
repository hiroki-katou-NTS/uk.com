package nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime;

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
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 集計加給時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_WEK_TIME_BONUS_PAY")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtWekTimeBonusPay extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtWekTimeBonusPayPK PK;
	
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
	/** 所定内加給時間 */
	@Column(name = "BNSPAY_WITHIN_TIME")
	public int withinTime;
	/** 所定内特定加給時間 */
	@Column(name = "BNSPAY_WITHIN_SPEC_TIME")
	public int withinSpecTime;
	/** 所定外加給時間 */
	@Column(name = "BNSPAY_EXCESS_TIME")
	public int excessTime;
	/** 休出特定加給時間 */
	@Column(name = "BNSPAY_EXCESS_SPEC_TIME")
	public int excessSpecTime;

	/** マッチング：週別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "WEEK_NO", referencedColumnName = "WEEK_NO", insertable = false, updatable = false)
	})
	public KrcdtWekAttendanceTime krcdtWekAttendanceTime;
	
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
				new AttendanceTimeMonth(this.holidayWorkSpecificBonusPayTime),
				new AttendanceTimeMonth(this.withinTime),
				new AttendanceTimeMonth(this.withinSpecTime),
				new AttendanceTimeMonth(this.excessTime),
				new AttendanceTimeMonth(this.excessSpecTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 集計加給時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfWeeklyKey key, AggregateBonusPayTime domain){
		
		this.PK = new KrcdtWekTimeBonusPayPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				key.getWeekNo(),
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
		this.withinTime = domain.getWithin().v();
		this.withinSpecTime = domain.getWithinSpecific().v();
		this.excessTime = domain.getExcess().v();
		this.excessSpecTime = domain.getExcessSpecific().v();
	}
}
