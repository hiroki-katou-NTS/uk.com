package nts.uk.ctx.at.function.ac.alarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeSprPubAlarmAdapter;
import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.PersonInfoSprExport;

@Stateless
public class EmployeeSprPubAlarmAdapterImpl implements EmployeeSprPubAlarmAdapter {
	@Inject
	private EmployeeSprPub employeeSprPub;

	@Override
	public String getEmployeeNameBySId(String sID) {
		String employeeName = "";
		PersonInfoSprExport persionInfo = employeeSprPub.getPersonInfo(sID);
		if (persionInfo != null) {
			employeeName = persionInfo.getBusinessName();
		}
		return employeeName;
	}

}
