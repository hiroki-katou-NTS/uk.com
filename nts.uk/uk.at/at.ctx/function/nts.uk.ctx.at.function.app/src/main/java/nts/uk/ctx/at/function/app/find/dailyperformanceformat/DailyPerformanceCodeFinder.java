package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceCodeFinder {

	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;

	public List<DailyPerformanceCodeDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		List<DailyPerformanceCodeDto> dailyPerformanceCodeDtos = new ArrayList<DailyPerformanceCodeDto>();
		if (this.authorityDailyPerformanceFormatRepository.getListCode(companyId).isEmpty()) {
			dailyPerformanceCodeDtos = new ArrayList<>();
		} 
		dailyPerformanceCodeDtos = this.authorityDailyPerformanceFormatRepository
				.getListCode(companyId).stream().map(f -> {
					return new DailyPerformanceCodeDto(f.getDailyPerformanceFormatCode().v(),
							f.getDailyPerformanceFormatName().v());
				}).collect(Collectors.toList());
		dailyPerformanceCodeDtos.sort((c1, c2) -> c1.getDailyPerformanceFormatCode().compareTo(c2.getDailyPerformanceFormatCode()));

		return dailyPerformanceCodeDtos;
	}

}
