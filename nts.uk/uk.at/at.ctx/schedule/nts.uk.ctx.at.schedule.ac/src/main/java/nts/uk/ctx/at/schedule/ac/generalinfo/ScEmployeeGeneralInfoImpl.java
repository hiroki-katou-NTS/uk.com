package nts.uk.ctx.at.schedule.ac.generalinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.ScEmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkplaceHistItemImported;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ScEmployeeGeneralInfoImpl implements ScEmployeeGeneralInfoAdapter {

	@Inject
	public EmployeeGeneralInfoPub employeeGeneralInfoPub;

	@Override
	public EmployeeGeneralInfoImported getPerEmpInfo(List<String> employeeIds, DatePeriod period) {
		EmployeeGeneralInfoDto employeeGeneralInfoDto = this.employeeGeneralInfoPub.getPerEmpInfo(employeeIds, period);

		List<ExEmploymentHistoryImported> employmentImported = employeeGeneralInfoDto.getEmploymentDto().stream()
				.map(employmentHistory -> new ExEmploymentHistoryImported(employmentHistory.getEmployeeId(),
						employmentHistory.getEmploymentItems().stream().map(exEmpHistItem -> {
							return new ExEmploymentHistItemImported(exEmpHistItem.getHistoryId(),
									exEmpHistItem.getPeriod(), exEmpHistItem.getEmploymentCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExClassificationHistoryImported> exClassificationHistoryImported = employeeGeneralInfoDto
				.getClassificationDto().stream()
				.map(classificationHistory -> new ExClassificationHistoryImported(classificationHistory.getEmployeeId(),
						classificationHistory.getClassificationItems().stream().map(classificationHistItem -> {
							return new ExClassificationHistItemImported(classificationHistItem.getHistoryId(),
									classificationHistItem.getPeriod(), classificationHistItem.getClassificationCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExJobTitleHistoryImported> exJobTitleHistoryImported = employeeGeneralInfoDto.getJobTitleDto().stream()
				.map(jobTitle -> new ExJobTitleHistoryImported(jobTitle.getEmployeeId(),
						jobTitle.getJobTitleItems().stream().map(jobTitleItem -> {
							return new ExJobTitleHistItemImported(jobTitleItem.getHistoryId(), jobTitleItem.getPeriod(),
									jobTitleItem.getJobTitleId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExWorkPlaceHistoryImported> exWorkPlaceHistoryImported = employeeGeneralInfoDto.getWorkplaceDto().stream()
				.map(workPlaceHistory -> new ExWorkPlaceHistoryImported(workPlaceHistory.getEmployeeId(),
						workPlaceHistory.getWorkplaceItems().stream().map(workPlaceHistoryItem -> {
							return new ExWorkplaceHistItemImported(workPlaceHistoryItem.getHistoryId(),
									workPlaceHistoryItem.getPeriod(), workPlaceHistoryItem.getWorkplaceId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		return new EmployeeGeneralInfoImported(employmentImported, exClassificationHistoryImported,
				exJobTitleHistoryImported, exWorkPlaceHistoryImported);
	}

}
