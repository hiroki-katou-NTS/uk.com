package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonFinder;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.DisplayTimeItemDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonthlyActualResultsDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeFinder;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.OrderReferWorkTypeDto;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthlyDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypesFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.EmployeeRoleDto;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.EmploymentRoleFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.shared.app.find.scherec.attitem.AttItemFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
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
@DomainID(value = "MonthlyExport")
public class RoleMonthlyExportExcelImpl implements MasterListData {

	//sheet1
	@Inject
	private AtItemNameAdapter atItemNameAdapter;
	@Inject
	private EmploymentRoleFinder employmentRoleFinder; 
	@Inject
	private AttItemFinder attfinder; //sheet 2 dung chung
	
	//sheet 2 
	@Inject
	private ControlOfAttMonthlyRepoExcel controlOfItemlExcel;
	//sheet 3
	@Inject
	private BusinessTypesFinder findAll;
	@Inject
	private MonthlyRecordWorkTypeFinder fiderRecordMonthly;
	//sheet 4 
	@Inject
	private BusinessTypeSortedMonFinder businessFinder;
	//sheet 1
	List<EmployeeRoleDto> listEmployeeRoleDto ;
	List<AttItemName> listAttItemNameNoAuth;
	List<AttItemName> listAttItemNameWithAuth;
	//sheet 2
	Map<String,List<AttItemName>> authSeting = new HashMap<>();
	Map<Integer, ControlOfAttMonthlyDtoExcel> listConItem = new HashMap<>();
	//sheet 3 
	List<BusinessTypeFormatMonthly> listBusinessMonthly;
	List<BusinessTypeDto> listBzMonthly = new ArrayList<>();
	Map<Integer, AttItemName> mapAttNameMonthlys = new HashMap<>();
	List<MonthlyRecordWorkTypeDto> listMonthlyRecord = new ArrayList<>();
	Map<String, MonthlyRecordWorkTypeDto> mapListRecordMonthly = new HashMap<>();
	//sheet 4 
	List<MonthlyRecordWorkTypeDto> listMonthly ;
	List<OrderReferWorkTypeDto> listOrderReferWorkType = new ArrayList<>();
 
