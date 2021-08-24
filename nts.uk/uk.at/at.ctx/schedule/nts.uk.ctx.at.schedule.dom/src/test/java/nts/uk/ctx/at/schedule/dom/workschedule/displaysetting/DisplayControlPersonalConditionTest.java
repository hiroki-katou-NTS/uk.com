package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class DisplayControlPersonalConditionTest {

	@Injectable
	private DisplayControlPersonalCondition.Require require;

	@Test
	public void getter() {
		NtsAssert.invokeGetters( DisplayControlPersonalConditionHelper.defaultData() );
	}

	/**
	 * ・条件
	 * 		資格のするしない区分 = する
	 * 		資格設定.isEmpty
	 * ・期待
	 * 		BusinessException Msg_1682
	 */
	@Test
	public void get_Msg_1682() {
		
		List<PersonInforDisplayControl> controlList = 
				DisplayControlPersonalConditionHelper.createControlListWithQualificationAtr(NotUseAtr.USE);
		
		NtsAssert.businessException("Msg_1682", () -> {
			DisplayControlPersonalCondition.get("cid", controlList, Optional.empty());
		});
	}

	/**
	 * ・条件
	 * 		資格のするしない区分 = しない
	 * 		資格設定.isEmpty
	 * ・期待
	 * 		作成できる
	 */
	@Test
	public void test_getSuccess_qualification_NotUse () {
		
		List<PersonInforDisplayControl> controlList = 
				DisplayControlPersonalConditionHelper.createControlListWithQualificationAtr( NotUseAtr.NOT_USE );
		
		// run
		DisplayControlPersonalCondition result = 
				DisplayControlPersonalCondition.get("cid", controlList, Optional.empty());
		
		// assert
		assertThat( result.getCompanyID().equals("cid")).isTrue();
		assertThat( result.getListConditionDisplayControl() )
			.extracting( 
					e -> e.getConditionATR(), 
					e -> e.getDisplayCategory() )
			.contains( 
					tuple( ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.NOT_USE ));
		
		assertThat( result.getOtpWorkscheQualifi() ).isEmpty();
	}
	
	/**
	 * ・条件
	 * 		資格のするしない区分 = する
	 * 		資格設定.isPresent
	 * ・期待
	 * 		作成できる
	 */
	@Test
	public void test_getSuccess_qualification_Use () {
		
		List<PersonInforDisplayControl> controlList = 
				Arrays.asList(
						new PersonInforDisplayControl(ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.NOT_USE),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.NOT_USE),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.USE ),
						new PersonInforDisplayControl(ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE) );
		
		WorkscheQualifi workScheQualification = WorkscheQualifi.workScheduleQualification(
				new PersonSymbolQualify("x"), 
				Arrays.asList(
						new QualificationCD("01"), 
						new QualificationCD("02")));
		
		// run
		DisplayControlPersonalCondition result = 
				DisplayControlPersonalCondition.get("cid", controlList, Optional.of(workScheQualification));
		
		// assert
		assertThat( result.getCompanyID().equals("cid")).isTrue();
		assertThat( result.getListConditionDisplayControl())
			.extracting( 
					e -> e.getConditionATR(), 
					e -> e.getDisplayCategory() )
			.containsExactlyInAnyOrder( 
					tuple( ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.NOT_USE ),
					tuple( ConditionATRWorkSchedule.TEAM, NotUseAtr.NOT_USE ),
					tuple( ConditionATRWorkSchedule.RANK, NotUseAtr.USE ),
					tuple( ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.USE ),
					tuple( ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE ) );
		
		assertThat( result.getOtpWorkscheQualifi().get().getQualificationMark() ).isEqualTo( new PersonSymbolQualify("x") );
		assertThat( result.getOtpWorkscheQualifi().get().getListQualificationCD() )
			.containsExactlyInAnyOrder( 
					new QualificationCD("01"), 
					new QualificationCD("02") );
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
				EmpLicenseClassification.createEmpLicenseClassification("001", Helper.createNurseClassification(LicenseClassification.NURSE)),
				EmpLicenseClassification.createEmpLicenseClassification("002"),
				EmpLicenseClassification.createEmpLicenseClassification("003", Helper.createNurseClassification(LicenseClassification.NURSE_ASSOCIATE)),
				EmpLicenseClassification.createEmpLicenseClassification("004", Helper.createNurseClassification(LicenseClassification.NURSE_ASSIST))
				);

		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require,
					GeneralDate generalDate, List<String> listEmp) {
				return lstEmpLicense;
			}
		};

		DisplayControlPersonalCondition data = DisplayControlPersonalCondition.get("companyID",
				Arrays.asList(
					new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.USE),
					new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
					new PersonInforDisplayControl(ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE),
					new PersonInforDisplayControl(ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.NOT_USE),
					new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.NOT_USE) ),
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
	
	public static class Helper{
		/**
		 * 看護区分を作る
		 * @param nurseClassifiCode　看護区分コード
		 * @return
		 */
		public static NurseClassification createNurseClassification(
					LicenseClassification licenseClss) {
			return new NurseClassification(new CompanyId("CID") // dummy
						,	new NurseClassifiCode("1")// dummy
						,	new NurseClassifiName("NAME1") // dummy
						,	licenseClss, true, false // dummy
					);
		}
		
	}
}
