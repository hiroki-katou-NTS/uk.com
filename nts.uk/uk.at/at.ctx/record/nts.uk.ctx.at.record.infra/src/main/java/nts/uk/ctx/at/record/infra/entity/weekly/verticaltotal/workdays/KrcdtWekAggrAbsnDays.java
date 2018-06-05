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
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計欠勤日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_WEK_AGGR_ABSN_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtWekAggrAbsnDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtWekAggrAbsnDaysPK PK;
	
	/** 欠勤日数 */
	@Column(name = "ABSENCE_DAYS")
	public double absenceDays;
	/** 欠勤時間 */
	@Column(name = "ABSENCE_TIME")
	public int absenceTime;

	/** マッチング：週別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "WEEK_NO", referencedColumnName = "KRCDT_WEK_ATTENDANCE_TIME.WEEK_NO", insertable = false, updatable = false)
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
	 * @return 集計欠勤日数
	 */
	public AggregateAbsenceDays toDomain(){
		
		return AggregateAbsenceDays.of(
				this.PK.absenceFrameNo,
				new AttendanceDaysMonth(this.absenceDays),
				new AttendanceTimeMonth(this.absenceTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 集計欠勤日数
	 */
	public void fromDomainForPersist(AttendanceTimeOfWeeklyKey key, AggregateAbsenceDays domain){
		
		this.PK = new KrcdtWekAggrAbsnDaysPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				key.getWeekNo(),
				domain.getAbsenceFrameNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計欠勤日数
	 */
	public void fromDomainForUpdate(AggregateAbsenceDays domain){
		
		this.absenceDays = domain.getDays().v();
		this.absenceTime = domain.getTime().v();
	}
}
