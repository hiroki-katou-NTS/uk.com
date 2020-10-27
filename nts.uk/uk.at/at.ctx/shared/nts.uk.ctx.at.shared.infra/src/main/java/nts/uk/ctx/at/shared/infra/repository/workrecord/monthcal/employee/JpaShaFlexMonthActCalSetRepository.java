/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetFleSya;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetFleSyaPK;

/**
 * The Class JpaShaFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaShaFlexMonthActCalSetRepository extends JpaRepository implements ShaFlexMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.employee.ShaFlexMonthActCalSet)
	 */
	@Override
	public void add(ShaFlexMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetFleSya entity = new KrcmtCalcMSetFleSya();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetFleSyaPK(
				new KrcmtCalcMSetFleSyaPK(domain.getComId(), domain.getEmpId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaFlexMonthActCalSet)
	 */
	@Override
	public void update(ShaFlexMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetFleSyaPK pk = new KrcmtCalcMSetFleSyaPK(domain.getComId().toString(),
				domain.getEmpId());
		
		this.queryProxy().find(pk, KrcmtCalcMSetFleSya.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShaFlexMonthActCalSet> find(String cid, String sid) {
		// Get info
		KrcmtCalcMSetFleSyaPK pk = new KrcmtCalcMSetFleSyaPK(cid, sid);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetFleSya.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String sid) {
		this.queryProxy().find(new KrcmtCalcMSetFleSyaPK(cid, sid),
				KrcmtCalcMSetFleSya.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private ShaFlexMonthActCalSet toDomain (KrcmtCalcMSetFleSya e) {
		
		return ShaFlexMonthActCalSet.of(e.getKrcmtCalcMSetFleSyaPK().getCid(),
										e.flexAggregateMethod(),
										e.shortageFlexSetting(), 
										e.aggregateTimeSetting(), 
										e.flexTimeHandle(), 
										e.getKrcmtCalcMSetFleSyaPK().getSid());
	}

}
