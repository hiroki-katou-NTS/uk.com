package nts.uk.ctx.at.record.infra.entity.daily.actualworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_ATTENDANCE_TIME")
public class KrcdtDayAttendanceTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayAttendanceTimePK krcdtDayAttendanceTimePK;
	/*総労働時間*/
	@Column(name = "TOTAL_ATT_TIME")
	public int totalAttTime;
	/*総計算時間*/
	@Column(name = "TOTAL_CALC_TIME")
	public int totalCalcTime;
	/*実働時間*/
	@Column(name = "ACTWORK_TIME")
	public int actWorkTime;
	/*勤務回数*/
	@Column(name = "WORK_TIMES")
	public int workTimes;
	/*総拘束時間*/
	@Column(name = "TOTAL_BIND_TIME")
	public int totalBindTime;
	/*深夜拘束時間*/
	@Column(name = "MIDN_BIND_TIME")
	public int midnBindTime;
	/*拘束差異時間*/
	@Column(name = "BIND_DIFF_TIME")
	public int bindDiffTime;
	/*時差勤務時間*/
	@Column(name = "DIFF_TIME_WORK_TIME")
	public int diffTimeWorkTime;
	/*所定外深夜時間*/
	@Column(name = "OUT_PRS_MIDN_TIME")
	public int outPrsMidnTime;
	/*事前所定外深夜時間*/
	@Column(name = "PRE_OUT_PRS_MIDN_TIME")
	public int preOutPrsMidnTime;
	/*予実差異時間*/
	@Column(name = "BUDGET_TIME_VARIANCE")
	public int budgetTimeVariance;
	/*不就労時間*/
	@Column(name = "UNEMPLOYED_TIME")
	public int unemployedTime;
	/*滞在時間*/
	@Column(name = "STAYING_TIME")
	public int stayingTime;
	/*出勤前時間*/
	@Column(name = "BFR_WORK_TIME")
	public int bfrWorkTime;
	/*退勤後時間*/
	@Column(name = "AFT_LEAVE_TIME")
	public int aftLeaveTime;
	/*PCログオン前時間*/
	@Column(name = "BFR_PC_LOGON_TIME")
	public int bfrPcLogonTime;
	/*PCログオフ後時間*/
	@Column(name = "AFT_PC_LOGOFF_TIME")
	public int aftPcLogoffTime;
	
	
	@Override
	protected Object getKey() {
		return this.krcdtDayAttendanceTimePK;
	}
	
	
}
