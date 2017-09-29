/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigSetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigPK;

public class JpaWorkplaceConfigSetMemento implements WorkplaceConfigSetMemento{

	/** The entity. */
	private BsymtWkpConfig entity;
	
	public JpaWorkplaceConfigSetMemento(BsymtWkpConfig entity) {
		this.entity = entity;
	}

	@Override
	public void setCompanyId(String companyId) {
		BsymtWkpConfigPK pk = new BsymtWkpConfigPK();
		pk.setCid(companyId);
		this.entity.setBsymtWkpConfigPK(pk);
	}

	@Override
	public void setWkpConfigHistory(List<WorkplaceConfigHistory> wkpConfigHistory) {
		if (wkpConfigHistory.isEmpty()) {
			return;
		}
		WorkplaceConfigHistory wkpConfig = wkpConfigHistory.get(0);
		this.entity.getBsymtWkpConfigPK().setHistoryId(wkpConfig.getHistoryId());
		this.entity.setStrD(wkpConfig.getPeriod().getStartDate());
		this.entity.setEndD(wkpConfig.getPeriod().getEndDate());
	}

}
