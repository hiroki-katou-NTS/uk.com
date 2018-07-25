/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.statement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.generalinfo.classification.ExClassificationHistoryDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.employment.ExEmploymentHistoryDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.jobtitle.ExJobTitleHistoryDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.workplace.ExWorkPlaceHistoryDto;



/**
 * The Class EmployeeGeneralInfoDto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeGeneralInfoDto {
	
	/** The employment dto. */
	private List<ExEmploymentHistoryDto> employmentDto;
	
	/** The classification dto. */
	private List<ExClassificationHistoryDto> classificationDto;
	
	/** The job title dto. */
	private List<ExJobTitleHistoryDto> jobTitleDto;
	
	/** The workplace dto. */
	private List<ExWorkPlaceHistoryDto> workplaceDto;
}
