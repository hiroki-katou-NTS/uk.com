package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSorted;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedRepository;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class BusinessTypeSortedFinder {

	@Inject
	private AttendanceItemsFinder attendanceItemsFinder;

	@Inject
	private AttendanceItemRepository attendanceItemRepository;

	@Inject
	private BusinessFormatSortedRepository businessFormatSortedRepository;

	public List<BusinessTypeSortedDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// find list attendanceItem has 使用区分 = true
		Map<Integer, AttendanceItemDto> attendanceItemDtos = this.attendanceItemsFinder.find().stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		
//		Map<Integer, AttendanceItem> attendanceItemDtos = this.attendanceItemRepository.getAttendanceItems(companyId, 1).stream()
//				.collect(Collectors.toMap(AttendanceItem::getAttendanceId, x -> x));

		List<BusinessTypeSorted> businessTypeSorted = this.businessFormatSortedRepository.find(companyId,
				attendanceItemDtos.keySet().stream().collect(Collectors.toList()));

		List<BusinessTypeSortedDto> businessTypeSortedDtos = businessTypeSorted.stream().map(f -> {
			return new BusinessTypeSortedDto(f.getAttendanceItemId(),
					attendanceItemDtos.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
					attendanceItemDtos.get(f.getAttendanceItemId()).getAttendanceItemName(),
//					attendanceItemDtos.get(f.getAttendanceItemId()).getDislayNumber(),
//					attendanceItemDtos.get(f.getAttendanceItemId()).getAttendanceName().v(),
					f.getOrder().v());
		}).collect(Collectors.toList());

		return businessTypeSortedDtos;
	}

}
