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
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItemDomainServiceDto;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
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
	private DailyAttendanceItemNameDomainService dailyAttendanceItemNameDomainService;

	@Inject
	private DivergenceTimeRepository divergenceTimeRepository;

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Inject
	private FrameNoAdapter frameNoAdapter;

	@Inject
	private PremiumItemAdapter premiumItemAdapter;

	@Inject
	private BPTimeItemRepository bPTimeItemRepository;

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

		List<DailyAttendanceItemDomainServiceDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameDomainService
				.getNameOfDailyAttendanceItem(attendanceItemIds);
		
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

		List<DailyAttendanceItemDomainServiceDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameDomainService
				.getNameOfDailyAttendanceItem(attendanceItemIds);
		
		
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
}


