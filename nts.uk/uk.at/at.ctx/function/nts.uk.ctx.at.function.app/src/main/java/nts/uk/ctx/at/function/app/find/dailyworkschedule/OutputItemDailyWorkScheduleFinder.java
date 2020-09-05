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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.command.dailyworkschedule.OutputItemDailyWorkScheduleCopyCommand;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FontSizeEnum;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemUsedRepository;
//import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.DailyItemDto;
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
	
//	@Inject
//	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private BusinessTypesRepository businessTypesRepository;
	
	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;
	
	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;
	
	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;
	
	@Inject
	private FormatPerformanceAdapter formatPerformanceAdapter;
	
	@Inject
	private DailyAttendanceItemNameDomainService dailyAttendanceItemNameDomainService;
	
	@Inject
	private CompanyDailyItemService companyDailyItemService;
	
	@Inject
	private AttendanceItemNameService attendanceItemNameService;

	/** The output standard setting repository. */
	@Inject
	private OutputStandardSettingRepository outputStandardSettingRepository;
	
	/** The free setting of output item repository. */
	@Inject
	private FreeSettingOfOutputItemRepository freeSettingOfOutputItemRepository;

	/** The daily attendance item used repository. */
	@Inject
	private DailyAttendanceItemUsedRepository dailyAttendanceItemUsedRepository;
	
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
	
	/** The Constant SHEET_NO_1. */
	private static final int SHEET_NO_1 = 1;
	
	private static final String AUTHORITY_DEFINE = "権限";
	private static final String BUSINESS_TYPE_DEFINE = "勤務種別";

	public Map<String, Object> startScreenC(Optional<String> layoutId, int selectionType) {	
		String companyID = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		Map<String, Object> mapDtoReturn = new HashMap<>();
		
		List<OutputItemDailyWorkSchedule> lstDomainModel;
		
		// Input．項目選択種類をチェック (Check the selection type of Input.item)
		// Type: 定型選択の場合
		if (selectionType == ItemSelectionType.STANDARD_SELECTION.value) {

			// 定型設定の出力項目を取得 (Get the output item for fix-form setup)
			Optional<OutputStandardSettingOfDailyWorkSchedule> standardSetting = this.outputStandardSettingRepository
					.getStandardSettingByCompanyId(companyID);
			lstDomainModel = standardSetting.isPresent() ? standardSetting.get().getOutputItems() : new ArrayList<>();
		
		// Type: 自由設定の場合
		} else {
			
			// 社員IDから自由設定の出力項目を取得 (Get the output item for free setup from Company ID)
			Optional<FreeSettingOfOutputItemForDailyWorkSchedule> freeSetting = this.freeSettingOfOutputItemRepository
					.getFreeSettingByCompanyAndEmployee(companyID, employeeId);
			lstDomainModel = freeSetting.isPresent() ? freeSetting.get().getOutputItemDailyWorkSchedules() : new ArrayList<>();
		}
		
		// 取得したドメインモデル「日別勤務表の出力項目」からList<「出力項目設定>を作成 (Creat list [出力項目設定]from the
		// acquired domain model 「日別勤務表の出力項目」)
		mapDtoReturn.put("outputItemDailyWorkSchedule", lstDomainModel.stream()
				.map(domain -> {
					OutputItemSettingDto dto = new OutputItemSettingDto();
					dto.setCode(String.valueOf(domain.getItemCode().v()));
					dto.setName(domain.getItemName().v());
					dto.setLayoutId(domain.getOutputLayoutId());
					return dto;
				})
				.sorted(Comparator.comparing(OutputItemSettingDto::getCode)).collect(Collectors.toList()));

		
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
		
		List<DataInforReturnDto> lstData;
		
		if (!optFormatPerformanceImport.isPresent()) {
			return new ArrayList<>();
		}
		switch (optFormatPerformanceImport.get().getSettingUnitType()) 
		{
			case AUTHORITY: // In case of authority
				// Get domain 会社の日別実績の修正のフォーマット
				List<AuthorityDailyPerformanceFormat> lstAuthorityDailyPerformanceFormat = authorityDailyPerformanceFormatRepository.getListCode(companyId);
				lstData = lstAuthorityDailyPerformanceFormat.stream()
							.map(obj -> {
								DataInforReturnDto dto = new DataInforReturnDto();
								dto.setCode(obj.getDailyPerformanceFormatCode().v());
								dto.setName(obj.getDailyPerformanceFormatName().v());
								return dto;
							}).collect(Collectors.toList());
				if (lstData.isEmpty()) {
					throw new BusinessException("Msg_1410", new String[]{AUTHORITY_DEFINE});
				} 
				return lstData;
			case BUSINESS_TYPE: // In case of work type
				// Get doamin 勤務種別日別実績の修正のフォーマット
				List<BusinessTypeFormatDaily> lstBusinessTypeFormatDaily = businessTypeFormatDailyRepository.getBusinessTypeFormatByCompanyId(companyId);
				Set<String> setBusinessTypeFormatDailyCode = lstBusinessTypeFormatDaily.stream()
															.map(domain -> domain.getBusinessTypeCode().v())
															.collect(Collectors.toSet());
				
				// Get domain ドメインモデル「勤務種別」を取得する
				// businessTypeCode get from doamin 勤務種別日別実績の修正のフォーマット
				List<BusinessType> lstBusinessType = businessTypesRepository.findAll(companyId);
	
				lstData = lstBusinessType.stream()
					.filter(domain -> setBusinessTypeFormatDailyCode.contains(domain.getBusinessTypeCode().v()))
					.map(domain -> {
						DataInforReturnDto dto = new DataInforReturnDto();
						dto.setCode(domain.getBusinessTypeCode().v());
						dto.setName(domain.getBusinessTypeName().v());
						return dto;
					})
				.collect(Collectors.toList());
				if (lstData.isEmpty()) {
					throw new BusinessException("Msg_1410", new String[]{BUSINESS_TYPE_DEFINE});
				}
				return lstData;
			default:
				return new ArrayList<>();
		}
	}
	
	// algorithm for screen D: copy
	public DataReturnDto executeCopy(String codeCopy, String codeSourceSerivce, Integer selection, Integer fontSize) {
		String companyId = AppContexts.user().companyId();
		
		// Input．項目選択種類をチェック (Check the selection type of Input.item)
		// Type: 定型選択の場合
		if (selection == ItemSelectionType.STANDARD_SELECTION.value) {

			// 定型設定の出力項目を取得 (Get the output item for fix-form setup)
			Optional<OutputStandardSettingOfDailyWorkSchedule> standardSetting = this.outputStandardSettingRepository
					.findByCompanyIdAndCode(companyId, codeCopy);
			
			if (standardSetting.isPresent()) {
				throw new BusinessException("Msg_3");
			}
		
		// Type: 自由設定の場合
		} else {
			String employeeId = AppContexts.user().employeeId();

			// 社員IDから自由設定の出力項目を取得 (Get the output item for free setup from Company ID)
			Optional<FreeSettingOfOutputItemForDailyWorkSchedule> freeSetting = this.freeSettingOfOutputItemRepository
					.findByCompanyIdAndEmployeeIdAndCode(companyId, employeeId, codeCopy);
			
			if (freeSetting.isPresent()) {
				throw new BusinessException("Msg_3");
			}
		}
		DataReturnDto dataReturnDto = getDomConvertDailyWork(companyId, codeSourceSerivce, fontSize);
		//List<DataInforReturnDto> lstData = getDomConvertDailyWork(companyId, codeSourceSerivce);
		if (dataReturnDto.getDataInforReturnDtos().isEmpty()) {
			throw new BusinessException("Msg_1411");
		} 
		return dataReturnDto;
	}
	
	// アルゴリズム「日別勤務表用フォーマットをコンバートする」を実行する(Execute algorithm "Convert daily work table format")
	private DataReturnDto getDomConvertDailyWork(String companyId, String codeSourceSerivce, Integer fontSize) {
		// Get domain 実績修正画面で利用するフォーマット from request list 402
		Optional<FormatPerformanceImport> optFormatPerformanceImport = formatPerformanceAdapter.getFormatPerformance(companyId);
		
		DataReturnDto dataReturnDto = new DataReturnDto();
		List<DataInforReturnDto> lstDataReturn = new ArrayList<>();
		
		if (optFormatPerformanceImport.isPresent()) {
			switch (optFormatPerformanceImport.get().getSettingUnitType()) 
			{
				case AUTHORITY: // In case of authority
					// ドメインモデル「会社の日別実績の修正のフォーマット」を取得する (Acquire the domain model "format of company's daily performance correction")
					// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from "display items for correction of daily performance")
					List<AuthorityFomatDaily>  lstAuthorityFomatDaily = authorityFormatDailyRepository.getAuthorityFormatDailyDetail(companyId, new DailyPerformanceFormatCode(codeSourceSerivce));
					lstAuthorityFomatDaily.sort(Comparator.comparing(AuthorityFomatDaily::getDisplayOrder));
					lstDataReturn = lstAuthorityFomatDaily.stream()
															.map(domain -> {
																DataInforReturnDto dto = new DataInforReturnDto();
																dto.setId(domain.getAttendanceItemId());
																return dto;
															})
															.collect(Collectors.toList());
					break;
				case BUSINESS_TYPE:
					// ドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する (Acquire the domain model "Format of working type daily performance correction)
					// 「日別実績の修正の表示項目」から表示項目を取得する (Acquire display items from "display items for correction of daily performance")
					List<BusinessTypeFormatDaily> lstBusinessTypeFormatDaily = businessTypeFormatDailyRepository.getBusinessTypeFormatDailyDetail(companyId, new BusinessTypeCode(codeSourceSerivce).v());
					lstBusinessTypeFormatDaily.sort(Comparator.comparing(BusinessTypeFormatDaily::getOrder));
					lstDataReturn = lstBusinessTypeFormatDaily.stream()
															.map(domain -> {
																DataInforReturnDto dto = new DataInforReturnDto();
																dto.setId(domain.getAttendanceItemId());
																return dto;
															})
															.collect(Collectors.toList());
					break;
				default:
					break;
			}
		}
		
		// get list data was showed in kwr001
		List<AttendanceType> lstAttendanceType = attendanceTypeRepository.getItemByScreenUseAtr(companyId, DAILY_WORK_SCHEDULE);
		List<Integer> lstAttendanceID = lstAttendanceType.stream().map(domain -> domain.getAttendanceItemId()).collect(Collectors.toList());
		
		List<OutputItemDailyWorkScheduleCopyCommand> lstCommandCopy = companyDailyItemService.getDailyItems(companyId, Optional.empty(), lstAttendanceID, new ArrayList<>()).stream()
												.map(dto -> { 
													OutputItemDailyWorkScheduleCopyCommand dtoClientReturn = new OutputItemDailyWorkScheduleCopyCommand();
													dtoClientReturn.setCode(String.valueOf(dto.getAttendanceItemDisplayNumber()));
													dtoClientReturn.setId(dto.getAttendanceItemId());
													dtoClientReturn.setName(dto.getAttendanceItemName());
													return dtoClientReturn;
												}).collect(Collectors.toList());
		
		
		Map<Integer, String> mapIdName =  lstCommandCopy.stream()
				.collect(Collectors.toMap(OutputItemDailyWorkScheduleCopyCommand::getId, 
										  OutputItemDailyWorkScheduleCopyCommand::getName));
		// compare data return from kdw008 to kwr001
		// if item of kwr008 exist in kwr001, it will be save
		int sizeData = lstDataReturn.size();
		lstDataReturn = lstDataReturn.stream()
				.filter(domain -> mapIdName.containsKey(domain.getId()))
				.map(domain -> {
					domain.setName(mapIdName.get(domain.getId()));
					return domain;
				}).collect(Collectors.toList());
		
		if (sizeData != lstDataReturn.size()) {
			List<String> lstMsgErr = new ArrayList<String>();
			lstMsgErr.add("Msg_1476");
			dataReturnDto.setMsgErr(lstMsgErr);
		}
		// 1Sheet目の表示項目を返り値とする (Coi hạng mục hiển thj của sheet đầu tiên là giá trị trả về)
		
		dataReturnDto.setDataInforReturnDtos(lstDataReturn);
		
		// 　・大の場合：48件までとする
		int numberDisplayItem = 48;

		// 表示項目の件数を最大表示件数とチェックする(Check số hạng mục hiển thị)
		if (fontSize == FontSizeEnum.SMALL.value) {
			// 小の場合：60件までとする
			numberDisplayItem = 60;
		}
		if (lstDataReturn.size() <= numberDisplayItem) {
			return dataReturnDto;
		} else { // 1Sheet目の表示項目の先頭48項目までを返り値とする(Lấy 48 hạng mục đầu trong sheet đầu tiên làm giá trị trả về)
			List<DataInforReturnDto> lstDto = dataReturnDto.getDataInforReturnDtos().stream().limit(numberDisplayItem)
					.collect(Collectors.toList());
			dataReturnDto.setDataInforReturnDtos(lstDto);
			return dataReturnDto;
		}
	}
	
	/**
	 * To dto timeitem tobe display.
	 *
	 * @param lstDomainObject the lst domain object
	 * @return the list
	 */
	private List<TimeitemTobeDisplayDto> toDtoTimeitemTobeDisplay(List<AttendanceItemsDisplay> lstDomainObject, Map<Integer, String> mapCodeManeAttendance) {
		return lstDomainObject.stream()
									.map(domain -> {
										TimeitemTobeDisplayDto dto = new TimeitemTobeDisplayDto();
										dto.setAttendanceDisplay(domain.getAttendanceDisplay());
										dto.setOrderNo(domain.getOrderNo());
										dto.setAttendanceName(mapCodeManeAttendance.get(domain.getAttendanceDisplay()));
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
	
	public OutputItemDailyWorkScheduleDto findByLayoutId(String layoutId) {
		String companyId = AppContexts.user().companyId();
		OutputItemDailyWorkScheduleDto dtoOIDW = new OutputItemDailyWorkScheduleDto();
		OutputItemDailyWorkSchedule domainOIDW = outputItemDailyWorkScheduleRepository.findByLayoutId(layoutId).get();

		Map<Integer, String> mapIdNameAttendance = dailyAttendanceItemNameDomainService.getNameOfDailyAttendanceItem(domainOIDW.getLstDisplayedAttendance()
																					.stream()
																					.map(atdId -> atdId.getAttendanceDisplay())
																					.collect(Collectors.toList()))
											.stream()
											.collect(Collectors.toMap(DailyAttendanceItem::getAttendanceItemId, DailyAttendanceItem::getAttendanceItemName));
		
		dtoOIDW.setItemCode(domainOIDW.getItemCode().v());
		dtoOIDW.setItemName(domainOIDW.getItemName().v());
		dtoOIDW.setLstDisplayedAttendance(toDtoTimeitemTobeDisplay(domainOIDW.getLstDisplayedAttendance(), mapIdNameAttendance));
		dtoOIDW.setLstRemarkContent(toDtoPrintRemarksContent(domainOIDW.getLstRemarkContent()));
		dtoOIDW.setWorkTypeNameDisplay(domainOIDW.getWorkTypeNameDisplay().value);
		dtoOIDW.setRemarkInputNo(domainOIDW.getRemarkInputNo().value);
		return dtoOIDW;
	}
	
	/**
	 * Get output standard setting
	 *
	 * @param companyId 会社ID
	 * @return 「日別勤務表の出力項目定型設定」 dto
	 */
	public OutputStandardSettingOfDailyWorkScheduleDto getStandardSetting(String companyId) {
		
		// call ドメインモデル「日別勤務表の出力項目定型設定」を取得 (Get domain model 「日別勤務表の出力項目定型設定」)
		Optional<OutputStandardSettingOfDailyWorkSchedule> oDomain = this.outputStandardSettingRepository
				.getStandardSettingByCompanyId(companyId);

		return oDomain.map(d -> OutputStandardSettingOfDailyWorkScheduleDto.toStandardDto(d)).orElse(null);
	}

	/**
	 * Gets the free setting.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the free setting
	 */
	public FreeSettingOfOutputItemForDailyWorkScheduleDto getFreeSetting(String companyId, String employeeId) {
		Optional<FreeSettingOfOutputItemForDailyWorkSchedule> oDomain = this.freeSettingOfOutputItemRepository
				.getFreeSettingByCompanyAndEmployee(companyId, employeeId);

		return oDomain.map(d -> FreeSettingOfOutputItemForDailyWorkScheduleDto.toFreeSettingDto(d)).orElse(null);
	}
	
	/**
	 * 画面で使用可能な日次勤怠項目を取得する(Get daily attendance items available on screen)
	 *
	 * @param companyId 会社ID
	 * @param formId 帳票ID
	 * @param type 勤怠項目の種類
	 * @return the daily attendance items avaiable
	 */
	public List<Integer> getDailyAttendanceItemsAvaiable(String companyId, FormCanUsedForTime formId, TypeOfItem type) {
		
		// アルゴリズム「帳票で利用できる日次の勤怠項目を取得する」を実行する Thực hiện thuật toán 「帳票で利用できる日次の勤怠項目を取得する」
		List<Integer> dailyItemUsed = this.dailyAttendanceItemUsedRepository.getAllDailyItemId(companyId,
				BigDecimal.valueOf(formId.value));
		
		// アルゴリズム「使用不可の勤怠項目を除く」を実行する Thực hiện thuật toán 「使用不可の勤怠項目を除く」
		List<Integer> avaiableItem = this.attendanceItemNameService.getAvaiableAttendanceItem(companyId, type, dailyItemUsed);

		return avaiableItem;
		
	}

	public SelectedInformationItemDto getSlectedInformation(String companyId, Optional<String> layoutId,
			Optional<OutputItemDailyWorkSchedule> outputItem) {
		
		// 画面で使用可能な日次勤怠項目を取得する(Get daily attendance items available on screen)
		List<Integer> avaiableItems = this.getDailyAttendanceItemsAvaiable(companyId,
				FormCanUsedForTime.DAILY_WORK_SCHEDULE, TypeOfItem.Daily);
		
		// 日次勤怠項目に対応する名称、属性を取得する (Get the name and attribute corresponding to the daily
		// attendance item)
		List<DailyItemDto> dailyItemDtos = this.companyDailyItemService.findByAttendanceItems(companyId, avaiableItems);
		
		Map<Integer, DailyItemDto> mapAttendanceItem = dailyItemDtos.stream()
				.collect(Collectors.toMap(DailyItemDto::getTimeId, Function.identity()));

		// Check Input．Optional＜日別勤務表の出力項目＞with Input．Optional＜出力レイアウトID＞
		// Input．Optional＜出力レイアウトID＞　設定あり　(空ではない)( != Empty)
		// AND
		// Input．Optional＜日別勤務表の出力項目＞ 設定なし　(空)( = Empty)
		if (layoutId.isPresent() && !outputItem.isPresent()) {
			// Input．Optional＜日別勤務表の出力項目＞　←　ドメインモデル「日別勤務表の出力項目」を取得
			outputItem = this.outputItemDailyWorkScheduleRepository.findByLayoutId(layoutId.get());
		}

		if (outputItem.isPresent()) {
			//  「Input．Optional＜日別勤務表の出力項目＞．表示する勤怠項目」と取得したList＜勤怠項目ID、名称、属性、マスタの種類、表示番号＞を結合してセットす
			List<InformationItemDto> displayDtos = outputItem.get().getLstDisplayedAttendance().stream()
					.map(t -> {
						DailyItemDto item = mapAttendanceItem.get(t.getAttendanceDisplay());
						if (item != null) {
							 return InformationItemDto.builder()
									 .attendanceItemAtt(t.getAttendanceDisplay())
									 .attendanceItemAtt(item.getAttribute())
									 .masterType(item.getMasterType())
									 .name(item.getName())
									 .orderNo(t.getOrderNo())
									 .build();
						}
						return null;
					}).filter(Objects::nonNull).collect(Collectors.toList());
			
			List<PrintRemarksContentDto> printRemarksContents = this
					.toDtoPrintRemarksContent(outputItem.get().getLstRemarkContent());
			
			List<Integer> attendanceItemFromOutputItem = outputItem.get().getLstDisplayedAttendance().stream()
					.map(AttendanceItemsDisplay::getAttendanceDisplay)
					.collect(Collectors.toList());

			List<InformationItemDto> possibleSelectedItem = dailyItemDtos.stream()
					.filter(t -> !attendanceItemFromOutputItem.contains(t.getTimeId()))
					.map(item -> InformationItemDto.builder()
								 .attendanceItemAtt(item.getTimeId())
								 .attendanceItemAtt(item.getAttribute())
								 .masterType(item.getMasterType())
								 .name(item.getName())
								 .build())
					.collect(Collectors.toList());
		}
		
		return new SelectedInformationItemDto();
	}

}
