package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.ApproverStateForAppDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.AttachPersonReportFileFinder;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSetting;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSettingRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.RegistrationStatus;
import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.PerInfoItemDefImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.HumanCategoryPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgShowImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalBehaviorAtrHrExport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprRootStateHrImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApprStateHrImport;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproveRepository;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PhaseSttHrImport;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_20;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReportItemFinder {
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;

	@Inject
	private RegisterPersonalReportItemRepository itemReportClsRepo;

	@Inject
	private HumanItemPub humanItemPub;

	@Inject
	private ReportItemRepository reportItemRepo;

	@Inject
	private HumanCategoryPub humanCtgPub;
	
	@Inject
	private ReportStartSettingRepository reportStartSettingRepo;
	
	@Inject
	private RegistrationPersonReportRepository registrationPersonReportRepo;

	@Inject
	private AttachPersonReportFileFinder attachPersonReportFileFinder;

	@Inject
	private ApproveRepository approveRepository;
	
	@Inject
	private EmployeeInformationAdaptor employeeInforAdapter;
	
	private static boolean approve = true;
	
	/**
	 * ?????????Flg = false ??????????????????
???????????? *????????????Flg = true ?????????????????????
	 * ??????????????????????????????????????????????????????????????? (Th???c hi???n thu???t to??n "X??? l?? select report list")
	 * @param reportClsId
	 * @return
	 */
	public ReportLayoutDto getDetailReportCls(ReportParams params) {

		String cid = AppContexts.user().companyId();
		String appSid = "";
		String appBussinessName = "";
		
		ApprRootStateHrImport approvalStateHrImport = new ApprRootStateHrImport();
		
		approve = true;
		
		List<ApprovalPhaseStateForAppDto> appPhaseLst = new ArrayList<>();
		
		Optional<RegistrationPersonReport> registrationPersonReport = this.registrationPersonReportRepo.getDomainByReportId(cid, params.getReportId() == null ? null : Integer.valueOf(params.getReportId()));

		// ????????????????????????????????????????????????????????????????????????????????????????????????????????? ??????????????????[????????????????????????]?????????????????????
		// (Get t???t c??? domain models???type ????n xin c?? nh??n????????? Item dang ky don xin ca nhan???)
		Optional<PersonalReportClassification> reportClsOpt = Optional.empty();
		
		if(registrationPersonReport.isPresent()) {
			
			appSid = registrationPersonReport.get().getAppSid();
			appBussinessName = registrationPersonReport.get().getAppBussinessName();
			
			reportClsOpt = this.reportClsRepo.getDetailReportClsByReportClsID(cid,
					registrationPersonReport.get().getReportLayoutID());
			
			if(registrationPersonReport.get().getRegStatus() == RegistrationStatus.Registration) {
				
				getInfoApprover(registrationPersonReport.get().getRootSateId(), approvalStateHrImport, appPhaseLst);
				
			}
			
			reportClsOpt.get().setPReportName(new String_Any_20(registrationPersonReport.get().getReportName()));
			
		 } else {

			Optional<RegistrationPersonReport> domain = this.registrationPersonReportRepo.getDomain(cid, params.getReportLayoutId());
			if (domain.isPresent()) {
				appSid = domain.get().getAppSid();
				appBussinessName = domain.get().getAppBussinessName();
			}
			
			reportClsOpt = this.reportClsRepo.getDetailReportClsByReportClsID(cid, params.getReportLayoutId());
			
		}
		 
		int reportLayoutId = getReportLayoutId(params, reportClsOpt);

		//??????ID????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		Optional<ReportStartSetting> reportStartSetting = this.reportStartSettingRepo.getReportStartSettingByCid(cid);
		
		// ??????????????????????????????????????????????????????????????????????????????????????????????????????????????? (Get all domain model
		// ??????????????????????????????????????????????????????????????????)
		List<RegisterPersonalReportItem> listItemCls = this.itemReportClsRepo.getAllItemBy(cid, reportLayoutId);
		
		//???????????????????????????????????????(??????????????????[???????????????????????????????????????]???????????????)
		List<DocumentSampleDto> documentSampleDtoLst = this.attachPersonReportFileFinder.findAll(reportLayoutId, params.getReportId() == null ? null : Integer.valueOf(params.getReportId()));

		List<LayoutReportClsDto> items = mapItemCls(params, listItemCls);

		List<LayoutReportClsDto> itemInter = new ArrayList<>();

		int itemSize = items.size();

		for (int i = 0; i < itemSize; i++) {

			if (items.get(i).getLayoutItemType() == LayoutReportItemType.SeparatorLine && (i + 1) < itemSize) {

				if (items.get(i + 1).getLayoutItemType() == LayoutReportItemType.SeparatorLine) {

					i = i + 1;

				}

				itemInter.add(items.get(i));

			} else {

				itemInter.add(items.get(i));

			}
		}
		
		boolean release = approvalStateHrImport == null?false: approvalStateHrImport.getApprState() == null? false: approvalStateHrImport.getApprState().isReflectFlag();
		
		return reportClsOpt.isPresent() == true
				? ReportLayoutDto.createFromDomain(reportClsOpt.get(), reportStartSetting, registrationPersonReport,
						itemInter, documentSampleDtoLst, appPhaseLst, release, approve, appSid, appBussinessName)
				: new ReportLayoutDto();
	}
	
	/*
	 * ?????????????????????
	 */
	public void getInfoApprover(String rootInstanceId, ApprRootStateHrImport approvalStateHrImport,  List<ApprovalPhaseStateForAppDto> appPhaseLst) {
		
		ApprRootStateHrImport approvalStateHrImportResult = this.approveRepository.getApprovalRootStateHr(rootInstanceId);
		
		approvalStateHrImport.setErrorFlg(approvalStateHrImportResult.isErrorFlg());
		
		approvalStateHrImport.setApprState(approvalStateHrImportResult.getApprState());
		
		convertData(approvalStateHrImport, appPhaseLst);
		
	}
	
	private int getReportLayoutId(ReportParams params , Optional<PersonalReportClassification> reportClsOpt) {
		if(reportClsOpt.isPresent()) {
			return reportClsOpt.get().getPReportClsId();
		}
		
		return params.getReportLayoutId();
	}
	

	/**
	 * ????????????????????????????????????
	 * convert item ????? l???y th??ng tin ki???u d??? li???u c???a item ????
	 * 
	 * @param listItemCls
	 * @return
	 */
	private List<LayoutReportClsDto> mapItemCls(ReportParams params, List<RegisterPersonalReportItem> listItemCls) {
		List<LayoutReportClsDto> result = new ArrayList<>();

		String cid = AppContexts.user().companyId();

		String contractCd = AppContexts.user().contractCode();

		if (CollectionUtil.isEmpty(listItemCls))
			return new ArrayList<LayoutReportClsDto>();

		int order = 0;

		for (RegisterPersonalReportItem item : listItemCls) {

			LayoutReportClsDto classDto = new LayoutReportClsDto(item.getPReportClsId(), item.getLayoutOrder(),
					item.getCategoryId(), item.getItemType(), item.getCategoryCd(), 1, item.getCategoryName());

			switch (item.getItemType()) {

			case 0:// single item

			case 1:// list item

				if (order == item.getLayoutOrder())
					break;

				List<RegisterPersonalReportItem> itemsByOrder1 = listItemCls.stream()
						.filter(c -> c.getLayoutOrder() == item.getLayoutOrder()
								&& c.getPReportClsId() == item.getPReportClsId())
						.collect(Collectors.toList());

				order = item.getLayoutOrder();

				List<String> itemIds = itemsByOrder1.stream().map(c -> c.getItemId()).distinct()
						.collect(Collectors.toList());

				if (!CollectionUtil.isEmpty(itemIds)) {

					List<PerInfoItemDefImport> listItemDefDto = new ArrayList<PerInfoItemDefImport>();

					// ?????????????????????????????????????????????????????????????????? - L???y ?????nh ngh??a item, ??i???u ki???n
					List<PerInfoItemDefImport> listItemDef = humanItemPub.getAll(itemIds);

					listItemDef.stream().forEach(c -> {

						c.setCategoryCode(item.getCategoryCd());

						c.setCategoryName(item.getCategoryName());
					});

					for (String id : itemIds) {

						List<PerInfoItemDefImport> dto = listItemDef.stream().filter(p -> p.getId().equals(id))
								.collect(Collectors.toList());

						if (!dto.isEmpty()) {

							listItemDefDto.add(dto.get(0));
						}
					}

					List<String> roots = listItemDefDto.stream()
							.filter(f -> f.getItemParentCode().equals("") || f.getItemParentCode() == null)
							.map(m -> m.getItemCode()).collect(Collectors.toList());

					if (roots.size() > 1) {

						classDto.setListItemDf(listItemDefDto);

					} else {

						if (listItemDefDto.size() > 1) {

							if (classDto.getLayoutItemType() == LayoutReportItemType.ITEM) {

								if (listItemDefDto.get(0).getItemTypeState().getItemType() == 1
										|| listItemDefDto.get(0).getItemTypeState().getItemType() == 3) {

									classDto.setListItemDf(listItemDefDto);

								} else {

									classDto.setListItemDf(new ArrayList<PerInfoItemDefImport>());

								}

							} else if (classDto.getLayoutItemType() == LayoutReportItemType.LIST) {

								classDto.setListItemDf(listItemDefDto);

							}

						} else if (listItemDefDto.size() == 1) {

							classDto.setListItemDf(listItemDefDto);

						} else {

							classDto.setListItemDf(new ArrayList<PerInfoItemDefImport>());

						}

						if (classDto.getLayoutItemType() == LayoutReportItemType.ITEM && !listItemDefDto.isEmpty()
								&& listItemDefDto.get(0) != null) {

							classDto.setClassName(listItemDefDto.get(0).getItemName());

						}
					}
				}

				if (classDto.getLayoutItemType() == LayoutReportItemType.LIST) {

					String catDto = this.humanItemPub.getCategoryName(cid, classDto.getCategoryCode());

					if (catDto != null) {

						classDto.setClassName(catDto);

					}
				}

				result.add(classDto);

				break;
			case 2:// SeparatorLine

				result.add(classDto);

				break;
			}

		}

		Map<String, List<ReportItem>> reportItems  = new HashMap<>();
		
		if(params.getReportId() != null) {
			
			reportItems.putAll(this.reportItemRepo.getDetailReport(cid, Integer.valueOf(params.getReportId())).stream()
					.collect(Collectors.groupingBy(c -> c.getCtgCode())));
		}

		// GET DATA WITH EACH CATEGORY
		Map<String, List<LayoutReportClsDto>> classItemInCategoryMap = result.stream()
				.filter(classItem -> classItem.getLayoutItemType() != LayoutReportItemType.SeparatorLine)
				.collect(Collectors.groupingBy(LayoutReportClsDto::getPersonInfoCategoryID));

		String sid = AppContexts.user().employeeId();

		for (Entry<String, List<LayoutReportClsDto>> classItemsOfCategory : classItemInCategoryMap.entrySet()) {

			String categoryId = classItemsOfCategory.getKey();

			List<LayoutReportClsDto> classItemList = classItemsOfCategory.getValue();

			Optional<PerInfoCtgShowImport> ctgOpt = this.humanCtgPub.getCategoryByCidAndContractCd(categoryId,
					contractCd);

			if (ctgOpt.isPresent()) {

				PerInfoCtgShowImport ctg = ctgOpt.get();

				List<ReportItem> itemDatas = reportItems.get(ctg.getCategoryCode());

				switch (ctg.getCategoryType()) {
				case 1:

				case 3:

				case 4:

				case 5:

				case 6:

					// get data
					getDataforSingleItem(params, sid, ctg, itemDatas, classItemList);

					break;

				case 2:

					getDataforListItem(params, sid, ctg, classItemList.get(0), itemDatas);

					break;

				default:

					break;
				}
			}
		}

		return result;
	}

	private void mapListItemClass(List<ReportItem> itemDatas, List<LayoutReportClsDto> classItemList) {

		if (!CollectionUtil.isEmpty(itemDatas)) {
			
			classItemList.stream().forEach(c -> {

				if (!CollectionUtil.isEmpty(c.getItems())) {

					c.getItems().stream().forEach(i -> {

						Optional<ReportItem> itemDataOpt = itemDatas.stream()
								.filter(data -> data.getItemCd().equals(i.getItemCode())).findFirst();

						if (itemDataOpt.isPresent()) {

							i.setShowColor(false);

							ReportItem itemData = itemDataOpt.get();

							i.setRecordId(String.valueOf(itemData.getReportID()));

							if (itemData.getSaveDataAtr() == 1) {

								i.setValue(itemData.getStringVal());

							} else if (itemData.getSaveDataAtr() == 2) {

								i.setValue(itemData.getIntVal());

							} else {

								i.setValue(itemData.getDateVal());

							}
						}

					});
				}

			});

		}

	}

	public void matchDataToValueItems(String recordId, List<LayoutHumanInfoValueDto> valueItems,
			List<ReportItem> dataItems) {
		
		for (LayoutHumanInfoValueDto valueItem : valueItems) {
			// recordId
			valueItem.setRecordId(recordId);

			valueItem.setShowColor(false);

			// data
			for (ReportItem dataItem : dataItems) {
				
				if (valueItem.getItemCode().equals(dataItem.getItemCd())) {
					
					if (dataItem.getSaveDataAtr() == 1) {
						
						valueItem.setValue(dataItem.getStringVal());
						
					} else if (dataItem.getSaveDataAtr() == 2) {
						
						valueItem.setValue(dataItem.getIntVal());
						
					} else if (dataItem.getSaveDataAtr() == 3) {
						
						valueItem.setValue(dataItem.getDateVal());
						
					}

				}
			}
		}
	}

	/**
	 * @param perInfoCategory
	 * @param authClassItem
	 * @param standardDate
	 * @param personId
	 * @param employeeId
	 * @param query
	 */
	private void getDataforSingleItem(ReportParams params,String employeeId, PerInfoCtgShowImport perInfoCategory,
			List<ReportItem> itemDatas, List<LayoutReportClsDto> classItemList) {

		cloneDefItemToValueItem(perInfoCategory, classItemList);
		
		if(params.isScreenC()) {
			
			classItemList.stream().forEach(c ->{
				
				c.getItems().forEach(i ->{
					
					i.setActionRole(ActionRole.VIEW_ONLY);
					
				});
				
			});
		}
		
		getSingleOptionData(perInfoCategory, classItemList, itemDatas);

	}

	private void getSingleOptionData(PerInfoCtgShowImport perInfoCategory, 
			List<LayoutReportClsDto> classItemList, List<ReportItem> itemDatas) {
		
		if (itemDatas != null) {
			
			mapListItemClass(itemDatas, classItemList);
			
		}

	}
	
	private void getDataforListItem(ReportParams param, String employeeId, PerInfoCtgShowImport perInfoCategory,
			LayoutReportClsDto classItem, List<ReportItem> itemDatas) {

		classItem.setItems(new ArrayList<>());

		// get option category data
		getMultiOptionData(param, employeeId, perInfoCategory, classItem, itemDatas);

		classItem.setListItemDf(null);

		classItem.setCategoryCode(perInfoCategory.getCategoryCode());

	}

	private List<LayoutHumanInfoValueDto> convertDefItem(PerInfoCtgShowImport perInfoCategory,
			List<PerInfoItemDefImport> listItemDf) {

		return listItemDf.stream().map(itemDef -> LayoutHumanInfoValueDto.cloneFromItemDef(perInfoCategory, itemDef))
				.collect(Collectors.toList());
	}

	private void getMultiOptionData(ReportParams params, String employeeId, PerInfoCtgShowImport perInfoCategory,
			LayoutReportClsDto classItem, List<ReportItem> itemDatas) {

		classItem.getItems().addAll(convertDefItem(perInfoCategory, classItem.getListItemDf()));
		
		if (CollectionUtil.isEmpty(itemDatas)) return;

		// create new line data
		List<LayoutHumanInfoValueDto> valueItems = convertDefItem(perInfoCategory, classItem.getListItemDf());

		if(params.isScreenC()) {
			
			classItem.getItems().forEach(c ->{
				
				c.setActionRole(ActionRole.VIEW_ONLY);
				
			});
			
		}
		
		mapListItemClass(itemDatas, Arrays.asList(classItem));

//		classItem.getItems().addAll(valueItems);

	}

	private void cloneDefItemToValueItem(PerInfoCtgShowImport perInfoCategory, List<LayoutReportClsDto> classItemList) {

		for (LayoutReportClsDto classItem : classItemList) {

			List<LayoutHumanInfoValueDto> items = new ArrayList<>();

			for (PerInfoItemDefImport itemDef : classItem.getListItemDf()) {

				items.add(LayoutHumanInfoValueDto.cloneFromItemDef(perInfoCategory, itemDef));

			}

			classItem.setCategoryCode(perInfoCategory.getCategoryCode());

			classItem.setCtgType(perInfoCategory.getCategoryType());

			classItem.setListItemDf(null);

			classItem.setItems(items);

		}
	}
	
	private  void convertData(ApprRootStateHrImport apprRootState, List<ApprovalPhaseStateForAppDto> results) {
		
		String sid = AppContexts.user().employeeId();
		
		ApprStateHrImport apprState = apprRootState.getApprState();
		
		if(apprState == null) return;
		
		List<PhaseSttHrImport> lstPhaseState = apprState.getLstPhaseState();
		
		List<String> sids = new ArrayList<>();
		
		lstPhaseState.stream().forEach(c ->{
			
			c.getLstApprovalFrame().stream().forEach(app ->{
				
				List<String> appIds = app.getLstApproverInfo().stream().map(id ->{
					
					return StringUtil.isNullOrEmpty(id.getAgentID(), true)== true? id.getApproverID(): id.getAgentID();
					
				}).collect(Collectors.toList());
				
				sids.addAll(appIds);			
				
			});
			
		});
		
		//?????????????????? [??????????????????????????????] ??????????????? (th???c hi???n thu???t to??n [l???y th??ng tin employee]) --- 
		
		EmployeeInfoQueryImport paramApproverId = EmployeeInfoQueryImport.builder()
				
				.employeeIds(new ArrayList<String>(sids))
				
				.referenceDate(GeneralDate.today()) 
				
				.toGetWorkplace(false)
				
				.toGetDepartment(false)
				
				.toGetPosition(false)
				
				.toGetEmployment(false)
				
				.toGetClassification(false)
				
				.toGetEmploymentCls(false).build();
		
		Map<String, List<EmployeeInformationImport>> employeeInfoMaps = employeeInforAdapter.find(paramApproverId)
				
				.stream()
				
				.collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		
		
		List<ApprovalPhaseStateForAppDto> appDtoLst = lstPhaseState.stream().map(c ->{
			
			ApprovalPhaseStateForAppDto dto = ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(c, employeeInfoMaps);
			
			return dto;
			
		}).collect(Collectors.toList());
		
		appDtoLst.sort(Comparator.comparing(ApprovalPhaseStateForAppDto::getPhaseOrder));
		
		results.addAll(appDtoLst);
		
		results.stream().forEach(c ->{
			
			c.getListApprovalFrame().stream().forEach(f ->{
				
				Optional<ApproverStateForAppDto> appr = f.getListApprover().stream().filter(i -> i.getApproverID().equals(sid) && i.getApprovalAtrValue().equals(new Integer(ApprovalBehaviorAtrHrExport.APPROVED.value))).findFirst();
				
				if(appr.isPresent()) {
					
					this.approve = false;
				}
			});
		});
	}
}
