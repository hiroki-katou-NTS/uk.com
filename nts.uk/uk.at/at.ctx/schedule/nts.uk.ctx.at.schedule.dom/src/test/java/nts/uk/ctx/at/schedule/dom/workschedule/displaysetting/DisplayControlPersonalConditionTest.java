package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition.Require;


@RunWith(JMockit.class)
public class DisplayControlPersonalConditionTest {

	@Injectable
	private Require require;
	
	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(DisplayControlPersonalConditionHelper.getData());
	}
	@Test
	public void get_Have_Msg() {
		NtsAssert.
		businessException("Msg_1682", 
				() -> { DisplayControlPersonalCondition.get(
				"cid", 
				DisplayControlPersonalConditionHelper.getListPersonInfor(),
				Optional.empty());
		});}

	@Test
	public void getSucces(){
		DisplayControlPersonalCondition condition = DisplayControlPersonalCondition.get(
				"cid", DisplayControlPersonalConditionHelper.getListPersonSucces(), DisplayControlPersonalConditionHelper.getWorkscheQualifi());
		assertNotNull(condition);
	}
	
	@Test
	public void acquireInforDisplayControlPersonalCondition(){
		List<String> listEmp = Arrays.asList("003","004"); // dummy

		// ------------------------- Mocking ↓
		List<EmpTeamInfor> infors = listEmp.stream().map(x-> new EmpTeamInfor(
				x, Optional.ofNullable(new ScheduleTeamCd(x + "EmpTeamInforCode")), Optional.ofNullable(new ScheduleTeamName(x + "EmpTeamInforName")))).collect(Collectors.toList());
		new MockUp<GetScheduleTeamInfoService>() {
			@Mock
			public List<EmpTeamInfor> get(GetScheduleTeamInfoService.Require require, List<String> lstEmpId) {
				return infors;
			}
		};
		
		// ------------------------- Mocking ↓
		List<EmpRankInfor> lstRank = listEmp.stream().map(x-> new EmpRankInfor(x, new RankCode(x + "RankCode"), new RankSymbol(x + "RankSymbol"))).collect(Collectors.toList());
		new MockUp<GetEmRankInforService>() {
			@Mock
			public List<EmpRankInfor> get(GetEmRankInforService.Require require, List<String> listEmp) {
				return lstRank;
			}
		};
		
		// ------------------------- Mocking ↓
		List<EmpLicenseClassification> lstEmpLicense = listEmp.stream().map(x-> new EmpLicenseClassification(x, Optional.of(LicenseClassification.valueOf(LicenseClassification.NURSE.value)))).collect(Collectors.toList());
		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require, GeneralDate generalDate, List<String> listEmp) {
				return lstEmpLicense;
			}
		};
		List<PersonalCondition> list = DisplayControlPersonalCondition.acquireInforDisplayControlPersonalCondition(require, GeneralDate.today(), listEmp);
		assertThat(list).extracting(x-> x.getEmpId(),
				x-> x.getOptLicenseClassification().get().name(),
				x-> x.getOptRankSymbol().get(),
				x-> x.getTeamName().get())
		.containsExactly(tuple("003","NURSE", "003RankSymbol", "003EmpTeamInforName") , 
						tuple("004","NURSE", "004RankSymbol", "004EmpTeamInforName"));}
}


