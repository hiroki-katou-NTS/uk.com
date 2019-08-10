package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedMBDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSortedMobile;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedMBRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class BusinessTypeSortedMBFinder {

	@Inject
	private AttendanceItemsFinder attendanceItemsFinder;
	

	@Inject
	private BusinessFormatSortedMBRepository businessFormatSortedMBRepository;

	/**
	 * Find all BusinessTypeSortedMBDto
	 * @return
	 */
	public List<BusinessTypeSortedMBDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Map<Integer, AttendanceItemDto> attendanceItemDtos = this.attendanceItemsFinder.find().stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		
		List<BusinessTypeSortedMBDto> businessTypeSortedDtos = new ArrayList<>();

		// Get data BusinessTypeSortedMobile
		List<BusinessTypeSortedMobile> businessTypeSorted = this.businessFormatSortedMBRepository.find(companyId,
				attendanceItemDtos.keySet().stream().collect(Collectors.toList()));

		if(businessTypeSorted.isEmpty()){
			businessTypeSortedDtos = attendanceItemDtos.values().stream().map(f -> {
				return new BusinessTypeSortedMBDto(f.getAttendanceItemId(), f.getAttendanceItemDisplayNumber(), f.getAttendanceItemName(), 1);
			}).collect(Collectors.toList());
		} else {
			businessTypeSortedDtos = businessTypeSorted.stream().map(f -> {
				return new BusinessTypeSortedMBDto(f.getAttendanceItemId(),
						attendanceItemDtos.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
						attendanceItemDtos.get(f.getAttendanceItemId()).getAttendanceItemName(),
						f.getOrder().v());
			}).collect(Collectors.toList());
		}
		
		return businessTypeSortedDtos;
	}

}
