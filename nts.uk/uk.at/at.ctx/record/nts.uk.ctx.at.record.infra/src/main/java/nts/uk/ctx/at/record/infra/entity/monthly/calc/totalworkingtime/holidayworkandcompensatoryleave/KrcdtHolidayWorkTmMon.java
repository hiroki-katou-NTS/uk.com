package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

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
 * 月別実績の休出時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_HOLIDAY_WORK_TM_MON")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtHolidayWorkTmMon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 休出合計時間 */
	@Column(name = "TTL_HLDY_WRK_TM")
	public int totalHolidayWorkTime;
	
	/** 休出合計計算時間 */
	@Column(name = "TTL_HLDY_WRK_TM_CALC")
	public int totalHolidayWorkTimeCalc;
	
	/** 事前休出時間 */
	@Column(name = "BFR_HLDY_WRK_TM")
	public int beforeHolidayWorkTime;
	
	/** 振替合計時間 */
	@Column(name = "TRANSFER_TTL_TM")
	public int transferTotalTime;
	
	/** 振替合計計算時間 */
	@Column(name = "TRANSFER_TTL_TM_CALC")
	public int transferTotalTimeCalc;

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
