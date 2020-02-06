package nts.uk.ctx.pereg.app.find.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.PerEmpData;
import nts.uk.ctx.pereg.app.find.common.AnnLeaEmpBasicInfo;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.common.GetSPHolidayNextGrantDate;
import nts.uk.ctx.pereg.app.find.common.GetYearHolidayInfo;
import nts.uk.ctx.pereg.app.find.common.NextTimeEventDto;
import nts.uk.ctx.pereg.app.find.common.SpecialleaveInformation;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMainCategoryDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.CodeName;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeInfoDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.GridComboBoxSettingQuery;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregGridQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;

@Stateless
public class GridPeregProcessor {
//	@Inject
//	private ManagedParallelWithContext parallel;

	@Inject
	private PeregProcessor layoutProcessor;

	@Inject
	private PerInfoItemDefRepositoty perItemRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefForLayoutFinder itemForLayoutFinder;

	@Inject
	private EmployeeDataMngInfoRepository employeeMngRepo;
	
	@Inject
	private ComboBoxRetrieveFactory cbxFactory;
	
	@Inject
	private PerInfoItemDefFinder itemFinder;
	
	@Inject 
	private PerInfoCategoryFinder ctgFinder;
	
	@Inject
	private GetSPHolidayNextGrantDate getSPHolidayNextGrantDate;
	
	@Inject 
	private GetYearHolidayInfo getYearHolidayInfo;
	
	//chứa cả CS00024 - 年休情報
	private static final List<String>  specialCode = Arrays.asList("CS00024", "CS00025", "CS00026", "CS00027", "CS00028", "CS00029",
			"CS00030", "CS00031", "CS00032", "CS00033", "CS00034", "CS00049", "CS00050", "CS00051", "CS00052",
			"CS00053", "CS00054", "CS00055", "CS00056", "CS00057", "CS00058");
	
