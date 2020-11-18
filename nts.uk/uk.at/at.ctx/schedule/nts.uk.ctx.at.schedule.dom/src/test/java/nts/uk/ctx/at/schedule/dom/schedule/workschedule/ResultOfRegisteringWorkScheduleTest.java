package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import mockit.Injectable;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

public class ResultOfRegisteringWorkScheduleTest {
	
	@Test
	public void testCreate(@Injectable AtomTask atomTask) {
		
		ResultOfRegisteringWorkSchedule result = ResultOfRegisteringWorkSchedule.create(atomTask);
		
		assertThat( result.isHasError() ).isFalse();
		assertThat( result.getErrorInfomation() ).isEmpty();
		assertThat( result.getAtomTask().get() ).isEqualTo( atomTask );
	}
	
	@Test
	public void testCreateWithError() {
		
		ResultOfRegisteringWorkSchedule result = ResultOfRegisteringWorkSchedule
				.createWithError("empId", GeneralDate.ymd(2020, 11, 1), "msg");
		
		assertThat( result.isHasError() ).isTrue();
		assertThat( result.getErrorInfomation().get(0).getErrorMessage() ).isEqualTo( "msg" );
		assertThat( result.getAtomTask() ).isEmpty();
		
	}
	
	@Test
	public void testCreateWithErrorList(
			@Injectable ErrorInfoOfWorkSchedule error1,
			@Injectable ErrorInfoOfWorkSchedule error2
			) {
		
		List<ErrorInfoOfWorkSchedule> errorInfoList = Arrays.asList(error1, error2);
		
		ResultOfRegisteringWorkSchedule result = ResultOfRegisteringWorkSchedule.createWithErrorList(errorInfoList);
		
		assertThat( result.isHasError() ).isTrue();
		assertThat( result.getErrorInfomation() ).extracting( d -> d)
												.containsExactly( error1, error2);
		assertThat( result.getAtomTask() ).isEmpty();
	}
	

}
