package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;

public class RegisterResultFromSupportableEmployeeTest {
	
	@Test
	public void testCreateWithError(
			@Injectable SupportableEmployee supportableEmployee) {
		String message = "message content";
		
		val result = RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, message);
		
		assertThat( result.isError() ).isTrue();
		assertThat( result.getAtomTaskList() ).isEmpty();
		assertThat( result.getErrorInfo().get().getSupportableEmployee() ).isEqualTo( supportableEmployee );
		assertThat( result.getErrorInfo().get().getErrorMessage() ).isEqualTo( message );
	}
	
	@Test
	public void testCreateWithoutError(
			@Injectable List<AtomTask> atomTaskList ) {
		
		val result = RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
		
		assertThat( result.isError() ).isFalse();
		assertThat( result.getErrorInfo() ).isEmpty();
		assertThat( result.getAtomTaskList() ).isEqualTo(atomTaskList);
	}
	
	@Test
	public void testCreateEmpty () {
		
		val result = RegisterResultFromSupportableEmployee.createEmpty();
		
		assertThat( result.isError() ).isFalse();
		assertThat( result.getErrorInfo() ).isEmpty();
		assertThat( result.getAtomTaskList() ).isEmpty();
	}

}
