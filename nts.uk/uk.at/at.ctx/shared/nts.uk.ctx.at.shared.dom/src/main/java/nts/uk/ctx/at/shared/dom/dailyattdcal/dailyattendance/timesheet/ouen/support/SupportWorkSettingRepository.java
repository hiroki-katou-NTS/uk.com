package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.support;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSetting;

public interface SupportWorkSettingRepository {
	public void insert(SupportWorkSetting domain);

	public void update(SupportWorkSetting domain);

	public SupportWorkSetting get(String cid);
	
	public void delete(SupportWorkSetting domain);
}