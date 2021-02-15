/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaCompanyEstablishmentGetMemento.
 */
public class JpaComEstablishmentGetMemento implements CompanyEstablishmentGetMemento{
	
	public static final int FIRST_TIME = 0;
	
	/** The estimate time companys. */
	private List<KscmtEstTimeComSet> estimateTimeCompanys;
	
	/** The estimate price companys. */
	private List<KscmtEstPriceComSet> estimatePriceCompanys;
	
	/** The estimate days companys. */
	private List<KscmtEstDaysComSet> estimateDaysCompanys;
	
	
	
	/**
	 * Instantiates a new jpa company establishment get memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 */
	public JpaComEstablishmentGetMemento(List<KscmtEstTimeComSet> estimateTimeCompanys,
			List<KscmtEstPriceComSet> estimatePriceCompanys,
			List<KscmtEstDaysComSet> estimateDaysCompanys) {
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
		return new CompanyId(
				this.estimateTimeCompanys.get(FIRST_TIME).getKscmtEstTimeComSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentGetMemento#getTargetYear()
	 */
	@Override
	public Year getTargetYear() {
		return new Year(this.estimateTimeCompanys.get(FIRST_TIME).getKscmtEstTimeComSetPK()
				.getTargetYear());
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
