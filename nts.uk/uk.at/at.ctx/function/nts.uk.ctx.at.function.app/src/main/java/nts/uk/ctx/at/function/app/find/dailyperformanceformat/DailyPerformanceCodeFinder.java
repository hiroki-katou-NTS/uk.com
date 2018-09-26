package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceCodeFinder {

	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	public List<DailyPerformanceCodeDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<DailyPerformanceCodeDto> dailyPerformanceCodeDtos = new ArrayList<DailyPerformanceCodeDto>();
		List<AuthorityDailyPerformanceFormat> listDailyFormat = this.authorityDailyPerformanceFormatRepository
				.getListCode(companyId);
		for (AuthorityDailyPerformanceFormat format : listDailyFormat) {
			boolean defaultInitial = this.authorityFormatInitialDisplayRepository.checkExistData(companyId,
					format.getDailyPerformanceFormatCode());
			DailyPerformanceCodeDto dto = new DailyPerformanceCodeDto(format.getDailyPerformanceFormatCode().v(),
					format.getDailyPerformanceFormatName().v(), defaultInitial);
			dailyPerformanceCodeDtos.add(dto);
		}
		dailyPerformanceCodeDtos
				.sort((c1, c2) -> c1.getDailyPerformanceFormatCode().compareTo(c2.getDailyPerformanceFormatCode()));

		return dailyPerformanceCodeDtos;
	}

}
