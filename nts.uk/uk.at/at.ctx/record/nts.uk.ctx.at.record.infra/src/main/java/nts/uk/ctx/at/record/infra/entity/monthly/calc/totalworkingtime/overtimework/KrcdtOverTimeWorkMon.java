package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework;

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
 * 月別実績の残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_OVER_TIME_WORK_MON")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtOverTimeWorkMon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 残業合計時間 */
	@Column(name = "TTL_OVER_TM_WRK")
	public int totalOverTimeWork;
	
	/** 残業合計計算時間 */
	@Column(name = "TTL_OVER_TM_WRK_CALC")
	public int totalOverTimeWorkCalc;
	
	/** 事前残業時間 */
	@Column(name = "BFR_OVER_TM_WRK")
	public int beforeOverTimeWork;
	
	/** 振替残業合計時間 */
	@Column(name = "TRN_TTL_OVR_TM_WRK")
	public int transferTotalOverTimeWork;
	
	/** 振替残業合計計算時間 */
	@Column(name = "TRN_TTL_OVR_TM_WRK_C")
	public int transferTotalOverTimeWorkCalc;

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
