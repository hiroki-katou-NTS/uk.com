/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FormulaFinder.
 */
@Stateless
public class FormulaFinder {

	@Inject
	private FormulaRepository repo;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<FormulaDto> findByItemNo(String itemNo) {
		List<Formula> list = this.repo.findByOptItemNo(AppContexts.user().companyId(), itemNo);

		List<FormulaDto> listDto = list.stream().map(item -> {
			FormulaDto dto = new FormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		return listDto;
	}
}
