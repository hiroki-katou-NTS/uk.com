/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.attdItemLinking.AttendanceItemLinkingFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AttendanceItemsFinder {

	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
	
	@Inject
	private AttendanceItemNameAdapter monthlyAttendanceItemNameAdapter;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;
	
	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	/** The attd item linking finder. */
	@Inject
	private AttendanceItemLinkingFinder attdItemLinkingFinder;

	public List<AttendanceItemDto> find() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttendanceItemDto> attendanceItemDtos = new ArrayList<>();

		// 勤怠項目
		List<DailyAttendanceItem> dailyAttendanceItems = this.dailyAttendanceItemRepository.getListTobeUsed(companyId,
				1);

		if (dailyAttendanceItems.isEmpty()) {
			return attendanceItemDtos;
		}

		// get list attendanceItemId
		List<Integer> attendanceItemIds = dailyAttendanceItems.stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		List<DailyAttendanceItemNameAdapterDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameAdapter
				.getDailyAttendanceItemName(attendanceItemIds);

		// List<AttendanceItemDto> attendanceItemDtoResult =
		// dailyAttendanceItemDomainServiceDtos.stream().map(f -> {
		// return new AttendanceItemDto(f.getAttendanceItemId(),
		// f.getAttendanceItemName(), f.getAttendanceItemDisplayNumber());
		// }).collect(Collectors.toList());

		dailyAttendanceItemDomainServiceDtos.forEach(f -> {
			AttendanceItemDto attendanceItemDto = new AttendanceItemDto();
			attendanceItemDto.setAttendanceItemId(f.getAttendanceItemId());
			attendanceItemDto.setAttendanceItemName(f.getAttendanceItemName());
			attendanceItemDto.setAttendanceItemDisplayNumber(f.getAttendanceItemDisplayNumber());
			attendanceItemDtos.add(attendanceItemDto);
		});

		return attendanceItemDtos;
	}
	
	public List<AttdItemDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = new ArrayList<>();

		// 勤怠項目
		List<DailyAttendanceItem> dailyAttendanceItems = this.dailyAttendanceItemRepository.getList(companyId);

		if (dailyAttendanceItems.isEmpty()) {
			return attendanceItemDtos;
		}

		// get list attendanceItemId
		List<Integer> attendanceItemIds = dailyAttendanceItems.stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		List<DailyAttendanceItemNameAdapterDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameAdapter
				.getDailyAttendanceItemName(attendanceItemIds);

		Map<Integer, DailyAttendanceItem> dailyAttendanceItemMap = dailyAttendanceItems.stream()
				.collect(Collectors.toMap(DailyAttendanceItem::getAttendanceItemId, c -> c));

		dailyAttendanceItemDomainServiceDtos.forEach(f -> {
			AttdItemDto attendanceItemDto = new AttdItemDto();
			attendanceItemDto.setAttendanceItemId(f.getAttendanceItemId());
			attendanceItemDto.setAttendanceItemName(f.getAttendanceItemName());
			attendanceItemDto.setAttendanceItemDisplayNumber(f.getAttendanceItemDisplayNumber());
			DailyAttendanceItem dailyAttendanceItem = dailyAttendanceItemMap.get(f.getAttendanceItemId());
			attendanceItemDto.setDailyAttendanceAtr(dailyAttendanceItem.getDailyAttendanceAtr().value);
			attendanceItemDto.setNameLineFeedPosition(dailyAttendanceItem.getNameLineFeedPosition());
			attendanceItemDtos.add(attendanceItemDto);
		});

		return attendanceItemDtos;
	}

	public List<AttdItemDto> findAllMonthly() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = new ArrayList<>();

		// 勤怠項目
		List<MonthlyAttendanceItem> monthlyAttendanceItems = this.monthlyAttendanceItemRepository.findAll(companyId);

		if (monthlyAttendanceItems.isEmpty()) {
			return attendanceItemDtos;
		}

		// get list attendanceItemId
		List<Integer> attendanceItemIds = monthlyAttendanceItems.stream().map(f -> {
			return f.getAttendanceItemId();
		}).collect(Collectors.toList());

		List<MonthlyAttendanceItemNameDto> monthlyAttendanceItemNameDtos = this.monthlyAttendanceItemNameAdapter.
				getMonthlyAttendanceItemName(attendanceItemIds);

		Map<Integer, MonthlyAttendanceItem> monthlyAttendanceItemMap = monthlyAttendanceItems.stream()
				.collect(Collectors.toMap(MonthlyAttendanceItem::getAttendanceItemId, c -> c));

		monthlyAttendanceItemNameDtos.forEach(f -> {
			AttdItemDto attendanceItemDto = new AttdItemDto();
			attendanceItemDto.setAttendanceItemId(f.getAttendanceItemId());
			attendanceItemDto.setAttendanceItemName(f.getAttendanceItemName());
			attendanceItemDto.setAttendanceItemDisplayNumber(f.getAttendanceItemDisplayNumber());
			MonthlyAttendanceItem monthlyAttendanceItem = monthlyAttendanceItemMap.get(f.getAttendanceItemId());
			attendanceItemDto.setDailyAttendanceAtr(monthlyAttendanceItem.getMonthlyAttendanceAtr().value);
			attendanceItemDto.setNameLineFeedPosition(monthlyAttendanceItem.getNameLineFeedPosition());
			attendanceItemDtos.add(attendanceItemDto);
		});

		return attendanceItemDtos;
	}
	
	public List<AttdItemDto> findListByAttendanceAtr(int dailyAttendanceAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.dailyAttendanceItemRepository
				.findByAtr(companyId, EnumAdaptor.valueOf(dailyAttendanceAtr, DailyAttendanceAtr.class)).stream()
				.map(f -> {
					AttdItemDto attdItemDto = new AttdItemDto();
					attdItemDto.setAttendanceItemDisplayNumber(f.getDisplayNumber());
					attdItemDto.setAttendanceItemId(f.getAttendanceItemId());
					attdItemDto.setAttendanceItemName(f.getAttendanceName().v());
					attdItemDto.setDailyAttendanceAtr(f.getDailyAttendanceAtr().value);
					attdItemDto.setNameLineFeedPosition(f.getNameLineFeedPosition());
					return attdItemDto;
				}).collect(Collectors.toList());

		return attendanceItemDtos;
	}
	
	/**
	 * added by HungTT
	 * @param monthlyAttendanceAtr
	 * @return List
	 */
	public List<AttdItemDto> findListMonthlyByAttendanceAtr(int monthlyAttendanceAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.monthlyAttendanceItemRepository
				.findByAtr(companyId, EnumAdaptor.valueOf(monthlyAttendanceAtr, MonthlyAttendanceItemAtr.class)).stream()
				.map(f -> {
					AttdItemDto attdItemDto = new AttdItemDto();
					attdItemDto.setAttendanceItemDisplayNumber(f.getDisplayNumber());
					attdItemDto.setAttendanceItemId(f.getAttendanceItemId());
					attdItemDto.setAttendanceItemName(f.getAttendanceName().v());
					attdItemDto.setDailyAttendanceAtr(f.getMonthlyAttendanceAtr().value);
					attdItemDto.setNameLineFeedPosition(f.getNameLineFeedPosition());
					return attdItemDto;
				}).collect(Collectors.toList());

		return attendanceItemDtos;
	}
	
	/**
	 * added by tuTk
	 * @param monthlyAttendanceAtr
	 * @return List
	 */
	public List<AttdItemDto> findListMonthlyByAtrPrimitive(int monthlyAttendanceAtr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.monthlyAttendanceItemRepository
				.findByAtrPrimitiveValue(companyId, EnumAdaptor.valueOf(monthlyAttendanceAtr, MonthlyAttendanceItemAtr.class)).stream()
				.map(f -> {
					AttdItemDto attdItemDto = new AttdItemDto();
					attdItemDto.setAttendanceItemDisplayNumber(f.getDisplayNumber());
					attdItemDto.setAttendanceItemId(f.getAttendanceItemId());
					attdItemDto.setAttendanceItemName(f.getAttendanceName().v());
					attdItemDto.setDailyAttendanceAtr(f.getMonthlyAttendanceAtr().value);
					attdItemDto.setNameLineFeedPosition(f.getNameLineFeedPosition());
					return attdItemDto;
				}).collect(Collectors.toList());

		return attendanceItemDtos;
	}
	

	/**
	 * Find by any item.
	 *
	 * @param request the request
	 * @return the list
	 * 
	 * @author anhnm
	 */
	public List<AttdItemDto> findByAnyItem(AttdItemLinkRequest request) {
		// get list attendance item by atr
		List<AttdItemDto> attdItems = this.findListByAttendanceAtr(this.convertToAttdItemType(request.getFormulaAtr()));

		if (!CollectionUtil.isEmpty(request.getAnyItemNos())) {
			// get attendance item linking
			List<Integer> attdItemLinks = this.attdItemLinkingFinder.findByAnyItem(request).stream().map(FrameNoAdapterDto::getAttendanceItemId)
					.collect(Collectors.toList());

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
		List<DailyAttendanceItemNameAdapterDto> attdItemNames = this.dailyAttendanceItemNameAdapter
				.getDailyAttendanceItemName(new ArrayList<Integer>(attdItemsMap.keySet()));

		// set attendance item name
		attdItemNames.forEach(item -> {
			attdItemsMap.get(item.getAttendanceItemId()).setAttendanceItemName(item.getAttendanceItemName());
		});

		return attdItemsMap.values().stream().sorted((a, b) -> a.getAttendanceItemId() - b.getAttendanceItemId())
				.collect(Collectors.toList());
	}

	/**
	 * Convert to attd item type.
	 *
	 * @param formulaAtr the formula atr
	 * @return the int
	 * 
	 * @author anhnm
	 */
	private int convertToAttdItemType(int formulaAtr) {

		if (formulaAtr == OptionalItemAtr.AMOUNT.value) {
			return DailyAttendanceAtr.AmountOfMoney.value;
		} else if (formulaAtr == OptionalItemAtr.NUMBER.value) {
			return DailyAttendanceAtr.NumberOfTime.value;
		} else if (formulaAtr == OptionalItemAtr.TIME.value) {
			return DailyAttendanceAtr.Time.value;
		} else {
			throw new RuntimeException("value not found");
		}
	}

}
