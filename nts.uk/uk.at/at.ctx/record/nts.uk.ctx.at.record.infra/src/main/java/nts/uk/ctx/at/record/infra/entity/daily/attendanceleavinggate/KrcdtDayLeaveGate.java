package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The persistent class for the KRCDT_DAY_LEAVE_GATE database table.
 * 
 */
@Entity
@Table(name = "KRCDT_DAY_LEAVE_GATE")
// @NamedQuery(name="KrcdtDayLeaveGate.findAll", query="SELECT k FROM
// KrcdtDayLeaveGate k")
public class KrcdtDayLeaveGate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayLeaveGatePK id;

	@Column(name = "ATTENDANCE_PLACE_CODE1")
	public String attendancePlaceCode1;

	@Column(name = "ATTENDANCE_PLACE_CODE2")
	public String attendancePlaceCode2;

	@Column(name = "ATTENDANCE_PLACE_CODE3")
	public String attendancePlaceCode3;

	@Column(name = "ATTENDANCE_STAMP_SOURCE1")
	public Integer attendanceStampSource1;

	@Column(name = "ATTENDANCE_STAMP_SOURCE2")
	public Integer attendanceStampSource2;

	@Column(name = "ATTENDANCE_STAMP_SOURCE3")
	public Integer attendanceStampSource3;

	@Column(name = "ATTENDANCE_TIME1")
	public Integer attendanceTime1;

	@Column(name = "ATTENDANCE_TIME2")
	public Integer attendanceTime2;

	@Column(name = "ATTENDANCE_TIME3")
	public Integer attendanceTime3;

	@Column(name = "LEAVE_PLACE_CODE1")
	public String leavePlaceCode1;

	@Column(name = "LEAVE_PLACE_CODE2")
	public String leavePlaceCode2;

	@Column(name = "LEAVE_PLACE_CODE3")
	public String leavePlaceCode3;

	@Column(name = "LEAVE_STAMP_SOURCE1")
	public Integer leaveStampSource1;

	@Column(name = "LEAVE_STAMP_SOURCE2")
	public Integer leaveStampSource2;

	@Column(name = "LEAVE_STAMP_SOURCE3")
	public Integer leaveStampSource3;

	@Column(name = "LEAVE_TIME1")
	public Integer leaveTime1;

	@Column(name = "LEAVE_TIME2")
	public Integer leaveTime2;

	@Column(name = "LEAVE_TIME3")
	public Integer leaveTime3;

	public KrcdtDayLeaveGate() {
	}

	public KrcdtDayLeaveGate(KrcdtDayLeaveGatePK id) {
		super();
		this.id = id;
	}
	
	public static KrcdtDayLeaveGate from(AttendanceLeavingGateOfDaily domain){
		KrcdtDayLeaveGate entity = new KrcdtDayLeaveGate(new KrcdtDayLeaveGatePK(domain.getEmployeeId(), domain.getYmd()));
		entity.mergeData(domain);
		return entity;
	}
	
	public void mergeData(AttendanceLeavingGateOfDaily domain){
		getAttendanceLeavingGateNo(1, domain).ifPresent(al -> {
			al.getAttendance().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.attendancePlaceCode1 = plc.v();
				});
				this.attendanceTime1 = getTime(a);
				this.attendanceStampSource1 = getSourceStamp(a);
			});
			al.getLeaving().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.leavePlaceCode1 = plc.v();
				});
				this.leaveTime1 = getTime(a);
				this.leaveStampSource1 = getSourceStamp(a);
			});
			
		});
		getAttendanceLeavingGateNo(2, domain).ifPresent(al -> {
			al.getAttendance().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.attendancePlaceCode2 = plc.v();
				});
				this.attendanceTime2 = getTime(a);
				this.attendanceStampSource2 = getSourceStamp(a);
			});
			al.getLeaving().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.leavePlaceCode2 = plc.v();
				});
				this.leaveTime2 = getTime(a);
				this.leaveStampSource2 = getSourceStamp(a);
			});
			
		});
		getAttendanceLeavingGateNo(3, domain).ifPresent(al -> {
			al.getAttendance().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.attendancePlaceCode3 = plc.v();
				});
				this.attendanceTime3 = getTime(a);
				this.attendanceStampSource3 = getSourceStamp(a);
			});
			al.getLeaving().ifPresent(a -> {
				a.getLocationCode().ifPresent(plc -> {
					this.leavePlaceCode3 = plc.v();
				});
				this.leaveTime3 = getTime(a);
				this.leaveStampSource3 = getSourceStamp(a);
			});
			
		});
	}

	private static Integer getSourceStamp(WorkStamp a) {
		return a.getStampSourceInfo() == null ? null : a.getStampSourceInfo().value;
	}

	private static Integer getTime(WorkStamp a) {
		return a.getTimeWithDay() == null ? null : a.getTimeWithDay().valueAsMinutes();
	}
	
	private static Optional<AttendanceLeavingGate> getAttendanceLeavingGateNo(int no, AttendanceLeavingGateOfDaily domain){
		return domain.getAttendanceLeavingGates().stream().filter(c -> c.getWorkNo().v() == no).findFirst();
	}

	public AttendanceLeavingGateOfDaily toDomain() {
		return new AttendanceLeavingGateOfDaily(this.id.sid, this.id.ymd,
				Stream.of(getNo1(), getNo2(), getNo3()).collect(Collectors.toList()));
	}

	private AttendanceLeavingGate getNo1() {
		return new AttendanceLeavingGate(new WorkNo(1),
				new WorkStamp(toTimeWithDay(attendanceTime1), toTimeWithDay(attendanceTime1),
						toWorkLocationCD(attendancePlaceCode1), toWorkLocationCD(attendanceStampSource1)),
				new WorkStamp(toTimeWithDay(leaveTime1), toTimeWithDay(leaveTime1), toWorkLocationCD(leavePlaceCode1),
						toWorkLocationCD(leaveStampSource1)));
	}

	private AttendanceLeavingGate getNo2() {
		return new AttendanceLeavingGate(new WorkNo(2),
				new WorkStamp(toTimeWithDay(attendanceTime2), toTimeWithDay(attendanceTime2),
						toWorkLocationCD(attendancePlaceCode2), toWorkLocationCD(attendanceStampSource2)),
				new WorkStamp(toTimeWithDay(leaveTime2), toTimeWithDay(leaveTime2), toWorkLocationCD(leavePlaceCode2),
						toWorkLocationCD(leaveStampSource2)));
	}

	private AttendanceLeavingGate getNo3() {
		return new AttendanceLeavingGate(new WorkNo(3),
				new WorkStamp(toTimeWithDay(attendanceTime3), toTimeWithDay(attendanceTime3),
						toWorkLocationCD(attendancePlaceCode3), toWorkLocationCD(attendanceStampSource3)),
				new WorkStamp(toTimeWithDay(leaveTime3), toTimeWithDay(leaveTime3), toWorkLocationCD(leavePlaceCode3),
						toWorkLocationCD(leaveStampSource3)));
	}

	private TimeWithDayAttr toTimeWithDay(Integer time) {
		return time == null ? null : new TimeWithDayAttr(time);
	}

	private WorkLocationCD toWorkLocationCD(String placeCode) {
		return placeCode == null ? null : new WorkLocationCD(placeCode);
	}

	private StampSourceInfo toWorkLocationCD(Integer stampSource) {
		return stampSource == null ? null : EnumAdaptor.valueOf(leaveStampSource1, StampSourceInfo.class);
	}
}