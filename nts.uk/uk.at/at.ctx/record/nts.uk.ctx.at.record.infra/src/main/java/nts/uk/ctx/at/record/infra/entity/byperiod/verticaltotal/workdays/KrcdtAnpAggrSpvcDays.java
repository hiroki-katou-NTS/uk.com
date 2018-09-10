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
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateSpcVacationDays;
import nts.uk.ctx.at.record.infra.entity.byperiod.KrcdtAnpAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計特別休暇日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_AGGR_SPVC_DAYS")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrSpvcDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpAggrSpvcDaysPK PK;
	
	/** 特別休暇日数 */
	@Column(name = "SPCVACT_DAYS")
	public double specialVacationDays;
	/** 特別休暇時間 */
	@Column(name = "SPCVACT_TIME")
	public int specialVacationTime;

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
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, AggregateSpcVacationDays domain){
		
		this.PK = new KrcdtAnpAggrSpvcDaysPK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
