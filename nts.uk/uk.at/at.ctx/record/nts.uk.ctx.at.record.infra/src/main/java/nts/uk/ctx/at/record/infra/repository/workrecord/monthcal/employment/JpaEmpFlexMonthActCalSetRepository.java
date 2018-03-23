/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSetPK;

/**
 * The Class JpaEmpFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpFlexMonthActCalSetRepository extends JpaRepository implements EmpFlexMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.employment.EmpFlexMonthActCalSet)
	 */
	@Override
	public void add(EmpFlexMonthActCalSet domain) {
		// Create new entity
		KrcstEmpFlexMCalSet entity = new KrcstEmpFlexMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaEmpFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpFlexMonthActCalSet)
	 */
	@Override
	public void update(EmpFlexMonthActCalSet domain) {
		// Get info
		KrcstEmpFlexMCalSetPK pk = new KrcstEmpFlexMCalSetPK(domain.getCompanyId().toString(),
				domain.getEmploymentCode().toString());
		Optional<KrcstEmpFlexMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpFlexMCalSet.class);

		KrcstEmpFlexMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaEmpFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmpFlexMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcstEmpFlexMCalSetPK pk = new KrcstEmpFlexMCalSetPK(cid, empCode);
		Optional<KrcstEmpFlexMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpFlexMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new EmpFlexMonthActCalSet(new JpaEmpFlexMonthActCalSetGetMemento(optEntity.get())));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		Optional<KrcstEmpFlexMCalSet> optEntity = this.queryProxy().find(new KrcstEmpFlexMCalSetPK(cid, empCode),
				KrcstEmpFlexMCalSet.class);
		KrcstEmpFlexMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}
