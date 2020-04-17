package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import org.junit.Test;

import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public class StampDataReflectResultTest {

	@Test
	public void getters() {
		StampDataReflectResult stampDataReflectResult = DomainServiceHeplper.getStampDataReflectResultDefault();
		NtsAssert.invokeGetters(stampDataReflectResult);
	}
	
	@Test
	public void testStampDataReflectResult () {
		Optional<GeneralDate> reflectDate = Optional.of(GeneralDate.today()); //dummy
		AtomTask atomTask =  AtomTask.of(() -> {});//dummy
		StampDataReflectResult stampDataReflectResult = new StampDataReflectResult(reflectDate, atomTask);
		NtsAssert.invokeGetters(stampDataReflectResult);
	}
}
