/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.monthlyattditem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.attdItemLinking.AttendanceItemLinkingFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttdItemLinkRequest;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
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
	private AtItemNameAdapter attdItemNameAdapter;

	@Inject
    private OptionalItemRepository optItemRepo;
	
	@Inject
    private FrameNoAdapter frameAdapter;

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
			filtered.forEach(item -> {
				if (attdItems.stream().anyMatch(i -> i.getAttendanceItemId() == item.getAttendanceItemId())) {
					return;
				}
				attdItems.add(item);
			});
		}

		if (CollectionUtil.isEmpty(attdItems)) {
			return attdItems;
		}

		// convert to map
		Map<Integer, AttdItemDto> attdItemsMap = attdItems.stream()
				.collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));

		// get attd item name list
		List<AttItemNameImport> attdItemNames = this.attdItemNameAdapter
				.getNameOfAttendanceItem(new ArrayList<Integer>(attdItemsMap.keySet()), TypeOfItemImport.Monthly);

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

		return this.monthlyRepo.findByAtr(companyId, MonthlyAttendanceItemAtr.valueOf(atr)).stream()
				.map(dom -> this.toDto(dom)).collect(Collectors.toList());
	}

	public List<AttdItemDto> findMonthlyAttendanceItemBy(int checkItem) {
		List<Integer> filteredOptionItemByAtr = this.optItemRepo
				.findByAtr(AppContexts.user().companyId(), convertToOptionalItemAtr(checkItem)).stream()
				.filter(ii -> ii.isUsed())
				.map(OptionalItem::getOptionalItemNo).map(OptionalItemNo::v).collect(Collectors.toList());
		if (filteredOptionItemByAtr.isEmpty())
			return Collections.emptyList();

		// > ドメインモデル「勤怠項目と枠の紐付け」を取得する
		// return list AttendanceItemLinking after filtered by list optional item.
		int TypeOfAttendanceItem = 2;
		List<Integer> attdItemLinks = this.frameAdapter.getByAnyItem(TypeOfAttendanceItem).stream()
				.filter(item -> filteredOptionItemByAtr.contains(item.getFrameNo()))
				.map(FrameNoAdapterDto::getAttendanceItemId).collect(Collectors.toList());
		if (attdItemLinks.isEmpty())
			return Collections.emptyList();

		// get list attendance item filtered by attdItemLinks
		String companyId = AppContexts.user().companyId();
		List<AttdItemDto> attdItems = this.monthlyRepo.findByAttendanceItemId(companyId, attdItemLinks).stream()
				.map(dom -> this.toDto(dom)).collect(Collectors.toList());

		return attdItems;
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
	
	private int convertToOptionalItemAtr(int attr){
		MonthlyAttendanceItemAtr monthlyAttendanceAtr = MonthlyAttendanceItemAtr.valueOf(attr);
    	switch (monthlyAttendanceAtr) {
		case AMOUNT:
			return OptionalItemAtr.AMOUNT.value;
		case TIME:
			return OptionalItemAtr.TIME.value;
		default: //NUMBER, DAYS
			return OptionalItemAtr.NUMBER.value;
		}
    }
}
