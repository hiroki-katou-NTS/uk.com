package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkScheduleTest {
	
	@Injectable
	WorkSchedule.Require require;
	
	@Injectable 
	TimeLeavingOfDailyAttd timeLeaving;
	
	@Injectable 
	AttendanceTimeOfDailyAttendance attendanceTime;
	
	@Injectable
	OutingTimeOfDailyAttd outingTime;
	
	@Injectable
	static WorkInfoOfDailyAttendance workInfo;
	
	@Mocked
	static AffiliationInforOfDailyAttd affInfo;
	
	@Injectable
	static TimevacationUseTimeOfDaily timeVacationUseOfDaily;
	
	@Injectable
	static BreakTimeGoOutTimes workTime;
	
	@Injectable
	static OutingTotalTime recordTotalTime;
	
	@Injectable
	static OutingTotalTime deductionTotalTime;
	
	@Test
	public void getters() {
		WorkSchedule data = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, new BreakTimeOfDailyAttd(),
				new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty());
		NtsAssert.invokeGetters(data);
	}
	
	public static class TestCreate { 
		
		@Injectable
		WorkSchedule.Require require;
		
		@Test
		public void throwException(@Injectable WorkInformation workInformation) {
			
			new Expectations() {{
				
				workInformation.checkNormalCondition(require);
				result = false;
				
			}};
			
			NtsAssert.businessException("Msg_2119", 
					() -> WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation));
			
		}
		
		/**
		 * 休日の場合
		 */
		@Test
		public void withDayOff(
				@Injectable WorkInformation workInformation,
				@Mocked AffiliationInforOfDailyAttd affInfo,
				@Mocked WorkInfoOfDailyAttendance workInfo,
				@Mocked TimeLeavingOfDailyAttd timeLeaving
				) {
			
			new Expectations() {{
				
				workInformation.checkNormalCondition(require);
				result = true;
				
				workInformation.isAttendanceRate(require);
				result = false;
				
			}};
			
			WorkSchedule result = WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
			
			assertThat( result.getEmployeeID() ).isEqualTo( "empId" );
			assertThat ( result.getYmd() ).isEqualTo( GeneralDate.ymd(2020, 11, 1) );
			assertThat ( result.getConfirmedATR() ).isEqualTo( ConfirmedATR.UNSETTLED );
			assertThat ( result.getLstBreakTime().getBreakTimeSheets() ).isEmpty();
			assertThat ( result.getLstEditState() ).isEmpty();
			assertThat ( result.getOptAttendanceTime() ).isEmpty();
			assertThat ( result.getOptSortTimeWork() ).isEmpty();
			assertThat ( result.getAffInfo() ).isEqualTo( affInfo );
			assertThat ( result.getWorkInfo() ).isEqualTo( workInfo );
			// day off 休日
			assertThat ( result.getOptTimeLeaving() ).isEmpty();
			
		}
		
		/**
		 * 出勤の場合
		 */
		@Test
		public void withAttendanceDay(
				@Injectable WorkInformation workInformation,
				@Mocked AffiliationInforOfDailyAttd affInfo,
				@Mocked WorkInfoOfDailyAttendance workInfo,
				@Mocked TimeLeavingOfDailyAttd timeLeaving
				) {
			
			new Expectations() {{
				
				workInformation.checkNormalCondition(require);
				result = true;
				
				workInformation.isAttendanceRate(require);
				result = true;
			}};
			
			WorkSchedule result = WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
			
			assertThat( result.getEmployeeID() ).isEqualTo( "empId" );
			assertThat ( result.getYmd() ).isEqualTo( GeneralDate.ymd(2020, 11, 1) );
			assertThat ( result.getConfirmedATR() ).isEqualTo( ConfirmedATR.UNSETTLED );
			assertThat ( result.getLstBreakTime().getBreakTimeSheets() ).isEmpty();
			assertThat ( result.getLstEditState() ).isEmpty();
			assertThat ( result.getOptAttendanceTime() ).isEmpty();
			assertThat ( result.getOptSortTimeWork() ).isEmpty();
			assertThat ( result.getAffInfo() ).isEqualTo( affInfo );
			assertThat ( result.getWorkInfo() ).isEqualTo( workInfo );
			// attendance day 出勤
			assertThat ( result.getOptTimeLeaving().get() ).isEqualTo( timeLeaving );
			
		}
	}
	
	
	
	@Test
	public void testCreateByHandCorrectionWithWorkInformation(
			@Injectable WorkInformation workInformation,
			@Mocked AffiliationInforOfDailyAttd affInfo,
			@Mocked WorkInfoOfDailyAttendance workInfo,
			@Mocked TimeLeavingOfDailyAttd timeLeaving) {
		
		new Expectations() {{
			
			workInformation.checkNormalCondition(require);
			result = true;
			
			require.getLoginEmployeeId();
			result = "empId";
			
			workInformation.isAttendanceRate(require);
			result = true;
		}};
		
		WorkSchedule result = WorkSchedule.createByHandCorrectionWithWorkInformation(
				require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
		
		assertThat( result.getEmployeeID() ).isEqualTo( "empId" );
		assertThat( result.getYmd() ).isEqualTo( GeneralDate.ymd(2020, 11, 1) );
		assertThat( result.getConfirmedATR() ).isEqualTo( ConfirmedATR.UNSETTLED );
		assertThat( result.getLstBreakTime().getBreakTimeSheets() ).isEmpty();
		assertThat( result.getLstEditState() )
			.extracting( 
					d -> d.getAttendanceItemId(),
					d -> d.getEditStateSetting() )
			.containsExactly(
				tuple( WS_AttendanceItem.WorkType.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
				tuple( WS_AttendanceItem.WorkTime.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
				tuple( WS_AttendanceItem.StartTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
				tuple( WS_AttendanceItem.EndTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
				tuple( WS_AttendanceItem.StartTime2.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
				tuple( WS_AttendanceItem.EndTime2.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
				);
		assertThat( result.getOptAttendanceTime() ).isEmpty();
		assertThat( result.getOptSortTimeWork() ).isEmpty();
		assertThat ( result.getAffInfo() ).isEqualTo( affInfo );
		assertThat ( result.getWorkInfo() ).isEqualTo( workInfo );
		assertThat ( result.getOptTimeLeaving().get() ).isEqualTo( timeLeaving );
		
	}
	
	@Test
	public void testGetAttendanceItemValue(
			@Injectable TimeWithDayAttr start1,
			@Injectable TimeWithDayAttr end1,
			@Injectable TimeWithDayAttr start2,
			@Injectable TimeWithDayAttr end2,
			@Injectable NotUseAttribute goStraight,
			@Injectable NotUseAttribute backStraight
			) {

		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd(start1, end1, Optional.of( start2 ), Optional.of( end2 ));
		
		
		WorkSchedule workSchedule = Helper.createWithParams( Optional.of(timeLeaving), goStraight, backStraight );
		
		val start1_OfWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.StartTime1.ID );
		val end1_OfWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.EndTime1.ID );
		val start2_OfWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.StartTime2.ID );
		val end2_OfWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.EndTime2.ID );
		val goStraight_ofWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.GoStraight.ID );
		val backStraight_ofWorkSchedule = NtsAssert.Invoke.privateMethod(workSchedule, "getAttendanceItemValue", WS_AttendanceItem.BackStraight.ID );
		
		assertThat( start1_OfWorkSchedule ).isEqualTo( start1 );
		assertThat( end1_OfWorkSchedule ).isEqualTo( end1 );
		assertThat( start2_OfWorkSchedule ).isEqualTo( start2 );
		assertThat( end2_OfWorkSchedule ).isEqualTo( end2 );
		assertThat( goStraight_ofWorkSchedule ).isEqualTo( goStraight );
		assertThat( backStraight_ofWorkSchedule ).isEqualTo( backStraight );
		
	}
	
	@Test
	public void testUpdateValue(
			@Injectable TimeWithDayAttr start1,
			@Injectable TimeWithDayAttr end1,
			@Injectable TimeWithDayAttr start2,
			@Injectable TimeWithDayAttr end2,
			@Injectable NotUseAttribute goStraight,
			@Injectable NotUseAttribute backStraight,
			
			@Injectable TimeWithDayAttr newStart1,
			@Injectable TimeWithDayAttr newEnd1,
			@Injectable TimeWithDayAttr newStart2,
			@Injectable TimeWithDayAttr newEnd2,
			@Injectable NotUseAttribute newGoStraight,
			@Injectable NotUseAttribute newBackStraight
			) {

		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd(start1, end1, Optional.of( start2 ), Optional.of( end2 ));
		
		
		WorkSchedule workSchedule = Helper.createWithParams( Optional.of(timeLeaving), goStraight, backStraight );
		
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.StartTime1.ID, newStart1 );
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.EndTime1.ID, newEnd1 );
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.StartTime2.ID, newStart2 );
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.EndTime2.ID, newEnd2 );
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.GoStraight.ID, newGoStraight );
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValue", WS_AttendanceItem.BackStraight.ID, newBackStraight );
		
		val start1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
				.getAttendanceLeavingWork(1).get()
				.getAttendanceStamp().get()
				.getStamp().get()
				.getTimeDay().getTimeWithDay().get();
		val end1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
				.getAttendanceLeavingWork(1).get()
				.getLeaveStamp().get()
				.getStamp().get()
				.getTimeDay().getTimeWithDay().get();
		val start2_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
				.getAttendanceLeavingWork(2).get()
				.getAttendanceStamp().get()
				.getStamp().get()
				.getTimeDay().getTimeWithDay().get();
		val end2_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
				.getAttendanceLeavingWork(2).get()
				.getLeaveStamp().get()
				.getStamp().get()
				.getTimeDay().getTimeWithDay().get();
		val goStraight_ofWorkSchedule = workSchedule.getWorkInfo().getBackStraightAtr();
		val backStraight_ofWorkSchedule = workSchedule.getWorkInfo().getBackStraightAtr();
		
		assertThat( start1_OfWorkSchedule ).isEqualTo( newStart1 );
		assertThat( end1_OfWorkSchedule ).isEqualTo( newEnd1 );
		assertThat( start2_OfWorkSchedule ).isEqualTo( newStart2 );
		assertThat( end2_OfWorkSchedule ).isEqualTo( newEnd2 );
		assertThat( goStraight_ofWorkSchedule ).isEqualTo( newGoStraight );
		assertThat( backStraight_ofWorkSchedule ).isEqualTo( newBackStraight );
		
	}
	
	@Test
	public void testUpdateValueByHandCorrection_sameValue_noAddEditState(
			@Injectable TimeWithDayAttr end1
			) {
		
		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd(new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		
		WorkSchedule workSchedule = Helper.createWithParams( 
				timeLeaving, 
				new ArrayList<>()); // editStateList is empty
		
		// Act
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValueByHandCorrection", 
				require, 
				WS_AttendanceItem.StartTime1.ID, 
				new TimeWithDayAttr(123));
		
		
		// Assert
		val start1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
												.getAttendanceLeavingWork(1).get()
												.getAttendanceStamp().get()
												.getStamp().get()
												.getTimeDay().getTimeWithDay().get();
		assertThat( start1_OfWorkSchedule.v() ).isEqualTo( 123 );
		assertThat( workSchedule.getLstEditState() ).isEmpty();
	}
	
	@Test
	public void testUpdateValueByHandCorrection_sameValue_editStateNotChange(
			@Injectable TimeWithDayAttr end1
			) {
		
		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd(new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList( 
				new EditStateOfDailyAttd(WS_AttendanceItem.StartTime1.ID, EditStateSetting.IMPRINT )
				));
		
		WorkSchedule workSchedule = Helper.createWithParams( timeLeaving, editStateList);
		
		// Act
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValueByHandCorrection", 
				require, 
				WS_AttendanceItem.StartTime1.ID, 
				new TimeWithDayAttr(123));
		
		
		// Assert
		val start1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
												.getAttendanceLeavingWork(1).get()
												.getAttendanceStamp().get()
												.getStamp().get()
												.getTimeDay().getTimeWithDay().get();
		assertThat( start1_OfWorkSchedule.v() ).isEqualTo( 123 );
		assertThat( workSchedule.getLstEditState() )
			.extracting( 
					d -> d.getAttendanceItemId(),
					d -> d.getEditStateSetting() )
			.containsExactly(
				tuple ( 
					WS_AttendanceItem.StartTime1.ID, 
					EditStateSetting.IMPRINT ));
	}
	
	@Test
	public void testUpdateValueByHandCorrection_differentValue_addNewEditState(
			@Injectable TimeWithDayAttr end1
			) {
		
		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd( new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		WorkSchedule workSchedule = Helper.createWithParams( 
				timeLeaving, 
				new ArrayList<>()); // editStateList is empty
		
		new Expectations() {{
			require.getLoginEmployeeId();
			result = workSchedule.getEmployeeID();
		}};
		
		// Act
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValueByHandCorrection", 
				require, 
				WS_AttendanceItem.StartTime1.ID, 
				new TimeWithDayAttr(234));
		
		// Assert
		val start1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
												.getAttendanceLeavingWork(1).get()
												.getAttendanceStamp().get()
												.getStamp().get()
												.getTimeDay().getTimeWithDay().get();
		
		assertThat( start1_OfWorkSchedule.v() ).isEqualTo( 234 );
		assertThat( workSchedule.getLstEditState() )
			.extracting( 
					d -> d.getAttendanceItemId(),
					d -> d.getEditStateSetting() )
			.containsExactly(
				tuple ( 
					WS_AttendanceItem.StartTime1.ID, 
					EditStateSetting.HAND_CORRECTION_MYSELF ));
	}
	
	@Test
	public void testUpdateValueByHandCorrection_differentValue_changeEditState(
			@Injectable TimeWithDayAttr end1
			) {
		
		TimeLeavingOfDailyAttd timeLeaving = 
				Helper.createTimeLeavingOfDailyAttd( new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList( 
				new EditStateOfDailyAttd(WS_AttendanceItem.StartTime1.ID, EditStateSetting.IMPRINT )
				));
		
		WorkSchedule workSchedule = Helper.createWithParams( timeLeaving, editStateList);
		
		new Expectations() {{
			require.getLoginEmployeeId();
			result = workSchedule.getEmployeeID();
		}};
		
		// Act
		NtsAssert.Invoke.privateMethod(workSchedule, "updateValueByHandCorrection", 
				require, 
				WS_AttendanceItem.StartTime1.ID, 
				new TimeWithDayAttr(234));
		
		// Assert
		val start1_OfWorkSchedule = workSchedule.getOptTimeLeaving().get()
												.getAttendanceLeavingWork(1).get()
												.getAttendanceStamp().get()
												.getStamp().get()
												.getTimeDay().getTimeWithDay().get();
		
		assertThat( start1_OfWorkSchedule.v() ).isEqualTo( 234 );
		assertThat( workSchedule.getLstEditState() )
			.extracting( 
					d -> d.getAttendanceItemId(),
					d -> d.getEditStateSetting() )
			.containsExactly(
				tuple ( 
					WS_AttendanceItem.StartTime1.ID, 
					EditStateSetting.HAND_CORRECTION_MYSELF ));
	}
	
	@Test
	public void testConfirm() {
		
		WorkSchedule target = Helper.createWithConfirmAtr(ConfirmedATR.UNSETTLED);
		target.confirm();
		
		assertThat(target.getConfirmedATR()).isEqualTo(ConfirmedATR.CONFIRMED);
	}
	
	@Test
	public void testRemoveConfirm() {
		
		WorkSchedule target = Helper.createWithConfirmAtr(ConfirmedATR.CONFIRMED);
		target.removeConfirm();
		
		assertThat(target.getConfirmedATR()).isEqualTo(ConfirmedATR.UNSETTLED);
	}
	
	@Test
	public void testRemoveHandCorrections() {
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList(
				new EditStateOfDailyAttd(1, EditStateSetting.HAND_CORRECTION_MYSELF),
				new EditStateOfDailyAttd(2, EditStateSetting.HAND_CORRECTION_OTHER),
				new EditStateOfDailyAttd(3, EditStateSetting.REFLECT_APPLICATION),
				new EditStateOfDailyAttd(4, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(5, EditStateSetting.HAND_CORRECTION_MYSELF),
				new EditStateOfDailyAttd(6, EditStateSetting.HAND_CORRECTION_OTHER),
				new EditStateOfDailyAttd(7, EditStateSetting.REFLECT_APPLICATION),
				new EditStateOfDailyAttd(8, EditStateSetting.IMPRINT)));
		
		WorkSchedule target = Helper.createWithEditStateList(editStateList);
		target.removeHandCorrections();
		
		assertThat(target.getLstEditState())
			.extracting( 
					e -> e.getAttendanceItemId(), 
					e -> e.getEditStateSetting())
			.containsExactly( 
					tuple(3, EditStateSetting.REFLECT_APPLICATION),
					tuple(4, EditStateSetting.IMPRINT),
					tuple(7, EditStateSetting.REFLECT_APPLICATION),
					tuple(8, EditStateSetting.IMPRINT)
					);
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 出退勤(timeLeaving) is mocked 
	 */
	@Test
	public void testGetTimeVacation_empty_case1() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		
	}
	
	/** 
	 * 勤怠時間(attendanceTime) is mocked
	 * 出退勤 (timeLeaving) is empty
	 * 外出時間帯(outingTime) empty
	 */
	@Test
	public void testGetTimeVacation_empty_case2() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.of(attendanceTime), // 勤怠時間 mocked
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outingTimes = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outingTimes).isEmpty();
		
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 外出時間帯(outingTime) is mocked
	 */
	@Test
	public void testGetTimeVacation_empty_case3() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.of(outingTime)); // 外出時間帯
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 出退勤 (timeLeaving) is empty
	 * 外出時間帯(outingTime) empty
	 */
	@Test
	public void testGetTimeVacation_empty_case5() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outtingTimes = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outtingTimes).isEmpty();
		
	}
	
	/**
	 * 遅刻時間を取得する getLateTimeOfDaily == empty List
	 * 早退時間を取得	getLeaveEarlyTimeOfDaily == empty list
	 * 外出時間を取得する getOutingTimeOfDaily == emptyList
	 */
	@Test
	public void testGetTimeVacation_empty_case6() {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
										Optional.of(timeLeaving), 
										Optional.of(attendanceTime),
										Optional.of(outingTime));
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			// result = empty
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			// result = empty
			
			attendanceTime.getOutingTimeOfDaily();
			// result = empty
			
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outingTime = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outingTime).isEmpty();
		
	}
	
	/**
	 * result has one item
	 */
	@Test
	public void testGetLateTimes_successfully_single(
			@Injectable LateTimeOfDaily lateTime1,
			@Injectable LateTimeOfDaily lateTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			result = Arrays.asList( lateTime1, lateTime2 );
			
			lateTime1.getWorkNo();
			result = new WorkNo(1);
			lateTime2.getWorkNo();
			result = new WorkNo(2);
			
			timeLeaving.getStartTimeVacations(new WorkNo(1));
			// result = empty
			
			timeLeaving.getStartTimeVacations(new WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			lateTime2.getTimePaidUseTime();
			// result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		
		// Assert
		assertThat(result).hasSize(1);
		TimeVacation value = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_BEFORE);
		assertThat(value.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 100, 200 ));
		
	}
	
	/**
	 * result has two item
	 */
	@Test
	public void testGetLateTimes_successfully_multi(
			@Injectable LateTimeOfDaily lateTime1,
			@Injectable LateTimeOfDaily lateTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime1,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			result = Arrays.asList( lateTime1, lateTime2 );
			
			lateTime1.getWorkNo();
			result = new WorkNo(1);
			lateTime2.getWorkNo();
			result = new WorkNo(2);
			
			timeLeaving.getStartTimeVacations(new WorkNo(1));
			result = Optional.of(new TimeSpanForCalc(
					new TimeWithDayAttr(100),
					new TimeWithDayAttr(200)));
			
			timeLeaving.getStartTimeVacations(new WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(300),
									new TimeWithDayAttr(400)));

			lateTime1.getTimePaidUseTime();
			result = timePaidUseTime1;
			
			lateTime2.getTimePaidUseTime();
			result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		
			// value1
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE);
		assertThat(value1.getUseTime()).isEqualTo(timePaidUseTime1);
		assertThat(value1.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 100, 200 ));
		
			// value2
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_BEFORE);
		assertThat(value2.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value2.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 300, 400 ));
		
	}
	
	@Test
	public void testGetEarlyTimes_successfully_single(
			@Injectable LeaveEarlyTimeOfDaily earlyTime1,
			@Injectable LeaveEarlyTimeOfDaily earlyTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			result = Arrays.asList( earlyTime1, earlyTime2 );
			
			earlyTime1.getWorkNo();
			result = new WorkNo(1);
			earlyTime2.getWorkNo();
			result = new WorkNo(2);
			
			timeLeaving.getEndTimeVacations(new WorkNo(1));
			// result = empty
			timeLeaving.getEndTimeVacations(new WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			earlyTime2.getTimePaidUseTime();
			// result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(result).hasSize(1);
		TimeVacation value = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER);
		assertThat(value.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 100, 200 ));
		
	}
	
	@Test
	public void testGetEarlyTimes_successfully_multi(
			@Injectable LeaveEarlyTimeOfDaily earlyTime1,
			@Injectable LeaveEarlyTimeOfDaily earlyTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime1,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			result = Arrays.asList( earlyTime1, earlyTime2 );
			
			earlyTime1.getWorkNo();
			result = new WorkNo(1);
			earlyTime2.getWorkNo();
			result = new WorkNo(2);
			
			timeLeaving.getEndTimeVacations(new WorkNo(1));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			timeLeaving.getEndTimeVacations(new WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(300),
									new TimeWithDayAttr(400)));
			
			earlyTime1.getTimePaidUseTime();
			result = timePaidUseTime1;
			
			earlyTime2.getTimePaidUseTime();
			result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.WORK_NO1_AFTER);
		assertThat(value1.getUseTime()).isEqualTo(timePaidUseTime1);
		assertThat(value1.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 100, 200 ));
		
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER);
		assertThat(value2.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value2.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 300, 400 ));
		
	}
	
	
	/**
	 * 外出時間を取得する getOutingTimeOfDaily 
	 * 「私用, 組合」と合ってない
	 */
	@Test
	public void getOutingTimes_empty() {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
										Optional.empty(),
										Optional.of(attendanceTime),
										Optional.of(outingTime));
		
		new Expectations() {{
			
			attendanceTime.getOutingTimeOfDaily();
			result = Arrays.asList(
					Helper.createOutingTimeOfDailyWithReason(GoingOutReason.COMPENSATION),
					Helper.createOutingTimeOfDailyWithReason(GoingOutReason.PUBLIC)
					);
			
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void getOutingTimes_successfully(
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily1,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily2,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily3,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily4
			) {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
										Optional.empty(),
										Optional.of(attendanceTime), // 勤怠時間
										Optional.of(outingTime)); // 外出時間帯
		
		new Expectations() {{
			
			attendanceTime.getOutingTimeOfDaily();
			result = Arrays.asList(
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.PRIVATE, timevacationUseTimeOfDaily1),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.PUBLIC, timevacationUseTimeOfDaily2),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.COMPENSATION, timevacationUseTimeOfDaily3),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.UNION, timevacationUseTimeOfDaily4));
			
			outingTime.getTimeZoneByGoOutReason((GoingOutReason) any);
			returns(
					Arrays.asList(
							new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
							new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400))),
					Arrays.asList(new TimeSpanForCalc(new TimeWithDayAttr(500), new TimeWithDayAttr(600)))
					);
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE);
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_UNION);
		
		assertThat(value1.getUseTime()).isEqualTo(timevacationUseTimeOfDaily1);
		assertThat(value1.getTimeList())
			.extracting(
				d -> d.getStart().v(),
				d -> d.getEnd().v())
			.containsExactly(
					tuple(100, 200),
					tuple(300, 400));
		
		assertThat(value2.getUseTime()).isEqualTo(timevacationUseTimeOfDaily4);
		assertThat(value2.getTimeList())
			.extracting(
				d -> d.getStart().v(),
				d -> d.getEnd().v())
			.containsExactly(
				tuple(500, 600));
		
	}
	
	@Test
	public void testConvertToIntegrationOfDaily_empty(
				@Injectable WorkInfoOfDailyAttendance workInfo
			,	@Injectable AffiliationInforOfDailyAttd affInfo
			,	@Injectable BreakTimeOfDailyAttd lstBreakTime) {
		
		WorkSchedule workSchedule = new WorkSchedule("sid"
				,	GeneralDate.ymd(2021, 03, 19)
				,	ConfirmedATR.CONFIRMED
				,	workInfo
				,	affInfo
				,	lstBreakTime
				,	Collections.emptyList() //lstEditState
				,	Optional.empty() //timeLeaving
				,	Optional.empty() //attendanceTime
				,	Optional.empty() //shortTime
				,	Optional.empty()); //outingTime
		
		val daily = workSchedule.convertToIntegrationOfDaily();
		
		assertThat(daily.getEmployeeId()).isEqualTo(	"sid"	);
		assertThat(daily.getYmd()).isEqualTo(	GeneralDate.ymd(2021, 03, 19)	);
		assertThat(daily.getWorkInformation()).isEqualTo(	workInfo	);
		
		assertThat(daily.getAffiliationInfor()).isEqualTo(	affInfo	);
		assertThat(daily.getBreakTime()).isEqualTo(	lstBreakTime	);
		assertThat(daily.getPcLogOnInfo()).isEmpty();
		
		assertThat(daily.getEmployeeError()).isEmpty();
		assertThat(daily.getOutingTime()).isEmpty();
		assertThat(daily.getAttendanceTimeOfDailyPerformance()).isEmpty();
		
		assertThat(daily.getAttendanceLeave()).isEmpty();
		assertThat(daily.getShortTime()).isEmpty();
		assertThat(daily.getSpecDateAttr()).isEmpty();
		
		assertThat(daily.getAttendanceLeavingGate()).isEmpty();
		assertThat(daily.getAnyItemValue()).isEmpty();
		assertThat(daily.getEditState()).isEmpty();
		
		assertThat(daily.getTempTime()).isEmpty();
		assertThat(daily.getRemarks()).isEmpty();
		assertThat(daily.getSnapshot()).isEmpty();
	}
	
	@Test
	public void testConvertToIntegrationOfDaily(
				@Injectable WorkInfoOfDailyAttendance workInfo
			,	@Injectable AffiliationInforOfDailyAttd affInfo
			,	@Injectable BreakTimeOfDailyAttd lstBreakTime
			,	@Injectable List<EditStateOfDailyAttd> lstEditState
			,	@Injectable TimeLeavingOfDailyAttd timeLeaving
			,	@Injectable AttendanceTimeOfDailyAttendance attendanceTime
			,	@Injectable ShortTimeOfDailyAttd shortTime
			,	@Injectable OutingTimeOfDailyAttd outingTime) {
		
		WorkSchedule workSchedule = new WorkSchedule("sid"
				,	GeneralDate.ymd(2021, 03, 19)
				,	ConfirmedATR.CONFIRMED
				,	workInfo
				,	affInfo
				,	lstBreakTime
				,	lstEditState
				,	Optional.of(timeLeaving)
				,	Optional.of(attendanceTime)
				,	Optional.of(shortTime)
				,	Optional.of(outingTime));
		
		val daily = workSchedule.convertToIntegrationOfDaily();
		
		assertThat(daily.getEmployeeId()).isEqualTo( "sid" );
		assertThat(daily.getYmd()).isEqualTo(	GeneralDate.ymd(2021, 03, 19)	);
		assertThat(daily.getWorkInformation()).isEqualTo( 	workInfo	);
		
		assertThat(daily.getAffiliationInfor()).isEqualTo(	affInfo	);
		assertThat(daily.getOutingTime().get()).isEqualTo(	outingTime	)	;
		assertThat(daily.getBreakTime()).isEqualTo(	lstBreakTime	);
		
		assertThat(daily.getAttendanceTimeOfDailyPerformance().get()).isEqualTo(	attendanceTime	);
		assertThat(daily.getAttendanceLeave().get()).isEqualTo(	timeLeaving	);
		assertThat(daily.getShortTime().get()).isEqualTo(	shortTime	);
		
		assertThat(daily.getEditState()).containsAnyElementsOf(	lstEditState	);
		assertThat(daily.getPcLogOnInfo()).isEmpty();
		assertThat(daily.getEmployeeError()).isEmpty();
		
		assertThat(daily.getSpecDateAttr()).isEmpty();
		assertThat(daily.getAttendanceLeavingGate()).isEmpty();
		assertThat(daily.getAnyItemValue()).isEmpty();
		
		assertThat(daily.getTempTime()).isEmpty();
		assertThat(daily.getRemarks()).isEmpty();
		assertThat(daily.getSnapshot()).isEmpty();
		
	}
	
	public static class TestHandCorrectBreakTimeList {
		
		@Injectable
		WorkSchedule.Require require;
		
		private WorkSchedule workSchedule;
		
		@Before
		public void init() {
			
			BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(new ArrayList<>(Arrays.asList(
					new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0))
					)));
			
			List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList(
					new EditStateOfDailyAttd(WS_AttendanceItem.WorkTime.ID, EditStateSetting.REFLECT_APPLICATION),
					new EditStateOfDailyAttd(WS_AttendanceItem.StartTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
					new EditStateOfDailyAttd(WS_AttendanceItem.StartBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
					new EditStateOfDailyAttd(WS_AttendanceItem.EndBreakTime2.ID, EditStateSetting.HAND_CORRECTION_OTHER),
					new EditStateOfDailyAttd(WS_AttendanceItem.StartBreakTime5.ID, EditStateSetting.REFLECT_APPLICATION),
					new EditStateOfDailyAttd(WS_AttendanceItem.BreakTime.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
					));
			
			workSchedule = Helper.createWithParams(breakTime, editStateList);
			
			new Expectations() {{
				require.getLoginEmployeeId();
				result = workSchedule.getEmployeeID();
			}};
		}
		
		@Test
		public void testHandCorrectBreakTimeList_inputEmpty() {

			// Act
			workSchedule.handCorrectBreakTimeList(require, new ArrayList<>() );
			
			assertThat(workSchedule.getLstBreakTime().getBreakTimeSheets()).isEmpty();
			
			assertThat( workSchedule.getLstEditState())
				.extracting( 
						d -> d.getAttendanceItemId(),
						d -> d.getEditStateSetting() )
				.containsExactly(
						tuple( WS_AttendanceItem.WorkTime.ID, EditStateSetting.REFLECT_APPLICATION),
						tuple( WS_AttendanceItem.StartTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.StartBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.EndBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.BreakTime.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
						);
		} 
		
		
		@Test
		public void testHandCorrectBreakTimeList_inputNotEmpty() {
			
			// Act
			workSchedule.handCorrectBreakTimeList(require, Arrays.asList(
					new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400)),
					new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
					new TimeSpanForCalc(new TimeWithDayAttr(500), new TimeWithDayAttr(600))
					));
			
			assertThat(workSchedule.getLstBreakTime().getBreakTimeSheets())
				.extracting( 
						d -> d.getBreakFrameNo().v(),
						d -> d.getStartTime().v(),
						d -> d.getEndTime().v() )
				.containsExactly(
						tuple( 1, 100, 200),
						tuple( 2, 300, 400),
						tuple( 3, 500, 600)
						);
			
			assertThat( workSchedule.getLstEditState())
				.extracting( 
						d -> d.getAttendanceItemId(),
						d -> d.getEditStateSetting() )
				.containsExactly(
						tuple( WS_AttendanceItem.WorkTime.ID, EditStateSetting.REFLECT_APPLICATION),
						tuple( WS_AttendanceItem.StartTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						// 休憩時刻　１～３
						tuple( WS_AttendanceItem.StartBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.EndBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.StartBreakTime2.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.EndBreakTime2.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.StartBreakTime3.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						tuple( WS_AttendanceItem.EndBreakTime3.ID, EditStateSetting.HAND_CORRECTION_MYSELF),
						// 休憩時間
						tuple( WS_AttendanceItem.BreakTime.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
						);
		} 
	}
	
	static class Helper {
		
		/**
		 * @param optTimeLeaving 出退勤
		 * @param optAttendanceTime 勤怠時間
		 * @param outingTime 外出時間帯
		 * @return
		 */
		static WorkSchedule createWithParams(
				Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
				Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
				Optional<OutingTimeOfDailyAttd> outingTime
				) {
			
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					new BreakTimeOfDailyAttd(),
					Collections.emptyList(),
					optTimeLeaving, // parameter
					optAttendanceTime, // parameter
					Optional.empty(),
					outingTime); // parameter
		}
		
		/**
		 * @param timeLeaving 出退勤
		 * @param editStateList 編修状態
		 * @return
		 */
		static WorkSchedule createWithParams(
				TimeLeavingOfDailyAttd timeLeaving,
				List<EditStateOfDailyAttd> editStateList
				) {
			
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					new BreakTimeOfDailyAttd(),
					editStateList, 
					Optional.of( timeLeaving ),
					Optional.empty(), 
					Optional.empty(),
					Optional.empty()); 
		}
		
		/**
		 * @param optTimeLeaving 出退勤
		 * @param goStraight 直行区分
		 * @param backStraight 直帰区分 
		 * @return
		 */
		static WorkSchedule createWithParams(
				Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
				NotUseAttribute goStraight,
				NotUseAttribute backStraight
				) {
			
			WorkInfoOfDailyAttendance workInfo = new WorkInfoOfDailyAttendance(
					new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("002")),
					CalculationState.No_Calculated, 
					goStraight, 
					backStraight, 
					DayOfWeek.MONDAY, 
					new ArrayList<>()); 
			
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					new BreakTimeOfDailyAttd(),
					Collections.emptyList(), 
					optTimeLeaving,
					Optional.empty(), 
					Optional.empty(),
					Optional.empty()); 
		}
		
		static WorkSchedule createWithParams(
				BreakTimeOfDailyAttd breakTime,
				List<EditStateOfDailyAttd> editStateList
				) {
			
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					breakTime,
					editStateList, 
					Optional.empty(),
					Optional.empty(), 
					Optional.empty(),
					Optional.empty()); 
		}
		
		static WorkSchedule createWithConfirmAtr(ConfirmedATR confirmAtr) {
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					confirmAtr,
					workInfo,
					affInfo, 
					new BreakTimeOfDailyAttd(),
					Collections.emptyList(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty());
		}
		
		static WorkSchedule createWithEditStateList(List<EditStateOfDailyAttd> editStateList) {
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					new BreakTimeOfDailyAttd(),
					editStateList,
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty());
		}
		
		static OutingTimeOfDaily createOutingTimeOfDailyWithReason(GoingOutReason reason) {
			return new OutingTimeOfDaily(
					workTime,
					reason,
					timeVacationUseOfDaily,
					recordTotalTime,
					deductionTotalTime,
					Collections.emptyList());
			
		}
		
		static OutingTimeOfDaily createOutingTimeOfDailyWithParams(GoingOutReason reason, TimevacationUseTimeOfDaily timeVacationUseOfDaily) {
			return new OutingTimeOfDaily(
					workTime,
					reason,
					timeVacationUseOfDaily,
					recordTotalTime,
					deductionTotalTime,
					Collections.emptyList());
			
		}
		
		static TimeLeavingOfDailyAttd createTimeLeavingOfDailyAttd(
				TimeWithDayAttr start1,
				TimeWithDayAttr end1,
				Optional<TimeWithDayAttr> start2,
				Optional<TimeWithDayAttr> end2) {
			
			// 勤務時刻情報 １
			WorkTimeInformation start1_wti = new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), start1);
			WorkTimeInformation end1_wti = new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), end1);
			// 勤怠打刻１
			WorkStamp start1_ws = new WorkStamp(start1_wti, Optional.empty());
			WorkStamp end1_ws = new WorkStamp(end1_wti, Optional.empty());
			// 勤怠打刻（実打刻付き）１
			TimeActualStamp timeActualStampStart1 = new TimeActualStamp(Optional.empty(), Optional.of(start1_ws), 1, Optional.empty(), Optional.empty());
			TimeActualStamp timeActualStampEnd1 = new TimeActualStamp(Optional.empty(), Optional.of(end1_ws), 1, Optional.empty(), Optional.empty());
			// 出退勤１
			TimeLeavingWork timeLeavingWork1 = new TimeLeavingWork(
					new WorkNo(1), 
					Optional.of(timeActualStampStart1), 
					Optional.of(timeActualStampEnd1), 
					false, 
					false);
			
			
			if ( !start2.isPresent() || !end2.isPresent() ) {
				return new TimeLeavingOfDailyAttd( Arrays.asList(timeLeavingWork1), new WorkTimes(1));
			}
			
			// 勤務時刻情報 ２
			WorkTimeInformation start2_wti = new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), start2.get());
			WorkTimeInformation end2_wti = new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), end2.get());
			// 勤怠打刻２
			WorkStamp start2_ws = new WorkStamp(start2_wti, Optional.empty());
			WorkStamp end2_ws = new WorkStamp(end2_wti, Optional.empty());
			// 勤怠打刻（実打刻付き）２
			TimeActualStamp timeActualStampStart2 = new TimeActualStamp(Optional.empty(), Optional.of(start2_ws), 1, Optional.empty(), Optional.empty());
			TimeActualStamp timeActualStampEnd2 = new TimeActualStamp(Optional.empty(), Optional.of(end2_ws), 1, Optional.empty(), Optional.empty());
			TimeLeavingWork timeLeavingWork2 = new TimeLeavingWork(
					new WorkNo(2), 
					Optional.of(timeActualStampStart2), 
					Optional.of(timeActualStampEnd2), 
					false, 
					false);
			
			return new TimeLeavingOfDailyAttd( Arrays.asList(timeLeavingWork1, timeLeavingWork2), new WorkTimes(2) );
			
		}
		
	}
	
}
