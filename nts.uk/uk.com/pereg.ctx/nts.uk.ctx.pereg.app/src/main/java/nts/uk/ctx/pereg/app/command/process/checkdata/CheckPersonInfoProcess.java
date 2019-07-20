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
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridLayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
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
	
	final List<String> listPersistentResidentHistoryCtg = Arrays.asList("CS00004","CS00014","CS00016","CS00017","CS00020","CS00070");
	
	final List<String> listPersistentResidentHisAndPersistentHisCtg = Arrays.asList("CS00004","CS00014","CS00016","CS00017","CS00020","CS00021","CS00070");
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
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
		
	}

	private void checkReferenceItem(PeregEmpInfoQuery empCheck,
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			List<ErrorInfoCPS013> result, EmployeeDataMngInfo employee, String bussinessName) {
		
	}

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

	// 空白期間チェック (kiem tra thoi gian trong)
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
			GeneralDate startDate = items.stream().filter(i -> i.getItemCode().equals(itemCodeStartDate)).findFirst().isPresent() ? (GeneralDate)(items.stream().filter(i -> i.getItemCode().equals(itemCodeStartDate)).findFirst().get().getValue()) : null;
			GeneralDate endtDate = items.stream().filter(i -> i.getItemCode().equals(itemCodeEndDate)).findFirst().isPresent() ? (GeneralDate)(items.stream().filter(i -> i.getItemCode().equals(itemCodeEndDate)).findFirst().get().getValue()) : null;
			if (startDate != null && endtDate!= null) {
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
