/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;

/**
 * The Class FormulaFinder.
 */
@Stateless
public class FormulaFinder {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<FormulaDto> findByItemNo(String itemNo) {
		// this.repo.findAll(AppContexts.user().companyId());
		// TODO mock data
		List<Formula> list = new ArrayList<Formula>();

		List<FormulaDto> listDto = list.stream().map(item -> {
			FormulaDto dto = new FormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		for (int i = 0; i < 10; i++) {
			FormulaDto dto = new FormulaDto();
			dto.setFormulaId(new FormulaId(""+i));
			listDto.add(dto);
		}
		return listDto;
	}
}
