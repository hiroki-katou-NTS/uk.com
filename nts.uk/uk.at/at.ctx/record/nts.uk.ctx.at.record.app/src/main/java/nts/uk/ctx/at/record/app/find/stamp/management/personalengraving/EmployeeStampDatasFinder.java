package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampRecordDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;

/**
 * @author anhdt
 * 打刻入力の打刻履歴一覧を表示する
 */
@Stateless
public class EmployeeStampDatasFinder {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingFinder;
	
	public List<StampRecordDto> getEmployeeStampData(DatePeriod period, String employeeId) {
		List<StampDataOfEmployees> domains = stampSettingFinder.getEmployeeStampDatas(period, employeeId);
		List<StampDataOfEmployeesDto> stampDataWrp = domains.stream()
				.map(r -> new StampDataOfEmployeesDto(r))
				.collect(Collectors.toList());
		List<StampRecordDto> results = new ArrayList<>();
		for(StampDataOfEmployeesDto st : stampDataWrp) {
			results.addAll(st.getStampRecords());
		}
		
		return results;
	}
}
