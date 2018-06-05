/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleCopyCommand;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleFinder.
 * @author HoangDD
 */
@Stateless
public class OutputItemDailyWorkScheduleFinder {
	
	/** The output item daily work schedule repository. */
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemDailyWorkScheduleRepository;
	
	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;
	
	@Inject
	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;
	
	@Inject
	private BusinessTypesRepository businessTypesRepository;
	
	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;
	
	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;
	
	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;
	
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	@Inject
	private FormatPerformanceAdapter formatPerformanceAdapter;
	
	// Input of algorithm when use enum ScreenUseAtr: 勤怠項目を利用する画面
	private static final int DAILY_WORK_SCHEDULE = 19;
	
	/** The Constant USE. */
	private static final int USE = 1;
	
	/** The Constant NOT_USE. */
	private static final int NOT_USE = 0;
	
	/** The Constant AUTHORITY. */
	// SettingUnitType.AUTHORITY.value
	private static final int AUTHORITY = 0;
	
	/** The Constant BUSINESS_TYPE. */
	// SettingUnitType.BUSINESS_TYPE.value
	private static final int BUSINESS_TYPE = 1;
	
	/**
	 * Find by cid.
	 *
	 * @param code the code
	 * @return the output item daily work schedule dto
	 */
	public Map<String, Object> findByCid() {
		String companyID = AppContexts.user().companyId();
		Map<String, Object> mapDtoReturn = new HashMap<>();
		
		// Start algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する
		// 対応するドメインモデル「画面で利用できる勤怠項目一覧」を取得する (get domain model đối ứng 「画面で利用できる勤怠項目一覧」 )
		List<AttendanceType> lstAttendanceType = attendanceTypeRepository.getItemByScreenUseAtr(companyID, DAILY_WORK_SCHEDULE);
		
		// Get domain 任意項目
		List<OptionalItem> lstOptionalItem = optionalItemRepository.findAll(companyID);
		
		// TODO: hoangdd - doi QA
//		lstOptionalItem = lstOptionalItem.stream()
//								.filter(domain -> (domain.getPerformanceAtr().value == PerformanceAtr.DAILY_PERFORMANCE.value
//													&& (domain.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value 
//														|| domain.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value
//														|| domain.getOptionalItemAtr().value == OptionalItemAtr.TIME.value))
//										)
//								.map(domain -> domain)
//								.collect(Collectors.toList());
		
		// Use condition filter to get 画面で利用できる勤怠項目一覧
		// dao nguoc lai de lay lstOptionalItem, sau do su dung file excel cua KH cung cap, + them 640 se ra duoc attendanceId
//		List<AttendanceType> lstAttendanceTypeFilter = lstOptionalItem.stream()
//				.flatMap(domainOptionItem -> lstAttendanceType.stream()
//								.filter(domainAttendance -> domainOptionItem.getPerformanceAtr().value == PerformanceAtr.DAILY_PERFORMANCE.value 
//																&& (
//																		( domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.TIME.value 
//																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.WORK_TIME.value)
//																		|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value 
//																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.ATTENDANCE_TIMES.value)
//																		|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value 
//																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.TOTAL_COMMUTING_AMOUNT.value)
//																		|| (domainAttendance.getScreenUseAtr().value == ScreenUseAtr.DAILY_WORK_SCHEDULE.value)
//																))
//								.map(domainAttendance -> domainAttendance))
//								.sorted(Comparator.comparing(AttendanceType::getAttendanceItemId))
//								.distinct()
//				.collect(Collectors.toList());
		List<OptionalItem> lstAttendanceTypeFilter = lstAttendanceType.stream()
				.flatMap(domainAttendance -> lstOptionalItem.stream()
								.filter(domainOptionItem -> domainOptionItem.getPerformanceAtr().value == PerformanceAtr.DAILY_PERFORMANCE.value 
																&& (
																		( domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.TIME.value 
																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.WORK_TIME.value)
																		|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value 
																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.ATTENDANCE_TIMES.value)
																		|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value 
																			&& domainAttendance.getScreenUseAtr().value == ScreenUseAtr.TOTAL_COMMUTING_AMOUNT.value)
																		|| (domainAttendance.getScreenUseAtr().value == ScreenUseAtr.DAILY_WORK_SCHEDULE.value
																			&& (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value 
																				|| domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value 
																				|| domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.TIME.value))
																))
								.map(domainOptionItem -> domainOptionItem))
