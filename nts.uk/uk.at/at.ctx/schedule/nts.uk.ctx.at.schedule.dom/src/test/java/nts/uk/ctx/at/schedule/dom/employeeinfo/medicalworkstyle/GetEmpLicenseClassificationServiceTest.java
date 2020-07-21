package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService.Require;

@RunWith(JMockit.class)
public class GetEmpLicenseClassificationServiceTest {
	
	@Injectable
	private Require require;
	
	@Test
	public void ClassifiCode_NotNull() {
		List<String> listEmp = Arrays.asList("003","004"); // dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = EmpLicenseHelper.getLstEmpMedical();
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<NurseClassification>  listNurseClassification = EmpLicenseHelper.getLstNurseClass();
		
		new Expectations() {
			{
				require.getListCompanyNurseCategory(); // dummy
				result = listNurseClassification;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		assertTrue(classifications.get(0).getOptLicenseClassification().isPresent());
	}
	
	@Test
	public void testClassifiCode_Null() {
		List<String> listEmp = Arrays.asList("003","004"); // dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = EmpLicenseHelper.getLstEmpMedical_null();
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<NurseClassification>  listNurseClassification = EmpLicenseHelper.getLstNurseClass_null();
		
		new Expectations() {
			{
				require.getListCompanyNurseCategory(); // dummy
				result = listNurseClassification;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		assertFalse(classifications.get(0).getOptLicenseClassification().isPresent());
	}
	
	@Test
	public void testNurseClassification_Null() {
		List<String> listEmp = Arrays.asList("003","002"); // dummy
		
		List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = EmpLicenseHelper.getnurseClass_null();
		new Expectations() {
			{
				require.get(listEmp, GeneralDate.today());// dummy
				result = listEmpMedicalWorkFormHisItem;
			}
		};
		
		List<NurseClassification>  listNurseClassification = EmpLicenseHelper.getnurseClassification_null();
		
		new Expectations() {
			{
				require.getListCompanyNurseCategory(); // dummy
				result = listNurseClassification;
			}
		};
		
		List<EmpLicenseClassification> classifications = GetEmpLicenseClassificationService.get(require, GeneralDate.today(), listEmp);
		assertFalse(classifications.get(0).getOptLicenseClassification().isPresent());
	}
}