	//特別休暇付与基準日
	private static final List<String> grantDateLst =  Arrays.asList("IS00279", "IS00295","IS00302","IS00309","IS00316","IS00323","IS00330","IS00337","IS00344","IS00351","IS00358","IS00559","IS00566","IS00573","IS00580","IS00587","IS00594","IS00601","IS00608","IS00615","IS00622");
	//付与設定
	private static final List<String> appSetLst = Arrays.asList("IS00297","IS00304","IS00311","IS00318","IS00325","IS00332","IS00339","IS00346","IS00353","IS00360","IS00561","IS00568","IS00575","IS00582","IS00589","IS00596","IS00603","IS00610","IS00617","IS00624");
	//付与日数
	private static final List<String> grantDayLst = Arrays.asList("IS00298","IS00305","IS00312","IS00319","IS00326","IS00333","IS00340","IS00347","IS00354","IS00361","IS00562","IS00569","IS00576","IS00583","IS00590","IS00597","IS00604","IS00611","IS00618","IS00625");
	//特休付与テーブルコード
	private static final List<String> grantTableLst = Arrays.asList("IS00280", "IS00299","IS00306","IS00313","IS00320","IS00327","IS00334","IS00341","IS00348","IS00355","IS00362","IS00563","IS00570","IS00577","IS00584","IS00591","IS00598","IS00605","IS00612","IS00619","IS00626");
	//次回付与日 - nextTimeGrantDays
	private static final List<String> nextGrantDateLst = Arrays.asList("IS00281", "IS00300","IS00307","IS00314","IS00321","IS00328","IS00335","IS00342","IS00349","IS00356","IS00363","IS00564","IS00571","IS00578","IS00585","IS00592","IS00599","IS00606","IS00613","IS00620","IS00627");
	//次回年休付与日数 - nextTimeGrantDays IS00282
	private static final List<String> nextGrantDayLst = Arrays.asList("IS00282");
	// 次回時間年休付与上限 - nextTimeMaxTime
	private static final List<String> nextTimeMaxTimeLst = Arrays.asList("IS00283");
	
	
	public GridEmployeeDto getGridLayout(PeregGridQuery query) {
		// app context
		LoginUserContext loginUser = AppContexts.user();
		String contractCode = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();

		GridEmployeeDto geDto = new GridEmployeeDto(query.getCategoryId(), query.getStandardDate(), Arrays.asList(),
				Arrays.asList());
		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();

		// get header
		{
			// map PersonInfoItemDefinition → GridEmpHead
			List<GridEmpHead> headers = getPerItemDefForLayout(perInfoCtg, contractCode, roleId).stream()
					.map(m -> new GridEmpHead(m.getId(), m.getDispOrder(), m.getItemCode(), m.getItemParentCode(),
							m.getItemName(), m.getItemTypeState(), m.getIsRequired() == 1, m.getResourceId(),
							m.getLstChildItemDef().stream()
							.sorted(Comparator.comparing(PerInfoItemDefDto::getItemCode, Comparator.naturalOrder()))
									.map(c -> new GridEmpHead(c.getId(), m.getDispOrder(), c.getItemCode(),
											c.getItemParentCode(), c.getItemName(), c.getItemTypeState(),
											c.getIsRequired() == 1, c.getResourceId(), null))
									.collect(Collectors.toList())))
					.sorted(Comparator.comparing(GridEmpHead::getItemOrder, Comparator.naturalOrder()).thenComparing(GridEmpHead::getItemCode, Comparator.naturalOrder()))
					.collect(Collectors.toList());

			headers.addAll(headers.stream().flatMap(m -> m.getChilds().stream()).collect(Collectors.toList()));
			geDto.setHeadDatas(headers.stream()
					.map(m -> new GridEmpHead(m.getItemId(), m.getItemOrder(), m.getItemCode(), m.getItemParentCode(),
							m.getItemName(), m.getItemTypeState(), m.isRequired(), m.getResourceId(), null))
					.collect(Collectors.toList()));
		}

		// get body
		if (!CollectionUtil.isEmpty(query.getLstEmployee())) {
			
			List<PerEmpData> personDatas = employeeMngRepo.getEmploymentInfos(query.getLstEmployee(), query.getStandardDate());
			
			PeregQueryByListEmp lquery = PeregQueryByListEmp.createQueryLayout(perInfoCtg.getPersonInfoCategoryId(),
					perInfoCtg.getCategoryCode().v(), query.getStandardDate(),
					personDatas.stream().map(m -> new PeregEmpInfoQuery(m.getPersonId(), m.getEmployeeId())).collect(Collectors.toList()));
			
			List<EmpMainCategoryDto> layouts = layoutProcessor.getCategoryDetailByListEmp(lquery, true);
			
			if(specialCode.contains(query.getCategoryCode())) {
				if(query.getCategoryCode().equals("CS00024")) {
					//アルゴリズム「次回年休情報を取得する」
					List<AnnLeaEmpBasicInfo> params = layouts.stream().map(c ->  {
						GeneralDate standardDate =null;
						String grantTable = null;
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> grantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantTableOpt = c.getItems().stream().filter(item -> grantTableLst.contains(item.getItemCode())).findFirst();
						if(grantDateOpt.isPresent()) {
							standardDate = (GeneralDate) grantDateOpt.get().getValue();
						}
						
						if(grantTableOpt.isPresent()) {
							grantTable = (String) grantTableOpt.get().getValue();
						}
						return new AnnLeaEmpBasicInfo(c.getEmployeeId(),
								standardDate, grantTable,
								new DatePeriod(null, null), null, null);
					}).collect(Collectors.toList());
					
					Map<String, NextTimeEventDto> yearHolidayMap = getYearHolidayInfo.getAllYearHolidayInfoBySids(params.stream().filter(c -> c.getGrantDate() != null).collect(Collectors.toList()));
					layouts.stream().forEach(c ->{
						//nextGrantDateLst
						Optional<GridEmpBody> nextTimeGrantDateOpt = c.getItems().stream().filter(item -> nextGrantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> nextTimeGrantDaysOpt = c.getItems().stream().filter(item -> nextGrantDayLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> nextTimeMaxTimeOpt = c.getItems().stream().filter(item -> nextTimeMaxTimeLst.contains(item.getItemCode())).findFirst();
						
						NextTimeEventDto nextTimeEventDto = yearHolidayMap.get(c.getEmployeeId());
						if(nextTimeGrantDateOpt.isPresent()) {
							nextTimeGrantDateOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeGrantDate());
						}
						
						if(nextTimeGrantDaysOpt.isPresent()) {
							nextTimeGrantDaysOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeGrantDays());
						}
						
						if(nextTimeMaxTimeOpt.isPresent()) {
							nextTimeMaxTimeOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeMaxTime());
						}
					});	
					
				}else {
					List<SpecialleaveInformation> params = layouts.stream().map(c ->  {
						GeneralDate grantDate =null;
						int appSet = 0;
						Double grantDays = null;
						String grantTable = null;
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> grantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> appSetOpt = c.getItems().stream().filter(item -> appSetLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantDayOpt = c.getItems().stream().filter(item -> grantDayLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantTableOpt = c.getItems().stream().filter(item -> grantTableLst.contains(item.getItemCode())).findFirst();
						if(grantDateOpt.isPresent()) {
							grantDate = (GeneralDate) grantDateOpt.get().getValue();
						}
						if(grantDate == null) return null;
						if(appSetOpt.isPresent()) {
							appSet = Integer.valueOf((String) appSetOpt.get().getValue()).intValue();
						}
						
						if(grantDayOpt.isPresent()) {
							if(grantDayOpt.get().getValue() instanceof Integer) {
								grantDays = new Double (grantDayOpt.get().getValue().toString());
							}
							
							if(grantDayOpt.get().getValue() instanceof Double) {
								grantDays = (Double) grantDayOpt.get().getValue();
							}
							
							if(grantDayOpt.get().getValue() instanceof BigDecimal) {
								grantDays = (Double) grantDayOpt.get().getValue();
							}
							
						}
						if(grantTableOpt.isPresent()) {
							grantTable = (String) grantTableOpt.get().getValue();
							if(StringUtil.isNullOrEmpty(grantTable, true)) {
								grantTable = null;
							}
						}
						return new SpecialleaveInformation(c.getEmployeeId(), getSpecialCode(query.getCategoryCode()), grantDate, appSet, grantTable, grantDays, null, null);
					}).collect(Collectors.toList());
					Map<String, GeneralDate> nextGrantDateMap = getSPHolidayNextGrantDate.getAllSPHolidayGrantDateBySids(params.stream().filter(c -> c!= null).collect(Collectors.toList()));
					layouts.stream().forEach(c ->{
						//nextGrantDateLst
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> nextGrantDateLst.contains(item.getItemCode())).findFirst();
						GeneralDate nextGrantDateOpt = nextGrantDateMap.get(c.getEmployeeId());
						if(grantDateOpt.isPresent()) {
							grantDateOpt.get().setValue(nextGrantDateOpt == null? null: nextGrantDateOpt.toString());
						}
					});					
					
				}

			}
						
			if (!CollectionUtil.isEmpty(layouts)) {
				List<GridEmployeeInfoDto> resultsSync = Collections.synchronizedList(new ArrayList<>());
				
				query.getLstEmployee().stream().forEach(c ->{
					List<EmpMainCategoryDto> layoutOpt = layouts.stream().filter(x -> x.getEmployeeId().equals(c)).collect(Collectors.toList());
					if(CollectionUtil.isEmpty(layoutOpt)) return;
					layoutOpt.stream().forEach(l ->{
						personDatas.stream().filter(p -> p.getEmployeeId().equals(l.getEmployeeId())).findFirst()
						.ifPresent(dto -> {
							GridEmployeeInfoDto syncDto = new GridEmployeeInfoDto(dto.getPersonId(),
									dto.getEmployeeId(), new CodeName(dto.getEmployeeCode(), dto.getEmployeeName()),
									dto.getEmployeeBirthday(),
									new CodeName(dto.getDepartmentCode(), dto.getDepartmentName()),
									new CodeName(dto.getWorkplaceCode(), dto.getWorkplaceName()),
									new CodeName(dto.getPositionCode(), dto.getPositionName()),
									new CodeName(dto.getEmploymentCode(), dto.getEmploymentName()),
									new CodeName(dto.getClassificationCode(), dto.getClassificationName()),
									l.getItems());

							resultsSync.add(syncDto);
						});
					});
				});	
				
				geDto.setBodyDatas(new ArrayList<>(resultsSync));
			}
		}

		return geDto;
	}

