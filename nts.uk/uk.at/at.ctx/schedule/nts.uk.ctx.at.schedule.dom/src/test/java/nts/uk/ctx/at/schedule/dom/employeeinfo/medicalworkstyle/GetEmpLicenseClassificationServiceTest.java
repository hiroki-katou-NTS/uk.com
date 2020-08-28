package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService.Require;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@RunWith(JMockit.class)
public class GetEmpLicenseClassificationServiceTest {
	
	@Injectable
	private Require require;
	
	@Test
	public void test_classifiCode_nurseClassifi_NotNull() {
		List<String> listEmp = Arrays.asList("003","004"); // dummy
		
		EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
				"003", 
				"historyID2", true,// dummy
				Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,// dummy
						new NurseClassifiCode("2"), true)),// dummy
				Optional.ofNullable(null));// dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<EmpMedicalWorkFormHisItem>();
		listEmpMedicalWorkFormHisItem.add(results);
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<NurseClassification>  listNurseClassification = new ArrayList<NurseClassification>();
		listNurseClassification.add(new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("2"), new NurseClassifiName("NAME1"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true));
		
		new Expectations() {
			{
				require.getListCompanyNurseCategory(); // dummy
				result = listNurseClassification;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		
		assertThat(classifications).extracting(x-> x.getEmpID(),
				x-> x.getOptLicenseClassification().isPresent() ? x.getOptLicenseClassification().get().name() : Optional.empty())
		.containsExactly(tuple("003","NURSE_ASSIST") , 
						tuple("004",Optional.empty()));
	}
	
	@Test
	public void test_ClassifiCode_Null() {
		List<String> listEmp = Arrays.asList("003","004"); // dummy
		
		EmpMedicalWorkFormHisItem result2 = new EmpMedicalWorkFormHisItem(
				"008", 
				"historyID4", true,// dummy
				Optional.ofNullable(null),// dummy
				Optional.ofNullable(null));// dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
		listEmpMedicalWorkFormHisItem.add(result2);
		
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		assertThat(classifications).extracting(x-> x.getEmpID(),
				x-> x.getOptLicenseClassification())
		.containsExactly(tuple("003",Optional.empty()),tuple("004",Optional.empty()));
	}
	
	@Test
	public void test_nurseClassification_null() {
		List<String> listEmp = Arrays.asList("003","002"); // dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<EmpMedicalWorkFormHisItem>();
		listEmpMedicalWorkFormHisItem.add(
				new EmpMedicalWorkFormHisItem(
				"003", 
				"historyID1", true,// dummy
				Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
						new NurseClassifiCode("9"), true)),
				Optional.ofNullable(null)));// dummy
		
		listEmpMedicalWorkFormHisItem.add(new EmpMedicalWorkFormHisItem(
				"002", 
				"historyID2", true,
				Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
						new NurseClassifiCode("7"), true)),
				Optional.ofNullable(null)));// dummy

		
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<NurseClassification>  listNurseClassification = new ArrayList<NurseClassification>();
		listNurseClassification.add(new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("3"), // dummy
				 new NurseClassifiName("NAME1"), // dummy
				 LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true));// dummy
		new Expectations() {
			{
				require.getListCompanyNurseCategory(); // dummy
				result = listNurseClassification;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		assertThat(classifications).extracting(x-> x.getEmpID(),
				x-> x.getOptLicenseClassification())
		.containsExactly(tuple("003",Optional.empty()),tuple("002",Optional.empty()));
	}
}
