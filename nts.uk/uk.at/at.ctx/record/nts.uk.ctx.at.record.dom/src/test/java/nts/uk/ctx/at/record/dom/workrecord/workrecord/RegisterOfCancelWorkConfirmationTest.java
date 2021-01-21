package nts.uk.ctx.at.record.dom.workrecord.workrecord;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.RegisterOfCancelWorkConfirmation.Require;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class RegisterOfCancelWorkConfirmationTest {
	
	@Injectable
	private Require require;
	
	/**
	 * whetherToCancel = true
	 * 
	 * AtomTask : require insert()
	 * 
	 * AtomTask atomTask = AtomTask.of(() -> {
				require.insert(employmentConfirmed);
			});
	 */
	
	@Test
	public void test() {
		
		NtsAssert.atomTask(
				() -> RegisterOfCancelWorkConfirmation.get(require,
						new CompanyId("DUMMY"),
						new WorkplaceId("DUMMY"),
						ClosureId.ClosureFour,
						new YearMonth(2020),
						Optional.of("DUMMY"),
						Optional.of(GeneralDateTime.now()),
						false).get(),
				any -> require.insert(any.get()));
	}
	
	/**
	 * !optEmploymentConfirmed.isPresent()
	 * require.get() = empty
	 */
	@Test
	public void test_1() {
		
		new Expectations() {
			{
				require.get("DUMMY", "DUMMY", ClosureId.ClosureFour, new YearMonth(2020));

			}
		};
		
		Optional<AtomTask> atom = RegisterOfCancelWorkConfirmation.get(require,
				new CompanyId("DUMMY"),
				new WorkplaceId("DUMMY"),
				ClosureId.ClosureFour,
				new YearMonth(2020),
				Optional.of("DUMMY"),
				Optional.of(GeneralDateTime.now()),
				true);
		
		assertThat(atom).isEmpty();
	}
	
	/**
	 * whetherToCancel = true
	 * require.delete
	 * 
	 * AtomTask atomTask = AtomTask.of(() -> {
			require.delete(optEmploymentConfirmed.get());
		});
	 */
	@Test
	public void test_2() {
		
		new Expectations() {
			{
				require.get("DUMMY", "DUMMY", ClosureId.ClosureFour, new YearMonth(2020));
				
				EmploymentConfirmed confirmed = new EmploymentConfirmed(new CompanyId("DUMMY"),
						new WorkplaceId("DUMMY"),
						ClosureId.ClosureFour,
						new YearMonth(2020),
						"DUMMY",
						GeneralDateTime.now());
				
				result = Optional.of(confirmed);
				
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterOfCancelWorkConfirmation.get(require,
						new CompanyId("DUMMY"),
						new WorkplaceId("DUMMY"),
						ClosureId.ClosureFour,
						new YearMonth(2020),
						Optional.of("DUMMY"),
						Optional.of(GeneralDateTime.now()),
						true).get(),
				any -> require.delete(any.get()));
		
	}
}