	String companyId = "";
			//new 

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		companyId = AppContexts.user().companyId();
		initSheet1();
		initSheet2();
		initSheet3();
		initSheet4();
		// add the work place sheet
		List<SheetData> sheetDatas = new ArrayList<>();
		SheetData sheet2 = new SheetData(getMasterDatasSheet2(query),
				getHeaderColumnsSheet2(query), null, null, "sheet2");
		sheetDatas.add(sheet2);
		SheetData sheet3 = new SheetData(getMasterDatasSheet3(query),
				getHeaderColumnsSheet3(query), null, null, "sheet3");
		sheetDatas.add(sheet3);
		SheetData sheet4 = new SheetData(getMasterDatasSheet4(query),
				getHeaderColumnsSheet4(query), null, null, "sheet4");
		sheetDatas.add(sheet4);
		return sheetDatas;
	}

	


	private void initSheet4() {
		BusinessTypeSortedMonDto bzTypeSortMon =  businessFinder.getBusinessTypeSortedMon();
		if(bzTypeSortMon!=null){
			listOrderReferWorkType = bzTypeSortMon.getListOrderReferWorkType();
			listOrderReferWorkType.sort(Comparator.comparing(OrderReferWorkTypeDto::getAttendanceItemID));
		}
	}




	private void initSheet3() {
		listBzMonthly = findAll.findAll();
		listMonthlyRecord = fiderRecordMonthly.getListMonthlyRecordWorkType();
		mapListRecordMonthly = 
				listMonthlyRecord.stream().collect(Collectors.toMap(MonthlyRecordWorkTypeDto::getBusinessTypeCode,
				Function.identity()));
	}


	private void initSheet2() {
		 listConItem = controlOfItemlExcel.getAllByCompanyId(companyId);
	}


	private void initSheet1() {
		listEmployeeRoleDto =  employmentRoleFinder.findEmploymentRoles();
		listEmployeeRoleDto.sort(Comparator.comparing(EmployeeRoleDto::getRoleCode));
		listAttItemNameNoAuth = atItemNameAdapter.getNameOfAttdItemByType(EnumAdaptor.valueOf(2, TypeOfItemImport.class));//sheet 2,3 dung chung
		listAttItemNameNoAuth.sort(Comparator.comparing(AttItemName::getAttendanceItemDisplayNumber));
		mapAttNameMonthlys = listAttItemNameNoAuth.stream().collect(Collectors.toMap(AttItemName::getAttendanceItemId,
				Function.identity()));
		for (EmployeeRoleDto employeeRoleDto : listEmployeeRoleDto) {
			listAttItemNameWithAuth = attfinder.getMonthlyAttItemById(employeeRoleDto.getRoleId());
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
					data.put("本人修正設定", "－" );
				}
				if(attItemAuthor.isCanBeChangedByOthers()){
					data.put("本人以外修正設定", "○" );
				}else {
					data.put("本人以外修正設定", "－" );
				}
			}
		}else {
			data.put("利用区分", "－");
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
				ControlOfAttMonthlyDtoExcel controlItem = listConItem.get(c.getAttendanceItemId());
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet2(data);
			
				data.put("コード", c.getAttendanceItemDisplayNumber());
				data.put("項目", c.getAttendanceItemName());
				if(controlItem!=null){
					if(controlItem.getHeaderBgColorOfMonthlyPer()!=null){
						data.put("ヘッダー色", controlItem.getHeaderBgColorOfMonthlyPer());
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
		columns.add(new MasterHeaderColumn("Sheet選択", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称SheetName", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("月次項目 項目", TextResource.localize("KML004_16"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterData> getMasterDatasSheet3(MasterListExportQuery query) {
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listBzMonthly)) {
			throw new BusinessException("Msg_393");
		} else {
			listBzMonthly.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet3(data);
				MonthlyRecordWorkTypeDto montlhyRecord = mapListRecordMonthly.get(c.getBusinessTypeCode());
				data.put("コード", c.getBusinessTypeCode());
				data.put("名称", c.getBusinessTypeName());
				if(montlhyRecord!=null &&montlhyRecord.getDisplayItem()!=null){
					MonthlyActualResultsDto monActualResult = montlhyRecord.getDisplayItem();
					List<SheetCorrectedMonthlyDto> listSheetCorrectedMonthly = monActualResult.getListSheetCorrectedMonthly();
					int check = 0;
					if(!CollectionUtil.isEmpty(listSheetCorrectedMonthly)){
						for(int i = 0 ; i < listSheetCorrectedMonthly.size() ; i++) {
							Map<String, Object> dataChil = new HashMap<>();
							putDataEmptySheet3(dataChil);
							SheetCorrectedMonthlyDto sheetCorectMon = listSheetCorrectedMonthly.get(i);
							if(check==0){
								data.put("Sheet選択", sheetCorectMon.getSheetNo());
								data.put("名称SheetName", sheetCorectMon.getSheetName());
								//add last column
								List<String> codeAndNameMonthly = new ArrayList<>();
								AttItemName attenNameMonthly = new AttItemName();
								List<DisplayTimeItemDto> listDisplayItem = sheetCorectMon.getListDisplayTimeItem();
								if(!CollectionUtil.isEmpty(listDisplayItem)){
									listDisplayItem.sort(Comparator.comparing(DisplayTimeItemDto::getDisplayOrder));
									for (DisplayTimeItemDto displayTimeItemDto : listDisplayItem) {
										attenNameMonthly = mapAttNameMonthlys.get(displayTimeItemDto.getItemDaily());
										if(attenNameMonthly!=null)
										codeAndNameMonthly.add(attenNameMonthly.getAttendanceItemDisplayNumber()+attenNameMonthly.getAttendanceItemName());
									}
								}
								String listCodeAndNameMonthly = String.join(",", codeAndNameMonthly);
								data.put("月次項目 項目", listCodeAndNameMonthly);
								datas.add(alignMasterDataSheet3(data));
								check++;
							}else {
								putDataEmptySheet3(dataChil);
								dataChil.put("Sheet選択", sheetCorectMon.getSheetNo());
								dataChil.put("名称SheetName", sheetCorectMon.getSheetName());
								//add last column
								List<String> codeAndNameMonthly = new ArrayList<>();
								AttItemName attenNameMonthly = new AttItemName();
								List<DisplayTimeItemDto> listDisplayItem = sheetCorectMon.getListDisplayTimeItem();
								if(!CollectionUtil.isEmpty(listDisplayItem)){
									listDisplayItem.sort(Comparator.comparing(DisplayTimeItemDto::getDisplayOrder));
									for (DisplayTimeItemDto displayTimeItemDto : listDisplayItem) {
										attenNameMonthly = mapAttNameMonthlys.get(displayTimeItemDto.getItemDaily());
										if(attenNameMonthly!=null)
										codeAndNameMonthly.add(attenNameMonthly.getAttendanceItemDisplayNumber()+attenNameMonthly.getAttendanceItemName());
									}
								}
								String listCodeAndNameMonthly = String.join(",", codeAndNameMonthly);
								dataChil.put("月次項目 項目", listCodeAndNameMonthly);
								datas.add(alignMasterDataSheet3(dataChil));
							}
						}
					}
				}
			});
		}
		return datas;
	}
	
	private void putDataEmptySheet3(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("Sheet選択","");
		data.put("名称SheetName","");
		data.put("月次項目 項目","");
	}
	private MasterData alignMasterDataSheet3(Map<String, Object> data) {
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("Sheet選択").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称SheetName").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("月次項目 項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		return masterData;
	}

	//SHEET 4
	public List<MasterHeaderColumn> getHeaderColumnsSheet4(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("順", TextResource.localize("KML004_11"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasSheet4(MasterListExportQuery query) {
		
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listOrderReferWorkType)) {
			throw new BusinessException("Msg_393");
		} else {
			listOrderReferWorkType.stream().forEach(c -> {
				
				Map<String, Object> data = new HashMap<>();
				putDataEmptySheet4(data);
				OrderReferWorkTypeDto oderBzSort = c;
				AttItemName attDtdo = mapAttNameMonthlys.get(c.getAttendanceItemID());
				String nameAtt =attDtdo!=null?attDtdo.getAttendanceItemName():"";
				data.put("コード", attDtdo!=null?attDtdo.getAttendanceItemDisplayNumber():"");
				data.put("名称", nameAtt);
				data.put("順", oderBzSort.getOrder());
				datas.add(alignMasterDataSheet4(data));
				
			});
		}
		return datas;
	}
	
	private void putDataEmptySheet4(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("順","");
	}
	private MasterData alignMasterDataSheet4(Map<String, Object> data) {
	
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("順").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		return masterData;
	}
}

