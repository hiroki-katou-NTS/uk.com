package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AggregateLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AnyLeave;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の休業
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_LEAVE")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonLeave extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 産前休業日数 */
	@Column(name = "PRENATAL_LEAVE_DAYS")
	public double prenatalLeaveDays;
	
	/** 産後休業日数 */
	@Column(name = "POSTPARTUM_LEAVE_DAYS")
	public double postpartumLeaveDays;
	
	/** 育児休業日数 */
	@Column(name = "CHILDCARE_LEAVE_DAYS")
	public double childcareLeaveDays;
	
	/** 介護休業日数 */
	@Column(name = "CARE_LEAVE_DAYS")
	public double careLeaveDays;
	
	/** 傷病休業日数 */
	@Column(name = "INJILN_LEAVE_DAYS")
	public double injuryOrIllnessLeaveDays;
	
	/** 任意休業日数01 */
	@Column(name = "ANY_LEAVE_DAYS_01")
	public double anyLeaveDays01;
	
	/** 任意休業日数02 */
	@Column(name = "ANY_LEAVE_DAYS_02")
	public double anyLeaveDays02;
	
	/** 任意休業日数03 */
	@Column(name = "ANY_LEAVE_DAYS_03")
	public double anyLeaveDays03;
	
	/** 任意休業日数04 */
	@Column(name = "ANY_LEAVE_DAYS_04")
	public double anyLeaveDays04;

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
	 * @return 月別実績の休業
	 */
	public LeaveOfMonthly toDomain(){
		
		List<AggregateLeaveDays> fixLeaveDaysList = new ArrayList<>();
		List<AnyLeave> anyLeaveDaysList = new ArrayList<>();
		
		if (this.prenatalLeaveDays != 0.0){
			fixLeaveDaysList.add(AggregateLeaveDays.of(
					CloseAtr.PRENATAL, new AttendanceDaysMonth(this.prenatalLeaveDays)));
		}
		if (this.postpartumLeaveDays != 0.0){
			fixLeaveDaysList.add(AggregateLeaveDays.of(
					CloseAtr.POSTPARTUM, new AttendanceDaysMonth(this.postpartumLeaveDays)));
		}
		if (this.childcareLeaveDays != 0.0){
			fixLeaveDaysList.add(AggregateLeaveDays.of(
					CloseAtr.CHILD_CARE, new AttendanceDaysMonth(this.childcareLeaveDays)));
		}
		if (this.careLeaveDays != 0.0){
			fixLeaveDaysList.add(AggregateLeaveDays.of(
					CloseAtr.CARE, new AttendanceDaysMonth(this.careLeaveDays)));
		}
		if (this.injuryOrIllnessLeaveDays != 0.0){
			fixLeaveDaysList.add(AggregateLeaveDays.of(
					CloseAtr.INJURY_OR_ILLNESS, new AttendanceDaysMonth(this.injuryOrIllnessLeaveDays)));
		}
		if (this.anyLeaveDays01 != 0.0){
			anyLeaveDaysList.add(AnyLeave.of(1, new AttendanceDaysMonth(this.anyLeaveDays01)));
		}
		if (this.anyLeaveDays02 != 0.0){
			anyLeaveDaysList.add(AnyLeave.of(2, new AttendanceDaysMonth(this.anyLeaveDays02)));
		}
		if (this.anyLeaveDays03 != 0.0){
			anyLeaveDaysList.add(AnyLeave.of(3, new AttendanceDaysMonth(this.anyLeaveDays03)));
		}
		if (this.anyLeaveDays04 != 0.0){
			anyLeaveDaysList.add(AnyLeave.of(4, new AttendanceDaysMonth(this.anyLeaveDays04)));
		}
		
		val domain = LeaveOfMonthly.of(fixLeaveDaysList, anyLeaveDaysList);
		return domain;
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の休業
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, LeaveOfMonthly domain){
		
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
	 * @param domain 月別実績の休業
	 */
	public void fromDomainForUpdate(LeaveOfMonthly domain){
		
		this.prenatalLeaveDays = 0.0;
		this.postpartumLeaveDays = 0.0;
		this.childcareLeaveDays = 0.0;
		this.careLeaveDays = 0.0;
		this.injuryOrIllnessLeaveDays = 0.0;
		this.anyLeaveDays01 = 0.0;
		this.anyLeaveDays02 = 0.0;
		this.anyLeaveDays03 = 0.0;
		this.anyLeaveDays04 = 0.0;
		val fixLeaveDaysMap = domain.getFixLeaveDays();
		if (fixLeaveDaysMap.containsKey(CloseAtr.PRENATAL)){
			this.prenatalLeaveDays = fixLeaveDaysMap.get(CloseAtr.PRENATAL).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.POSTPARTUM)){
			this.postpartumLeaveDays = fixLeaveDaysMap.get(CloseAtr.POSTPARTUM).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CHILD_CARE)){
			this.childcareLeaveDays = fixLeaveDaysMap.get(CloseAtr.CHILD_CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CARE)){
			this.careLeaveDays = fixLeaveDaysMap.get(CloseAtr.CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.INJURY_OR_ILLNESS)){
			this.injuryOrIllnessLeaveDays = fixLeaveDaysMap.get(CloseAtr.INJURY_OR_ILLNESS).getDays().v();
		}
		val anyLeaveDaysMap = domain.getAnyLeaveDays();
		if (anyLeaveDaysMap.containsKey(1)){
			this.anyLeaveDays01 = anyLeaveDaysMap.get(1).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(2)){
			this.anyLeaveDays02 = anyLeaveDaysMap.get(2).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(3)){
			this.anyLeaveDays03 = anyLeaveDaysMap.get(3).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(4)){
			this.anyLeaveDays04 = anyLeaveDaysMap.get(4).getDays().v();
		}
	}
}
