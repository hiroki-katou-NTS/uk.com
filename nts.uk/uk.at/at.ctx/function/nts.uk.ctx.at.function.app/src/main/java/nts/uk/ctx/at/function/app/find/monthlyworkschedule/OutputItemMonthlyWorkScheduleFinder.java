package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.command.monthlyworkschedule.OutputItemMonthlyWorkScheduleCopyCommand;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyFormatPerformanceAdapter;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyFormatPerformanceImport;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemMonthlyWorkScheduleFinder.
 */
@Stateless
public class OutputItemMonthlyWorkScheduleFinder {

	/** The output item monthly work schedule repository. */
	@Inject
	private OutputItemMonthlyWorkScheduleRepository outputItemMonthlyWorkScheduleRepository;

	/** The attendance type repository. */
	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;

	/** The optional item repository. */
	@Inject
	private OptionalItemRepository optionalItemRepository;

	/** The monthly attendance item repository. */
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	/** The format performance adapter. */
	@Inject
	private MonthlyFormatPerformanceAdapter monthlyFormatPerformanceAdapter;

	/** The mon pfm correction format repository. */
	@Inject
	private MonPfmCorrectionFormatRepository monPfmCorrectionFormatRepository;

	/** The business types repository. */
	@Inject
	private BusinessTypesRepository businessTypesRepository;

	/** The authority format monthly repository. */
	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	/** The business type format monthly repository. */
	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	/** The Constant AUTHORITY. */
	// SettingUnitType.AUTHORITY.value
	private static final int AUTHORITY = 0;

	/** The Constant BUSINESS_TYPE. */
	// SettingUnitType.BUSINESS_TYPE.value
	private static final int BUSINESS_TYPE = 1;

	/**
	 * Find by cid.
	 *
	 * @return the map
	 */
	public Map<String, Object> findByCid() {
		String companyID = AppContexts.user().companyId();
		Map<String, Object> mapDtoReturn = new HashMap<>();

		// Start algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する
		// 対応するドメインモデル「画面で利用できる勤怠項目一覧」を取得する (get domain model đối ứng
		// 「画面で利用できる勤怠項目一覧」 )
		List<AttendanceType> lstAttendanceType = attendanceTypeRepository.getItemByScreenUseAtr(companyID,
				EnumAdaptor.valueOf(ScreenUseAtr.MONTHLY_WORK_SCHEDULE.value, ScreenUseAtr.class).value);

		// Get domain 任意項目
		List<OptionalItem> lstOptionalItem = optionalItemRepository.findAll(companyID);

		Set<Integer> setScreenUseAtr = lstAttendanceType.stream().map(domain -> domain.getScreenUseAtr().value)
				.collect(Collectors.toSet());

		List<OptionalItem> lstAttendanceTypeFilter = lstOptionalItem.stream().filter(domainOptionItem -> {
			// 日別勤務表 = 時間or回数or金額
			if ((domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.TIME.value
					|| domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value
					|| domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value)
					&& setScreenUseAtr.contains(ScreenUseAtr.DAILY_WORK_SCHEDULE.value)) {
				return true;
			}
			return false;
		}).map(domainOptionItem -> domainOptionItem).distinct().collect(Collectors.toList());
		// End algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する

		// get list attendanceId of OptionalItem 任意項目. according file
		// 日次項目一覧.xls参照
		List<Integer> lstAttendanceID = lstAttendanceTypeFilter.stream()
				.map(domain -> domain.getOptionalItemNo().v() + 588).collect(Collectors.toList());

		// get list attendanceId of AttendanceType 画面で利用できる勤怠項目一覧
		List<Integer> lstAttendanceID2 = lstAttendanceType.stream().map(domain -> domain.getAttendanceItemId())
				.collect(Collectors.toList());
		lstAttendanceID.addAll(lstAttendanceID2);

		// ドメインモデル「月次の勤怠項目」をすべて取得する(Acquire all domain model "monthly attendance
		// items")
		List<MonthlyAttendanceItem> lstMonthlyAttendanceItem = new ArrayList<>();
		if (!lstAttendanceID.isEmpty()) {
			lstMonthlyAttendanceItem = monthlyAttendanceItemRepository.findByAttendanceItemId(companyID,
					lstAttendanceID);
			mapDtoReturn.put("monthlyAttendanceItem", lstMonthlyAttendanceItem.stream().map(domain -> {
				MonthlyAttendanceItemDto dto = new MonthlyAttendanceItemDto();
				dto.setCode(String.valueOf(domain.getAttendanceItemId()));
				dto.setName(domain.getAttendanceName().v());
				return dto;
			}).collect(Collectors.toList()));
		} else {
			mapDtoReturn.put("monthlyAttendanceItem", Collections.emptyList());
		}

		/*
		 * mapDtoReturn.put("monthlyAttendanceItem",
		 * dailyAttendanceItemNameDomainService
		 * .getNameOfDailyAttendanceItem(lstAttendanceID).stream().map(domain ->
		 * { DailyAttendanceItemDto dto = new DailyAttendanceItemDto();
		 * dto.setCode(String.valueOf(domain.getAttendanceItemId()));
		 * dto.setName(domain.getAttendanceItemName()); return dto;
		 * }).collect(Collectors.toList()));
		 */

		Map<String, String> mapCodeManeAttendance = convertListToMapAttendanceItem(
				(List<MonthlyAttendanceItemDto>) mapDtoReturn.get("dailyAttendanceItem"));

		// ドメインモデル「月別勤務表の出力項目」をすべて取得する
		// get all domain OutputItemMonthlyWorkSchedule
		List<OutputItemMonthlyWorkSchedule> lstOutputItemMonthlyWorkSchedule = this.outputItemMonthlyWorkScheduleRepository
				.findByCid(companyID);

		if (!lstOutputItemMonthlyWorkSchedule.isEmpty()) {
			mapDtoReturn.put("outputItemMonthlyWorkSchedule", lstOutputItemMonthlyWorkSchedule.stream().map(domain -> {
				OutputItemMonthlyWorkScheduleDto dto = new OutputItemMonthlyWorkScheduleDto();
				dto.setItemCode(domain.getItemCode().v());
				dto.setItemName(domain.getItemName().v());
				dto.setLstDisplayedAttendance(
						toDtoTimeItemTobeDisplay(domain.getLstDisplayedAttendance(), mapCodeManeAttendance));
				dto.setPrintSettingRemarksColumn(domain.getPrintSettingRemarksColumn());
				return dto;
			}).sorted(Comparator.comparing(OutputItemMonthlyWorkScheduleDto::getItemCode))
					.collect(Collectors.toList()));
		}

		// find nothing
		return mapDtoReturn;
	}

