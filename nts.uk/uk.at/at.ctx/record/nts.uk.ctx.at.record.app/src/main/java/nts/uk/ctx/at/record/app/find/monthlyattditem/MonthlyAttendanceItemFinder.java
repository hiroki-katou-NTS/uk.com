/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.monthlyattditem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.attdItemLinking.AttendanceItemLinkingFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttdItemLinkRequest;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
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

	/** The attd item name adapter. */
	@Inject
	private DailyAttendanceItemNameAdapter attdItemNameAdapter;

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
			List<Integer> attdItemLinks = this.attdItemLinkingFinder.findByAnyItem(request).stream()
					.map(FrameNoAdapterDto::getAttendanceItemId).collect(Collectors.toList());

			// get list attendance item filtered by attdItemLinks
			List<AttdItemDto> filtered = this.findAll().stream()
					.filter(item -> attdItemLinks.contains(item.getAttendanceItemId())).collect(Collectors.toList());

			// merge two list attendance items
			attdItems.addAll(filtered);
		}

		if (attdItems.isEmpty()) {
			return attdItems;
		}

		// convert to map
		Map<Integer, AttdItemDto> attdItemsMap = attdItems.stream()
				.collect(Collectors.toMap(k -> k.getAttendanceItemId(), vl -> vl));

		// get attd item name list
		List<DailyAttendanceItemNameAdapterDto> attdItemNames = this.attdItemNameAdapter
				.getDailyAttendanceItemName(new ArrayList<Integer>(attdItemsMap.keySet()));

		// set attendance item name
		attdItemNames.forEach(item -> {
			attdItemsMap.get(item.getAttendanceItemId()).setAttendanceItemName(item.getAttendanceItemName());
		});

		return attdItemsMap.values().stream().sorted((a, b) -> a.getAttendanceItemId() - b.getAttendanceItemId())
				.collect(Collectors.toList());

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
		attdItemDto.setDisplayNumber(dom.getDisplayNumber());
		attdItemDto.setUserCanUpdateAtr(dom.getUserCanUpdateAtr().value);
		return attdItemDto;

	}

	/**
	 * Convert to attd item type.
	 *
	 * @param formulaAtr the formula atr
	 * @return the int
	 */
	private int convertToAttdItemType(int formulaAtr) {
		OptionalItemAtr vl = OptionalItemAtr.valueOf(formulaAtr);
		switch (vl) {
		case AMOUNT:
			return MonthlyAttendanceItemAtr.AMOUNT.value;
		case TIME:
			return MonthlyAttendanceItemAtr.TIME.value;
		case NUMBER:
			return MonthlyAttendanceItemAtr.NUMBER.value;
		default:
			throw new RuntimeException("value not found");
		}
	}
}
