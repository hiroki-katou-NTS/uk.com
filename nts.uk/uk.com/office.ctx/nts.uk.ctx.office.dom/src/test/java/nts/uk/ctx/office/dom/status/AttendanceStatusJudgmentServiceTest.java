package nts.uk.ctx.office.dom.status;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.status.adapter.AttendanceStateImport;
import nts.uk.ctx.office.dom.status.service.AttendanceAccordActualData;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService.Required;

@RunWith(JMockit.class)
public class AttendanceStatusJudgmentServiceTest {
	
	@Injectable
	private Required require;
	
	/**
	 * case 1
	 * 
	 * goout.isPresent()
	 * getGoOutTime().EqualTo(now)
	 * getComebackTime().EqualTo(now)
	 */
	@Test
	public void getActivityStatusTest1_1() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
				.goOutTime(now)
				.comebackTime(now)
				.build();
		Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 1
	 * 
	 * goout.isPresent()
	 * getGoOutTime().lessThan(now)
	 * getComebackTime().EqualTo(now)
	 */
	@Test
	public void getActivityStatusTest1_2() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
				.goOutTime(now-1000)
				.comebackTime(now)
				.build();
		Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 1
	 * 
	 * goout.isPresent()
	 * getGoOutTime().EqualTo(now)
	 * getComebackTime().greaterThan(now)
	 */
	@Test
	public void getActivityStatusTest1_3() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
				.goOutTime(now)
				.comebackTime(now+1000)
				.build();
		Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 1
	 * 
	 * goout.isPresent()
	 * getGoOutTime().lessThan(now)
	 * getComebackTime().greaterThan(now)
	 */
	@Test
	public void getActivityStatusTest1_4() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
				.goOutTime(now-100)
				.comebackTime(now+1000)
				.build();
		Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 2
	 */
	@Test
	public void getActivityStatusTest2_1() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_HOME)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = Optional.empty();
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 2
	 * 
	 * goout.isPresent()
	 * getGoOutTime().greaterThan(now)
	 */
	@Test
	public void getActivityStatusTest2_2() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
				Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
				GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
						.goOutTime(now+1000)
						.comebackTime(now+2000)
						.build();
				Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_HOME)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 2
	 * 
	 * goout.isPresent()
	 * getComebackTime().lessThan(now)
	 */
	@Test
	public void getActivityStatusTest2_3() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-1]
				Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
				GoOutEmployeeInformationDto gooutDto = GoOutEmployeeInformationDto.builder()
						.goOutTime(now)
						.comebackTime(now-1000)
						.build();
				Optional<GoOutEmployeeInformation> gooutResult = Optional.ofNullable(GoOutEmployeeInformation.createFromMemento(gooutDto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_HOME)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = gooutResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 3
	 */
	@Test
	public void getActivityStatusTest3() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-2]
		ActivityStatusDto dto = ActivityStatusDto.builder()
				.activity(2)
				.date(GeneralDate.today())
				.sid("sid")
				.build();
		Optional<ActivityStatus> statusResult = Optional.ofNullable(ActivityStatus.createFromMemento(dto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.PRESENT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = Optional.empty();
			}
			{
				require.getActivityStatus(sid, baseDate);
				result = statusResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 4
	 */
	@Test
	public void getActivityStatusTest4() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		//[R-2]
		ActivityStatusDto dto = ActivityStatusDto.builder()
				.activity(0)
				.date(GeneralDate.today())
				.sid("sid")
				.build();
		Optional<ActivityStatus> statusResult = Optional.ofNullable(ActivityStatus.createFromMemento(dto));
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = Optional.empty();
			}
			{
				require.getActivityStatus(sid, baseDate);
				result = statusResult;
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(statusResult.get().getActivity())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 5
	 */
	@Test
	public void getActivityStatusTest5() {
		//given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		
		//[R-3]
		AttendanceStateImport attendacneRqResult = AttendanceStateImport.builder()
				.attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false)
				.build();
		
		new Expectations() {
			{
				require.getGoOutEmployeeInformation(sid, baseDate);
				result = Optional.empty();
			}
			{
				require.getActivityStatus(sid, baseDate);
				result = Optional.empty();
			}
			{
				require.getAttendace(sid);
				result = attendacneRqResult;
			}
		};
		
		//when
		val result = AttendanceStatusJudgmentService.getActivityStatus(require, sid);
		
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(attendacneRqResult.getAttendanceState())
				.workingNow(attendacneRqResult.isWorkingNow())
				.build();
		
		//then
		assertThat(result).isEqualTo(expected);
	}
}
