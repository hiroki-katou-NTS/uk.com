package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceReplaceResult;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.ReplaceWorkplacesService.Require;
/**
 * 
 * @author phongtq
 *
 */
public class ReplaceWorkplacesServiceTest {
	@Injectable
	private Require require;
	
	@Test
	public void testRep() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		List<String> lstWorkplaceId = DomainServiceHelper.getLstId();
		List<AffWorkplaceGroup> lstFormerAffInfo = DomainServiceHelper.getHelper();
		new Expectations() {
			{
				require.getByWKPGRPID("00000000000001");// dummy
				result = lstFormerAffInfo;
			}
		};
		Map<String, WorkplaceReplaceResult> workplacesService = ReplaceWorkplacesService.getWorkplace(require, group, lstWorkplaceId);
		workplacesService.forEach((WKPID, result) -> {
			
			new Expectations() {
				{
					require.deleteByWKPID("00000000000001");// dummy
				}
			};
			result.getPersistenceProcess().get().run();
		});
		
		assertThat(workplacesService.isEmpty()).isFalse();
	}
	
}