//								.sorted(Comparator.comparing(AttendanceType::getAttendanceItemId))
								.distinct()
				.collect(Collectors.toList());
		
		// End algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する
		
		// Get list attendanceID from domain 画面で利用できる勤怠項目一覧 
//		List<Integer> lstAttendanceID = lstAttendanceTypeFilter.stream()
//													.map(domain -> domain.getAttendanceItemId())
//													.collect(Collectors.toList());
		
		// get list attendanceId of OptionalItem. according file 日次項目一覧.xls参照
		List<Integer> lstAttendanceID = lstAttendanceTypeFilter.stream()
				.map(domain -> domain.getOptionalItemNo().v() + 640)
				.collect(Collectors.toList());
		
		// get list attendanceId of AttendanceType
		List<Integer> lstAttendanceID2 = lstAttendanceType.stream()
				.map(domain -> domain.getAttendanceItemId())
				.collect(Collectors.toList());
		lstAttendanceID.addAll(lstAttendanceID2);
		
		// get domain 日次の勤怠項目
		// TODO: hoangdd - chua sort nhu note tren eap
		List<DailyAttendanceItem> lstDailyAttendanceItem = dailyAttendanceItemRepository.getListById(companyID, lstAttendanceID);
		mapDtoReturn.put("dailyAttendanceItem", lstDailyAttendanceItem.stream().map(domain -> {
																						DailyAttendanceItemDto dto = new DailyAttendanceItemDto();
																						dto.setCode(String.valueOf(domain.getAttendanceItemId()));
																						dto.setName(domain.getAttendanceName().v());
																						return dto;
																					}).collect(Collectors.toList()));
		
		Map<String, String> mapCodeManeAttendance = convertListToMapAttendanceItem((List<DailyAttendanceItemDto>) mapDtoReturn.get("dailyAttendanceItem"));
		
		// get all domain 日別勤務表の出力項目
		List<OutputItemDailyWorkSchedule> lstOutputItemDailyWorkSchedule = this.outputItemDailyWorkScheduleRepository.findByCid(companyID);
		
		// if find
		if (!lstOutputItemDailyWorkSchedule.isEmpty()) {
			mapDtoReturn.put("outputItemDailyWorkSchedule", lstOutputItemDailyWorkSchedule.stream()
									.map(domain -> {
										OutputItemDailyWorkScheduleDto dto = new OutputItemDailyWorkScheduleDto();
										dto.setItemCode(domain.getItemCode().v());
										dto.setItemName(domain.getItemName().v());
										dto.setLstDisplayedAttendance(toDtoTimeitemTobeDisplay(domain.getLstDisplayedAttendance(), mapCodeManeAttendance));
										dto.setLstRemarkContent(toDtoPrintRemarksContent(domain.getLstRemarkContent()));
										dto.setWorkTypeNameDisplay(domain.getWorkTypeNameDisplay().value);
										return dto;
									})
									.sorted(Comparator.comparing(OutputItemDailyWorkScheduleDto::getItemCode))
									.collect(Collectors.toList()));
		}
		
		// find nothing
		return mapDtoReturn;
	}
	
	/**
	 * Gets the format daily performance.
	 *
	 * @return the format daily performance
	 */
	// algorithm for screen D: start screen
	public List<DataInforReturnDto> getFormatDailyPerformance() {
		String companyId = AppContexts.user().companyId();
		// Get domain 実績修正画面で利用するフォーマット from request list 402
		Optional<FormatPerformanceImport> optFormatPerformanceImport = formatPerformanceAdapter.getFormatPerformance(companyId);
		
		if (!optFormatPerformanceImport.isPresent()) {
			return new ArrayList<>();
		}
		switch (optFormatPerformanceImport.get().getSettingUnitType()) 
		{
			case AUTHORITY: // In case of authority
				// Get domain 会社の日別実績の修正のフォーマット
				List<AuthorityDailyPerformanceFormat> lstAuthorityDailyPerformanceFormat = authorityDailyPerformanceFormatRepository.getListCode(companyId);
				return lstAuthorityDailyPerformanceFormat.stream()
							.map(obj -> {
								return new DataInforReturnDto(obj.getDailyPerformanceFormatCode().v(), obj.getDailyPerformanceFormatName().v());
							}).collect(Collectors.toList());
			case BUSINESS_TYPE: // In case of work type
				// Get doamin 勤務種別日別実績の修正のフォーマット
				List<BusinessTypeFormatDaily> lstBusinessTypeFormatDaily = businessTypeFormatDailyRepository.getBusinessTypeFormatByCompanyId(companyId);
				Set<String> setBusinessTypeFormatDailyCode = lstBusinessTypeFormatDaily.stream()
															.map(domain -> domain.getBusinessTypeCode().v())
															.collect(Collectors.toSet());
				
				// Get domain ドメインモデル「勤務種別」を取得する
				// businessTypeCode get from doamin 勤務種別日別実績の修正のフォーマット
				List<BusinessType> lstBusinessType = businessTypesRepository.findAll(companyId);
	
				return lstBusinessType.stream()
					.filter(domain -> setBusinessTypeFormatDailyCode.contains(domain.getBusinessTypeCode().v()))
					.map(domain -> new DataInforReturnDto(domain.getBusinessTypeCode().v(), domain.getBusinessTypeName().v()))
				.collect(Collectors.toList());
				default:
					return new ArrayList<>();
		}
	}
	
	// algorithm for screen D: copy
	public List<DataInforReturnDto> executeCopy(String codeCopy, String codeSourceSerivce, List<OutputItemDailyWorkScheduleCopyCommand> lstCommandCopy) {
		String companyId = AppContexts.user().companyId();
		
		// get domain 日別勤務表の出力項目
		Optional<OutputItemDailyWorkSchedule> optOutputItemDailyWorkSchedule = outputItemDailyWorkScheduleRepository.findByCidAndCode(companyId, new OutputItemSettingCode(codeCopy).v());
		
		if (optOutputItemDailyWorkSchedule.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			return getDomConvertDailyWork(companyId, codeSourceSerivce, lstCommandCopy);
		}
	}
	
	// アルゴリズム「日別勤務表用フォーマットをコンバートする」を実行する(Execute algorithm "Convert daily work table format")
	private List<DataInforReturnDto> getDomConvertDailyWork(String companyId, String code, List<OutputItemDailyWorkScheduleCopyCommand> lstCommandCopy) {
		// Get domain 実績修正画面で利用するフォーマット from request list 402
		Optional<FormatPerformanceImport> optFormatPerformanceImport = formatPerformanceAdapter.getFormatPerformance(companyId);
		
		List<DataInforReturnDto> lstDataReturn = new ArrayList<>();
		
		if (optFormatPerformanceImport.isPresent()) {
			switch (optFormatPerformanceImport.get().getSettingUnitType()) 
			{
				case AUTHORITY: // In case of authority
					// ドメインモデル「会社の日別実績の修正のフォーマット」を取得する (Acquire the domain model "format of company's daily performance correction")
					// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from "display items for correction of daily performance")
					List<AuthorityFomatDaily>  lstAuthorityFomatDaily = authorityFormatDailyRepository.getAuthorityFormatDailyDetail(companyId, new DailyPerformanceFormatCode(code), new BigDecimal(1));
					lstAuthorityFomatDaily.sort(Comparator.comparing(AuthorityFomatDaily::getDisplayOrder));
					lstDataReturn = lstAuthorityFomatDaily.stream()
															.map(domain -> new DataInforReturnDto(domain.getAttendanceItemId()+"", ""))
															.collect(Collectors.toList());
					break;
				case BUSINESS_TYPE:
					// ドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する (Acquire the domain model "Format of working type daily performance correction)
					// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from "display items for correction of daily performance")
					List<BusinessTypeFormatDaily> lstBusinessTypeFormatDaily = businessTypeFormatDailyRepository.getBusinessTypeFormat(companyId, new BusinessTypeCode(code).v());
					lstBusinessTypeFormatDaily.sort(Comparator.comparing(BusinessTypeFormatDaily::getOrder));
					lstDataReturn = lstBusinessTypeFormatDaily.stream()
															.map(domain -> new DataInforReturnDto(domain.getAttendanceItemId()+"", ""))
															.collect(Collectors.toList());
					break;
				default:
					break;
			}
		}
		
		Map<String, String> mapCodeName =  lstCommandCopy.stream()
				.collect(Collectors.toMap(OutputItemDailyWorkScheduleCopyCommand::getCode, 
										  OutputItemDailyWorkScheduleCopyCommand::getName));
		lstDataReturn = lstDataReturn.stream()
				.filter(domain -> mapCodeName.containsKey(domain.getCode()))
				.map(domain -> {
					domain.setName(mapCodeName.get(domain.getCode()));
					return domain;
				}).collect(Collectors.toList());
		
		if (lstDataReturn.size() <= 48) {
			return lstDataReturn;
		} else {
			return lstDataReturn.stream().limit(48).collect(Collectors.toList());
		}
	}
	
	/**
	 * To dto timeitem tobe display.
	 *
	 * @param lstDomainObject the lst domain object
	 * @return the list
	 */
	private List<TimeitemTobeDisplayDto> toDtoTimeitemTobeDisplay(List<AttendanceItemsDisplay> lstDomainObject, Map<String, String> mapCodeManeAttendance) {
		return lstDomainObject.stream()
									.map(domain -> {
										TimeitemTobeDisplayDto dto = new TimeitemTobeDisplayDto();
										dto.setAttendanceDisplay(domain.getAttendanceDisplay());
										dto.setOrderNo(domain.getOrderNo());
										dto.setAttendanceName(mapCodeManeAttendance.get(String.valueOf(domain.getAttendanceDisplay())));
										return dto;
									})
									.sorted(Comparator.comparing(TimeitemTobeDisplayDto::getOrderNo))
									.collect(Collectors.toList());
	}
	
	/**
	 * To dto print remarks content.
	 *
	 * @param lstDomainObject the lst domain object
	 * @return the list
	 */
	private List<PrintRemarksContentDto> toDtoPrintRemarksContent(List<PrintRemarksContent> lstDomainObject) {
		return lstDomainObject.stream()
								.map(domain -> {
									PrintRemarksContentDto dto = new PrintRemarksContentDto();
									dto.setPrintItem(domain.getPrintItem().value);
									dto.setUsedClassification(domain.isUsedClassification() ? USE : NOT_USE);
									return dto;
								})
								.sorted(Comparator.comparing(PrintRemarksContentDto::getPrintItem))
								.collect(Collectors.toList());
	} 
	
	/**
	 * Convert list to map attendance item.
	 *
	 * @param lst the lst
	 * @return the map
	 */
	private Map<String, String> convertListToMapAttendanceItem(List<DailyAttendanceItemDto> lst) {
		return lst.stream().collect(
                Collectors.toMap(DailyAttendanceItemDto::getCode, DailyAttendanceItemDto::getName));
	}
}
