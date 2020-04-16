package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice.DeleteWorkplaceGroupService.Require;

@RunWith(JMockit.class)
public class DeleteWorkplaceGroupServiceTest {

	@Injectable
	private Require require;

	@Test
	public void delete_success() {
		String wKPGRPID = "000000000000000000000000000000000011";

		NtsAssert.atomTask(() -> DeleteWorkplaceGroupService.delete(require, wKPGRPID),
				any -> require.delete(wKPGRPID), any -> require.deleteByWKPGRPID(wKPGRPID));
	}
}
