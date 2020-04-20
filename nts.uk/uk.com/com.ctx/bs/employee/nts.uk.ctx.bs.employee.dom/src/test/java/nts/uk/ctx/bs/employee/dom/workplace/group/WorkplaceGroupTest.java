package nts.uk.ctx.bs.employee.dom.workplace.group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup.Require;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DeleteWorkplaceGroupService;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DomainServiceHelper;

public class WorkplaceGroupTest {
	
	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		NtsAssert.invokeGetters(group);
	}
	
	@Test
	public void addAffWorkplaceGroup() {
		AffWorkplaceGroup group = DomainServiceHelper.Helper.DUMMY_AFF;
		group = WorkplaceGroup.addAffWorkplaceGroup(group.getWKPGRPID(), group.getWKPID());
		NtsAssert.invokeGetters(group);
	}
	
	@Test
	public void getAffWorkplace() {
		new Expectations() {
			{
				require.getWKPID("01");// dummy
			}
		};
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		assertThat(group.getAffWorkplace(require).isEmpty()).isTrue();
	}
}
