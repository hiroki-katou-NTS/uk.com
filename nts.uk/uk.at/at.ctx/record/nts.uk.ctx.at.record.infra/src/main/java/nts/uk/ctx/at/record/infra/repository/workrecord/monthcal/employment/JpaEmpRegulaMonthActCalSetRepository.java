/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSetPK;

/**
 * The Class JpaEmpRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpRegulaMonthActCalSetRepository extends JpaRepository implements EmpRegulaMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.employment.EmpRegulaMonthActCalSet)
	 */
	@Override
	public void add(EmpRegulaMonthActCalSet domain) {
		// Create new entity
		KrcstEmpRegMCalSet entity = new KrcstEmpRegMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaEmpRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpRegulaMonthActCalSet)
	 */
	@Override
	public void update(EmpRegulaMonthActCalSet domain) {
		// Get info
		KrcstEmpRegMCalSetPK pk = new KrcstEmpRegMCalSetPK(domain.getCompanyId().toString(),
				domain.getEmploymentCode().toString());
		Optional<KrcstEmpRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpRegMCalSet.class);

		KrcstEmpRegMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaEmpRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<EmpRegulaMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcstEmpRegMCalSetPK pk = new KrcstEmpRegMCalSetPK(cid, empCode);
		Optional<KrcstEmpRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpRegMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new EmpRegulaMonthActCalSet(new JpaEmpRegulaMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		Optional<KrcstEmpRegMCalSet> optEntity = this.queryProxy().find(new KrcstEmpRegMCalSetPK(cid, empCode),
				KrcstEmpRegMCalSet.class);
		KrcstEmpRegMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}
