package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeMonthlyDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
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
public class BusinessTypeMonthlyDetailFinder {

	@Inject
	private AttendanceItemRepository attendanceItemRepository;

	@Inject
	private BusinessTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	public BusinessTypeMonthlyDetailDto findDetail(String businessTypeCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();		

		List<BusinessTypeFormatMonthly> workTypeFormatMonthlies = workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);
		
		if(workTypeFormatMonthlies.isEmpty()){
			return null;
		} 
		
		List<BusinessTypeFormatDetailDto> workTypeFormatMonthlyDtos = workTypeFormatMonthlies.stream().map(f -> {
			Optional<AttendanceItem> attendanceItem = this.attendanceItemRepository.getAttendanceItemDetail(companyId,
					f.getAttendanceItemId());
			return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(), attendanceItem.get().getDislayNumber(),
					attendanceItem.get().getAttendanceName().v(), f.getOrder(), f.getColumnWidth());
		}).collect(Collectors.toList());

		 BusinessTypeMonthlyDetailDto workTypeDetailDto = new BusinessTypeMonthlyDetailDto(workTypeFormatMonthlyDtos);

		return workTypeDetailDto;
	}

}
