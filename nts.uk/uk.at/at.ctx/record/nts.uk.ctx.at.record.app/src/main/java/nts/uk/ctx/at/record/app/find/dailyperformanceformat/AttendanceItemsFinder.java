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

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
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
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	/** The frame adapter. */
	@Inject
	private FrameNoAdapter frameAdapter;

	/** The opt item repo. */
	@Inject
	private OptionalItemRepository optItemRepo;

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

		List<DailyAttendanceItemNameAdapterDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(attendanceItemIds);
		
//		List<AttendanceItemDto> attendanceItemDtoResult = dailyAttendanceItemDomainServiceDtos.stream().map(f -> {
//			return new AttendanceItemDto(f.getAttendanceItemId(), f.getAttendanceItemName(), f.getAttendanceItemDisplayNumber());
//		}).collect(Collectors.toList());
		
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

		List<DailyAttendanceItemNameAdapterDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(attendanceItemIds);
		
		
		Map<Integer, DailyAttendanceItem> dailyAttendanceItemMap =
				dailyAttendanceItems.stream().collect(Collectors.toMap(DailyAttendanceItem::getAttendanceItemId, c -> c));
			
		
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

	/**
	 * Find by any item.
	 *
	 * @param anyItemNos the any item nos
	 * @param formulaAtr the formula atr
	 * @param performanceAtr the performance atr
	 * @return the list
	 * 
	 * @author anhnm
	 */
	public List<FrameNoAdapterDto> findByAnyItem(AttdItemLinkRequest request) {

		// in case list anyItemNos is empty.
		if (request.getAnyItemNos().isEmpty()) {
			return this.getAttendaneItemLinks(request.getPerformanceAtr());
		}

		// find list optional item by attribute
		Map<String, String> filteredByAtr = this.optItemRepo
				.findByAtr(AppContexts.user().companyId(), request.getFormulaAtr()).stream()
				.collect(Collectors.toMap(i -> i.getOptionalItemNo().v(), i -> i.getOptionalItemNo().v()));

		// filter list optional item by selectable list parameters.
		Map<Integer, Integer> filteredBySelectableList = request.getAnyItemNos().stream()
				.filter(itemNo -> filteredByAtr.containsKey(itemNo))
				.collect(Collectors.toMap(i -> Integer.parseInt(i), i -> Integer.parseInt(i)));

		// return list AttendanceItemLinking after filtered by list optional item.
		return this.getAttendaneItemLinks(request.getPerformanceAtr()).stream()
				.filter(item -> filteredBySelectableList.containsKey(item.getFrameNo())).collect(Collectors.toList());

	}

	/**
	 * Gets the attendane item links.
	 *
	 * @param performanceAtr the performance atr
	 * @return the attendane item links
	 * 
	 * @author anhnm
	 */
	private List<FrameNoAdapterDto> getAttendaneItemLinks(int performanceAtr) {
		return this.frameAdapter.getByAnyItem(convertToFrameType(performanceAtr));
	}

	/**
	 * Convert to frame type.
	 *
	 * @param performanceAtr the performance atr
	 * @return the int
	 * 
	 * @author anhnm
	 */
	private static final int convertToFrameType(int performanceAtr) {
		final int MONTHLY_FRAME = 1;
		final int DAILY_FRAME = 3;
		return performanceAtr == PerformanceAtr.DAILY_PERFORMANCE.value ? DAILY_FRAME : MONTHLY_FRAME;
	}
}


