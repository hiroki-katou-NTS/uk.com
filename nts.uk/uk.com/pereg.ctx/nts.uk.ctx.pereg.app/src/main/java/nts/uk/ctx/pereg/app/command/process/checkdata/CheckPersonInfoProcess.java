package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridLayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.ProductType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;


@Stateless
public class CheckPersonInfoProcess {
	
	final String chekPersonInfoType = TextResource.localize("CPS013_5");
   
	final List<String> lisCategorythistory = Arrays.asList("CS00003", "CS00004", "CS00014",
			"CS00016", "CS00017", "CS00018", "CS00019", "CS00020", "CS00021", "CS00070");
	
	final List<String> listPersistentResidentHistoryCtg = Arrays.asList("CS00004","CS00014","CS00016","CS00017","CS00020","CS00070");
	
	final List<String> listPersistentResidentHisAndPersistentHisCtg = Arrays.asList("CS00004","CS00014","CS00016","CS00017","CS00020","CS00021","CS00070");
	
	final List<String> listItemToCheckSpace =  Arrays.asList("IS00003","IS00004","IS00015","IS00016");
	
	final List<String> listItem_Master_History = Arrays.asList("IS00084","IS00085","IS00079");
	
	private static final List<String> standardDateItemCodes = Arrays.asList("IS00020", "IS00077", "IS00082", "IS00119", "IS00781");
	

	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;
	
	private static final String JP_SPACE = "　";
	
	// 個人基本情報チェック (Check thông tin cá nhân cơ bản)
	public void checkPersonInfo(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee,
			CheckDataFromUI excuteCommand,Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, EmployeeDataMngInfo employee, String bussinessName, List<ErrorInfoCPS013> errors) {
		
		// アルゴリズム「履歴整合性チェック」を実行する (Thực thi thuật toán 「Check tính hợp lệ của lịch sử」)
		checkCategoryHistory(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, errors, employee, bussinessName);
		
		// システム必須チェック (Kiểm tra required system)
		checkSystemRequired(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, errors, employee, bussinessName);
		
		// 参照項目チェック処理 (Check item tham chiếu)
		checkReferenceItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, errors, employee, bussinessName);
		
