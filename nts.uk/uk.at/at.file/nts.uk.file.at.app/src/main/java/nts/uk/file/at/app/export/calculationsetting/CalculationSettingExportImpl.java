package nts.uk.file.at.app.export.calculationsetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionFinder;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionDto;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionFinder;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
@Stateless
@DomainID(value = "CalculationSetting")
public class CalculationSettingExportImpl implements MasterListData {
	@Inject
	SelectFunctionFinder finder;
	
	@Inject
	private HolidayAddtionFinder holidayAddtimeFinder;
	
	private static final String select = "○";
	private static final String unselect = "-";
	
	private static final String column1Sheet1="1";
	private static final String column2Sheet1="2";
	private static final String column3Sheet1="3";
	private static final String column1Sheet2="1";
	private static final String column2Sheet2="2";
	private static final String column3Sheet2="3";
	private static final String column4Sheet2="4";
	private static final String column5Sheet2="5";
	
	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 SheetData settingwrokhour = new SheetData(getDataSettingWorkingHours(query), getHeaderColumnsSettingWorkingHours(query), null, null,TextResource.localize("KMK013_427"));
		 sheetDatas.add(settingwrokhour);
		return sheetDatas;
	}
	

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		SelectFunctionDto selectFunctionDto=finder.findAllSetting();
		for(int i =1;i<=4;i++){
			Map<String, Object> data = new HashMap<>();
			switch (i) {
			case 1:
				data.put(column1Sheet1, TextResource.localize("KMK013_206"));
				data.put(column2Sheet1, TextResource.localize("KMK013_207"));
				if(!Objects.isNull(selectFunctionDto)){
					if(!Objects.isNull(selectFunctionDto.getFlexWorkManagement()) ){				
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getFlexWorkManagement()));
					}
				}				
				break;
			case 2:
				data.put(column1Sheet1, "");
				data.put(column2Sheet1, TextResource.localize("KMK013_211"));
				if(!Objects.isNull(selectFunctionDto)){
					if(!Objects.isNull(selectFunctionDto.getUseAggDeformedSetting()) ){				
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getFlexWorkManagement()));
					}
				}
				
				break;
			case 3:
				data.put(column1Sheet1,TextResource.localize("KMK013_215"));
				data.put(column2Sheet1, TextResource.localize("KMK013_216"));
				if(!Objects.isNull(selectFunctionDto)){
					if(!Objects.isNull(selectFunctionDto.getUseWorkManagementMultiple()) ){				
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getUseWorkManagementMultiple()));
					}
				}
				break;
			case 4:
				data.put(column1Sheet1,"");
				data.put(column2Sheet1, TextResource.localize("KMK013_220"));
				if(!Objects.isNull(selectFunctionDto)){
					if(!Objects.isNull(selectFunctionDto.getUseTempWorkUse()) ){				
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getUseTempWorkUse()));
					}
				}
				break;
			default:
				break;
			}
			
			MasterData masterData = new MasterData(data, null, "");
			Map<String, MasterCellData> rowData = masterData.getRowData();
			rowData.get(column1Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get(column2Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get(column3Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			datas.add(masterData);
			
		}
	
		
		
		return datas;
	}

	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet1,TextResource.localize("KMK013_445"),ColumnTextAlign.LEFT, "", true));				
		columns.add(new MasterHeaderColumn(column2Sheet1,"",ColumnTextAlign.CENTER, "", true));		
		columns.add(new MasterHeaderColumn(column3Sheet1,TextResource.localize("KMK013_446"),ColumnTextAlign.LEFT, "", true));	
		return columns;
	}


	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK013_425");
	}

	
	private List<MasterHeaderColumn> getHeaderColumnsSettingWorkingHours (MasterListExportQuery query){
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet2,TextResource.localize("KMK013_445"),ColumnTextAlign.LEFT, "", true));				
		columns.add(new MasterHeaderColumn(column2Sheet2,"",ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet2,"",ColumnTextAlign.CENTER, "", true));	
		columns.add(new MasterHeaderColumn(column4Sheet2,"",ColumnTextAlign.CENTER, "", true));	
		columns.add(new MasterHeaderColumn(column5Sheet2,TextResource.localize("KMK013_446"),ColumnTextAlign.LEFT, "", true));
		return columns;
		
	}
	
	private List<MasterData> getDataSettingWorkingHours (MasterListExportQuery query){
		List<MasterData> datas = new ArrayList<>();
		List<HolidayAddtionDto> listHolidayAddtionDto=holidayAddtimeFinder.findAllHolidayAddtime();
		if(!CollectionUtil.isEmpty(listHolidayAddtionDto)){
			HolidayAddtionDto holidayAddtionDto=listHolidayAddtionDto.get(0);
		//	holidayAddtionDto.getReferActualWorkHours();
			Map<String, Object> line1 = new HashMap<>();
			line1.put(column1Sheet2, TextResource.localize("KMK013_3"));
			line1.put(column2Sheet2,"");
			line1.put(column3Sheet2,"");
			line1.put(column4Sheet2,"");
			if(holidayAddtionDto.getReferActualWorkHours()==1){
				line1.put(column5Sheet2,TextResource.localize("KMK013_5"));
			}else{
				line1.put(column5Sheet2,TextResource.localize("KMK013_6"));
			}
			MasterData masterData1 = new MasterData(line1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignsheet2(rowData1);		
			datas.add(masterData1);
			
		
			if(holidayAddtionDto.getReferActualWorkHours()==0){
				Map<String, Object> line2 = new HashMap<>();
				line2.put(column1Sheet2, TextResource.localize("KMK013_7"));
				line2.put(column2Sheet2,"");
				line2.put(column3Sheet2,"");
				line2.put(column4Sheet2,"");
				if(holidayAddtionDto.getNotReferringAch()==1){
					line2.put(column5Sheet2,TextResource.localize("KMK013_9"));
				}else{
					line2.put(column5Sheet2,TextResource.localize("KMK013_10"));
				}
				MasterData masterData2 = new MasterData(line2, null, "");
				Map<String, MasterCellData> rowData2 = masterData2.getRowData();
				getAlignsheet2(rowData2);
				datas.add(masterData2);
				//
				Map<String, Object> line3 = new HashMap<>();
				line3.put(column1Sheet2, TextResource.localize("KMK013_11"));
				line3.put(column2Sheet2,"");
				line3.put(column3Sheet2,"");
				line3.put(column4Sheet2,"");
				if(holidayAddtionDto.getReferComHolidayTime()==1){
					line3.put(column5Sheet2,TextResource.localize("KMK013_13"));
				}else{
					line3.put(column5Sheet2,TextResource.localize("KMK013_14"));
				}
				MasterData masterData3 = new MasterData(line3, null, "");
				Map<String, MasterCellData> rowData3 = masterData3.getRowData();
				getAlignsheet2(rowData3);
				datas.add(masterData3);
				//
				if(holidayAddtionDto.getReferComHolidayTime()==0){
					Map<String, Object> line4 = new HashMap<>();
					line4.put(column1Sheet2,"");
					line4.put(column2Sheet2,TextResource.localize("KMK013_15"));
					line4.put(column3Sheet2,"");
					line4.put(column4Sheet2,"");					
					line4.put(column5Sheet2,formatValueAttendance(holidayAddtionDto.getOneDay().intValue()));
					MasterData masterData4 = new MasterData(line4, null, "");
					Map<String, MasterCellData> rowData4 = masterData4.getRowData();
					getAlignRightsheet2(rowData4);
					datas.add(masterData4);
					
					//
					Map<String, Object> line5 = new HashMap<>();
					line5.put(column1Sheet2,"");
					line5.put(column2Sheet2,TextResource.localize("KMK013_17"));
					line5.put(column3Sheet2,"");
					line5.put(column4Sheet2,"");					
					line5.put(column5Sheet2,formatValueAttendance(holidayAddtionDto.getMorning().intValue()));
					MasterData masterData5 = new MasterData(line5, null, "");
					Map<String, MasterCellData> rowData5 = masterData5.getRowData();
					getAlignRightsheet2(rowData5);
					datas.add(masterData5);
					//
					Map<String, Object> line6 = new HashMap<>();
					line6.put(column1Sheet2,"");
					line6.put(column2Sheet2,TextResource.localize("KMK013_19"));
					line6.put(column3Sheet2,"");
					line6.put(column4Sheet2,"");					
					line6.put(column5Sheet2,formatValueAttendance(holidayAddtionDto.getAfternoon().intValue()));
					MasterData masterData6 = new MasterData(line6, null, "");
					Map<String, MasterCellData> rowData6 = masterData6.getRowData();
					getAlignRightsheet2(rowData6);
					datas.add(masterData6);
					
				}
						
			
			}
			
			//
			Map<String, Object> line7 = new HashMap<>();
			line7.put(column1Sheet2,TextResource.localize("KMK013_246"));
			line7.put(column2Sheet2,"");
			line7.put(column3Sheet2,TextResource.localize("KMK013_251"));
			line7.put(column4Sheet2,"");
			if(holidayAddtionDto.getAddingMethod1()==1){
				line7.put(column5Sheet2,TextResource.localize("KMK013_249"));
			}else{
				line7.put(column5Sheet2,TextResource.localize("KMK013_248"));
			}
		
			MasterData masterData7 = new MasterData(line7, null, "");
			Map<String, MasterCellData> rowData7 = masterData7.getRowData();
			getAlignsheet2(rowData7);
			datas.add(masterData7);
			//
			Map<String, Object> line8 = new HashMap<>();
			line8.put(column1Sheet2,TextResource.localize("KMK013_252"));
			line8.put(column2Sheet2,"");
			line8.put(column3Sheet2,TextResource.localize("KMK013_251"));
			line8.put(column4Sheet2,"");
			if(holidayAddtionDto.getAddingMethod2()==1){
				line8.put(column5Sheet2,TextResource.localize("KMK013_249"));
			}else{
				line8.put(column5Sheet2,TextResource.localize("KMK013_248"));
			}
		
			MasterData masterData8 = new MasterData(line8, null, "");
			Map<String, MasterCellData> rowData8 = masterData8.getRowData();
			getAlignsheet2(rowData8);
			datas.add(masterData8);
			
			//
			
			Map<String, Object> line9 = new HashMap<>();
			line9.put(column1Sheet2,TextResource.localize("KMK013_21"));
			line9.put(column2Sheet2,TextResource.localize("KMK013_22"));
			line9.put(column3Sheet2,"");
			line9.put(column4Sheet2,"");
			if(holidayAddtionDto.getAnnualHoliday()==1){
				line9.put(column5Sheet2,select);
			}else{
				line9.put(column5Sheet2,unselect);
			}
		
			MasterData masterData9 = new MasterData(line9, null, "");
			Map<String, MasterCellData> rowData9 = masterData9.getRowData();
			getAlignsheet2(rowData9);
			datas.add(masterData9);
			//
			Map<String, Object> line10 = new HashMap<>();
			line10.put(column1Sheet2,"");
			line10.put(column2Sheet2,TextResource.localize("KMK013_23"));
			line10.put(column3Sheet2,"");
			line10.put(column4Sheet2,"");
			if(holidayAddtionDto.getSpecialHoliday()==1){
				line10.put(column5Sheet2,select);
			}else{
				line10.put(column5Sheet2,unselect);
			}
		
			MasterData masterData10 = new MasterData(line10, null, "");
			Map<String, MasterCellData> rowData10 = masterData10.getRowData();
			getAlignsheet2(rowData10);
			datas.add(masterData10);
			//
			
			Map<String, Object> line11 = new HashMap<>();
			line11.put(column1Sheet2,"");
			line11.put(column2Sheet2,TextResource.localize("KMK013_24"));
			line11.put(column3Sheet2,"");
			line11.put(column4Sheet2,"");
			if(holidayAddtionDto.getYearlyReserved()==1){
				line11.put(column5Sheet2,select);
			}else{
				line11.put(column5Sheet2,unselect);
			}
		
			MasterData masterData11 = new MasterData(line11, null, "");
			Map<String, MasterCellData> rowData11 = masterData11.getRowData();
			getAlignsheet2(rowData11);
			datas.add(masterData11);
			//
			Map<String, Object> line12 = new HashMap<>();
			line12.put(column1Sheet2,TextResource.localize("KMK013_253"));
			line12.put(column2Sheet2,"");
			line12.put(column3Sheet2,"");
			line12.put(column4Sheet2,"");
			if(holidayAddtionDto.getAddSetManageWorkHour()==1){
				line12.put(column5Sheet2,TextResource.localize("KMK013_256"));
			}else{
				line12.put(column5Sheet2,TextResource.localize("KMK013_255"));
			}
			MasterData masterData12 = new MasterData(line12, null, "");
			Map<String, MasterCellData> rowData12 = masterData12.getRowData();
			getAlignsheet2(rowData12);
			datas.add(masterData12);
			
			//
			Map<String, Object> line13 = new HashMap<>();
			line13.put(column1Sheet2,TextResource.localize("KMK013_25"));
			line13.put(column2Sheet2,TextResource.localize("KMK013_28"));
			line13.put(column3Sheet2,"");
			line13.put(column4Sheet2,"");
			if(!Objects.isNull(holidayAddtionDto.getRegularWork()) && holidayAddtionDto.getRegularWork().getCalcActualOperationPre()==1){
				line13.put(column5Sheet2,TextResource.localize("KMK013_31"));
			}else{
				line13.put(column5Sheet2,TextResource.localize("KMK013_32"));
			}
			MasterData masterData13 = new MasterData(line13, null, "");
			Map<String, MasterCellData> rowData13 = masterData13.getRowData();
			getAlignsheet2(rowData13);
			datas.add(masterData13);
			
			//
			if(!Objects.isNull(holidayAddtionDto.getRegularWork()) && holidayAddtionDto.getRegularWork().getCalcActualOperationPre()==1){
				Map<String, Object> line14 = new HashMap<>();
				line14.put(column1Sheet2,"");
				line14.put(column2Sheet2,"");
				line14.put(column3Sheet2,TextResource.localize("KMK013_33"));
				line14.put(column4Sheet2,"");
				if(!Objects.isNull(holidayAddtionDto.getRegularWork()) && !Objects.isNull(holidayAddtionDto.getRegularWork().getAdditionTimePre())
						&& holidayAddtionDto.getRegularWork().getAdditionTimePre()==1){
					line14.put(column5Sheet2,select);
				}else{
					line14.put(column5Sheet2,unselect);
				}
				MasterData masterData14 = new MasterData(line14, null, "");
				Map<String, MasterCellData> rowData14 = masterData14.getRowData();
				getAlignsheet2(rowData14);
				datas.add(masterData14);
				//
				Map<String, Object> line15 = new HashMap<>();
				line15.put(column1Sheet2,"");
				line15.put(column2Sheet2,"");
				line15.put(column3Sheet2,"");
				
				line15.put(column4Sheet2,TextResource.localize("KMK013_34"));
				if(!Objects.isNull(holidayAddtionDto.getRegularWork()) && !Objects.isNull(holidayAddtionDto.getRegularWork().getDeformatExcValuePre())
						&& holidayAddtionDto.getRegularWork().getDeformatExcValuePre()==1){
					line15.put(column5Sheet2,TextResource.localize("KMK013_37"));
				}else{
					line15.put(column5Sheet2,TextResource.localize("KMK013_36"));
				}
				MasterData masterData15 = new MasterData(line15, null, "");
				Map<String, MasterCellData> rowData15 = masterData15.getRowData();
				getAlignsheet2(rowData15);
				datas.add(masterData15);
				
				
			}else{
				
			}
			
			
			
			
		}
	
		return datas;
		
	}
	
	private void getAlignsheet2(Map<String, MasterCellData> rowData){
		rowData.get(column1Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column4Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column5Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	
		
	}
	private void getAlignRightsheet2(Map<String, MasterCellData> rowData){
		rowData.get(column1Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column4Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column5Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
	
		
	}
	
	public String getFunctionSelect(int attr) {
		UseAtr useAtr=UseAtr.valueOf(attr);
		switch (useAtr) {
		case NOTUSE:
			return TextResource.localize("KMK013_210");
		case USE:
			return TextResource.localize("KMK013_209");		
		default:
			return "";
		}
	}
	private String formatValueAttendance(int att) {
		Integer hours = att / 60, minutes = att % 60;
		return String.join("", hours < 10 ? "0" : "", hours.toString(), ":", minutes < 10 ? "0" : "", minutes.toString());
	}

}
