/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.optitem.calculation.FormulaDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemFinder.
 */
@Stateless
public class OptionalItemFinder {

	/** The repo. */
	@Inject
	private OptionalItemRepository repository;

	/** The repo. */
	@Inject
	private FormulaRepository formulaRepo;

	/** The order repo. */
	@Inject
	private FormulaDispOrderRepository orderRepo;

	/**
	 * Find.
	 *
	 * @return the optional item dto
	 */
	public OptionalItemDto find(String optionalItemNo) {
		OptionalItemDto dto = new OptionalItemDto();
		OptionalItem dom = this.repository.find(AppContexts.user().companyId(), optionalItemNo).get();
		dom.saveToMemento(dto);
		dto.setFormulas(this.getFormulas(optionalItemNo));
		return dto;
	}

	/**
	 * Gets the formulas.
	 *
	 * @param itemNo the item no
	 * @return the formulas
	 */
	private List<FormulaDto> getFormulas(String itemNo) {
		String comId = AppContexts.user().companyId();

		// Get list formula
		List<Formula> list = this.formulaRepo.findByOptItemNo(comId, itemNo);

		// Get list formula order.
		Map<FormulaId, Integer> orders = this.orderRepo.findByOptItemNo(comId, itemNo).stream()
				.collect(Collectors.toMap(FormulaDispOrder::getOptionalItemFormulaId, dod -> dod.getDispOrder().v()));

		// Convert to dto.
		List<FormulaDto> listDto = list.stream().map(item -> {
			FormulaDto dto = new FormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		// Get list daily attendance item
		//List<MonthlyAttendanceItem> monthly = this.monthlyRepo.findByAtr(comId, item.getFormulaAtr());
		//List<DailyAttendanceItem> daily = this.dailyRepo

		listDto.forEach(item -> {
			// Set order
			item.setOrderNo(orders.get(new FormulaId(item.getFormulaId())));

			// set attendance item name if calculationAtr == item selection.
			if (item.getCalcAtr() == CalculationAtr.ITEM_SELECTION.value) {
				
				item.getItemSelection().getAttendanceItems().forEach(attendanceItem -> {
					attendanceItem.getAttendanceItemId(); //TODO set name & operator
					attendanceItem.getOperator();
					attendanceItem.setAttendanceItemName("name");
					attendanceItem.setOperatorText("+");
				});
				
			}
		});

		return listDto;
		
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OptionalItemHeaderDto> findAll() {
		List<OptionalItem> list = this.repository.findAll(AppContexts.user().companyId());
		List<OptionalItemHeaderDto> listDto = list.stream().map(item -> {
			OptionalItemHeaderDto dto = new OptionalItemHeaderDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());


		return listDto;
	}
}
