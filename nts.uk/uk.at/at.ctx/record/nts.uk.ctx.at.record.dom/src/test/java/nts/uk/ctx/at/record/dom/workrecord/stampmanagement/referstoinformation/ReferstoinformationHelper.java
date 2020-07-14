package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import java.util.ArrayList;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.EmployeeVeinInformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerVeininformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.VeinContent;

/**
 * 
 * @author chungnt
 *
 */

public class ReferstoinformationHelper { 
	
	public static FingerVeininformation getFingerVeininformationDefault() {
		return new FingerVeininformation(FingerType.INDEXFINGER, //dummy
				new VeinContent("VeinContent")); //dummy
	}
	
	public static EmployeeVeinInformation getEmployeeVeinInformationDefault() {
		return new EmployeeVeinInformation("0000-000000000001", 
				"sid",
				new ArrayList<FingerVeininformation>());
	}

}
