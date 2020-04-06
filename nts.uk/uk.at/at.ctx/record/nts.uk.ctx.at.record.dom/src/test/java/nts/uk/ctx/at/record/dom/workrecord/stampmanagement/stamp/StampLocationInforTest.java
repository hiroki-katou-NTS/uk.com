package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.gul.location.GeoCoordinate;
/**
 * 
 * @author tutk
 *
 */
public class StampLocationInforTest {
	
	@Test
	public void getters() {
		StampLocationInfor stampLocationInfor = StampHelper.getStampLocationInforDefault();
		NtsAssert.invokeGetters(stampLocationInfor);
	}

	@Test
	public void testStampLocationInfor() {
		boolean outsideAreaAtr = true; //dummy
		GeoCoordinate positionInfor =  new GeoCoordinate(1, 2); //dummy
		StampLocationInfor stampLocationInfor = new StampLocationInfor(
				outsideAreaAtr, 
				positionInfor);
		NtsAssert.invokeGetters(stampLocationInfor); 
	}

}
