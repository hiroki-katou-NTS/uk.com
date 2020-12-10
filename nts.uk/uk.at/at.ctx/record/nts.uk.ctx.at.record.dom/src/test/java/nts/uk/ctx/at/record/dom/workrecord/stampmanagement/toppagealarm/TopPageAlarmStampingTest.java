package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class TopPageAlarmStampingTest {

	@Test
	public void getters() {
		List<TopPageAlarmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageAlarmDetail("DUMMY1", 1, "DUMMY01"));
		list.add(new TopPageAlarmDetail("DUMMY2", 1, "DUMMY02"));
		list.add(new TopPageAlarmDetail("DUMMY3", 1, "DUMMY03"));
		list.add(new TopPageAlarmDetail("DUMMY4", 1, "DUMMY04"));
		
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		
		TopPageAlarmMgrStamp topPageAlarm = new TopPageAlarmMgrStamp("DUMMY", GeneralDateTime.now(), ExistenceError.NO_ERROR, IsCancelled.NOT_CANCELLED, new ArrayList<>());
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
		
		List<String> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		lstsid.add("123456789");
		lstsid.add("987654321");
		
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping("DUMMY", list, "DUMMY", list);
	
		assertThat(alarmStamping.getLstTopPageDetail()).isEmpty();
	}
	
	/**
	 * lsterror not Empty
	 */
	
	@Test
	public void testGetLstNotErrorEmpty() {
		
		List<TopPageAlarmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageAlarmDetail("DUMMY1", 1, "DUMMY01"));
		list.add(new TopPageAlarmDetail("DUMMY2", 1, "DUMMY02"));
		list.add(new TopPageAlarmDetail("DUMMY3", 1, "DUMMY03"));
		list.add(new TopPageAlarmDetail("DUMMY4", 1, "DUMMY04"));
		
		lstsid.add("123456");
		lstsid.add("234567");
		lstsid.add("345678");
		lstsid.add("456789");
		
		TopPageAlarmStamping alarmStamping1 = new TopPageAlarmStamping("DUMMY", lstsid, "DUMMY", list.stream().map(m -> m.getErrorMessage()).collect(Collectors.toList()));
		
		assertThat(alarmStamping1.getLstTopPageDetail().get(0).getSid_tgt()).isEqualTo("DUMMY");
		assertThat(alarmStamping1.getLstTopPageDetail().get(0).getSerialNumber()).isEqualTo(0);
		assertThat(alarmStamping1.getLstTopPageDetail().get(0).getErrorMessage()).isEqualTo("DUMMY1");
		assertThat(alarmStamping1.getPageAlarm().getLstManagementEmployee().get(0).getSid_mgr()).isEqualTo("123456");
	}

}
