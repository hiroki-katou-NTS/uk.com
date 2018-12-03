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
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
//import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の医療時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_MEDICAL_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonMedicalTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonMedicalTimePK PK;
	
	/** 勤務時間 */
	@Column(name = "WORK_TIME")
	public int workTime;
	
	/** 控除時間 */
	@Column(name = "DEDUCTION_TIME")
	public int deductionTime;
	
	/** 申送時間 */
	@Column(name = "TAKE_OVER_TIME")
	public int takeOverTime;

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
	 * @return 月別実績の医療時間
	 */
	public MedicalTimeOfMonthly toDomain(){
		
		return MedicalTimeOfMonthly.of(
				EnumAdaptor.valueOf(this.PK.dayNightAtr, WorkTimeNightShift.class),
				new AttendanceTimeMonth(this.workTime),
				new AttendanceTimeMonth(this.deductionTime),
				new AttendanceTimeMonth(this.takeOverTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の医療時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, MedicalTimeOfMonthly domain){
		
		this.PK = new KrcdtMonMedicalTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getDayNightAtr().value);
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の医療時間
	 */
	public void fromDomainForUpdate(MedicalTimeOfMonthly domain){
		
		this.workTime = domain.getWorkTime().v();
		this.deductionTime = domain.getDeducationTime().v();
		this.takeOverTime = domain.getTakeOverTime().v();
	}
}
