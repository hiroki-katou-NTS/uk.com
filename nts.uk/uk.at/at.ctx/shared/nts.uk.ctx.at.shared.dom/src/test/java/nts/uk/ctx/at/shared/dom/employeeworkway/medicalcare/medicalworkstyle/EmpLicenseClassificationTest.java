package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@RunWith(JMockit.class)
public class EmpLicenseClassificationTest {
	@Test
	public void  getter(){
		EmpLicenseClassification target = new EmpLicenseClassification("sid", Optional.of(LicenseClassification.NURSE_ASSOCIATE),Optional.of(false));
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void  testCreateEmpLicenseClassificationBySid(){
		EmpLicenseClassification classification = EmpLicenseClassification.createEmpLicenseClassification("sid");
		assertEquals(classification.getEmpID(),"sid");
		assertThat(classification.getOptLicenseClassification().isPresent()).isFalse();
		assertThat(classification.getIsNursingManager().isPresent()).isFalse();
	}
	
	@Test
	public void  createEmpLicenseClassificationByNurseClassification(){
		//Act
		val classification = EmpLicenseClassification.createEmpLicenseClassification("sid"
				,	Hepler.createNurseClassification(
							LicenseClassification.NURSE_ASSIST //准看護師
						,	false)//看護管理か
				);
		
		//Assert
		assertEquals(classification.getEmpID(),"sid");
		assertThat(classification.getOptLicenseClassification().get()).isEqualTo(LicenseClassification.NURSE_ASSIST);
		assertThat(classification.getIsNursingManager().get()).isFalse();
	}
	
	public static class Hepler{
		/**
		 * create NurseClassification
		 * @param license
		 * @param isNursingManager
		 * @return
		 */
		public static NurseClassification createNurseClassification(
					LicenseClassification license
				,	boolean isNursingManager) {
			
			return NurseClassification.create(new CompanyId("cid")//dummy
					,	new NurseClassifiCode("01")//dummy
					,	new NurseClassifiName("name")//dummy
					,	license
					,	false//dummy
					,	isNursingManager);
		}
	}
}