	public List<PerInfoItemDefForLayoutDto> getPerItemDefForLayout(PersonInfoCategory category, String contractCode,
			String roleId) {

		// get per info item def with order
		List<PersonInfoItemDefinition> fullItemDefinitionList = perItemRepo
				.getAllItemDefByCategoryId(category.getPersonInfoCategoryId(), contractCode);

		List<PersonInfoItemDefinition> parentItemDefinitionList = fullItemDefinitionList.stream()
				.filter(item -> item.haveNotParentCode()).collect(Collectors.toList());

		List<PerInfoItemDefForLayoutDto> lstReturn = new ArrayList<>();

		Map<String, PersonInfoItemAuth> mapItemAuth = itemAuthRepo
				.getAllItemAuth(roleId, category.getPersonInfoCategoryId()).stream()
				.collect(Collectors.toMap(e -> e.getPersonItemDefId(), e -> e));

		for (int i = 0; i < parentItemDefinitionList.size(); i++) {
			PersonInfoItemDefinition itemDefinition = parentItemDefinitionList.get(i);

			// check authority
			PersonInfoItemAuth personInfoItemAuth = mapItemAuth.get(itemDefinition.getPerInfoItemDefId());

			if (personInfoItemAuth == null) {
				continue;
			}
			
			if(personInfoItemAuth.getSelfAuth() == PersonInfoAuthType.HIDE
					&& personInfoItemAuth.getOtherAuth() == PersonInfoAuthType.HIDE) {
				continue;
			}
			// convert item-definition to layoutDto
			PerInfoItemDefForLayoutDto itemDto = itemForLayoutFinder.createItemLayoutDto(category, itemDefinition, i,
					ActionRole.EDIT);

			// get and convert childrenItems
			List<PerInfoItemDefForLayoutDto> childrenItems = itemForLayoutFinder
					.getChildrenItems(fullItemDefinitionList, category, itemDefinition, i, ActionRole.EDIT);

			itemDto.setLstChildItemDef(childrenItems);

			lstReturn.add(itemDto);
		}

		return lstReturn;
	}

