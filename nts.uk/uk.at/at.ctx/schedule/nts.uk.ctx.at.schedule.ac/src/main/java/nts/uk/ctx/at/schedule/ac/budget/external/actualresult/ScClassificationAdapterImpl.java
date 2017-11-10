/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ac.budget.external.actualresult;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.ScClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ClassificationDto;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScClassificationAdapterImpl implements ScClassificationAdapter{

	/** The sy classification pub. */
	@Inject
	private SyClassificationPub syClassificationPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.adapter.ScClassificationAdapter#findByDate(
	 * java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ClassificationDto> findByDate(String employeeId, GeneralDate baseDate) {
		return syClassificationPub.findSClsHistBySid(AppContexts.user().companyId(), employeeId, baseDate)
				.map(export -> this.toDto(export));
	}
	
	/**
	 * To dto.
	 *
	 * @param export the export
	 * @return the classification dto
	 */
	private ClassificationDto toDto(SClsHistExport export) {
		ClassificationDto dto = new ClassificationDto();
		dto.setClassificationCode(export.getClassificationCode());
		dto.setClassificationName(export.getClassificationName());
		dto.setEmployeeId(export.getEmployeeId());
		dto.setPeriod(export.getPeriod());
		return dto;
	}

}
