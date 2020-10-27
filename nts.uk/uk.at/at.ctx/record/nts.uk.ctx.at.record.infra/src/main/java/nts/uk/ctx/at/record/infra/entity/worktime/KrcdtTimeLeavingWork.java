package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 出退勤
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TIME_LEAVING_WORK")
public class KrcdtTimeLeavingWork extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTimeLeavingWorkPK krcdtTimeLeavingWorkPK;

	@Column(name = "ATD_ACTUAL_ROUDING_TIME_DAY")
	public Integer attendanceActualRoudingTime;

	@Column(name = "ATD_ACTUAL_TIME")
	public Integer attendanceActualTime;

	@Column(name = "ATD_ACTUAL_PLACE_CODE")
	public String attendanceActualPlaceCode;

	@Column(name = "ATD_ACTUAL_SOURCE_INFO")
	public Integer attendanceActualSourceInfo;

	@Column(name = "ATD_STAMP_ROUDING_TIME_DAY")
	public Integer attendanceStampRoudingTime;

	@Column(name = "ATD_STAMP_TIME")
	public Integer attendanceStampTime;

	@Column(name = "ATD_STAMP_PLACE_CODE")
	public String attendanceStampPlaceCode;

	@Column(name = "ATD_STAMP_SOURCE_INFO")
	public Integer attendanceStampSourceInfo;

	@Column(name = "ATD_NUMBER_STAMP")
	public Integer attendanceNumberStamp;

	@Column(name = "ATD_OVERTIME")
	public Integer atdOvertime;
	
	@Column(name = "ATD_LATE_NIGHT_OVERTIME")
	public Integer atdLateNightOvertime;
	
	@Column(name = "ATD_BREAK_START")
	public Integer atdBreakStart;
	
	@Column(name = "ATD_BREAK_END")
	public Integer atdBreakEnd;
	
	@Column(name = "LWK_ACTUAL_ROUDING_TIME_DAY")
	public Integer leaveWorkActualRoundingTime;

	@Column(name = "LWK_ACTUAL_TIME")
	public Integer leaveWorkActualTime;

	@Column(name = "LWK_ACTUAL_PLACE_CODE")
	public String leaveWorkActualPlaceCode;

	@Column(name = "LWK_ACTUAL_SOURCE_INFO")
	public Integer leaveActualSourceInfo;

	@Column(name = "LWK_STAMP_ROUDING_TIME_DAY")
	public Integer leaveWorkStampRoundingTime;

	@Column(name = "LWK_STAMP_TIME")
	public Integer leaveWorkStampTime;

	@Column(name = "LWK_STAMP_PLACE_CODE")
	public String leaveWorkStampPlaceCode;

	@Column(name = "LWK_STAMP_SOURCE_INFO")
	public Integer leaveWorkStampSourceInfo;

	@Column(name = "LWK_NUMBER_STAMP")
	public Integer leaveWorkNumberStamp;
	
	@Column(name = "LWK_OVERTIME")
	public Integer lwkOvertime;
	
	@Column(name = "LWK_LATE_NIGHT_OVERTIME")
	public Integer lwkLateNightOvertime;
	
	@Column(name = "LWK_BREAK_START")
	public Integer lwkBreakStart;
	
	@Column(name = "LWK_BREAK_END")
	public Integer lwkBreakEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiLeavingWork daiLeavingWork;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiTemporaryTime daiTemporaryTime;

	public TimeLeavingWork toDomain() {
		TimeLeavingWork domain = new TimeLeavingWork(new WorkNo(this.krcdtTimeLeavingWorkPK.workNo),
				new TimeActualStamp(
						this.attendanceActualTime != null ? getWorkStamp(this.attendanceActualRoudingTime, this.attendanceActualTime, 
								this.attendanceActualPlaceCode, this.attendanceActualSourceInfo) : null,
						this.attendanceStampTime != null ? getWorkStamp(this.attendanceStampRoudingTime, this.attendanceStampTime,
								this.attendanceStampPlaceCode, this.attendanceStampSourceInfo) : null,
						this.attendanceNumberStamp,
						(this.atdOvertime==null || this.atdLateNightOvertime==null)?null:
						new OvertimeDeclaration(new AttendanceTime(this.atdOvertime),
								new AttendanceTime(this.atdLateNightOvertime)) ,
						(this.atdBreakStart==null || this.atdBreakEnd==null)?null:
						new TimeZone(new TimeWithDayAttr(this.atdBreakStart),
								new TimeWithDayAttr(this.atdBreakEnd))
						),
				new TimeActualStamp(
						this.leaveWorkActualTime !=null ? getWorkStamp(this.leaveWorkActualRoundingTime, this.leaveWorkActualTime,
								this.leaveWorkActualPlaceCode, this.leaveActualSourceInfo) : null,
						this.leaveWorkStampTime != null ? getWorkStamp(this.leaveWorkStampRoundingTime, this.leaveWorkStampTime,
								this.leaveWorkStampPlaceCode, this.leaveWorkStampSourceInfo) : null,
						this.leaveWorkNumberStamp,
						(this.lwkOvertime==null || this.lwkLateNightOvertime==null)?null:
						new OvertimeDeclaration(new AttendanceTime(this.lwkOvertime),
								new AttendanceTime(this.lwkLateNightOvertime)),
						(this.lwkBreakStart==null || this.lwkBreakEnd==null)?null:
						new TimeZone(new TimeWithDayAttr(this.lwkBreakStart),
								new TimeWithDayAttr(this.lwkBreakEnd))
						));
		return domain;
	}

	private WorkStamp getWorkStamp(Integer roudingTime, Integer time, String placeCode, Integer sourceInfo) {
		return new WorkStamp(
				roudingTime == null ? null : new TimeWithDayAttr(roudingTime),
				time == null ? null : new TimeWithDayAttr(time),
				placeCode == null ? null : new WorkLocationCD(placeCode),
				sourceInfo == null ? null : EnumAdaptor.valueOf(sourceInfo, TimeChangeMeans.class));
	}

	public static KrcdtTimeLeavingWork toEntity(String employeeId, GeneralDate ymd, TimeLeavingWork domain, int type) {
		TimeActualStamp attendanceStamp = (domain.getAttendanceStamp() != null && domain.getAttendanceStamp().isPresent()) ? domain.getAttendanceStamp().get() : null;
		TimeActualStamp leaveStamp = (domain.getLeaveStamp() != null && domain.getLeaveStamp().isPresent()) ? domain.getLeaveStamp().get() : null;
		KrcdtTimeLeavingWork krcdtTimeLeavingWork = new KrcdtTimeLeavingWork();
		KrcdtTimeLeavingWorkPK krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK(employeeId, domain.getWorkNo().v(), ymd, type);
		krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK = krcdtTimeLeavingWorkPK;
		toEntityAttendance(krcdtTimeLeavingWork, attendanceStamp);
		toEntityLeave(krcdtTimeLeavingWork, leaveStamp);
		return krcdtTimeLeavingWork;
	}
	
	private static void toEntityAttendance(KrcdtTimeLeavingWork krcdtTimeLeavingWork, TimeActualStamp attendanceStamp){
		if (attendanceStamp != null) {
			if(attendanceStamp.getActualStamp() != null && attendanceStamp.getActualStamp().isPresent()){
				val actualStamp = attendanceStamp.getActualStamp().get();
				krcdtTimeLeavingWork.attendanceActualPlaceCode = (actualStamp.getLocationCode() != null && actualStamp.getLocationCode().isPresent()) ? actualStamp.getLocationCode().get().v() : null;
				krcdtTimeLeavingWork.attendanceActualRoudingTime = actualStamp.getAfterRoundingTime() != null ? actualStamp.getAfterRoundingTime().valueAsMinutes() : null;
				krcdtTimeLeavingWork.attendanceActualSourceInfo = actualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() != null ? actualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value : null;
				krcdtTimeLeavingWork.attendanceActualTime = actualStamp.getTimeDay().getTimeWithDay().isPresent()? actualStamp.getTimeDay().getTimeWithDay().get().v() : null;
			} else {
				krcdtTimeLeavingWork.attendanceActualPlaceCode = null;
				krcdtTimeLeavingWork.attendanceActualRoudingTime = null;
				krcdtTimeLeavingWork.attendanceActualTime = null;
			}
			if(attendanceStamp.getOvertimeDeclaration().isPresent()) {
				krcdtTimeLeavingWork.atdOvertime = attendanceStamp.getOvertimeDeclaration().get().getOverTime().v();
				krcdtTimeLeavingWork.atdLateNightOvertime = attendanceStamp.getOvertimeDeclaration().get().getOverLateNightTime().v();
			}else {
				krcdtTimeLeavingWork.atdOvertime = null;
				krcdtTimeLeavingWork.atdLateNightOvertime = null;
			}
			
			if(attendanceStamp.getTimeVacation().isPresent()) {
				krcdtTimeLeavingWork.atdBreakStart = attendanceStamp.getTimeVacation().get().getStart().v();
				krcdtTimeLeavingWork.atdBreakEnd = attendanceStamp.getTimeVacation().get().getEnd().v();
			}else {
				krcdtTimeLeavingWork.atdBreakStart = null;
				krcdtTimeLeavingWork.atdBreakEnd = null;
			}
			
			if (attendanceStamp.getStamp() != null && attendanceStamp.getStamp().isPresent()) {
				val stamp = attendanceStamp.getStamp().get();
				krcdtTimeLeavingWork.attendanceStampPlaceCode = (stamp.getLocationCode() != null && stamp.getLocationCode().isPresent()) ? stamp.getLocationCode().get().v() : null;
				krcdtTimeLeavingWork.attendanceStampRoudingTime = stamp.getAfterRoundingTime() != null ? stamp.getAfterRoundingTime().valueAsMinutes() : null;
				krcdtTimeLeavingWork.attendanceStampSourceInfo = stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() != null ? stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value : null;
				krcdtTimeLeavingWork.attendanceStampTime = stamp.getTimeDay().getTimeWithDay().isPresent()? stamp.getTimeDay().getTimeWithDay().get().v() : null;
			} else {
				krcdtTimeLeavingWork.attendanceStampPlaceCode = null;
				krcdtTimeLeavingWork.attendanceStampRoudingTime = null;
				krcdtTimeLeavingWork.attendanceStampTime = null;
			}
			krcdtTimeLeavingWork.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
			
		} else {
			krcdtTimeLeavingWork.attendanceActualPlaceCode = null;
			krcdtTimeLeavingWork.attendanceActualRoudingTime = null;
			krcdtTimeLeavingWork.attendanceActualTime = null;
			krcdtTimeLeavingWork.attendanceStampPlaceCode = null;
			krcdtTimeLeavingWork.attendanceStampRoudingTime = null;
			krcdtTimeLeavingWork.attendanceStampTime = null;
		}
		
	}
	
	private static void toEntityLeave(KrcdtTimeLeavingWork krcdtTimeLeavingWork, TimeActualStamp leaveStamp){
		if (leaveStamp != null) {
			if (leaveStamp.getActualStamp() != null && leaveStamp.getActualStamp().isPresent()) {
				WorkStamp actualStamp = leaveStamp.getActualStamp().orElse(null);
				krcdtTimeLeavingWork.leaveWorkActualPlaceCode = (actualStamp.getLocationCode() != null && actualStamp.getLocationCode().isPresent()) ? actualStamp.getLocationCode().get().v() : null;
				krcdtTimeLeavingWork.leaveWorkActualRoundingTime = actualStamp.getAfterRoundingTime() != null ? actualStamp.getAfterRoundingTime().valueAsMinutes() : null;
				krcdtTimeLeavingWork.leaveActualSourceInfo = actualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() != null ? actualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value : null;
				krcdtTimeLeavingWork.leaveWorkActualTime = actualStamp.getTimeDay().getTimeWithDay().isPresent()? actualStamp.getTimeDay().getTimeWithDay().get().v() : null;
			} else {
				krcdtTimeLeavingWork.leaveWorkActualPlaceCode = null;
				krcdtTimeLeavingWork.leaveWorkActualRoundingTime = null;
				krcdtTimeLeavingWork.leaveWorkActualTime = null;
			}
			if(leaveStamp.getOvertimeDeclaration().isPresent()) {
				krcdtTimeLeavingWork.lwkOvertime = leaveStamp.getOvertimeDeclaration().get().getOverTime().v();
				krcdtTimeLeavingWork.lwkLateNightOvertime = leaveStamp.getOvertimeDeclaration().get().getOverLateNightTime().v();
			}else {
				krcdtTimeLeavingWork.lwkOvertime = null;
				krcdtTimeLeavingWork.lwkLateNightOvertime = null;
			}
			
			if(leaveStamp.getTimeVacation().isPresent()) {
				krcdtTimeLeavingWork.lwkBreakStart = leaveStamp.getTimeVacation().get().getStart().v();
				krcdtTimeLeavingWork.lwkBreakEnd = leaveStamp.getTimeVacation().get().getEnd().v();
			}else {
				krcdtTimeLeavingWork.lwkBreakStart = null;
				krcdtTimeLeavingWork.lwkBreakEnd = null;
			}
			if (leaveStamp.getStamp() != null && leaveStamp.getStamp().isPresent()) {
				WorkStamp stamp = leaveStamp.getStamp().orElse(null);
				krcdtTimeLeavingWork.leaveWorkStampPlaceCode = (stamp.getLocationCode() != null && stamp.getLocationCode().isPresent()) ? stamp.getLocationCode().get().v() : null;
				krcdtTimeLeavingWork.leaveWorkStampRoundingTime = stamp.getAfterRoundingTime() != null ? stamp.getAfterRoundingTime().valueAsMinutes() : null;
				krcdtTimeLeavingWork.leaveWorkStampSourceInfo = stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() != null ? stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value : null;
				krcdtTimeLeavingWork.leaveWorkStampTime = stamp.getTimeDay().getTimeWithDay().isPresent() ? stamp.getTimeDay().getTimeWithDay().get().v() : null;
			} else {
				krcdtTimeLeavingWork.leaveWorkStampPlaceCode = null;
				krcdtTimeLeavingWork.leaveWorkStampRoundingTime = null;
				krcdtTimeLeavingWork.leaveWorkStampTime = null;
			}
			krcdtTimeLeavingWork.leaveWorkNumberStamp = leaveStamp.getNumberOfReflectionStamp();
		} else {
			krcdtTimeLeavingWork.leaveWorkActualPlaceCode = null;
			krcdtTimeLeavingWork.leaveWorkActualRoundingTime = null;
			krcdtTimeLeavingWork.leaveWorkActualTime = null;
			krcdtTimeLeavingWork.leaveWorkStampPlaceCode = null;
			krcdtTimeLeavingWork.leaveWorkStampRoundingTime = null;
			krcdtTimeLeavingWork.leaveWorkStampTime = null;
		}
	}

	public KrcdtTimeLeavingWork(KrcdtTimeLeavingWorkPK krcdtTimeLeavingWorkPK, Integer attendanceActualRoudingTime,
			Integer attendanceActualTime, String attendanceActualPlaceCode, Integer attendanceActualSourceInfo,
			Integer attendanceStampRoudingTime, Integer attendanceStampTime, String attendanceStampPlaceCode,
			Integer attendanceStampSourceInfo, Integer attendanceNumberStamp, Integer leaveWorkActualRoundingTime,
			Integer leaveWorkActualTime, String leaveWorkActualPlaceCode, Integer leaveActualSourceInfo,
			Integer leaveWorkStampRoundingTime, Integer leaveWorkStampTime, String leaveWorkStampPlaceCode,
			Integer leaveWorkStampSourceInfo, Integer leaveWorkNumberStamp) {
		super();
		this.krcdtTimeLeavingWorkPK = krcdtTimeLeavingWorkPK;
		this.attendanceActualRoudingTime = attendanceActualRoudingTime;
		this.attendanceActualTime = attendanceActualTime;
		this.attendanceActualPlaceCode = attendanceActualPlaceCode;
		this.attendanceActualSourceInfo = attendanceActualSourceInfo;
		this.attendanceStampRoudingTime = attendanceStampRoudingTime;
		this.attendanceStampTime = attendanceStampTime;
		this.attendanceStampPlaceCode = attendanceStampPlaceCode;
		this.attendanceStampSourceInfo = attendanceStampSourceInfo;
		this.attendanceNumberStamp = attendanceNumberStamp;
		this.leaveWorkActualRoundingTime = leaveWorkActualRoundingTime;
		this.leaveWorkActualTime = leaveWorkActualTime;
		this.leaveWorkActualPlaceCode = leaveWorkActualPlaceCode;
		this.leaveActualSourceInfo = leaveActualSourceInfo;
		this.leaveWorkStampRoundingTime = leaveWorkStampRoundingTime;
		this.leaveWorkStampTime = leaveWorkStampTime;
		this.leaveWorkStampPlaceCode = leaveWorkStampPlaceCode;
		this.leaveWorkStampSourceInfo = leaveWorkStampSourceInfo;
		this.leaveWorkNumberStamp = leaveWorkNumberStamp;
	}

	public static List<TimeLeavingWork> toDomain(List<KrcdtTimeLeavingWork> krcdtTimeLeavingWorks) {
		return krcdtTimeLeavingWorks.stream().map(f -> f.toDomain()).collect(Collectors.toList());
	};

