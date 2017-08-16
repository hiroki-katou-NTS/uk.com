package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceFinder {

	@Inject
	private AttendanceItemRepository attendanceItemRepository;

	@Inject
	private BusinessTypeDailyDetailFinder businessTypeDailyDetailFinder;

	@Inject
	private BusinessTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	public BusinessTypeDetailDto findAll(String businessTypeCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// 勤怠項目 - find attendance item
		List<AttendanceItem> attendanceItems = this.attendanceItemRepository.getAttendanceItems(companyId, 1);
		List<AttendanceItemDto> attendanceItemDtos = attendanceItems.stream().map(f -> {
			return new AttendanceItemDto(f.getAttendanceId(), f.getAttendanceName().v(), f.getDislayNumber());
		}).collect(Collectors.toList());

		// find daily detail
		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = new BusinessTypeFormatDailyDto(null, null, null);
		if (sheetNo == null) {
			businessTypeFormatDailyDto = new BusinessTypeFormatDailyDto(null, null, null);
		} else {
			businessTypeFormatDailyDto = businessTypeDailyDetailFinder.getDetail(businessTypeCode, sheetNo);
		}

		// find monthly detail
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlies = workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);
		List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos = new ArrayList<BusinessTypeFormatDetailDto>();
		if (businessTypeFormatMonthlies.isEmpty()) {
			businessTypeFormatMonthlyDtos = new ArrayList<>();
		}

		businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies.stream()
				.map(f -> {
					return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		BusinessTypeDetailDto businessTypeDetail = new BusinessTypeDetailDto(attendanceItemDtos,
				businessTypeFormatDailyDto, businessTypeFormatMonthlyDtos);

		return businessTypeDetail;
	}

}
