package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.AttendanceItemDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDailyDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceItemAuthorityDto;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemNameImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceAuthorityFinder {

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	public DailyAttendanceItemAuthorityDto findAll(String dailyPerformanceFormatCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		//会社の日次項目を取得する
		List<AttendanceItemDto> attendanceItemDailyDtos = companyDailyItemService
				.getDailyItems(companyId, Optional.empty(), null, null).stream()
				.map(f -> new AttendanceItemDto(f.getAttendanceItemId()
						, f.getAttendanceItemName(), f.getAttendanceItemDisplayNumber()))
				.collect(Collectors.toList());
		Map<Integer, AttendanceItemDto> attendanceItemDailyMaps = attendanceItemDailyDtos.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		//会社の月次項目を取得する
		Map<Integer, AttItemNameImport> attendanceMonthlyItemMaps = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), null, null).stream()
				.collect(Collectors.toMap(AttItemNameImport::getAttendanceItemId, x -> x));

		// find daily detail
		List<AuthorityFomatDaily> authorityFormatDailies = this.authorityFormatDailyRepository
				.getAuthorityFormatDailyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode), sheetNo);
		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos = authorityFormatDailies.stream().map(f -> {
				if (attendanceItemDailyMaps.containsKey(f.getAttendanceItemId()))
					return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(),
							attendanceItemDailyMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
							attendanceItemDailyMaps.get(f.getAttendanceItemId()).getAttendanceItemName(),
							f.getDisplayOrder(), f.getColumnWidth());
				return null;
			}).collect(Collectors.toList());
		DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto = new DailyAttendanceAuthorityDailyDto(
				sheetNo,
				this.authorityFormatSheetRepository
						.find(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),sheetNo)
						.map(f -> f.getSheetName()).orElse(null),
				dailyAttendanceAuthorityDetailDtos);

		// find monthly detail
		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto = this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode)).stream()
				.map(f -> {
					if (attendanceMonthlyItemMaps.containsKey(f.getAttendanceItemId()))
						return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(),
								attendanceMonthlyItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
								attendanceMonthlyItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(),
								f.getDisplayOrder(), f.getColumnWidth());
					return null;
				}).collect(Collectors.toList());

		int isDefaultInitial = this.authorityFormatInitialDisplayRepository.checkExistData(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode)) ? 1 : 0;

		return new DailyAttendanceItemAuthorityDto(
				attendanceItemDailyDtos, dailyAttendanceAuthorityMonthlyDto, dailyAttendanceAuthorityDailyDto,
				isDefaultInitial);
	}

	//	public DailyAttendanceItemAuthorityDto findAll(String dailyPerformanceFormatCode, BigDecimal sheetNo) {
//		LoginUserContext login = AppContexts.user();
//		String companyId = login.companyId();
//
//		// 勤怠項目 - find attendance item
//		List<AttendanceItemDto> attendanceItemDtos = this.attendanceItemFinder.find();
//
//		if (attendanceItemDtos.isEmpty()) {
//			return new DailyAttendanceItemAuthorityDto(null,
//					null, null, 0);
//		}
//		Map<Integer, AttendanceItemDto> attendanceItemMaps = attendanceItemDtos.stream()
//				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
//
//		// find daily detail
//		DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto = this.authorityDailyDetailFinder
//				.findDetail(dailyPerformanceFormatCode, sheetNo);
//
//		// find monthly detail
//		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto = new ArrayList<DailyAttendanceAuthorityDetailDto>();
//		if (this.authorityFormatMonthlyRepository
//				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode)).isEmpty()) {
//			dailyAttendanceAuthorityMonthlyDto = new ArrayList<>();
//		}
//
//		dailyAttendanceAuthorityMonthlyDto = this.authorityFormatMonthlyRepository
//				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode)).stream()
//				.map(f -> {
//					if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
//						return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(),
//								attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
//								attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(),
//								f.getDisplayOrder(), f.getColumnWidth());
//					return null;
//				}).collect(Collectors.toList());
//
//		Integer isDefaultInitial = 0;
//		boolean check = this.authorityFormatInitialDisplayRepository.checkExistData(companyId,
//				new DailyPerformanceFormatCode(dailyPerformanceFormatCode));
//		if (check) {
//			isDefaultInitial = 1;
//		} else
//			isDefaultInitial = 0;
//
//		DailyAttendanceItemAuthorityDto dailyAttendanceItemAuthorityDto = new DailyAttendanceItemAuthorityDto(
//				attendanceItemDtos, dailyAttendanceAuthorityMonthlyDto, dailyAttendanceAuthorityDailyDto,
//				isDefaultInitial);
//		return dailyAttendanceItemAuthorityDto;
//	}
}