		//単一項目チェック(Check single item )
		checkSingleItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, errors, employee, bussinessName);
		
	}

	private void checkSingleItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		Optional<PersonInfoCategory> isExitCS00020 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00020")).findFirst();
		Optional<PersonInfoCategory> isExitCS00070 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00070")).findFirst();
		List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1 = new ArrayList<>();
		List<LayoutPersonInfoValueDto> itemsOfWorkingCondition2 = new ArrayList<>();
		
		for (int i = 0; i < listCategory.size(); i++) {
			PersonInfoCategory category =  listCategory.get(i);
			List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(category.getCategoryCode().v());
		
			listDataEmpOfCtg.forEach(data -> {
				List<LayoutPersonInfoValueDto> items = new ArrayList<>();
				List<LayoutPersonInfoClsDto> layoutDtos = data.getLayoutDtos();
				layoutDtos.forEach(item -> {
					items.addAll(item.getItems());
					if (category.getCategoryCode().v().equals("CS00020")){
						itemsOfWorkingCondition1.addAll(item.getItems());
					}
					if (category.getCategoryCode().v().equals("CS00070")) {
						itemsOfWorkingCondition2.addAll(item.getItems());
					}
				});
				
				items.forEach(item -> {
					
					// 必須項目のNULLチェック (Check Null cho các requiredItem)
					if(item.isRequired()){
						checkNullOfRequiredItems(item, employee, category, bussinessName, result);
					}
				});
				
				// 項目の特殊チェック (Check item đặc biệt)
				if (category.getCategoryCode().v().equals("CS00002")) {
					checkItemSpecial(items, employee, category, bussinessName, result);
				}
				
				// 日付の逆転チェック (Check đảo ngược date )
				if (lisCategorythistory.contains(category.getCategoryCode().v())) {
					checkDateReversal(items, employee, category, bussinessName, result); 
				}
			});
			
			// アルゴリズム「就業時間帯の必須チェック」を実行する (Thực hiện thuật toán [check required của worktime])
			if (isExitCS00020.isPresent()) {
				checkRequiredWorktime1(itemsOfWorkingCondition1, employee, isExitCS00020.get(), bussinessName, result);
			}
			
			if (isExitCS00070.isPresent()) {
				checkRequiredWorktime2(itemsOfWorkingCondition2, employee, isExitCS00070.get(), bussinessName, result);
			}
		}
	}

	private void checkRequiredWorktime2(List<LayoutPersonInfoValueDto> itemsOfWorkingCondition2,
			EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName,
			List<ErrorInfoCPS013> result) {
		
		Optional<LayoutPersonInfoValueDto> IS00184Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00184")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00193Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00193")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00202Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00202")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00211Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00211")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00220Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00220")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00229Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00229")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00238Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00238")).findFirst();
		
		
		if (IS00184Opt.isPresent()) {
			CheckWorkTime(IS00184Opt.get(),"IS00185", "IS00187", "IS00188" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00193Opt.isPresent()) {
			CheckWorkTime(IS00193Opt.get(),"IS00194", "IS00196", "IS00197" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00202Opt.isPresent()) {
			CheckWorkTime(IS00202Opt.get(),"IS00203", "IS00205", "IS00206" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00211Opt.isPresent()) {
			CheckWorkTime(IS00211Opt.get(),"IS00212", "IS00214", "IS00215" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00220Opt.isPresent()) {
			CheckWorkTime(IS00220Opt.get(),"IS00221", "IS00223", "IS00224" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00229Opt.isPresent()) {
			CheckWorkTime(IS00229Opt.get(),"IS00230", "IS00232", "IS00233" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
		
		if (IS00238Opt.isPresent()) {
			CheckWorkTime(IS00238Opt.get(),"IS00239", "IS00241", "IS00242" ,itemsOfWorkingCondition2,  employee, category, bussinessName, result);
		}
	}

	private void checkRequiredWorktime1(List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result) {
		
		Optional<LayoutPersonInfoValueDto> IS00148Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00148")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00157Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00157")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00166Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00166")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00175Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00175")).findFirst();
		
		if (IS00148Opt.isPresent()) {
			CheckWorkTime(IS00148Opt.get(),"IS00149", "IS00151", "IS00152" ,itemsOfWorkingCondition1,  employee, category, bussinessName, result);
		}
		
		if (IS00157Opt.isPresent()) {
			CheckWorkTime(IS00157Opt.get(),"IS00158", "IS00160", "IS00161" ,itemsOfWorkingCondition1,  employee, category, bussinessName, result);
		}
		
		if (IS00166Opt.isPresent()) {
			CheckWorkTime(IS00166Opt.get(),"IS00167", "IS00169", "IS00170" ,itemsOfWorkingCondition1,  employee, category, bussinessName, result);
		}
		
		if (IS00175Opt.isPresent()) {
			CheckWorkTime(IS00175Opt.get(),"IS00176", "IS00178", "IS00179" ,itemsOfWorkingCondition1,  employee, category, bussinessName, result);
		}
	}

	
	
	private void CheckWorkTime(LayoutPersonInfoValueDto itemWorkType, String itemCdWorkTime, String itemCdStartDate1,
			String itemCdEndDate1,List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1, EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName,
			List<ErrorInfoCPS013> result) {
		String workTypeCD = (String) itemWorkType.getValue();
		//就業時間帯の必須チェック
		SetupType checkNeededOfWorkTimeSetting = basicService.checkNeededOfWorkTimeSetting(workTypeCD);
		if (checkNeededOfWorkTimeSetting == SetupType.REQUIRED) {
			Optional<LayoutPersonInfoValueDto> itemWorkTimeOpt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdWorkTime)).findFirst();
			if (itemWorkTimeOpt.isPresent()) {
				Object itemWorkTime = itemWorkTimeOpt.get().getValue();
				if (itemWorkTime == null) {
					ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),
							TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), itemWorkTimeOpt.get().getItemName() ));
					result.add(error_workTime);
				}else {
					// check startTime 1
					Optional<LayoutPersonInfoValueDto> IS00151_StartTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdStartDate1)).findFirst();
					if (IS00151_StartTime1_Opt.isPresent()) {
						Object IS00151_StartTime1 = IS00151_StartTime1_Opt.get().getValue();
						if (IS00151_StartTime1 == null ) {
							ErrorInfoCPS013 error_StartTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00151_StartTime1_Opt.get().getItemName() ));
							result.add(error_StartTime1);
						}
					}
					
					// check endTime 1
					Optional<LayoutPersonInfoValueDto> IS00152_EndTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdEndDate1)).findFirst();
					if (IS00152_EndTime1_Opt.isPresent()) {
						Object IS00152_EndTime1 = IS00152_EndTime1_Opt.get().getValue();
						if (IS00152_EndTime1 == null ) {
							ErrorInfoCPS013 error_EndTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00152_EndTime1_Opt.get().getItemName() ));
							result.add(error_EndTime1);
						}
					}
				}
			}
		} else if(checkNeededOfWorkTimeSetting == SetupType.NOT_REQUIRED){

			Optional<LayoutPersonInfoValueDto> itemWorkTimeOpt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdWorkTime)).findFirst();
			if (itemWorkTimeOpt.isPresent()) {
				// check workTime
				Object itemWorkTime = itemWorkTimeOpt.get().getValue();
				if (itemWorkTime != null) {
					ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),
							TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), itemWorkTimeOpt.get().getItemName() ));
					result.add(error_workTime);
				}else {
					// check startTime 1
					Optional<LayoutPersonInfoValueDto> IS00151_StartTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdStartDate1)).findFirst();
					if (IS00151_StartTime1_Opt.isPresent()) {
						Object IS00151_StartTime1 = IS00151_StartTime1_Opt.get().getValue();
						if (IS00151_StartTime1 != null ) {
							ErrorInfoCPS013 error_StartTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00151_StartTime1_Opt.get().getItemName() ));
							result.add(error_StartTime1);
						}
					}
					
					// check endTime 1
					Optional<LayoutPersonInfoValueDto> IS00152_EndTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdEndDate1)).findFirst();
					if (IS00152_EndTime1_Opt.isPresent()) {
						Object IS00152_EndTime1 = IS00152_EndTime1_Opt.get().getValue();
						if (IS00152_EndTime1 != null ) {
							ErrorInfoCPS013 error_EndTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00152_EndTime1_Opt.get().getItemName() ));
							result.add(error_EndTime1);
						}
					}
				}
			}
		}
	}

	private void checkDateReversal(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result) {
		switch (category.getCategoryCode().v()) {
		case "CS00003":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00020" , "IS00021");
			break;
		case "CS00004":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00026" , "IS00027");
			break;
		case "CS00014":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00066" , "IS00067");
			break;
		case "CS00015":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00071" , "IS00072");
			break;
		case "CS00016":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00077" , "IS00078");
			break;
		case "CS00017":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00082" , "IS00083");
			break;
		case "CS00018":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00087" , "IS00088");
			break;
		case "CS00019":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00102" , "IS00103");
			break;
		case "CS00020":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00119" , "IS00120");
			break;
		case "CS00021":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00255" , "IS00256");
			break;
		case "CS00070":
			checkItemReversal(items,employee, category, bussinessName, result , "IS00781" , "IS00782");
			break;
		}
	}

	private void checkItemReversal(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result, String itemCodeStartDate , String itemCodeEndDate) {
		Optional<LayoutPersonInfoValueDto> startDateOpt =  items.stream().filter(itCode -> itCode.getItemCode().equals(itemCodeStartDate)).findFirst();
		Optional<LayoutPersonInfoValueDto> endDateOpt =  items.stream().filter(itCode -> itCode.getItemCode().equals(itemCodeEndDate)).findFirst();
		if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
			GeneralDate startDate = GeneralDate.fromString(startDateOpt.get().getValue().toString() , "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(endDateOpt.get().getValue().toString() , "yyyy/MM/dd");
			if (startDate.after(endDate)) {
				ErrorInfoCPS013 error_itemReversal = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
						chekPersonInfoType, category.getCategoryName().v(),TextResource.localize("Msg_953", category.getCategoryName().v(), startDateOpt.get().getItemName() , startDate.toString(), category.getCategoryName().v(), endDateOpt.get().getItemName(), endDate.toString()));
				result.add(error_itemReversal);
			}
		}
	}

	private void checkItemSpecial(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result) {
		
		// 半角または全角スペースあるか(có hay không half space or full space?)
		items.forEach(item ->{
			if (listItemToCheckSpace.contains(item.getItemCode())) {
				String value = (String) item.getValue();
				if (value.startsWith(JP_SPACE) || value.endsWith(JP_SPACE) || !value.contains(JP_SPACE)) {
					ErrorInfoCPS013 error_itemReq = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),TextResource.localize("Msg_952", item.getItemName()));
					result.add(error_itemReq);
				}
			}
		});
	}

	private void checkNullOfRequiredItems(LayoutPersonInfoValueDto item, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result) {
		if (item.getValue() == null || item.getValue() == "") {
			ErrorInfoCPS013 error_itemReq = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
					chekPersonInfoType, category.getCategoryName().v(),TextResource.localize("Msg_955", item.getItemName()));
			result.add(error_itemReq);
		}
	}

	/**
	 * 参照項目チェック処理 (Check item tham chiếu)
	 * @param empCheck
	 * @param dataOfEmployee
	 * @param excuteCommand
	 * @param mapCategoryWithListItemDf
	 * @param result
	 * @param employee
	 * @param bussinessName
	 */
	private void checkReferenceItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		Map<String, List<PersonInfoItemDefinition>> itemCodesByCtg = new HashMap<>();
		Map<String, String> itemStartCode = new HashMap<>();
		mapCategoryWithListItemDf.entrySet().forEach(c -> {
			List<PersonInfoItemDefinition> itemDefined = c.getValue().stream().filter(i -> i.isSelection())
					.collect(Collectors.toList());
			Optional<String> itemDefinedStartCode = c.getValue().stream()
					.filter(i -> standardDateItemCodes.contains(i.getItemCode().v())).map(i -> i.getItemCode().v())
					.findFirst();
			if (!CollectionUtil.isEmpty(itemDefined)) {
				itemCodesByCtg.put(c.getKey().getCategoryCode().v(), itemDefined);
			}

			if (itemDefinedStartCode.isPresent()) {
				itemStartCode.put(c.getKey().getCategoryCode().v(), itemDefinedStartCode.get());
			}
		});

		if (itemCodesByCtg.isEmpty())
			return;
		Map<String, String>  masterName = getNameMasterError();
		itemCodesByCtg.entrySet().forEach(c -> {
			List<GridLayoutPersonInfoClsDto> empData = dataOfEmployee.get(c.getKey());
			if (CollectionUtil.isEmpty(empData))
				return;
			empData.stream().forEach(emp -> {
				List<LayoutPersonInfoClsDto> clsDto = emp.getLayoutDtos();
				clsDto.stream().forEach(cls -> {
					List<LayoutPersonInfoValueDto> items = cls.getItems();
					c.getValue().forEach(item -> {
						Optional<LayoutPersonInfoValueDto> itemOpt = items.stream()
								.filter(i -> i.getItemCode().equals(item.getItemCode().v())).findFirst();
						setError(item, c.getKey(), itemStartCode, items, result, employee, bussinessName, itemOpt, masterName);
					});
				});

			});
		});
	}
	
	/**
	 * setError
	 * @param item
	 * @param categoryCode
	 * @param itemStartCode
	 * @param items
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param itemDtoOpt
	 */
	public void setError(PersonInfoItemDefinition item, String categoryCode, Map<String, String> itemStartCode, List<LayoutPersonInfoValueDto> items, List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName, Optional<LayoutPersonInfoValueDto> itemDtoOpt, Map<String, String>  masterName) {
		if (itemDtoOpt.isPresent()) {
			
			if (item.isEnum()) {
				checkErrorEnum(result, employee, bussinessName, itemDtoOpt.get());
			}
			
			if (item.isCodeName()) {
				checkCodeName(result, employee, bussinessName, itemDtoOpt.get());
			}
			
			if (item.isDesignateMaster()) {
				String itemCodeStart = itemStartCode.get(categoryCode);
				if (itemCodeStart == null) {
					checkDesignateMaster(result, employee, bussinessName, itemDtoOpt.get(), null, masterName);

				} else {
					Optional<LayoutPersonInfoValueDto> itemStart = items.stream()
							.filter(i -> i.getItemCode().equals(itemCodeStart)).findFirst();
					if (itemStart.isPresent()) {
						checkDesignateMaster(result, employee, bussinessName, itemDtoOpt.get(),
								itemStart.get().getValue(), masterName);
					}
				}
			}
		}
	}
	
	/**
	 * Enum参照する場合 ( TH tham chiếu Enum)
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param item
	 */
	public void checkErrorEnum(List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName, LayoutPersonInfoValueDto item) {
		 String value = (String) item.getValue();
		 List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		if (comboxValue.size() >0 && !comboxValue.contains(value)) {
			//Enum値の最小値
			String max = comboxValue.size() == 0? " ": comboxValue.get(comboxValue.size() - 1);
			//{0} : 項目名, {1} : データの値, {2} : Enum値の最小値 (giá trị max của list combox)
			ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
					employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
					TextResource.localize("Msg_949", item.getItemName(), value, max));
			result.add(error);
		}
	}
	
	/**
	 * 個人情報選択項目マスタ参照する場合 ( TH tham chiếu Personal Information Selection Item Master)
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param item
	 */
	public void checkCodeName(List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName, LayoutPersonInfoValueDto item) {
		 String value = (String) item.getValue();
		 List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		if (comboxValue.size() >0 && !comboxValue.contains(value)) {
			//{0} : 項目名, {1} : データの値
			ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
					employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
					TextResource.localize("Msg_950", item.getItemName(), value));
			result.add(error);
		}
	}
	
	private Map<String, String> getNameMasterError(){
		Map<String, String> masterName = new HashMap<>();
			// M00001 - CS00015- 部門情報
			
			masterName.put("IS00073", "部門マスタ");
			
			// M00002 - CS00017- 職場情報
			
			masterName.put("IS00084", "職場マスタ");
			
			masterName.put("IS00085", "職場マスタ");
			
			// M00003 - CS00014- 雇用
			
			masterName.put("IS00068", "雇用マスタ");
			
			// M00004 - CS00004 - 分類
			
			masterName.put("IS00028", "分類マスタ１");
			
			// M00005 - CS00016- 職位情報
			
			masterName.put("IS00079", "職位マスタ");
			
			// M00006 - 休職休業枠
			
			masterName.put("IS00089", "休職休業マスタ");
			
			// M00007 - 勤務種別
			
			masterName.put("IS00257", "勤務種別マスタ");
			
			// M00008 - 勤務種別 chưa có
			
			// M00009 - 就業時間帯
			
			masterName.put("IS00131", "就業時間帯マスタ");
			
			masterName.put("IS00140", "就業時間帯マスタ");
			
			masterName.put("IS00158", "就業時間帯マスタ");
			
			masterName.put("IS00167", "就業時間帯マスタ");
			
			masterName.put("IS00176", "就業時間帯マスタ");
			
			masterName.put("IS00149", "就業時間帯マスタ");
			
			masterName.put("IS00194", "就業時間帯マスタ");
			
			masterName.put("IS00203", "就業時間帯マスタ");
			
			masterName.put("IS00212", "就業時間帯マスタ");
			
			masterName.put("IS00221", "就業時間帯マスタ");
			
			masterName.put("IS00230", "就業時間帯マスタ");
			
			masterName.put("IS00239", "就業時間帯マスタ");
			
			masterName.put("IS00185", "就業時間帯マスタ");
			
			//  M00010 - 勤務種類
			
			masterName.put("IS00193", "勤務種類マスタ2");
			
			masterName.put("IS00202", "勤務種類マスタ2");
			
			masterName.put("IS00211", "勤務種類マスタ2");	
			
			masterName.put("IS00220", "勤務種類マスタ2");
			
			masterName.put("IS00229", "勤務種類マスタ2");
			
			masterName.put("IS00238", "勤務種類マスタ2");
			
			masterName.put("IS00184", "勤務種類マスタ2");
			
			// M00011 - 勤務種類
			
			masterName.put("IS00128", "勤務種類マスタ3");
			
			// M00012 - 勤務種類
			masterName.put("IS00139", "勤務種類マスタ4");
			
			masterName.put("IS00157", "勤務種類マスタ4");
			
			masterName.put("IS00166", "勤務種類マスタ4");
			
			masterName.put("IS00175", "勤務種類マスタ4");
			
			masterName.put("IS00148", "勤務種類マスタ4");
			
			//M00013 - CS00020- 勤務種類
			//masterName.put("CS00070", "勤務種類マスタ5");
			
			//M00014 - CS00020- 月間パターン
			
			masterName.put("IS00127", "月間パターンマスタ4");
			
			//M00015 - CS00020 - 加給設定
			
			masterName.put("IS00246", "加給時間帯マスタ");
			
			//M00016 - CS00024 - 年休付与テーブルマスタ
			
			masterName.put("IS00280", "年休付与テーブルマスタ");
			
			//M00017 - CS00025 - 特別休暇勤続年数テーブル
			
			masterName.put("IS00299", "勤続年数テーブル");
			
			masterName.put("IS00306", "勤続年数テーブル");
			
			masterName.put("IS00313", "勤続年数テーブル");
			
			masterName.put("IS00320", "勤続年数テーブル");
			
			masterName.put("IS00327", "勤続年数テーブル");
			
			masterName.put("IS00334", "勤続年数テーブル");
			
			masterName.put("IS00341", "勤続年数テーブル");
			
			masterName.put("IS00348", "勤続年数テーブル");
			
			masterName.put("IS00355", "勤続年数テーブル");
			
			masterName.put("IS00362", "勤続年数テーブル");
			
			masterName.put("IS00563", "勤続年数テーブル");
			
			masterName.put("IS00570", "勤続年数テーブル");
			
			masterName.put("IS00577", "勤続年数テーブル");
			
			masterName.put("IS00584", "勤続年数テーブル");
			
			masterName.put("IS00591", "勤続年数テーブル");
			
			masterName.put("IS00598", "勤続年数テーブル");
			
			masterName.put("IS00605", "勤続年数テーブル");
			
			masterName.put("IS00612", "勤続年数テーブル");
			
			masterName.put("IS00619", "勤続年数テーブル");
			
			masterName.put("IS00626", "勤続年数テーブル");
			
			//M00025 
			//masterName.put("CS00070", "就業時間帯マスタ");
			return masterName;
	}
	
	/**
	 * マスタ未登録チェックする (check master item)
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param item
	 * @param startValue
	 */
	public void checkDesignateMaster(List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName, LayoutPersonInfoValueDto item, Object startValue, Map<String, String>  masterNames) {
		 String value = (String) item.getValue();
		 List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		 String masterName  = masterNames.get(item.getItemCode());
		 if (comboxValue.size() >0 && !comboxValue.contains(value)) {
			//履歴ありマスタの場合 (TH master có history)
			if(listItem_Master_History.contains(item.getItemCode())) {
				ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
						TextResource.localize("Msg_938", item.getItemName(), value, masterName, startValue.toString() ));
				result.add(error);
			}else {
				//履歴なしマスタの場合 ( TH master không có history)
				ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
						TextResource.localize("Msg_937", item.getItemName(), value, masterName));
				result.add(error);
			}
		}
	}


	/**
	 * システム必須チェック (Kiểm tra required system)
	 * @param empCheck
	 * @param dataOfEmployee
	 * @param excuteCommand
	 * @param mapCategoryWithListItemDf
	 * @param result
	 * @param employee
	 * @param bussinessName
	 */
	private void checkSystemRequired(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		
		// baseDate from UI
		GeneralDate baseDate = GeneralDate.fromString(excuteCommand.getDateTime() , "yyyy/MM/dd");
		
		/**
		 * 各システム必須となったカテゴリだけチェック対象とする ＜下記の条件の該当するカテゴリが対象となる、それ以外は除外する＞
		 * [個人情報整合性チェックカテゴリ]の [就業システム必須]:True または [人事システム必須]:True または
		 * [給与システム必須]:True の場合 ----------------
		 * Chi co categories system Required la doi tuong check < categorie tuong duong voi dieu kien
		 * duoi day la doi tuong, cai khac thi loai bo>. 
		 * [Personal Information Integrity Check Category]. 
		 * [Employment system required]: True or
		 * [Human Resources System Required]: True or 
		 * [Salary System Required]: True
		 */

		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		List<String> listCategoryCode = listCategory.stream().map(m -> m.getCategoryCode().v()).collect(Collectors.toList());
		
		List<PerInfoValidateCheckCategory> lstCtgSetting = this.perInfoCheckCtgRepo.getListPerInfoValidByListCtgId(listCategoryCode, AppContexts.user().contractCode());
		
		List<PerInfoValidateCheckCategory> listCategoryFilter = lstCtgSetting.stream().filter(ctg -> {
			if ((ctg.getHumanSysReq().value == NotUseAtr.USE.value) || (ctg.getPaySysReq().value == NotUseAtr.USE.value)
			|| (ctg.getJobSysReq().value == NotUseAtr.USE.value)) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		
		List<String> listCategoryCodeFilter = listCategoryFilter.stream().map(m -> m.getCategoryCd().v()).collect(Collectors.toList());
		
		for (int i = 0; i < listCategory.size(); i++) {
				PersonInfoCategory category = listCategory.get(i);
				
				if (!listCategoryCodeFilter.contains(category.getCategoryCode().v())) {
					continue;
				}
				
				List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(category.getCategoryCode().v());
				
				if(category.isHistoryCategory()){
					List<DatePeriod> lstDatePeriod =  getListDataHistory(category, listDataEmpOfCtg);
					boolean checkExitHisDataOfBaseDate = false;
					for (int j = 0; j < lstDatePeriod.size(); j++) {
						if (baseDate.afterOrEquals(lstDatePeriod.get(i).start()) && baseDate.beforeOrEquals(lstDatePeriod.get(i).end())) {
							checkExitHisDataOfBaseDate = true;
						}
					}
					
					if (checkExitHisDataOfBaseDate) {
						ErrorInfoCPS013 error_6 = new ErrorInfoCPS013(employee.getEmployeeId(), category.getPersonInfoCategoryId(),
								employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, category.getCategoryName().v(),
								TextResource.localize("Msg_951",""));
						result.add(error_6);
					}
					
				}
				
				if(category.isMultiCategory() || category.isSingleCategory()){
					if (listDataEmpOfCtg.isEmpty()) {
						ErrorInfoCPS013 error_7 = new ErrorInfoCPS013(employee.getEmployeeId(), category.getPersonInfoCategoryId(),
								employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, category.getCategoryName().v(),
								TextResource.localize("Msg_1482"));
						result.add(error_7);
					}
				}
		}
	}

	/**
	 * アルゴリズム「履歴整合性チェック」を実行する (Thực thi thuật toán 「Check tính hợp lệ của lịch sử」)
	 * @param empCheck
	 * @param dataOfEmployee
	 * @param excuteCommand
	 * @param mapCategoryWithListItemDf
	 * @param result
	 * @param employee
	 * @param bussinessName
	 */
	public void checkCategoryHistory(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		List<PersonInfoCategory> filterCtg = listCategory.stream().filter(ctg -> ctg.isHistoryCategoryCps013())
				.collect(Collectors.toList());

		for (int i = 0; i < filterCtg.size(); i++) {
			PersonInfoCategory ctg = filterCtg.get(i);
			List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(ctg.getCategoryCode().v());
			
			if (listDataEmpOfCtg.isEmpty()) {
				return;
			}
			
			List<DatePeriod> listDataPeriodOfCtgHis = getListDataHistory(ctg, listDataEmpOfCtg);

			// 「制約：連続かつ永続かつ１件以上存在」該当するカテゴリのデータ１件もない場合
			// (TH không có dữ liệu của category tương ứng「Constraint：tồn tại
			// hơn 1 cái liên tục hoặc lâu dài」)
			if (checkPersistentResidentHistory(ctg, listDataEmpOfCtg)) {
				ErrorInfoCPS013 error_1 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_935"));
				result.add(error_1);
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, result);
				
				continue;
			}

			// 期間重複チェック (kiểm tra trùng lặp thời gian)
			String error = checkOverlapPeriod(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis);
			if (error != null) {
				ErrorInfoCPS013 error_2 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_933", error));
				result.add(error_2);
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, result);
				
				continue;
			}

			// 空白期間チェック (kiem tra thoi gian trong)
			List<DatePeriod> listDateBlank = checkBlankOfPeriod(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis);
			if (!listDateBlank.isEmpty()) {
				String errorBlankOfPeriod = listDateBlank.get(0).start().toString() + " - "
						+ listDateBlank.get(0).end().toString();
				ErrorInfoCPS013 error_3 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_931", errorBlankOfPeriod));
				result.add(error_3);
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, result);
				
				continue;
			}

			// 永続チェック (kiểm tra ràng buộc lâu dài -永続)
			if (!checkPermanent(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis)) {
				DatePeriod endList = listDataPeriodOfCtgHis.get(listDataPeriodOfCtgHis.size() - 1);
				ErrorInfoCPS013 error_4 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_932", endList.start().toString(), endList.end().toString()));
				result.add(error_4);
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, result);
				
				continue;
			}

			// 履歴データ特殊チェック (check historyData đặc biệt)
			checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, result);
			
		}
	}
	
	/**
	 * 履歴データ特殊チェック (Check historyData đặc biệt)
	 * @param ctg
	 * @param listDataEmpOfCtg
	 * @param listDataPeriodOfCtgHis
	 * @param employee
	 * @param bussinessName
	 * @param result
	 */
	private void checkHistoricalDataSpecial(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg,
			List<DatePeriod> listDataPeriodOfCtgHis, EmployeeDataMngInfo employee, String bussinessName, List<ErrorInfoCPS013> result ) {
		if (AppContexts.system().isInstalled(ProductType.ATTENDANCE) == true && ctg.getCategoryCode().toString().equals("CS00014")) {
			List<String> listResult =  new ArrayList<>();
			listDataEmpOfCtg.forEach(data -> {
				List<LayoutPersonInfoValueDto> items = new ArrayList<>();
				List<LayoutPersonInfoClsDto> layoutDtos = data.getLayoutDtos();
				layoutDtos.forEach(item -> {
					items.addAll(item.getItems());
				});
				
				String employmentCode = (String) items.stream().filter(i -> i.getItemCode().toString().equals("IS00068")).findFirst().get().getValue();
				
				Optional<ClosureEmployment> closureEmp = closureEmploymentRepository.findByEmploymentCD(AppContexts.user().companyId(), employmentCode);
				if(!closureEmp.isPresent()){
					listResult.add(employmentCode);
				}
			});
			if (!listResult.isEmpty() ) {
				String errorInfo = listResult.stream().map(String::toString).collect(Collectors.joining(","));
				ErrorInfoCPS013 error_5 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_934", errorInfo ));
				result.add(error_5);

			}
		}
	}

	private boolean checkPermanent(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg,
			List<DatePeriod> listDataPeriodOfCtgHis) {
		if (ctg.getCategoryType() ==  CategoryType.CONTINUOUSHISTORY) {
			if (listPersistentResidentHisAndPersistentHisCtg.contains(ctg.getCategoryCode().v()) && !listDataPeriodOfCtgHis.isEmpty()) {
				GeneralDate endDate =  listDataPeriodOfCtgHis.get(listDataPeriodOfCtgHis.size() - 1).end();
				if (endDate.equals(GeneralDate.max())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 空白期間チェック (kiem tra thoi gian trong)
	 * @param ctg
	 * @param listDataEmpOfCtg
	 * @param listDataPeriodOfCtgHis
	 * @return
	 */
	private List<DatePeriod> checkBlankOfPeriod(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg, List<DatePeriod> listDataPeriodOfCtgHis) {
		if (ctg.getCategoryType() ==  CategoryType.CONTINUOUSHISTORY) {
			if (listPersistentResidentHisAndPersistentHisCtg.contains(ctg.getCategoryCode().v()) && !listDataPeriodOfCtgHis.isEmpty() && listDataPeriodOfCtgHis.size() > 1) {
				List<DatePeriod> result = new ArrayList<>();
				for (int i = 0; i < listDataPeriodOfCtgHis.size() - 1; i++) {
					DatePeriod datePeriod_i = listDataPeriodOfCtgHis.get(i);
					DatePeriod datePeriod_i1 = listDataPeriodOfCtgHis.get(i + 1);
					if(!isHistoryContinue(datePeriod_i, datePeriod_i1)){
						if(result.isEmpty()){
							result.add(datePeriod_i);
							result.add(datePeriod_i1);
						}
					}
				}
			}
		}
		return listDataPeriodOfCtgHis;
	}

	private boolean isHistoryContinue(DatePeriod datePeriod1, DatePeriod datePeriod2) {
		
		GeneralDate endDate1 = datePeriod1.end();
		if (endDate1.addDays(1).equals(datePeriod2.start())) {
			return true;
		}
		return false;
	}

	// 期間重複チェック (kiểm tra trùng lặp thời gian)
	private String checkOverlapPeriod(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg, List<DatePeriod> listDataPeriod ) {
		
		List<GeneralDate> listDateOverLap = isOverlap(listDataPeriod);
		
		if (listDateOverLap.isEmpty()) return null;
		return listDateOverLap.get(0).toString();
	}

	/**
	 * CS00003  - UnduplicatableHistory
	 * CS00004  - PersistentResidentHistory
	 * CS00014  - PersistentResidentHistory
	 * CS00015  - Department
	 * CS00016  - PersistentResidentHistory
	 * CS00017  - PersistentResidentHistory
	 * CS00018  - UnduplicatableHistory 
	 * CS00019  - UnduplicatableHistory
	 * CS00020  - PersistentResidentHistory
	 * CS00021  - PersistentHistory
	 * CS00070  - PersistentResidentHistory
	 * @param ctg
	 * @param listDataEmpOfCtg
	 */
	private List<DatePeriod> getListDataHistory(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg ) {
		
		List<DatePeriod> historys = new ArrayList<>();
		switch (ctg.getCategoryCode().v()) {
		case "CS00003":
			historys = getListDatePeriod("IS00020","IS00021",listDataEmpOfCtg);
			break;
		case "CS00004":
			historys = getListDatePeriod("IS00026","IS00027",listDataEmpOfCtg);
			break;
		case "CS00014":
			historys = getListDatePeriod("IS00066","IS00067",listDataEmpOfCtg);
			break;
		case "CS00015":
			// chua doi ung
			//historys = getListDatePeriod("IS00071","IS00072",listDataEmpOfCtg);
			break;
		case "CS00016":
			historys = getListDatePeriod("IS00077","IS00078",listDataEmpOfCtg);
			break;
		case "CS00017":
			historys = getListDatePeriod("IS00082","IS00083",listDataEmpOfCtg);
			break;
		case "CS00018":
			historys = getListDatePeriod("IS00087","IS00088",listDataEmpOfCtg);
			break;
		case "CS00019":
			historys = getListDatePeriod("IS00102","IS00103",listDataEmpOfCtg);
			break;
		case "CS00020":
			historys = getListDatePeriod("IS00119","IS00120",listDataEmpOfCtg);
			break;
		case "CS00021":
			historys = getListDatePeriod("IS00255","IS00256",listDataEmpOfCtg);
			break;
		case "CS00070":
			historys = getListDatePeriod("IS00781","IS00782",listDataEmpOfCtg);
			break;
		}
		return historys;
	}

	private List<DatePeriod> getListDatePeriod(String itemCodeStartDate, String itemCodeEndDate, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg) {
		List<DatePeriod> result = new ArrayList<>();
		listDataEmpOfCtg.forEach(data ->{
			List<LayoutPersonInfoValueDto> items = new ArrayList<>(); 
			List<LayoutPersonInfoClsDto> layoutDtos = data.getLayoutDtos();
			layoutDtos.forEach(item -> {
				items.addAll(item.getItems());
			});
			
			Optional<LayoutPersonInfoValueDto> startDateOpt = items.stream().filter(i -> i.getItemCode().equals(itemCodeStartDate)).findFirst();
			Optional<LayoutPersonInfoValueDto> endDateOpt = items.stream().filter(i -> i.getItemCode().equals(itemCodeEndDate)).findFirst();
			if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
				GeneralDate startDate = GeneralDate.fromString(startDateOpt.get().getValue().toString() , "yyyy/MM/dd");
				GeneralDate endtDate  = GeneralDate.fromString(endDateOpt.get().getValue().toString() , "yyyy/MM/dd");
				result.add(new DatePeriod(startDate, endtDate));
			}
		});
		return result;
	}

	private boolean checkPersistentResidentHistory(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg) {
		if (ctg.getCategoryType() ==  CategoryType.CONTINUOUSHISTORY) {
			if (listPersistentResidentHistoryCtg.contains(ctg.getCategoryCode().v())) {
				if(CollectionUtil.isEmpty(listDataEmpOfCtg)) {
					return true;
				};
			}
		}
		return false;
	}
	
	/**
	 * 履歴重複の場合 ( TH trùng lặp lịch sử)
	 * @param historys
	 * @return
	 */
	private List<GeneralDate> isOverlap(List<DatePeriod> historys) {
		List<GeneralDate> result = new ArrayList<>();
		historys.forEach(his1 -> {
			historys.forEach(hisOther -> {
				if (his1 != hisOther) {
					if ((his1.start().afterOrEquals(hisOther.start()) && his1.start().beforeOrEquals(hisOther.end())) || 
							(his1.start().beforeOrEquals(hisOther.start()) && his1.start().afterOrEquals(hisOther.end()))) {
						result.add(his1.start());
					}

					if ((his1.end().afterOrEquals(hisOther.start()) && his1.end().beforeOrEquals(hisOther.end())) || 
							(his1.end().beforeOrEquals(hisOther.start()) && his1.end().afterOrEquals(hisOther.end()))) {
						result.add(his1.end());
					}
				}
			});
		});
		
		return result.stream().sorted().collect(Collectors.toList());
	}
}
