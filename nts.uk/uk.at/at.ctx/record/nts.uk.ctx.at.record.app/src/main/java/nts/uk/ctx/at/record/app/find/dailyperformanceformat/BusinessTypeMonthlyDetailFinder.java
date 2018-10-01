package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeMonthlyDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
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
	private BusinessTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	public BusinessTypeMonthlyDetailDto findDetail(String businessTypeCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		// find monthly attendance item
		/*List<AttendanceItemDto> monthlyAttItem = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList()).stream()
				.map(x -> {
					AttendanceItemDto dto = new AttendanceItemDto();
					dto.setAttendanceItemId(x.getAttendanceItemId());
					dto.setAttendanceItemName(x.getAttendanceItemName());
					dto.setAttendanceItemDisplayNumber(x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		Map<Integer, AttendanceItemDto> attendanceItemMaps = monthlyAttItem.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));*/
		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlies = this.workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);
		List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies.stream()
				.map(x -> new BusinessTypeFormatDetailDto(x.getAttendanceItemId(), x.getOrder(), x.getColumnWidth()))
				.collect(Collectors.toList());

		/*businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies.stream().map(f -> {
			if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
				return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(),
						attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
						attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(), f.getOrder(),
						f.getColumnWidth());
			return null;
		}).collect(Collectors.toList());*/
		BusinessTypeMonthlyDetailDto businessTypeMonthlyDetailDto = new BusinessTypeMonthlyDetailDto(businessTypeFormatMonthlyDtos);
		return businessTypeMonthlyDetailDto;
	}
}
