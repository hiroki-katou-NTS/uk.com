package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypeSortedFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypesFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedDto;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.EmployeeRoleDto;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.EmploymentRoleFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.app.find.scherec.attitem.AttItemFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.bs.employee.app.find.employment.EmploymentFinder;
import nts.uk.ctx.bs.employee.app.find.employment.dto.EmploymentDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author Hoidd
 *
 */
@Stateless
@DomainID(value = "DailyExport")
public class RoleDailyExportExcelImpl implements MasterListData {

	//sheet1
	@Inject
	private AtItemNameAdapter atItemNameAdapter;
	
	@Inject
	private EmploymentRoleFinder employmentRoleFinder; 
	
	@Inject
	private AttItemFinder attfinder; //sheet 2 dung chung
	
	//sheet 2 
	@Inject
	private ControlOfAttItemsRepoExcel controlOfItemlExcel;
	//sheet 3
	@Inject
	private BusinessTypesFinder findAll;
	@Inject
	private BusinessTypeFormatMonthlyRepository finAllMonthly;
	@Inject
	private BusinessDailyRepo businessDailyRepo;
	//sheet5
	@Inject
	private BusinessTypeSortedFinder businessTypeSortedFinder;
	//sheet 6
	@Inject
	private EmploymentFinder finderEmp;
	@Inject
	private WorkTypeGroupExcel workTypeGroup;
	//sheet 1
	List<EmployeeRoleDto> listEmployeeRoleDto ;
	List<AttItemName> listAttItemNameNoAuth;
	List<AttItemName> listAttItemNameWithAuth;
	//sheet 2
	Map<String,List<AttItemName>> authSeting = new HashMap<>();
	Map<Integer, ControlOfAttendanceItemsDtoExcel> listConItem = new HashMap<>();
	//sheet 3 
	List<BusinessTypeDto> listBusinessType;
	List<BusinessTypeFormatMonthly> listBusinessMonthly;
	List<AttItemName> listAttItemNameMonthly;
	Map<Integer, AttItemName> mapAttNameMonthlys = new HashMap<>();
	Map<BusinessTypeCode,List<BusinessTypeFormatMonthly>> mapMonthlyBz = new HashMap<>();
	Map<String, Map<Integer, List<BusinessDailyExcel>>> maplistBzDaily = new HashMap<>();
	Map<Integer, AttItemName> mapAttNameDailys = new HashMap<>();
	String companyId = "";
	//sheet 5
	List<BusinessTypeSortedDto> listBzTypeSort = new ArrayList<>();
	//sheet 6
	List<EmploymentDto> listEmp = new ArrayList<>();
	Map<String, Map<Integer, List<WorkTypeDtoExcel>>> mapTypeByEmpAndGroup = new HashMap<>();
	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		companyId = AppContexts.user().companyId();
		initSheet1();
		initSheet2();
		initSheet3();
		initSheet5();
		initSheet6();
		// add the work place sheet
		List<SheetData> sheetDatas = new ArrayList<>();
		SheetData sheet2 = new SheetData(getMasterDatasSheet2(query),
				getHeaderColumnsSheet2(query), null, null, "sheet2");
		sheetDatas.add(sheet2);
		SheetData sheet3 = new SheetData(getMasterDatasSheet3(query),
				getHeaderColumnsSheet3(query), null, null, "sheet3");
		sheetDatas.add(sheet3);
//		SheetData sheet5 = new SheetData(getMasterDatasSheet5(query),
//				getHeaderColumnsSheet5(query), null, null, "sheet5");
//		sheetDatas.add(sheet5);
		
