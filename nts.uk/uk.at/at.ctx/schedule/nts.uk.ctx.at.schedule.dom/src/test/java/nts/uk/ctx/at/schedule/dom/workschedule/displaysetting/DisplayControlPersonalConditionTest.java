package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
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
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition.Require;
import nts.uk.shr.com.enumcommon.NotUseAtr;


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
		List<PersonInforDisplayControl> result = Arrays.asList(
				new PersonInforDisplayControl(
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.INSURANCE_STATUS.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(NotUseAtr.USE.value, NotUseAtr.class)),
				new PersonInforDisplayControl(
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.TEAM.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(NotUseAtr.USE.value, NotUseAtr.class)),
				new PersonInforDisplayControl(
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.RANK.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(NotUseAtr.USE.value, NotUseAtr.class)),
				new PersonInforDisplayControl(
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.QUALIFICATION.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(NotUseAtr.USE.value, NotUseAtr.class)));
		NtsAssert.
		businessException("Msg_1682", 
				() -> { DisplayControlPersonalCondition.get(
				"cid", 
				result,
				Optional.empty());
		});}
	
	@Test
	public void test_msg_1786(){
		NtsAssert.
		businessException("Msg_1786", 
				() -> { WorkscheQualifi.workScheduleQualification(
						new PersonSymbolQualify("1"), 
						new ArrayList<>());});}

	@Test
	public void test_getSucces(){
		 List<QualificationCD> listQualificationCD = Arrays.asList(new QualificationCD("1"), new QualificationCD("2"));
		WorkscheQualifi qualifi = WorkscheQualifi.workScheduleQualification(new PersonSymbolQualify("1"), listQualificationCD);
		
		List<PersonInforDisplayControl> result = Arrays.asList(
				new PersonInforDisplayControl(
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.QUALIFICATION.value, ConditionATRWorkSchedule.class),
						EnumAdaptor.valueOf(ConditionATRWorkSchedule.TEAM.value, NotUseAtr.class)));
		
		DisplayControlPersonalCondition condition = DisplayControlPersonalCondition.get(
				"cid", result, Optional.ofNullable(qualifi));
		assertThat(condition.getCompanyID().equals("cid")).isTrue();
		assertThat(condition.getOtpWorkscheQualifi().get().getQualificationMark().v().equals("1")).isTrue();
		assertThat(condition.getOtpWorkscheQualifi().get().getListQualificationCD().isEmpty()).isFalse();
		assertThat(condition.getListConditionDisplayControl()).extracting(x-> x.getConditionATR().value,
				x-> x.getDisplayCategory().value)
		.containsExactly(tuple(3,1));
	}
	
	@Test
	public void test_acquireInforDisplayControlPersonalCondition(){
		List<String> listEmp = Arrays.asList("003","004"); // dummy

		// ------------------------- Mocking ↓
		List<EmpTeamInfor> infors = listEmp.stream().map(x-> new EmpTeamInfor(
				x, Optional.ofNullable(new ScheduleTeamCd(x + "EmpTeamInforCode")), 
				Optional.ofNullable(new ScheduleTeamName(x == "003" ? "" : x + "EmpTeamInforName"))
				)).collect(Collectors.toList());
		
		new MockUp<GetScheduleTeamInfoService>() {
			@Mock
			public List<EmpTeamInfor> get(GetScheduleTeamInfoService.Require require, List<String> lstEmpId) {
				return infors;
			}
		};
		
		// ------------------------- Mocking ↓
		List<EmpRankInfor> lstRank = listEmp.stream().map(x-> new EmpRankInfor
				(x, new RankCode(x + "RankCode"), new RankSymbol(x == "003" ? "" : x + "RankSymbol"))).collect(Collectors.toList());
		new MockUp<GetEmRankInforService>() {
			@Mock
			public List<EmpRankInfor> get(GetEmRankInforService.Require require, List<String> listEmp) {
				return lstRank;
			}
		};
		
		// ------------------------- Mocking ↓
		List<EmpLicenseClassification> lstEmpLicense = listEmp.stream().map(x-> new EmpLicenseClassification
				(x, Optional.of(LicenseClassification.valueOf(LicenseClassification.NURSE.value)))).collect(Collectors.toList());
		
		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require, GeneralDate generalDate, List<String> listEmp) {
				return lstEmpLicense;
			}
		};
		
		DisplayControlPersonalCondition data = new DisplayControlPersonalCondition("",  new ArrayList<>(), Optional.empty()); // dummy
		List<PersonalCondition> list = data.acquireInforDisplayControlPersonalCondition(require, GeneralDate.today(), listEmp);
		assertThat(list).extracting(x-> x.getEmpId(),
				x-> x.getOptLicenseClassification().get().name(),
				x-> x.getOptRankSymbol().get(),
				x-> x.getTeamName().get())
		.containsExactly(tuple("003","NURSE", "", "") , 
						tuple("004","NURSE", "004RankSymbol", "004EmpTeamInforName"));}
}


