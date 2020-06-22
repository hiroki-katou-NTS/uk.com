package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *勤務予定の勤怠時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_TIME")
public class KscdtSchTime extends ContractUkJpaEntity {

	@EmbeddedId
	public KscdtSchTimePK pk;
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 勤務回数**/
	@Column(name = "COUNT")
	public int count;
	
	/** 総労働時間 **/									
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	
	/** 実働時間 **/
	@Column(name = "TOTAL_TIME_ACT")
	public int totalTimeAct;
	
	/** 所定内 就業時間 **/
	@Column(name = "PRS_WORK_TIME")
	public int prsWorkTime;
	
	/** 所定内 実働時間 **/
	@Column(name = "PRS_WORK_TIME_ACT")
	public int prsWorkTimeAct;
	
	/** 所定内 割増時間 **/
	@Column(name = "PRS_PRIME_TIME")
	public int prsPrimeTime;
	
	/** 所定内 深夜時間 **/
	@Column(name = "PRS_MIDNITE_TIME")
	public int prsMidniteTime;
	
	/** 所定外 残業拘束時間 **/
	@Column(name = "EXT_BIND_TIME_OTW")
	public int extBindTimeOtw;
										
	/** 所定外 休出拘束時間 **/
	@Column(name = "EXT_BIND_TIME_HDW")
	public int extBindTimeHw;
	
	/** 所定外 変形労働 法定内残業時間 **/
	@Column(name = "EXT_VARWK_OTW_TIME_LEGAL")
	public int extVarwkOtwTimeLegal;
	
	/** 所定外 フレックス時間 **/
	@Column(name = "EXT_FLEX_TIME")
	public int extFlexTime;
	
	/** 所定外 フレックス時間 事前申請時間 **/
	@Column(name = "EXT_FLEX_TIME_PREAPP")
	public int extFlexTimePreApp;	
	
	/** 所定外深夜 残業時間 **/
	@Column(name = "EXT_MIDNITE_OTW_TIME")
	public int extMidNiteOtwTime;	

	/** 所定外深夜 休出時間 法定内休日 **/
	@Column(name = "EXT_MIDNITE_HDW_TIME_LGHD")
	public int extMidNiteHdwTimeLghd;
	
	/** 所定外深夜 休出時間 法定外休日**/
	@Column(name = "EXT_MIDNITE_HDW_TIME_ILGHD")
	public int extMidNiteHdwTimeIlghd;
	
	/** 所定外深夜 休出時間 祝日**/
	@Column(name = "EXT_MIDNITE_HDW_TIME_PUBHD")
	public int extMidNiteHdwTimePubhd;
	
	/** 所定外深夜 合計時間**/
	@Column(name = "EXT_MIDNITE_TOTAL")
	public int extMidNiteTotal;
	
	/** 所定外深夜 合計時間 事前申請時間 **/
	@Column(name = "EXT_MIDNITE_TOTAL_PREAPP")
	public int extMidNiteTotalPreApp;
	
	/** インターバル出勤時刻 **/
	@Column(name = "INTERVAL_ATD_CLOCK")
	public int intervalAtdClock;	
	
	/** インターバル時間 **/
	@Column(name = "INTERVAL_TIME")
	public int intervalTime;	
	
	/** 休憩合計時間 **/
	@Column(name = "BRK_TOTAL_TIME")
	public int brkTotalTime;	
	
	/** 年休使用時間 **/
	@Column(name = "HDPAID_TIME")
	public int hdPaidTime;
	
	/** 年休時間消化休暇使用時間 **/
	@Column(name = "HDPAID_HOURLY_TIME")
	public int hdPaidHourlyTime;
	
	/** 代休使用時間 **/
	@Column(name = "HDCOM_TIME")
	public int hdComTime;
	
	/** 代休時間消化休暇使用時間 **/
	@Column(name = "HDCOM_HOURLY_TIME")
	public int hdComHourlyTime;
	
	/** 超過有休使用時間**/
	@Column(name = "HD60H_TIME")
	public int hd60hTime;
	
	/**超過有休時間消化休暇使用時間**/
	@Column(name = "HD60H_HOURLY_TIME")
	public int hd60hHourlyTime;
	
	/**特別休暇使用時間**/
	@Column(name = "HDSP_TIME")
	public int hdspTime;
	
	/**特別休暇時間消化休暇使用時間**/
	@Column(name = "HDSP_HOURLY_TIME")
	public int hdspHourlyTime;
								
	/**積立年休使用時間**/
	@Column(name = "HDSTK_TIME")
	public int hdstkTime;
	
	/** 時間消化休暇使用時間 **/
	@Column(name = "HD_HOURLY_TIME")
	public int hdHourlyTime;
	
	/** 時間消化休暇不足時間 **/
	@Column(name = "HD_HOURLY_SHORTAGE_TIME")
	public int hdHourlyShortageTime;
	
	/** 欠勤時間 **/
	@Column(name = "ABSENCE_TIME")
	public int absenceTime;
	
	/** 休暇加算時間 **/
	@Column(name = "VACATION_ADD_TIME")
	public int vacationAddTime;
	
	/** 時差勤務時間**/
	@Column(name = "STAGGERED_WH_TIME")
	public int staggeredWhTime;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
