package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
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
		Stamp stamp = StampHelper.getStampDefault();//dummy
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampAtr, stamp);
		NtsAssert.invokeGetters(stampInfoDisp);
	}

	@Test
	public void testStampInfoDisp_C1() {
		StampNumber stampNumber = new StampNumber("stampNumber");//dummy
		GeneralDateTime stampDatetime = GeneralDateTime.now();//dummy
		StampRecord stampRecord = StampRecordHelper.getStampRecord();//dummy
		Optional<Stamp> stamp = Optional.of(StampHelper.getStampDefault());//dummy
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampRecord, stamp);
		NtsAssert.invokeGetters(stampInfoDisp);
		
	}
	
	@Test
	public void testCreateStamp_stamp_is_null() {
		StampNumber stampNumber = new StampNumber("stampNumber");//dummy
		GeneralDateTime stampDatetime = GeneralDateTime.now();//dummy
		StampRecord stampRecord = StampRecordHelper.getStampRecord();//dummy
		Optional<Stamp> stamp = Optional.empty();
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampRecord, stamp);
		assertThat(stampInfoDisp.getStampAtr()).isEqualTo(stampRecord.getRevervationAtr().nameId);
		
	}
	
	/**
	 * stampArt == true
	 * revervationAtr == ReservationArt.NONE
	 */
	@Test
	public void testCreateStamp_stamp_not_null_1() {
		StampNumber stampNumber = new StampNumber("stampNumber");//dummy
		GeneralDateTime stampDatetime = GeneralDateTime.now();//dummy
		StampRecord stampRecord = StampRecordHelper.getStampSetStampArtAndRevervationAtr(true, ReservationArt.NONE);
		Optional<Stamp> stamp = Optional.of(StampHelper.getStampDefault());
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampRecord, stamp);
		assertThat(stampInfoDisp.getStampAtr()).isEqualTo(stamp.get().getType().createStampTypeDisplay());
	}
	
	/**
	 * stampArt == true
	 * revervationAtr != ReservationArt.NONE
	 */
	@Test
	public void testCreateStamp_stamp_not_null_2() {
		StampNumber stampNumber = new StampNumber("stampNumber");
		GeneralDateTime stampDatetime = GeneralDateTime.now();
		StampRecord stampRecord = StampRecordHelper.getStampSetStampArtAndRevervationAtr(true, ReservationArt.CANCEL_RESERVATION);
		Optional<Stamp> stamp = Optional.of(StampHelper.getStampDefault());
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampRecord, stamp);
		assertThat(stampInfoDisp.getStampAtr()).isEqualTo(stamp.get().getType().createStampTypeDisplay()+"+"+stampRecord.getRevervationAtr().nameId);
	}
	/**
	 * stampArt != true
	 * revervationAtr != ReservationArt.NONE
	 */
	@Test
	public void testCreateStamp_stamp_not_null_3() {
		StampNumber stampNumber = new StampNumber("stampNumber");
		GeneralDateTime stampDatetime = GeneralDateTime.now();
		StampRecord stampRecord = StampRecordHelper.getStampSetStampArtAndRevervationAtr(false, ReservationArt.CANCEL_RESERVATION);
		Optional<Stamp> stamp = Optional.of(StampHelper.getStampDefault());
		StampInfoDisp stampInfoDisp = new StampInfoDisp(stampNumber, stampDatetime, stampRecord, stamp);
		assertThat(stampInfoDisp.getStampAtr()).isEqualTo(stampRecord.getRevervationAtr().nameId);
	}
}
