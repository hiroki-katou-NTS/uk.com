package nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime;

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
 * 月別実績の変形労働時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_IRREG_WRK_TIME_MON")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtIrregWrkTimeMon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 複数月変形途中時間 */
	@Column(name = "MULTI_MON_MIDDLE_TM")
	public int multiMonthIrregularMiddleTime;
	
	/** 変形期間繰越時間 */
	@Column(name = "PERIOD_CARRYFWD_TM")
	public int irregularPeriodCarryforwardTime;
	
	/** 変形労働不足時間 */
	@Column(name = "SHORTAGE_TIME")
	public int irregularWorkingShortageTime;
	
	/** 変形法定内残業時間 */
	@Column(name = "WITHIN_OVER_TIME")
	public int irregularWithinStatutoryOverTimeWork;
	
	/** 変形法定内残業計算時間 */
	@Column(name = "WITHIN_OVER_TIME_CAL")
	public int irregularWithinStatutoryOverTimeWorkCalc;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "START_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.START_YMD", insertable = false, updatable = false),
		@JoinColumn(name = "END_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.END_YMD", insertable = false, updatable = false)
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
