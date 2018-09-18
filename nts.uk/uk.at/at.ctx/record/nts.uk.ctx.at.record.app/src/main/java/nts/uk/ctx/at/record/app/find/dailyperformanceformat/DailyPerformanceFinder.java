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

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceFinder {

	@Inject
	private BusinessTypeDailyDetailFinder businessTypeDailyDetailFinder;

	@Inject
	private BusinessTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	public BusinessTypeDetailDto findAll(String businessTypeCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		//find monthly item
		
		// List<AttdItemDto> listMonthlyItem = monthlyAttendanceItemFinder.findAll();
		List<AttdItemDto> listMonthlyItem = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList()).stream()
				.map(x -> {
					AttdItemDto dto = new AttdItemDto();
					dto.setAttendanceItemId(x.getAttendanceItemId());
					dto.setAttendanceItemName(x.getAttendanceItemName());
					dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		
		// 勤怠項目 - find attendance item
		List<AttendanceItemDto> attendanceItemDtos = companyDailyItemService
				.getDailyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList()).stream()
				.map(x -> {
					AttendanceItemDto dto = new AttendanceItemDto();
					dto.setAttendanceItemId(x.getAttendanceItemId());
					dto.setAttendanceItemName(x.getAttendanceItemName());
					dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		if(attendanceItemDtos.isEmpty()){
			BusinessTypeDetailDto businessTypeDetailDto = new BusinessTypeDetailDto(null, null, null);
			return businessTypeDetailDto;
		}
		
		Map<Integer, AttdItemDto> attendanceItemMapsMonthly = listMonthlyItem.stream().collect(
				Collectors.toMap(AttdItemDto::getAttendanceItemId, x->x));

		// find daily detail
		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = businessTypeDailyDetailFinder
				.getDetail(businessTypeCode, sheetNo);

		// find monthly detail
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlies = this.workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);
		List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos = new ArrayList<BusinessTypeFormatDetailDto>();
		if (businessTypeFormatMonthlies.isEmpty()) {
			businessTypeFormatMonthlyDtos = new ArrayList<>();
		}

		businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies.stream().map(f -> {
			if (attendanceItemMapsMonthly.containsKey(f.getAttendanceItemId()))
				return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(), attendanceItemMapsMonthly.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
						attendanceItemMapsMonthly.get(f.getAttendanceItemId()).getAttendanceItemName(), f.getOrder(), f.getColumnWidth());
			return null;
		}).collect(Collectors.toList());

		BusinessTypeDetailDto businessTypeDetail = new BusinessTypeDetailDto(attendanceItemDtos,
				businessTypeFormatDailyDto, businessTypeFormatMonthlyDtos);

		return businessTypeDetail;
	}

}
