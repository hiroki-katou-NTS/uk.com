/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;

/**
 * The Class JpaFormulaRepository.
 */
@Stateless
public class JpaFormulaRepository extends JpaRepository implements FormulaRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#create(
	 * java.util.List)
	 */
	@Override
	public void create(List<Formula> listFormula) {
		System.out.println(listFormula);
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#remove(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String comId, String optItemNo) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#
	 * findByOptItemNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Formula> findByOptItemNo(String companyId, String optItemNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
