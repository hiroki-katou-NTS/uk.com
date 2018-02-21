package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
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
		@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
}
