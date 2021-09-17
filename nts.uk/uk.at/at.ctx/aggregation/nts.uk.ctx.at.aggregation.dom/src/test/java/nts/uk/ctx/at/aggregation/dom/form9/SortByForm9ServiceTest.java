package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import org.assertj.core.groups.Tuple;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;

@RunWith(JMockit.class)
public class SortByForm9ServiceTest {
	
	@Injectable
	private SortByForm9Service.Require require;
	
	/**
	 * target: sort
	 * pattern: 看護区分, 職位コード, 社員コードを並べる
	 */
	@Test
	public void testSort_licenses_jobtileCode_employeeCode() {
		val baseDate = GeneralDate.ymd(2021, 01, 01);
		val sids = new ArrayList<>(Arrays.asList(
					"sid1", "sid2", "sid3", "sid4", "sid5", "sid6"
				,	"sid7", "sid8", "sid9", "sid10", "sid11", "sid12" ));
		/**
		 * sid2, sid6, sid9 : NURSE
		 * sid4, sid7, sid8 : NURSE_ASSOCIATE
		 * sid3, sid5 : NURSE_ASSIST
		 * sid1, sid10, sid11, sid12: 免許区分がない
		 * 
		 */
		val empLicenses = new ArrayList<>( Arrays.asList(
					Helper.createEmpLicenseClassification( "sid1" )
				,	Helper.createEmpLicenseClassification( "sid2", LicenseClassification.NURSE )
				,	Helper.createEmpLicenseClassification( "sid3", LicenseClassification.NURSE_ASSIST )
				,	Helper.createEmpLicenseClassification( "sid4", LicenseClassification.NURSE_ASSOCIATE )
				,	Helper.createEmpLicenseClassification( "sid5", LicenseClassification.NURSE_ASSIST )
				,	Helper.createEmpLicenseClassification( "sid6", LicenseClassification.NURSE )
				,	Helper.createEmpLicenseClassification( "sid7", LicenseClassification.NURSE_ASSOCIATE )
				,	Helper.createEmpLicenseClassification( "sid8", LicenseClassification.NURSE_ASSOCIATE )
				,	Helper.createEmpLicenseClassification( "sid9", LicenseClassification.NURSE )
				,	Helper.createEmpLicenseClassification( "sid10")
				,	Helper.createEmpLicenseClassification( "sid11")
				,	Helper.createEmpLicenseClassification( "sid12")
					));
		
		/**
		 * sid1, sid7, sid9 : J01
		 * sid2, sid10 : J03
		 * sid3, sid9: J02
		 * sid4, sid8: J08
		 * sid5: J04
		 * sid6, sid11, sid12: empty
		 */
		val empJobTitles = new ArrayList<>( Arrays.asList(
					Helper.createEmployeeJobTitleImport( "sid1", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid2", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid3", "J02" )
				,	Helper.createEmployeeJobTitleImport( "sid4", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid5", "J04" )
				//	sid6 empty
				,	Helper.createEmployeeJobTitleImport( "sid7", "J01" )
				,	Helper.createEmployeeJobTitleImport( "sid8", "J08" )
				,	Helper.createEmployeeJobTitleImport( "sid9", "J02" )
				,	Helper.createEmployeeJobTitleImport( "sid10", "J03" )
				// sid11, sid12 empty
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo( "sid1", "E01" )
				,	Helper.createPersonInfo( "sid2", "E02" )
				,	Helper.createPersonInfo( "sid3", "E03" )
				,	Helper.createPersonInfo( "sid4", "E04" )
				,	Helper.createPersonInfo( "sid5", "E05" )
				,	Helper.createPersonInfo( "sid6", "E06" )
				,	Helper.createPersonInfo( "sid7", "E07" )
				,	Helper.createPersonInfo( "sid8", "E08" )
				,	Helper.createPersonInfo( "sid9", "E09" )
				,	Helper.createPersonInfo( "sid10", "E10" )
				,	Helper.createPersonInfo( "sid11", "E11" )
				,	Helper.createPersonInfo( "sid12", "E12" )
					));
		
		new Expectations(GetEmpLicenseClassificationService.class) {
			{
				GetEmpLicenseClassificationService.get(require, baseDate, sids);
				result = empLicenses;
				
				require.getEmployeeJobTitle( baseDate, sids);
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
									"sid9" //NURSE, J02
								,	"sid2" //NURSE, J03
								,	"sid6" //NURSE, empty
								,	"sid7" //NURSE_ASSOCIATE, J01
								,	"sid4" //NURSE_ASSOCIATE, J08, E04
								,	"sid8" //NURSE_ASSOCIATE, J08, E08
								,	"sid3" //NURSE_ASSIST, J02
								,	"sid5" //NURSE_ASSIST, J04
								,	"sid1" //empty, J02
								,	"sid10"//empty, J03
								,	"sid11"//empty, empty, E11
								,	"sid12"//empty, empty, E12
									);
	}
	
	/**
	 * target: createForm9SortEmployeeInfo
	 */
	@Test
	public void testCreateForm9SortEmployeeInfo() {
		
		val sids = new ArrayList<>(Arrays.asList("sid1", "sid2", "sid3", "sid4" ));
		val empLicenses = new ArrayList<>( Arrays.asList(
					Helper.createEmpLicenseClassification( "sid1" )//sid1 empty
				,	Helper.createEmpLicenseClassification( "sid2", LicenseClassification.NURSE )
				,	Helper.createEmpLicenseClassification( "sid3", LicenseClassification.NURSE_ASSIST )
				,	Helper.createEmpLicenseClassification( "sid4", LicenseClassification.NURSE_ASSOCIATE )
					));
		
		val empJobTitles = new ArrayList<>( Arrays.asList(
					Helper.createEmployeeJobTitleImport( "sid1", "J01" )
					//sid2 empty
				,	Helper.createEmployeeJobTitleImport( "sid3", "J03" )
				,	Helper.createEmployeeJobTitleImport( "sid4", "J04" )
				
					));
		
		val personInfos = new ArrayList<>( Arrays.asList(
					Helper.createPersonInfo( "sid1", "E01" )
				,	Helper.createPersonInfo( "sid2", "E02" )
				,	Helper.createPersonInfo( "sid3", "E03" )
				,	Helper.createPersonInfo( "sid4", "E04" )
					));
		
		//Act
		List<Form9SortEmployeeInfo> result = NtsAssert.Invoke.staticMethod(
					SortByForm9Service.class
				,	"createForm9SortEmployeeInfo"
				,	sids, empLicenses
				,	empJobTitles, personInfos );
		
		//Assert
		assertThat(result)
			.extracting(
					d -> d.getEmployeeId()
				,	d -> d.getLicenseClassification()
				,	d -> d.getJobTitleCode()
				,	d -> d.getEmployeeCode()
					)
			.containsExactly(
					Tuple.tuple("sid1", null, "J01", "E01")//license empty
				,	Tuple.tuple("sid2", LicenseClassification.NURSE, null, "E02")//jobtitle empty
				,	Tuple.tuple("sid3", LicenseClassification.NURSE_ASSIST, "J03", "E03")
				,	Tuple.tuple("sid4", LicenseClassification.NURSE_ASSOCIATE, "J04", "E04")
					);
	}
	
	private static class Helper{
		
		/**
		 * 看護区分を作る
		 * @param nurseClassifiCode 看護区分コード
		 * @param license 免許区分
		 * @return
		 */
		public static NurseClassification createNurseClassification( LicenseClassification license ) {
			return new NurseClassification(
						new CompanyId("cid1")//dummy
					,	new NurseClassifiCode("01" + license.value)
					,	new NurseClassifiName(("name"))//dummy
					,	license
					,	false//dummy
					,	false//dummy
						);
		}
		
		/**
		 * 社員免許区分を作る
		 * @param sid 社員ID
		 * @param license 免許区分
		 * @return
		 */
		public static EmpLicenseClassification createEmpLicenseClassification(String sid, LicenseClassification license) {
			
			return EmpLicenseClassification.createEmpLicenseClassification(sid, createNurseClassification(license));
		}
		
		/**
		 * 社員免許区分なしを作る
		 * @param sid 社員ID
		 * @return
		 */
		public static EmpLicenseClassification createEmpLicenseClassification(String sid) {
			return EmpLicenseClassification.createEmpLicenseClassification(sid);
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
		public static SharedAffJobTitleHisImport createEmployeeJobTitleImport(String sid, String jobTitleCode) {
			 return new SharedAffJobTitleHisImport(
						sid, "id"+ jobTitleCode
					,	new DatePeriod( GeneralDate.ymd(2021, 01, 01), GeneralDate.ymd(2021, 12, 31))
					,	"jobTitleName", jobTitleCode );
		}
	}
}
