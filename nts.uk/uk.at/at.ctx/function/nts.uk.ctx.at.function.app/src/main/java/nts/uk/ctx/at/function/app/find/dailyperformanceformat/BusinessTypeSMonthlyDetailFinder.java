package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

//import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
//import java.util.Map;
//import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSFormatDetailDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSMonthlyFormatDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatMonthlyRepository;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class BusinessTypeSMonthlyDetailFinder {

	@Inject
	private BusinessTypeSFormatMonthlyRepository workTypeSFormatMonthlyRepository;

	public BusinessTypeSMonthlyFormatDto findDetail(String businessTypeCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		// find monthly attendance item
		List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlies = this.workTypeSFormatMonthlyRepository
				.getMonthlyDetail(companyId, businessTypeCode);

		List<BusinessTypeSFormatDetailDto> businessTypeFormatMonthlyDtos = businessTypeFormatMonthlies
				.stream()
				.map(x -> new BusinessTypeSFormatDetailDto(x.getAttendanceItemId(), x.getOrder()))
				.collect(Collectors.toList());
		
		BusinessTypeSMonthlyFormatDto businessTypeMonthlyDetailDto = new BusinessTypeSMonthlyFormatDto(businessTypeFormatMonthlyDtos);
		
		return businessTypeMonthlyDetailDto;
	}
}