	// algorithm for screen D: start screen
	public List<MonthlyDataInforReturnDto> getFormatMonthlyPerformance() {
		String companyId = AppContexts.user().companyId();
		// Get domain 実績修正画面で利用するフォーマット from request list 402
		Optional<MonthlyFormatPerformanceImport> optFormatPerformanceImport = monthlyFormatPerformanceAdapter
				.getFormatPerformance(companyId);

		if (!optFormatPerformanceImport.isPresent()) {
			return new ArrayList<>();
		}
		switch (optFormatPerformanceImport.get().getSettingUnitType()) {
		case AUTHORITY: // In case of authority
			// Get domain 会社の月別実績の修正のフォーマット
			List<MonPfmCorrectionFormat> lstMonPfmCorrectionFormat = monPfmCorrectionFormatRepository
					.getAllMonPfm(companyId);
			return lstMonPfmCorrectionFormat.stream().map(obj -> {
				return new MonthlyDataInforReturnDto(obj.getMonthlyPfmFormatCode().v(),
						obj.getMonPfmCorrectionFormatName().v());
			}).collect(Collectors.toList());
		case BUSINESS_TYPE: // In case of work type
			// Get domain 勤務種別月別実績の修正のフォーマット
			List<BusinessTypeFormatMonthly> lstBusinessTypeFormatMonthly = businessTypeFormatMonthlyRepository
					.getMonthlyDetailByCompanyId(companyId);
			Set<String> setBusinessTypeFormatMonthlyCode = lstBusinessTypeFormatMonthly.stream()
					.map(domain -> domain.getBusinessTypeCode().v()).collect(Collectors.toSet());

			// Get domain businessTypeCode ドメインモデル「勤務種別」を取得する
			List<BusinessType> lstBusinessType = businessTypesRepository.findAll(companyId);

			return lstBusinessType.stream()
					.filter(domain -> setBusinessTypeFormatMonthlyCode.contains(domain.getBusinessTypeCode().v()))
					.map(domain -> new MonthlyDataInforReturnDto(domain.getBusinessTypeCode().v(),
							domain.getBusinessTypeName().v()))
					.collect(Collectors.toList());
		default:
			return new ArrayList<>();
		}
	}

	// algorithm for screen D: copy
	public List<MonthlyDataInforReturnDto> executeCopy(String codeCopy, String codeSourceSerivce,
			List<OutputItemMonthlyWorkScheduleCopyCommand> lstCommandCopy) {
		String companyId = AppContexts.user().companyId();

		// get domain 月別勤務表の出力項目
		Optional<OutputItemMonthlyWorkSchedule> optOutputItemMonthlyWorkSchedule = outputItemMonthlyWorkScheduleRepository
				.findByCidAndCode(companyId, new OutputItemSettingCode(codeCopy).v());

		if (optOutputItemMonthlyWorkSchedule.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			return getDomConvertMonthlyWork(companyId, codeSourceSerivce, lstCommandCopy);
		}
	}

