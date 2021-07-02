package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
/**
 * 
 * @author tutk
 *
 */
public class RelieveTest {

	@Test
	public void getters() {
		Relieve data = StampHelper.getRelieveDefault();
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void testRelieve() {
		AuthcMethod authcMethod = AuthcMethod.valueOf(1);//dummy
		StampMeans stampMeans = StampMeans.valueOf(0);//dummy
		Relieve data = new Relieve(authcMethod, stampMeans);
		NtsAssert.invokeGetters(data);
	}
	
	// test Func convertStampmethodtostampSourceInfo
	@Test
	public void testFuncConvertStampmethodtostampSourceInfo() {
		GeneralDate date = GeneralDate.today();
		AuthcMethod authcMethod = AuthcMethod.valueOf(1);//dummy
		StampMeans stampMeans = StampMeans.valueOf(0);//dummy
		Relieve data = new Relieve(authcMethod, stampMeans);
		ReasonTimeChange reasonTimeChange =  data.convertStampmethodtostampSourceInfo(date);
		assertThat(reasonTimeChange.getTimeChangeMeans()).isEqualTo(TimeChangeMeans.REAL_STAMP);
		assertThat(reasonTimeChange.getEngravingMethod().get()).isEqualTo(EngravingMethod.TIME_RECORD_ID_INPUT);
	}

}
