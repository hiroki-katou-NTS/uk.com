package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

public class AffiliationInforOfDailyAttdTest {
	
	@Injectable
	AffiliationInforOfDailyAttd.Require require;
	
	@Test
	public void testGetEmploymentCode() {
		new Expectations() {{
			require.getAffEmploymentHistory( anyString, (GeneralDate) any);
			result = new SharedSyEmploymentImport("empId", "E01", "正社員", 
						new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 12, 31)));
		}};
		
		EmploymentCode result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getEmploymentCode", 
				require, "empId", GeneralDate.today());
		
		assertThat( result.v() ).isEqualTo( "E01" );
	}
	
	@Test
	public void testGetJobTitleId() {
		new Expectations() {{
			require.getAffJobTitleHistory( anyString, (GeneralDate) any);
			result = new SharedAffJobTitleHisImport(
						"empId", 
						"job-id", 
						new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 12, 31)), 
						"job-name",
						"job-code");
		}};
		
		String result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getJobTitleId", 
				require, "empId", GeneralDate.today());
		
		assertThat( result ).isEqualTo( "job-id" );
	}
	
	@Test
	public void testGetWorkplaceId() {
		new Expectations() {{
			require.getAffWorkplaceHistory( anyString, (GeneralDate) any);
			result = new SharedAffWorkPlaceHisImport(
						new DatePeriod(
								GeneralDate.ymd(2020, 1, 1), 
								GeneralDate.ymd(2020, 12, 31)), 
						"empId",
						"wpl-id", 
						"wpl-code", 
						"wpl-name", 
						"wpl-displayName");
		}};
		
		String result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getWorkplaceId", 
				require, "empId", GeneralDate.today());
		
		assertThat( result ).isEqualTo( "wpl-id" );
	}
	
	@Test
	public void testGetClassificationCode() {
		new Expectations() {{
			require.getClassificationHistory( anyString, (GeneralDate) any);
			result = new SClsHistImport(
						new DatePeriod(
								GeneralDate.ymd(2020, 1, 1), 
								GeneralDate.ymd(2020, 12, 31)), 
						"empId", 
						"class-code", 
						"class-name");
		}};
		
		ClassificationCode result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getClassificationCode", 
				require, "empId", GeneralDate.today());
		
		assertThat( result.v() ).isEqualTo( "class-code" );
	}
	
	@Test
	public void testGetBusinessTypeCode_empty() {
		
		new Expectations() {{
			require.getBusinessType(anyString, (GeneralDate) any);
			//result = empty
		}};
		
		Optional<BusinessTypeCode> result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getBusinessTypeCode", 
				require, "empId", GeneralDate.today());
		
		assertThat( result ).isEmpty();
	}
	
	@Test
	public void testGetBusinessTypeCode_ok() {
		
		new Expectations() {{
			require.getBusinessType(anyString, (GeneralDate) any);
			result = Optional.of(
						new BusinessTypeOfEmployee(
								new BusinessTypeCode("001"), "histId", "empId"));
		}};
		
		Optional<BusinessTypeCode> result = NtsAssert.Invoke.staticMethod(AffiliationInforOfDailyAttd.class, "getBusinessTypeCode", 
				require, "empId", GeneralDate.today());
		
		assertThat( result.get().v() ).isEqualTo( "001" );
	}
	
	@Test
	public void testGetBonusPaySettingCode_exception() {

		new Expectations() {{
			
			require.getWorkingConditionHistory( anyString, (GeneralDate) any);
			// result = empty;
			
		}};
		
		NtsAssert.businessException("Msg_430", () -> AffiliationInforOfDailyAttd.create(require, "empId", GeneralDate.today())); 
		
		
	}
	
	@Test
	public void testGetBonusPaySettingCode_empty(
			@Injectable WorkingConditionItem workingCondition
			) {

		new Expectations() {{
			
			require.getWorkingConditionHistory( anyString, (GeneralDate) any);
			result = Optional.of(workingCondition);
			
			workingCondition.getTimeApply();
			// result = empty;
			
		}};
		
		Optional<BonusPaySettingCode> result = NtsAssert.Invoke.staticMethod(
					AffiliationInforOfDailyAttd.class, 
					"getBonusPaySettingCode", 
					require, "empId", GeneralDate.today());
		
		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void testGetBonusPaySettingCode_successfully(
			@Injectable WorkingConditionItem workingCondition,
			@Injectable BonusPaySettingCode bonusPaySettingcode
			) {

		new Expectations() {{
			
			require.getWorkingConditionHistory( anyString, (GeneralDate) any);
			result = Optional.of(workingCondition);
			
			workingCondition.getTimeApply();
			result = Optional.of(new BonusPaySettingCode("001"));
			
		}};
		
		Optional<BonusPaySettingCode> result = NtsAssert.Invoke.staticMethod(
					AffiliationInforOfDailyAttd.class, 
					"getBonusPaySettingCode", 
					require, "empId", GeneralDate.today());
		
		assertThat(result.get().v()).isEqualTo("001");
		
	}
	
	@Test
	public void testCreate(@Injectable WorkingConditionItem workingCondition) {
		
		new Expectations() {{
			
			require.getAffEmploymentHistory(anyString, (GeneralDate) any);
			result = Helper.createEmployment("employmentCode");
			
			require.getAffJobTitleHistory(anyString, (GeneralDate) any);
			result = Helper.createJobTitle("jobTitleId");
			
			
			require.getAffWorkplaceHistory(anyString, (GeneralDate) any);
			result = Helper.createWorkplace("workplaceId");
			
			require.getClassificationHistory(anyString, (GeneralDate) any);
			result = Helper.createClassification("classificationCode");
			
			require.getWorkingConditionHistory( anyString, (GeneralDate) any);
			result = Optional.of(workingCondition);
			
			workingCondition.getTimeApply();
			result = Optional.of(new BonusPaySettingCode("001"));
			
		}};
		
		
		AffiliationInforOfDailyAttd result = AffiliationInforOfDailyAttd.create(require, "empId", GeneralDate.today()); 
		
		assertThat( result.getEmploymentCode().v() ).isEqualTo( "employmentCode" );
		assertThat( result.getJobTitleID() ).isEqualTo( "jobTitleId" );
		assertThat( result.getWplID() ).isEqualTo( "workplaceId" );
		assertThat( result.getClsCode().v() ).isEqualTo( "classificationCode" );
		assertThat( result.getBusinessTypeCode() ).isEmpty();
		assertThat( result.getBonusPaySettingCode().get().v() ).isEqualTo( "001" );
		
	}
	
	
	
	static class Helper {
		
		static SharedSyEmploymentImport createEmployment( String employmentCode ) {
			
			return new SharedSyEmploymentImport(
					"empId", 
					employmentCode, 
					"employmentName", 
					new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 2, 1))); 
		}
		
		static SharedAffJobTitleHisImport createJobTitle(String jobTitleId) {
			
			return new SharedAffJobTitleHisImport(
					"empId", 
					jobTitleId, 
					new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 2, 1)), 
					"jobTitleName",
					"jobtitelCode");
		}
		
		static SharedAffWorkPlaceHisImport createWorkplace(String workplaceId) {
			
			return new SharedAffWorkPlaceHisImport(
					new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 2, 1)), 
					"empId", 
					workplaceId, 
					"wplCode", 
					"wplName", 
					"wkpDisplayName");
		}
		
		static SClsHistImport createClassification(String classificationCode) {
			
			return new SClsHistImport(
					new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 2, 1)), 
					"empId", 
					classificationCode, 
					"classificationName");
		}
		
	}

}
