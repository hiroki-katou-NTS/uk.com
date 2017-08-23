/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaCompanyEstablishmentGetMemento.
 */
public class JpaCompanyEstablishmentSetMemento implements CompanyEstablishmentSetMemento{
	
	public static final int FIRST_TIME = 0;
	
	/** The estimate time companys. */
	private List<KscmtEstTimeComSet> estimateTimeCompanys;
	
	/** The estimate price companys. */
	private List<KscmtEstPriceComSet> estimatePriceCompanys;
	
	
	/**
	 * Instantiates a new jpa company establishment get memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 */
	public JpaCompanyEstablishmentSetMemento(List<KscmtEstTimeComSet> estimateTimeCompanys,
			List<KscmtEstPriceComSet> estimatePriceCompanys) {
		this.estimateTimeCompanys = estimateTimeCompanys;
		this.estimatePriceCompanys = estimatePriceCompanys;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.estimateTimeCompanys.forEach(estimateTime -> {
			estimateTime.getKscmtEstTimeComSetPK().setCid(companyId.v());
		});
		
		this.estimatePriceCompanys.forEach(esTimatePrice->{
			esTimatePrice.getKscmtEstPriceComSetPK().setCid(companyId.v());
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentSetMemento#setTargetYear(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.Year)
	 */
	@Override
	public void setTargetYear(Year targetYear) {
		this.estimateTimeCompanys.forEach(estimateTime -> {
			estimateTime.getKscmtEstTimeComSetPK().setTargetYear(targetYear.v());
		});
		
		this.estimatePriceCompanys.forEach(esTimatePrice->{
			esTimatePrice.getKscmtEstPriceComSetPK().setTargetYear(targetYear.v());
		});
	}

	@Override
	public void setAdvancedSetting(EstimateDetailSetting advancedSetting) {
		// TODO Auto-generated method stub
		
	}

}
