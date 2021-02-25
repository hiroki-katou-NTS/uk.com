/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaCompanyEstablishmentGetMemento.
 */
public class JpaComEstablishmentGetMemento implements CompanyEstablishmentGetMemento{

	private String companyId;
	private int targetYear;

	/** The estimate time companys. */
	private List<KscmtEstTimeCom> estimateTimeCompanys;
	
	/** The estimate price companys. */
	private List<KscmtEstPriceCom> estimatePriceCompanys;
	
	/** The estimate days companys. */
	private List<KscmtEstDaysCom> estimateDaysCompanys;
	
	
	
	/**
	 * Instantiates a new jpa company establishment get memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 */
	public JpaComEstablishmentGetMemento(String companyId, int targetYear, List<KscmtEstTimeCom> estimateTimeCompanys,
			List<KscmtEstPriceCom> estimatePriceCompanys,
			List<KscmtEstDaysCom> estimateDaysCompanys) {
		this.companyId = companyId;
		this.targetYear = targetYear;
		this.estimateTimeCompanys = estimateTimeCompanys;
		this.estimatePriceCompanys = estimatePriceCompanys;
		this.estimateDaysCompanys = estimateDaysCompanys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentGetMemento#getTargetYear()
	 */
	@Override
	public Year getTargetYear() {
		return new Year(this.targetYear);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentGetMemento#getAdvancedSetting()
	 */
	@Override
	public EstimateDetailSetting getAdvancedSetting() {
		return new EstimateDetailSetting(new JpaComEstDetailSetGetMemento(
				this.estimateTimeCompanys, this.estimatePriceCompanys, this.estimateDaysCompanys));
	}

}