		SheetData sheet6 = new SheetData(getMasterDatasSheet6(query),
				getHeaderColumnsSheet6(query), null, null, "sheet6");
		sheetDatas.add(sheet6);
		return sheetDatas;
	}

	
	private void initSheet6() {
		listEmp = finderEmp.findAll();
		listEmp.sort(Comparator.comparing(EmploymentDto::getCode));
		mapTypeByEmpAndGroup = workTypeGroup.getAllWorkType();
	}


	private void initSheet5() {
		listBzTypeSort = businessTypeSortedFinder.findAll();
		listBzTypeSort.sort(Comparator.comparing(BusinessTypeSortedDto::getDislayNumber));
		
	}


	private void initSheet3() {
		listBusinessType = findAll.findAll(); // 
		listBusinessMonthly = finAllMonthly.getMonthlyDetailByCompanyId(companyId);
		mapMonthlyBz= listBusinessMonthly.stream().collect(
			      Collectors.groupingBy(BusinessTypeFormatMonthly::getBusinessTypeCode, HashMap::new, Collectors.toCollection(ArrayList::new))
			    );
		listAttItemNameMonthly = atItemNameAdapter.getNameOfAttdItemByType(EnumAdaptor.valueOf(2, TypeOfItemImport.class));
		mapAttNameMonthlys =
				listAttItemNameMonthly.stream().collect(Collectors.toMap(AttItemName::getAttendanceItemId,
						Function.identity()));
		
		//Daily
		maplistBzDaily = businessDailyRepo.getAllByComp(companyId);
		mapAttNameDailys = listAttItemNameNoAuth.stream().collect(Collectors.toMap(AttItemName::getAttendanceItemId,
				Function.identity()));
		
	}


	private void initSheet2() {
		 listConItem = controlOfItemlExcel.getAllByCompanyId(companyId);
	}


	private void initSheet1() {
		listEmployeeRoleDto =  employmentRoleFinder.findEmploymentRoles();
		listEmployeeRoleDto.sort(Comparator.comparing(EmployeeRoleDto::getRoleCode));
		listAttItemNameNoAuth = atItemNameAdapter.getNameOfAttdItemByType(EnumAdaptor.valueOf(1, TypeOfItemImport.class));//sheet 3 dung chung
		listAttItemNameNoAuth.sort(Comparator.comparing(AttItemName::getAttendanceItemDisplayNumber));
		
		for (EmployeeRoleDto employeeRoleDto : listEmployeeRoleDto) {
			listAttItemNameWithAuth = attfinder.getDailyAttItemById(employeeRoleDto.getRoleId());
			authSeting.put(employeeRoleDto.getRoleId(), listAttItemNameWithAuth);
		}

	}


	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("コード2", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("利用区分", TextResource.localize("KML004_53"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("本人修正設定", TextResource.localize("KML004_53"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("本人以外修正設定", TextResource.localize("KML004_53"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listEmployeeRoleDto)) {
			throw new BusinessException("Msg_393");
		} else {
			listEmployeeRoleDto.stream().forEach(c -> {
				List<AttItemName> attItemNamesAuthSet = authSeting.get(c.getRoleId());
				Map<Integer, AttItemName> result =
						attItemNamesAuthSet.stream().collect(Collectors.toMap(AttItemName::getAttendanceItemId,
					                                              Function.identity()));
				attItemNamesAuthSet.sort(Comparator.comparing(AttItemName::getAttendanceItemDisplayNumber));
				Map<String, Object> data = new HashMap<>();
				Map<String, Object> dataChild = new HashMap<>();
				putDataEmpty(data);
				putDataEmpty(dataChild);
				data.put("コード", c.getRoleCode());
				data.put("名称", c.getRoleName());
				if(!CollectionUtil.isEmpty(listAttItemNameNoAuth)){
					if(listAttItemNameNoAuth.size()==1){
						
						AttItemName  attItemName = listAttItemNameNoAuth.get(0);
						data.put("コード2", attItemName.getAttendanceItemDisplayNumber());
						data.put("項目", attItemName.getAttendanceItemName());
						//putAthSeting
						AttItemAuthority attItemAuthor = result.get(attItemName.getAttendanceItemId()).getAuthority();
						putAuthor(data,attItemAuthor,attItemName.getUserCanUpdateAtr());
						alignMasterDataSheetRoleDaily(data);
						datas.add(alignMasterDataSheetRoleDaily(data)); 
						putDataEmpty(data);
					}else {
						AttItemName  attItemName = listAttItemNameNoAuth.get(0);
						data.put("コード2", attItemName.getAttendanceItemDisplayNumber());
						data.put("項目", attItemName.getAttendanceItemName());
						//putAthSeting
						if(result.get(attItemName.getAttendanceItemId())!=null  ){
							AttItemAuthority attItemAuthor = result.get(attItemName.getAttendanceItemId()).getAuthority();
							putAuthor(data,attItemAuthor,attItemName.getUserCanUpdateAtr());
						}
						datas.add(alignMasterDataSheetRoleDaily(data));
						putDataEmpty(data);
						for(int i = 1; i < listAttItemNameNoAuth.size() ; i++){
							putDataEmpty(dataChild);
							attItemName = new AttItemName();
							attItemName = listAttItemNameNoAuth.get(i);
							dataChild.put("コード2", attItemName.getAttendanceItemDisplayNumber());
							dataChild.put("項目", attItemName.getAttendanceItemName());
							//putAthSeting
							if(result.get(attItemName.getAttendanceItemId())!=null){
								AttItemAuthority attItemAuthor = result.get(attItemName.getAttendanceItemId()).getAuthority();
								putAuthor(dataChild,attItemAuthor,attItemName.getUserCanUpdateAtr());
							}
							datas.add(alignMasterDataSheetRoleDaily(dataChild));
						}
					}
					
				}
			});
		}
		return datas;
	}
	
	private void putAuthor(Map<String, Object> data, AttItemAuthority attItemAuthor, int isCanUpDate) {
		if(attItemAuthor.isToUse()){
		data.put("利用区分", "○");
			if(isCanUpDate == 1){
				if(attItemAuthor.isYouCanChangeIt()){
					data.put("本人修正設定", "○" );
				}else {
					data.put("本人修正設定", "-" );
				}
				if(attItemAuthor.isCanBeChangedByOthers()){
					data.put("本人以外修正設定", "○" );
				}else {
					data.put("本人以外修正設定", "-" );
				}
			}
		}else {
			data.put("利用区分", "-");
		}
		
	}

	@Override
	public String mainSheetName() {
		return "マスタリスト";
	}
	
	private void putDataEmpty(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("コード2","");
		data.put("項目","");
		data.put("利用区分","");
		data.put("本人修正設定","");
		data.put("本人以外修正設定","");
	}
	private MasterData alignMasterDataSheetRoleDaily(Map<String, Object> data) {
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("コード2").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("利用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("本人修正設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("本人以外修正設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		return masterData;
	}
	
	//sheet 2 
	public List<MasterHeaderColumn> getHeaderColumnsSheet2(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KML004_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("ヘッダー色", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("丸め単位", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterData> getMasterDatasSheet2(MasterListExportQuery query) {
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listAttItemNameNoAuth)) {
			throw new BusinessException("Msg_393");
		} else {
			listAttItemNameNoAuth.stream().forEach(c -> {
				ControlOfAttendanceItemsDtoExcel controlItem = listConItem.get(c.getAttendanceItemId());
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet2(data);
			
				data.put("コード", c.getAttendanceItemDisplayNumber());
				data.put("項目", c.getAttendanceItemName());
				if(controlItem!=null){
					if(controlItem.getHeaderBgColorOfDailyPer()!=null){
						data.put("ヘッダー色", controlItem.getHeaderBgColorOfDailyPer());
					}
					TimeInputUnit timeInputUnit = EnumAdaptor.valueOf(controlItem.getInputUnitOfTimeItem()==null?0:controlItem.getInputUnitOfTimeItem(), TimeInputUnit.class);
					data.put("丸め単位", timeInputUnit.nameId);
				}else{
					data.put("ヘッダー色", "");
					data.put("丸め単位", TimeInputUnit.TIME_INPUT_1Min.nameId);
				}
				datas.add(alignMasterDataSheet2(data));
				
			});
		}
		return datas;
	}
	
	private void putDataEmptySheet2(Map<String, Object> data){
		data.put("コード","");
		data.put("項目","");
		data.put("ヘッダー色","");
		data.put("丸め単位","");
	}
	private MasterData alignMasterDataSheet2(Map<String, Object> data) {
		/**
		 * 	TIME_INPUT_1Min(0, "1分"),
		TIME_INPUT_5Min(1, "5分"),
		TIME_INPUT_10Min(2, "10分"),
		TIME_INPUT_15Min(3, "15分"),
		TIME_INPUT_30Min(4, "30分"),
		TIME_INPUT_50Min(5, "60分");
		 */
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("ヘッダー色").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("ヘッダー色").setStyle(MasterCellStyle.build().backgroundColor((data.get("ヘッダー色").toString())));
		masterData.cellAt("丸め単位").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		return masterData;
	}
	
	//sheet 3
public List<MasterHeaderColumn> getHeaderColumnsSheet3(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("日次項目 Sheet選択", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("日次項目 名称", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("日次項目 項目", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("月次項目", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterData> getMasterDatasSheet3(MasterListExportQuery query) {
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listBusinessType)) {
			throw new BusinessException("Msg_393");
		} else {
			listBusinessType.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet3(data);
				
				data.put("コード", c.getBusinessTypeCode());
				data.put("名称", c.getBusinessTypeName());
				BusinessTypeCode businessTypeCode = new BusinessTypeCode(c.getBusinessTypeCode());
				List<BusinessTypeFormatMonthly> businessTypeFormatMonthly = mapMonthlyBz.get(businessTypeCode);
				List<Integer> keyMonthly = Collections.emptyList();
				if(!CollectionUtil.isEmpty(businessTypeFormatMonthly)){
				 keyMonthly = 
						businessTypeFormatMonthly.stream()
					              .map(BusinessTypeFormatMonthly::getAttendanceItemId)
					              .collect(Collectors.toList());
				}
				List<String> codeAndNameMonthly = new ArrayList<>();
				AttItemName attenNameMonthly = new AttItemName();
				for (Integer key : keyMonthly) {
					attenNameMonthly =!CollectionUtil.isEmpty(businessTypeFormatMonthly)? mapAttNameMonthlys.get(key):null;
					if(attenNameMonthly!=null)
					codeAndNameMonthly.add(attenNameMonthly.getAttendanceItemDisplayNumber()+attenNameMonthly.getAttendanceItemName());
				}
				String listCodeAndNameMonthly = String.join(",", codeAndNameMonthly);
				data.put("月次項目", listCodeAndNameMonthly);
				//put Daily
				Map<Integer, List<BusinessDailyExcel>> mapListBzDaily = maplistBzDaily.get(c.getBusinessTypeCode());
				List<Integer> listKeyBzDailyBySheetNo  = new ArrayList<>();
				if(mapListBzDaily!=null){
					listKeyBzDailyBySheetNo = new ArrayList<Integer>(mapListBzDaily.keySet());
				}
				Collections.sort(listKeyBzDailyBySheetNo);
				if(!CollectionUtil.isEmpty(listKeyBzDailyBySheetNo)){
					int check = 0;
					for (int keyBySheetNo : listKeyBzDailyBySheetNo) {
						List<BusinessDailyExcel> listBzDailyExcel = mapListBzDaily.get(keyBySheetNo);
						Map<String, Object> dataChil = new HashMap<>();
						putDataEmptySheet3(dataChil);
						if(check==0){
							data.put("日次項目 Sheet選択", keyBySheetNo);
							data.put("日次項目 名称", listBzDailyExcel==null?"":listBzDailyExcel.get(0).getSheetName());
							List<Integer> keyDaily = Collections.emptyList();
							if(!CollectionUtil.isEmpty(listBzDailyExcel)){
								keyDaily = 
										listBzDailyExcel.stream()
								              .map(BusinessDailyExcel::getAttItemId)
								              .collect(Collectors.toList());
							}
							List<String> codeAndNameDaily = new ArrayList<>();
//							AttItemName attenNameDaily = new AttItemName();
							String duplicateStringName = "";
							for (Integer key : keyDaily) {
								AttItemName attenNameDaily =!CollectionUtil.isEmpty(listBzDailyExcel)? mapAttNameDailys.get(key):null;
								if(attenNameDaily!=null && !duplicateStringName.equals(attenNameDaily.getAttendanceItemDisplayNumber()+attenNameDaily.getAttendanceItemName())){
									duplicateStringName = attenNameDaily.getAttendanceItemDisplayNumber()+attenNameDaily.getAttendanceItemName();
									codeAndNameDaily.add(duplicateStringName);
								}
							}
							String listItemDaily = String.join(",", codeAndNameDaily);
							data.put("日次項目 項目", listItemDaily);
							datas.add(alignMasterDataSheet3(data));
							check++;
						}else {
							dataChil.put("日次項目 Sheet選択", keyBySheetNo);
							List<BusinessDailyExcel> value = listBzDailyExcel;
							dataChil.put("日次項目 名称", value==null?"":value.get(0).getSheetName());
							List<Integer> keyDaily = Collections.emptyList();
							if(!CollectionUtil.isEmpty(value)){
								keyDaily = 
										value.stream()
								              .map(BusinessDailyExcel::getAttItemId)
								              .collect(Collectors.toList());
							}
							List<String> codeAndNameDaily = new ArrayList<>();
							String duplicateStringName = "";
							for (Integer key : keyDaily) {
								AttItemName attenNameDaily =!CollectionUtil.isEmpty(listBzDailyExcel)? mapAttNameDailys.get(key):null;
								if(attenNameDaily!=null && !duplicateStringName.equals(attenNameDaily.getAttendanceItemDisplayNumber()+attenNameDaily.getAttendanceItemName())){
									duplicateStringName = attenNameDaily.getAttendanceItemDisplayNumber()+attenNameDaily.getAttendanceItemName();
									codeAndNameDaily.add(duplicateStringName);
								}
							}
							String listItemDaily = String.join(",", codeAndNameDaily);
							dataChil.put("日次項目 項目", listItemDaily);
							datas.add(alignMasterDataSheet3(dataChil));
						}
					}
				}else {
					datas.add(alignMasterDataSheet3(data));
				}
				
				
				
			});
		}
		return datas;
	}
	
	private void putDataEmptySheet3(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("日次項目 Sheet選択","");
		data.put("日次項目 名称","");
		data.put("日次項目 項目","");
		data.put("月次項目","");
	}
	private MasterData alignMasterDataSheet3(Map<String, Object> data) {
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("日次項目 Sheet選択").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("日次項目 名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("日次項目 項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("月次項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		return masterData;
	}
public List<MasterHeaderColumn> getHeaderColumnsSheet5(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("順", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterData> getMasterDatasSheet5(MasterListExportQuery query) {
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listBzTypeSort)) {
			return null;
		} else {
			listBzTypeSort.stream().forEach(c -> {
				
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet5(data);
				BusinessTypeSortedDto bzSort = c;
				data.put("コード", bzSort.getDislayNumber());
				data.put("名称", bzSort.getAttendanceItemName());
				data.put("順", bzSort.getOrder());
				datas.add(alignMasterDataSheet5(data));
				
			});
		}
		return datas;
	}
	
	private void putDataEmptySheet5(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("順","");
	}
	private MasterData alignMasterDataSheet5(Map<String, Object> data) {

		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("順").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		return masterData;
	}
	
	//sheet 6
	public List<MasterHeaderColumn> getHeaderColumnsSheet6(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", column1,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", column2,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column3", column3,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column4", column4,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column5", column5,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column6", column6,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column7", column7,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column8", column8,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column9", column9,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column10", column10,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column11", column10,
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("column12", column12,
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterData> getMasterDatasSheet6(MasterListExportQuery query) {
		List<String> listEmpCode = new ArrayList<String>(mapTypeByEmpAndGroup.keySet());
		Collections.sort(listEmpCode);
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listEmpCode)) {
			return null;
		} else {
			for (String empCode : listEmpCode) {
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet6(data);
				
				Map<Integer, List<WorkTypeDtoExcel>> mapbyGroupNo = mapTypeByEmpAndGroup.get(empCode);
				List<Integer> listGroupNo = new ArrayList<Integer>(mapbyGroupNo.keySet());
				Collections.sort(listGroupNo);
				for (Integer GroupNo : listGroupNo) {
					List<WorkTypeDtoExcel> groupWorkType = mapbyGroupNo.get(GroupNo);
					if(!CollectionUtil.isEmpty(groupWorkType)){
						List<String> listString = groupWorkType.stream()
						        .map(developer -> new String(developer.getWorkTypeName()==null?"":developer.getWorkTypeName()))
						        .collect(Collectors.toList());
						Set<String> setListString = new HashSet<String>(listString);
						String listWorkTypeName = setListString.stream().collect(Collectors.joining(","));
						data.put("column"+(GroupNo+2),listWorkTypeName);
					}
					
				}
				data.put("コード",empCode);
				data.put("名称",mapbyGroupNo.get(listGroupNo.get(0)).get(0).getNameEmp());
				datas.add(alignMasterDataSheet6(data));
			}
		}
		return datas;
	}
	
	private void putDataEmptySheet6(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("column3","");
		data.put("column4","");
		data.put("column5","");
		data.put("column6","");
		data.put("column7","");
		data.put("column8","");
		data.put("column9","");
		data.put("column10","");
		data.put("column11","");
		data.put("column12","");
	}
	private MasterData alignMasterDataSheet6(Map<String, Object> data) {
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column3").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column4").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column5").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column6").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column7").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column8").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column9").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column10").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column11").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("column12").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		return masterData;
	}
	String column1 = "コード";
	String column2 = "名称";
	String column3 = "1出勤から変更可能な勤務種類初期値";
	String column4 = "2法定内休日から変更可能な勤務種類初期値";
	String column5 = "3法定外休日から変更可能な勤務種類初期値";
	String column6 = "4法定外休日(祝)から変更可能な勤務種類初期値";
	String column7 = "5no month";
	String column8 = "6no1";
	String column9 = "7法定外休日から変更可能";
	String column10 = "8法定外休日(祝)から変更";
	String column11 = "9no month";
	String column12 = "10no month";
}


