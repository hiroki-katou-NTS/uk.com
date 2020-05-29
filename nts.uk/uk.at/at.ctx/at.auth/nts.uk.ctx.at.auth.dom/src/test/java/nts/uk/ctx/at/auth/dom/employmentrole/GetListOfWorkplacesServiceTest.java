package nts.uk.ctx.at.auth.dom.employmentrole;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.employmentrole.GetListOfWorkplacesService.Require;


/**
 * @author laitv
 */

@RunWith(JMockit.class)
public class GetListOfWorkplacesServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_1() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		GeneralDate baseDate = GeneralDate.today();
		Integer closureId = 1; // dummy
		
		new Expectations() {
			{
				require.getUserID(employeeId);
			}
		};
		
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
		
		
	}
	

}
