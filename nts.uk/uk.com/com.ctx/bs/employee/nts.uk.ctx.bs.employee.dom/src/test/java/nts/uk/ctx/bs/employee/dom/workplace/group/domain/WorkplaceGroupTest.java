package nts.uk.ctx.bs.employee.dom.workplace.group.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup.Require;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DomainServiceHelper;

@RunWith(JMockit.class)
public class WorkplaceGroupTest {
	
	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		NtsAssert.invokeGetters(workplaceGroup);
	}
	
	@Test
	public void getWpg() {
		
		WorkplaceGroup workplaceGroup = WorkplaceGroup.getWpg("01",new WorkplaceGroupCode("0000000001"), 
				new WorkplaceGroupName("00000000000000000011"), 
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));
		assertThat(workplaceGroup.getCID().equals("01")).isTrue();
	}
	
	@Test
	public void addAffWorkplaceGroup() {
		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		AffWorkplaceGroup affWorkplaceGroup = workplaceGroup.addAffWorkplaceGroup("01");
		assertThat(affWorkplaceGroup.getWKPGRPID().equals(workplaceGroup.getWKPGRPID())).isTrue();
	}
	
	@Test
	public void getAffWorkplace_empty() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		new Expectations() {
			{
				require.getWKPID(group.getCID(),group.getWKPGRPID());// dummy
			}
		};
		
		assertThat(group.getAffWorkplace(require).isEmpty()).isTrue();
	}
	

	@Test
	public void getAffWorkplace() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		List<String> lstWpgid = DomainServiceHelper.getLstId();
		new Expectations() {
			{
				require.getWKPID(group.getCID(),group.getWKPGRPID());// dummy
				result = lstWpgid;
			}
		};
		
		assertThat(group.getAffWorkplace(require).isEmpty()).isFalse();
	}
}