//	public static KrcdtTimeLeavingWork toEntity(TimeLeavingWork timeLeavingWork, String employeeId, GeneralDate ymd, int workNo, int type) {
//		return new KrcdtTimeLeavingWork(new KrcdtTimeLeavingWorkPK(employeeId, workNo, ymd, type),
//				timeLeavingWork.getAttendanceStamp().get().getActualStamp().getAfterRoundingTime().v(),
//				timeLeavingWork.getAttendanceStamp().get().getActualStamp().getTimeWithDay().v(),
//				timeLeavingWork.getAttendanceStamp().get().getActualStamp().getLocationCode().v(),
//				timeLeavingWork.getAttendanceStamp().get().getActualStamp().getStampSourceInfo().value,
//				timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getAfterRoundingTime().v() : null,
//				timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay().v() : null,
//				timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() ? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode().v() : null,
//				timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getStampSourceInfo().value : null,
//				timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp(), 
//				timeLeavingWork.getLeaveStamp().get().getActualStamp().getAfterRoundingTime().v(),
//				timeLeavingWork.getLeaveStamp().get().getActualStamp().getTimeWithDay().v(),
//				timeLeavingWork.getLeaveStamp().get().getActualStamp().getLocationCode().v(), 
//				timeLeavingWork.getLeaveStamp().get().getActualStamp().getStampSourceInfo().value,
//				timeLeavingWork.getLeaveStamp().get().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().get().getStamp().get().getAfterRoundingTime().v() : null,
//				timeLeavingWork.getLeaveStamp().get().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay().v() : null,
//				timeLeavingWork.getLeaveStamp().get().getStamp().isPresent() ? timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode().v() : null,
//				timeLeavingWork.getLeaveStamp().get().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().get().getStamp().get().getStampSourceInfo().value : null,
//				timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp());
//	}

	@Override
	protected Object getKey() {
		return this.krcdtTimeLeavingWorkPK;
	}

}
