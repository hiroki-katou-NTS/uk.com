package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
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
}
