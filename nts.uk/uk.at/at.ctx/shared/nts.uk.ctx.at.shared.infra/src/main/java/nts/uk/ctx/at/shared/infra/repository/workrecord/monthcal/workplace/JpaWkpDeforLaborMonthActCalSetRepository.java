/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcmtCalcMSetDefWkp;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcmtCalcMSetDefWkpPK;

/**
 * The Class JpaWkpDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements WkpDeforLaborMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(WkpDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetDefWkp entity = new KrcmtCalcMSetDefWkp();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetDefWkpPK(
				new KrcmtCalcMSetDefWkpPK(domain.getComId(), domain.getWorkplaceId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(WkpDeforLaborMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetDefWkpPK pk = new KrcmtCalcMSetDefWkpPK(domain.getComId(),
				domain.getWorkplaceId());
		
		this.queryProxy().find(pk, KrcmtCalcMSetDefWkp.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WkpDeforLaborMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcmtCalcMSetDefWkpPK pk = new KrcmtCalcMSetDefWkpPK(cid, wkpId);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetDefWkp.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		
		this.queryProxy().find(new KrcmtCalcMSetDefWkpPK(cid, wkpId),
				KrcmtCalcMSetDefWkp.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));
	}

	private WkpDeforLaborMonthActCalSet toDomain (KrcmtCalcMSetDefWkp e) {
		
		return WkpDeforLaborMonthActCalSet.of(e.getKrcmtCalcMSetDefWkpPK().getWkpId(),
				e.getKrcmtCalcMSetDefWkpPK().getCid(), 
				e.getAggregateTimeSet(), e.getExcessOutsideTimeSet(),
				e.deforLaborCalSetting(),
				e.deforLaborSettlementPeriod());
	}

}
