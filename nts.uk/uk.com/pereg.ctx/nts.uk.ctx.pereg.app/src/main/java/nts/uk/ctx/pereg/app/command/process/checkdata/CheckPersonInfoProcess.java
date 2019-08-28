package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
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
			CheckDataFromUI excuteCommand,Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, EmployeeDataMngInfo employee, String bussinessName, TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		
		// アルゴリズム「履歴整合性チェック」を実行する (Thực thi thuật toán 「Check tính hợp lệ của lịch sử」)
		checkCategoryHistory(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, dataSetter, employee, bussinessName, listError);
		
		// システム必須チェック (Kiểm tra required system)
		checkSystemRequired(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, dataSetter, employee, bussinessName, listError);
		
		// 参照項目チェック処理 (Check item tham chiếu)
		checkReferenceItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, dataSetter, employee, bussinessName, listError);
		
		//単一項目チェック(Check single item )
		checkSingleItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, dataSetter, employee, bussinessName, listError);
		
	}

	private void checkSingleItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName,
			List<ErrorInfoCPS013> listError) {
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		Optional<PersonInfoCategory> isExitCS00020 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00020")).findFirst();
		Optional<PersonInfoCategory> isExitCS00070 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00070")).findFirst();
		List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1 = new ArrayList<>();
		List<LayoutPersonInfoValueDto> itemsOfWorkingCondition2 = new ArrayList<>();
		List<LayoutPersonInfoValueDto> itemsIS00017 = new ArrayList<>();
		List<LayoutPersonInfoValueDto> itemsIS00020 = new ArrayList<>();
		mapCategoryWithListItemDf.entrySet().stream().forEach(c ->{
			PersonInfoCategory category =  c.getKey();
			List<String> itemCodeSingle = c.getValue().stream().filter(i -> i.isSingleItem())
					.map(i -> i.getItemCode().v()).collect(Collectors.toList());
			List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(category.getCategoryCode().v());
			listDataEmpOfCtg.forEach(data -> {
				List<LayoutPersonInfoValueDto> items = new ArrayList<>();
				itemsOfWorkingCondition1.removeAll(itemsOfWorkingCondition1);
				itemsOfWorkingCondition2.removeAll(itemsOfWorkingCondition2);
				List<LayoutPersonInfoClsDto> layoutDtos = data.getLayoutDtos();
				layoutDtos.forEach(item -> {
					items.addAll(item.getItems());
					if (category.getCategoryCode().v().equals("CS00020")){
						itemsOfWorkingCondition1.addAll(item.getItems());
					}
					if (category.getCategoryCode().v().equals("CS00070")) {
						itemsOfWorkingCondition2.addAll(item.getItems());
					}
					
					if (category.getCategoryCode().v().equals("CS00002")) {
						Optional<LayoutPersonInfoValueDto> itemIS00017Opt = item.getItems().stream()
								.filter(i -> i.getItemCode().equals("IS00017")).findFirst();
						if (itemIS00017Opt.isPresent()) {
							itemsIS00017.add(itemIS00017Opt.get());
						}
					}
					
					if (category.getCategoryCode().v().equals("CS00003")) {
						Optional<LayoutPersonInfoValueDto> IS00020Opt = item.getItems().stream()
								.filter(i -> i.getItemCode().equals("IS00020")).findFirst();
						if (IS00020Opt.isPresent()) {
								itemsIS00020.add(IS00020Opt.get());
						}
					}
					
				});
				
				if(category.getCategoryCode().v().equals("CS00020")) {
					// アルゴリズム「就業時間帯の必須チェック」を実行する (Thực hiện thuật toán [check required của worktime])
					checkRequiredWorktime1(itemsOfWorkingCondition1, employee, isExitCS00020.get(), bussinessName, dataSetter, listError);
				}
				
				if(category.getCategoryCode().v().equals("CS00070")) {
					checkRequiredWorktime2(itemsOfWorkingCondition2, employee, isExitCS00070.get(), bussinessName, dataSetter, listError);
				}
				
				items.forEach(item -> {
					// 必須項目のNULLチェック (Check Null cho các requiredItem)
					if(item.isRequired() && itemCodeSingle.contains(item.getItemCode())){
						checkNullOfRequiredItems(item, employee, category, bussinessName, dataSetter, listError);
					}
				});
				
				// 項目の特殊チェック (Check item đặc biệt)
				if (category.getCategoryCode().v().equals("CS00002")) {
					checkItemSpecial(items, employee, category, bussinessName, dataSetter, listError);
				}
				
				// 日付の逆転チェック (Check đảo ngược date )
				if (lisCategorythistory.contains(category.getCategoryCode().v())) {
					checkDateReversalOfHistories(items, employee, category, bussinessName, dataSetter, listError); 
				}
				
			});

		});
		
		if(!itemsIS00017.isEmpty() && !itemsIS00020.isEmpty()) {
			LayoutPersonInfoValueDto itemIS00020 = getMinValue(itemsIS00020);
			LayoutPersonInfoValueDto itemIS00017 = itemsIS00017.get(0);
			checkDateReversalOfSpecialItem(itemIS00017, itemIS00020, employee, bussinessName, dataSetter, listError);
		}
	}
	
	private LayoutPersonInfoValueDto getMinValue(List<LayoutPersonInfoValueDto> IS00020s) {
		LayoutPersonInfoValueDto itemMin = IS00020s.get(0);
		GeneralDate min = (GeneralDate) itemMin.getValue();
		for(int i = 0; i < IS00020s.size(); i++) {
			LayoutPersonInfoValueDto item = IS00020s.get(i);
			GeneralDate a = (GeneralDate) item.getValue();
			if(a.compareTo(min) < 0) {
				min = a;
				itemMin = IS00020s.get(i);
			}
		}
		return itemMin;
	}

	private void checkRequiredWorktime2(List<LayoutPersonInfoValueDto> itemsOfWorkingCondition2,
			EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName,
			TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		
		Optional<LayoutPersonInfoValueDto> IS00184Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00184")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00193Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00193")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00202Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00202")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00211Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00211")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00220Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00220")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00229Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00229")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00238Opt = itemsOfWorkingCondition2.stream().filter(item -> item.getItemCode().equals("IS00238")).findFirst();
		
		
		if (IS00184Opt.isPresent()) {
			checkWorkTime(IS00184Opt.get(),"IS00185", "IS00187", "IS00188" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00193Opt.isPresent()) {
			checkWorkTime(IS00193Opt.get(),"IS00194", "IS00196", "IS00197" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00202Opt.isPresent()) {
			checkWorkTime(IS00202Opt.get(),"IS00203", "IS00205", "IS00206" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00211Opt.isPresent()) {
			checkWorkTime(IS00211Opt.get(),"IS00212", "IS00214", "IS00215" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00220Opt.isPresent()) {
			checkWorkTime(IS00220Opt.get(),"IS00221", "IS00223", "IS00224" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00229Opt.isPresent()) {
			checkWorkTime(IS00229Opt.get(),"IS00230", "IS00232", "IS00233" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00238Opt.isPresent()) {
			checkWorkTime(IS00238Opt.get(),"IS00239", "IS00241", "IS00242" ,itemsOfWorkingCondition2,  employee, category, bussinessName, dataSetter, listError);
		}
	}

	private void checkRequiredWorktime1(List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1,
			EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {
		
		Optional<LayoutPersonInfoValueDto> IS00148Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00148")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00157Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00157")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00166Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00166")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00175Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00175")).findFirst();
		
		if (IS00148Opt.isPresent()) {
			checkWorkTime(IS00148Opt.get(),"IS00149", "IS00151", "IS00152" ,itemsOfWorkingCondition1,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00157Opt.isPresent()) {
			checkWorkTime(IS00157Opt.get(),"IS00158", "IS00160", "IS00161" ,itemsOfWorkingCondition1,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00166Opt.isPresent()) {
			checkWorkTime(IS00166Opt.get(),"IS00167", "IS00169", "IS00170" ,itemsOfWorkingCondition1,  employee, category, bussinessName, dataSetter, listError);
		}
		
		if (IS00175Opt.isPresent()) {
			checkWorkTime(IS00175Opt.get(),"IS00176", "IS00178", "IS00179" ,itemsOfWorkingCondition1,  employee, category, bussinessName, dataSetter, listError);
		}
	}

	
	
	private void checkWorkTime(LayoutPersonInfoValueDto itemWorkType, String itemCdWorkTime, String itemCdStartDate1,
			String itemCdEndDate1, List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1,
			EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {
		String workTypeCD = (String) itemWorkType.getValue();
		//就業時間帯の必須チェック
		SetupType workTimeSetting = basicService.checkNeededOfWorkTimeSetting(workTypeCD);
		if (workTimeSetting == SetupType.REQUIRED) {
			Optional<LayoutPersonInfoValueDto> itemWorkTimeOpt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdWorkTime)).findFirst();
			if (itemWorkTimeOpt.isPresent()) {
				Object itemWorkTime = itemWorkTimeOpt.get().getValue();
				if (itemWorkTime == null) {
					ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),
							TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), itemWorkTimeOpt.get().getItemName() ));
					listError.add(error_workTime);
					setErrorDataGetter(error_workTime, dataSetter);
				}else {
					// check startTime 1
					Optional<LayoutPersonInfoValueDto> IS00151_StartTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdStartDate1)).findFirst();
					if (IS00151_StartTime1_Opt.isPresent()) {
						Object IS00151_StartTime1 = IS00151_StartTime1_Opt.get().getValue();
						if (IS00151_StartTime1 == null ) {
							ErrorInfoCPS013 error_StartTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00151_StartTime1_Opt.get().getItemName() ));
							listError.add(error_StartTime1);
							setErrorDataGetter(error_StartTime1, dataSetter);
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
							listError.add(error_EndTime1);
							setErrorDataGetter(error_EndTime1, dataSetter);
						}
					}
				}
			}
		} else if(workTimeSetting == SetupType.NOT_REQUIRED){

			Optional<LayoutPersonInfoValueDto> itemWorkTimeOpt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdWorkTime)).findFirst();
			if (itemWorkTimeOpt.isPresent()) {
				// check workTime
				Object itemWorkTime = itemWorkTimeOpt.get().getValue();
				if (itemWorkTime != null) {
					ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),
							TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), itemWorkTimeOpt.get().getItemName() ));
					listError.add(error_workTime);
					setErrorDataGetter(error_workTime, dataSetter);
				}else {
					// check startTime 1
					Optional<LayoutPersonInfoValueDto> IS00151_StartTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals(itemCdStartDate1)).findFirst();
					if (IS00151_StartTime1_Opt.isPresent()) {
						Object IS00151_StartTime1 = IS00151_StartTime1_Opt.get().getValue();
						if (IS00151_StartTime1 != null ) {
							ErrorInfoCPS013 error_StartTime1 = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", itemWorkType.getItemName(), (String)itemWorkType.getValue(), IS00151_StartTime1_Opt.get().getItemName() ));
							listError.add(error_StartTime1);
							setErrorDataGetter(error_StartTime1, dataSetter);
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
							listError.add(error_EndTime1);
							setErrorDataGetter(error_EndTime1, dataSetter);
						}
					}
				}
			}
		}
	}

	private void checkDateReversalOfHistories(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		switch (category.getCategoryCode().v()) {
		case "CS00003":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00020" , "IS00021", listError);
			break;
		case "CS00004":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00026" , "IS00027", listError);
			break;
		case "CS00014":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00066" , "IS00067", listError);
			break;
		case "CS00015":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00071" , "IS00072", listError);
			break;
		case "CS00016":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00077" , "IS00078", listError);
			break;
		case "CS00017":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00082" , "IS00083", listError);
			break;
		case "CS00018":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00087" , "IS00088", listError);
			break;
		case "CS00019":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00102" , "IS00103", listError);
			break;
		case "CS00020":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00119" , "IS00120", listError);
			break;
		case "CS00021":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00255" , "IS00256", listError);
			break;
		case "CS00070":
			checkItemReversal(items,employee, category, bussinessName, dataSetter , "IS00781" , "IS00782", listError);
			break;
		}
	}

	private void checkDateReversalOfSpecialItem(LayoutPersonInfoValueDto IS00017, LayoutPersonInfoValueDto IS00020,
			EmployeeDataMngInfo employee, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {
			GeneralDate startDate = GeneralDate.fromString(IS00017.getValue().toString(), "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(IS00020.getValue().toString(), "yyyy/MM/dd");
			if (startDate.after(endDate)) {
				ErrorInfoCPS013 error_itemReversal = new ErrorInfoCPS013(employee.getEmployeeId(),
						IS00017.getCategoryId(), employee.getEmployeeCode().v(), bussinessName,
						chekPersonInfoType, IS00017.getCategoryName(),
						TextResource.localize("Msg_953", IS00017.getCategoryName(),
								IS00017.getItemName(), startDate.toString(), IS00020.getCategoryName(),
								IS00020.getItemName(), endDate.toString()));
				listError.add(error_itemReversal);
				setErrorDataGetter(error_itemReversal, dataSetter);
			}
	}

	private void checkItemReversal(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter, String itemCodeStartDate,
			String itemCodeEndDate, List<ErrorInfoCPS013> listError) {
		Optional<LayoutPersonInfoValueDto> startDateOpt = items.stream()
				.filter(itCode -> itCode.getItemCode().equals(itemCodeStartDate)).findFirst();
		Optional<LayoutPersonInfoValueDto> endDateOpt = items.stream()
				.filter(itCode -> itCode.getItemCode().equals(itemCodeEndDate)).findFirst();
		if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
			GeneralDate startDate = GeneralDate.fromString(startDateOpt.get().getValue().toString(), "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(endDateOpt.get().getValue().toString(), "yyyy/MM/dd");
			if (startDate.after(endDate)) {
				ErrorInfoCPS013 error_itemReversal = new ErrorInfoCPS013(employee.getEmployeeId(),
						category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
						chekPersonInfoType, category.getCategoryName().v(),
						TextResource.localize("Msg_953", category.getCategoryName().v(),
								startDateOpt.get().getItemName(), startDate.toString(), category.getCategoryName().v(),
								endDateOpt.get().getItemName(), endDate.toString()));
				listError.add(error_itemReversal);
				setErrorDataGetter(error_itemReversal, dataSetter);
			}
		}
	}

	private void checkItemSpecial(List<LayoutPersonInfoValueDto> items, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {
		
		// 半角または全角スペースあるか(có hay không half space or full space?)
		items.forEach(item ->{
			if (listItemToCheckSpace.contains(item.getItemCode())) {
				String value = (String) item.getValue();
				if (value.startsWith(JP_SPACE) || value.endsWith(JP_SPACE) || !value.contains(JP_SPACE)) {
					ErrorInfoCPS013 error_itemReq = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
							chekPersonInfoType, category.getCategoryName().v(),TextResource.localize("Msg_952", item.getItemName()));
					listError.add(error_itemReq);
					setErrorDataGetter(error_itemReq, dataSetter);
				}
			}
		});
	}

	private void checkNullOfRequiredItems(LayoutPersonInfoValueDto item, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {
		if (item.getValue() == null || item.getValue() == "") {
			ErrorInfoCPS013 error_itemReq = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
					chekPersonInfoType, category.getCategoryName().v(),TextResource.localize("Msg_955", item.getItemName()));
			listError.add(error_itemReq);
			setErrorDataGetter(error_itemReq, dataSetter);
		}
	}

	/**
	 * 参照項目チェック処理 (Check item tham chiếu)
	 * @param empCheck
	 * @param dataOfEmployee mapping categoryCode - List<GridLayoutPersonInfoClsDto>
	 * @param excuteCommand
	 * @param mapCategoryWithListItemDf
	 * @param result
	 * @param employee
	 * @param bussinessName
	 */
	private void checkReferenceItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName,
			List<ErrorInfoCPS013> listError) {
		
		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> itemCodesByCtg = new HashMap<>();
		
		Map<String, String> itemStartCode = new HashMap<>();
		
		mapCategoryWithListItemDf.entrySet().forEach(c -> {
			
			List<PersonInfoItemDefinition> lstItemDefined = c.getValue().stream()
					.filter(i -> i.isSelection())
					.collect(Collectors.toList());
			
			Optional<String> itemDefinedStartCode = c.getValue().stream()
					.filter(i -> standardDateItemCodes.contains(i.getItemCode().v())).map(i -> i.getItemCode().v())
					.findFirst();
			
			if (!CollectionUtil.isEmpty(lstItemDefined)) {
				itemCodesByCtg.put(c.getKey(), lstItemDefined);
			}

			if (itemDefinedStartCode.isPresent()) {
				itemStartCode.put(c.getKey().getCategoryCode().v(), itemDefinedStartCode.get());
			}
		});

		if (itemCodesByCtg.isEmpty())
			return;
		
		Map<String, String>  masterName = getNameMasterError();
		
		itemCodesByCtg.entrySet().forEach(c -> {
			
			List<GridLayoutPersonInfoClsDto> empData = dataOfEmployee.get(c.getKey().getCategoryCode().toString());
			
			List<PersonInfoItemDefinition> lstItemDefineFull = mapCategoryWithListItemDf.get(c.getKey());
			
			if (CollectionUtil.isEmpty(empData))
				return;
			
			empData.stream().forEach(emp -> {
				
				List<LayoutPersonInfoClsDto> listClsDto = emp.getLayoutDtos();
				
				
				List<LayoutPersonInfoValueDto> listAllItemValue = new ArrayList<>();
				listClsDto.forEach(cls -> {
					List<LayoutPersonInfoValueDto> itemsValue = cls.getItems();
					listAllItemValue.addAll(itemsValue);
				});
				
				
				listClsDto.stream().forEach(cls -> {
					
					List<LayoutPersonInfoValueDto> itemsValue = cls.getItems();
					List<PersonInfoItemDefinition> listItemDefinedSelection = c.getValue();  // list nay chỉ chua danh sach item selection
					
					listItemDefinedSelection.forEach(itemDf -> {
						
						Optional<LayoutPersonInfoValueDto> itemValueOpt = itemsValue.stream()
								.filter(i -> i.getItemCode().equals(itemDf.getItemCode().toString())).findFirst();
						
						
						if (itemValueOpt.isPresent()) {

							checkError(itemValueOpt.get(), c.getKey().getCategoryCode().toString(), itemStartCode, itemsValue, listAllItemValue, lstItemDefineFull, dataSetter, employee, bussinessName, itemDf, masterName, listError);
							
						}
					});
				});
			});
		});
	}
	
	/**
	 * 
	 * @param itemValue : item selection ddang check
	 * @param categoryCode
	 * @param itemStartCode
	 * @param itemsValue : list Item nawmf torng cungf 1 claasfication
	 * @param lstItemDefineFull
	 * @param dataSetter
	 * @param employee
	 * @param bussinessName
	 * @param itemDf
	 * @param masterName
	 * @param listError
	 */
	public void checkError(LayoutPersonInfoValueDto itemValue, String categoryCode, Map<String, String> itemStartCode,
			List<LayoutPersonInfoValueDto> itemsValue, List<LayoutPersonInfoValueDto> listAlltemsValue, List<PersonInfoItemDefinition> lstItemDefineFull,  TaskDataSetter dataSetter, EmployeeDataMngInfo employee,
			String bussinessName, PersonInfoItemDefinition itemDf, Map<String, String> masterName,
			List<ErrorInfoCPS013> listError) {

		if (itemDf.isEnum()) {
			checkErrorEnum(dataSetter, employee, bussinessName, itemValue, listError);
		}

		if (itemDf.isCodeName()) {
			checkCodeName(dataSetter, employee, bussinessName, itemValue, listError);
		}

		if (itemDf.isDesignateMaster()) {
			String itemCodeStart = itemStartCode.get(categoryCode);
			if (itemCodeStart == null) {
				
				checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, null, null, masterName, listError);

			} else {
				// itemCodeStart != null
				switch (itemValue.getItemCode().toString()) {
				case "IS00023": // startDate code IS00022
					Optional<LayoutPersonInfoValueDto> itemStartDate22 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00020")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox23 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00023")).findFirst();
					if (itemStartDate22.isPresent() && itemCombobox23.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate22.get().getValue(),itemCombobox23.get().getValue(), masterName, listError);
					}
					break;
				case "IS00079":  // startDate code IS00077
					Optional<LayoutPersonInfoValueDto> itemStartDate77 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00077")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox79 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00079")).findFirst();
					if (itemStartDate77.isPresent() && itemCombobox79.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate77.get().getValue(),itemCombobox79.get().getValue(), masterName, listError);
					}
					break;
				case "IS00084":  // startDate code IS00082
					Optional<LayoutPersonInfoValueDto> itemStartDate82_1 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00082")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox84 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00084")).findFirst();
					if (itemStartDate82_1.isPresent() && itemCombobox84.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate82_1.get().getValue(),itemCombobox84.get().getValue(), masterName, listError);
					}
					break;
				case "IS00085": // startDate code IS00082
					Optional<LayoutPersonInfoValueDto> itemStartDate82_2 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00082")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox85 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00085")).findFirst();
					if (itemStartDate82_2.isPresent() && itemCombobox85.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate82_2.get().getValue(),itemCombobox85.get().getValue(), masterName, listError);
					}
					break;
				case "IS00131": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_1 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox131 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00131")).findFirst();
					if (itemStartDate119_1.isPresent() && itemCombobox131.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_1.get().getValue(),itemCombobox131.get().getValue(), masterName, listError);
					}
					break;
				case "IS00140": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_2 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox140 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00140")).findFirst();
					if (itemStartDate119_2.isPresent() && itemCombobox140.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_2.get().getValue(),itemCombobox140.get().getValue(), masterName, listError);
					}
					break;
				case "IS00158": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_3 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox158 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00158")).findFirst();
					if (itemStartDate119_3.isPresent() && itemCombobox158.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_3.get().getValue(),itemCombobox158.get().getValue(), masterName, listError);
					}
					break;
				case "IS00167": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_4 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox167 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00167")).findFirst();
					if (itemStartDate119_4.isPresent() && itemCombobox167.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_4.get().getValue(),itemCombobox167.get().getValue(), masterName, listError);
					}
					break;
				case "IS00176": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_5 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox176 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00176")).findFirst();
					if (itemStartDate119_5.isPresent() && itemCombobox176.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_5.get().getValue(),itemCombobox176.get().getValue(), masterName, listError);
					}
					break;
				case "IS00149": // startDate code IS00119
					Optional<LayoutPersonInfoValueDto> itemStartDate119_6 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00119")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox149 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00149")).findFirst();
					if (itemStartDate119_6.isPresent() && itemCombobox149.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate119_6.get().getValue(),itemCombobox149.get().getValue(), masterName, listError);
					}
					break;
				case "IS00194": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_1 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox194 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00194")).findFirst();
					if (itemStartDate781_1.isPresent() && itemCombobox194.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_1.get().getValue(),itemCombobox194.get().getValue(), masterName, listError);
					}
					break;
				case "IS00203": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_2 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox203 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00203")).findFirst();
					if (itemStartDate781_2.isPresent() && itemCombobox203.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_2.get().getValue(),itemCombobox203.get().getValue(), masterName, listError);
					}
					break;
				case "IS00212": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_3 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox212 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00212")).findFirst();
					if (itemStartDate781_3.isPresent() && itemCombobox212.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_3.get().getValue(),itemCombobox212.get().getValue(), masterName, listError);
					}
					break;
				case "IS00221": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_4 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox221 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00221")).findFirst();
					if (itemStartDate781_4.isPresent() && itemCombobox221.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_4.get().getValue(),itemCombobox221.get().getValue(), masterName, listError);
					}
					break;
				case "IS00230": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_5 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox230 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00230")).findFirst();
					if (itemStartDate781_5.isPresent() && itemCombobox230.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_5.get().getValue(),itemCombobox230.get().getValue(), masterName, listError);
					}
					break;
				case "IS00239": // startDate code IS00781
					Optional<LayoutPersonInfoValueDto> itemStartDate781_6 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox239 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00239")).findFirst();
					if (itemStartDate781_6.isPresent() && itemCombobox239.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_6.get().getValue(),itemCombobox239.get().getValue(), masterName, listError);
					}
					break;
				case "IS00185": // startDate code IS00781 
					Optional<LayoutPersonInfoValueDto> itemStartDate781_7 = listAlltemsValue.stream().filter(i -> i.getItemCode().equals("IS00781")).findFirst();
					Optional<LayoutPersonInfoValueDto> itemCombobox185 = itemsValue.stream().filter(i -> i.getItemCode().equals("IS00185")).findFirst();
					if (itemStartDate781_7.isPresent() && itemCombobox185.isPresent()) {
						checkDesignateMaster(dataSetter, employee, bussinessName, itemValue, itemStartDate781_7.get().getValue(),itemCombobox185.get().getValue(), masterName, listError);
					}
					break;

				
				}
				
			}
		}

	}
	
	/** "IS00020", "IS00077", "IS00082", "IS00119", "IS00781"
	 * Enum参照する場合 ( TH tham chiếu Enum)
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param item
	 */
	public void checkErrorEnum(TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName,
			LayoutPersonInfoValueDto item, List<ErrorInfoCPS013> listError) {
		 String value = (String) item.getValue();
		 
		if (Objects.isNull(value)) {
			return;
		}

		if (value.trim().equals("")) {
			return;
		}
			
		List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		if (comboxValue.size() >0 && !comboxValue.contains(value)) {
			//Enum値の最小値
			String min = " ";
			if(item.isRequired()){
				 min = comboxValue.size() == 0? " " : comboxValue.get(0);
			}else{
				 min = comboxValue.size() >  1? comboxValue.get(1) : " ";
			}
			String max = comboxValue.size() == 0? " ": comboxValue.get(comboxValue.size() - 1);
			//{0} : 項目名, {1} : データの値, {2} : Enum値の最小値 (giá trị max của list combox)
			ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
					employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
					TextResource.localize("Msg_949", item.getItemName(), value == null? "": value, min, max));
			listError.add(error);
			setErrorDataGetter(error, dataSetter);
		}
	}
	
	/**
	 * 個人情報選択項目マスタ参照する場合 ( TH tham chiếu Personal Information Selection Item Master)
	 * @param result
	 * @param employee
	 * @param bussinessName
	 * @param item
	 */
	public void checkCodeName(TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName,
			LayoutPersonInfoValueDto item, List<ErrorInfoCPS013> listError) {
		String value = (String) item.getValue();
		if (Objects.isNull(value)) {
			return;
		}

		if (value.trim().equals("")) {
			return;
		}
		List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		if (comboxValue.size() >0 && !comboxValue.contains(value)) {
			//{0} : 項目名, {1} : データの値
			ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
					employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
					TextResource.localize("Msg_950", item.getItemName(), value == null? "": value));
			listError.add(error);
			setErrorDataGetter(error, dataSetter);
		}
	}
	
	private Map<String, String> getNameMasterError(){
		Map<String, String> masterName = new HashMap<>();
			// M00001 - CS00015- 部門情報
			
			masterName.put("IS00073", "部門");
			
			// M00002 - CS00017- 職場情報
			
			masterName.put("IS00084", "職場");
			
			masterName.put("IS00085", "職場");
			
			// M00003 - CS00014- 雇用
			
			masterName.put("IS00068", "雇用");
			
			// M00004 - CS00004 - 分類
			
			masterName.put("IS00028", "分類");
			
			// M00005 - CS00016- 職位情報
			
			masterName.put("IS00079", "職位");
			
			// M00006 - 休職休業枠
			
			masterName.put("IS00089", "休職休業");
			
			// M00007 - 勤務種別
			
			masterName.put("IS00257", "勤務種別");
			
			// M00008 - 勤務種別 chưa có
			
			// M00009 - 就業時間帯
			
			masterName.put("IS00131", "就業時間帯");
			
			masterName.put("IS00140", "就業時間帯");
			
			masterName.put("IS00158", "就業時間帯");
			
			masterName.put("IS00167", "就業時間帯");
			
			masterName.put("IS00176", "就業時間帯");
			
			masterName.put("IS00149", "就業時間帯");
			
			masterName.put("IS00194", "就業時間帯");
			
			masterName.put("IS00203", "就業時間帯");
			
			masterName.put("IS00212", "就業時間帯");
			
			masterName.put("IS00221", "就業時間帯");
			
			masterName.put("IS00230", "就業時間帯");
			
			masterName.put("IS00239", "就業時間帯");
			
			masterName.put("IS00185", "就業時間帯");
			
			//  M00010 - 勤務種類
			
			masterName.put("IS00193", "勤務種類");
			
			masterName.put("IS00202", "勤務種類");
			
			masterName.put("IS00211", "勤務種類");	
			
			masterName.put("IS00220", "勤務種類");
			
			masterName.put("IS00229", "勤務種類");
			
			masterName.put("IS00238", "勤務種類");
			
			masterName.put("IS00184", "勤務種類");
			
			// M00011 - 勤務種類
			
			masterName.put("IS00128", "勤務種類");
			
			// M00012 - 勤務種類
			masterName.put("IS00139", "勤務種類");
			
			masterName.put("IS00157", "勤務種類");
			
			masterName.put("IS00166", "勤務種類");
			
			masterName.put("IS00175", "勤務種類");
			
			masterName.put("IS00148", "勤務種類");
			
			//M00013 - CS00020- 勤務種類
			//masterName.put("CS00070", "勤務種類マスタ5");
			
			//M00014 - CS00020- 月間パターン
			
			masterName.put("IS00127", "月間パターン");
			
			//M00015 - CS00020 - 加給設定
			
			masterName.put("IS00246", "加給時間帯");
			
			//M00016 - CS00024 - 年休付与テーブル
			
			masterName.put("IS00280", "年休付与テーブル");
			
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
			//masterName.put("CS00070", "就業時間帯");
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
	public void checkDesignateMaster(TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName,
			LayoutPersonInfoValueDto item, Object startValue,Object comboboxValue, Map<String, String> masterNames,
			List<ErrorInfoCPS013> listError) {
		 String value = (String) item.getValue();
		 List<String> comboxValue = item.getLstComboBoxValue().stream().map(c -> c.getOptionValue()).sorted().collect(Collectors.toList());
		 String masterName  = masterNames.get(item.getItemCode());
		 
		if (Objects.isNull(value)) {
			return;
		}

		if (value.trim().equals("")) {
			return;
		}

		if (comboxValue.size() > 0 && !comboxValue.contains(value)) {
			// 履歴ありマスタの場合 (TH master có history)
			if (listItem_Master_History.contains(item.getItemCode())) {
				ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
						TextResource.localize("Msg_936", item.getItemName(), value == null ? "null" : value, masterName, startValue.toString()));
				listError.add(error);
				setErrorDataGetter(error, dataSetter);
			} else {
				// 履歴なしマスタの場合 ( TH master không có history)
				ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(), item.getCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, item.getCategoryName(),
						TextResource.localize("Msg_937", item.getItemName(), value == null ? "null" : value, masterName));
				listError.add(error);
				setErrorDataGetter(error, dataSetter);
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
			TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName, 
			List<ErrorInfoCPS013> listError) {
		
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
		
		List<PerInfoValidateCheckCategory> listCategoryFilter = lstCtgSetting.stream()
				.filter(ctg -> ((ctg.getHumanSysReq().value == NotUseAtr.USE.value)
						|| (ctg.getPaySysReq().value == NotUseAtr.USE.value)
						|| (ctg.getJobSysReq().value == NotUseAtr.USE.value)))
				.collect(Collectors.toList());
		
		List<String> listCategoryCodeFilter = listCategoryFilter.stream().map(m -> m.getCategoryCd().v()).collect(Collectors.toList());
		
		for (int i = 0; i < listCategory.size(); i++) {
				PersonInfoCategory category = listCategory.get(i);
				
				if (!listCategoryCodeFilter.contains(category.getCategoryCode().v())) {
					continue;
				}
				
				List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(category.getCategoryCode().v());
				
				if(category.isHistoryCategory()){
					List<DatePeriod> lstDatePeriod =  getListDataHistory(category, listDataEmpOfCtg);
					
					if (CollectionUtil.isEmpty(lstDatePeriod)) {
						continue;
					}
					boolean checkExitHisDataOfBaseDate = false;
					for (int j = 0; j < lstDatePeriod.size(); j++) {
						if (baseDate.afterOrEquals(lstDatePeriod.get(j).start()) && baseDate.beforeOrEquals(lstDatePeriod.get(j).end())) {
							checkExitHisDataOfBaseDate = true;
						}
					}
					
					if (!checkExitHisDataOfBaseDate) {
						//TODO
						/**
						データ取得条件：基準日＝パラメータ.基準日
						注意：
						１：期間が「年期間」の場合は、パラメータ.基準日の年を利用
						　例：基準日:2018/04/01   ==>2018
						            カテゴリ[CS00079 社員住民税納付方法] チェックする際には[2018]を基準年として利用する
						２：期間が「年月期間」の場合は、パラメータ.基準日の[年月]を利用
						　例：基準日:2018/04/01   ==>201804
						          カテゴリ[CS00077 社員所得税情報]チェックする際には[201804]を基準年月として利用
						*/
						ErrorInfoCPS013 error_6 = new ErrorInfoCPS013(employee.getEmployeeId(), category.getPersonInfoCategoryId(),
								employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, category.getCategoryName().v(),
								TextResource.localize("Msg_951", baseDate.toString()));
						listError.add(error_6);
						setErrorDataGetter(error_6, dataSetter);
					}
					
				}
				
				if(category.isMultiCategory() || category.isSingleCategory()){
					if (listDataEmpOfCtg.isEmpty()) {
						ErrorInfoCPS013 error_7 = new ErrorInfoCPS013(employee.getEmployeeId(), category.getPersonInfoCategoryId(),
								employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, category.getCategoryName().v(),
								TextResource.localize("Msg_1482"));
						listError.add(error_7);
						setErrorDataGetter(error_7, dataSetter);
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
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, TaskDataSetter dataSetter, EmployeeDataMngInfo employee, String bussinessName, List<ErrorInfoCPS013> listError) {
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		List<PersonInfoCategory> filterCtg = listCategory.stream().filter(ctg -> ctg.isHistoryCategoryCps013())
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(filterCtg)) {
			return;
		}
		
		for (int i = 0; i < filterCtg.size(); i++) {
			PersonInfoCategory ctg = filterCtg.get(i);
			List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(ctg.getCategoryCode().v());
			
			if (CollectionUtil.isEmpty(listDataEmpOfCtg)) {
				continue;
			}
			
			List<DatePeriod> listDataPeriodOfCtgHis = getListDataHistory(ctg, listDataEmpOfCtg);

			// 「制約：連続かつ永続かつ１件以上存在」該当するカテゴリのデータ１件もない場合
			// (TH không có dữ liệu của category tương ứng「Constraint：tồn tại
			// hơn 1 cái liên tục hoặc lâu dài」)
			if (checkPersistentResidentHistory(ctg, listDataEmpOfCtg)) {
				ErrorInfoCPS013 error_1 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_935"));
				listError.add(error_1);
				setErrorDataGetter(error_1, dataSetter);
				
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, dataSetter, listError);
				
				continue;
			}

			// 期間重複チェック (kiểm tra trùng lặp thời gian)
			DatePeriod error = checkOverlapPeriod(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis);
			if (error != null) {
				ErrorInfoCPS013 error_2 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_933", error.start().toString(), error.end().toString()));
				listError.add(error_2);
				setErrorDataGetter(error_2, dataSetter);
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, dataSetter, listError);
				
				continue;
			}

			// 空白期間チェック (kiem tra thoi gian trong)
			List<DatePeriod> listDateBlank = checkBlankOfPeriod(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis);
			if (!listDateBlank.isEmpty()) {
				
				listDateBlank.forEach(date -> {
					ErrorInfoCPS013 error_3 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
							employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
							TextResource.localize("Msg_931",date.start().toString() , date.end().toString() ));
					listError.add(error_3);
					setErrorDataGetter(error_3, dataSetter);
				}); 
				
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, dataSetter, listError);
				
				continue;
			}

			// 永続チェック (kiểm tra ràng buộc lâu dài -永続)
			if (!checkPermanent(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis)) {
				DatePeriod endList = listDataPeriodOfCtgHis.get(listDataPeriodOfCtgHis.size() - 1);
				ErrorInfoCPS013 error_4 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
						employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
						TextResource.localize("Msg_932", endList.start().toString(), endList.end().toString()));
				listError.add(error_4);
				setErrorDataGetter(error_4, dataSetter);
				//TODO
				// 履歴データ特殊チェック (check historyData đặc biệt)
				checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, dataSetter, listError);
				
				continue;
			}
			
			// 履歴データ特殊チェック (check historyData đặc biệt)
			checkHistoricalDataSpecial(ctg, listDataEmpOfCtg, listDataPeriodOfCtgHis, employee, bussinessName, dataSetter, listError);
			
		}
	}
	
	private void setErrorDataGetter(ErrorInfoCPS013 error, TaskDataSetter dataSetter) {
		String ramdom = IdentifierUtil.randomUniqueId();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dataSetter.setData("employeeId" + ramdom, mapper.writeValueAsString(error));
		} catch (JsonProcessingException e) {
			System.out.println("cps013 mapper object to json fail");
			e.printStackTrace();
			
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
			List<DatePeriod> listDataPeriodOfCtgHis, EmployeeDataMngInfo employee, String bussinessName, TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		
		if (AppContexts.user().roles().forAttendance() != null  && ctg.getCategoryCode().toString().equals("CS00014")) {
			listDataEmpOfCtg.forEach(data -> {
				List<LayoutPersonInfoValueDto> items = new ArrayList<>();
				List<LayoutPersonInfoClsDto> layoutDtos = data.getLayoutDtos();
				layoutDtos.forEach(item -> {
					items.addAll(item.getItems());
				});
				
				String employmentCode = (String) items.stream().filter(i -> i.getItemCode().toString().equals("IS00068")).findFirst().get().getValue();
				
				Optional<ClosureEmployment> closureEmp = closureEmploymentRepository.findByEmploymentCD(AppContexts.user().companyId(), employmentCode);
				if(!closureEmp.isPresent()){
					ErrorInfoCPS013 error_5 = new ErrorInfoCPS013(employee.getEmployeeId(), ctg.getPersonInfoCategoryId(),
							employee.getEmployeeCode().v(), bussinessName, chekPersonInfoType, ctg.getCategoryName().v(),
							TextResource.localize("Msg_934", employmentCode ));
					listError.add(error_5);
					setErrorDataGetter(error_5, dataSetter);
				}
			});
		}
	}

	private boolean checkPermanent(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg,
			List<DatePeriod> listDataPeriodOfCtgHis) {
		if (ctg.getCategoryType() ==  CategoryType.CONTINUOUSHISTORY) {
			if (listPersistentResidentHisAndPersistentHisCtg.contains(ctg.getCategoryCode().v()) && !listDataPeriodOfCtgHis.isEmpty()) {
				GeneralDate endDate =  listDataPeriodOfCtgHis.get(listDataPeriodOfCtgHis.size() - 1).end();
				if (endDate.equals(GeneralDate.max())) {
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 空白期間チェック (kiem tra thoi gian trong)
	 * @param ctg
	 * @param listDataEmpOfCtg
	 * @param listDataPeriodOfCtgHis
	 * @return
	 */
	private List<DatePeriod> checkBlankOfPeriod(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg, List<DatePeriod> listDataPeriodOfCtgHis) {
		
		if(ctg.getCategoryCode().v().equals("CS00003")) return new ArrayList<>();
		
		if (listDataPeriodOfCtgHis.isEmpty() || listDataPeriodOfCtgHis.size() <= 1) {
			return new ArrayList<>();
		}
		if (ctg.getCategoryType() ==  CategoryType.CONTINUOUSHISTORY) {
			if (listPersistentResidentHisAndPersistentHisCtg.contains(ctg.getCategoryCode().v()) && !listDataPeriodOfCtgHis.isEmpty() && listDataPeriodOfCtgHis.size() > 1) {
				List<DatePeriod> result = new ArrayList<>();
				for (int i = 0; i < listDataPeriodOfCtgHis.size() - 1; i++) {
					DatePeriod datePeriod_i = listDataPeriodOfCtgHis.get(i);
					DatePeriod datePeriod_i1 = listDataPeriodOfCtgHis.get(i + 1);
					if(!isHistoryContinue(datePeriod_i, datePeriod_i1)){
						if(result.isEmpty()){
							result.add(new DatePeriod(datePeriod_i.end().addDays(1), datePeriod_i1.start().addDays(-1)));
						}
					}
				}
				return result;
			}
		}
		
		return new ArrayList<>();
	}

	private boolean isHistoryContinue(DatePeriod datePeriod1, DatePeriod datePeriod2) {
		
		if (datePeriod1.end().addDays(1).equals(datePeriod2.start())) {
			return true;
		}
		return false;
	}

	// 期間重複チェック (kiểm tra trùng lặp thời gian)
	private DatePeriod checkOverlapPeriod(PersonInfoCategory ctg, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg, List<DatePeriod> listDataPeriod ) {
		
		if (listDataPeriod.isEmpty() || listDataPeriod.size() <= 1) {
			return null;
		}
		DatePeriod dateOverLap = isOverlap(listDataPeriod);
		
		if (dateOverLap == null) return null;
		return dateOverLap;
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
		return historys.stream().sorted((a, b) -> a.start().compareTo(b.start())).collect(Collectors.toList());
	}

	private List<DatePeriod> getListDatePeriod(String itemCodeStartDate, String itemCodeEndDate, List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg) {
		List<DatePeriod> result = new ArrayList<>();
		if (CollectionUtil.isEmpty(listDataEmpOfCtg)) {
			return new ArrayList<>();
		}
		
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
				GeneralDate endtDate = null;
				if (endDateOpt.get().getValue() != null) {
					endtDate  = GeneralDate.fromString(endDateOpt.get().getValue().toString() , "yyyy/MM/dd");
				}
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
	private DatePeriod isOverlap(List<DatePeriod> historys) {
		List<DatePeriod> sortedList = historys.stream()
				.sorted(Comparator.comparing(DatePeriod::start))
				.collect(Collectors.toList());
		
		List<DatePeriod> result = new ArrayList<>();
		sortedList.forEach(his1 -> {
			sortedList.forEach(hisOther -> {
				if (his1 != hisOther) {
					if ((his1.start().afterOrEquals(hisOther.start()) && his1.start().beforeOrEquals(hisOther.end())) || 
							(his1.start().beforeOrEquals(hisOther.start()) && his1.start().afterOrEquals(hisOther.end()))) {
						result.add(new DatePeriod(his1.start(), hisOther.end()));
					}

					if ((his1.end().afterOrEquals(hisOther.start()) && his1.end().beforeOrEquals(hisOther.end())) || 
							(his1.end().beforeOrEquals(hisOther.start()) && his1.end().afterOrEquals(hisOther.end()))) {
						result.add(new DatePeriod(hisOther.start(), his1.end()));
					}
				}
			});
		});
		
		return result.isEmpty() ? null : result.stream()
				.sorted(Comparator.comparing(DatePeriod::start))
				.collect(Collectors.toList()).get(0);
	}
}
