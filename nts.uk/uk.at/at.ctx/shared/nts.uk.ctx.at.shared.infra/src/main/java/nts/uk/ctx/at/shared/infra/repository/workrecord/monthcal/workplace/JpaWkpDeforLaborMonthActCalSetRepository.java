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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSetPK;

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
		KrcstWkpDeforMCalSet entity = new KrcstWkpDeforMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstWkpDeforMCalSetPK(
				new KrcstWkpDeforMCalSetPK(domain.getComId(), domain.getWorkplaceId()));

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
		KrcstWkpDeforMCalSetPK pk = new KrcstWkpDeforMCalSetPK(domain.getComId(),
				domain.getWorkplaceId());
		
		this.queryProxy().find(pk, KrcstWkpDeforMCalSet.class).ifPresent(e -> {
			
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
		KrcstWkpDeforMCalSetPK pk = new KrcstWkpDeforMCalSetPK(cid, wkpId);
		
		return this.queryProxy().find(pk, KrcstWkpDeforMCalSet.class).map(c -> toDomain(c));
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
		
		this.queryProxy().find(new KrcstWkpDeforMCalSetPK(cid, wkpId),
				KrcstWkpDeforMCalSet.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));
	}

	private WkpDeforLaborMonthActCalSet toDomain (KrcstWkpDeforMCalSet e) {
		
		return WkpDeforLaborMonthActCalSet.of(e.getKrcstWkpDeforMCalSetPK().getWkpId(),
				e.getKrcstWkpDeforMCalSetPK().getCid(), 
				e.getAggregateTimeSet(), e.getExcessOutsideTimeSet(),
				e.deforLaborCalSetting(),
				e.deforLaborSettlementPeriod());
	}

}
