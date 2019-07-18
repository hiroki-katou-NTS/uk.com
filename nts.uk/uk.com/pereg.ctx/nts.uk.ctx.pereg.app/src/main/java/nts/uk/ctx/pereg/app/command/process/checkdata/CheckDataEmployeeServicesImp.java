package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.DataTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ObjectDate;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.com.system.config.ProductType;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class CheckDataEmployeeServicesImp implements CheckDataEmployeeServices {

	@Inject
	private RegulationInfoEmployeeRepository regulationInfoEmployeeRepo;
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepo;
	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;
	@Inject
	private PerInfoItemDefRepositoty perInfotemDfRepo;
	@Inject
	private PeregProcessor peregProcessor;
	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;
	@Inject
	private EmployeeFinderCtg employeeFinderCtg;
	@Inject
	private LayoutingProcessor layoutingProcessor;
	@Inject
	I18NResourcesForUK ukResouce;
	@Inject
	private EmpCtgFinder empCtgFinder;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private  BasicScheduleService basicScheduleService;

	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public <C> void manager(CheckDataFromUI excuteCommand, AsyncCommandHandlerContext<C> async) {

		// 実行状態 初期設定
		val dataSetter = async.getDataSetter();
		
		// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
		// (thực thi thuật toán 「Search employee theo điều kiện thông tin cá nhân, và thay đổi thứ tự」
		List<EmployeeResultDto> listEmp = this.findEmployeesInfo(excuteCommand);
		
		// アルゴリズム「個人情報カテゴリ取得」を実行する (Thực hiện thuật toán 「Lấy PersonInfoCategory」)
		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf = this.getAllCategory(AppContexts.user().companyId());
		
		
		
		
		// システム日時を取得する (lấy system date)
		dataSetter.setData("startTime", GeneralDateTime.now().toString());
		
		// todo: get data
		//Map<Employee, Data> mapEmployeeWithData 
		
		dataSetter.setData("numberEmpChecked", 0);
		dataSetter.setData("countEmp", listEmp.size());
		dataSetter.setData("statusCheck", ExecutionStatusCps013.PROCESSING.name);
		List<ErrorInfoCPS013> listError = new ArrayList<>();
		
		for (int i = 0; i < listEmp.size(); i++) {
			System.out.println("==== t");
			dataSetter.updateData("numberEmpChecked", i + 1);
			if (i % 10 == 0) {
				ErrorInfoCPS013 error = new ErrorInfoCPS013(listEmp.get(i).sid, "categoryId",
						listEmp.get(i).employeeCode, listEmp.get(i).bussinessName, "check personInfo", "categoryName",
						"error" + i);
				listError.add(error);
			}
		}
		for (int i = 0; i < listError.size(); i++) {
			dataSetter.setData("employeeId" + i, listError.get(i).employeeId);
			dataSetter.setData("categoryId" + i, listError.get(i).categoryId);
			dataSetter.setData("employeeCode" + i, listError.get(i).employeeCode);
			dataSetter.setData("bussinessName" + i, listError.get(i).bussinessName);
			dataSetter.setData("clsCategoryCheck" + i, listError.get(i).clsCategoryCheck);
			dataSetter.setData("categoryName" + i, listError.get(i).categoryName);
			dataSetter.setData("error" + i, listError.get(i).error);
		}
		
		if (listError.isEmpty()) {
			dataSetter.setData("statusCheck", ExecutionStatusCps013.DONE.name);
		}else{
			dataSetter.setData("statusCheck", ExecutionStatusCps013.DONE_WITH_ERROR.name);
		}
		
	}

	// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
	// (thực thi thuật toán 「Search employee theo điều kiện thông tin cá nhân, và thay đổi thứ tự」)
	private List<EmployeeResultDto> findEmployeesInfo(CheckDataFromUI query) {
		EmpQueryDto queryDto = new EmpQueryDto();
		GeneralDateTime baseDate = GeneralDateTime.fromString(query.getDateTime() + TIME_DAY_START, DATE_TIME_FORMAT);
		return this.regulationInfoEmployeeRepo.find(AppContexts.user().companyId(), queryDto.toQueryModel(baseDate))
				.stream().map(model -> this.toEmployeeDto(model)).collect(Collectors.toList());
	}

	private EmployeeResultDto toEmployeeDto(RegulationInfoEmployee model) {
		return new EmployeeResultDto(model.getEmployeeID(), model.getEmployeeCode(), model.getName().orElse(""));
	}

	// アルゴリズム「個人情報カテゴリ取得」を実行する (Thực hiện thuật toán 「Lấy PersonInfoCategory」)
	public Map<PersonInfoCategory, List<PersonInfoItemDefinition>> getAllCategory(String cid) {
		// アルゴリズム「システム利用区分から利用可能な個人情報カテゴリを全て取得する」を実行する
		// (Thực hiện thuật toán 「Từ SystemUseAtr, lấy toàn bộ
		// PersonInfoCategory có thể sử dụng」)
		int forAttendance = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		int forPayroll = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		int forPersonnel = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				forAttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				forPayroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				forPersonnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}

		String contracCd = AppContexts.user().contractCode();

		// trong câu query đã lọc ra những category không có item nào rồi.
		List<PersonInfoCategory> lstCtg = perInfoCtgRepo.getAllCategoryForCPS013(cid, forAttendance, forPayroll,
				forPersonnel);

		// ドメインモデル「個人情報整合性チェックカテゴリ」を全て取得する
		// (Get toàn bộ domain model 「PerInfoValidChkCtg」)
		// チェック対象項目件数をチェックする
		// (Check số data item đối tượng check)
		if (lstCtg.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("Msg_930"));
		}

		List<String> listCategoryCode = lstCtg.stream().map(i -> i.getCategoryCode().toString())
				.collect(Collectors.toList());

		List<PerInfoValidateCheckCategory> lstCtgCheck = this.perInfoCheckCtgRepo
				.getListPerInfoValidByListCtgId(listCategoryCode, contracCd);

		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf = new HashMap<>();

		lstCtgCheck.forEach(ctg -> {
			PersonInfoCategory category = lstCtg.stream()
					.filter(i -> i.getCategoryCode().toString().equals(ctg.getCategoryCd().toString())).findFirst()
					.get();
			List<PersonInfoItemDefinition> listItemDf = this.perInfotemDfRepo.getAllPerInfoItemDefByCategoryIdCPS013(
					category.getPersonInfoCategoryId(), AppContexts.user().contractCode());
			if (!listItemDf.isEmpty()) {
				mapCategoryWithListItemDf.put(category, listItemDf);
			}
		});

		// チェック対象となるカテゴリを絞り込み (filter category là đối tượng check)
		filterCategory(lstCtgCheck, mapCategoryWithListItemDf);

		return mapCategoryWithListItemDf;
	}

	public void filterCategory(List<PerInfoValidateCheckCategory> listCtgSetting, Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf) {
		mapCategoryWithListItemDf.entrySet().forEach(ctg -> {
			PerInfoValidateCheckCategory ctgSetting = listCtgSetting.stream()
					.filter(i -> i.getCategoryCd().toString().equals(ctg.getKey().getCategoryCode().toString())).findFirst()
					.get();
			if (!checkCategory(ctgSetting)) {
				mapCategoryWithListItemDf.remove(ctg);
			}
		});
	}

	private boolean checkCategory(PerInfoValidateCheckCategory ctgSetting) {
		if ((ctgSetting.getHumanSysReq() == NotUseAtr.USE) || (ctgSetting.getJobSysReq() == NotUseAtr.USE)
				|| (ctgSetting.getPaySysReq() == NotUseAtr.USE) || (ctgSetting.getPayMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getMonthCalcMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getMonthActualMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getBonusMngReq() == NotUseAtr.USE) || (ctgSetting.getScheduleMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getDailyActualMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getYearMngReq() == NotUseAtr.USE))
			return true;
		return false;
	}

		/**
		 * lay toan bo data của category 
		 * @param category
		 * @param contractCode
		 * @return
		 */
		private List<PerInfoItemDefForLayoutDto> getPerItemDefForLayout(PersonInfoCategory category, String contractCode) {

			// get per info item def with order
			List<PersonInfoItemDefinition> fullItemDefinitionList = this.perInfotemDfRepo
					.getAllPerInfoItemDefByCategoryIdCPS013(category.getPersonInfoCategoryId(),
							AppContexts.user().contractCode());

			List<PersonInfoItemDefinition> parentItemDefinitionList = fullItemDefinitionList.stream()
					.filter(item -> item.haveNotParentCode()).collect(Collectors.toList());

			List<PerInfoItemDefForLayoutDto> lstReturn = new ArrayList<>();

			for (int i = 0; i < parentItemDefinitionList.size(); i++) {
				PersonInfoItemDefinition itemDefinition = parentItemDefinitionList.get(i);

				PerInfoItemDefForLayoutDto itemDto = this.createItemLayoutDto(category, itemDefinition, i);

				// get and convert childrenItems
				List<PerInfoItemDefForLayoutDto> childrenItems = this.getChildrenItems(fullItemDefinitionList, category,
						itemDefinition, i);

				itemDto.setLstChildItemDef(childrenItems);

				lstReturn.add(itemDto);
			}

			return lstReturn;
		}

		public PerInfoItemDefForLayoutDto createItemLayoutDto(PersonInfoCategory category,
				PersonInfoItemDefinition itemDefinition, int dispOrder) {
			PerInfoItemDefForLayoutDto itemForLayout = new PerInfoItemDefForLayoutDto(itemDefinition);

			itemForLayout.setDispOrder(dispOrder);
			List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
			itemForLayout.setSelectionItemRefTypes(selectionItemRefTypes);

			itemForLayout.setPerInfoCtgCd(category.getCategoryCode().v());
			itemForLayout.setCtgType(category.getCategoryType().value);
			return itemForLayout;
		}

		public List<PerInfoItemDefForLayoutDto> getChildrenItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
				PersonInfoCategory category, PersonInfoItemDefinition parentItem, int dispOrder) {

			List<PerInfoItemDefForLayoutDto> childLayoutitems = new ArrayList<>();

			// get children by itemId list
			ItemType itemType = parentItem.getItemTypeState().getItemType();
			if (itemType == ItemType.SET_ITEM || itemType == ItemType.TABLE_ITEM) {

				List<PersonInfoItemDefinition> childItemDefinitionList = getChildItems(fullItemDefinitionList,
						parentItem.getItemCode().v());

				for (int i = 0; i < childItemDefinitionList.size(); i++) {

					PersonInfoItemDefinition childItemDefinition = childItemDefinitionList.get(i);
					PerInfoItemDefForLayoutDto itemForLayout = createItemLayoutDto(category, childItemDefinition,
							dispOrder);
					childLayoutitems.add(itemForLayout);

					List<PerInfoItemDefForLayoutDto> grandChildItemForLayouts = getChildrenItems(fullItemDefinitionList,
							category, childItemDefinition, i);

					childLayoutitems.addAll(grandChildItemForLayouts);
				}
			}
			return childLayoutitems;
		}

		private List<PersonInfoItemDefinition> getChildItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
				String itemCode) {
			return fullItemDefinitionList.stream()
					.filter(itemDef -> itemDef.getItemParentCode() != null && itemDef.getItemParentCode().equals(itemCode))
					.collect(Collectors.toList());
		}

		public EmpMaintLayoutDto getCategoryDetail(PeregQuery query, PersonInfoCategory category) {

			// map PersonInfoItemDefinition →→ PerInfoItemDefForLayoutDto
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDefForLayout = getPerItemDefForLayout(category,
					AppContexts.user().contractCode());
			if (lstPerInfoItemDefForLayout.isEmpty()) {
				return new EmpMaintLayoutDto();
			}

			List<LayoutPersonInfoClsDto> classItemList = this.peregProcessor.getDataClassItemList(query, category,
					lstPerInfoItemDefForLayout);

			return new EmpMaintLayoutDto(classItemList);
		}

		public List<DataEmployeeToCheck> checkDataEmployee(CheckDataFromUI query) {
			// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
			// (thực thi thuật toán 「Search employee theo điều kiện thông tin cá
			// nhân, và thay đổi thứ tự」)
			List<EmployeeResultDto> listEmployee = this.findEmployeesInfo(query);

			Map<String, String> mapSidPid = this.mapEmployeeToPerson(listEmployee);

			//List<PersonInfoCategory> listCategory = this.getAllCategory(AppContexts.user().companyId());
			
			List<PersonInfoCategory> listCategory = new ArrayList<>();

			List<String> listCategoryCode = listCategory.stream().map(i -> i.getCategoryCode().toString())
					.collect(Collectors.toList());

			List<PerInfoValidateCheckCategory> lstCtgSetting = this.perInfoCheckCtgRepo
					.getListPerInfoValidByListCtgId(listCategoryCode, AppContexts.user().contractCode());

			if (listEmployee.isEmpty())
				return null;

			// khai bao List ErrorList
			List<ErrorInfoCPS013> listError = new ArrayList<>();

			listEmployee.forEach(emp -> {

				List<DataEmployeeToCheck> listCtgToCheck = new ArrayList<>();

				if (query.isPerInfoCheck() && query.isMasterCheck()) {

				} else if (query.isPerInfoCheck()) {
					listCtgToCheck = getAllDataEmployee(query, mapSidPid, listCategory, emp);
					this.checkPersonInfo(listCtgToCheck, lstCtgSetting, query, emp, listError);
				} else if (query.isMasterCheck()) {
					this.checkMaster(listCategory, lstCtgSetting, query, emp, mapSidPid, listError);
				}
			});
			return null;
		}
		
		/**
		 * 個人基本情報チェック Check thong tin ca nhan co ban
		 * 
		 * @param listCtgToCheck
		 * @param lstCtgSetting
		 * @param query
		 * @param listError
		 * @author yennth
		 */
		private List<ErrorInfoCPS013> checkPersonInfo(List<DataEmployeeToCheck> listCtgToCheck, List<PerInfoValidateCheckCategory> lstCtgSetting, 
				CheckDataFromUI query, EmployeeResultDto emp , List<ErrorInfoCPS013> listError) {
			List<ErrorInfoCPS013> listHist = this.checkHistValid(listCtgToCheck, lstCtgSetting, query, emp, listError);
			listError.addAll(listHist);
			List<ErrorInfoCPS013> requiredSystem = this.checkRequiredSystem(listCtgToCheck, lstCtgSetting, query, emp, listError);
			listError.addAll(requiredSystem);
			List<ErrorInfoCPS013> reference = this.checkPersonInfoReference(listCtgToCheck, lstCtgSetting, query, emp, listError);
			listError.addAll(reference);
			List<ErrorInfoCPS013> single = this.checkSingle(listCtgToCheck, lstCtgSetting, query, emp, listError);
			listError.addAll(single);
			return listError;
		}
		
		/**
		 * アルゴリズム「履歴整合性チェック」を実行する - Check tinh hop le cua lich su
		 * @param listCtgToCheck
		 * @param lstCtgSetting
		 * @param query
		 * @param emp
		 * @param listError
		 * @return
		 */
		private List<ErrorInfoCPS013> checkHistValid(List<DataEmployeeToCheck> listCtgToCheck, List<PerInfoValidateCheckCategory> lstCtgSetting, 
										CheckDataFromUI query, EmployeeResultDto emp , List<ErrorInfoCPS013> listError) {
			// 【個人情報カテゴリ.種類】：[3 連続履歴] [4 非連続履歴][6 連続履歴(期間指定)] のいずれかの場合 - Lọc ra list những data cần check mà có category type = 3 hoặc 4 hoặc 6
			List<DataEmployeeToCheck> listEHistoChk = listCtgToCheck.stream().filter(x -> {
																							if(x.getCategoryType() == 3 || x.getCategoryType() == 4 || x.getCategoryType() == 6){
																								return true;
																							}
																							return false;
																						}).collect(Collectors.toList());
			
			// Lọc ra list những data cần check mà có category type = 3 hoặc 6
			List<DataEmployeeToCheck> listHistType36 = listCtgToCheck.stream().filter(x -> {
																							if(x.getCategoryType() == 3 || x.getCategoryType() == 6){
																								return true;
																							}
																							return false;
																						}).collect(Collectors.toList());
			
			// Lọc ra list những data cần check mà có category type = 3
					List<DataEmployeeToCheck> listHistType3 = listCtgToCheck.stream().filter(x -> {
																									if(x.getCategoryType() == 3){
																										return true;
																									}
																									return false;
																								}).collect(Collectors.toList());
			
			// list những data cần check mà category không phải 3, 4 hoặc 6
//			List<DataEmployeeToCheck> listERemainChk = listCtgToCheck.stream().filter(x -> {
//				if (x.getCategoryType() == 3 || x.getCategoryType() == 4 || x.getCategoryType() == 6) {
//					return false;
//				}
//				return true;
//			}).collect(Collectors.toList());
			
			/**
			 * Check tính hợp lệ của lịch sử
			 */
			List<EmployeeResultDto> listEmp = new ArrayList<>();
			listEmp.add(emp);
			Map<String, String> map = this.mapEmployeeToPerson(listEmp);
			//Check thời gian chồng chéo
			for(DataEmployeeToCheck item : listEHistoChk){
				// lấy tất cả history của 1 category
				PeregQuery pereg = new PeregQuery(null, item.getCategoryCode().toString(),
													item.getEmployeeId().toString(), map.get(emp.sid));
				pereg.setCategoryId(item.getCategoryId().toString());
				List<ComboBoxObject> listHist = empCtgFinder.getListInfoCtgByCtgIdAndSid(pereg);
				// list startDate ~ endDate
				List<String> listHistDay =  listHist.stream().map(x -> x.getOptionText()).collect(Collectors.toList());
				// 「制約：連続かつ永続かつ１件以上存在」該当するカテゴリのデータ１件もない場合 - list lịch sử không có phần tử nào.
				if(listHistDay.size() == 0){
					if(this.checkHistDataSpecial(item.getCategoryCode(), item) == null){
						listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
								emp.bussinessName, TextResource.localize("CPS013_5"), item.getCategoryName(), TextResource.localize("Msg_935")));
					}
				}else{
					// kiểm tra trùng lặp thời gian
					List<ObjectDate> listObDate = new ArrayList<>();
					// lọc lấy list các object có start và end date, bỏ ký tự "~" nối giữa chúng
					for(String hist: listHistDay){
						if(hist.contains("~")){
							String[] splited = hist.split(" ~ ", 2);
							ObjectDate paramObDate = new ObjectDate("", splited[0], splited[1], false);
							listObDate.add(paramObDate);
						}
					}
					if(listObDate.size()>1){
						for(int i = 0; i < (listObDate.size()-1); i++){
							for(int j = 1; j < listObDate.size(); j++){
								ObjectDate overLap = this.isOverlap(listObDate.get(i), listObDate.get(j));
								// nếu thời gian bị chồng chéo thì add thêm message
								if(overLap != null){
									listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), item.getCategoryName(), 
											TextResource.localize("Msg_933", overLap.getStartDate().toString(), overLap.getEndDate().toString())));
								}else{
									// check tính liên tục của lịch sử - 空白期間チェック
									for(DataEmployeeToCheck object : listHistType36){
										List<ObjectDate> listOb = new ArrayList<>();
										listOb = this.getListHist(listHistType36, map, emp);
										// check xem history có liên tục không
										for(int a = 0; a < (listOb.size()-2); a++){
											for(int b = 1; b < (listOb.size()-1); b++){
												ObjectDate isCon = this.isContinue(listOb.get(a), listOb.get(b));
												// nếu không liên tục
												if(isCon!=null){
													listError.add(new ErrorInfoCPS013(object.getEmployeeId(), object.getCategoryId(), emp.getEmployeeCode(), 
															emp.bussinessName, TextResource.localize("CPS013_5"), object.getCategoryName(), TextResource.localize("Msg_931", isCon.getStartDate(), isCon.getEndDate())));
												}else{
													// Check ràng buộc lâu dài - 永続チェック
													for(DataEmployeeToCheck data : listHistType3){
														List<ObjectDate> listODate = new ArrayList<>();
														listODate = this.getListHist(listHistType3, map, emp);
														// check xem history cos ràng buộc lâu dài không
														if(listODate.get(listODate.size()-1).getEndDate() == "9999/12/31" || listODate.get(listODate.size()-1).getEndDate() == ""){
															listError.add(new ErrorInfoCPS013(data.getEmployeeId(), data.getCategoryId(), emp.getEmployeeCode(), 
																	emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
																	TextResource.localize("Msg_932", listODate.get(listODate.size()-1).getStartDate(), listODate.get(listODate.size()-1).getEndDate())));
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				// 履歴データ特殊チェック(check historyData đặc biệt)
				if(this.checkHistDataSpecial(item.getCategoryCode(), item).getClosureId()!=null){
					if(this.checkHistDataSpecial(item.getCategoryCode(), item).getClosureId().toString().length()!=0){
						listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
								emp.bussinessName, TextResource.localize("CPS013_5"), item.getCategoryName(), 
								TextResource.localize("Msg_934", this.checkHistDataSpecial(item.getCategoryCode(), item).getEmploymentCD().toString())));
					}
				}
			}
			return listError;
		}
		
		/**
		 * システム必須チェック - check required system
		 * @author yennth
		 */
		private List<ErrorInfoCPS013> checkRequiredSystem(List<DataEmployeeToCheck> listCtgToCheck, List<PerInfoValidateCheckCategory> lstCtgSetting, 
				CheckDataFromUI query, EmployeeResultDto emp , List<ErrorInfoCPS013> listError) {
			List<DataEmployeeToCheck> mapCheckSetting = new ArrayList<>();
			List<EmployeeResultDto> listEmp = new ArrayList<>();
			listEmp.add(emp);
			// システム必須チェック - kiểm tra required system
			Map<String, String> map = this.mapEmployeeToPerson(listEmp);
			// lọc category sao cho [個人情報整合性チェックカテゴリ]の[就業システム必須]:True または[人事システム必須]:True または[給与システム必須]:True 
			List<PerInfoValidateCheckCategory> listFillterSetting = lstCtgSetting.stream().filter(x -> {
				if(x.getJobSysReq().value == 1 || x.getHumanSysReq().value == 1 || x.getPaySysReq().value == 1){
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			
			for(PerInfoValidateCheckCategory obj: listFillterSetting){ 
				mapCheckSetting.addAll(listCtgToCheck.stream().filter(x -> {
																				return x.getCategoryCode() == obj.getCategoryCd().v();
																}).collect(Collectors.toList()));
			}
			// 履歴かどうかをチェックする - Check xem có phải lịch sử hay không
			for(DataEmployeeToCheck data: mapCheckSetting){
				if(data.getCategoryType() == 3 || data.getCategoryType() == 4 || data.getCategoryType() == 5 || data.getCategoryType() == 6){
					// check sự tồn tại history data
					List<ComboBoxObject> listHistory = empCtgFinder.getListInfoCtgByCtgIdAndSid(PeregQuery.createQueryLayout(data.getCategoryCode(), 
																								data.getEmployeeId(), map.get(emp.sid), GeneralDate.today()));
					listHistory = new ArrayList<>();
					// データ１も存在しない場合 - không có data
					if(listHistory.isEmpty()){
						listError.add(new ErrorInfoCPS013(data.getEmployeeId(), data.getCategoryId(), emp.getEmployeeCode(), 
								emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
								TextResource.localize("Msg_951", GeneralDate.today().toString())));
					}
				}else{
					// Chờ anh Lai
					/**
					 * 
					 */
				}
			}
			return listError;
		}
		
		/**
		 * 参照項目チェック処理
		 * Check item tham chiếu
		 * @param listCtgToCheck
		 * @param lstCtgSetting
		 * @param query
		 * @param emp
		 * @param listError
		 * @return
		 * @author yennth
		 */
		private List<ErrorInfoCPS013> checkPersonInfoReference(List<DataEmployeeToCheck> listCtgToCheck, List<PerInfoValidateCheckCategory> lstCtgSetting, 
				CheckDataFromUI query, EmployeeResultDto emp , List<ErrorInfoCPS013> listError) {
			// 選択項目の参照区分をチェックする - check reference division cuả selection item
			for(DataEmployeeToCheck item: listCtgToCheck){
				for(LayoutPersonInfoClsDto obj : item.getData().getClassificationItems()){
					for(LayoutPersonInfoValueDto data : obj.getItems()){
						if(data.getLstComboBoxValue() != null){
							List<String> comboValue = data.getLstComboBoxValue().stream().map(x -> x.getOptionValue()).collect(Collectors.toList());
							Object value = data.getValue();
							DataTypeStateDto dataTypeValue = data.getItem();
							if(dataTypeValue.getDataTypeValue() == 6 || dataTypeValue.getDataTypeValue() == 7 || dataTypeValue.getDataTypeValue() == 8){
								SelectionItemDto selectionDto = (SelectionItemDto) dataTypeValue;
								ReferenceTypes referType = selectionDto.getReferenceType();
								// nếu là enum
								if(referType.value == 3){
									if(!comboValue.contains(value.toString())){
										listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
												emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
												TextResource.localize("Msg_949", data.getItemName().toString(), value.toString(), java.util.Collections.min(comboValue), 
														java.util.Collections.max(comboValue))));
									}
								}
								// nếu là code name 
								else if(referType.value == 2){
									if(!comboValue.contains(value.toString())){
										listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
												emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
												TextResource.localize("Msg_950", data.getItemName().toString(), value.toString())));
									}
								}
								// nếu là design master
								else if(referType.value == 1){
									if(!comboValue.contains(value.toString())){
										if(item.getCategoryCode() == "CS00017" || item.getCategoryCode() == "CS00016"){
											listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
													emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
													TextResource.localize("Msg_936", data.getItemName().toString(), value.toString(), data.getItemName().toString(), GeneralDate.today().toString())));
										}else{
											listError.add(new ErrorInfoCPS013(item.getEmployeeId(), item.getCategoryId(), emp.getEmployeeCode(), 
													emp.bussinessName, TextResource.localize("CPS013_5"), data.getCategoryName(), 
													TextResource.localize("Msg_937", data.getItemName().toString(), value.toString(), data.getItemName().toString())));
										}
									}
								}
							}
						}
					}
				}
			}
			return listError;
		}
		
		/**
		 * Check single item
		 * @param listCtgToCheck
		 * @param lstCtgSetting
		 * @param query
		 * @param emp
		 * @param listError
		 * @return
		 * @author yennth
		 */
		private List<ErrorInfoCPS013> checkSingle(List<DataEmployeeToCheck> listCtgToCheck, List<PerInfoValidateCheckCategory> lstCtgSetting, 
				CheckDataFromUI query, EmployeeResultDto emp , List<ErrorInfoCPS013> listError) {
			String birthday = "";
			String joinCompany = "";
			String retireDate = "";
			String ctgname1 = "";
			String itemname1 = "";
			String value1 = "";
			String ctgname2 = "";
			String itemname2 = "";
			String value2 = "";
			String ctgname3 = "";
			String itemname3 = "";
			String value3 = "";
			String emp1 = "";
			String ctgId1 = "";
			String emp2 = "";
			String ctgId2 = "";
			for(DataEmployeeToCheck ctgChk: listCtgToCheck){
				for(LayoutPersonInfoClsDto perInfo: ctgChk.getData().getClassificationItems()){
					for(LayoutPersonInfoValueDto layout: perInfo.getItems()){
						// Check null cho các item required
						if(layout.isRequired() && layout.getValue() == null){
							listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
									emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
									TextResource.localize("Msg_955", layout.getItemName().toString())));
							continue;
						}
						// Check xem có phải special check item? có hay không ký tự space tiếng anh hoặc tiếng nhật
						if(layout.getItemCode().toString() == "IS00003" || layout.getItemCode().toString() == "IS00004" || 
								layout.getItemCode().toString() == "IS00015" || layout.getItemCode().toString() == "IS00016"){
							if(layout.getValue().toString().contains(" ") || layout.getValue().toString().contains("　")){
								listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
										emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
										TextResource.localize("Msg_952", layout.getItemName().toString())));
							}
							continue;
						}
						// ngày sinh nhật
						if(layout.getItemCode().toString() == "IS00017"){
							birthday = layout.getValue().toString();
							ctgname1 = ctgChk.getCategoryName().toString();
							itemname1 = layout.getItemName().toString();
							value1 = layout.getValue().toString();
							emp1 = ctgChk.getEmployeeId().toString();
							ctgId1 = ctgChk.getCategoryId().toString();
						}
						// ngày vào cty
						if(layout.getItemCode().toString() == "IS00020"){
							joinCompany = layout.getValue().toString();
							ctgname2 = ctgChk.getCategoryName().toString();
							itemname2 = layout.getItemName().toString();
							value2 = layout.getValue().toString();
							emp2 = ctgChk.getEmployeeId().toString();
							ctgId2 = ctgChk.getCategoryId().toString();
						}
						// ngày nghỉ hưu
						if(layout.getItemCode().toString() == "IS00021"){
							retireDate = layout.getValue().toString();
							ctgname3 = ctgChk.getCategoryName().toString();
							itemname3 = layout.getItemName().toString();
							value3 = layout.getValue().toString();
						}
						// thực hiện thuật toán check required của workTime - アルゴリズム「就業時間帯の必須チェック」
						if(layout.getCategoryCode().toString() == "CS00020"){
							if(layout.getItemCode().toString() == "IS00149"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00148")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00151")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00152")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00158"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00157")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00160")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00161")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00167"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00166")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00169")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00170")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00176"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00175")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00178")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00179")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
						}
						if(layout.getCategoryCode().toString() == "CS00070"){
							if(layout.getItemCode().toString() == "IS00194"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00193")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00196")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00197")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00203"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00202")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00205")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00206")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00212"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00211")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00214")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00215")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00221"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00220")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00223")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00224")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00230"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00229")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00230")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00233")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00239"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00238")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00241")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00242")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
							
							if(layout.getItemCode().toString() == "IS00185"){
								LayoutPersonInfoValueDto is1 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00184")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is2 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00187")).collect(Collectors.toList()).get(0);
								LayoutPersonInfoValueDto is3 = perInfo.getItems().stream().filter(x -> x.getItemCode().equals("IS00188")).collect(Collectors.toList()).get(0);
								// check work type và work time
								if(basicScheduleService.checkNeededOfWorkTimeSetting(is1.getValue().toString()) == SetupType.REQUIRED 
																						&& (layout.getValue() == null || is2.getValue() == null 
																							|| is3.getValue() == null)){
									listError.add(new ErrorInfoCPS013(ctgChk.getEmployeeId(), ctgChk.getCategoryId(), emp.getEmployeeCode(), 
											emp.bussinessName, TextResource.localize("CPS013_5"), ctgChk.getCategoryName(), 
											TextResource.localize("Msg_956", is1.getItemName().toString(), is1.getValue().toString(), layout.getItemName().toString())));
									continue;
								}
							}
						}
					}
				}
				// check đảo ngược date
				if(birthday.compareTo(joinCompany) > 0 || joinCompany.compareTo(birthday) > 0){
					listError.add(new ErrorInfoCPS013(emp1, ctgId1, emp.getEmployeeCode(), 
							emp.bussinessName, TextResource.localize("CPS013_5"), ctgname1, 
							TextResource.localize("Msg_953", ctgname1, itemname1, value1, ctgname2, itemname2, value2)));
					continue;
				}
				if(joinCompany.compareTo(birthday) > 0){
					listError.add(new ErrorInfoCPS013(emp2, ctgId2, emp.getEmployeeCode(), 
							emp.bussinessName, TextResource.localize("CPS013_5"), ctgname2, 
							TextResource.localize("Msg_953", ctgname2, itemname2, value2, ctgname3, itemname3, value3)));
					continue;
				}
			}
			return listError; 	
		}
		
