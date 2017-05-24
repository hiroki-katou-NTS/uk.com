/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.ComCompensLeave;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.ComCompensLeaveRepository;

/**
 * The Class JpaComCompensLeaveRepo.
 */
@Stateless
public class JpaComCompensLeaveRepo extends JpaRepository implements ComCompensLeaveRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveRepository#add(nts.uk.ctx.at.shared.dom.vacation.setting.
	 * compensleave.ComCompensLeave)
	 */
	@Override
	public void add(ComCompensLeave setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveRepository#update(nts.uk.ctx.at.shared.dom.vacation.
	 * setting.compensleave.ComCompensLeave)
	 */
	@Override
	public void update(ComCompensLeave setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveRepository#findAll(java.lang.String)
	 */
	@Override
	public List<ComCompensLeave> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensleave.
	 * ComCompensLeaveRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<ComCompensLeave> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
