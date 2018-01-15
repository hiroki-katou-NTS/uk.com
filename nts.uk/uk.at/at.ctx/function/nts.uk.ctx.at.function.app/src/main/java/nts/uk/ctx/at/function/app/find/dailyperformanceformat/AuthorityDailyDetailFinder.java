package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDailyDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AuthorityDailyDetailFinder {

	@Inject
	private AttendanceItemListFinder attendanceItemFinder;

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	public DailyAttendanceAuthorityDailyDto findDetail(String dailyPerformanceFormatCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttendanceItemDto> attendanceItemDtos = this.attendanceItemFinder.find();
		Map<Integer, AttendanceItemDto> attendanceItemMaps = attendanceItemDtos.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));

		List<AuthorityFomatDaily> authorityFomatDailies = this.authorityFormatDailyRepository
				.getAuthorityFormatDailyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode), sheetNo);

		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos = new ArrayList<>();
		if (!authorityFomatDailies.isEmpty()) {
			dailyAttendanceAuthorityDetailDtos = authorityFomatDailies.stream().map(f -> {
				if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
					return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(),
							attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
							attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(),
							f.getDisplayOrder(), f.getColumnWidth());
				return null;
			}).collect(Collectors.toList());
		}

		DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto = new DailyAttendanceAuthorityDailyDto(
				sheetNo,
				this.authorityFormatSheetRepository
						.find(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),sheetNo)
						.isPresent()
								? this.authorityFormatSheetRepository.find(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),sheetNo)
										.get().getSheetName()
								: null,
				dailyAttendanceAuthorityDetailDtos);

		return dailyAttendanceAuthorityDailyDto;

	}

}
