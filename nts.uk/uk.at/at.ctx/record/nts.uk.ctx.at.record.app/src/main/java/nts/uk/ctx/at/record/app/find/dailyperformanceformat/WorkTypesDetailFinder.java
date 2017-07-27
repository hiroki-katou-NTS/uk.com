package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.WorkTypeDetailDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.WorkTypeFormatDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.WorkTypeFormatMonthlyDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypeFormatMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class WorkTypesDetailFinder {

	@Inject
	private WorkTypeFormatDailyRepository workTypeFormatDailyRepository;

	@Inject
	private WorkTypeFormatMonthlyRepository workTypeFormatMonthlyRepository;

	public WorkTypeDetailDto findDetail(String workTypeCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<WorkTypeFormatDaily> workTypeFormatDailies = workTypeFormatDailyRepository.getDailyDetail(companyId,
				workTypeCode);
		List<WorkTypeFormatMonthly> workTypeFormatMonthlies = workTypeFormatMonthlyRepository
				.getMonthlyDetail(companyId, workTypeCode);

		List<WorkTypeFormatDailyDto> workTypeFormatDailyDtos = workTypeFormatDailies.stream().map(f -> {
			return new WorkTypeFormatDailyDto(
					f.getAttendanceItemId(),
					f.getSheetName().v(), f.getSheetNo(),
					f.getOrder(), f.getColumnWidth());
		}).collect(Collectors.toList());
		
		List<WorkTypeFormatMonthlyDto> workTypeFormatMonthlyDtos = workTypeFormatMonthlies.stream().map(f -> {
			return new WorkTypeFormatMonthlyDto(f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
		}).collect(Collectors.toList());

		 WorkTypeDetailDto workTypeDetailDto = new WorkTypeDetailDto(workTypeFormatDailyDtos, workTypeFormatMonthlyDtos);

		return workTypeDetailDto;
	}

}
