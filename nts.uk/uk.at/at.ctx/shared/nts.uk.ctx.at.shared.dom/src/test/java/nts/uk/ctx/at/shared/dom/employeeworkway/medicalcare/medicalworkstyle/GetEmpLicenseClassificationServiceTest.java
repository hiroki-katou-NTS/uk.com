package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService.Require;
@RunWith(JMockit.class)
public class GetEmpLicenseClassificationServiceTest {
	
	@Injectable
	private Require require;
	
	/**
	 * input: 	社員IDリスト　＝「"sid_1","sid_2"」
	 * 			社員の医療勤務形態履歴項目リスト　＝　empty
	 * 			会社の看護区分リスト = empty
	 * output: 	　[ {"sid_1", empty}, {"sid_2", empty} ]
	 * 
	 *
	 */
	@Test
	public void test_getEmpMedicalWorkFormHisItem_empty() {
		val listEmp = Arrays.asList("sid_1","sid_2"); // dummy
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem(listEmp, (GeneralDate) any);
				
				require.getListCompanyNurseCategory(); 
			}
		};
		
		val result = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		
		assertThat(result)
				.extracting(emp -> emp.getEmpID(),	emp -> emp.getOptLicenseClassification(),	emp -> emp.getIsNursingManager())
				.containsExactly(
								tuple(	"sid_1",	Optional.empty(),	Optional.empty()) , 
								tuple(	"sid_2",	Optional.empty(),	Optional.empty()));
		
	}
	
	/**
	 * input  
	 * 		社員IDリスト　＝　「"sid_1","sid_2"」
	 * 		社員の医療勤務形態履歴項目リスト　=	 「	{ 社員ID = sid_1, 看護区分コード = "2"}	」
	 * 		看護リスト　= 					「 	｛看護区分コード = "2", 免許区分 ＝ NURSE_ASSIST｝	」
	 * output:
	 * 		 「	{ 社員ID = sid_1, NURSE_ASSIST}	
	 * 			{ 社員ID = sid_1, empty}
	 * 		」
	 */
	@Test
	public void test_classifiCode_nurseClassifi_NotNull() {
		val listEmp = Arrays.asList("sid_1","sid_2"); // dummy
		
		val histItems = Arrays.asList(
				Helper.createEmpMedicalWorkFormHisItem("sid_1", new NurseClassifiCode("2")));
		
		val  nurseClassifications = Arrays.asList(
				Helper.createNurseClassification(	new NurseClassifiCode("2")
												,	LicenseClassification.NURSE_ASSIST//免許区分
												,	false));//看護管理者か
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem(listEmp, GeneralDate.today());// dummy
				result = histItems;
				
				require.getListCompanyNurseCategory(); // dummy
				result = nurseClassifications;
			}
		};
		
		val classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		
		assertThat(classifications)
			.extracting(	emp -> emp.getEmpID()
						,	emp -> emp.getOptLicenseClassification().isPresent() ? emp.getOptLicenseClassification().get() : Optional.empty()
						,	emp -> emp.getIsNursingManager().isPresent() ? emp.getIsNursingManager().get() : Optional.empty()		
					)
			.containsExactly(
						tuple("sid_1",	LicenseClassification.NURSE_ASSIST,	new Boolean(false) ) , 
						tuple("sid_2",	Optional.empty(),	Optional.empty()));
	}
	
	/**
	 * input  
	 * 		社員IDリスト　＝　「"sid_1","sid_2"」
	 * 		社員の医療勤務形態履歴項目リスト　=	 「		{ 社員ID = sid_1, 看護区分コード = "9"}	
	 * 										, 	{ 社員ID = sid_2, 看護区分コード = "7"}」
	 * 		看護リスト　= 					「 	｛看護区分コード = "3", 免許区分 ＝ NURSE_ASSIST｝」
	 * output:
	 * 		 「	{ 社員ID = sid_1, empty}	
	 * 			{ 社員ID = sid_1, empty}」
	 */
	@Test
	public void test_nurseClassification_null() {
		val listEmp = Arrays.asList("sid_1","sid_2"); // dummy
		
		val histItems = Arrays.asList(
					Helper.createEmpMedicalWorkFormHisItem("sid_1", new NurseClassifiCode("9"))
				,	Helper.createEmpMedicalWorkFormHisItem("sid_2", new NurseClassifiCode("7"))
			);
		
		val  nurseClassifications = Arrays.asList(
				Helper.createNurseClassification(	new NurseClassifiCode("3")
												,	LicenseClassification.NURSE_ASSIST//免許区分
												,	false));//看護管理者か
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem(listEmp, GeneralDate.today());// dummy
				result = histItems;
				
				require.getListCompanyNurseCategory(); // dummy
				result = nurseClassifications;
			}
		};
		
		val result = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		
		assertThat(result)
				.extracting(
								emp-> emp.getEmpID()
							,	emp-> emp.getOptLicenseClassification()
							,	emp-> emp.getIsNursingManager()
							)
				.containsExactly(
								tuple("sid_1",	Optional.empty(), Optional.empty())
							,	tuple("sid_2",	Optional.empty(), Optional.empty()));
	}
	
	public static class Helper{
		/**
		 * 社員の医療勤務形態履歴項目を作る
		 * @param sid 社員ID
		 * @param historyId 履歴ID
		 * @param nurseClassifiCode 看護区分コード
		 * @return
		 */
		public static EmpMedicalWorkStyleHistoryItem createEmpMedicalWorkFormHisItem(String sid, NurseClassifiCode nurseClassifiCode) {
				return new EmpMedicalWorkStyleHistoryItem(
						sid, 
						IdentifierUtil.randomUniqueId(), // dummy
						nurseClassifiCode,
						true,// dummy
						MedicalCareWorkStyle.FULLTIME,// dummy
						true);// dummy
		}
		
		/**
		 * 看護区分を作る
		 * @param nurseClassifiCode　看護区分コード
		 * @return
		 */
		public static NurseClassification createNurseClassification(NurseClassifiCode nurseClassifiCode
				,	LicenseClassification licenseClss
				,	boolean isNursingManager) {
			return new NurseClassification(new CompanyId("CID") // dummy
						,	nurseClassifiCode
						,	 new NurseClassifiName("NAME1") // dummy
						,	licenseClss, true, isNursingManager);
		}
		
	}
}
