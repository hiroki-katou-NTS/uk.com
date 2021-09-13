package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.jobtitle.EmployeeJobTitleImport;

@RunWith(JMockit.class)
public class SortByForm9ServiceTest {
	
	@Injectable
	private SortByForm9Service.Require require;
	
	/**
	 * target: sort
	 * pattern: 看護区分, 職位コード, 社員コードを並べる
	 */
	@Test
	public void sort_licenses_jobtileCode_employeeCode() {
		val baseDate = GeneralDate.ymd(2021, 01, 01);
		val nurse = Helper.createNurseClassification(
					new NurseClassifiCode("01")
				,	LicenseClassification.NURSE);
		val nurse_Assist = Helper.createNurseClassification(
					new NurseClassifiCode("02")
				,	LicenseClassification.NURSE_ASSIST);
		val nurse_Associate = Helper.createNurseClassification(
					new NurseClassifiCode("03")
				,	LicenseClassification.NURSE_ASSOCIATE );
		val sids = new ArrayList<>(Arrays.asList(
					"sid1", "sid2", "sid3", "sid4", "sid5"
				,	"sid6", "sid7", "sid8", "sid9", "sid10" ));
		val empLicenses = new ArrayList<>( Arrays.asList(
					EmpLicenseClassification.createEmpLicenseClassification("sid1")	//sid1 empty
				,	EmpLicenseClassification.createEmpLicenseClassification("sid2", nurse)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid3", nurse_Assist)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid4", nurse_Associate)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid5", nurse_Assist)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid9")	//sid9 empty
				,	EmpLicenseClassification.createEmpLicenseClassification("sid6", nurse)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid8")	//sid8 empty
				,	EmpLicenseClassification.createEmpLicenseClassification("sid7", nurse_Associate)
				,	EmpLicenseClassification.createEmpLicenseClassification("sid10")//sid10 empty
					));
		val empJobTitles = new ArrayList<>( Arrays.asList(
					Helper.createEmployeeJobTitleImport( "sid1", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid2", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid3", "J02" )
				,	Helper.createEmployeeJobTitleImport( "sid4", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid5", "J04" )
				//	sid6 empty
				,	Helper.createEmployeeJobTitleImport( "sid7", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid8", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid10", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid9", "J02" )
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo("sid1", "E01")
				,	Helper.createPersonInfo("sid5", "E05")
				,	Helper.createPersonInfo("sid3", "E03")
				,	Helper.createPersonInfo("sid2", "E02")
				,	Helper.createPersonInfo("sid9", "E09")
				,	Helper.createPersonInfo("sid4", "E04")
				,	Helper.createPersonInfo("sid6", "E06")
				,	Helper.createPersonInfo("sid7", "E07")
				,	Helper.createPersonInfo("sid8", "E08")
				,	Helper.createPersonInfo("sid10", "E10")
					));
		
		new Expectations(GetEmpLicenseClassificationService.class) {
			{
				GetEmpLicenseClassificationService.get(require, baseDate, sids);
				result = empLicenses;
				
				require.getAffJobTitleHis( baseDate, sids);
				result = empJobTitles;
				
				require.getEmployeeCodeAndDisplayNameImportByEmployeeIds(sids);
				result = personInfos;
				
			}
		};
		
		//Act
		val result = SortByForm9Service.sort(require, baseDate, sids);
		
		//Assert
		assertThat(result).extracting(d -> d)
							.containsExactly(
									"sid2"
								,	"sid6"
								,	"sid7"
								,	"sid4"
								,	"sid3"
								,	"sid5"
								,	"sid1"
								,	"sid9"
								,	"sid10"
								,	"sid8");
	}

	/**
	 * target: sort
	 * pattern: 看護区分_empty, 職位コード, 社員コードを並べる
	 */
	@Test
	public void sort_jobtileCode_employeeCode() {
		val baseDate = GeneralDate.ymd(2021, 01, 01);
		val sids = new ArrayList<>(Arrays.asList(
					"sid1", "sid2", "sid3", "sid4", "sid5"
				,	"sid6", "sid7", "sid8", "sid9", "sid10" ));
		val empLicenses = new ArrayList<>( Arrays.asList(
					EmpLicenseClassification.createEmpLicenseClassification("sid1")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid2")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid3")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid4")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid5")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid9")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid6")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid8")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid7")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid10")
					));
		val empJobTitles = new ArrayList<>( Arrays.asList(
					Helper.createEmployeeJobTitleImport( "sid1", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid2", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid3", "J02" )
				,	Helper.createEmployeeJobTitleImport( "sid4", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid5", "J04" )
				,	Helper.createEmployeeJobTitleImport( "sid6", "J02" )
				,	Helper.createEmployeeJobTitleImport( "sid7", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid8", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid10", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid9", "J02" )
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo("sid1", "E01")
				,	Helper.createPersonInfo("sid5", "E05")
				,	Helper.createPersonInfo("sid10", "E10")
				,	Helper.createPersonInfo("sid3", "E03")
				,	Helper.createPersonInfo("sid2", "E02")
				,	Helper.createPersonInfo("sid9", "E09")
				,	Helper.createPersonInfo("sid4", "E04")
				,	Helper.createPersonInfo("sid6", "E06")
				,	Helper.createPersonInfo("sid7", "E07")
				,	Helper.createPersonInfo("sid8", "E08")
					));
		
		new Expectations(GetEmpLicenseClassificationService.class) {
			{
				GetEmpLicenseClassificationService.get(require, baseDate, sids);
				result = empLicenses;
				
				require.getAffJobTitleHis( baseDate, sids);
				result = empJobTitles;
				
				require.getEmployeeCodeAndDisplayNameImportByEmployeeIds(sids);
				result = personInfos;
				
			}
		};
		
		//Act
		val result = SortByForm9Service.sort(require, baseDate, sids);
		
		//Assert
		assertThat(result).extracting(d -> d)
							.containsExactly(
									"sid1"
								,	"sid7"
								,	"sid3"
								,	"sid6"
								,	"sid9"
								,	"sid2"
								,	"sid10"
								,	"sid5"
								,	"sid4"
								,	"sid8");
	}
	
	/**
	 * target: sort
	 * pattern: 看護区分_empty, 職位コード = empty, 社員コードを並べる
	 */
	@Test
	public void sort_employeeCode() {
		val baseDate = GeneralDate.ymd(2021, 01, 01);
		val sids = new ArrayList<>(Arrays.asList(
					"sid1", "sid2", "sid3", "sid4", "sid5"
				,	"sid6", "sid7", "sid8", "sid9", "sid10" ));
		val empLicenses = new ArrayList<>( Arrays.asList(
					EmpLicenseClassification.createEmpLicenseClassification("sid1")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid2")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid3")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid4")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid5")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid9")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid6")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid8")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid7")
				,	EmpLicenseClassification.createEmpLicenseClassification("sid10")
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo("sid1", "E01")
				,	Helper.createPersonInfo("sid5", "E05")
				,	Helper.createPersonInfo("sid10", "E10")
				,	Helper.createPersonInfo("sid3", "E03")
				,	Helper.createPersonInfo("sid2", "E02")
				,	Helper.createPersonInfo("sid9", "E09")
				,	Helper.createPersonInfo("sid4", "E04")
				,	Helper.createPersonInfo("sid6", "E06")
				,	Helper.createPersonInfo("sid7", "E07")
				,	Helper.createPersonInfo("sid8", "E08")
					));
		
		new Expectations(GetEmpLicenseClassificationService.class) {
			{
				GetEmpLicenseClassificationService.get(require, baseDate, sids);
				result = empLicenses;
				
				require.getEmployeeCodeAndDisplayNameImportByEmployeeIds(sids);
				result = personInfos;
				
			}
		};
		
		//Act
		val result = SortByForm9Service.sort(require, baseDate, sids);
		
		//Assert
		assertThat(result).extracting(d -> d)
							.containsExactly(
									"sid1"
								,	"sid2"
								,	"sid3"
								,	"sid4"
								,	"sid5"
								,	"sid6"
								,	"sid7"
								,	"sid8"
								,	"sid9"
								,	"sid10");
	}
	
	/**
	 * target: sort
	 * pattern: 職位コード = empty, 看護区分, 社員コードを並べる
	 */
	@Test
	public void sort_license_employeeCode() {
		val baseDate = GeneralDate.ymd(2021, 01, 01);
		val nurse = Helper.createNurseClassification(
					new NurseClassifiCode("01")
				,	LicenseClassification.NURSE);
		val nurse_Assist = Helper.createNurseClassification(
					new NurseClassifiCode("02")
				,	LicenseClassification.NURSE_ASSIST);
		val nurse_Associate = Helper.createNurseClassification(
					new NurseClassifiCode("03")
				,	LicenseClassification.NURSE_ASSOCIATE );
		val sids = new ArrayList<>(Arrays.asList(
					"sid1", "sid2", "sid3", "sid4", "sid5"
				,	"sid6", "sid7", "sid8", "sid9", "sid10" ));
		val empLicenses = new ArrayList<>( Arrays.asList(
					EmpLicenseClassification.createEmpLicenseClassification( "sid1", nurse_Assist )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid2", nurse )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid3", nurse_Assist )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid4", nurse_Associate )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid5", nurse_Assist )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid9", nurse_Associate )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid6", nurse )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid8", nurse_Assist )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid7", nurse_Associate )
				,	EmpLicenseClassification.createEmpLicenseClassification( "sid10", nurse_Assist )
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo("sid1", "E01")
				,	Helper.createPersonInfo("sid5", "E05")
				,	Helper.createPersonInfo("sid10", "E10")
				,	Helper.createPersonInfo("sid3", "E03")
				,	Helper.createPersonInfo("sid2", "E02")
				,	Helper.createPersonInfo("sid9", "E09")
				,	Helper.createPersonInfo("sid4", "E04")
				,	Helper.createPersonInfo("sid6", "E06")
				,	Helper.createPersonInfo("sid7", "E07")
				,	Helper.createPersonInfo("sid8", "E08")
					));
		
		new Expectations(GetEmpLicenseClassificationService.class) {
			{
				GetEmpLicenseClassificationService.get(require, baseDate, sids);
				result = empLicenses;
				
				require.getEmployeeCodeAndDisplayNameImportByEmployeeIds(sids);
				result = personInfos;
				
			}
		};
		
		//Act
		val result = SortByForm9Service.sort(require, baseDate, sids);
		
		//Assert
		assertThat(result).extracting(d -> d)
							.containsExactly(
									"sid2"
								,	"sid6"
								,	"sid4"
								,	"sid7"
								,	"sid9"
								,	"sid1"
								,	"sid3"
								,	"sid5"
								,	"sid8"
								,	"sid10");
	}
	
	private static class Helper{
		
		/**
		 * 看護区分を作る
		 * @param nurseClassifiCode 看護区分コード
		 * @param license 免許区分
		 * @return
		 */
		public static NurseClassification createNurseClassification(
					NurseClassifiCode nurseClassifiCode
				,	LicenseClassification license) {
			return new NurseClassification(
						new CompanyId("cid1")//dummy
					,	nurseClassifiCode
					,	new NurseClassifiName(("name"))//dummy
					,	license
					,	false//dummy
					,	false//dummy
						);
		}
		
		/**
		 * 社員コードと表示名を作る
		 * @param sid 社員ID
		 * @param employeeCode 社員コード
		 * @return
		 */
		public static EmployeeCodeAndDisplayNameImport createPersonInfo(String sid, String employeeCode) {
			return new EmployeeCodeAndDisplayNameImport( sid, employeeCode, "bussinessName" );
		}
		
		/**
		 * 社員職位を作る
		 * @param sid 社員ID
		 * @param jobTitleCode 職位コード
		 * @return
		 */
		public static EmployeeJobTitleImport createEmployeeJobTitleImport(String sid, String jobTitleCode) {
			 return new EmployeeJobTitleImport( sid, "id"+ jobTitleCode, jobTitleCode );
		}
	}
}
