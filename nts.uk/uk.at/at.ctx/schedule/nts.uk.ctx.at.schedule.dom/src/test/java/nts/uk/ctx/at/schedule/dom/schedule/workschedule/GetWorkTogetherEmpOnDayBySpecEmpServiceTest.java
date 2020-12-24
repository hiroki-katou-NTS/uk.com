package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferBySpecOrganizationService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class GetWorkTogetherEmpOnDayBySpecEmpServiceTest {
	
	@Injectable
	GetWorkTogetherEmpOnDayBySpecEmpService.Require require;
	
	@Mocked 
	GetTargetIdentifiInforService targetIndentifiServce;
	
	@Mocked 
	GetEmpCanReferBySpecOrganizationService empCanReferBySpeOrgService;
	
	@Test
	public void getWorkTogetherEmployeeOnDay(@Mocked 
			GetTargetIdentifiInforService targetOrgService, @Mocked 
			GetEmpCanReferBySpecOrganizationService empCanReferBySpeOrgService) {
		val sid = "sid";
		val targetOrg= TargetOrgIdenInfor.creatIdentifiWorkplace("wkplaceId");
		val empSameOrgs = Arrays.asList("sid_1", "sid_2", "sid_3", "sid_4");
		val baseDate = GeneralDate.today();
		List<WorkInfoOfDailyAttendance> workInfoDailyAttds = Helper.createWorkInfoOfDailyAttendanceList(Arrays.asList(
				new WorkInformation("01", "01"), new WorkInformation("02", "02"), new WorkInformation("03", "03")));
		val workSchedule1 = Helper.createWorkSchedule("sid_1", baseDate, workInfoDailyAttds.get(0));
		val workSchedule2 = Helper.createWorkSchedule("sid_2", baseDate, workInfoDailyAttds.get(1));
		val workSchedule3 = Helper.createWorkSchedule("sid_3", baseDate, workInfoDailyAttds.get(2));
		
		new Expectations(targetOrg, empSameOrgs, workSchedule1, workSchedule2, workSchedule3) {
			{
				targetOrgService.get(require, baseDate, sid);
				result = targetOrg;
				
				empCanReferBySpeOrgService.getListEmpID(require, baseDate, sid, targetOrg);
				result = empSameOrgs;
				
				require.getWorkSchedule(empSameOrgs, (DatePeriod) any);
				result = Arrays.asList(workSchedule1, workSchedule2, workSchedule3);
			}
			
		};
		
		val result = GetWorkTogetherEmpOnDayBySpecEmpService.get(require, sid, baseDate);
		assertThat(result).isEmpty();
		
		
	}
	
	public static class Helper{
		public static WorkSchedule createWorkSchedule(String sid, GeneralDate date, WorkInfoOfDailyAttendance workInfo) {
			return new WorkSchedule(sid, date, ConfirmedATR.CONFIRMED
					, workInfo, null
					, Collections.emptyList(), Collections.emptyList()
					, Optional.empty(), Optional.empty()
					, Optional.empty());
		}

		public static WorkInfoOfDailyAttendance createWorkInfoOfDailyAttendance(WorkInformation recordInfo) {
			return new WorkInfoOfDailyAttendance(
					  recordInfo
					, recordInfo
					, CalculationState.Calculated
					, NotUseAttribute.Not_use
					, NotUseAttribute.Not_use
					, DayOfWeek.FRIDAY, Collections.emptyList());
		}
		
		public static List<WorkInfoOfDailyAttendance> createWorkInfoOfDailyAttendanceList(List<WorkInformation> workInfos){
			return workInfos.stream().map(c -> Helper.createWorkInfoOfDailyAttendance(c)).collect(Collectors.toList());
		}
	}
	
	
}
