package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceReplaceResult;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice.AddWplOfWorkGrpService.Require;

public class AddWplOfWorkGrpServiceTest {
	
	@Injectable
	private Require require;
	
	@Test
	public void insert_false() {
		String wKPID = "000000000000000000000000000000000011";
		String wKPGRPID = "00000000000001";
		new Expectations() {
			{
				require.getByWKPID(
						wKPID);// dummy
				AffWorkplaceGroup affWorkplaceGroup = new AffWorkplaceGroup(wKPGRPID, wKPID);
				result = Optional.of(affWorkplaceGroup);
			}
		};
		
		WorkplaceReplaceResult workplaceReplaceResult = AddWplOfWorkGrpService.addWorkplace(require, DomainServiceHelper.Helper.DUMMY,wKPID);
		
		assertThat(workplaceReplaceResult.getPersistenceProcess().isPresent()).isFalse();
	}
	
	@Test
	public void insert_false1() {
		String wKPID = "000000000000000000000000000000000011";
		String wKPGRPID = "000000000000000000000000000000000011";
		new Expectations() {
			{
				require.getByWKPID(
						wKPID);// dummy
				AffWorkplaceGroup affWorkplaceGroup = new AffWorkplaceGroup(wKPGRPID, wKPID);
				result = Optional.of(affWorkplaceGroup);
			}
		};
		
		WorkplaceReplaceResult workplaceReplaceResult = AddWplOfWorkGrpService.addWorkplace(require, DomainServiceHelper.Helper.DUMMY,wKPID);
		assertThat(workplaceReplaceResult.getPersistenceProcess().isPresent()).isFalse();
	}
	
	@Test
	public void insert_success() {
		String wKPID = "000000000000000000000000000000000011";
		new Expectations() {
			{
				require.getByWKPID(
						wKPID);// dummy
			}
		};
		
		WorkplaceReplaceResult workplaceReplaceResult = AddWplOfWorkGrpService.addWorkplace(require, DomainServiceHelper.Helper.DUMMY,wKPID);
		
		NtsAssert.atomTask(
				() -> workplaceReplaceResult.getPersistenceProcess().get(),
				any -> require.insert((AffWorkplaceGroup) any.get())
		);
	}
	
}
