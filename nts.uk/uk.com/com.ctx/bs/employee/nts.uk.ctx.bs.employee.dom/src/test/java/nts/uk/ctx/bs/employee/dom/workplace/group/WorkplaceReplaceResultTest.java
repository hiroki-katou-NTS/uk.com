package nts.uk.ctx.bs.employee.dom.workplace.group;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.DomainServiceHelper;
/**
 * 
 * @author phongtq
 *
 */
public class WorkplaceReplaceResultTest {
	AtomTask atomTask =  AtomTask.of(() -> {});
	@Test
	public void getters() {
		WorkplaceReplaceResult workplaceReplaceResult = DomainServiceHelper.getWorkplaceReplaceResultDefault(0);
		NtsAssert.invokeGetters(workplaceReplaceResult);
	}
	
	@Test
	public void getters1() {
		WorkplaceReplaceResult workplaceReplaceResult = new WorkplaceReplaceResult();
		NtsAssert.invokeGetters(workplaceReplaceResult);
	}
	
	
	@Test
	public void testAdd() {
		WorkplaceReplaceResult workplaceReplaceResult = DomainServiceHelper.getWorkplaceReplaceResultDefault(0);
		WorkplaceReplaceResult.add(Optional.of(atomTask));
		assertThat(workplaceReplaceResult).isNotNull();
	}
	
	@Test
	public void testDelete () {
		WorkplaceReplaceResult workplaceReplaceResult = DomainServiceHelper.getWorkplaceReplaceResultDefault(1);
		WorkplaceReplaceResult.delete(Optional.of(atomTask));
		assertThat(workplaceReplaceResult).isNotNull();
	}
	
	@Test
	public void testAlreadyBelong () {
		WorkplaceReplaceResult workplaceReplaceResult = DomainServiceHelper.getWorkplaceReplaceResultDefault(2);
		WorkplaceReplaceResult.alreadyBelong();
		assertThat(workplaceReplaceResult).isNotNull();
	}
	
	@Test
	public void testBelongAnother () {
		WorkplaceReplaceResult workplaceReplaceResult = DomainServiceHelper.getWorkplaceReplaceResultDefault(3);
		WorkplaceReplaceResult.belongAnother();
		assertThat(workplaceReplaceResult).isNotNull();
	}
	
}
