/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpSubstVacationRepository;

/**
 * The Class JpaEmpSubstVacationRepo.
 */
@Stateless
public class JpaEmpSubstVacationRepo extends JpaRepository implements EmpSubstVacationRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.
	 * compensleave.EmpSubstVacation)
	 */
	@Override
	public void add(EmpSubstVacation setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.EmpSubstVacation)
	 */
	@Override
	public void update(EmpSubstVacation setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationRepository#findAll(java.lang.String)
	 */
	@Override
	public List<EmpSubstVacation> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpSubstVacationRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<EmpSubstVacation> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
