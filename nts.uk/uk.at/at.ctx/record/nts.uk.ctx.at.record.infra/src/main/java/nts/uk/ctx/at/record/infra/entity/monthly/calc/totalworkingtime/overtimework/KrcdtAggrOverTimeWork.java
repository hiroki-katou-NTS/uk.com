package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework;

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
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_AGGR_OVER_TIME_WORK")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAggrOverTimeWork extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAggrOverTimeWorkPK PK;

	/** 残業時間 */
	@Column(name = "OVR_TM_WRK")
	public int overTimeWork;

	/** 残業計算時間 */
	@Column(name = "OVR_TM_WRK_CALC")
	public int overTimeWorkCalc;

	/** 事前残業時間 */
	@Column(name = "BFR_OVR_TM_WRK")
	public int beforeOverTimeWork;

	/** 振替残業時間 */
	@Column(name = "TRN_OVR_TM_WRK")
	public int transferOverTimeWork;

	/** 振替残業計算時間 */
	@Column(name = "TRN_OVR_TM_WRK_CALC")
	public int transferOverTimeWorkCalc;

	/** 法定内残業時間 */
	@Column(name = "WITHIN_OVR_TM_WK")
	public int withinStatutoryOverTimeWork;

	/** 法定内振替残業時間 */
	@Column(name = "WITHIN_TRN_OVR_TM_WK")
	public int withinStatutoryTransferOverTimeWork;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="SID", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
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