	/**
	 * Get list option for dropdownlist in CPS003F
	 * 
	 * @return
	 */
	public List<ComboBoxObject> getComboBox(GridComboBoxSettingQuery query) {

		PerInfoItemDefDto item = itemFinder.getPerInfoItemDefByIdForLayout(query.getItemId());
		PerInfoCtgFullDto ctgItem = ctgFinder.getPerInfoCtg(item.getPerInfoCtgId());

		try {
			SingleItemDto sidto = (SingleItemDto) item.getItemTypeState();
			SelectionItemDto slidto = (SelectionItemDto) sidto.getDataTypeState();

			return cbxFactory.getComboBox(slidto, null, query.getBaseDate(), query.isRequired(),
					EnumAdaptor.valueOf(ctgItem.getPersonEmployeeType(), PersonEmployeeType.class), true,
					ctgItem.getCategoryCode(), null, true);
		} catch (Exception ex) {
			return Arrays.asList();
		}
	}
	
	private int getSpecialCode(String categoryCode) {
		if(categoryCode.equals("CS00025")) {
			return 1;
		}
			
		if(categoryCode.equals("CS00026")) {
			return 2;
		}
		
		if(categoryCode.equals("CS00027")) {
			return 3;
		}
		
		if(categoryCode.equals("CS00028")) {
			return 4;
		}
		
		if(categoryCode.equals("CS00029")) {
			return 5;
		}
		
		if(categoryCode.equals("CS00030")) {
			return 6;
		}
		
		if(categoryCode.equals("CS00031")) {
			return 7;
		}
		
		if(categoryCode.equals("CS00032")) {
			return 8;
		}
		
		if(categoryCode.equals("CS00033")) {
			return 9;
		}
		
		if(categoryCode.equals("CS00034")) {
			return 10;
		}
		
		if(categoryCode.equals("CS00049")) {
			return 11;
		}
		
		if(categoryCode.equals("CS00050")) {
			return 12;
		}
		
		if(categoryCode.equals("CS00051")) {
			return 13;
		}
		
		if(categoryCode.equals("CS00052")) {
			return 14;
		}
		
		if(categoryCode.equals("CS00053")) {
			return 15;
		}
		
		if(categoryCode.equals("CS00054")) {
			return 16;
		}
		
		if(categoryCode.equals("CS00055")) {
			return 17;
		}
		
		if(categoryCode.equals("CS00056")) {
			return 18;
		}
		
		if(categoryCode.equals("CS00057")) {
			return 19;
		}
		
		if(categoryCode.equals("CS00058")) {
			return 20;
		}
		
		return 0;
	}
	
