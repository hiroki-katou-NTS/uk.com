/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Category;

/**
 * The Class VacationAcquisitionRuleDto.
 */
@Builder
public class VacationAcquisitionRuleDto implements AcquisitionRuleSetMemento {

	/** The company id. */
	private String companyId;

	/** The setting class. */
	private Category settingClass;

	/** The va ac orders. */
	private List<VacationAcquisitionOrderItemDto> vaAcOrders;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionRuleSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacationacquisitionrule.VaAcRuleSetMemento#
	 * setSettingclassification(nts.uk.ctx.pr.core.dom.vacationacquisitionrule.
	 * Settingclassification)
	 */
	@Override
	public void setSettingclassification(Category settingclassification) {
		this.settingClass = settingclassification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacationacquisitionrule.VaAcRuleSetMemento#
	 * setAcquisitionOrder(java.util.List)
	 */
	@Override
	public void setAcquisitionOrder(List<AcquisitionOrder> listVacationAcquisitionOrder) {
		this.vaAcOrders = listVacationAcquisitionOrder.stream().map(domain -> {
			VacationAcquisitionOrderItemDto dto = VacationAcquisitionOrderItemDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
