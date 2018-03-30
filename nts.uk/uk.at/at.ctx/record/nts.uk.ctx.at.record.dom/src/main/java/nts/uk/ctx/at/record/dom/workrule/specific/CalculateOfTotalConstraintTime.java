package nts.uk.ctx.at.record.dom.workrule.specific;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnNo;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 総拘束時間の計算.
 *
 * @author HoangNDH
 */
@Data
@AllArgsConstructor
public class CalculateOfTotalConstraintTime {
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The calc method. */
	// 計算方法
	private CalculationMethodOfConstraintTime calcMethod;
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param calcMethod the calc method
	 * @return the calculate of total constraint time
	 */
	public static CalculateOfTotalConstraintTime createFromJavaType(String companyId, int calcMethod) {
		return new CalculateOfTotalConstraintTime(new CompanyId(companyId), EnumAdaptor.valueOf(calcMethod, CalculationMethodOfConstraintTime.class));
	}
	
	
	/**
	 * 総拘束時間の計算
	 * @return
	 */
	public AttendanceTime calcCalculateOfTotalConstraintTime(Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDaily,
															  Optional<PCLogOnInfoOfDaily> pCLogOnInfoOfDaily,
															  Optional<TimeLeavingOfDailyPerformance> attendanceLeave) {
		
		AttendanceTime result = new AttendanceTime(createCalculateOfTotalConstraintTimeSheetList(attendanceLeavingGateOfDaily,
													  											 pCLogOnInfoOfDaily,
													  											 attendanceLeave).stream().filter(t->t!=null)
																			   											  .mapToInt(t -> {if(t.getStart()==null) {
																			   												  return t.getEnd()!=null?0:t.getEnd().valueAsMinutes();
																			   											  }else if(t.getEnd()==null) {
																			   												  return t.getStart()!=null?0:0-t.getStart().valueAsMinutes();
																			   											  }
																			   											  return t.getEnd().valueAsMinutes()-t.getStart().valueAsMinutes();})
																			   											  .sum());		
		return result;
	}
	
	/**
	 * 打刻の取得
	 * @param attendanceLeavingGateOfDaily
	 * @param pCLogOnInfoOfDaily
	 * @param attendanceLeave
	 * @return
	 */
	public List<TimeSpanForCalc> createCalculateOfTotalConstraintTimeSheetList(Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDaily,
			  																   Optional<PCLogOnInfoOfDaily> pCLogOnInfoOfDaily,
			  																   Optional<TimeLeavingOfDailyPerformance> attendanceLeave){
		List<TimeSpanForCalc> list = new ArrayList<>();
		TimeWithDayAttr attendance = null;
		TimeWithDayAttr leave = null;
		
		for(int i=1;i<3;i++) {
			//出勤、退勤時間の取得
			if(attendanceLeave.isPresent()) {
				if(attendanceLeave.get().getAttendanceLeavingWork(i).isPresent()) {
					if(attendanceLeave.get().getAttendanceLeavingWork(i).get().getAttendanceStamp().isPresent()) {
						if(attendanceLeave.get().getAttendanceLeavingWork(i).get().getAttendanceStamp().get().getStamp().isPresent()) {
							attendance = attendanceLeave.get().getAttendanceLeavingWork(i).get().getAttendanceStamp().get().getStamp().get().getTimeWithDay();
						}
					}
					if(attendanceLeave.get().getAttendanceLeavingWork(i).get().getLeaveStamp().isPresent()) {
						if(attendanceLeave.get().getAttendanceLeavingWork(i).get().getLeaveStamp().get().getStamp().isPresent()) {
							leave = attendanceLeave.get().getAttendanceLeavingWork(i).get().getLeaveStamp().get().getStamp().get().getTimeWithDay();
						}
					}
				}
			}
			//PCログイン・ログオフから求める場合
			if(this.calcMethod.isREQUEST_FROM_PC_OUTSIDE_ATTENDANCE()){
				//ログオフ、ログオフの取得
				TimeWithDayAttr pcLogOn = null;
				TimeWithDayAttr pcLogOff = null;
				if(pCLogOnInfoOfDaily.isPresent()) {
					pcLogOn = pCLogOnInfoOfDaily.get().getLogOnTime(new PCLogOnNo(i), GoLeavingWorkAtr.GO_WORK).orElse(null);
					pcLogOff = pCLogOnInfoOfDaily.get().getLogOnTime(new PCLogOnNo(i), GoLeavingWorkAtr.LEAVING_WORK).orElse(null);
				}
				list.add(createTotalConstraintTimeSheet(attendance,leave,pcLogOn,pcLogOff));
			}else {//入退門から求める場合
				TimeWithDayAttr attendanceGate = null;
				TimeWithDayAttr leaveGate = null;
				//入門、退門時間の取得
				if(attendanceLeavingGateOfDaily.isPresent()) {
					attendanceGate = attendanceLeavingGateOfDaily.get().getAttendanceLeavingGateTime(new WorkNo(i), GoLeavingWorkAtr.GO_WORK);
					leaveGate = attendanceLeavingGateOfDaily.get().getAttendanceLeavingGateTime(new WorkNo(i), GoLeavingWorkAtr.LEAVING_WORK);
				}
				list.add(createTotalConstraintTimeSheet(attendance,leave,attendanceGate,leaveGate));
			}
		}
		return list;
	}
	
	/**
	 * 総拘束時間計算対象時間帯を作成
	 * @return
	 */
	public TimeSpanForCalc createTotalConstraintTimeSheet(TimeWithDayAttr attendance,
														  TimeWithDayAttr leave,
														  TimeWithDayAttr ComparisonStart,
														  TimeWithDayAttr ComparisonEnd) {
		TimeWithDayAttr start = null;
		TimeWithDayAttr end = null;
		if(this.calcMethod.isREQUEST_FROM_ENTRANCE_EXIT_OUTSIDE_ATTENDANCE()||this.calcMethod.isREQUEST_FROM_PC_OUTSIDE_ATTENDANCE()) {//出勤退勤よりも外側の場合のみ
			//出勤＞比較対象開始の時
			if(attendance.greaterThan(ComparisonStart.valueAsMinutes())||ComparisonStart!=null) {
				start = ComparisonStart;
			}
			start = attendance;
			//退勤1＞比較対象終了の時
			if(ComparisonEnd.greaterThan(leave.valueAsMinutes())||ComparisonEnd!=null) {
			end = ComparisonEnd;
			}
			end = leave;
		}else {//入門退門から求める場合
			if(ComparisonStart!=null){
				start = ComparisonStart;
			}
			start = attendance;
			if(ComparisonEnd!=null) {
				end = ComparisonEnd;
			}
			end = leave;
		}
		return new TimeSpanForCalc(start, end);
	}
		
}