	public GridEmployeeDto getGridLayoutRegister(PeregGridQuery query) {
		// app context
		LoginUserContext loginUser = AppContexts.user();
		String contractCode = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();

		GridEmployeeDto geDto = new GridEmployeeDto(query.getCategoryId(), query.getStandardDate(), Arrays.asList(),
				Arrays.asList());
		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();

		// get header
		{
			// map PersonInfoItemDefinition → GridEmpHead
			List<GridEmpHead> headers = getPerItemDefForLayout(perInfoCtg, contractCode, roleId).stream()
					.map(m -> new GridEmpHead(m.getId(), m.getDispOrder(), m.getItemCode(), m.getItemParentCode(),
							m.getItemName(), m.getItemTypeState(), m.getIsRequired() == 1, m.getResourceId(),
							m.getLstChildItemDef().stream()
							.sorted(Comparator.comparing(PerInfoItemDefDto::getItemCode, Comparator.naturalOrder()))
									.map(c -> new GridEmpHead(c.getId(), m.getDispOrder(), c.getItemCode(),
											c.getItemParentCode(), c.getItemName(), c.getItemTypeState(),
											c.getIsRequired() == 1, c.getResourceId(), null))
									.collect(Collectors.toList())))
					.sorted(Comparator.comparing(GridEmpHead::getItemOrder, Comparator.naturalOrder()).thenComparing(GridEmpHead::getItemCode, Comparator.naturalOrder()))
					.collect(Collectors.toList());

			headers.addAll(headers.stream().flatMap(m -> m.getChilds().stream()).collect(Collectors.toList()));
			geDto.setHeadDatas(headers.stream()
					.map(m -> new GridEmpHead(m.getItemId(), m.getItemOrder(), m.getItemCode(), m.getItemParentCode(),
							m.getItemName(), m.getItemTypeState(), m.isRequired(), m.getResourceId(), null))
					.collect(Collectors.toList()));
		}

		// get body
		if (!CollectionUtil.isEmpty(query.getLstEmployee())) {
			
			List<PerEmpData> personDatas = employeeMngRepo.getEmploymentInfos(query.getLstEmployee(), query.getStandardDate());
			
			PeregQueryByListEmp lquery = PeregQueryByListEmp.createQueryLayout(perInfoCtg.getPersonInfoCategoryId(),
					perInfoCtg.getCategoryCode().v(), query.getStandardDate(),
					personDatas.stream().map(m -> new PeregEmpInfoQuery(m.getPersonId(), m.getEmployeeId())).collect(Collectors.toList()));
			
			List<EmpMainCategoryDto> layouts = layoutProcessor.getCategoryDetailByListEmp(lquery, false);
			
			if(specialCode.contains(query.getCategoryCode())) {
				if(query.getCategoryCode().equals("CS00024")) {
					//アルゴリズム「次回年休情報を取得する」
					List<AnnLeaEmpBasicInfo> params = layouts.stream().map(c ->  {
						GeneralDate standardDate =null;
						String grantTable = null;
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> grantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantTableOpt = c.getItems().stream().filter(item -> grantTableLst.contains(item.getItemCode())).findFirst();
						if(grantDateOpt.isPresent()) {
							standardDate = (GeneralDate) grantDateOpt.get().getValue();
						}
						
						if(grantTableOpt.isPresent()) {
							grantTable = (String) grantTableOpt.get().getValue();
						}
						return new AnnLeaEmpBasicInfo(c.getEmployeeId(),
								standardDate, grantTable,
								new DatePeriod(null, null), null, null);
					}).collect(Collectors.toList());
					
					Map<String, NextTimeEventDto> yearHolidayMap = getYearHolidayInfo.getAllYearHolidayInfoBySids(params.stream().filter(c -> c.getGrantDate() != null).collect(Collectors.toList()));
					layouts.stream().forEach(c ->{
						//nextGrantDateLst
						Optional<GridEmpBody> nextTimeGrantDateOpt = c.getItems().stream().filter(item -> nextGrantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> nextTimeGrantDaysOpt = c.getItems().stream().filter(item -> nextGrantDayLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> nextTimeMaxTimeOpt = c.getItems().stream().filter(item -> nextTimeMaxTimeLst.contains(item.getItemCode())).findFirst();
						
						NextTimeEventDto nextTimeEventDto = yearHolidayMap.get(c.getEmployeeId());
						if(nextTimeGrantDateOpt.isPresent()) {
							nextTimeGrantDateOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeGrantDate());
						}
						
						if(nextTimeGrantDaysOpt.isPresent()) {
							nextTimeGrantDaysOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeGrantDays());
						}
						
						if(nextTimeMaxTimeOpt.isPresent()) {
							nextTimeMaxTimeOpt.get().setValue(nextTimeEventDto == null? null: nextTimeEventDto.getNextTimeMaxTime());
						}
					});	
					
				}else {
					List<SpecialleaveInformation> params = layouts.stream().map(c ->  {
						GeneralDate grantDate =null;
						int appSet = 0;
						Double grantDays = null;
						String grantTable = null;
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> grantDateLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> appSetOpt = c.getItems().stream().filter(item -> appSetLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantDayOpt = c.getItems().stream().filter(item -> grantDayLst.contains(item.getItemCode())).findFirst();
						Optional<GridEmpBody> grantTableOpt = c.getItems().stream().filter(item -> grantTableLst.contains(item.getItemCode())).findFirst();
						if(grantDateOpt.isPresent()) {
							grantDate = (GeneralDate) grantDateOpt.get().getValue();
						}
						if(grantDate == null) return null;
						if(appSetOpt.isPresent()) {
							appSet = Integer.valueOf((String) appSetOpt.get().getValue()).intValue();
						}
						
						if(grantDayOpt.isPresent()) {
							if(grantDayOpt.get().getValue() instanceof Integer) {
								grantDays = new Double (grantDayOpt.get().getValue().toString());
							}
							
							if(grantDayOpt.get().getValue() instanceof Double) {
								grantDays = (Double) grantDayOpt.get().getValue();
							}
							
							if(grantDayOpt.get().getValue() instanceof BigDecimal) {
								grantDays = (Double) grantDayOpt.get().getValue();
							}
							
						}
						if(grantTableOpt.isPresent()) {
							grantTable = (String) grantTableOpt.get().getValue();
							if(StringUtil.isNullOrEmpty(grantTable, true)) {
								grantTable = null;
							}
						}
						return new SpecialleaveInformation(c.getEmployeeId(), getSpecialCode(query.getCategoryCode()), grantDate, appSet, grantTable, grantDays, null, null);
					}).collect(Collectors.toList());
					Map<String, GeneralDate> nextGrantDateMap = getSPHolidayNextGrantDate.getAllSPHolidayGrantDateBySids(params.stream().filter(c -> c!= null).collect(Collectors.toList()));
					layouts.stream().forEach(c ->{
						//nextGrantDateLst
						Optional<GridEmpBody> grantDateOpt = c.getItems().stream().filter(item -> nextGrantDateLst.contains(item.getItemCode())).findFirst();
						GeneralDate nextGrantDateOpt = nextGrantDateMap.get(c.getEmployeeId());
						if(grantDateOpt.isPresent()) {
							grantDateOpt.get().setValue(nextGrantDateOpt == null? null: nextGrantDateOpt.toString());
						}
					});					
					
				}

			}
						
			if (!CollectionUtil.isEmpty(layouts)) {
				List<GridEmployeeInfoDto> resultsSync = Collections.synchronizedList(new ArrayList<>());
				
				query.getLstEmployee().stream().forEach(c ->{
					List<EmpMainCategoryDto> layoutOpt = layouts.stream().filter(x -> x.getEmployeeId().equals(c)).collect(Collectors.toList());
					if(CollectionUtil.isEmpty(layoutOpt)) return;
					layoutOpt.stream().forEach(l ->{
						personDatas.stream().filter(p -> p.getEmployeeId().equals(l.getEmployeeId())).findFirst()
						.ifPresent(dto -> {
							GridEmployeeInfoDto syncDto = new GridEmployeeInfoDto(dto.getPersonId(),
									dto.getEmployeeId(), new CodeName(dto.getEmployeeCode(), dto.getEmployeeName()),
									dto.getEmployeeBirthday(),
									new CodeName(dto.getDepartmentCode(), dto.getDepartmentName()),
									new CodeName(dto.getWorkplaceCode(), dto.getWorkplaceName()),
									new CodeName(dto.getPositionCode(), dto.getPositionName()),
									new CodeName(dto.getEmploymentCode(), dto.getEmploymentName()),
									new CodeName(dto.getClassificationCode(), dto.getClassificationName()),
									l.getItems());

							resultsSync.add(syncDto);
						});
					});
				});	
				
				geDto.setBodyDatas(new ArrayList<>(resultsSync));
			}
		}

		return geDto;
		
	}
}
