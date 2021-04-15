package nts.uk.ctx.bs.employee.dom.workplace.group.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DomainServiceHelper;

@RunWith(JMockit.class)
public class WorkplaceGroupTest {

	@Injectable
	private WorkplaceGroup.Require require;

	@Test
	public void getters() {
		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		NtsAssert.invokeGetters(workplaceGroup);
	}

	@Test
	public void create() {

		WorkplaceGroup workplaceGroup = WorkplaceGroup.create(
					"01"
				,	new WorkplaceGroupCode("0000000001")
				,	new WorkplaceGroupName("00000000000000000011")
				,	WorkplaceGroupType.MEDICAL_CARE
			);

		assertThat( workplaceGroup.getCID() ).isEqualTo( "01" );
		assertThat( workplaceGroup.getCode() ).isEqualTo( new WorkplaceGroupCode("0000000001") );
		assertThat( workplaceGroup.getName() ).isEqualTo( new WorkplaceGroupName("00000000000000000011") );
		assertThat( workplaceGroup.getType() ).isEqualTo( WorkplaceGroupType.MEDICAL_CARE );

	}

	@Test
	public void addAffWorkplaceGroup() {

		WorkplaceGroup workplaceGroup = DomainServiceHelper.Helper.DUMMY;
		AffWorkplaceGroup affWorkplaceGroup = workplaceGroup.addAffWorkplaceGroup("01");

		assertThat( affWorkplaceGroup.getWorkplaceGroupId() ).isEqualTo( workplaceGroup.getId() );

	}

	@Test
	public void getAffWorkplace_empty() {

		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		new Expectations() {{
			require.getWorkplacesInGroup(group.getCID(), group.getId());
		}};

		val result = group.getAffWorkplace( require );

		assertThat( result ).isEmpty();

	}

	@Test
	public void getAffWorkplace() {

		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
		List<String> lstWpgid = DomainServiceHelper.getLstId();
		new Expectations() {
			{
				require.getWorkplacesInGroup(group.getCID(),group.getId());
				result = lstWpgid;
			}
		};

		val result = group.getAffWorkplace( require );

		assertThat( result ).isNotEmpty();
		assertThat( result ).containsExactlyInAnyOrderElementsOf( lstWpgid );

	}

}
