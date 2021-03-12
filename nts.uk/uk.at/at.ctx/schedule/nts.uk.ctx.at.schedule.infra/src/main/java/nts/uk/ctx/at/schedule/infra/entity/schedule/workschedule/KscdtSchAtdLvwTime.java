package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の出退勤時刻
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_ATD_LVW_TIME")
@Getter
public class KscdtSchAtdLvwTime extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchAtdLvwTimePK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 出勤時刻 **/
	@Column(name = "ATD_CLOCK")
	public int atdClock;
	
	/** 出勤時時間休暇 開始時刻 */
	@Column(name = "ATD_HOURLY_HD_TS_START")
	public Integer atdHourlyHDTSStart;
	
	/** 出勤時時間休暇 終了時刻 */
	@Column(name = "ATD_HOURLY_HD_TS_END")
	public Integer atdHourlyHDTSEnd;
	
	/** 退勤時刻**/
	@Column(name = "LVW_CLOCK")
	public int lwkClock;
	
	/** 退勤時時間休暇 開始時刻 */
	@Column(name = "LVW_HOURLY_HD_TS_START")
	public Integer lvwHourlyHDTSStart;
	
	/** 退勤時時間休暇 終了時刻 */
	@Column(name = "LVW_HOURLY_HD_TS_END")
	public Integer lvwHourlyHDTSEnd;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;

	// 勤務予定．出退勤．出退勤
	public static KscdtSchAtdLvwTime toEntity(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID) {
		KscdtSchAtdLvwTimePK pk = new KscdtSchAtdLvwTimePK(sID, yMD, leavingWork.getWorkNo().v());
		TimeWithDayAttr timeWithDayAtt = null;
		TimeWithDayAttr timeWithDayLea = null;
		
		if(leavingWork.getAttendanceStamp().isPresent()) {
			if(leavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				if(leavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					timeWithDayAtt = leavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		
		if(leavingWork.getLeaveStamp().isPresent()) {
			if(leavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				if(leavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
					timeWithDayLea = leavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				}
			}
		}
		Integer atdHourlyHDTSStart  = null;
		Integer atdHourlyHDTSEnd  = null;
		if(leavingWork.getAttendanceStamp().isPresent()) {
			if(leavingWork.getAttendanceStamp().get().getTimeVacation().isPresent()) {
				atdHourlyHDTSStart = leavingWork.getAttendanceStamp().get().getTimeVacation().get().start();
				atdHourlyHDTSEnd = leavingWork.getAttendanceStamp().get().getTimeVacation().get().end();
			}
		}
		Integer lvwHourlyHDTSStart  = null;
		Integer lvwHourlyHDTSEnd  = null;
		if(leavingWork.getLeaveStamp().isPresent()) {
			if(leavingWork.getLeaveStamp().get().getTimeVacation().isPresent()) {
				lvwHourlyHDTSStart = leavingWork.getLeaveStamp().get().getTimeVacation().get().start();
				lvwHourlyHDTSEnd = leavingWork.getLeaveStamp().get().getTimeVacation().get().end();
			}
		}
		
		return new KscdtSchAtdLvwTime(pk, cID, 
				timeWithDayAtt == null ? 0 : timeWithDayAtt.v(),
				atdHourlyHDTSStart,
				atdHourlyHDTSEnd,
				timeWithDayLea == null ? 0 :timeWithDayLea.v(),
				lvwHourlyHDTSStart,
				lvwHourlyHDTSEnd
				);
	}
	
	@Override
	protected Object getKey() {

		return this.pk;
	}


	public KscdtSchAtdLvwTime(KscdtSchAtdLvwTimePK pk, String cid, int atdClock, Integer atdHourlyHDTSStart,
			Integer atdHourlyHDTSEnd, int lwkClock, Integer lvwHourlyHDTSStart, Integer lvwHourlyHDTSEnd) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.atdClock = atdClock;
		this.atdHourlyHDTSStart = atdHourlyHDTSStart;
		this.atdHourlyHDTSEnd = atdHourlyHDTSEnd;
		this.lwkClock = lwkClock;
		this.lvwHourlyHDTSStart = lvwHourlyHDTSStart;
		this.lvwHourlyHDTSEnd = lvwHourlyHDTSEnd;
	}
	
}
