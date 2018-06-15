package nts.uk.ctx.at.record.infra.entity.monthly.excessoutside;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の時間外超過
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_EXCESS_OUTSIDE")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonExcessOutside extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 週割増合計時間 */
	@Column(name = "TOTAL_WEEK_PRM_TIME")
	public int totalWeeklyPremiumTime;
	
	/** 月割増合計時間 */
	@Column(name = "TOTAL_MONTH_PRM_TIME")
	public int totalMonthlyPremiumTime;
	
	/** 変形繰越時間 */
	@Column(name = "DEFORM_CARRYFWD_TIME")
	public int deformationCarryforwardTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @param krcdtMonExcoutTime 時間外超過
	 * @return 月別実績の時間外超過
	 */
	public ExcessOutsideWorkOfMonthly toDomain(
			List<KrcdtMonExcoutTime> krcdtMonExcoutTime){

		if (krcdtMonExcoutTime == null) krcdtMonExcoutTime = new ArrayList<>();
		
		return ExcessOutsideWorkOfMonthly.of(
				new AttendanceTimeMonth(this.totalWeeklyPremiumTime),
				new AttendanceTimeMonth(this.totalMonthlyPremiumTime),
				new AttendanceTimeMonthWithMinus(this.deformationCarryforwardTime),
				krcdtMonExcoutTime.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の時間外超過
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, ExcessOutsideWorkOfMonthly domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の時間外超過
	 */
	public void fromDomainForUpdate(ExcessOutsideWorkOfMonthly domain){
		
		this.totalWeeklyPremiumTime = domain.getWeeklyTotalPremiumTime().v();
		this.totalMonthlyPremiumTime = domain.getMonthlyTotalPremiumTime().v();
		this.deformationCarryforwardTime = domain.getDeformationCarryforwardTime().v();
	}
}
