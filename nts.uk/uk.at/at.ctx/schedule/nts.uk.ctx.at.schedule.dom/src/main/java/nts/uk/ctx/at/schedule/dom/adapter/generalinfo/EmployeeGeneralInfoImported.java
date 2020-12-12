package nts.uk.ctx.at.schedule.dom.adapter.generalinfo;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHis;

@Getter
public class EmployeeGeneralInfoImported {

	private List<ExEmploymentHistoryImported> employmentDto;

	private List<ExClassificationHistoryImported> classificationDto;

	private List<ExJobTitleHistoryImported> jobTitleDto;

	private List<ExWorkPlaceHistoryImported> workplaceDto;
	
	private List<BusinessTypeOfEmployeeHis> listBusTypeOfEmpHis;

	public EmployeeGeneralInfoImported(List<ExEmploymentHistoryImported> employmentDto,
			List<ExClassificationHistoryImported> classificationDto, List<ExJobTitleHistoryImported> jobTitleDto,
			List<ExWorkPlaceHistoryImported> workplaceDto) {
		super();
		this.employmentDto = employmentDto;
		this.classificationDto = classificationDto;
		this.jobTitleDto = jobTitleDto;
		this.workplaceDto = workplaceDto;
	}

	public EmployeeGeneralInfoImported(List<ExEmploymentHistoryImported> employmentDto,
			List<ExClassificationHistoryImported> classificationDto, List<ExJobTitleHistoryImported> jobTitleDto,
			List<ExWorkPlaceHistoryImported> workplaceDto, List<BusinessTypeOfEmployeeHis> listBusTypeOfEmpHis) {
		super();
		this.employmentDto = employmentDto;
		this.classificationDto = classificationDto;
		this.jobTitleDto = jobTitleDto;
		this.workplaceDto = workplaceDto;
		this.listBusTypeOfEmpHis = listBusTypeOfEmpHis;
	}

	public void setListBusTypeOfEmpHis(List<BusinessTypeOfEmployeeHis> listBusTypeOfEmpHis) {
		this.listBusTypeOfEmpHis = listBusTypeOfEmpHis;
	}
	
	

}
