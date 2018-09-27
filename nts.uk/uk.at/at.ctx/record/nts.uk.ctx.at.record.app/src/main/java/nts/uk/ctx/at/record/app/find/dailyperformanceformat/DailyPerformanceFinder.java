package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceFinder {

	@Inject
	private BusinessTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	@Inject
	private BusinessTypeFormatDailyRepository workTypeFormatDailyRepository;

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	public BusinessTypeDetailDto findAll(String businessTypeCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// find monthly attendance item
		List<AttendanceItemDto> monthlyAttItem = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList()).stream()
				.map(x -> {
					AttendanceItemDto dto = new AttendanceItemDto();
					dto.setAttendanceItemId(x.getAttendanceItemId());
					dto.setAttendanceItemName(x.getAttendanceItemName());
					dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());

		// find daily attendance item
		List<AttendanceItemDto> dailyAttItem = companyDailyItemService
				.getDailyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList()).stream()
				.map(x -> {
					AttendanceItemDto dto = new AttendanceItemDto();
					dto.setAttendanceItemId(x.getAttendanceItemId());
					dto.setAttendanceItemName(x.getAttendanceItemName());
					dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		if (dailyAttItem.isEmpty()) {
			BusinessTypeDetailDto businessTypeDetailDto = new BusinessTypeDetailDto(null, null, null);
			return businessTypeDetailDto;
		}

		// find daily detail
		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = this.getBusinessTypeFormatDaily(companyId,
				businessTypeCode, sheetNo, dailyAttItem);

		// find monthly detail
		List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos = this.getBusinessTypeFormatMonthly(companyId,
				businessTypeCode, monthlyAttItem);

		BusinessTypeDetailDto businessTypeDetail = new BusinessTypeDetailDto(dailyAttItem, businessTypeFormatDailyDto,
				businessTypeFormatMonthlyDtos);

		return businessTypeDetail;
	}

	private BusinessTypeFormatDailyDto getBusinessTypeFormatDaily(String companyId, String businessTypeCode,
			BigDecimal sheetNo, List<AttendanceItemDto> dailyAttItem) {
		Map<Integer, AttendanceItemDto> attendanceItemMaps = dailyAttItem.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		List<BusinessTypeFormatDaily> businessTypeFormatDailies = workTypeFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, businessTypeCode, sheetNo);
		List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos = new ArrayList<>();
		if (!businessTypeFormatDailies.isEmpty()) {
			businessTypeFormatDetailDtos = businessTypeFormatDailies.stream().map(f -> {
				if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
					return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(),
							//attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
							//attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(), 
							f.getOrder(),
							f.getColumnWidth());
				return null;
			}).collect(Collectors.toList());
		}
		Optional<BusinessFormatSheet> businessFormatSheet = businessFormatSheetRepository.getSheetInformation(companyId,
				new BusinessTypeCode(businessTypeCode), sheetNo);
		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = new BusinessTypeFormatDailyDto(sheetNo,
				businessFormatSheet.isPresent() ? businessFormatSheet.get().getSheetName() : null,
				businessTypeFormatDetailDtos);
		return businessTypeFormatDailyDto;
	}

	private List<BusinessTypeFormatDetailDto> getBusinessTypeFormatMonthly(String companyId, String businessTypeCode,
			List<AttendanceItemDto> monthlyAttItem) {
		Map<Integer, AttendanceItemDto> attendanceItemMaps = monthlyAttItem.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlies = this.workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);
		List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos = new ArrayList<BusinessTypeFormatDetailDto>();
		if (businessTypeFormatMonthlies.isEmpty()) {
			businessTypeFormatMonthlyDtos = new ArrayList<>();
		}

		businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies.stream().map(f -> {
			if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
				return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(),
						//attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
						//attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(), 
						f.getOrder(),
						f.getColumnWidth());
			return null;
		}).collect(Collectors.toList());
		return businessTypeFormatMonthlyDtos;
	}
}
