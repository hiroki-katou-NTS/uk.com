/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;

/**
 * The Class JpaClosureRepository.
 */
@Stateless
public class JpaClosureRepository extends JpaRepository implements ClosureRepository{
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#add(nts.uk.
	 * ctx.at.record.dom.workrecord.closure.Closure)
	 */
	@Override
	public void add(Closure closure) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#update(nts.
	 * uk.ctx.at.record.dom.workrecord.closure.Closure)
	 */
	@Override
	public void update(Closure closure) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#
	 * getAllClosure(java.lang.String)
	 */
	@Override
	public List<Closure> getAllClosure(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
