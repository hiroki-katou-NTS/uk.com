/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.company.KrcmtCalcMSetRegCom;

/**
 * The Class JpaComRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaComRegulaMonthActCalSetRepository extends JpaRepository
		implements ComRegulaMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComRegulaMonthActCalSet> find(String companyId) {
		// Get info
		return this.queryProxy().find(companyId, KrcmtCalcMSetRegCom.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.company.ComRegulaMonthActCalSet)
	 */
	@Override
	public void add(ComRegulaMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetRegCom entity = new KrcmtCalcMSetRegCom();

		// Transfer data
		entity.transfer(domain);
		entity.setCid(domain.getComId());

		// Insert into DB
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.company.ComRegulaMonthActCalSet)
	 */
	@Override
	public void update(ComRegulaMonthActCalSet domain) {
		// Get info
		this.queryProxy().find(domain.getComId(), KrcmtCalcMSetRegCom.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String cId) {
		this.commandProxy().remove(KrcmtCalcMSetRegCom.class, cId);
	}

	private ComRegulaMonthActCalSet toDomain (KrcmtCalcMSetRegCom e) {
		
		return ComRegulaMonthActCalSet.of(e.getCid(), 
				e.getAggregateTimeSet(), 
				e.getExcessOutsideTimeSet());
	}

}
