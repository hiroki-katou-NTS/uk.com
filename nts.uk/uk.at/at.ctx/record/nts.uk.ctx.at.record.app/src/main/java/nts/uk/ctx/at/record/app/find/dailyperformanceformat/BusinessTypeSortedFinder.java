package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	private AttendanceItemRepository attendanceItemRepository;
	
	@Inject
	private BusinessFormatSortedRepository businessFormatSortedRepository;
	
	public List<BusinessTypeSortedDto> findAll(){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		//find list attendanceItem has 使用区分　=　true
		List<AttendanceItem> attendanceItems = this.attendanceItemRepository.getAttendanceItems(companyId, 1);
		
		List<BusinessTypeSortedDto> businessTypeSortedDtos = attendanceItems.stream().map(f -> {
			
			Optional<BusinessTypeSorted> businessTypeSorted = this.businessFormatSortedRepository.find(companyId, f.getAttendanceId());
			
			return new BusinessTypeSortedDto(f.getAttendanceId(), f.getDislayNumber(), f.getAttendanceName().v(), businessTypeSorted.isPresent() ? businessTypeSorted.get().getOrder() : 1);
		}).collect(Collectors.toList());
		
		return businessTypeSortedDtos;
	}

}
