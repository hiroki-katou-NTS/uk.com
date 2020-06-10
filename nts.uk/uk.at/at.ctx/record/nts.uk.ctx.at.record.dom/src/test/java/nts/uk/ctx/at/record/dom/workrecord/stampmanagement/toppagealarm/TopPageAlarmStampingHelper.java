package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.ArrayList;
import java.util.List;

public class TopPageAlarmStampingHelper {

	public static TopPageAlarmStamping getDefault() {
		
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
		
		TopPageArm topPageArm = new TopPageArm(ExistenceError.HAVE_ERROR, lstsid);
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(list, topPageArm);
		
		return alarmStamping;
	}
	
public static TopPageAlarmStamping getListErrorNull() {
		
		List<TopPageArmDetail> list = new ArrayList<>();
		List<String> lstsid = new ArrayList<>();
		
		list.add(new TopPageArmDetail("DUMMY", 1, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 2, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 3, "DUMMY"));
		list.add(new TopPageArmDetail("DUMMY", 4, "DUMMY"));
		
		TopPageAlarmStamping alarmStamping = new TopPageAlarmStamping(list, new TopPageArm(ExistenceError.HAVE_ERROR, lstsid));
		
		return alarmStamping;
	}
	
}
