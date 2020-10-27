package nts.uk.ctx.at.record.infra.entity.daily.info;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotNull;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

public class KrcdtDayInfo extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KrcdtDayInfoPK krcdtDayInfoPK;
	
	// 勤務実績の勤務情報. 勤務種類コード
	@Column(name = "RECORD_WORK_WORKTYPE_CODE")
	public String recordWorkWorktypeCode;

	// 勤務実績の勤務情報. 就業時間帯コード
	@Column(name = "RECORD_WORK_WORKTIME_CODE")
	public String recordWorkWorktimeCode;

	// 勤務予定の勤務情報. 勤務種類コード
	@Column(name = "SCHEDULE_WORK_WORKTYPE_CODE")
	public String scheduleWorkWorktypeCode;

	// 勤務予定の勤務情報. 勤務種類コード
	@Column(name = "SCHEDULE_WORK_WORKTIME_CODE")
	public String scheduleWorkWorktimeCode;

	@Column(name = "CALCULATION_STATE")
	public Integer calculationState;

	@Column(name = "GO_STRAIGHT_ATR")
	public Integer goStraightAttribute;

	@Column(name = "BACK_STRAIGHT_ATR")
	public Integer backStraightAttribute;
	
	/*日別実績の所属情報*/
	@Column(name = "DAY_OF_WEEK")
	public Integer dayOfWeek;
	
	@Column(name = "EMP_CODE")
	public String employmentCode;
	
	@Column(name = "JOB_ID")
	public String jobtitleID;
	
	@Column(name = "CLS_CODE")
	public String classificationCode;
	
	@Column(name = "WKP_ID")
	public String workplaceID;
	
	@Column(name = "BONUS_PAY_CODE")
	public String bonusPayCode;
	/*日別実績の所属情報*/
	
	/*日別実績の勤務種別*/
	@Column(name = "WORKTYPE_CODE")
	public String workTypeCode;
	/*日別実績の勤務種別*/
	
	/*日別実績の計算区分*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "BONUS_PAY_NORMAL_CAL_SET")
    public int bonusPayNormalCalSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BONUS_PAY_SPE_CAL_SET")
    public int bonusPaySpeCalSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEAVE_LATE_SET")
    public int leaveLateSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEAVE_EARLY_SET")
    public int leaveEarlySet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIVERGENCE_TIME")
    public int divergenceTime;
    /*日別実績の計算区分*/
    
    /*フレックス超過時間*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_EXCESS_TIME_CAL_ATR")
    public int flexExcessTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_EXCESS_LIMIT_SET")
    public int flexExcessLimitSet;
    /*フレックス超過時間*/
    
	/*休出時間の自動計算設定*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_CAL_ATR")
    public int holWorkTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_LIMIT_SET")
    public int holWorkTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_CAL_ATR")
    public int lateNightTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_LIMIT_SET")
    public int lateNightTimeLimitSet;
    /*休出時間の自動計算設定*/
    
    /*残業時間の自動計算設定*/
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_OVER_TIME_CAL_ATR")
    public int earlyOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_OVER_TIME_LIMIT_SET")
    public int earlyOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_MID_OT_CAL_ATR")
    public int earlyMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_MID_OT_LIMIT_SET")
    public int earlyMidOtLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OVER_TIME_CAL_ATR")
    public int normalOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OVER_TIME_LIMIT_SET")
    public int normalOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_MID_OT_CAL_ATR")
    public int normalMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_MID_OT_LIMIT_SET")
    public int normalMidOtLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_OVER_TIME_CAL_ATR")
    public int legalOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_OVER_TIME_LIMIT_SET")
    public int legalOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_MID_OT_CAL_ATR")
    public int legalMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_MID_OT_LIMIT_SET")
    public int legalMidOtLimitSet;
    /*残業時間の自動計算設定*/
	
	@Override
	protected Object getKey() {
		return this.krcdtDayInfoPK;
	}
	
	
}
