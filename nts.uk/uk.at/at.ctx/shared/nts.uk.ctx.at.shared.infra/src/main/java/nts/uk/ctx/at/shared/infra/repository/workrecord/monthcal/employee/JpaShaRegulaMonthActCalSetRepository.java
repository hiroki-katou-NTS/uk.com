/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetRegSya;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetRegSyaPK;

/**
 * The Class JpaShaRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaShaRegulaMonthActCalSetRepository extends JpaRepository implements ShaRegulaMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void add(ShaRegulaMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetRegSya entity = new KrcmtCalcMSetRegSya();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetRegSyaPK(
				new KrcmtCalcMSetRegSyaPK(domain.getComId(), domain.getEmployeeId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void update(ShaRegulaMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetRegSyaPK pk = new KrcmtCalcMSetRegSyaPK(domain.getComId(),
				domain.getEmployeeId());
		
		this.queryProxy().find(pk, KrcmtCalcMSetRegSya.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<ShaRegulaMonthActCalSet> find(String cid, String sid) {
		// Get info
		KrcmtCalcMSetRegSyaPK pk = new KrcmtCalcMSetRegSyaPK(cid, sid);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetRegSya.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String sId) {
		Optional<KrcmtCalcMSetRegSya> optEntity = this.queryProxy().find(new KrcmtCalcMSetRegSyaPK(cid, sId),
				KrcmtCalcMSetRegSya.class);
		KrcmtCalcMSetRegSya entity = optEntity.get();
		this.commandProxy().remove(entity);
	}

	private ShaRegulaMonthActCalSet toDomain (KrcmtCalcMSetRegSya e) {
		
		return ShaRegulaMonthActCalSet.of(e.getKrcmtCalcMSetRegSyaPK().getSid(), 
				e.getKrcmtCalcMSetRegSyaPK().getCid(), 
				e.getAggregateTimeSet(), 
				e.getExcessOutsideTimeSet());
	}

}
