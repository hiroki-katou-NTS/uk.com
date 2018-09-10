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
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.record.infra.entity.byperiod.KrcdtAnpAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計欠勤日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_ABSN_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrAbsnDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrAbsnDaysPK PK;
	
	/** 欠勤日数 */
	@Column(name = "ABSENCE_DAYS")
	public double absenceDays;
	/** 欠勤時間 */
	@Column(name = "ABSENCE_TIME")
	public int absenceTime;

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
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateAbsenceDays domain){
		
		this.PK = new KrcdtAnpAggrAbsnDaysPK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
