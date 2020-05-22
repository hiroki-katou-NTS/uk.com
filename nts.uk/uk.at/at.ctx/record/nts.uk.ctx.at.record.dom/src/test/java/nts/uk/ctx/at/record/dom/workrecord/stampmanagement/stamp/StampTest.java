package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author tutk
 *
 */
public class StampTest {

	@Test
	public void getters() {
		Stamp stamp = StampHelper.getStampDefault();
		NtsAssert.invokeGetters(stamp);
	}
	
	@Test
	public void testStamp_contructor_C0() {
		StampNumber cardNumber = new StampNumber("cardNumber");//dummy
		GeneralDateTime stampDateTime = GeneralDateTime.now();
		Relieve relieve =  StampHelper.getRelieveDefault();
		StampType type = StampHelper.getStampTypeDefault();
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();
		StampLocationInfor locationInfor = StampHelper.getStampLocationInforDefault();
		boolean reflectedCategory = false;
		ContractCode contactCode = new ContractCode("aaaa");
		Stamp stamp = new Stamp(contactCode, cardNumber, stampDateTime, relieve, type, refActualResults,
				reflectedCategory, locationInfor);
		NtsAssert.invokeGetters(stamp);
	}
	
	@Test
	public void testStamp_contructor_C1() {
		StampNumber cardNumber = new StampNumber("cardNumber");//dummy
		GeneralDateTime stampDateTime = GeneralDateTime.now();
		Relieve relieve =  StampHelper.getRelieveDefault();
		StampType type = StampHelper.getStampTypeDefault();
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();
		StampLocationInfor locationInfor = StampHelper.getStampLocationInforDefault();
		ContractCode contactCode = new ContractCode(AppContexts.user().companyCode());
		Stamp stamp = new Stamp(contactCode, cardNumber, stampDateTime, relieve, type, refActualResults, locationInfor);
		assertThat(stamp.isReflectedCategory()).isFalse();
		NtsAssert.invokeGetters(stamp);
	}
	
	@Test
	public void testSetAttendanceTime() {
		AttendanceTime attendanceTime = new AttendanceTime(10);//dummy
		Stamp stamp = StampHelper.getStampDefault();
		stamp.setAttendanceTime(attendanceTime);
		assertThat(stamp.getAttendanceTime().get()).isEqualTo(attendanceTime);
	}

}
