package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@RunWith(JMockit.class)
public class NurseClassificationTest {
	
	/**
	 * 免許区分 = 准看護師, 看護管理者か = true
	 */
	@Test
	public void testCreate_Msg_2238_case1() {
		NtsAssert.businessException("Msg_2238", () ->{
			NurseClassification.create(new CompanyId("cid")
						,	new NurseClassifiCode("01")
						,	new NurseClassifiName("name")
						,	LicenseClassification.NURSE_ASSOCIATE //准看護師
						,	true
						,	true//看護管理者か
						);
		});
	}
	
	/**
	 * 免許区分 = 看護補助者, 看護管理者か = true
	 */
	@Test
	public void testCreate_Msg_2238_case2() {
		NtsAssert.businessException("Msg_2238", () ->{
			NurseClassification.create(new CompanyId("cid")
						,	new NurseClassifiCode("01")
						,	new NurseClassifiName("name")
						,	LicenseClassification.NURSE_ASSIST //看護補助者
						,	true
						,	true//看護管理者か
						);
		});
	}
	
	@Test
	public void testCreate_success() {
		//Act
		val result = NurseClassification.create(new CompanyId("cid")
				,	new NurseClassifiCode("01")
				,	new NurseClassifiName("name")
				,	LicenseClassification.NURSE //看護補助者
				,	true
				,	true//看護管理者か
				);
		
		//Assert
		assertThat( result.getCompanyId() ).isEqualTo( new CompanyId( "cid") );
		assertThat( result.getNurseClassifiCode() ).isEqualTo( new NurseClassifiCode( "01") );
		assertThat( result.getNurseClassifiName() ).isEqualTo( new NurseClassifiName( "name") );
		assertThat( result.getLicense() ).isEqualTo( LicenseClassification.NURSE );
		assertThat( result.isOfficeWorker()).isTrue();
		assertThat( result.isNursingManager()).isTrue();
		
	}
}
