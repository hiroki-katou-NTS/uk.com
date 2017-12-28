package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 出退勤
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TIME_LEAVING_WORK")
public class KrcdtTimeLeavingWork extends UkJpaEntity implements Serializable {

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

	public TimeLeavingWork toDomain() {
		TimeLeavingWork domain = new TimeLeavingWork(new WorkNo(this.krcdtTimeLeavingWorkPK.workNo),
				new TimeActualStamp(
						new WorkStamp(new TimeWithDayAttr(this.attendanceActualRoudingTime),
								new TimeWithDayAttr(this.attendanceActualTime),
								new WorkLocationCD(this.attendanceActualPlaceCode),
								EnumAdaptor.valueOf(this.attendanceActualSourceInfo, StampSourceInfo.class)),
						new WorkStamp(new TimeWithDayAttr(this.attendanceStampRoudingTime),
								new TimeWithDayAttr(this.attendanceStampTime),
								new WorkLocationCD(this.attendanceStampPlaceCode),
								EnumAdaptor.valueOf(this.attendanceStampSourceInfo, StampSourceInfo.class)),
						this.attendanceNumberStamp.intValue()),
				new TimeActualStamp(
						new WorkStamp(new TimeWithDayAttr(this.leaveWorkActualRoundingTime),
								new TimeWithDayAttr(this.leaveWorkActualTime),
								new WorkLocationCD(this.leaveWorkActualPlaceCode),
								EnumAdaptor.valueOf(this.leaveActualSourceInfo, StampSourceInfo.class)),
						new WorkStamp(new TimeWithDayAttr(this.leaveWorkStampRoundingTime),
								new TimeWithDayAttr(this.leaveWorkStampTime),
								new WorkLocationCD(this.leaveWorkStampPlaceCode),
								EnumAdaptor.valueOf(this.leaveWorkStampSourceInfo, StampSourceInfo.class)),
						this.leaveWorkNumberStamp.intValue()));
		return domain;
	}

	public static KrcdtTimeLeavingWork toEntity(String employeeId, GeneralDate ymd, TimeLeavingWork domain) {
		WorkStamp attendanceStamp = domain.getAttendanceStamp().getStamp().orElse(null);
		WorkStamp leaveStamp = domain.getAttendanceStamp().getStamp().orElse(null);
		return new KrcdtTimeLeavingWork(new KrcdtTimeLeavingWorkPK(employeeId, domain.getWorkNo().v(), ymd),
				domain.getAttendanceStamp().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
				domain.getAttendanceStamp().getActualStamp().getTimeWithDay().valueAsMinutes(),
				domain.getAttendanceStamp().getActualStamp().getLocationCode().v(),
				domain.getAttendanceStamp().getActualStamp().getStampSourceInfo().value,
				attendanceStamp == null ? null : attendanceStamp.getAfterRoundingTime().valueAsMinutes(),
				attendanceStamp == null ? null : attendanceStamp.getTimeWithDay().valueAsMinutes(),
				attendanceStamp.getLocationCode().v(),
				attendanceStamp.getStampSourceInfo().value, domain.getAttendanceStamp().getNumberOfReflectionStamp(), 
				domain.getLeaveStamp().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
				domain.getLeaveStamp().getActualStamp().getTimeWithDay().valueAsMinutes(),
				domain.getLeaveStamp().getActualStamp().getLocationCode().v(),
				domain.getLeaveStamp().getActualStamp().getStampSourceInfo().value,
				leaveStamp == null ? null : leaveStamp.getAfterRoundingTime().valueAsMinutes(),
				leaveStamp == null ? null : leaveStamp.getTimeWithDay().valueAsMinutes(),
				leaveStamp.getLocationCode().v(),
				leaveStamp.getStampSourceInfo().value, domain.getLeaveStamp().getNumberOfReflectionStamp());

	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiLeavingWork daiLeavingWork;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiTemporaryTime daiTemporaryTime;

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

	public static KrcdtTimeLeavingWork toEntity(TimeLeavingWork timeLeavingWork, String employeeId, GeneralDate ymd, int workNo) {
		return new KrcdtTimeLeavingWork(new KrcdtTimeLeavingWorkPK(employeeId, workNo, ymd),
				timeLeavingWork.getAttendanceStamp().getActualStamp().getAfterRoundingTime().v(),
				timeLeavingWork.getAttendanceStamp().getActualStamp().getTimeWithDay().v(),
				timeLeavingWork.getAttendanceStamp().getActualStamp().getLocationCode().v(),
				timeLeavingWork.getAttendanceStamp().getActualStamp().getStampSourceInfo().value,
				timeLeavingWork.getAttendanceStamp().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime().v() : null,
				timeLeavingWork.getAttendanceStamp().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay().v() : null,
				timeLeavingWork.getAttendanceStamp().getStamp().isPresent() ? timeLeavingWork.getAttendanceStamp().getStamp().get().getLocationCode().v() : null,
				timeLeavingWork.getAttendanceStamp().getStamp().isPresent() ?  timeLeavingWork.getAttendanceStamp().getStamp().get().getStampSourceInfo().value : null,
				timeLeavingWork.getAttendanceStamp().getNumberOfReflectionStamp(), 
				timeLeavingWork.getLeaveStamp().getActualStamp().getAfterRoundingTime().v(),
				timeLeavingWork.getLeaveStamp().getActualStamp().getTimeWithDay().v(),
				timeLeavingWork.getLeaveStamp().getActualStamp().getLocationCode().v(), 
				timeLeavingWork.getLeaveStamp().getActualStamp().getStampSourceInfo().value,
				timeLeavingWork.getLeaveStamp().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().getStamp().get().getAfterRoundingTime().v() : null,
				timeLeavingWork.getLeaveStamp().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay().v() : null,
				timeLeavingWork.getLeaveStamp().getStamp().isPresent() ? timeLeavingWork.getLeaveStamp().getStamp().get().getLocationCode().v() : null,
				timeLeavingWork.getLeaveStamp().getStamp().isPresent() ?  timeLeavingWork.getLeaveStamp().getStamp().get().getStampSourceInfo().value : null,
				timeLeavingWork.getLeaveStamp().getNumberOfReflectionStamp());
	}

	@Override
	protected Object getKey() {
		return this.krcdtTimeLeavingWorkPK;
	}

}
