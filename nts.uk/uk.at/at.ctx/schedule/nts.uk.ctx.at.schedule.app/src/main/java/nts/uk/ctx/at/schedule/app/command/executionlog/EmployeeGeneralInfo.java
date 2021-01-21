package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
/**
 * 
 * @author phongtq
 *
 */
@Getter
public class EmployeeGeneralInfo {
	//社員の短時間勤務履歴一覧
	private List<WorkCondItemDto> listWorkingConItem;
	
	private List<ExEmploymentHistoryImported> employmentDto;

	private List<ExClassificationHistoryImported> classificationDto;

	private List<ExJobTitleHistoryImported> jobTitleDto;

	private List<ExWorkPlaceHistoryImported> workplaceDto;
}
