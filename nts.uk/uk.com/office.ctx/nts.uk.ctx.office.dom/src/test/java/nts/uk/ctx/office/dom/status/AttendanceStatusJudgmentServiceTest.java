//package nts.uk.ctx.office.dom.status;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import lombok.val;
//import mockit.Expectations;
//import mockit.Injectable;
//import mockit.Mocked;
//import mockit.integration.junit4.JMockit;
//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.office.dom.dto.DailyWorkDto;
//import nts.uk.ctx.office.dom.dto.TimeLeavingOfDailyPerformanceDto;
//import nts.uk.ctx.office.dom.dto.WorkInfoOfDailyPerformanceDto;
//import nts.uk.ctx.office.dom.dto.WorkTypeDto;
//import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
//import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService;
//import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService.Required;
//
//@RunWith(JMockit.class)
//public class AttendanceStatusJudgmentServiceTest {
//	@Injectable
//	private Required rq;
//	
//	@Mocked
//	private String sId = "mock-sid";
//	
//	
//	@Test
//	public void testCase1() {
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		GeneralDate date = GeneralDate.ymd(2020, 11, 11);
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, date);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, date);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, date, Optional.empty(), Optional.empty(), Optional.empty());
//		assertThat(res).isEmpty();
//	}
//	
//	@Test
//	public void testCase2() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getGoOutInfo(0000, 2359));
//		Optional<ActivityStatus> status = Optional.empty();
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.empty());
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_OUT);
//	}
//	
//	@Test
//	public void testCase3() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getGoOutInfo(0000, 2359));
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.leaveTime(2359)
//				.build();
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.empty());
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_OUT);
//	}
//	
//	@Test
//	public void testCase4() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getStatus(0));
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.empty());
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.NOT_PRESENT);
//	}
//
//	@Test
//	public void testCase5() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.empty());
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.NOT_PRESENT);
//	}
//	
//	@Test
//	public void testCase6() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getStatus(1));
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.PRESENT);
//	}
//	
//	@Test
//	public void testCase7() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.HOLIDAY);
//	}
//	
//	@Test
//	public void testCase8() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.leaveTime(0000)
//				.attendanceTime(2349)
//				.build();
//		
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase9() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(0000)
//				.build();
//		
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase10() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.leaveTime(0000)
//				.build();
//		
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase11() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(2359)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.HOLIDAY);
//	}
//	
//	@Test
//	public void testCase12() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getStatus(3));
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(2359)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase13() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(2359)
//				.leaveTime(9999)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(8)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.PRESENT);
//	}
//	
//	@Test
//	public void testCase14() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getStatus(2));
//	
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_OUT);
//	}
//	
//	@Test
//	public void testCase15() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//	
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.empty(), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.NOT_PRESENT);
//	}
//	
//	@Test
//	public void testCase16() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.leaveTime(0000)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, Optional.empty(), Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase17() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		Optional<WorkInfoOfDailyPerformanceDto> workInfo = Optional.of(WorkInfoOfDailyPerformanceDto
//				.builder()
//				.backStraightAtr(1)
//				.build());
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(0000)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, workInfo, Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_OUT);
//	}
//	
//	@Test
//	public void testCase18() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		Optional<WorkInfoOfDailyPerformanceDto> workInfo = Optional.of(WorkInfoOfDailyPerformanceDto
//				.builder()
//				.backStraightAtr(2)
//				.build());
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(0000)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, workInfo, Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.NOT_PRESENT);
//	}
//	
//	@Test
//	public void testCase19() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.of(AttendanceStatusJudgmentServiceTestHelper.getStatus(3));
//		Optional<WorkInfoOfDailyPerformanceDto> workInfo = Optional.of(WorkInfoOfDailyPerformanceDto
//				.builder()
//				.backStraightAtr(2)
//				.build());
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(9999)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, workInfo, Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_HOME);
//	}
//	
//	@Test
//	public void testCase20() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		Optional<WorkInfoOfDailyPerformanceDto> workInfo = Optional.of(WorkInfoOfDailyPerformanceDto
//				.builder()
//				.backStraightAtr(1)
//				.build());
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(9999)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, workInfo, Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.GO_OUT);
//	}
//	
//	@Test
//	public void testCase21() {
//		GeneralDate baseDate = GeneralDate.today();
//		Optional<GoOutEmployeeInformation> goOutInfo = Optional.empty();
//		Optional<ActivityStatus> status = Optional.empty();
//		Optional<WorkInfoOfDailyPerformanceDto> workInfo = Optional.of(WorkInfoOfDailyPerformanceDto
//				.builder()
//				.backStraightAtr(2)
//				.build());
//		TimeLeavingOfDailyPerformanceDto timeLeave = TimeLeavingOfDailyPerformanceDto.builder()
//				.attendanceTime(9999)
//				.build();
//		WorkTypeDto workType = WorkTypeDto.builder().build();
//		DailyWorkDto dailyWorkDto = DailyWorkDto.builder()
//				.oneDay(7)
//				.build();
//		workType.setDailyWork(dailyWorkDto);
//		
//		new Expectations() {
//			{
//				rq.getGoOutEmployeeInformation(sId, baseDate);
//				result = goOutInfo;
//			}
//			{
//				rq.getActivityStatus(sId, baseDate);
//				result = status;
//			}
//		};
//		
//		val res = AttendanceStatusJudgmentService.getActivityStatus(rq, sId, baseDate, workInfo, Optional.of(timeLeave), Optional.of(workType));
//		assertThat(res).isNotEmpty();
//		assertThat(res.get().getActivity()).isEqualTo(StatusClassfication.PRESENT);
//	}
//	
//}
