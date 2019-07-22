package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.shr.com.context.AppContexts;
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
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private BasicScheduleService basicService;
	
	private static final String JP_SPACE = "　";
	
	// 個人基本情報チェック (Check thông tin cá nhân cơ bản)
	public List<ErrorInfoCPS013> checkPersonInfo2(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee,
			CheckDataFromUI excuteCommand,Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, EmployeeDataMngInfo employee, String bussinessName) {
		
		List<ErrorInfoCPS013> result = new ArrayList<>();
		
		// アルゴリズム「履歴整合性チェック」を実行する (Thực thi thuật toán 「Check tính hợp lệ của lịch sử」)
		checkCategoryHistory(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, result, employee, bussinessName);
		
		// システム必須チェック (Kiểm tra required system)
		checkSystemRequired(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, result, employee, bussinessName);
		
		// 参照項目チェック処理 (Check item tham chiếu)
		checkReferenceItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, result, employee, bussinessName);
		
		//単一項目チェック(Check single item )
		checkSingleItem(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, result, employee, bussinessName);
		
		return result;
	}

	private void checkSingleItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		boolean isExitCS00020 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00020")).findFirst().isPresent();
		boolean isExitCS00070 = listCategory.stream().filter( ctg -> ctg.getCategoryCode().v().equals("CS00070")).findFirst().isPresent();
		
		for (int i = 0; i < listCategory.size(); i++) {
			PersonInfoCategory category =  listCategory.get(i);
			List<GridLayoutPersonInfoClsDto> listDataEmpOfCtg = dataOfEmployee.get(category.getCategoryCode().v());
			List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1 = new ArrayList<>();
			List<LayoutPersonInfoValueDto> itemsOfWorkingCondition2 = new ArrayList<>();
		
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
			if (isExitCS00020) {
				checkRequiredWorktime1(itemsOfWorkingCondition1, employee, category, bussinessName, result);
			}
		}
	}

	private void checkRequiredWorktime1(List<LayoutPersonInfoValueDto> itemsOfWorkingCondition1, EmployeeDataMngInfo employee,
			PersonInfoCategory category, String bussinessName, List<ErrorInfoCPS013> result) {
		
		Optional<LayoutPersonInfoValueDto> IS00148Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00148")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00157Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00157")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00166Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00166")).findFirst();
		Optional<LayoutPersonInfoValueDto> IS00175Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00175")).findFirst();
		
		if (IS00148Opt.isPresent()) {
			String workTypeCD = (String) IS00148Opt.get().getValue();
			//就業時間帯の必須チェック
			SetupType checkNeededOfWorkTimeSetting = basicService.checkNeededOfWorkTimeSetting(workTypeCD);
			if (checkNeededOfWorkTimeSetting == SetupType.REQUIRED) {
				Optional<LayoutPersonInfoValueDto> IS00149Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00149")).findFirst();
				if (IS00149Opt.isPresent()) {
					Object workTimeIS00149 = IS00149Opt.get().getValue();
					if (workTimeIS00149 == null) {
						ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
								chekPersonInfoType, category.getCategoryName().v(),
								TextResource.localize("Msg_956", IS00148Opt.get().getItemName(), (String)IS00148Opt.get().getValue(), IS00149Opt.get().getItemName() ));
						result.add(error_workTime);
					}else {
						Optional<LayoutPersonInfoValueDto> IS00151_StartTime1_Opt = itemsOfWorkingCondition1.stream().filter(item -> item.getItemCode().equals("IS00151")).findFirst();
						if (IS00151_StartTime1_Opt.isPresent()) {
							ErrorInfoCPS013 error_workTime = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
									chekPersonInfoType, category.getCategoryName().v(),
									TextResource.localize("Msg_956", IS00148Opt.get().getItemName(), (String)IS00148Opt.get().getValue(), IS00149Opt.get().getItemName() ));
							result.add(error_workTime);
						}
						
						
					}
				}
			}else if(checkNeededOfWorkTimeSetting == SetupType.NOT_REQUIRED){
				
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

		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet());
		
		List<PersonInfoCategory> listCategoryFilterBySystemReq = listCategory.stream().filter(ctg -> {
			return ctg.isEmployment() || ctg.isPersonnel() || ctg.isSalary();
		}).collect(Collectors.toList());
		
		for (int i = 0; i < listCategoryFilterBySystemReq.size(); i++) {
				PersonInfoCategory category = listCategoryFilterBySystemReq.get(i);
				
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
