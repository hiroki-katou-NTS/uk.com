package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class TopPageAlarmStampingTest {

	@Test
	public void getters() {
		List<TopPageAlarmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		
		TopPageAlarm topPageAlarm = new TopPageAlarm("DUMMY", GeneralDateTime.now(), ExistenceError.NO_ERROR, IsCancelled.NOT_CANCELLED, new ArrayList<>());
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(list, topPageAlarm);
		
		NtsAssert.invokeGetters(alarmStamping);
	}
	
	/**
	 * if (lsterror.isEmpty()) {
			TopPageAlarm arm = new TopPageAlarm(companyId, ExistenceError.NO_ERROR, lstEmployeeId); 
					
			return new TopPageAlarmStamping(new ArrayList<>(), arm);
		}
	 */
	
	@Test
	public void testGetLstErrorEmpty() {
		
		List<TopPageAlarmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping("DUMMY", lstsid, "DUMMY", lstsid);
	
		assertThat(alarmStamping.lstTopPageDetail).isEmpty();
	}
	
	/**
	 * lsterror not Empty
	 */
	
	@Test
	public void testGetLstNotErrorEmpty() {
		
		List<TopPageAlarmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageAlarmDetail("DUMMY", 1, "DUMMY"));
		
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		
		TopPageAlarmStamping alarmStamping1 = new TopPageAlarmStamping("DUMMY", lstsid, "DUMMY", lstsid);
		
		assertThat(alarmStamping1.lstTopPageDetail.get(0).getSid()).isEqualTo("DUMMY");
		assertThat(alarmStamping1.lstTopPageDetail.get(0).getSerialNumber()).isEqualTo(0);
		assertThat(alarmStamping1.lstTopPageDetail.get(0).getErrorMessage()).isEqualTo("DUMMY");
	}

}
