/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FormulaFinder.
 */
@Stateless
public class FormulaFinder {

	/** The repo. */
	@Inject
	private FormulaRepository repo;

	/** The order repo. */
	@Inject
	private FormulaDispOrderRepository orderRepo;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<FormulaDto> findByItemNo(String itemNo) {
		String comId = AppContexts.user().companyId();

		// Get list formula
		List<Formula> list = this.repo.findByOptItemNo(comId, itemNo);

		// Get list formula order.
		Map<FormulaId, Integer> orders = this.orderRepo.findByOptItemNo(comId, itemNo).stream()
				.collect(Collectors.toMap(FormulaDispOrder::getOptionalItemFormulaId, dod -> dod.getDispOrder().v()));

		// Convert to dto.
		List<FormulaDto> listDto = list.stream().map(item -> {
			FormulaDto dto = new FormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		// Set order
		listDto.forEach(item -> {
			item.setOrderNo(orders.get(new FormulaId(item.getFormulaId())));
		});

		return listDto;
	}
}
