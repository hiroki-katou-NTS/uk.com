package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author anhdt
 * 表示フォーマットの初期表示											
 */
@Stateless
public class DailyPerformanceMobileCodeFinder {

	@Inject
	private AuthorityDailyPerformanceSFormatRepository authDailyPerMobFormatRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	public List<DailyPerformanceCodeDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<DailyPerformanceCodeDto> dailyPerformanceCodeDtos = new ArrayList<DailyPerformanceCodeDto>();
		List<AuthorityDailyPerformanceSFormat> listDailyFormat = this.authDailyPerMobFormatRepository
				.getAllByCompanyId(companyId);
		/*ドメインモデル「会社の日別実績の修正のフォーマット（スマホ版）」を取得する*/
		for (AuthorityDailyPerformanceSFormat format : listDailyFormat) {
			boolean defaultInitial = this.authorityFormatInitialDisplayRepository.checkExistData(
					companyId,
					format.getDailyPerformanceFormatCode(), 
					PCSmartPhoneAtt.SMART_PHONE);
			DailyPerformanceCodeDto dto = new DailyPerformanceCodeDto(format.getDailyPerformanceFormatCode().v(),
					format.getDailyPerformanceFormatName().v(), defaultInitial);
			dailyPerformanceCodeDtos.add(dto);
		}
		
		dailyPerformanceCodeDtos
				.sort((c1, c2) -> c1.getDailyPerformanceFormatCode().compareTo(c2.getDailyPerformanceFormatCode()));

		return dailyPerformanceCodeDtos;
	}

}
