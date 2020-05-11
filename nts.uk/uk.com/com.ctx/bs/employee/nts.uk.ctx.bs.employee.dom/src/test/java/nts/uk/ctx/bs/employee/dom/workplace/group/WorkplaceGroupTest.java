package nts.uk.ctx.bs.employee.dom.workplace.group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup.Require;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DomainServiceHelper;

public class WorkplaceGroupTest {
	
	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		NtsAssert.invokeGetters(workplaceGroup);
	}
	
	@Test
	public void addAffWorkplaceGroup() {
		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		AffWorkplaceGroup affWorkplaceGroup = workplaceGroup.addAffWorkplaceGroup("01");
		assertThat(affWorkplaceGroup.getWKPGRPID().equals(workplaceGroup.getWKPGRPID())).isTrue();
	}
	
	@Test
	public void getAffWorkplace() {
		new Expectations() {
			{
				require.getWKPID("0000000000001","01");// dummy
			}
		};
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		assertThat(group.getAffWorkplace(require).isEmpty()).isTrue();
	}
}