	// アルゴリズム「月別勤務表用フォーマットをコンバートする」を実行する(Execute algorithm "Convert monthly work
	// table format")
	private List<MonthlyDataInforReturnDto> getDomConvertMonthlyWork(String companyId, String code,
			List<OutputItemMonthlyWorkScheduleCopyCommand> lstCommandCopy) {

		// Get domain 実績修正画面で利用するフォーマット from request list 402
		Optional<MonthlyFormatPerformanceImport> optFormatPerformanceImport = monthlyFormatPerformanceAdapter
				.getFormatPerformance(companyId);

		List<MonthlyDataInforReturnDto> lstMonthlyDataReturn = new ArrayList<>();

		if (optFormatPerformanceImport.isPresent()) {
			switch (optFormatPerformanceImport.get().getSettingUnitType()) {
			case AUTHORITY: // In case of authority
				// ドメインモデル「会社の月別実績の修正のフォーマット (Acquire the domain model
				// "format of company's daily performance correction")
				// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from
				// "display items for correction of daily performance")
				// waitting nampt add function getBusinessTypeFormat with 3
				// parameter
				List<AuthorityFomatMonthly> lstAuthorityFomatMonthly = authorityFormatMonthlyRepository
						.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(code));
				lstAuthorityFomatMonthly.sort(Comparator.comparing(AuthorityFomatMonthly::getDisplayOrder));
				lstMonthlyDataReturn = lstAuthorityFomatMonthly.stream()
						.map(domain -> new MonthlyDataInforReturnDto(domain.getAttendanceItemId() + "", ""))
						.collect(Collectors.toList());
				break;
			case BUSINESS_TYPE:
				// ドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する (Acquire the domain model
				// "Format of working type daily performance correction)
				// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from
				// "display items for correction of daily performance")

				List<BusinessTypeFormatMonthly> lstBusinessTypeFormatMonthly = businessTypeFormatMonthlyRepository
						.getMonthlyDetail(companyId, new BusinessTypeCode(code).v());
				lstBusinessTypeFormatMonthly.sort(Comparator.comparing(BusinessTypeFormatMonthly::getOrder));
				lstMonthlyDataReturn = lstBusinessTypeFormatMonthly.stream()
						.map(domain -> new MonthlyDataInforReturnDto(domain.getAttendanceItemId() + "", ""))
						.collect(Collectors.toList());
				break;
			default:
				break;
			}
		}

		Map<String, String> mapCodeName = lstCommandCopy.stream().collect(Collectors.toMap(
				OutputItemMonthlyWorkScheduleCopyCommand::getCode, OutputItemMonthlyWorkScheduleCopyCommand::getName));
		lstMonthlyDataReturn = lstMonthlyDataReturn.stream().filter(domain -> mapCodeName.containsKey(domain.getCode()))
				.map(domain -> {
					domain.setName(mapCodeName.get(domain.getCode()));
					return domain;
				}).collect(Collectors.toList());

		if (lstMonthlyDataReturn.size() <= 39) {
			return lstMonthlyDataReturn;
		} else {
			return lstMonthlyDataReturn.stream().limit(48).collect(Collectors.toList());
		}
	}

	private List<TimeItemTobeDisplayDto> toDtoTimeItemTobeDisplay(List<MonthlyAttendanceItemsDisplay> lstDomainObject,
			Map<String, String> mapCodeNameAttendance) {
		return lstDomainObject.stream().map(domain -> {
			TimeItemTobeDisplayDto dto = new TimeItemTobeDisplayDto();
			dto.setAttendanceDisplay(domain.getAttendanceDisplay());
			dto.setOrderNo(domain.getOrderNo());
			dto.setAttendanceName(mapCodeNameAttendance.get(String.valueOf(domain.getAttendanceDisplay())));
			return dto;
		}).sorted(Comparator.comparing(TimeItemTobeDisplayDto::getOrderNo)).collect(Collectors.toList());
	}

	/**
	 * Convert list to map attendance item.
	 *
	 * @param lst
	 *            the lst
	 * @return the map
	 */
	private Map<String, String> convertListToMapAttendanceItem(List<MonthlyAttendanceItemDto> lst) {
		return lst.stream()
				.collect(Collectors.toMap(MonthlyAttendanceItemDto::getCode, MonthlyAttendanceItemDto::getName));
	}
}
