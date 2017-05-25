/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpCompensLeave;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.EmpCompensLeaveRepository;

/**
 * The Class JpaEmpCompensLeaveRepo.
 */
@Stateless
public class JpaEmpCompensLeaveRepo extends JpaRepository implements EmpCompensLeaveRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.
	 * compensleave.EmpCompensLeave)
	 */
	@Override
	public void add(EmpCompensLeave setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.EmpCompensLeave)
	 */
	@Override
	public void update(EmpCompensLeave setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveRepository#findAll(java.lang.String)
	 */
	@Override
	public List<EmpCompensLeave> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * EmpCompensLeaveRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<EmpCompensLeave> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
