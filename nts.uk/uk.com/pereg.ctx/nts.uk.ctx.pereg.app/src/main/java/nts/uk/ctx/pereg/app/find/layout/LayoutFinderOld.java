///**
// * 
// */

//package nts.uk.ctx.pereg.app.find.layout;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
//import nts.uk.ctx.pereg.app.find.layout.dto.SimpleEmpMainLayoutDto;
//import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
//import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
//import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
//import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
//import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
//import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
//import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
//import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
//import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
//import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
//import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
//import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
//import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
//import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
//import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
//import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;
//import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
//import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
//import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
//import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
//import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
//import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
//import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
//import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
//import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
//import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
//import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
//import nts.uk.shr.com.context.AppContexts;
//
///**
// * @author danpv
// *
// */
//@Stateless
//public class LayoutFinderOld {
//
//	@Inject
//	private IMaintenanceLayoutRepository maintenanceRepo;
//
//	@Inject
//	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;
//
//	@Inject
//	private PersonInfoItemAuthRepository perInfoItemAuthRepo;
//
//	@Inject
//	private PerInfoCategoryRepositoty perInfoCateRepo;
//
//	// inject category-data-repo
//	@Inject
//	private PerInfoCtgDataRepository perInCtgDataRepo;
//
//	@Inject
//	private PerInfoItemDataRepository perInItemDataRepo;
//
//	@Inject
//	private EmInfoCtgDataRepository empInCtgDataRepo;
//
//	@Inject
//	private EmpInfoItemDataRepository empInItemDataRepo;
//
//	@Inject
//	private IMaintenanceLayoutRepository layoutRepo;
//
//	@Inject
//	private LayoutPersonInfoClsFinder clsFinder;
//
//	public List<SimpleEmpMainLayoutDto> getSimpleLayoutList(String browsingEmpId) {
//		String loginEmpId = AppContexts.user().employeeId();
//		String companyId = AppContexts.user().companyId();
//		String roleId = AppContexts.user().roles().forPersonnel();
//		boolean selfBrowsing = loginEmpId.equals(browsingEmpId);
//
//		List<MaintenanceLayout> simpleLayouts = layoutRepo.getAllMaintenanceLayout(companyId);
//		Map<String, PersonInfoCategoryAuth> mapCategoryAuth = perInfoCtgAuthRepo.getAllCategoryAuthByRoleId(roleId)
//				.stream().collect(Collectors.toMap(e -> e.getPersonInfoCategoryAuthId(), e -> e));
//		List<SimpleEmpMainLayoutDto> acceptSplLayouts = new ArrayList<>();
//		for (MaintenanceLayout simpleLayout : simpleLayouts) {
//			if (haveAnItemAuth(simpleLayout.getMaintenanceLayoutID(), mapCategoryAuth, selfBrowsing)) {
//				acceptSplLayouts.add(SimpleEmpMainLayoutDto.fromDomain(simpleLayout));
//			}
//		}
//		return acceptSplLayouts;
//	}
//
//	private boolean haveAnItemAuth(String layoutId, Map<String, PersonInfoCategoryAuth> mapCategoryAuth,
//			boolean selfBrowsing) {
//		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(layoutId);
//		for (LayoutPersonInfoClsDto itemClass : itemClassList) {
//			if (itemClass.getLayoutItemType() == LayoutItemType.SeparatorLine) {
//				continue;
//			}
//			PersonInfoCategoryAuth categoryAuth = mapCategoryAuth.get(itemClass.getPersonInfoCategoryID());
//			if (categoryAuth == null) {
//				continue;
//			}
//			if (selfBrowsing && categoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES) {
//				return true;
//			}
//			if (!selfBrowsing && categoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * @param query
//	 * @return get layout and data of layout with browsing employee
//	 */
//	public EmpMaintLayoutDto getLayout(LayoutQuery query) {
//		// query properties
//		GeneralDate standardDate = GeneralDate.legacyDate(query.getStandardDate());
//		String mainteLayoutId = query.getLayoutId();
//		String browsingEmpId = query.getBrowsingEmpId();
//		// login information
//		String contractCode = AppContexts.user().contractCode();
//		String companyId = AppContexts.user().companyId();
//		String loginEmployeeId = AppContexts.user().employeeId();
//		String roleId = AppContexts.user().roles().forPersonnel();
//		// check standard date
//		// get layout
//		MaintenanceLayout maintenanceLayout = maintenanceRepo.getById(companyId, mainteLayoutId).get();
//		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(mainteLayoutId);
//		EmpMaintLayoutDto result = EmpMaintLayoutDto.createFromDomain(maintenanceLayout);
//		boolean selfBrowsing = browsingEmpId.equals(loginEmployeeId);
//		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();
//		/*
//		 * for each class-item, check author of person who login with class-item
//		 * then, get data for class-item
//		 */
//		for (LayoutPersonInfoClsDto classItem : itemClassList) {
//			// if item is separator line, do not check
//			if (classItem.getLayoutItemType() == LayoutItemType.SeparatorLine) {
//				authItemClasList.add(classItem);
//			} else {
//				Optional<PersonInfoCategoryAuth> personCategoryAuthOpt = perInfoCtgAuthRepo
//						.getDetailPersonCategoryAuthByPId(roleId, classItem.getPersonInfoCategoryID());
//
//				if (validateAuthClassItem(personCategoryAuthOpt, selfBrowsing)) {
//					LayoutPersonInfoClsDto authClassItem = classItem;
//					// check author of each definition in class-item
//					List<PersonInfoItemAuth> inforAuthItems = perInfoItemAuthRepo.getAllItemAuth(roleId,
//							classItem.getPersonInfoCategoryID());
//					List<PerInfoItemDefDto> dataInfoItems = validateAuthItem(inforAuthItems, classItem.getListItemDf(),
//							selfBrowsing);
//					// if definition-items is empty, will NOT show this
//					// class-item
//					if (dataInfoItems.isEmpty()) {
//						continue;
//					}
//
//					authClassItem.setListItemDf(dataInfoItems);
//
//					PersonInfoCategory perInfoCategory = perInfoCateRepo
//							.getPerInfoCategory(authClassItem.getPersonInfoCategoryID(), contractCode).get();
//					// get data
//					if (authClassItem.getLayoutItemType() == LayoutItemType.ITEM) {
//					} else if (authClassItem.getLayoutItemType() == LayoutItemType.LIST) {
//						getDataforListItem(perInfoCategory, personCategoryAuthOpt.get(), inforAuthItems, authClassItem,
//								standardDate, null, null, selfBrowsing);
//					}
//
//					authItemClasList.add(authClassItem);
//				}
//			}
//		}
//
//		List<LayoutPersonInfoClsDto> authItemClasList1 = new ArrayList<>();
//		for (int i = 0; i < authItemClasList.size(); i++) {
//			if (i == 0) {
//				authItemClasList1.add(authItemClasList.get(i));
//			} else {
//				boolean notAcceptElement = authItemClasList.get(i).getLayoutItemType() == LayoutItemType.SeparatorLine
//						&& authItemClasList.get(i - 1).getLayoutItemType() == LayoutItemType.SeparatorLine;
//				if (!notAcceptElement) {
//					authItemClasList1.add(authItemClasList.get(i));
//				}
//			}
//		}
//
//		result.setClassificationItems(authItemClasList1);
//		return result;
//	}
//
//	/**
//	 * @param roleId
//	 * @param item
//	 * @param selfBrowsing
//	 *            Target: check author of person who login with class-item
//	 * @return
//	 */
//	private boolean validateAuthClassItem(Optional<PersonInfoCategoryAuth> personCategoryAuthOpt,
//			boolean selfBrowsing) {
//		if (!personCategoryAuthOpt.isPresent()) {
//			return false;
//		}
//		if (selfBrowsing && personCategoryAuthOpt.get().getAllowPersonRef() == PersonInfoPermissionType.YES) {
//			return true;
//		}
//		if (!selfBrowsing && personCategoryAuthOpt.get().getAllowOtherRef() == PersonInfoPermissionType.YES) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * @param authItems
//	 * @param listItemDef
//	 * @param selfBrowsing
//	 * @return Target: check author of person who login with each
//	 *         definition-items in class-item
//	 */
//	private List<PerInfoItemDefDto> validateAuthItem(List<PersonInfoItemAuth> authItems,
//			List<PerInfoItemDefDto> listItemDef, boolean selfBrowsing) {
//		List<PerInfoItemDefDto> dataInfoItems = new ArrayList<>();
//		for (PerInfoItemDefDto itemDef : listItemDef) {
//			Optional<PersonInfoItemAuth> authItemOpt = authItems.stream()
//					.filter(p -> p.getPersonItemDefId().equals(itemDef.getId())).findFirst();
//			if (authItemOpt.isPresent()) {
//				if (selfBrowsing && authItemOpt.get().getSelfAuth() != PersonInfoAuthType.HIDE) {
//					dataInfoItems.add(itemDef);
//				} else if (!selfBrowsing && authItemOpt.get().getOtherAuth() != PersonInfoAuthType.HIDE) {
//					dataInfoItems.add(itemDef);
//				}
//			}
//		}
//		return dataInfoItems;
//
//	}
//
//	/**
//	 * @param perInfoCategory
//	 * @param authClassItem
//	 * @param standardDate
//	 * @param personId
//	 * @param employeeId
//	 *            Target: get data with definition-items
//	 */
//	private void getDataforListItem(PersonInfoCategory perInfoCategory, PersonInfoCategoryAuth personCategoryAuth,
//			List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto authClassItem, GeneralDate standardDate,
//			String personId, String employeeId, boolean selfBrowsing) {
//		if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.PERSON) {
//			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
//				// FIXED
//			} else {
//				// UNFIXED
//				Map<String, List<LayoutPersonInfoValueDto>> mapOptionData = getPersDataOptionalForListClsItem(
//						perInfoCategory.getCategoryCode().v(), authClassItem, personId);
//				authClassItem.setItems(new ArrayList<>(mapOptionData.values()));
//			}
//		} else if (perInfoCategory.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
//			if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
//			} else {
//				// UNFIXED
//				Map<String, List<LayoutPersonInfoValueDto>> mapOptionData = getEmpDataForListClsItem(
//						perInfoCategory.getCategoryCode().v(), authClassItem, employeeId);
//				authClassItem.setItems(new ArrayList<>(mapOptionData.values()));
//			}
//		}
//		// 一覧表を制御する
//		checkActionRoleWithData(perInfoCategory, personCategoryAuth, inforAuthItems, authClassItem, standardDate,
//				selfBrowsing);
//	}
//
//	private void checkActionRoleWithData(PersonInfoCategory perInfoCategory, PersonInfoCategoryAuth personCategoryAuth,
//			List<PersonInfoItemAuth> inforAuthItems, LayoutPersonInfoClsDto authClassItem, GeneralDate standardDate,
//			boolean selfBrowsing) {
//		switch (perInfoCategory.getCategoryType()) {
//		case MULTIINFO:
//			List<Object> mulSeigoItemsData = new ArrayList<>();
//			for (Object mulItem : authClassItem.getItems()) {
//				@SuppressWarnings("unchecked")
//				List<LayoutPersonInfoValueDto> mulRowData = (List<LayoutPersonInfoValueDto>) mulItem;
//				List<LayoutPersonInfoValueDto> mulActionRoleRowData = checkAndSetActionRole(mulRowData, inforAuthItems,
//						selfBrowsing);
//				mulSeigoItemsData.add(mulActionRoleRowData);
//			}
//			authClassItem.setItems(mulSeigoItemsData);
//			break;
//		case CONTINUOUSHISTORY:
//		case NODUPLICATEHISTORY:
//		case DUPLICATEHISTORY:
//		case CONTINUOUS_HISTORY_FOR_ENDDATE:
//			DateRangeItem dateRangeItem = perInfoCateRepo
//					.getDateRangeItemByCtgId(perInfoCategory.getPersonInfoCategoryId());
//			String startDateId = dateRangeItem.getStartDateItemId();
//			String endDateId = dateRangeItem.getEndDateItemId();
//			List<Object> seigoItemsData = new ArrayList<>();
//			for (Object item : authClassItem.getItems()) {
//				@SuppressWarnings("unchecked")
//				List<LayoutPersonInfoValueDto> rowData = (List<LayoutPersonInfoValueDto>) item;
//				Optional<LayoutPersonInfoValueDto> startDateOpt = rowData.stream()
//						.filter(column -> column.getItemDefId().equals(startDateId)).findFirst();
//				Optional<LayoutPersonInfoValueDto> endDateOpt = rowData.stream()
//						.filter(column -> column.getItemDefId().equals(endDateId)).findFirst();
//
//				if (startDateOpt.isPresent() && endDateOpt.isPresent()) {
//					PersonInfoAuthType auth = PersonInfoAuthType.UPDATE;
//					if (standardDate.after((GeneralDate) endDateOpt.get().getValue())) {
//						// past
//						auth = selfBrowsing ? personCategoryAuth.getSelfPastHisAuth()
//								: personCategoryAuth.getOtherPastHisAuth();
//					} else if (standardDate.before((GeneralDate) startDateOpt.get().getValue())) {
//						// future
//						auth = selfBrowsing ? personCategoryAuth.getSelfFutureHisAuth()
//								: personCategoryAuth.getOtherFutureHisAuth();
//					}
//					switch (auth) {
//					case REFERENCE:
//						rowData.forEach(element -> element.setActionRole(ActionRole.VIEW_ONLY));
//						seigoItemsData.add(rowData);
//						break;
//					case UPDATE:
//						List<LayoutPersonInfoValueDto> actionRoleRowData = checkAndSetActionRole(rowData,
//								inforAuthItems, selfBrowsing);
//						seigoItemsData.add(actionRoleRowData);
//						break;
//					case HIDE:
//						// do NOT add to authItemsData
//						break;
//					}
//				}
//			}
//			authClassItem.setItems(seigoItemsData);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private List<LayoutPersonInfoValueDto> checkAndSetActionRole(List<LayoutPersonInfoValueDto> rowData,
//			List<PersonInfoItemAuth> inforAuthItems, boolean selfBrowsing) {
//		List<LayoutPersonInfoValueDto> actionRoleRowData = new ArrayList<>();
//		for (LayoutPersonInfoValueDto element : rowData) {
//			Optional<PersonInfoItemAuth> authItemOpt = inforAuthItems.stream()
//					.filter(authItem -> authItem.getPersonItemDefId().equals(element.getItemDefId())).findFirst();
//			if (authItemOpt.isPresent()) {
//				PersonInfoAuthType auth = selfBrowsing ? authItemOpt.get().getSelfAuth()
//						: authItemOpt.get().getOtherAuth();
//				switch (auth) {
//				case REFERENCE:
//					element.setActionRole(ActionRole.VIEW_ONLY);
//					actionRoleRowData.add(element);
//					break;
//				case UPDATE:
//					element.setActionRole(ActionRole.EDIT);
//					actionRoleRowData.add(element);
//					break;
//				case HIDE:
//					// do NOT add to actionRoleRowData
//					break;
//				}
//			}
//		}
//		return actionRoleRowData;
//	}
//
//	/**
//	 * @param categoryCode
//	 * @param authClassItem
//	 * @param personId
//	 * @return Target: get OPTIONAL data with definition-items for item which
//	 *         have type is LIST
//	 */
//	private Map<String, List<LayoutPersonInfoValueDto>> getPersDataOptionalForListClsItem(String categoryCode,
//			LayoutPersonInfoClsDto authClassItem, String personId) {
//		// ドメインモデル「個人情報カテゴリデータ」を取得する
//		Map<String, List<LayoutPersonInfoValueDto>> resultMap = new HashMap<>();
//		List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(personId,
//				authClassItem.getPersonInfoCategoryID());
//		perInfoCtgDatas.forEach(perInfoCtgData -> {
//			List<PersonInfoItemData> dataItems = perInItemDataRepo
//					.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
//			List<LayoutPersonInfoValueDto> ctgDataList = new ArrayList<>();
//			for (PerInfoItemDefDto item : authClassItem.getListItemDf()) {
//
//				Optional<PersonInfoItemData> dataItemOpt = dataItems.stream()
//						.filter(dataItem -> dataItem.getPerInfoItemDefId() == item.getId()).findFirst();
//				if (dataItemOpt.isPresent()) {
//					PersonInfoItemData data = dataItemOpt.get();
//					Object value = null;
//					switch (data.getDataState().getDataStateType()) {
//					case String:
//						value = data.getDataState().getStringValue();
//						break;
//					case Numeric:
//						value = data.getDataState().getNumberValue().intValue();
//						break;
//					case Date:
//						value = data.getDataState().getDateValue();
//						break;
//					}
//					if (value != null) {
//						//ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, value));
//					}
//				} else {
//					//ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, null));
//				}
//			}
//			resultMap.put(perInfoCtgData.getRecordId(), ctgDataList);
//		});
//		return resultMap;
//	}
//
//	/**
//	 * @param categoryCode
//	 * @param authClassItem
//	 * @param employeeId
//	 * @return Target
//	 */
//	private Map<String, List<LayoutPersonInfoValueDto>> getEmpDataForListClsItem(String categoryCode,
//			LayoutPersonInfoClsDto authClassItem, String employeeId) {
//		Map<String, List<LayoutPersonInfoValueDto>> resultMap = new HashMap<>();
//		List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(employeeId,
//				authClassItem.getPersonInfoCategoryID());
//		empInfoCtgDatas.forEach(perInfoCtgData -> {
//			List<EmpInfoItemData> dataItems = empInItemDataRepo.getAllInfoItemByRecordId(perInfoCtgData.getRecordId());
//			List<LayoutPersonInfoValueDto> ctgDataList = new ArrayList<>();
//			authClassItem.getListItemDf().forEach(item -> {
//				Optional<EmpInfoItemData> dataItemOpt = dataItems.stream()
//						.filter(dataItem -> dataItem.getPerInfoDefId() == item.getId()).findFirst();
//				if (dataItemOpt.isPresent()) {
//					EmpInfoItemData data = dataItemOpt.get();
//					Object value = null;
//					switch (data.getDataState().getDataStateType()) {
//					case String:
//						value = data.getDataState().getStringValue();
//						break;
//					case Numeric:
//						value = data.getDataState().getNumberValue().intValue();
//						break;
//					case Date:
//						value = data.getDataState().getDateValue();
//						break;
//					}
//					if (value != null) {
//						//ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, value));
//					}
//				} else {
//					//ctgDataList.add(LayoutPersonInfoValueDto.initData(categoryCode, item, null));
//				}
//
//			});
//			resultMap.put(perInfoCtgData.getRecordId(), ctgDataList);
//		});
//		return resultMap;
//	}
//
//
//}
