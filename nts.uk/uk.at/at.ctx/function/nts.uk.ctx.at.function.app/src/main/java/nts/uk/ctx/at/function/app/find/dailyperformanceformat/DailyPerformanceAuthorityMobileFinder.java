package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDailyDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author anhdt
 *  表示フォーマットの初期表示
 */

@Stateless
public class DailyPerformanceAuthorityMobileFinder {

	@Inject
	private AuthorityDailyPerformanceSFormatRepository authorityMFormatDailyRepository;
	
	@Inject
	private AuthorityFormatMonthlySRepository authorityMFormatMonthlyRepository;

	public DailyAttendanceAuthorityDailyDto findAllByCode(String formatCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		/*ドメインモデル「会社の日別実績の修正のフォーマット（スマホ版）」を取得する*/
		AuthorityDailyPerformanceSFormat dom = authorityMFormatDailyRepository.getAllByCompanyIdAndCode(companyId,
				formatCode);
		if (dom.getDailyDisplayItem() != null) {
			List<DailyAttendanceAuthorityDetailDto> items = dom.getDailyDisplayItem()
			.stream()
			.map(itemDom -> {
				return new DailyAttendanceAuthorityDetailDto(itemDom.getAttendanceItemId(), itemDom.getDisplayOrder(), null);
			})
			.collect(Collectors.toList());
			
			return new DailyAttendanceAuthorityDailyDto(null, null, items);
		}

		return new DailyAttendanceAuthorityDailyDto(null, null, null);
	}

	public List<DailyAttendanceAuthorityDetailDto> findAllMonthly(String formatCode) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		// find monthly detail
		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto = this.authorityMFormatMonthlyRepository
				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(formatCode)).stream().map(f -> {
					return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(), f.getDisplayOrder(),
							null);
				}).collect(Collectors.toList());
		return dailyAttendanceAuthorityMonthlyDto;
	}
}
