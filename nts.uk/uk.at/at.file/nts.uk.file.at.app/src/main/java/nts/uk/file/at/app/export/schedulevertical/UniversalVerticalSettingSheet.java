package nts.uk.file.at.app.export.schedulevertical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.ParamExternalBudget;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.BaseItemsDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.DailyItemsParamDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormBuiltDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormPeopleDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormPeopleFuncDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormTimeDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormTimeFuncDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormulaAmountDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormulaNumericalDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.FormulaUnitpriceDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.MoneyFuncDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.TimeUnitFuncDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.VerticalCalItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.VerticalSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.VerticalSettingFinder;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author thangnv
 *
 */

@Stateless
public class UniversalVerticalSettingSheet extends JpaRepository{
	
	@Inject
	private VerticalSettingFinder verticalSettingFinder;
	
	@Inject
	private ExternalBudgetFinder externalBudgetFinder;
	
	private static final String column_1 = "コード";
	private static final String column_2 = "名称";
	private static final String column_3 = "利用区分";
	private static final String column_4 = "応援勤務を集計に";
	private static final String column_5 = "単位";
	private static final String column_6 = "No";
	private static final String column_7 = "属性";
	private static final String column_8 = "項目名";
	private static final String column_9 = "計算設定";
	private static final String column_10 = "column_10";
	private static final String column_11 = "表示区分";
	private static final String column_12 = "累計";
	private static final String column_13 = "丸め";
	private static final String column_14 = "端数処理";
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column_1, TextResource.localize("KML002_6"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_2, TextResource.localize("KML002_7"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_3, TextResource.localize("KML002_83"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_4, TextResource.localize("KML002_10"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_5, TextResource.localize("KML002_9"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_6, TextResource.localize("KML002_17"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_7, TextResource.localize("KML002_18"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_8, TextResource.localize("KML002_19"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_9, TextResource.localize("KML002_20"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_10, TextResource.localize("KML002_156"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_11, TextResource.localize("KML002_21"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_12, TextResource.localize("KML002_22"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_13, TextResource.localize("KML002_23"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column_14, TextResource.localize("KML002_24"), ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	/* (non-Javadoc)
	 * 
	 */
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<VerticalSettingDto> listItems = verticalSettingFinder.findAllVerticalCalSet();
		// loop listItems 
		listItems = listItems.stream().sorted((object1, object2) -> object1.getVerticalCalCd().compareTo(object2.getVerticalCalCd())).collect(Collectors.toList());
		listItems.stream().forEach(item -> {
			AtomicInteger count = new AtomicInteger(0); 
			datas.addAll(createRow(item, count));
		});
		return datas;
	}
	
	private List<MasterData> createRow (VerticalSettingDto rowData, AtomicInteger count){
		List<MasterData> listMasterDatas = new ArrayList<>();
		List<Map<String, Object>> datas = new ArrayList<>();
		List<VerticalCalItemDto> listItems = rowData.getVerticalCalItems();
		listItems = listItems.stream().sorted((object1, object2) -> object1.getDispOrder() - object2.getDispOrder()).collect(Collectors.toList());
		
		List<Map<String, Object>> listDailyItems = getDailyItems(rowData.getUnit()) ;
		List<Map<String, Object>> listTimeItems = getTimeItems(rowData.getUnit()) ;
		List<Map<String, Object>> listUnitItems = getUnitItems(rowData.getUnit()) ;
		
		List<Map<String, Object>> listDailyItemPlus = getDailyItemPlus(rowData.getUnit()) ;
		
		ParamExternalBudget param = new ParamExternalBudget(2, rowData.getUnit()); // 2 is fixed like screens
		List<ExternalBudgetDto> listExternalBudgets = externalBudgetFinder.findByAtr(param);
		
		param = new ParamExternalBudget(1, rowData.getUnit()); // 1 is fixed like screens
		List<ExternalBudgetDto> listExternalBudgetPeoples = externalBudgetFinder.findByAtr(param);
		
		param = new ParamExternalBudget(3, rowData.getUnit()); // 3 is fixed like screens
		List<ExternalBudgetDto> listExternalBudgetNumericals = externalBudgetFinder.findByAtr(param);
		
		param = new ParamExternalBudget(2, rowData.getUnit()); // 2 is fixed like screens
		List<ExternalBudgetDto> listExternalBudgetAmounts = externalBudgetFinder.findByAtr(param);
		
		List<String> listFormulas = new ArrayList<>();
		for (int i = 0; i < listItems.size(); i++) {
			listFormulas.add("");
		}
		for (int i = 0; i < listItems.size(); i++) {
			VerticalCalItemDto item = listItems.get(i);
			
    		Map<String, Object> data = new HashMap<>();
			putEmptyData(data); 
			if (count.get() == 0) {
				// display row parent
				data.put(column_1, rowData.getVerticalCalCd());
				data.put(column_2, rowData.getVerticalCalName());
				
				switch (rowData.getUseAtr()) {
				case 0:
					data.put(column_3, TextResource.localize("Enum_UseAtr_NotUse"));
					break;
				case 1:
					data.put(column_3, TextResource.localize("Enum_UseAtr_Use"));
					break;
	
				default:
					data.put(column_3, "");
					break;
				}
				
				switch (rowData.getAssistanceTabulationAtr()) {
				case 0:
					data.put(column_4, TextResource.localize("Enum_IncludeAtr_Exclude"));
					break;
				case 1:
					data.put(column_4, TextResource.localize("Enum_IncludeAtr_Include"));
					break;
					
				default:
					data.put(column_4, "");
					break;
				}
				
				switch (rowData.getUnit()) {
				case 0:
					data.put(column_5, TextResource.localize("Enum_Unit_DAILY"));
					break;
				case 1:
					data.put(column_5, TextResource.localize("Enum_Unit_BY_TIME_ZONE"));
					break;
					
				default:
					data.put(column_5, "");
					break;
				}
			}
			
			data.put(column_6, item.getDispOrder());
			
			switch (item.getAttributes()) {
			case 0:
				data.put(column_7, TextResource.localize("Enum_Attributes_TIME"));
				break;
			case 1:
				data.put(column_7, TextResource.localize("Enum_Attribute_Section_Money"));
				break;
				
			case 2:
				data.put(column_7, TextResource.localize("Enum_Attributes_NUMBER_OF_PEOPLE"));
				break;
				
			case 3:
				data.put(column_7, TextResource.localize("Enum_Attributes_NUMBER"));
				break;
				
			case 4:
				data.put(column_7, TextResource.localize("Enum_Attributes_AVERAGE_PRICE"));
				break;
				
			default:
				data.put(column_7, "");
				break;
			}
			
			data.put(column_8, item.getItemName());
			
			switch (item.getCalculateAtr()) {
			case 0:
				data.put(column_9, TextResource.localize("KML002_25"));
				break;
			case 1:
				data.put(column_9, TextResource.localize("KML002_26"));
				break;
				
			default:
				data.put(column_9, "");
				break;
			}
			
			String itemName = item.getItemName();
			int settingMethod = item.getCalculateAtr();
			int attribute = item.getAttributes();
			
			FormBuiltDto formBuiltDto = item.getFormBuilt();
			FormTimeDto formTimeDto = item.getFormTime();
			FormPeopleDto formPeopleDto = item.getFormPeople();
			FormulaAmountDto formulaAmountDto = item.getFormulaAmount();
			List<FormulaNumericalDto> listFormulaNumericals = item.getNumerical();
			FormulaUnitpriceDto unitPrice = item.getUnitPrice();
			
            String beforeFormula = "";
            if (i > 0) {
                beforeFormula = listFormulas.get(i - 1);
            }
			String formula = getFormulaText(listItems, rowData.getUnit(), itemName, settingMethod, attribute, i, beforeFormula, formTimeDto, formBuiltDto, formPeopleDto, formulaAmountDto, 
						listFormulaNumericals, unitPrice, listDailyItems, listTimeItems, listUnitItems, listDailyItemPlus, listExternalBudgets, listExternalBudgetPeoples, listExternalBudgetNumericals, listExternalBudgetAmounts);
			data.put(column_10, formula);
			listFormulas.add(formula);
			
			switch (item.getDisplayAtr()) {
			case 0:
				data.put(column_11, TextResource.localize("Enum_DisplayArt_Display"));
				break;
			case 1:
				data.put(column_11, TextResource.localize("Enum_DisplayArt_NonDisplay"));
				break;
				
			default:
				data.put(column_11, "");
				break;
			}
			
			if (item.getAttributes() != 4) {
				switch (item.getCumulativeAtr()) {
				case 0:
					data.put(column_12, TextResource.localize("Enum_CumulativeAtr_NOT_ACCUMULATE"));
					break;
				case 1:
					data.put(column_12, TextResource.localize("Enum_CumulativeAtr_ACCUMULATE"));
					break;
					
				default:
					data.put(column_12, "");
					break;
				}
			} else {
				data.put(column_12, "");
			}
			
			if (item.getAttributes() == 0) {
				switch (item.getRounding()) {
				case 0:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_1Min"));
					break;
				case 1:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_5Min"));
					break;
				case 2:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_6Min"));
					break;
				case 3:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_10Min"));
					break;
				case 4:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_15Min"));
					break;
				case 5:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_20Min"));
					break;
				case 6:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_30Min"));
					break;
				case 7:
					data.put(column_13, TextResource.localize("Enum_RoundingTime_60Min"));
					break;
				default:
					data.put(column_13, "");
					break;
				}
				
				switch (item.getRoundingProcessing()) {
				case 0:
					data.put(column_14, TextResource.localize("Enum_Rounding_Down"));
					break;
				case 1:
					data.put(column_14, TextResource.localize("Enum_Rounding_Up"));
					break;
				case 2:
					data.put(column_14, TextResource.localize("Enum_Rounding_Down_Over"));
					break;
				default:
					data.put(column_14, "");
					break;
				}
			} else {
				switch (item.getRounding()) {
				case 0:
					data.put(column_13, TextResource.localize("Enum_Unit_NONE"));
					break;
				case 1:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_1_Digits"));
					break;
				case 2:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_2_Digits"));
					break;
				case 3:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_3_Digits"));
					break;
				case 4:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_4_Digits"));
					break;
				case 5:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_5_Digits"));
					break;
				case 6:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_6_Digits"));
					break;
				case 7:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_7_Digits"));
					break;
				case 8:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_8_Digits"));
					break;
				case 9:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_9_Digits"));
					break;
				case 10:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_10_Digits"));
					break;
				case 11:
					data.put(column_13, TextResource.localize("Enum_Unit_Int_11_Digits"));
					break;
				case 12:
					data.put(column_13, TextResource.localize("Enum_Unit_Decimal_1st"));
					break;
				case 13:
					data.put(column_13, TextResource.localize("Enum_Unit_Decimal_2nd"));
					break;
				case 14:
					data.put(column_13, TextResource.localize("Enum_Unit_Decimal_3rd"));
					break;
				default:
					data.put(column_13, "");
					break;
				}
				
				switch (item.getRoundingProcessing()) {
				case 0:
					data.put(column_14, TextResource.localize("Enum_Rounding_Truncation"));
					break;
				case 1:
					data.put(column_14, TextResource.localize("Enum_Rounding_Round_Up"));
					break;
				case 2:
					data.put(column_14, TextResource.localize("Enum_Rounding_Down_4_Up_5"));
					break;
				default:
					data.put(column_14, "");
					break;
				}
			}
			datas.add(data);
			count.getAndIncrement();
		}
		
		if (!datas.isEmpty()){
			for (Map<String, Object> data : datas) {
				MasterData masterData = new MasterData(data, null, "");
				// set cell align
				masterData.cellAt(column_1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_6).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				masterData.cellAt(column_7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_8).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_11).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_12).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(column_13).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				masterData.cellAt(column_14).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				listMasterDatas.add(masterData);
			}
		}
		
		return listMasterDatas;
	}
	
	/*
	 * get formula text from formula
	 */
	private String getFormulaText(List<VerticalCalItemDto> listAllItems, int unit, String itemName, int settingMethod, int attribute, int index, String beforeFormula, 
		FormTimeDto formTimeDto, FormBuiltDto formBuiltDto, FormPeopleDto formPeopleDto,
			FormulaAmountDto formulaAmountDto, List<FormulaNumericalDto> listFormulaNumericals, FormulaUnitpriceDto unitPrice, List<Map<String, Object>> listDailyItems, 
			List<Map<String, Object>> listTimeItems, List<Map<String, Object>> listUnitItems,
			List<Map<String, Object>> listDailyItemPlus, List<ExternalBudgetDto> listExternalBudgets, List<ExternalBudgetDto> listExternalBudgetPeoples, 
			List<ExternalBudgetDto> listExternalBudgetNumericals, List<ExternalBudgetDto> listExternalBudgetAmounts){
		
		String formulaResult = "";
		if (settingMethod == 1) {
			if (formBuiltDto != null){
				String text1 = "";
				String text2 = "";
				String operator = getOperatorStr(formBuiltDto.getOperatorAtr());
				if (formBuiltDto.getSettingMethod1() == 0) {
					for (VerticalCalItemDto verticalCalItemDto : listAllItems) {
						if (verticalCalItemDto.getItemId() != null){
							if (verticalCalItemDto.getItemId().equals(formBuiltDto.getVerticalCalItem1())){
								text1 = verticalCalItemDto.getItemName();
							}
						}
					}
				} else {
					text1 = formBuiltDto.getVerticalInputItem1();
				}
				if (formBuiltDto.getSettingMethod2() == 0) {
					for (VerticalCalItemDto verticalCalItemDto : listAllItems) {
						if (verticalCalItemDto.getItemId() != null){
							if (verticalCalItemDto.getItemId().equals(formBuiltDto.getVerticalCalItem2())){
								text2 = verticalCalItemDto.getItemName();
							}
						}
					}
				} else {
					text2 = formBuiltDto.getVerticalInputItem2();
				}
				
				formulaResult = beforeFormula + " " + TextResource.localize("KML002_37") + " " + text1 + " " + operator + " " + text2;
			}
		} else {
			if (attribute == 4) {
				if (unitPrice != null){
					formulaResult = getUnitPrice(unitPrice.getUnitPrice());
				}
			} else {
				if (index == 0) {
                    if (attribute == 0) {
                    	if (formTimeDto.getLstFormTimeFunc().size() <= 0) {
                            formulaResult = "";
                        } else {
                        	List<FormTimeFuncDto> listFormTimeFuncs = formTimeDto.getLstFormTimeFunc();
                        	for (int i = 0; i < listFormTimeFuncs.size(); i++) {
                        		FormTimeFuncDto data = listFormTimeFuncs.get(i);
                                String operator = data.getOperatorAtr() == 0 ? TextResource.localize("KML002_37") : TextResource.localize("KML002_38");
                                String attendanceItem = getAttendanceItem(listDailyItems, "id", data);
                                if (attendanceItem != null) {
                                    formulaResult += operator + " " + attendanceItem + " ";
                                    continue;
                                }
                                String presetItem = getAttendanceItem(listDailyItems, "presetItemId", data);
                                if (presetItem != null) {
                                	formulaResult += operator + " " + presetItem + " ";
                                	continue;
                                }
                                String externalItem = getAttendanceItem(listDailyItems, "externalBudgetCd", data);
                                if (externalItem != null) {
                                    formulaResult += operator + " " + externalItem + " ";
                                }
                            }
                        }
                    } else if (attribute == 1) {
                    	 if (formulaAmountDto != null) {
                    		 if (formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs().size() <= 0 && formulaAmountDto.getMoneyFunc().getLstMoney().size() <= 0) {
                    			 formulaResult = "";
                    		 }
                    	 }
                    	 if (formulaAmountDto.getMoneyFunc().getLstMoney().size() > 0) {
                    		 List<MoneyFuncDto> listMoneyFuncDto = formulaAmountDto.getMoneyFunc().getLstMoney();
                    		 for (int i = 0; i < listMoneyFuncDto.size(); i++) {
                    			 MoneyFuncDto money = listMoneyFuncDto.get(i);
                    			 String operatorAtr = "";
                    			 if (money.getOperatorAtr() == 0){
                    				 operatorAtr = TextResource.localize("KML002_37");
                    			 } else {
                    				 operatorAtr = TextResource.localize("KML002_38");
                    			 }
                                 if (unit == 1) {
                                     formulaResult += operatorAtr + " " + getExternalBudgetName(money, listExternalBudgets) + " ";
                                 }
                                 if (formulaAmountDto.getMoneyFunc().getCategoryIndicator() == 0 && formulaAmountDto.getMoneyFunc().getActualDisplayAtr() == 1) {
                                	 for (Map<String,Object> map : listTimeItems) {
                                		 if (money.getPresetItemId() != null){
	                                		 if (money.getPresetItemId().equals(map.get("id"))) {
	                                			 formulaResult += operatorAtr + " " + map.get("name") + " ";
	                                			 break;
	                                		 }
                                		 }
                                	 }
                                 }
                                 if (formulaAmountDto.getMoneyFunc().getCategoryIndicator() == 0 && formulaAmountDto.getMoneyFunc().getActualDisplayAtr() == 0) {
                                	 for (Map<String,Object> map : listTimeItems) {
                                		 if (money.getPresetItemId() != null){
	                                		 if (money.getPresetItemId().equals(map.get("id"))) {
	                                			 formulaResult += operatorAtr + " " + map.get("name") + " ";
	                                			 break;
	                                		 }
                                		 }
                                	 }
                                 }
                                 
                                 if (formulaAmountDto.getMoneyFunc().getCategoryIndicator() == 1) {
                                     for (Map<String,Object> map : listDailyItemPlus) {
                                    	 if (money.getPresetItemId() != null){
	                                		 if (money.getPresetItemId().equals(map.get("id"))) {
	                                			 formulaResult += operatorAtr + " " + map.get("name") + " ";
	                                			 break;
	                                		 }
                                    	 }
                                	 }
                                 }
                             }
                    	 } else if (formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs().size() > 0) {
                    		 List<TimeUnitFuncDto> listTimeUnitFuncs = formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs();
                             for (int i = 0; i < listTimeUnitFuncs.size(); i++) {
                            	 TimeUnitFuncDto timeUnit = listTimeUnitFuncs.get(i);
                            	 String operator = "";
                            	 if (timeUnit.getOperatorAtr() == 0){
                            		 operator = TextResource.localize("KML002_37");
                            	 } else {
                            		 operator = TextResource.localize("KML002_38");
                            	 }
                            	 boolean _check = false;
                            	 for (Map<String,Object> map : listDailyItems) {
                            		 if (timeUnit.getAttendanceItemId() != null){
	                            		 if (timeUnit.getAttendanceItemId().equals(map.get("id"))) {
	                            			 formulaResult += operator + " " + map.get("name") + " ";
	                            			 _check = true;
	                            			 break;
	                            		 }
                            		 }
                            	 }
                            	 if (!_check){
	                            	 for (Map<String,Object> map : listDailyItems) {
	                            		 if (timeUnit.getPresetItemId() != null){
		                            		 if (timeUnit.getPresetItemId().equals(map.get("id"))) {
		                            			 formulaResult += operator + " " + map.get("name") + " ";
		                            			 break;
		                            		 }
	                            		 }
	                            	 }
                            	 }
                             }
                         }
                    } else if (attribute == 2) {
                    	if (formPeopleDto != null){
	                    	if (formPeopleDto.getLstPeopleFunc().size() <= 0) {
	                            formulaResult = "";
	                        } else {
	                        	List<FormPeopleFuncDto> listFormPeopleFuncs = formPeopleDto.getLstPeopleFunc();
	                            for (int i = 0; i < listFormPeopleFuncs.size(); i++) {
	                            	FormPeopleFuncDto people = listFormPeopleFuncs.get(i);
	                            	if (people.getOperatorAtr() == 0){
	                            		formulaResult += TextResource.localize("KML002_37") + " " + getExternalBudgetPeopleName(people, listExternalBudgetPeoples) + " ";
	                            	} else {
	                            		formulaResult += TextResource.localize("KML002_38") + " " + getExternalBudgetPeopleName(people, listExternalBudgetPeoples) + " ";
	                            	}
	                            }
	                        }
                    	}
                    } else if (attribute == 3) {
                    	if (listFormulaNumericals != null){
                    		if (listFormulaNumericals.size() <= 0){
                    			formulaResult = "";
                    		} else {
                    			for (int i = 0; i < listFormulaNumericals.size(); i++) {
                    				FormulaNumericalDto num = listFormulaNumericals.get(i);
                    				if (num.getOperatorAtr() == 0){
                    					formulaResult += TextResource.localize("KML002_37") + " " + getExternalBudgetNumericalName(num, listExternalBudgetNumericals) + " ";
                    				} else {
                    					formulaResult += TextResource.localize("KML002_38") + " " + getExternalBudgetNumericalName(num, listExternalBudgetNumericals) + " ";
                    				}
                                }
                    		}
                    	}
                    }
				} else {
                    if (attribute == 0) {
                    	if (formTimeDto.getLstFormTimeFunc().size() <= 0) {
                            formulaResult = "";
                        } else {
                        	List<FormTimeFuncDto> listFormTimeFuncs = formTimeDto.getLstFormTimeFunc();
                        	for (int i = 0; i < listFormTimeFuncs.size(); i++) {
                        		FormTimeFuncDto data = listFormTimeFuncs.get(i);
                                String operator = data.getOperatorAtr() == 0 ? TextResource.localize("KML002_37") : TextResource.localize("KML002_38");
                                String attendanceItem = getAttendanceItem(listDailyItems, "id", data);
                                if (attendanceItem != null) {
                                	formulaResult += operator + " " + attendanceItem + " ";
                                	continue;
                                }
                                String presetItem = getAttendanceItem(listDailyItems, "presetItemId", data);
                                if (presetItem != null) {
                                	formulaResult += operator + " " + presetItem + " ";
                                	continue;
                                }
                                String externalItem = getAttendanceItem(listDailyItems, "externalBudgetCd", data);
                                if (externalItem != null) {
                                    formulaResult += operator + " " + externalItem + " ";
                                }
                            }
                        }
                    } else if (attribute == 1) {
                    	 if (formulaAmountDto != null) {
                    		 if (formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs().size() <= 0 && formulaAmountDto.getMoneyFunc().getLstMoney().size() <= 0) {
                    			 formulaResult = "";
                    		 }
                    	 }
                    	 if (formulaAmountDto.getMoneyFunc().getLstMoney().size() > 0) {
                    		 List<MoneyFuncDto> listMoneyFuncDto = formulaAmountDto.getMoneyFunc().getLstMoney();
                    		 for (int i = 0; i < listMoneyFuncDto.size(); i++) {
                    			 MoneyFuncDto money = listMoneyFuncDto.get(i);
                    			 String itemNameTmp = null;
                    			 String operator = money.getOperatorAtr() == 0 ? TextResource.localize("KML002_37") : TextResource.localize("KML002_38");
                    			 for (ExternalBudgetDto x : listExternalBudgetAmounts) {
                    				 if (x.getExternalBudgetCode() != null){
	                    				 if (x.getExternalBudgetCode().equals(money.getExternalBudgetCd())){
	                    					 itemNameTmp = x.getExternalBudgetName();
	                    				 }
                    				 }
                    			 }
                    			 if (itemNameTmp != null && itemNameTmp != "") {
                                     formulaResult += operator + " " + itemName + " ";
                                     continue;
                    			 }
                    			 String attendanceItem = getAttendanceItemMoney(listTimeItems, "id", money);
                    			 if (attendanceItem != null) {
                    				 formulaResult += operator + " " + attendanceItem + " ";
                    				 continue;
                    			 }
                                 String presetItem = getAttendanceItemMoney(listTimeItems, "presetItemId", money);
                                 if (presetItem != null) {
                                	 formulaResult += operator + " " + presetItem + " ";
                                	 continue;
                                 }
                                 String externalItem = getAttendanceItemMoney(listTimeItems, "externalBudgetCd", money);
                                 if (externalItem != null) {
                                	 formulaResult += operator + " " + externalItem + " ";
                                 }
                             }
                    	 } else if (formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs().size() > 0) {
                    		 List<TimeUnitFuncDto> listTimeUnitFuncs = formulaAmountDto.getTimeUnit().getLstTimeUnitFuncs();
                             for (int i = 0; i < listTimeUnitFuncs.size(); i++) {
                            	 TimeUnitFuncDto timeUnit = listTimeUnitFuncs.get(i);
                            	 String operator = "";
                            	 if (timeUnit.getOperatorAtr() == 0){
                            		 operator = TextResource.localize("KML002_37");
                            	 } else {
                            		 operator = TextResource.localize("KML002_38");
                            	 }
                            	 boolean _check = false;
                            	 for (Map<String,Object> map : listUnitItems) { 
                            		 if (timeUnit.getAttendanceItemId() != null){
	                            		 if (timeUnit.getAttendanceItemId().equals(map.get("id"))) {
	                            			 formulaResult += operator + " " + map.get("name") + " ";
	                            			 _check = true;
	                            			 break;
	                            		 }
                            		 }
                            	 }
                            	 if (!_check){
	                            	 for (Map<String,Object> map : listUnitItems) {
	                            		 if (timeUnit.getPresetItemId() != null){
		                            		 if (timeUnit.getPresetItemId().equals(map.get("id"))) {
		                            			 formulaResult += operator + " " + map.get("name") + " ";
		                            			 break;
		                            		 }
	                            		 }
	                            	 }
                            	 }
                             }
                         }
                    } else if (attribute == 2) {
                    	if (formPeopleDto != null){
	                    	if (formPeopleDto.getLstPeopleFunc().size() <= 0) {
	                            formulaResult = "";
	                        } else {
	                        	List<FormPeopleFuncDto> listFormPeopleFuncs = formPeopleDto.getLstPeopleFunc();
	                            for (int i = 0; i < listFormPeopleFuncs.size(); i++) {
	                            	FormPeopleFuncDto people = listFormPeopleFuncs.get(i);
	                            	String operator = "";
	                            	if (people.getOperatorAtr() == 0){
	                            		operator = TextResource.localize("KML002_37");
	                            	} else {
	                            		operator = TextResource.localize("KML002_38");
	                            	}
                                    formulaResult += operator + " " + getExternalBudgetPeopleName(people, listExternalBudgetPeoples) + " ";
	                            }
	                        }
                    	}
                    } else if (attribute == 3) {
                    	if (listFormulaNumericals != null){
                    		if (listFormulaNumericals.size() <= 0){
                    			formulaResult = "";
                    		} else {
                    			for (int i = 0; i < listFormulaNumericals.size(); i++) {
                    				FormulaNumericalDto num = listFormulaNumericals.get(i);
                    				String operator = "";
                    				if (num.getOperatorAtr() == 0){
                    					operator = TextResource.localize("KML002_37");
                    				} else {
                    					operator = TextResource.localize("KML002_38");
                    				}
                                    formulaResult += operator + " " + getExternalBudgetNumericalName(num, listExternalBudgetNumericals) + " ";
                                }
                    		}
                    	}
                    }
				}
			}
		}
		formulaResult = formulaResult.trim();
		if (formulaResult.startsWith(TextResource.localize("KML002_37"))){
			formulaResult = formulaResult.substring(1);
		}
		return formulaResult.trim();
	}
	
	private String getOperatorStr(int operator){
		String operatorStr = null;
		switch (operator) {
		case 0:
			operatorStr = TextResource.localize("Enum_OperatorAtr_ADD");
			break;
		case 1:
			operatorStr = TextResource.localize("Enum_OperatorAtr_SUBTRACT");
			break;
		case 2:
			operatorStr = TextResource.localize("Enum_OperatorAtr_MULTIPLY");
			break;
		case 3:
			operatorStr = TextResource.localize("Enum_OperatorAtr_DIVIDE");
			break;

		default:
			operatorStr = "";
			break;
		}
		return operatorStr;
	}
	
	private String getUnitPrice(int unitPrice) {
		String unitPriceStr = null;
		switch (unitPrice) {
		case 0:
			unitPriceStr = TextResource.localize("KML002_53");
			break;
		case 1:
			unitPriceStr = TextResource.localize("KML002_54");
			break;
		case 2:
			unitPriceStr = TextResource.localize("KML002_55");
			break;
		case 3:
			unitPriceStr = TextResource.localize("KML002_56");
			break;
		case 4:
			unitPriceStr = TextResource.localize("KML002_57");
			break;

		default:
			unitPriceStr = "";
			break;
		}
		return unitPriceStr;
	}
	
	private String getAttendanceItem(List<Map<String, Object>> listDailyItems, String key, FormTimeFuncDto data) {
		if (!listDailyItems.isEmpty()){
			for (Map<String, Object> map : listDailyItems) {
				boolean check = false;
				if (map.containsKey("id")){
					if (map.get("id") != null && key != null){
						switch (key) {
						case "id":
							if (data.getAttendanceItemId() != null){
								if (data.getAttendanceItemId().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						case "presetItemId":
							if (data.getPresetItemId() != null){
								if (data.getPresetItemId().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						case "externalBudgetCd":
							if (data.getExternalBudgetCd() != null){
								if (data.getExternalBudgetCd().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						default:
							check = false;
							break;
						}
						
						if (check){
							if (map.containsKey("name")){
								if (map.get("name") != null){
									return (String)map.get("name");
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private String getAttendanceItemMoney(List<Map<String, Object>> listTimeItems, String key, MoneyFuncDto data) {
		if (!listTimeItems.isEmpty()){
			for (Map<String, Object> map : listTimeItems) {
				boolean check = false;
				if (map.containsKey("id")){
					if (map.get("id") != null && key != null){
						switch (key) {
						case "id":
							if (data.getAttendanceItemId() != null){
								if (data.getAttendanceItemId().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						case "presetItemId":
							if (data.getPresetItemId() != null){
								if (data.getPresetItemId().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						case "externalBudgetCd":
							if (data.getExternalBudgetCd() != null){
								if (data.getExternalBudgetCd().equals((String)map.get("id"))){
									check = true;
								}
							}
							break;
						default:
							check = false;
							break;
						}
						
						if (check){
							if (map.containsKey("name")){
								if (map.get("name") != null){
									return (String)map.get("name");
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/* put empty map */
	private void putEmptyData(Map<String, Object> data){
		data.put(column_1,"");
		data.put(column_2,"");
		data.put(column_3,"");
		data.put(column_4,"");
		data.put(column_5,"");
		data.put(column_6,"");
		data.put(column_7,"");
		data.put(column_8,"");
		data.put(column_9,"");
		data.put(column_10,"");
		data.put(column_11,"");
		data.put(column_12,"");
		data.put(column_13,"");
		data.put(column_14,"");
	}
	
	private List<Map<String, Object>> getDailyItems(int unit) {
        List<Integer> dailyAttendanceAtrs = new ArrayList<>();
        dailyAttendanceAtrs.add(new Integer(5)); // 5 fixed like screens
        
        DailyItemsParamDto pram = new DailyItemsParamDto();
        pram.setDailyAttendanceItemAtrs(dailyAttendanceAtrs);
        pram.setBudgetAtr(0); // 0 fixed like screens
        pram.setScheduleAtr(0); // 0 fixed like screens
        pram.setUnitAtr(unit);
        
        List<BaseItemsDto> listBaseItems = verticalSettingFinder.getDailyItems(pram);
        
        listBaseItems = listBaseItems.stream().sorted((object1, object2) -> object1.getDispOrder() - object2.getDispOrder()).collect(Collectors.toList());
        List<Map<String, Object>> listMaps = new ArrayList<>();
        
        for (BaseItemsDto base : listBaseItems) {
			Map<String, Object> map = new HashMap<>();
			String name = base.getItemName() + TextResource.localize("KML002_43");
			String id = base.getId();
			map.put("id", id.substring(0, id.length() - 1));
			map.put("name", name);
			map.put("itemType", base.getItemType());
			listMaps.add(map);
		}

        return listMaps;
    }
	
	private List<Map<String, Object>> getTimeItems(int unit) {
		List<Integer> dailyAttendanceAtrs = new ArrayList<>();
		dailyAttendanceAtrs.add(new Integer(3)); // 3 fixed like screens
		
		DailyItemsParamDto pram = new DailyItemsParamDto();
		pram.setDailyAttendanceItemAtrs(dailyAttendanceAtrs);
		pram.setBudgetAtr(2); // 2 fixed like screens
		pram.setScheduleAtr(6); // 6 fixed like screens
		pram.setUnitAtr(0);
		
		List<BaseItemsDto> listBaseItems = verticalSettingFinder.getDailyItems(pram);
		
		listBaseItems = listBaseItems.stream().sorted((object1, object2) -> object1.getDispOrder() - object2.getDispOrder()).collect(Collectors.toList());
		List<Map<String, Object>> listMaps = new ArrayList<>();
		
		for (BaseItemsDto base : listBaseItems) {
			Map<String, Object> map = new HashMap<>();
			String name = base.getItemName() + TextResource.localize("KML002_43");
			String id = base.getId();
			map.put("id", id.substring(0, id.length() - 1));
			map.put("name", name);
			map.put("itemType", base.getItemType());
			listMaps.add(map);
		}
		
		return listMaps;
	}
	
	private List<Map<String, Object>> getUnitItems(int unit) {
		List<Integer> dailyAttendanceAtrs = new ArrayList<>();
		dailyAttendanceAtrs.add(new Integer(5)); // 5 fixed like screens
		
		DailyItemsParamDto pram = new DailyItemsParamDto();
		pram.setDailyAttendanceItemAtrs(dailyAttendanceAtrs);
		pram.setBudgetAtr(1); // 2 fixed like screens
		pram.setScheduleAtr(0); // 6 fixed like screens
		pram.setUnitAtr(unit);
		
		List<BaseItemsDto> listBaseItems = verticalSettingFinder.getDailyItems(pram);
		
		listBaseItems = listBaseItems.stream().sorted((object1, object2) -> object1.getDispOrder() - object2.getDispOrder()).collect(Collectors.toList());
		List<Map<String, Object>> listMaps = new ArrayList<>();
		
		for (BaseItemsDto base : listBaseItems) {
			Map<String, Object> map = new HashMap<>();
			String name = base.getItemName() + TextResource.localize("KML002_43");
			String id = base.getId();
			map.put("id", id.substring(0, id.length() - 1));
			map.put("name", name);
			map.put("itemType", base.getItemType());
			listMaps.add(map);
		}
		
		return listMaps;
	}
	
	private List<Map<String, Object>> getDailyItemPlus(int unit) {
		List<Integer> dailyAttendanceAtrs = new ArrayList<>();
		dailyAttendanceAtrs.add(new Integer(3)); // 3 fixed like screens
		
		DailyItemsParamDto pram = new DailyItemsParamDto();
		pram.setDailyAttendanceItemAtrs(dailyAttendanceAtrs);
		pram.setBudgetAtr(2); // 2 fixed like screens
		pram.setScheduleAtr(6); // 6 fixed like screens
		pram.setUnitAtr(1); // 1 fixed like screens
		
		List<BaseItemsDto> listBaseItems = verticalSettingFinder.getDailyItems(pram);
		
		listBaseItems = listBaseItems.stream().sorted((object1, object2) -> object1.getDispOrder() - object2.getDispOrder()).collect(Collectors.toList());
		List<Map<String, Object>> listMaps = new ArrayList<>();
		
		for (BaseItemsDto base : listBaseItems) {
			Map<String, Object> map = new HashMap<>();
			String name = base.getItemName() + TextResource.localize("KML002_43");
			map.put("id", base.getId());
			map.put("name", name);
			map.put("itemType", base.getItemType());
			listMaps.add(map);
		}
		
		return listMaps;
	}
	
	private String getExternalBudgetName(MoneyFuncDto money, List<ExternalBudgetDto> listExternalBudgets) {
		String externalBudgetName = "";
		for (ExternalBudgetDto item : listExternalBudgets) {
			if (item.getExternalBudgetCode() != null){
				if (item.getExternalBudgetCode().equals(money.getExternalBudgetCd())) {
					return item.getExternalBudgetName();
				}
			}
		}
		String code = String.valueOf(listExternalBudgets.size() + 1);
		if (money.getExternalBudgetCd() != null){
			if (money.getExternalBudgetCd().equals(code)){
				return TextResource.localize("KML002_109");
			}
		}
		code = String.valueOf(listExternalBudgets.size() + 2);
		if (money.getExternalBudgetCd() != null){
			if (money.getExternalBudgetCd().equals(code)){
				return TextResource.localize("KML002_110");
			}
		}
		return externalBudgetName;
	}
	
	private String getExternalBudgetPeopleName(FormPeopleFuncDto people, List<ExternalBudgetDto> listExternalBudgetPeoples) {
		String externalBudgetName = "";
		for (ExternalBudgetDto item : listExternalBudgetPeoples) {
			if (item.getExternalBudgetCode() != null){
				if (item.getExternalBudgetCode().equals(people.getExternalBudgetCd())) {
					return item.getExternalBudgetName();
				}
			}
		}
		String code = String.valueOf(listExternalBudgetPeoples.size() + 1);
		if (people.getExternalBudgetCd() != null){
			if (people.getExternalBudgetCd().equals(code)){
				return TextResource.localize("KML002_109");
			}
		}
		code = String.valueOf(listExternalBudgetPeoples.size() + 2);
		if (people.getExternalBudgetCd() != null){
			if (people.getExternalBudgetCd().equals(code)){
				return TextResource.localize("KML002_110");
			}
		}
		return externalBudgetName;
	}
	
	private String getExternalBudgetNumericalName(FormulaNumericalDto numerical, List<ExternalBudgetDto> listExternalBudgetNumericals) {
		String externalBudgetName = "";
		for (ExternalBudgetDto item : listExternalBudgetNumericals) {
			if (item.getExternalBudgetCode() != null){
				if (item.getExternalBudgetCode().equals(numerical.getExternalBudgetCd())) {
					return item.getExternalBudgetName();
				}
			}
		}
		String code = String.valueOf(listExternalBudgetNumericals.size() + 1);
		if (numerical.getExternalBudgetCd() != null){
			if (numerical.getExternalBudgetCd().equals(code)){
				return TextResource.localize("KML002_109");
			}
		}
		return externalBudgetName;
	}
}