//		/**
//		 *  lấy những item con cấp bé nhất
//		 * @param listLayout
//		 * @param parentCd
//		 * @return
//		 * @author yen
//		 */
//		private List<LayoutPersonInfoValueDto> getChild(List<LayoutPersonInfoValueDto> listLayout, String parentCd){
//			List<LayoutPersonInfoValueDto> listResult = new ArrayList<>();
//			List<String> listParent = new ArrayList<>();
//			listParent.add(parentCd); 
//			int lengthOld = listParent.size(); 
//			for(LayoutPersonInfoValueDto item : listLayout){
//				if(item.getItemParentCode().toString() == listParent.get(0)){
//					listParent.add(item.getItemCode().toString());
//				}
//			}
//			if(listParent.size() == lengthOld){
//				listResult.add(listLayout.stream().filter(x -> x.getItemCode().equals(listParent.get(0))).collect(Collectors.toList()).get(0));
//			}
//			listParent.remove(0);
//			if(!listParent.isEmpty()){
//				this.getChild(listLayout, listParent.get(0));
//			}
//			return listResult;
//		}
		
		/**
		 * lấy list object history
		 * @author yennth
		 */
		private List<ObjectDate> getListHist(List<DataEmployeeToCheck> listHist, Map<String, String> map, EmployeeResultDto emp){
			List<ObjectDate> listOb = new ArrayList<>();
			for(DataEmployeeToCheck data : listHist){
				// lấy tất cả history của 1 category
				PeregQuery perQue = new PeregQuery(null, data.getCategoryCode(), data.getEmployeeId(), map.get(emp.sid));
				perQue.setCategoryId(data.getCategoryId().toString());
				List<ComboBoxObject> listHistory = empCtgFinder.getListInfoCtgByCtgIdAndSid(perQue);
				// list startDate ~ endDate
				List<String> listStEnd =  listHistory.stream().map(x -> x.getOptionText()).collect(Collectors.toList());
				// lọc lấy list các object có start và end date, bỏ ký tự "~" nối giữa chúng
				for(String his: listStEnd){
					if(his.contains("~")){
						String[] splited = his.split(" ~ ", 2);
						ObjectDate paramObDate = new ObjectDate("", splited[0], splited[1], false);
						listOb.add(paramObDate);
					}
				}
			}
			return listOb;
		}

		/**
		 * Check thông tin master
		 * 
		 * @param listCtgToCheck
		 * @param lstCtgSetting
		 * @param query
		 * @param emp
		 * @param mapSidPid
		 * @param listError
		 * @return
		 */
		private List<ErrorInfoCPS013> checkMaster(List<PersonInfoCategory> listCategory, List<PerInfoValidateCheckCategory> lstCtgSetting, CheckDataFromUI query, EmployeeResultDto emp,
				Map<String, String> mapSidPid, List<ErrorInfoCPS013> listError) {

			LoginUserRoles permission = AppContexts.user().roles();
			String forAttendance = permission.forAttendance();
			String forPayroll = permission.forPayroll();

			for (PersonInfoCategory ctg : listCategory) {
				ErrorInfoCPS013 error = new ErrorInfoCPS013();
				List<String> errorListInfo = new ArrayList<>();
				PerInfoValidateCheckCategory setting = lstCtgSetting.stream()
						.filter(i -> i.getCategoryCd().equals(ctg.getCategoryCode())).findFirst().get();
				PeregDto peregDto = null;
				if (ctg.getCategoryType().value == CategoryType.SINGLEINFO.value) {
					peregDto = layoutingProcessor.findSingle(
							new PeregQuery(null, ctg.getCategoryCode().toString(), emp.sid, mapSidPid.get(emp.sid)));
				} else {
					PeregQuery peregQuery = new PeregQuery(null, null, emp.sid, mapSidPid.get(emp.sid));
					peregQuery.setCategoryCode(ctg.getCategoryCode().toString());
					peregQuery.setCategoryId(ctg.getPersonInfoCategoryId());
					List<String> infoIds = this.employeeFinderCtg.getListInfoCtgByCtgIdAndSid(peregQuery);
					if (!infoIds.isEmpty()) {
						peregDto = layoutingProcessor.findSingle(new PeregQuery(infoIds.get(0),
								ctg.getCategoryCode().toString(), emp.sid, mapSidPid.get(emp.sid)));
					}
				}

				if ((forAttendance != null) && (query.isScheduleMngCheck())
						&& (setting.getScheduleMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "スケジュール管理"));
					}
				}

				if ((forAttendance != null) && (query.isDailyPerforMngCheck())
						&& (setting.getDailyActualMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "日別実績管理"));
					}
				}

				if ((forAttendance != null) && (query.isMonthPerforMngCheck())
						&& (setting.getMonthActualMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "月別実績管理"));
					}
				}

				if ((forPayroll != null) && (query.isPayRollMngCheck()) && (setting.getPayMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "給与管理"));
					}
				}

				if ((forPayroll != null) && (query.isBonusMngCheck()) && (setting.getBonusMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "賞与管理"));
					}
				}

				if ((forPayroll != null) && (query.isYearlyMngCheck()) && (setting.getYearMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "年調管理"));
					}
				}

				if ((forPayroll != null) && (query.isMonthCalCheck()) && (setting.getMonthCalcMngReq() == NotUseAtr.USE)) {
					if (peregDto == null) {
						errorListInfo.add(TextResource.localize("Msg_1483", "月額算定管理"));
					}
				}

				if (!errorListInfo.isEmpty()) {
					String errorInfo = "";
					for (int j = 0; j < errorListInfo.size(); j++) {
						errorInfo = errorInfo + " , ";
					}
					error = new ErrorInfoCPS013(emp.sid, ctg.getPersonInfoCategoryId(), emp.employeeCode, emp.bussinessName,
							"check master", ctg.getCategoryName().toString(), errorInfo);
					listError.add(error);
				}
			}
			return listError;
		}
		
		/**
		 * Lay toan bo data cua 1 nhan vien
		 * @param query
		 * @param map
		 * @param getAllCategory
		 * @param emp
		 * @return
		 */
		private List<DataEmployeeToCheck> getAllDataEmployee(CheckDataFromUI query, Map<String, String> map,
				List<PersonInfoCategory> getAllCategory, EmployeeResultDto emp) {
			List<DataEmployeeToCheck> result = new ArrayList<>();

			getAllCategory.forEach(ctg -> {
				if (ctg.getCategoryType() == CategoryType.SINGLEINFO) {
					PeregQuery peregQuery1 = new PeregQuery(null, null, emp.sid, map.get(emp.sid));
					peregQuery1.setCategoryCode(ctg.getCategoryCode().toString());
					peregQuery1.setCategoryId(ctg.getPersonInfoCategoryId());
					peregQuery1.setInfoId(null);
					EmpMaintLayoutDto dataCategory1 = this.getCategoryDetail(peregQuery1, ctg);
					DataEmployeeToCheck data1 = new DataEmployeeToCheck();
					data1.employeeId = emp.sid;
					data1.employeeCode = emp.employeeCode;
					data1.businessName = emp.bussinessName;
					data1.setCategoryId(ctg.getPersonInfoCategoryId());
					data1.setCategoryCode(ctg.getCategoryCode().toString());
					data1.setCategoryName(ctg.getCategoryName().toString());
					data1.setCategoryType(ctg.getCategoryType().value);
					data1.setData(dataCategory1);
					result.add(data1);
				} else {
					// list id cua history va cardId cua category lich su vaf multi
					PeregQuery peregQuery2 = new PeregQuery(null, null, emp.sid, map.get(emp.sid));
					peregQuery2.setCategoryCode(ctg.getCategoryCode().toString());
					peregQuery2.setCategoryId(ctg.getPersonInfoCategoryId());
					List<String> infoId = this.employeeFinderCtg.getListInfoCtgByCtgIdAndSid(peregQuery2);
					for (int i = 0; i < infoId.size(); i++) {
						DataEmployeeToCheck data2 = new DataEmployeeToCheck();
						data2.employeeId = emp.sid;
						data2.employeeCode = emp.employeeCode;
						data2.businessName = emp.bussinessName;
						data2.setCategoryId(ctg.getPersonInfoCategoryId());
						data2.setCategoryCode(ctg.getCategoryCode().toString());
						data2.setCategoryType(ctg.getCategoryType().value);
						data2.setCategoryName(ctg.getCategoryName().toString());
						peregQuery2.setInfoId(infoId.get(i));
						EmpMaintLayoutDto dataCategory2 = this.getCategoryDetail(peregQuery2, ctg);
						data2.setData(dataCategory2);
						result.add(data2);
					}
				}
			});
			return result;
		}

		public Map<String, String> mapEmployeeToPerson(List<EmployeeResultDto> listEmployee) {
			Map<String, String> map = new HashMap<>();
			List<String> sId = listEmployee.stream().map(i -> i.getSid()).collect(Collectors.toList());
			List<EmployeeDataMngInfo> empDataMngInfo = this.empDataMngRepo
					.findByListEmployeeId(AppContexts.user().companyId(), sId);
			empDataMngInfo.forEach(emp -> {
				map.put(emp.getEmployeeId(), emp.getPersonId());
			});
			return map;
		}
		
		/**
		 * Check sự chồng chéo giữa 2 khoảng thời gian của history
		 * @param date1
		 * @param date2
		 * @return
		 */
		private ObjectDate isOverlap(ObjectDate date1, ObjectDate date2){
			ObjectDate startEnd = new ObjectDate();
			/**
			 * date 1.........|..............]..........
			 * date 2............|......................
			 * sDate1<sDate2<eDate1
			 */
			if (date2.getStartDate().compareTo(date1.getStartDate()) > 0
					&& date2.getStartDate().compareTo(date1.getEndDate()) < 0) {
				startEnd.setStartDate(date2.getStartDate());
				startEnd.setEndDate(date1.getEndDate());
				return startEnd;
			}
			/**
			 * date 1.........|..............]..........
			 * date 2.........|..........]..............
			 * sDate1<=sDate2 && eDate<eDate1
			 */
			if (date2.getStartDate().compareTo(date1.getStartDate()) == 0
					&& date2.getEndDate().compareTo(date1.getEndDate()) < 0) {
				startEnd.setStartDate(date1.getStartDate());
				startEnd.setEndDate(date2.getEndDate());
				return startEnd;
			}
			
			/**
			 * date 1.........|..............]..........
			 * date 2.....|........]....................
			 * sDate2 < sDate1 and eDate2 > sDate1
			 */
			if (date2.getStartDate().compareTo(date1.getStartDate()) < 0
					&& date2.getEndDate().compareTo(date1.getStartDate()) > 0) {
				startEnd.setStartDate(date1.getStartDate());
				startEnd.setEndDate(date2.getEndDate());
				return startEnd;
			}
			/**
			 * date 1.........|..............]..........
			 * date 2.............]....................
			 * sDate1 < eDate2 < eDate1
			 */
			if (date1.getStartDate().compareTo(date2.getEndDate()) < 0
					&& date2.getEndDate().compareTo(date1.getEndDate()) < 0) {
				startEnd.setStartDate(date1.getStartDate());
				startEnd.setEndDate(date2.getEndDate());
				return startEnd;
			}
			/**
			 * date 1.........|..............]..........
			 * date 2..........|...................].....
			 * eDate2 > eDate1 && sDate2 < eDate1
			 */
			if(date2.getEndDate().compareTo(date1.getEndDate()) > 0
					&& date2.getStartDate().compareTo(date1.getEndDate()) <0){
				startEnd.setStartDate(date2.getStartDate());
				startEnd.setEndDate(date1.getEndDate());
				return startEnd;
			}
			return null;
		}
		
		/**
		 * check tính liên tục của lịch sử
		 * @param date1
		 * @param date2
		 * @return
		 */
		private ObjectDate isContinue(ObjectDate date1, ObjectDate date2){
			ObjectDate startEnd = new ObjectDate();
			if((date1.getEndDate().compareTo(date2.getStartDate() + 1) < 0) || (date1.getEndDate().compareTo(date2.getStartDate() + 1) > 0)) {
				startEnd.setStartDate(date1.getEndDate());
				startEnd.setEndDate(date2.getStartDate());
				return startEnd;
			}
			return null;
		}
		
		/**
		 * 実行時情報の、「ログインユーザコンテキスト」．システム利用区分を確認チェックする - check xem có phải đối tượng check đặc biệt
		 * true: đặc biệt
		 * @param categoryCd
		 * @return
		 */
		private ClosureEmployment checkHistDataSpecial(String categoryCd, DataEmployeeToCheck dataNeedChecked){
			ClosureEmployment temp = new ClosureEmployment();
			if(AppContexts.system().isInstalled(ProductType.ATTENDANCE)==true && categoryCd == "CS00014"){
				for(LayoutPersonInfoClsDto item : dataNeedChecked.getData().getClassificationItems()){
					for(LayoutPersonInfoValueDto obj: item.getItems()){
						if(obj.getItemCode() == "IS00068"){
							String employmentCd = obj.getValue().toString();
							// ドメン「雇用に紐づく就業締め」を取得する - lấy domain 雇用に紐づく就業締め
							Optional<ClosureEmployment> closureEmp = closureEmploymentRepository.findByEmploymentCD(AppContexts.user().companyId(), employmentCd);
							if(closureEmp.isPresent()){
								return closureEmp.get();
							}else{
								temp.setEmploymentCD(employmentCd);
								return temp;
							}
						}
					}
				}
			}
			return temp;
		}

}
