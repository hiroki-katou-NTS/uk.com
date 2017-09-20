/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optionalitem.calculationformula;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormula;

/**
 * The Class FormulaFinder.
 */
@Stateless
public class CalcFormulaFinder {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<CalcFormulaDto> findAll() {
		// this.repo.findAll(AppContexts.user().companyId());
		// TODO mock data
		List<OptionalItemFormula> list = new ArrayList<OptionalItemFormula>();

		List<CalcFormulaDto> listDto = list.stream().map(item -> {
			CalcFormulaDto dto = new CalcFormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		return listDto;
	}
}
