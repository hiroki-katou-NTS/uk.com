package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeFormatDetailDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
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
	private AttendanceItemsFinder attendanceItemsFinder;

	@Inject
	private BusinessTypeFormatDailyRepository workTypeFormatDailyRepository;
	
	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	public BusinessTypeFormatDailyDto getDetail(String businessTypeCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		List<AttendanceItemDto> attendanceItemDtos = this.attendanceItemsFinder.find();
		Map<Integer, AttendanceItemDto> attendanceItemMaps = attendanceItemDtos.stream().collect(
				Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x->x));

		List<BusinessTypeFormatDaily> businessTypeFormatDailies = workTypeFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, businessTypeCode, sheetNo);

		List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos = new ArrayList<>();
		if(!businessTypeFormatDailies.isEmpty()){			
			businessTypeFormatDetailDtos = businessTypeFormatDailies.stream().map(f -> {
				if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
					return new BusinessTypeFormatDetailDto(f.getAttendanceItemId(), attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
							attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(), f.getOrder(), f.getColumnWidth());
				return null;
			}).collect(Collectors.toList());			
		}
		
		Optional<BusinessFormatSheet> businessFormatSheet = businessFormatSheetRepository.getSheetInformation(companyId, new BusinessTypeCode(businessTypeCode), sheetNo);

		BusinessTypeFormatDailyDto businessTypeFormatDailyDto = new BusinessTypeFormatDailyDto(sheetNo, businessFormatSheet.isPresent() ? businessFormatSheet.get().getSheetName(): null,
				businessTypeFormatDetailDtos);

		return businessTypeFormatDailyDto;

	}

}
