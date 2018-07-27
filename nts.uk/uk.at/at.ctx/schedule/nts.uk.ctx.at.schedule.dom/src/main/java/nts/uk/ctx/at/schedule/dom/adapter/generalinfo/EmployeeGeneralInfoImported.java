package nts.uk.ctx.at.schedule.dom.adapter.generalinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;

@AllArgsConstructor
@Getter
public class EmployeeGeneralInfoImported {

	private List<ExEmploymentHistoryImported> employmentDto;

	private List<ExClassificationHistoryImported> classificationDto;

	private List<ExJobTitleHistoryImported> jobTitleDto;

	private List<ExWorkPlaceHistoryImported> workplaceDto;

}
