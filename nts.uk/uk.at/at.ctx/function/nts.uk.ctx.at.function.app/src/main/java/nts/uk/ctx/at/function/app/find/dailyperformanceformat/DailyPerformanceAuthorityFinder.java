package nts.uk.ctx.at.function.app.find.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatSheet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DailyPerformanceAuthorityFinder {

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	@Inject
	AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private AttendanceItemListFinder attendanceItemFinder;

	@Inject
	private AuthorityDailyDetailFinder authorityDailyDetailFinder;

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	/*public DailyAttendanceItemAuthorityDto findAll(String dailyPerformanceFormatCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// 勤怠項目 - find attendance item
		List<AttendanceItemDto> attendanceItemDtos = this.attendanceItemFinder.find();

		if (attendanceItemDtos.isEmpty()) {
			DailyAttendanceItemAuthorityDto dailyAttendanceItemAuthorityDto = new DailyAttendanceItemAuthorityDto(null,
					null, null, 0);
			return dailyAttendanceItemAuthorityDto;
		}
		Map<Integer, AttendanceItemDto> attendanceItemMaps = attendanceItemDtos.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));

		// find daily detail
		DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto = this.authorityDailyDetailFinder
				.findDetail(dailyPerformanceFormatCode, sheetNo);

		// find monthly detail
		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto = new ArrayList<DailyAttendanceAuthorityDetailDto>();
		if (this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode)).isEmpty()) {
			dailyAttendanceAuthorityMonthlyDto = new ArrayList<>();
		}

		dailyAttendanceAuthorityMonthlyDto = this.authorityFormatMonthlyRepository
				.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode)).stream()
				.map(f -> {
					if (attendanceItemMaps.containsKey(f.getAttendanceItemId()))
						return new DailyAttendanceAuthorityDetailDto(f.getAttendanceItemId(),
								attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemDisplayNumber(),
								attendanceItemMaps.get(f.getAttendanceItemId()).getAttendanceItemName(),
								f.getDisplayOrder(), f.getColumnWidth());
					return null;
				}).collect(Collectors.toList());

		Integer isDefaultInitial = 0;
		boolean check = this.authorityFormatInitialDisplayRepository.checkExistData(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode));
		if (check) {
			isDefaultInitial = 1;
		} else
			isDefaultInitial = 0;

		DailyAttendanceItemAuthorityDto dailyAttendanceItemAuthorityDto = new DailyAttendanceItemAuthorityDto(
				attendanceItemDtos, dailyAttendanceAuthorityMonthlyDto, dailyAttendanceAuthorityDailyDto,
				isDefaultInitial);
		return dailyAttendanceItemAuthorityDto;
	}*/
	
	//勤怠項目の取得
	public DailyAttendanceItemAuthorityDto getTimeItems(String dailyPerformanceFormatCode, BigDecimal sheetNo) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		// 勤怠項目 - find attendance item
		List<AttendanceItemDto> attendanceItemDtos = this.attendanceItemFinder.find();
		Map<Integer, AttendanceItemDto> attendanceItemMaps = attendanceItemDtos.stream()
				.collect(Collectors.toMap(AttendanceItemDto::getAttendanceItemId, x -> x));
		if (attendanceItemDtos.isEmpty()) {
			DailyAttendanceItemAuthorityDto dailyAttendanceItemAuthorityDto = new DailyAttendanceItemAuthorityDto(null,
					null, null, 0);
			return dailyAttendanceItemAuthorityDto;
		}
		//会社の日次項目を取得する
		List<DailyAttendanceItemNameAdapterDto> listDailyAttendanceItemNameAdapterDaily = companyDailyItemService
				.getDailyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList());
		//会社の月次項目を取得する
		List<DailyAttendanceItemNameAdapterDto> listDailyAttendanceItemNameAdapterMonthly = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), Collections.emptyList(), Collections.emptyList());
		List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto = listDailyAttendanceItemNameAdapterMonthly
				.stream()
				.map(x -> new DailyAttendanceAuthorityDetailDto(x.getAttendanceItemId(),
						x.getAttendanceItemDisplayNumber(), x.getAttendanceItemName(), 0, new BigDecimal(0)))
				.collect(Collectors.toList());
		Optional<AuthorityFormatSheet> authorityFormatSheet = authorityFormatSheetRepository.find(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode), sheetNo);
		String sheetName = authorityFormatSheet.isPresent() ? authorityFormatSheet.get().getSheetName() : null;
		DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto = new DailyAttendanceAuthorityDailyDto(
				sheetNo, sheetName,
				listDailyAttendanceItemNameAdapterDaily.stream()
						.map(x -> new DailyAttendanceAuthorityDetailDto(x.getAttendanceItemId(),
								x.getAttendanceItemDisplayNumber(), x.getAttendanceItemName(), 0, new BigDecimal(0)))
						.collect(Collectors.toList()));
		Integer isDefaultInitial = 0;
		boolean check = this.authorityFormatInitialDisplayRepository.checkExistData(companyId,
				new DailyPerformanceFormatCode(dailyPerformanceFormatCode));
		if (check) {
			isDefaultInitial = 1;
		} else
			isDefaultInitial = 0;
		DailyAttendanceItemAuthorityDto dailyAttendanceItemAuthorityDto = new DailyAttendanceItemAuthorityDto(
				attendanceItemDtos, dailyAttendanceAuthorityMonthlyDto, dailyAttendanceAuthorityDailyDto,
				isDefaultInitial);
		return dailyAttendanceItemAuthorityDto;
	}
}
