/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.optitem.calculation.FormulaDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.OperatorAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemNameAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemFinder.
 */
@Stateless
public class OptionalItemFinder {

	/** The repository. */
	@Inject
	private OptionalItemRepository repository;

	/** The formula repo. */
	@Inject
	private FormulaRepository formulaRepo;

	/** The order repo. */
	@Inject
	private FormulaDispOrderRepository orderRepo;

	/** The monthly repo. */
	@Inject
	private MonthlyAttendanceItemRepository monthlyRepo;

	/** The daily repo. */
	@Inject
	private DailyAttendanceItemRepository dailyRepo;

	/** The attd item name adapter. */
	@Inject
	private DailyAttendanceItemNameAdapter dailyAttdItemNameAdapter;
	
	/** The monthly attd item name adapter. */
	@Inject
	private MonthlyAttendanceItemNameAdapter monthlyAttdItemNameAdapter;

	@Inject
	private AtItemNameAdapter attdItemNameAdapter;
	
	/**
	 * Find.
	 *
	 * @param optionalItemNo the optional item no
	 * @return the optional item dto
	 */
	public OptionalItemDto find(Integer optionalItemNo) {
		OptionalItemDto dto = new OptionalItemDto();
		OptionalItem optionalItem = this.repository.find(AppContexts.user().companyId(), optionalItemNo);
		optionalItem.saveToMemento(dto);

		// Set list formula.
		dto.setFormulas(this.getFormulas(optionalItem));

		return dto;
	}

	/**
	 * Sets the name and order.
	 *
	 * @param listFormula the list formula
	 * @param optionalItem the optional item
	 */
	private void setNameAndOrder(List<FormulaDto> listFormula, OptionalItem optionalItem) {
		// Get list attendance item
		Map<Integer, String> attendanceItems = this.getAttendanceItems(optionalItem.getPerformanceAtr());

		// Get list formula order.
		Map<FormulaId, Integer> orders = this.getFormulaOrders(AppContexts.user().companyId(),
				optionalItem.getOptionalItemNo().v());

		// Set formula order & attendance item name & operator text.
		listFormula.forEach(item -> {
			// Set order
			item.setOrderNo(orders.get(new FormulaId(item.getFormulaId())));

			// set attendance item name if calculationAtr == item selection.
			if (item.getCalcAtr() == CalculationAtr.ITEM_SELECTION.value) {

				item.getItemSelection().getAttendanceItems().forEach(attendanceItem -> {
					String attendanceName = attendanceItems.get(attendanceItem.getAttendanceItemId());
					String operatorText = OperatorAtr.valueOf(attendanceItem.getOperator()).description;
					attendanceItem.setAttendanceItemName(attendanceName);
					attendanceItem.setOperatorText(operatorText);
				});

			}
		});

	}

	/**
	 * Gets the attendance items.
	 *
	 * @param atr the atr
	 * @return the attendance items
	 */
	private Map<Integer, String> getAttendanceItems(PerformanceAtr atr) {
		Map<Integer, String> result;
		switch (atr) {
		case DAILY_PERFORMANCE: {
			result = this.dailyRepo.getList(AppContexts.user().companyId()).stream()
					.collect(Collectors.toMap(DailyAttendanceItem::getAttendanceItemId,
							item -> item.getAttendanceName().v()));

			// get attd item name list
			List<DailyAttendanceItemNameAdapterDto> attdItemNames = this.dailyAttdItemNameAdapter
					.getDailyAttendanceItemName(new ArrayList<Integer>(result.keySet()));

			// set attendance item name
			attdItemNames.forEach(item -> {
				result.replace(item.getAttendanceItemId(), item.getAttendanceItemName());
			});
			break;
		}

		case MONTHLY_PERFORMANCE: {
			result = this.monthlyRepo.findAll(AppContexts.user().companyId()).stream()
					.collect(Collectors.toMap(MonthlyAttendanceItem::getAttendanceItemId,
							item -> item.getAttendanceName().v()));

			
			// get attd item name list
			List<AttItemName> attdItemNames = this.attdItemNameAdapter
					.getNameOfAttendanceItem(new ArrayList<Integer>(result.keySet()), TypeOfItemImport.Monthly);
//			// get attd item name list
//			List<AttendanceItemName> attdItemNames = monthlyAttdItemNameAdapter
//					.getNameOfMonthlyAttendanceItem(new ArrayList<Integer>(result.keySet()));

			// set attendance item name
			attdItemNames.forEach(item -> {
				result.replace(item.getAttendanceItemId(), item.getAttendanceItemName());
			});
			break;
		}

		default:
			throw new RuntimeException("Not support this enum value!");
		}

		// result not found
		if (result.isEmpty()) {
			return Collections.emptyMap();
		}

		return result;
	}

	/**
	 * Gets the formula orders.
	 *
	 * @param companyId the company id
	 * @param itemNo the item no
	 * @return the formula orders
	 */
	private Map<FormulaId, Integer> getFormulaOrders(String companyId, Integer itemNo) {
		return this.orderRepo.findByOptItemNo(companyId, itemNo).stream()
				.collect(Collectors.toMap(FormulaDispOrder::getOptionalItemFormulaId, dod -> dod.getDispOrder().v()));
	}

	/**
	 * Gets the formulas.
	 *
	 * @param optionalItem the optional item
	 * @return the formulas
	 */
	private List<FormulaDto> getFormulas(OptionalItem optionalItem) {
		String comId = AppContexts.user().companyId();

		// Get list formula
		List<Formula> list = this.formulaRepo.findByOptItemNo(comId, optionalItem.getOptionalItemNo().v());

		// Convert to dto.
		List<FormulaDto> listDto = list.stream().map(item -> {
			FormulaDto dto = new FormulaDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

		// Set formula order & attendance item name & operator text.
		this.setNameAndOrder(listDto, optionalItem);

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
