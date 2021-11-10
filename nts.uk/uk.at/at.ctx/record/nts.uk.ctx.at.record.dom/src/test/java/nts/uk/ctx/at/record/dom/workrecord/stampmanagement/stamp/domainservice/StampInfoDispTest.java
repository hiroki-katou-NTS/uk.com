package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
/**
 * 
 * @author tutk
 *
 */
public class StampInfoDispTest {
	@Test
	public void getters() {
		StampInfoDisp stampInfoDisp = DomainServiceHeplper.getStampInfoDispDefault();
		NtsAssert.invokeGetters(stampInfoDisp);
	}

	@Test
	public void testStampInfoDisp_C0() {
		StampNumber stampNumber = new StampNumber("stampNumber"); //dummy
		GeneralDateTime stampDatetime = GeneralDateTime.now();//dummy
		String stampAtr = "abc";//dummy
		Optional<Stamp> stamp = Optional.of(StampHelper.getStampDefault());// dummy
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampAtr, stamp);
		NtsAssert.invokeGetters(stampInfoDisp);
	}
}
