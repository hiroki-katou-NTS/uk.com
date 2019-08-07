package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

//import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSFormatDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSFormatDetailDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class BusinessTypeSDailyDetailFinder {

	@Inject
	private BusinessTypeSFormatDailyRepository workTypeMobileFormatDailyRepository;

	public BusinessTypeSFormatDto getDetail(String businessTypeCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		/*勤務種別の月別実績の修正のフォーマット．表示項目．月次表示項目シート一覧*/								
		List<BusinessTypeSFormatDaily> businessTypeFormatDailies = workTypeMobileFormatDailyRepository
				.getBusinessTypeFormatDailyDetail(companyId, businessTypeCode);
		
		List<BusinessTypeSFormatDetailDto> businessTypeFormatDetailDtos = businessTypeFormatDailies
				.stream()
				.map(x -> new BusinessTypeSFormatDetailDto(x.getAttendanceItemId(), x.getOrder()))
				.collect(Collectors.toList());

		BusinessTypeSFormatDto businessTypeFormatDailyDto = new BusinessTypeSFormatDto(
				businessTypeFormatDetailDtos);
		
		return businessTypeFormatDailyDto;
	}
}
