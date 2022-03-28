package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static mockit.Deencapsulation.invoke;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.share.AffiliationInforOfDailyAttdHelperInSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskScheduleDetailTestHelper;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
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
	
//	@Test
//	public void getters() {
//		WorkSchedule data = new WorkSchedule("employeeID",
//				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, new BreakTimeOfDailyAttd(),
//				new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty());
//		NtsAssert.invokeGetters(data);
//	}
	
	public static class TestCreate { 
		
		@Injectable
		WorkSchedule.Require require;
		
		@Test
		public void throwException(@Injectable WorkInformation workInformation) {
			
			new Expectations() {{
				
				workInformation.checkNormalCondition(require, anyString);
				result = false;
				
			}};
			
			NtsAssert.businessException("Msg_2119", 
					() -> WorkSchedule.create(require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), workInformation));
			
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
				
				workInformation.checkNormalCondition(require, anyString);
				result = true;
				
				workInformation.isAttendanceRate(require, anyString);
				result = false;
				
			}};
			
			WorkSchedule result = WorkSchedule.create(require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
			
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
				
				workInformation.checkNormalCondition(require, anyString);
				result = true;
				
				workInformation.isAttendanceRate(require, anyString);
				result = true;
			}};
			
			WorkSchedule result = WorkSchedule.create(require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
			
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
			
			workInformation.checkNormalCondition(require, anyString);
			result = true;
			
			require.getLoginEmployeeId();
			result = "empId";
			
			workInformation.isAttendanceRate(require, anyString);
			result = true;
		}};
		
		WorkSchedule result = WorkSchedule.createByHandCorrectionWithWorkInformation(
				require, "cmpId", "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
		
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd(start1, end1, Optional.of( start2 ), Optional.of( end2 ));
		
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( Optional.of(timeLeaving), goStraight, backStraight );
		
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd(start1, end1, Optional.of( start2 ), Optional.of( end2 ));
		
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( Optional.of(timeLeaving), goStraight, backStraight );
		
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd(new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( 
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd(new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList( 
				new EditStateOfDailyAttd(WS_AttendanceItem.StartTime1.ID, EditStateSetting.IMPRINT )
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( timeLeaving, editStateList);
		
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd( new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( 
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
				TimeLeavingOfDailyAttdHelper.createTimeLeavingOfDailyAttd( new TimeWithDayAttr(123), end1, Optional.empty(), Optional.empty());
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList( 
				new EditStateOfDailyAttd(WS_AttendanceItem.StartTime1.ID, EditStateSetting.IMPRINT )
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams( timeLeaving, editStateList);
		
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
		
		WorkSchedule target = WorkScheduleHelper.createWithConfirmAtr(ConfirmedATR.UNSETTLED);
		target.confirm();
		
		assertThat(target.getConfirmedATR()).isEqualTo(ConfirmedATR.CONFIRMED);
	}
	
	@Test
	public void testRemoveConfirm() {
		
		WorkSchedule target = WorkScheduleHelper.createWithConfirmAtr(ConfirmedATR.CONFIRMED);
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
		
		WorkSchedule target = WorkScheduleHelper.createWithEditStateList(editStateList);
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
	
	@Test
	public void testGetTimeVacation_success(
			@Injectable Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacations ) {	

		new MockUp<GettingTimeVacactionService>() {
			
			@Mock
			public Map<TimezoneToUseHourlyHoliday, TimeVacation> get(Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
					Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
					Optional<OutingTimeOfDailyAttd> outingTime) {

				return timeVacations;
				
			}

		};
		
		// Arrange
		WorkSchedule target = WorkScheduleHelper.createWithParams(
										Optional.of(timeLeaving), 
										Optional.of(attendanceTime),
										Optional.of(outingTime));
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEqualTo(timeVacations);

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
					new EditStateOfDailyAttd(WS_AttendanceItem.StartBreakTime5.ID, EditStateSetting.REFLECT_APPLICATION)
					));
			
			workSchedule = WorkScheduleHelper.createWithParams(breakTime, editStateList);
			
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
						tuple( WS_AttendanceItem.EndBreakTime1.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
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
						tuple( WS_AttendanceItem.EndBreakTime3.ID, EditStateSetting.HAND_CORRECTION_MYSELF)
						);
		} 
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2103(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TaskSchedule taskSchedule) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithWorkInfo(workInfo);
		workSchedule.setTaskSchedule(taskSchedule);
		
		new Expectations() {{
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2103", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2098(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithWorkInfoAndTimeLeaving(workInfo, timeLeaving);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 8, 0, 9, 0),
				TaskScheduleDetailTestHelper.create("code2", 10, 0, 12, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = new HashMap<>();
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2098", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2099(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 8, 0, 9, 0),
				TaskScheduleDetailTestHelper.create("code2", 10, 0, 12, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = new HashMap<>();
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = true;
		}};
		
		NtsAssert.businessException("Msg_2099", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2100(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 8, 0, 9, 0),
				TaskScheduleDetailTestHelper.create("code2", 10, 0, 12, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = new HashMap<>();
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = true;
		}};
		
		NtsAssert.businessException("Msg_2100", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2101_WORK_NO1_BEFORE(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 9, 0, 10, 0),
				TaskScheduleDetailTestHelper.create("code2", 13, 0, 14, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationResult = new HashMap<>();
		timeVacationResult.put(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(8, 0), 
							TimeWithDayAttr.hourMinute(10, 0))), 
						timevacationUseTimeOfDaily));
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = timeVacationResult;
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2101", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_2101_GOINGOUT_UNION(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 9, 0, 10, 0),
				TaskScheduleDetailTestHelper.create("code2", 13, 0, 14, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationResult = new HashMap<>();
		timeVacationResult.put(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(8, 0), 
							TimeWithDayAttr.hourMinute(9, 0))), 
						timevacationUseTimeOfDaily));
		timeVacationResult.put(TimezoneToUseHourlyHoliday.GOINGOUT_UNION, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(13, 0), 
							TimeWithDayAttr.hourMinute(15, 0))), 
						timevacationUseTimeOfDaily));
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = timeVacationResult;
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2101", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_Msg_3235(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
			@Injectable TargetOrgIdenInfor supportDestination) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		SupportSchedule supportSchedule = new SupportSchedule(Arrays.asList(
				new SupportScheduleDetail(
						supportDestination, 
						SupportType.TIMEZONE, 
						Optional.of( new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 30), 
								TimeWithDayAttr.hourMinute(10, 30))))));
		workSchedule.setSupportSchedule(supportSchedule);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 9, 0, 10, 0),
				TaskScheduleDetailTestHelper.create("code2", 13, 0, 14, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationResult = new HashMap<>();
		timeVacationResult.put(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(8, 0), 
							TimeWithDayAttr.hourMinute(9, 0))), 
						timevacationUseTimeOfDaily));
		timeVacationResult.put(TimezoneToUseHourlyHoliday.GOINGOUT_UNION, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(14, 0), 
							TimeWithDayAttr.hourMinute(15, 0))), 
						timevacationUseTimeOfDaily));
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = timeVacationResult;
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = false;
		}};
		
		NtsAssert.businessException("Msg_3235", () -> {
			invoke(workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule);
		});
		
	}
	
	@Test
	public void testCheckWhetherTaskScheduleIsCorrect_OK(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 9, 0, 10, 0),
				TaskScheduleDetailTestHelper.create("code2", 13, 0, 14, 0)));
		workSchedule.setTaskSchedule(taskSchedule);
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationResult = new HashMap<>();
		timeVacationResult.put(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(8, 0), 
							TimeWithDayAttr.hourMinute(9, 0))), 
						timevacationUseTimeOfDaily));
		timeVacationResult.put(TimezoneToUseHourlyHoliday.GOINGOUT_UNION, 
				new TimeVacation(
						Arrays.asList( new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(14, 0), 
							TimeWithDayAttr.hourMinute(15, 0))), 
						timevacationUseTimeOfDaily));
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = timeVacationResult;
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = false;
		}};
		
		boolean result = NtsAssert.Invoke.privateMethod( workSchedule, "checkWhetherTaskScheduleIsCorrect", require, "cid", taskSchedule );
		
		assertThat( result ).isTrue();
		
	}
	
	@Test
	public void testUpdateTaskSchedule(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable BreakTimeOfDailyAttd breakTime,
			@Injectable TimeLeavingOfDailyAttd timeLeaving,
			@Injectable ShortTimeOfDailyAttd shortTimeWork,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving, shortTimeWork);
		
		TaskSchedule newTaskSchedule = new TaskSchedule(Arrays.asList(
				TaskScheduleDetailTestHelper.create("code1", 9, 0, 10, 0),
				TaskScheduleDetailTestHelper.create("code2", 13, 0, 14, 0)));
		
		new Expectations(workSchedule) {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			workSchedule.getTimeVacation();
			result = new HashMap<>();
			
			timeLeaving.isIncludeInWorkTimeSpan( (TimeSpanForCalc) any );
			result = true;
			
			breakTime.isDuplicatedWithBreakTime( (TimeSpanForCalc) any );
			result = false;
			
			shortTimeWork.isDuplicatedWithShortTime( (TimeSpanForCalc) any );
			result = false;
			
		}};
		
		workSchedule.updateTaskSchedule(require, "cid", newTaskSchedule);
		
		assertThat( workSchedule.getTaskSchedule() ).isEqualTo( newTaskSchedule );
		
	}
	
	@Test
	public void testGetTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan_breakTime() {
		
		BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(15, 0), TimeWithDayAttr.hourMinute(15, 30))
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithBreakTimeAndShortTime(breakTime, Optional.empty());
		
		new Expectations(workSchedule) {{
			workSchedule.getTimeVacation();
			result = new HashMap<>();
		}};
		
		TimeSpanForCalc target = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan", require, target);
		
		assertThat( result )
		.extracting( 
			d -> d.getStart().hour(),
			d -> d.getStart().minute(),
			d -> d.getEnd().hour(),
			d -> d.getEnd().minute() )
		.containsExactly(
			tuple( 8, 0, 12, 0),
			tuple( 13, 0, 15, 0),
			tuple( 15, 30, 17, 0) );
		
	}
	
	@Test
	public void testGetTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan_shortTime() {
		
		ShortTimeOfDailyAttd shortTime = new ShortTimeOfDailyAttd(Arrays.asList(
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(1), 
						ChildCareAtr.CHILD_CARE, 
						TimeWithDayAttr.hourMinute(11, 0), 
						TimeWithDayAttr.hourMinute(12, 0)),
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAtr.CARE, 
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithBreakTimeAndShortTime(new BreakTimeOfDailyAttd(), Optional.of(shortTime) );
		
		new Expectations(workSchedule) {{
			workSchedule.getTimeVacation();
			result = new HashMap<>();
		}};
		
		TimeSpanForCalc target = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan", require, target);
		
		assertThat( result )
		.extracting( 
			d -> d.getStart().hour(),
			d -> d.getStart().minute(),
			d -> d.getEnd().hour(),
			d -> d.getEnd().minute() )
		.containsExactly(
			tuple( 8, 0, 11, 0),
			tuple( 12, 0, 15, 0) );
	}
	
	@Test
	public void testGetTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan_timeVacation(
			@Injectable TimevacationUseTimeOfDaily useTime) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithBreakTimeAndShortTime(new BreakTimeOfDailyAttd(), Optional.empty() );
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationMap = new HashMap<>();
		timeVacationMap.put(
				TimezoneToUseHourlyHoliday.GOINGOUT_UNION, 
				new TimeVacation(
					Arrays.asList(
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0)),
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(13, 0), 
							TimeWithDayAttr.hourMinute(15, 0)) ),
					useTime)
				);
		
		new Expectations(workSchedule) {{
			workSchedule.getTimeVacation();
			result = timeVacationMap;
		}};
		
		TimeSpanForCalc target = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan", require, target);
		
		assertThat( result )
		.extracting( 
			d -> d.getStart().hour(),	
			d -> d.getStart().minute(),
			d -> d.getEnd().hour(),
			d -> d.getEnd().minute() )
		.containsExactly(
			tuple( 8, 0, 9, 0),
			tuple( 10, 0, 13, 0),
			tuple( 15, 0, 17, 0) );
	}
	
	@Test
	public void testGetTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan_all(
			@Injectable TimevacationUseTimeOfDaily useTime) {
		
		BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(
						new BreakFrameNo(1), 
						TimeWithDayAttr.hourMinute(12, 0), 
						TimeWithDayAttr.hourMinute(13, 0)) ));
		
		ShortTimeOfDailyAttd shortTime = new ShortTimeOfDailyAttd(Arrays.asList(
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAtr.CARE, 
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithBreakTimeAndShortTime( breakTime, Optional.of( shortTime ) );
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationMap = new HashMap<>();
		timeVacationMap.put(
				TimezoneToUseHourlyHoliday.GOINGOUT_UNION, 
				new TimeVacation(
					Arrays.asList(
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0)) ),
					useTime)
				);
		
		new Expectations(workSchedule) {{
			
			workSchedule.getTimeVacation();
			result = timeVacationMap;
		}};
		
		TimeSpanForCalc target = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getTimeSpansWhichNotDuplicatedWithTheNotWorkingTimeSpan", require, target);
		
		assertThat( result )
		.extracting( 
			d -> d.getStart().hour(),
			d -> d.getStart().minute(),
			d -> d.getEnd().hour(),
			d -> d.getEnd().minute() )
		.containsExactly(
			tuple( 8, 0, 9, 0),
			tuple( 10, 0, 12, 0),
			tuple( 13, 0, 15, 0) );
	}
	
	@Test
	public void testGetWorkingTimeSpan_empty(
			@Injectable WorkInfoOfDailyAttendance workInfo) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithWorkInfo(workInfo);
		
		new Expectations(workSchedule) {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getWorkingTimeSpan", require, "cid");
		
		assertThat( result ).isEmpty();
	}
	
	@Test
	public void testGetWorkingTimeSpan_OK(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		// not working time
		BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(15, 0), TimeWithDayAttr.hourMinute(15, 30))
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving);
		
		List<TimeSpanForCalc> timeLeavingAttList = Arrays.asList(
				new TimeSpanForCalc( TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 00)),
				new TimeSpanForCalc( TimeWithDayAttr.hourMinute(20, 0), TimeWithDayAttr.hourMinute(23, 00))
				);
		
		new Expectations(workSchedule) {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = timeLeavingAttList;
		}};
		
		List<TimeSpanForCalc> result = NtsAssert.Invoke.privateMethod(workSchedule, "getWorkingTimeSpan", require, "cid");
		
		assertThat( result )
			.extracting( 
				d -> d.getStart().hour(),
				d -> d.getStart().minute(),
				d -> d.getEnd().hour(),
				d -> d.getEnd().minute())
			.containsExactly( 
				tuple(8, 0, 12, 0),
				// break-time 12:00~13:00
				tuple(13, 0, 15, 0),
				// break-time 15:00~15:30
				tuple(15, 30, 17, 0),
				tuple(20, 0, 23, 0)
			);
	}
	
	@Test
	public void testCreateTaskScheduleForWholeDay_Msg_2103(
			@Injectable WorkInfoOfDailyAttendance workInfo) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithWorkInfo(workInfo);
		
		new Expectations() {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2103", () -> {
			workSchedule.createTaskScheduleForWholeDay(require, "cid", new TaskCode("code"));
		});
	}
	
	@Test
	public void testCreateTaskScheduleForWholeDay_Ok(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		// not working time
		BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(15, 0), TimeWithDayAttr.hourMinute(15, 30))
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(workInfo, breakTime, timeLeaving);
		
		List<TimeSpanForCalc> timeLeavingAttList = Arrays.asList(
				new TimeSpanForCalc( TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 00)),
				new TimeSpanForCalc( TimeWithDayAttr.hourMinute(20, 0), TimeWithDayAttr.hourMinute(23, 00))
				);
		
		new Expectations() {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = timeLeavingAttList;
		}};
		
		workSchedule.createTaskScheduleForWholeDay(require, "cid", new TaskCode("code"));
		
		TaskSchedule result = workSchedule.getTaskSchedule();
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple("code", 8, 0, 12, 0),
				tuple("code", 13, 0, 15, 0),
				tuple("code", 15, 30, 17, 0),
				tuple("code", 20, 0, 23, 0));
	}
	
	/**
	 * WorkSchedule: holiday
	 * -----------
	 * Expect: BusinessException Msg_2103
	 */
	@Test
	public void testAddTaskScheduleWithTimeSpan_Msg_2103(
			@Injectable WorkInfoOfDailyAttendance workInfo) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithWorkInfo(workInfo);
		
		new Expectations() {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2103", () -> {
			workSchedule.addTaskScheduleWithTimeSpan(
					require,
					"cid",
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(11, 0),
						TimeWithDayAttr.hourMinute(18, 0)),
					new TaskCode("001") );
		});
	}
	
	/**
	 * WorkSchedule 8:00~17:00
	 * 		breakTime: 12:00~13:00
	 * 		existsTaskSchedule: "000" 8:00~12:00
	 * ↑
	 * insertTaskSchedule: "001" 10:00~18:00
	 * ----------------------------------
	 * Expect: 
	 * 		"000" 8:00~10:00
	 * 		"001" 10:00~12:00
	 * 		"001" 13:00~17:00
	 */
	@Test
	public void testAddTaskScheduleWithTimeSpan_ok(
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving
			) {
		
		// create WorkSchedule and its attributes
		BreakTimeOfDailyAttd breakTime = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(new BreakFrameNo(1), TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(Arrays.asList(
				new TaskScheduleDetail(
						new TaskCode("000"),
						new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(8, 00), 
								TimeWithDayAttr.hourMinute(12, 00))
						)
				));
		
		WorkSchedule workSchedule = WorkScheduleHelper.createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setTaskSchedule(taskSchedule);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		// mock
		new Expectations(workSchedule) {{
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = Arrays.asList(
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 00)) );
			
			workSchedule.getTimeVacation();
			result = new HashMap<>();
		}};
		
		// run
		workSchedule.addTaskScheduleWithTimeSpan(
			require,
			"cid",
			new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(10, 0),
				TimeWithDayAttr.hourMinute(18, 0)),
			new TaskCode("001") );
		
		// assert
		assertThat( workSchedule.getTaskSchedule().getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "000", 8, 0, 10, 0),
				tuple( "001", 10, 0, 12, 0),
				tuple( "001", 13, 0, 17, 0)
			);
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_notExistSupportSchedule() {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		NtsAssert.Invoke.privateMethod(workSchedule, "checkConsistencyOfSupportSchedule", require);
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_supportSchedule_allday(
			@Injectable TargetOrgIdenInfor supportDestination) {
		
		val supportSchedule = new SupportSchedule( Arrays.asList(
						new SupportScheduleDetail(
								supportDestination, 
								SupportType.ALLDAY, 
								Optional.empty()))); 
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), supportSchedule);
		
		NtsAssert.Invoke.privateMethod(workSchedule, "checkConsistencyOfSupportSchedule", require);
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_Msg_2275(
			@Injectable TargetOrgIdenInfor supportDestination,
			@Injectable WorkInfoOfDailyAttendance workInfo) {
		
		val supportSchedule = new SupportSchedule( Arrays.asList(
						new SupportScheduleDetail(
								supportDestination, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(11, 0)))))); 
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), supportSchedule);
		workSchedule.setWorkInfo(workInfo);
		
		new Expectations() {{
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2275", () -> {
			invoke(workSchedule, "checkConsistencyOfSupportSchedule", require);
		});
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_Msg_2276(
			@Injectable TargetOrgIdenInfor supportDestination,
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		val supportSchedule = new SupportSchedule( Arrays.asList(
						new SupportScheduleDetail(
								supportDestination, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(13, 0)))))); 
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), supportSchedule);
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		new Expectations() {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = Arrays.asList(new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		}};
		
		NtsAssert.businessException("Msg_2276", () -> {
			invoke(workSchedule, "checkConsistencyOfSupportSchedule", require);
		});
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_Msg_3235(
			@Injectable TargetOrgIdenInfor supportDestination,
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		val supportSchedule = new SupportSchedule( Arrays.asList(
						new SupportScheduleDetail(
								supportDestination, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(11, 0)))))); 
		val taskSchedule = new TaskSchedule( Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 30), 
										TimeWithDayAttr.hourMinute(11, 30)))
				));
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				taskSchedule, supportSchedule);
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		new Expectations() {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = Arrays.asList(new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		}};
		
		NtsAssert.businessException("Msg_3235", () -> {
			invoke(workSchedule, "checkConsistencyOfSupportSchedule", require);
		});
	}
	
	@Test
	public void testCheckConsistencyOfSupportSchedule_OK(
			@Injectable TargetOrgIdenInfor supportDestination,
			@Injectable WorkInfoOfDailyAttendance workInfo,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		val supportSchedule = new SupportSchedule( Arrays.asList(
						new SupportScheduleDetail(
								supportDestination, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(11, 0)))))); 
		val taskSchedule = new TaskSchedule( Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(11, 0)))
				));
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				taskSchedule, supportSchedule);
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		new Expectations() {{
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = Arrays.asList(new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(12, 0)));
		}};
		
		NtsAssert.Invoke.privateMethod(workSchedule, "checkConsistencyOfSupportSchedule", require);
	}

	@Test
	public void testCreateSupportSchedule_Msg_3254_defer_employee_id (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("other-emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty()));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_Msg_3254_defer_date (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 2), 
						Optional.empty()));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_Msg_2268 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.CONFIRMED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty()));
		
		NtsAssert.businessException("Msg_2268", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_Msg_2271 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(
						Arrays.asList(new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(8, 0), 
										TimeWithDayAttr.hourMinute(9, 0))))
						),
				SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty()));
		
		NtsAssert.businessException("Msg_2271", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_Msg_2273 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(
						Arrays.asList(new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(8, 0), 
										TimeWithDayAttr.hourMinute(9, 0))))
						),
				SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(8, 30), 
								TimeWithDayAttr.hourMinute(9, 30)))));
		
		NtsAssert.businessException("Msg_2273", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_Msg_2277(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty()),
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty())
				);
		
		NtsAssert.businessException("Msg_2277", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_supportSchedule_create_error (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0)))),
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 30),
								TimeWithDayAttr.hourMinute(10, 30))))
				);
		
		new Expectations() {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
		}};
		
		NtsAssert.businessException("Msg_2278", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_checkConsistencyOfSupportSchedule_error(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0)))),
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(13, 0),
								TimeWithDayAttr.hourMinute(14, 0))))
				);
		
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2275", () -> {
			workSchedule.createSupportSchedule(require, supportTickets);
		});
	}
	
	@Test
	public void testCreateSupportSchedule_OK(
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		val supportTickets = Arrays.asList(
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(13, 0),
								TimeWithDayAttr.hourMinute(14, 0)))),
				new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))))
				);
		
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo, timeLeaving) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		}};
		
		workSchedule.createSupportSchedule(require, supportTickets);
		
		assertThat(workSchedule.getSupportSchedule().getDetails())
			.extracting( 
					s -> s.getSupportDestination(),
					s -> s.getSupportType(),
					s -> s.getTimeSpan().get().getStart().rawHour(),
					s -> s.getTimeSpan().get().getStart().minute(),
					s -> s.getTimeSpan().get().getEnd().rawHour(),
					s -> s.getTimeSpan().get().getEnd().minute())
			.containsExactly(
					tuple(recipient, SupportType.TIMEZONE, 9, 0, 10, 0),
					tuple(recipient, SupportType.TIMEZONE, 13, 0, 14, 0));
	}

	@Test
	public void testAddSupportSchedule_Msg_3254_defer_employee_id(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val ticket = new SupportTicket(
						new EmployeeId("other-emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty());
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.addSupportSchedule(require, ticket);
		});
	}
	
	@Test
	public void testAddSupportSchedule_Msg_3254_defer_date(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val ticket = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 2), 
						Optional.empty());
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.addSupportSchedule(require, ticket);
		});
	}
	
	@Test
	public void testAddSupportSchedule_Msg_2268 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.CONFIRMED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val ticket = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty());
		
		NtsAssert.businessException("Msg_2268", () -> {
			workSchedule.addSupportSchedule(require, ticket);
		});
	}
	
	@Test
	public void testAddSupportSchedule_Msg_2271 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(
						Arrays.asList(new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(8, 0), 
										TimeWithDayAttr.hourMinute(9, 0))))
						), 
				SupportSchedule.createWithEmptyList());
		
		val ticket = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.ALLDAY, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.empty());
		
		NtsAssert.businessException("Msg_2271", () -> {
			workSchedule.addSupportSchedule(require, ticket);
		});
	}
	
	@Test
	public void testAddSupportSchedule_Msg_2273 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(
						Arrays.asList(new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(8, 0), 
										TimeWithDayAttr.hourMinute(9, 0))))
						), 
				SupportSchedule.createWithEmptyList());
		
		val ticket = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(8, 30), 
								TimeWithDayAttr.hourMinute(9, 30))));
		
		NtsAssert.businessException("Msg_2273", () -> {
			workSchedule.addSupportSchedule(require, ticket);
		});
	}

	@Test
	public void testAddSupportSchedule_supportSchedule_add_error(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(recipient, SupportType.ALLDAY, Optional.empty())
						)));
		
		val supportTicket = new SupportTicket(
								new EmployeeId("emp-id"), 
								recipient, 
								SupportType.ALLDAY, 
								GeneralDate.ymd(2021, 12, 1), 
								Optional.empty());
		
		NtsAssert.businessException("Msg_2277", () -> {
			workSchedule.addSupportSchedule(require, supportTicket);
		});
	}

	@Test
	public void testAddSupportSchedule_checkConsistencyOfSupportSchedule_error(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), SupportSchedule.createWithEmptyList());
		
		val supportTickets = new SupportTicket(
								new EmployeeId("emp-id"), 
								recipient, 
								SupportType.TIMEZONE, 
								GeneralDate.ymd(2021, 12, 1), 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(13, 0),
										TimeWithDayAttr.hourMinute(14, 0))));
		
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = false;
		}};
		
		NtsAssert.businessException("Msg_2275", () -> {
			workSchedule.addSupportSchedule(require, supportTickets);
		});
	}

	@Test
	public void testAddSupportSchedule_OK(
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
									TimeWithDayAttr.hourMinute(13, 0),
									TimeWithDayAttr.hourMinute(14, 0))))
						)));
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		val supportTicket = new SupportTicket(
								new EmployeeId("emp-id"), 
								recipient, 
								SupportType.TIMEZONE, 
								GeneralDate.ymd(2021, 12, 1), 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))));
	
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo, timeLeaving) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		}};
		
		workSchedule.addSupportSchedule(require, supportTicket);
		
		assertThat(workSchedule.getSupportSchedule().getDetails())
			.extracting( 
					s -> s.getSupportDestination(),
					s -> s.getSupportType(),
					s -> s.getTimeSpan().get().getStart().rawHour(),
					s -> s.getTimeSpan().get().getStart().minute(),
					s -> s.getTimeSpan().get().getEnd().rawHour(),
					s -> s.getTimeSpan().get().getEnd().minute())
			.containsExactly(
					tuple(recipient, SupportType.TIMEZONE, 9, 0, 10, 0),
					tuple(recipient, SupportType.TIMEZONE, 13, 0, 14, 0));
	}
	
	@Test
	public void testModifySupportSchedule_Msg_3254_beforeModify_differ_employee_id(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("other-emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_3254_beforeModify_differ_date(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 2), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_3254_afterModify_differ_employee_id(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("other-emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_3254_afterModify_differ_date(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 2), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_2268 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.CONFIRMED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_2268", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_2273_exist_taskSchedule_in_beforeModify (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(9, 30))))), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_2273", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_2273_exist_taskSchedule_in_afterModify (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 0), 
										TimeWithDayAttr.hourMinute(10, 30))))), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_2273", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}
	
	@Test
	public void testModifySupportSchedule_Msg_2273_exist_taskSchedule_in_both_beforeModify_and_afterModify (
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				new TaskSchedule(Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(9, 30))))), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))))
						);
		
		val beforeModify = new SupportTicket(
						new EmployeeId("emp-id"), 
						recipient, 
						SupportType.TIMEZONE, 
						GeneralDate.ymd(2021, 12, 1), 
						Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(9, 0), 
								TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		NtsAssert.businessException("Msg_2273", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}

	@Test
	public void testModifySupportSchedule_supportSchedule_update_error(
			@Injectable TargetOrgIdenInfor recipient) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0)))),
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(10, 30), 
										TimeWithDayAttr.hourMinute(11, 30))))
						)));
		
		val beforeModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		new Expectations() {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
		}};
		
		NtsAssert.businessException("Msg_2278", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}

	@Test
	public void testModifySupportSchedule_checkConsistencyOfSupportSchedule_error (
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))
						)));
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo, timeLeaving) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		}};
		
		
		val beforeModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(18, 0))));
		
		
		NtsAssert.businessException("Msg_2276", () -> {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		});
	}

	@Test
	public void testModifySupportSchedule_OK(
			@Injectable TargetOrgIdenInfor recipient,
			@Injectable TimeLeavingOfDailyAttd timeLeaving) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of(new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))
						)));
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		val workInfo = workSchedule.getWorkInfo();
		new Expectations(workInfo, timeLeaving) {{
			
			require.getSupportOperationSetting();
			result = new SupportOperationSetting( true, true, new MaximumNumberOfSupport(5) );
			
			workInfo.isAttendanceRate(require, anyString);
			result = true;
			
			timeLeaving.getTimeOfTimeLeavingAtt();
			result = new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(17, 0));
		}};
		
		val beforeModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))));
		val afterModify = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0))));
		
		// Action
		workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		
		// Assert
		assertThat(workSchedule.getSupportSchedule().getDetails())
			.extracting( 
					s -> s.getSupportDestination(),
					s -> s.getSupportType(),
					s -> s.getTimeSpan().get().getStart().rawHour(),
					s -> s.getTimeSpan().get().getStart().minute(),
					s -> s.getTimeSpan().get().getEnd().rawHour(),
					s -> s.getTimeSpan().get().getEnd().minute())
			.containsExactly(
					tuple(recipient, SupportType.TIMEZONE, 10, 0, 11, 0));
	}
	
	@Test
	public void testRemoveSupportSchedule_Msg_3254_differ_employee_id(
			@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(recipient, SupportType.ALLDAY, Optional.empty())
						)));
		
		val ticket = new SupportTicket(
				new EmployeeId("other-emp-id"), 
				recipient, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.removeSupportSchedule(ticket);
		}); 
		
	}
	
	@Test
	public void testRemoveSupportSchedule_Msg_3254_differ_date (
			@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(recipient, SupportType.ALLDAY, Optional.empty())
						)));
		
		val ticket = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 2), 
				Optional.empty());
		
		NtsAssert.businessException("Msg_3254", () -> {
			workSchedule.removeSupportSchedule(ticket);
		}); 
		
	}
	
	@Test
	public void testRemoveSupportSchedule_Msg_2268 (
			@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.CONFIRMED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(recipient, SupportType.ALLDAY, Optional.empty())
						)));
		
		val ticket = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		NtsAssert.businessException("Msg_2268", () -> {
			workSchedule.removeSupportSchedule(ticket);
		}); 
		
	}
	
	@Test
	public void testRemoveSupportSchedule_AllDay (
			@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.create(Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))))), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(recipient, SupportType.ALLDAY, Optional.empty())
						)));
		
		val ticket = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.ALLDAY, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.empty());
		
		
		// Action
		workSchedule.removeSupportSchedule(ticket);
		
		// Assert
		assertThat(workSchedule.getTaskSchedule().getDetails()).isEmpty();
		assertThat(workSchedule.getSupportSchedule().getDetails()).isEmpty();
	}
	
	@Test
	public void testRemoveSupportSchedule_Timezone(
			@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(
				"emp-id", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.create(Arrays.asList(
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(9, 0), 
										TimeWithDayAttr.hourMinute(10, 0))),
						new TaskScheduleDetail(
								new TaskCode("001"), 
								new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(14, 0), 
										TimeWithDayAttr.hourMinute(15, 0)))
						)), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of( new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(8, 0), 
										TimeWithDayAttr.hourMinute(12, 0)))),
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of( new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(13, 0), 
										TimeWithDayAttr.hourMinute(17, 0))))
						)));
		
		val ticket = new SupportTicket(
				new EmployeeId("emp-id"), 
				recipient, 
				SupportType.TIMEZONE, 
				GeneralDate.ymd(2021, 12, 1), 
				Optional.of(new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(12, 0))));
		
		
		// Action
		workSchedule.removeSupportSchedule(ticket);
		
		// Assert
		assertThat(workSchedule.getTaskSchedule().getDetails())
			.extracting(
					t -> t.getTaskCode().v(),
					t -> t.getTimeSpan().getStart().rawHour(),
					t -> t.getTimeSpan().getStart().minute(),
					t -> t.getTimeSpan().getEnd().rawHour(),
					t -> t.getTimeSpan().getEnd().minute())
			.containsExactly(
					tuple("001", 14, 0, 15, 0));
		assertThat(workSchedule.getSupportSchedule().getDetails())
			.extracting(
					t -> t.getSupportDestination(),
					t -> t.getSupportType(),
					t -> t.getTimeSpan().get().getStart().rawHour(),
					t -> t.getTimeSpan().get().getStart().minute(),
					t -> t.getTimeSpan().get().getEnd().rawHour(),
					t -> t.getTimeSpan().get().getEnd().minute())
			.containsExactly(
					tuple(recipient, SupportType.TIMEZONE, 13, 0, 17, 0));
	}
	
	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 応援する予定がない
	 */
	@Test
	public void testGetSupportInfoOfEmployee_not_support() {
		
		val affiliationInfo = AffiliationInforOfDailyAttdHelperInSchedule.createAffiliationInforOfDailyAttd( "workplaceId", Optional.empty() );//職場
		
		val workSchedule = WorkScheduleHelper.createWorkSchedule( "sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfo
				,	SupportSchedule.createWithEmptyList()//応援詳細リストがない
					);
		
		//act
		val result = workSchedule.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 25 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceId" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType() ).isEmpty();
		assertThat( result.getRecipientList() ).isEmpty();
		
	}
	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 終日で応援する予定
	 */
	@Test
	public void testGetSupportInfoOfEmployee_support_all_day() {
		
		val affiliationInfo = AffiliationInforOfDailyAttdHelperInSchedule.createAffiliationInforOfDailyAttd( "workplaceId", Optional.empty() );//職場
		val recipient1 = TargetOrgIdenInfor.creatIdentifiWorkplace( "recipient_1" );
		
		val supportSchedule = new SupportSchedule(
				Arrays.asList(new SupportScheduleDetail(recipient1, SupportType.ALLDAY, Optional.empty()))
				);
		
		val workSchedule = WorkScheduleHelper.createWorkSchedule( "sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfo
				,	supportSchedule
					);
		
		//act
		val result = workSchedule.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 25 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceId" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.ALLDAY );
		assertThat( result.getRecipientList() )
		.extracting(	r -> r.getUnit()
					,	r -> r.getTargetId())
		.containsExactly( 
						tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_1" )
				);
	}
	
	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 時間帯で応援する予定
	 */
	@Test
	public void testGetSupportInfoOfEmployee_support_time_zone(
				@Injectable TimeSpanForCalc time1
			,	@Injectable TimeSpanForCalc time2) {
		
		val affiliationInfo = AffiliationInforOfDailyAttdHelperInSchedule.createAffiliationInforOfDailyAttd( "workplaceId", Optional.empty() );//職場
		val recipient1 = TargetOrgIdenInfor.creatIdentifiWorkplace( "recipient_1" );
		val recipient2 = TargetOrgIdenInfor.creatIdentifiWorkplace( "recipient_2" );
		val supportSchedule = new SupportSchedule( Arrays.asList(
				new SupportScheduleDetail( recipient1, SupportType.TIMEZONE, Optional.of( time1 ) )
			,	new SupportScheduleDetail( recipient2, SupportType.TIMEZONE, Optional.of( time2 ) )
				));
		
		val workSchedule = WorkScheduleHelper.createWorkSchedule( "sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfo
				,	supportSchedule
					);
		
		//act
		SupportInfoOfEmployee result = workSchedule.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 25 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceId" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getRecipientList() )
		.extracting(	r -> r.getUnit()
					,	r -> r.getTargetId())
		.containsExactly( 
						tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_1" )
					,	tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_2" )
				);
	}
}