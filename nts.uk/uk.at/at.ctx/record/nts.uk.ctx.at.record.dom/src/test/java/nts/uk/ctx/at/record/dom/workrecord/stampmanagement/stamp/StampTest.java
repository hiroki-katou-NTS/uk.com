package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
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

	/**
	 * Test C1 初回打刻データを作成する
	 */
	
	@Test
	public void testStamp_contructor_C1() {
		StampNumber cardNumber = new StampNumber("cardNumber");//dummy
		GeneralDateTime stampDateTime = GeneralDateTime.now();
		Relieve relieve =  StampHelper.getRelieveDefault();
		StampType type = StampHelper.getStampTypeDefault();
		RefectActualResult refActualResults = StampHelper.getRefectActualResultDefault();
		Optional<GeoCoordinate> locationInfor = Optional.ofNullable(StampHelper.getGeoCoordinateDefault()) ;
		ContractCode contactCode = new ContractCode("DUMMY");
		Stamp stamp = new Stamp(contactCode, cardNumber, stampDateTime, relieve, type, refActualResults, locationInfor);
		assertThat(stamp.isReflectedCategory()).isFalse();
		NtsAssert.invokeGetters(stamp);
	}
	
	/**
	 * Test AttendanceTime
	 */
	@Test
	public void testSetAttendanceTime() {
		AttendanceTime attendanceTime = new AttendanceTime(10);//dummy
		Stamp stamp = StampHelper.getStampDefault();
		stamp.setAttendanceTime(attendanceTime);
		assertThat(stamp.getAttendanceTime().get()).isEqualTo(attendanceTime);
	}
	
	/**
	 * Test func convertToAttendanceStamp 
	 */
	@Test
	public void testConvertToAttendanceStamp() {
		GeneralDate date = GeneralDate.ymd(2021, 3, 15);
		Stamp stamp = StampHelper.getStampDefault();
		WorkTimeInformation wt = stamp.convertToAttendanceStamp(date);
		assertThat(wt.getTimeWithDay().get().valueAsMinutes()).isEqualTo(80);
		assertThat(wt.getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.REAL_STAMP);
		assertThat(wt.getReasonTimeChange().getEngravingMethod().get()).isEqualTo(EngravingMethod.TIME_RECORD_ID_INPUT);
	}

}
