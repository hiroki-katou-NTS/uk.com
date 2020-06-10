package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class TopPageAlarmStampingTest {

	@Test
	public void getters() {
		List<TopPageArmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		lstsid.add("DUMMY");
		
		TopPageArm topPageArm = new TopPageArm("DUMMY", GeneralDateTime.now(), ExistenceError.NO_ERROR, IsCancelled.NOT_CANCELLED, new ArrayList<>());
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(list, topPageArm);
		
		NtsAssert.invokeGetters(alarmStamping);
	}
	
	@Test
	public void get_lsterror_empty() {
		
		List<TopPageArmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 2, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 3, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 4, "DUMMY"));
		
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(list, new TopPageArm(ExistenceError.HAVE_ERROR, lstsid));
		
//		assertThat(topPageAlarmStamping.lstTopPageDetail).isEmpty();
	}

}
