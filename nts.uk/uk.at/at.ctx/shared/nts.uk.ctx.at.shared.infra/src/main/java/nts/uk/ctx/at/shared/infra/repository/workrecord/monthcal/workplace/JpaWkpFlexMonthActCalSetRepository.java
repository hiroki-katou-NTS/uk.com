/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcmtCalcMSetFleWkp;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcmtCalcMSetFleWkpPK;

/**
 * The Class JpaWkpFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpFlexMonthActCalSetRepository extends JpaRepository implements WkpFlexMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void add(WkpFlexMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetFleWkp entity = new KrcmtCalcMSetFleWkp();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetFleWkpPK(
				new KrcmtCalcMSetFleWkpPK(domain.getComId(), domain.getWorkplaceId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void update(WkpFlexMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetFleWkpPK pk = new KrcmtCalcMSetFleWkpPK(domain.getComId().toString(),
				domain.getWorkplaceId().toString());
		
		this.queryProxy().find(pk, KrcmtCalcMSetFleWkp.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpFlexMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcmtCalcMSetFleWkpPK pk = new KrcmtCalcMSetFleWkpPK(cid, wkpId);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetFleWkp.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.queryProxy().find(new KrcmtCalcMSetFleWkpPK(cid, wkpId),
				KrcmtCalcMSetFleWkp.class)
		.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private WkpFlexMonthActCalSet toDomain (KrcmtCalcMSetFleWkp e) {
		
		return WkpFlexMonthActCalSet.of(e.getKrcmtCalcMSetFleWkpPK().getCid(),
										e.flexAggregateMethod(),
										e.shortageFlexSetting(), 
										e.aggregateTimeSetting(), 
										e.flexTimeHandle(), 
										e.getKrcmtCalcMSetFleWkpPK().getWkpId());
	}
}
