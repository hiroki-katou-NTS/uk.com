package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText.Builder;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.CreateWorkSchedule.WorkTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule.Require;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ClockAreaAtr;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ContainsResult;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(Enclosed.class)
public class CreateWorkScheduleTest {
	
	public static class TestGetErrorInfo {
		
		@Injectable
		CreateWorkSchedule.Require require;
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testGetErrorInfo_empty_case2(
				@Injectable WorkInformation workInformation
				) {

			new Expectations() {{
				workInformation.containsOnChangeableWorkingTime(require, anyString, (ClockAreaAtr) any, (WorkNo) any, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			updateInfoMap.put(31, (T) new TimeWithDayAttr(31));
			
			Optional<ErrorInfoOfWorkSchedule> result = NtsAssert.Invoke.staticMethod(CreateWorkSchedule.class, "getErrorInfoWithWorkTimeZone", 
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					WorkTimeZone.START_TIME_1,
					updateInfoMap
					);
			
			assertThat( result ).isEmpty();
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testGetErrorInfo_successfully(
				@Injectable WorkInformation workInformation,
				@Mocked Builder builder
				) {
			new Expectations() {{
				
				workInformation.containsOnChangeableWorkingTime(require, anyString, (ClockAreaAtr) any, (WorkNo) any, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				builder.build().buildMessage();
				result = "msg";
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			updateInfoMap.put( WS_AttendanceItem.StartTime1.ID, (T) new TimeWithDayAttr(31));
			Optional<ErrorInfoOfWorkSchedule> result = NtsAssert.Invoke.staticMethod(CreateWorkSchedule.class, "getErrorInfoWithWorkTimeZone", 
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					WorkTimeZone.START_TIME_1,
					updateInfoMap
					);
			
			assertThat( result.get().getEmployeeId() ).isEqualTo( "empId" );
			assertThat( result.get().getDate() ).isEqualTo( GeneralDate.ymd( 2020, 11, 1) );
			assertThat( result.get().getAttendanceItemId().get() ).isEqualTo( WS_AttendanceItem.StartTime1.ID );
			assertThat( result.get().getErrorMessage() ).isEqualTo( "msg");
			
		}
	}
	
	public static class TestCheckTimeSpan {
		
		@Injectable
		CreateWorkSchedule.Require require;
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCheckTimeSpan(@Injectable WorkInformation workInformation, @Mocked Builder builder) {
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put( WS_AttendanceItem.EndTime1.ID, (T) new TimeWithDayAttr(34));
			updateInfoMap.put( WS_AttendanceItem.StartTime2.ID, (T) new TimeWithDayAttr(41));
			updateInfoMap.put( WS_AttendanceItem.EndTime2.ID, (T) new TimeWithDayAttr(44));
			
			new Expectations() {{
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_1.clockArea, WorkTimeZone.START_TIME_1.workNo, (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_1.clockArea, WorkTimeZone.END_TIME_1.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目41
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_2.clockArea, WorkTimeZone.START_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目44
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_2.clockArea, WorkTimeZone.END_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				builder.build().buildMessage();
				result = "msg";
			}};
			
			List<ErrorInfoOfWorkSchedule> result = NtsAssert.Invoke.staticMethod(CreateWorkSchedule.class, "checkTimeSpan", 
					require,
					"cmpId",
					"empId",
					GeneralDate.ymd(2020, 11, 1),
					workInformation,
					updateInfoMap);
			
			assertThat( result )
				.extracting( 
					e -> e.getEmployeeId(),
					e -> e.getDate(),
					e -> e.getAttendanceItemId().get(),
					e -> e.getErrorMessage())
				.containsExactly( 
						tuple("empId", GeneralDate.ymd(2020, 11, 1), WS_AttendanceItem.StartTime2.ID, "msg"),
						tuple("empId", GeneralDate.ymd(2020, 11, 1), WS_AttendanceItem.EndTime2.ID, "msg"));
		}
	}
	
	public static class TestCreate {
		
		@Injectable
		CreateWorkSchedule.Require require;
		
		@Test
		public <T> void testCreate_BusinessException(
				@Injectable WorkInformation workInformation,
				@Mocked BusinessException businessException,
				@Mocked Builder builder) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				//result = empty
				
				businessException.getMessage();
				result = "message content";
			}};

			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					new HashMap<>());
			
			assertThat( result.getAtomTask() ).isEmpty();
			assertThat( result.getErrorInformation())
				.extracting( 
						e -> e.getEmployeeId(),
						e -> e.getDate(),
						e -> e.getAttendanceItemId(),
						e -> e.getErrorMessage())
				.containsExactly( 
					tuple(
						"empId", 
						GeneralDate.ymd(2020, 11, 1), 
						Optional.empty(), 
						"message content"));
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_isNewRegister_hasError(
				@Injectable WorkInformation workInformation,
				@Injectable WorkInfoOfDailyAttendance workInfo,
				@Injectable AffiliationInforOfDailyAttd affInfo,
				@Mocked Builder builder) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				//result = empty
			}};
			
			WorkSchedule workSchedule = new WorkSchedule(
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					ConfirmedATR.UNSETTLED, 
					workInfo, 
					affInfo, 
					new BreakTimeOfDailyAttd(), 
					new ArrayList<>(),
					TaskSchedule.createWithEmptyList(),
					SupportSchedule.createWithEmptyList(),
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(),
					Optional.empty());
			
			new MockUp<WorkSchedule>() {
				@Mock
				public WorkSchedule createByHandCorrectionWithWorkInformation(Require require,
						String companyId,
						String employeeId,
						GeneralDate date,
						WorkInformation workInformation) {
					
					return workSchedule;
				}
			};
			
			new Expectations() {{
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_1.clockArea, WorkTimeZone.START_TIME_1.workNo, (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_1.clockArea, WorkTimeZone.END_TIME_1.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目41
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_2.clockArea, WorkTimeZone.START_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目44
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_2.clockArea, WorkTimeZone.END_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				builder.build().buildMessage();
				result = "msg";
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put( WS_AttendanceItem.EndTime1.ID, (T) new TimeWithDayAttr(34));
			updateInfoMap.put( WS_AttendanceItem.StartTime2.ID, (T) new TimeWithDayAttr(41));
			updateInfoMap.put( WS_AttendanceItem.EndTime2.ID, (T) new TimeWithDayAttr(44));
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					updateInfoMap);
			
			assertThat( result.getAtomTask() ).isEmpty();
			assertThat( result.getErrorInformation())
				.extracting( 
						e -> e.getEmployeeId(),
						e -> e.getDate(),
						e -> e.getAttendanceItemId(),
						e -> e.getErrorMessage())
				.containsExactly( 
					tuple(
						"empId", 
						GeneralDate.ymd(2020, 11, 1), 
						Optional.of(WS_AttendanceItem.StartTime2.ID), 
						"msg"),
					tuple(
						"empId",
						GeneralDate.ymd(2020, 11, 1),
						Optional.of(WS_AttendanceItem.EndTime2.ID),
						"msg"));
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_isNotNewRegister_workInfoNotSame_hasError(
				@Injectable WorkInformation workInformation,
				@Mocked WorkSchedule workSchedule,
				@Mocked Builder builder) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				result = Optional.of(workSchedule);
				
				workSchedule.getWorkInfo().getRecordInfo().isSame( (WorkInformation) any );
				result = false;
			}};
			
			new Expectations() {{
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_1.clockArea, WorkTimeZone.START_TIME_1.workNo, (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_1.clockArea, WorkTimeZone.END_TIME_1.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目41
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_2.clockArea, WorkTimeZone.START_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目44
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_2.clockArea, WorkTimeZone.END_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				builder.build().buildMessage();
				result = "msg";
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put( WS_AttendanceItem.EndTime1.ID, (T) new TimeWithDayAttr(34));
			updateInfoMap.put( WS_AttendanceItem.StartTime2.ID, (T) new TimeWithDayAttr(41));
			updateInfoMap.put( WS_AttendanceItem.EndTime2.ID, (T) new TimeWithDayAttr(44));
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					updateInfoMap);
			
			assertThat( result.getAtomTask() ).isEmpty();
			assertThat( result.getErrorInformation())
				.extracting( 
						e -> e.getEmployeeId(),
						e -> e.getDate(),
						e -> e.getAttendanceItemId(),
						e -> e.getErrorMessage())
				.containsExactly( 
					tuple(
						"empId", 
						GeneralDate.ymd(2020, 11, 1), 
						Optional.of(WS_AttendanceItem.StartTime2.ID), 
						"msg"),
					tuple(
						"empId",
						GeneralDate.ymd(2020, 11, 1),
						Optional.of(WS_AttendanceItem.EndTime2.ID),
						"msg"));
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_isNotNewRegister_workInfoSame_hasError(
				@Injectable WorkInformation workInformation,
				@Injectable WorkSchedule workSchedule,
				@Mocked Builder builder) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				result = Optional.of(workSchedule);
				
				workSchedule.getWorkInfo().getRecordInfo().isSame( (WorkInformation) any );
				result = true;
			}};
			
			new Expectations() {{
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_1.clockArea, WorkTimeZone.START_TIME_1.workNo, (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_1.clockArea, WorkTimeZone.END_TIME_1.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目41
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_2.clockArea, WorkTimeZone.START_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				// 勤怠項目44
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_2.clockArea, WorkTimeZone.END_TIME_2.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( false, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				builder.build().buildMessage();
				result = "msg";
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put( WS_AttendanceItem.EndTime1.ID, (T) new TimeWithDayAttr(34));
			updateInfoMap.put( WS_AttendanceItem.StartTime2.ID, (T) new TimeWithDayAttr(41));
			updateInfoMap.put( WS_AttendanceItem.EndTime2.ID, (T) new TimeWithDayAttr(44));
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					updateInfoMap);
			
			assertThat( result.getAtomTask() ).isEmpty();
			assertThat( result.getErrorInformation())
				.extracting( 
						e -> e.getEmployeeId(),
						e -> e.getDate(),
						e -> e.getAttendanceItemId(),
						e -> e.getErrorMessage())
				.containsExactly( 
					tuple(
						"empId", 
						GeneralDate.ymd(2020, 11, 1), 
						Optional.of(WS_AttendanceItem.StartTime2.ID), 
						"msg"),
					tuple(
						"empId",
						GeneralDate.ymd(2020, 11, 1),
						Optional.of(WS_AttendanceItem.EndTime2.ID),
						"msg"
							));
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_updateBreakTimeList(
				@Injectable WorkInformation workInformation,
				@Mocked WorkSchedule workSchedule) {
			
			List<TimeSpanForCalc> breakTimeList = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)),
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 0))
					);
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				result = Optional.of(workSchedule);
				
				workSchedule.handCorrectBreakTimeList(require, breakTimeList);
				times = 1;
				
				workSchedule.changeAttendanceItemValueByHandCorrection(require, (Map<Integer, T>) any);
			}};
			
			CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					true, // 休憩時間帯が手修正か: true
					breakTimeList,
					new ArrayList<>(),
					new HashMap<>());
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_notUpdateBreakTimeList(
				@Injectable WorkInformation workInformation,
				@Mocked WorkSchedule workSchedule) {
			
			List<TimeSpanForCalc> breakTimeList = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute(12, 0), TimeWithDayAttr.hourMinute(13, 0)),
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute(17, 30), TimeWithDayAttr.hourMinute(18, 0))
					);
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				result = Optional.of(workSchedule);
				
				workSchedule.handCorrectBreakTimeList(require, breakTimeList);
				times = 0;
				
				workSchedule.changeAttendanceItemValueByHandCorrection(require, (Map<Integer, T>) any);
			}};
			
			CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false, // 休憩時間帯が手修正か: false
					breakTimeList,
					new ArrayList<>(),
					new HashMap<>());
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_insert_successfull(
				@Injectable WorkInformation workInformation,
				@Mocked WorkSchedule workSchedule) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				// result = empty
				
			}};
			
			new Expectations() {{
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.START_TIME_1.clockArea, WorkTimeZone.START_TIME_1.workNo, (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(
						require, anyString, WorkTimeZone.END_TIME_1.clockArea, WorkTimeZone.END_TIME_1.workNo, (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				workSchedule.changeAttendanceItemValueByHandCorrection(require, (Map<Integer, T>) any);
				}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put( WS_AttendanceItem.EndTime1.ID, (T) new TimeWithDayAttr(34));
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					updateInfoMap);
			
			assertThat( result.getErrorInformation() ).isEmpty();
			NtsAssert.atomTask( () -> result.getAtomTask().get() , 
					any -> require.insertWorkSchedule(  any.get() ),
					any -> require.registerTemporaryData("emp", GeneralDate.ymd(2020, 11, 1))
					);
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_update_successfull(
				@Injectable WorkInformation workInformation,
				@Mocked WorkSchedule workSchedule
				) {
			
			new Expectations() {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				result = Optional.of(workSchedule);
				
				// 勤怠項目31
				workInformation.containsOnChangeableWorkingTime(require, anyString, ClockAreaAtr.START, new WorkNo(1), (TimeWithDayAttr) any);
				times = 0; 
				
				// 勤怠項目34
				workInformation.containsOnChangeableWorkingTime(require, anyString, ClockAreaAtr.END, new WorkNo(1), (TimeWithDayAttr) any);
				result = new ContainsResult( true, 
						Optional.of(new TimeSpanForCalc(
								new TimeWithDayAttr(1), 
								new TimeWithDayAttr(2))
								));
				
				workSchedule.changeAttendanceItemValueByHandCorrection(require, (Map<Integer, T>) any);
				
				require.correctWorkSchedule(workSchedule);
				result = workSchedule;
				
			}};
			
			Map<Integer, T> updateInfoMap = new HashMap<>();
			// updateInfoMap.put(31, value)
			updateInfoMap.put(34, (T) new TimeWithDayAttr(34));
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"cmpId",
					"empId", 
					GeneralDate.ymd(2020, 11, 1), 
					workInformation, 
					false,
					new ArrayList<>(),
					new ArrayList<>(),
					updateInfoMap);
			
			assertThat( result.getErrorInformation() ).isEmpty();
			NtsAssert.atomTask( () -> result.getAtomTask().get() , 
					any -> require.updateWorkSchedule( any.get() ),
					any -> require.registerTemporaryData("emp", GeneralDate.ymd(2020, 11, 1))
					);
			
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public <T> void testCreate_create_support_ticket_exception(
				@Injectable WorkInformation workInformation,
				@Injectable TargetOrgIdenInfor targetOrg) {
			
			String employeeId = "empId";
			GeneralDate date = GeneralDate.ymd(2020, 11, 1);
			WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(employeeId, date, 
					ConfirmedATR.UNSETTLED, 
					TaskSchedule.createWithEmptyList(), 
					SupportSchedule.createWithEmptyList());
			
			new Expectations(WorkSchedule.class) {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				// result = empty
				
				WorkSchedule.createByHandCorrectionWithWorkInformation(require, anyString, employeeId, date, workInformation);
				result = workSchedule;
				
				workSchedule.createSupportSchedule(require, (List<SupportTicket>) any);
				result = new BusinessException("message-id");
			}};
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"companyId",
					employeeId, 
					date, 
					workInformation, 
					false,
					new ArrayList<>(),
					Arrays.asList(new SupportTicket(new EmployeeId(employeeId), targetOrg, SupportType.ALLDAY, date, Optional.empty())),
					Collections.emptyMap() );
			
			// Assert
			assertThat(result.isHasError()).isTrue();
			assertThat(result.getErrorInformation())
				.extracting(
					e -> e.getEmployeeId(),
					e -> e.getDate(),
					e -> e.getAttendanceItemId(),
					e -> e.getErrorMessage() )
				.containsExactly(
					tuple(employeeId, date, Optional.empty(), "message-id"));
			assertThat(result.getAtomTask()).isEmpty();
			
			assertThat(workSchedule.getSupportSchedule().getDetails()).isEmpty();
			
		}
		
		@Test
		public <T> void testCreate_create_support_ticket_ok(
				@Injectable WorkInformation workInformation,
				@Injectable TargetOrgIdenInfor targetOrg) {
			
			String employeeId = "empId";
			GeneralDate date = GeneralDate.ymd(2020, 11, 1);
			WorkSchedule workSchedule = WorkScheduleHelper.createWithParams(employeeId, date, 
					ConfirmedATR.UNSETTLED, 
					TaskSchedule.createWithEmptyList(), 
					SupportSchedule.createWithEmptyList());
			
			new Expectations(WorkSchedule.class) {{
				require.getWorkSchedule(anyString, (GeneralDate) any);
				// result = empty
				
				WorkSchedule.createByHandCorrectionWithWorkInformation(require, anyString, employeeId, date, workInformation);
				result = workSchedule;
			}};
			
			ResultOfRegisteringWorkSchedule result = CreateWorkSchedule.create(
					require, 
					"companyId",
					employeeId, 
					date, 
					workInformation, 
					false,
					new ArrayList<>(),
					Arrays.asList(new SupportTicket(new EmployeeId(employeeId), targetOrg, SupportType.ALLDAY, date, Optional.empty())),
					Collections.emptyMap() );
			
			// Assert
			assertThat(workSchedule.getSupportSchedule().getDetails())
				.extracting(
					s -> s.getSupportDestination(),
					s -> s.getSupportType(),
					s -> s.getTimeSpan() )
				.containsExactly(
					tuple(targetOrg, SupportType.ALLDAY, Optional.empty())	);
			assertThat( result.getErrorInformation() ).isEmpty();
			NtsAssert.atomTask( () -> result.getAtomTask().get() , 
					any -> require.insertWorkSchedule(  any.get() ),
					any -> require.registerTemporaryData("emp", GeneralDate.ymd(2020, 11, 1))
					);
			
		}
	}

}
