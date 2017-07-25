/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleSetMemento;

/**
 * The Class VacationAcquisitionRuleDto.
 */
@Builder
public class AcquisitionRuleDto implements AcquisitionRuleSetMemento {

	/** The company id. */
	public String companyId;

	/** The category. */
	public int category;

	/** The va ac orders. */
	public List<AcquisitionOrderItemDto> vaAcOrders;

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
	public void setCategory(ManageDistinct category) {
		this.category = category.value;
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
			AcquisitionOrderItemDto dto = AcquisitionOrderItemDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
