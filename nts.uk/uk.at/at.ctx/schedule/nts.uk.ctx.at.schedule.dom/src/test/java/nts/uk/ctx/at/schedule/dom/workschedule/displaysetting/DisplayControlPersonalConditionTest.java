package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition.Require;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class DisplayControlPersonalConditionTest {

	@Injectable
	private Require require;

	@Test
	public void getter() {
		NtsAssert.invokeGetters(DisplayControlPersonalConditionHelper.getData());
	}

	@Test
	public void get_Have_Msg() {
		List<PersonInforDisplayControl> result = Arrays.asList(
				new PersonInforDisplayControl(ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.USE));
		NtsAssert.businessException("Msg_1682", () -> {
			DisplayControlPersonalCondition.get("cid", result, Optional.empty());
		});
	}

	@Test
	public void test_msg_1786() {
		NtsAssert.businessException("Msg_1786", () -> {
			WorkscheQualifi.workScheduleQualification(new PersonSymbolQualify("1"), new ArrayList<>());
		});
	}

	@Test
	public void test_getSucces() {
		List<QualificationCD> listQualificationCD = Arrays.asList(new QualificationCD("1"), new QualificationCD("2"));
		WorkscheQualifi qualifi = WorkscheQualifi.workScheduleQualification(new PersonSymbolQualify("1"),
				listQualificationCD);

		List<PersonInforDisplayControl> result = Arrays
				.asList(new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.USE));

		DisplayControlPersonalCondition condition = DisplayControlPersonalCondition.get("cid", result,
				Optional.ofNullable(qualifi));
		assertThat(condition.getCompanyID().equals("cid")).isTrue();
		assertThat(condition.getOtpWorkscheQualifi().get().getQualificationMark().v().equals("1")).isTrue();
		assertThat(condition.getOtpWorkscheQualifi().get().getListQualificationCD().isEmpty()).isFalse();
		assertThat(condition.getListConditionDisplayControl())
				.extracting(x -> x.getConditionATR(), x -> x.getDisplayCategory())
				.containsExactly(tuple(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.USE));
	}

	@Test
	public void test_acquireInforDisplayControlPersonalCondition() {
		List<String> listEmp = Arrays.asList("001", "002", "003", "004"); // dummy

		// ------------------------- Mocking ↓
		List<EmpTeamInfor> infors = Arrays.asList(
				EmpTeamInfor.create("001", new ScheduleTeamCd("Team01"), new ScheduleTeamName("TeamName01")),
				EmpTeamInfor.create("002", new ScheduleTeamCd("Team02"), new ScheduleTeamName("TeamName02")),
				EmpTeamInfor.create("003", new ScheduleTeamCd("Team03"), new ScheduleTeamName("TeamName03")),
				EmpTeamInfor.get("004"));

		new MockUp<GetScheduleTeamInfoService>() {
			@Mock
			public List<EmpTeamInfor> get(GetScheduleTeamInfoService.Require require, List<String> lstEmpId) {
				return infors;
			}
		};

		// ------------------------- Mocking ↓
		List<EmpRankInfor> lstRank = Arrays.asList(
				EmpRankInfor.create("001", new RankCode("Rank01"), new RankSymbol("RankSymbol01")),
				EmpRankInfor.create("002", new RankCode("Rank02"), new RankSymbol("RankSymbol02")),
				EmpRankInfor.makeWithoutRank("003"),
				EmpRankInfor.create("004", new RankCode("Rank04"), new RankSymbol("RankSymbol04")));

		new MockUp<GetEmRankInforService>() {
			@Mock
			public List<EmpRankInfor> get(GetEmRankInforService.Require require, List<String> listEmp) {
				return lstRank;
			}
		};

		// ------------------------- Mocking ↓

		List<EmpLicenseClassification> lstEmpLicense = Arrays.asList(
				EmpLicenseClassification.get("001", LicenseClassification.NURSE),
				EmpLicenseClassification.empLicenseClassification("002"),
				EmpLicenseClassification.get("003", LicenseClassification.NURSE_ASSOCIATE),
				EmpLicenseClassification.get("004", LicenseClassification.NURSE_ASSIST));

		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require,
					GeneralDate generalDate, List<String> listEmp) {
				return lstEmpLicense;
			}
		};

		DisplayControlPersonalCondition data = DisplayControlPersonalCondition.get("companyID",
				Arrays.asList(new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.USE),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE)),
				Optional.of(WorkscheQualifi.workScheduleQualification(new PersonSymbolQualify("〇"),
						Arrays.asList(new QualificationCD("C1")))));

		List<PersonalCondition> list = data.acquireInforDisplayControlPersonalCondition(require, GeneralDate.today(),
				listEmp);

		assertThat(list)
				.extracting(x -> x.getEmpId(), x -> x.getTeamName(), x -> x.getOptRankSymbol(),
						x -> x.getOptLicenseClassification())
				.containsExactly(
						tuple("001", Optional.of("TeamName01"), Optional.of("RankSymbol01"),
								Optional.of(LicenseClassification.NURSE)),
						tuple("002", Optional.of("TeamName02"), Optional.of("RankSymbol02"), Optional.empty()),
						tuple("003", Optional.of("TeamName03"), Optional.empty(),
								Optional.of(LicenseClassification.NURSE_ASSOCIATE)),
						tuple("004", Optional.empty(), Optional.of("RankSymbol04"),
								Optional.of(LicenseClassification.NURSE_ASSIST)));

	}
}
