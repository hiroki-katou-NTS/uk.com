package nts.uk.ctx.sys.portal.dom.generalsearch.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.sys.portal.dom.adapter.generalsearch.LoginRoleResponsibleAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.generalsearch.LoginRulerImport;

/**
 * The Class GeneralSearchHistoryServiceTest.
 * Test DomainService 汎用検索の履歴
 */
@RunWith(JMockit.class)
public class GeneralSearchHistoryServiceTest {
	@Tested
	private GeneralSearchHistoryService domainService;
	
	@Injectable
	private LoginRoleResponsibleAdapter adapter;
	
	@Mocked
	LoginRulerImport loginRulerImport = LoginRulerImport.builder()
		.isPayRoll(true)
		.isEmployee(true)
		.isHumanResource(true)
		.build();
	
	@Mocked
	boolean isPersonIncharge = true;
	
	@Test
	public void testGetLoginRuler() {
		String forCompanyAdmin = "forCompanyAdmin";
		String forSystemAdmin = "forSystemAdmin";
		new Expectations() {
			{
				adapter.getLoginResponsible().isPersonIncharge();
				result = false;

			}
		};
		
		//assertThat(domainService.checkRoleSearchManual(forCompanyAdmin, forSystemAdmin)).isTrue();
		assertTrue(domainService.checkRoleSearchManual(forCompanyAdmin, null));
		assertTrue(domainService.checkRoleSearchManual(null, forSystemAdmin));
		assertFalse(domainService.checkRoleSearchManual(null, null));
		assertTrue(domainService.checkRoleSearchManual(forCompanyAdmin, forSystemAdmin));
//		LoginRulerImport result2 = adapter.getLoginResponsible();
	}
}
