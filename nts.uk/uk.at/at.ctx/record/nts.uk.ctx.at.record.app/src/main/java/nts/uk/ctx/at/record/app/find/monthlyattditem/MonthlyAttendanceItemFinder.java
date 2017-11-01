/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.monthlyattditem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.attdItemLinking.AttendanceItemLinkingFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttdItemLinkRequest;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.dom.monthlyattendanceitem.MonthlyAttendanceItem;
import nts.uk.ctx.at.record.dom.monthlyattendanceitem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.record.dom.monthlyattendanceitem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyAttendanceItemFinder.
 */
@Stateless
public class MonthlyAttendanceItemFinder {

	/** The attd item linking finder. */
	@Inject
	private AttendanceItemLinkingFinder attdItemLinkingFinder;

	/** The monthly repo. */
	@Inject
	private MonthlyAttendanceItemRepository monthlyRepo;

	/**
	 * Find by any item.
	 *
	 * @param request the request
	 * @return the list
	 */
	public List<AttdItemDto> findByAnyItem(AttdItemLinkRequest request) {
		// get list attendance item by atr
		List<AttdItemDto> attdItems = this.findByAtr(this.convertToAttdItemType(request.getFormulaAtr()));

		if (!CollectionUtil.isEmpty(request.getAnyItemNos())) {
			// get attendance item linking
			Map<Integer, Integer> attdItemLinks = this.attdItemLinkingFinder.findByAnyItem(request).stream()
					.collect(Collectors.toMap(item -> item.getAttendanceItemId(), item -> item.getAttendanceItemId()));

			// get list attendance item filtered by attdItemLinks
			List<AttdItemDto> filtered = this.findAll().stream()
					.filter(item -> attdItemLinks.containsKey(item.getAttendanceItemId())).collect(Collectors.toList());

			// merge two list attendance items
			attdItems.addAll(filtered);
		}

		return attdItems;
	}

	/**
	 * Find by atr.
	 *
	 * @param atr the atr
	 * @return the list
	 */
	public List<AttdItemDto> findByAtr(int atr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.monthlyRepo
				.findByAtr(companyId, MonthlyAttendanceItemAtr.valueOf(atr)).stream().map(dom -> this.toDto(dom))
				.collect(Collectors.toList());

		return attendanceItemDtos;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AttdItemDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.monthlyRepo.findAll(companyId).stream().map(dom -> this.toDto(dom))
				.collect(Collectors.toList());

		return attendanceItemDtos;
	}

	/**
	 * To dto.
	 *
	 * @param dom the dom
	 * @return the attd item dto
	 */
	private AttdItemDto toDto(MonthlyAttendanceItem dom) {
		AttdItemDto attdItemDto = new AttdItemDto();
		attdItemDto.setAttendanceItemDisplayNumber(dom.getDisplayNumber());
		attdItemDto.setAttendanceItemId(dom.getAttendanceItemId());
		attdItemDto.setAttendanceItemName(dom.getAttendanceName().v());
		attdItemDto.setDailyAttendanceAtr(dom.getMonthlyAttendanceAtr().value);
		attdItemDto.setNameLineFeedPosition(dom.getNameLineFeedPosition());
		return attdItemDto;

	}

	/**
	 * Convert to attd item type.
	 *
	 * @param formulaAtr the formula atr
	 * @return the int
	 */
	private int convertToAttdItemType(int formulaAtr) {

		if (formulaAtr == OptionalItemAtr.AMOUNT.value) {
			return MonthlyAttendanceItemAtr.AMOUNT.value;
		} else if (formulaAtr == OptionalItemAtr.NUMBER.value) {
			return MonthlyAttendanceItemAtr.NUMBER.value;
		} else if (formulaAtr == OptionalItemAtr.TIME.value) {
			return MonthlyAttendanceItemAtr.TIME.value;
		} else {
			throw new RuntimeException("value not found");
		}
	}
}
