package nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.workdays;

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
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateSpcVacationDays;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計特別休暇日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_WEK_AGGR_SPVC_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtWekAggrSpvcDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtWekAggrSpvcDaysPK PK;
	
	/** 特別休暇日数 */
	@Column(name = "SPCVACT_DAYS")
	public double specialVacationDays;
	/** 特別休暇時間 */
	@Column(name = "SPCVACT_TIME")
	public int specialVacationTime;

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
	 * @return 集計特別休暇日数
	 */
	public AggregateSpcVacationDays toDomain(){
		
		return AggregateSpcVacationDays.of(
				this.PK.specialVacationFrameNo,
				new AttendanceDaysMonth(this.specialVacationDays),
				new AttendanceTimeMonth(this.specialVacationTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 集計特別休暇日数
	 */
	public void fromDomainForPersist(AttendanceTimeOfWeeklyKey key, AggregateSpcVacationDays domain){
		
		this.PK = new KrcdtWekAggrSpvcDaysPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				key.getWeekNo(),
				domain.getSpcVacationFrameNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計特別休暇日数
	 */
	public void fromDomainForUpdate(AggregateSpcVacationDays domain){
		
		this.specialVacationDays = domain.getDays().v();
		this.specialVacationTime = domain.getTime().v();
	}
}
