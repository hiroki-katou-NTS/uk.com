package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
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
public class BusinessTypeDailyDetailFinder {

	@Inject
	private AttendanceItemRepository attendanceItemRepository;

	@Inject
	private BusinessTypeFormatDailyRepository workTypeFormatDailyRepository;

	public BusinessTypeFormatDailyDto getDetail(String businessTypeCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		//
		// String sheetName =
		// businessFormatSheetRepository.getSheetInformation(companyId, new
		// BusinessTypeCode(businessTypeCode), sheetNo).get().getSheetName();

		List<BusinessTypeFormatDaily> businessTypeFormatDailies = workTypeFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, businessTypeCode, sheetNo);

		List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos = businessTypeFormatDailies.stream().map(f -> {
			Optional<AttendanceItem> attendanceItem = this.attendanceItemRepository.getAttendanceItemDetail(companyId,
					f.getAttendanceItemId());
			return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(), attendanceItem.get().getDislayNumber(),
					attendanceItem.get().getAttendanceName().v(), f.getOrder(), f.getColumnWidth());
		}).collect(Collectors.toList());

		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = new BusinessTypeFormatDailyDto(new BigDecimal(1), null,
				businessTypeFormatDetailDtos);

		return businessTypeFormatDailyDto;

	}

}
