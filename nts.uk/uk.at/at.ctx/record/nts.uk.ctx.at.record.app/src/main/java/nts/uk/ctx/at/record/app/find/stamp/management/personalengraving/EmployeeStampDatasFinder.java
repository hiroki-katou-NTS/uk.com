package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;

/**
 * @author anhdt
 * 打刻入力の打刻履歴一覧を表示する
 */
public class EmployeeStampDatasFinder {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingFinder;
	
	public List<StampDataOfEmployeesDto> getEmployeeStampData(DatePeriod period, String employeeId) {
		List<StampDataOfEmployees> domains = stampSettingFinder.getEmployeeStampDatas(period, employeeId);
		return domains.stream().map(r -> new StampDataOfEmployeesDto(r)).collect(Collectors.toList());
	}
}
