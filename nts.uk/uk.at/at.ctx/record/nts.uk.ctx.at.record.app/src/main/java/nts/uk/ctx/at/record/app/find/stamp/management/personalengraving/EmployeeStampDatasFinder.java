package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;

/**
 * @author anhdt
 *    打刻入力の打刻履歴一覧を表示する
 */
@Stateless
public class EmployeeStampDatasFinder {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingFinder;
	
	public List<EmployeeStampInfo> getEmployeeStampData(DatePeriod period, String employeeId) {
		List<EmployeeStampInfo> domains = stampSettingFinder.getEmployeeStampDatas(period, employeeId);
		return domains;
	}
}
