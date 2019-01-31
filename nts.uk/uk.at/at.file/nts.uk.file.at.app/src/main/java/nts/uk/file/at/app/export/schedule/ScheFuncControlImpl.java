package nts.uk.file.at.app.export.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftCondition;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionRepository;
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
 * @author thangnv
 *
 */

@Stateless
@DomainID(value = "ScheFuncControl")
public class ScheFuncControlImpl implements MasterListData {
	
	private static final String sheet1_column2 = "sheet1_column2";
	
	@Inject
	private FunctionControlRepository functionControlRepository;
	
	@Inject
	private DisplayControlSheet displayControlSheet;
	
	@Inject
	private WorkTypeControlSheet workTypeSheet;
	
	@Inject
	private AuthorityFuncControlSheet authorityFuncControlSheet;
	
	@Inject
	private ShiftConditionRepository shiftConditionRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KSM011_120"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(sheet1_column2, "",
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("値", TextResource.localize("KSM011_121"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	/**
	 * multi sheet
	 */
	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		List<SheetData> sheetDatas = new ArrayList<>();
		SheetData displayControl = new SheetData(displayControlSheet.getMasterDatas(query), displayControlSheet.getHeaderColumns(query), null, null,TextResource.localize("KSM011_130"));	 
		sheetDatas.add(displayControl);
		
		SheetData workType = new SheetData(workTypeSheet.getMasterDatas(query), workTypeSheet.getHeaderColumns(query), null, null,TextResource.localize("KSM011_131"));	 
		sheetDatas.add(workType);
		
		Map<String, Object> map = authorityFuncControlSheet.initSheet();
		@SuppressWarnings("unchecked")
		SheetData authoritySheet = new SheetData((List<MasterData>)map.get("listDatas"), (List<MasterHeaderColumn>)map.get("listColumns"), null, null,TextResource.localize("KSM011_132"));	 
		sheetDatas.add(authoritySheet);
		
		return sheetDatas;
	}
	
	/** 
	 * @param MasterListExportQuery query
	 * @return List of MasterData
	 */
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		prepareSeedData();
		List<MasterData> datas = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Optional<ScheFuncControl> scheFuncControlOptional = functionControlRepository.getScheFuncControl(companyId);
		
		List<Map<String, Object>> listItems = prepareSeedData();
		
		List<ShiftCondition> listShipConditions = shiftConditionRepository.getListShiftCondition(companyId);
		if (!scheFuncControlOptional.isPresent()){
			return null;
		} else {
			ScheFuncControl scheFuncControl = scheFuncControlOptional.get();
			if (CollectionUtil.isEmpty(listItems)) {
				return null;
			} else {
				// loop listItems 
				listItems.stream().forEach(mapItem -> {
					datas.addAll(createMasterDateForRow(mapItem, scheFuncControl, listShipConditions));
				});
			}
		}
		return datas;
	}
	
	/**
	 * @name getShiftConditionName
	 * @param conditionNo
	 * @return
	 */
	private String getShiftConditionName(int conditionNo, List<ShiftCondition> listShipConditions){
		if (!listShipConditions.isEmpty()){
			for (ShiftCondition shiftCondition : listShipConditions) {
				if (conditionNo == shiftCondition.getConditionNo()){
					return shiftCondition.getConditionName().v();
				}
			}
		}
		return null;
	}
	
	/**
	 * Prepare fixed data
	 */
	private List<Map<String, Object>> prepareSeedData(){
		List<Map<String, Object>> listItems = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<String> listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_7"));
		listChilds.add(TextResource.localize("KSM011_10"));
		listChilds.add(TextResource.localize("KSM011_11"));
		listChilds.add(TextResource.localize("KSM011_12"));
		listChilds.add(TextResource.localize("KSM011_13"));
		listChilds.add(TextResource.localize("KSM011_14"));
		listChilds.add(TextResource.localize("KSM011_15"));
		listChilds.add(TextResource.localize("KSM011_80"));
		map.put(TextResource.localize("KSM011_76"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_17"));
		listChilds.add(TextResource.localize("KSM011_18"));
		listChilds.add(TextResource.localize("KSM011_19"));
		map.put(TextResource.localize("KSM011_16"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_21"));
		listChilds.add(TextResource.localize("KSM011_22"));
		map.put(TextResource.localize("KSM011_20"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_24"));
		listChilds.add(TextResource.localize("KSM011_25"));
		listChilds.add(TextResource.localize("KSM011_26"));
		map.put(TextResource.localize("KSM011_23"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_28"));
		listChilds.add(TextResource.localize("KSM011_31"));
		listChilds.add(TextResource.localize("KSM011_32"));
		listChilds.add(TextResource.localize("KSM011_33"));
		map.put(TextResource.localize("KSM011_27"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_35"));
		listChilds.add(TextResource.localize("KSM011_36"));
		listChilds.add(TextResource.localize("KSM011_37"));
		map.put(TextResource.localize("KSM011_34"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_39"));
		listChilds.add(TextResource.localize("KSM011_40"));
		map.put(TextResource.localize("KSM011_38"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_7"));
		listChilds.add(TextResource.localize("KSM011_77"));
		listChilds.add(TextResource.localize("KSM011_44"));
		listChilds.add(TextResource.localize("KSM011_46"));
		listChilds.add(TextResource.localize("KSM011_47"));
		map.put(TextResource.localize("KSM011_43"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_49"));
		listChilds.add(TextResource.localize("KSM011_50"));
		map.put(TextResource.localize("KSM011_48"), listChilds);
		listItems.add(map);
		
		return listItems;
	}
	
	/**
	 * 
	 * @param String item
	 * @param String companyId
	 * @return instance of MasterData
	 */
	@SuppressWarnings("unchecked")
	private List<MasterData> createMasterDateForRow (Map<String, Object> item, ScheFuncControl scheFuncControl, List<ShiftCondition> listShipConditions){
		if (item.isEmpty()) return null;
		List<MasterData> listMasterDatas = new ArrayList<>();
		List<Map<String, Object>> datas = new ArrayList<>();
		
		for (Map.Entry<String, Object> entry : item.entrySet()) {
		    String parent = entry.getKey();
		    if (entry.getValue() != null) {
		    	List<String> listChilds = (List<String>)entry.getValue();
		    	if (listChilds.isEmpty()) {
		    		Map<String, Object> data = new HashMap<>();
					putEmptyData(data); 
					data.put("項目", parent);
					datas.add(data);
		    	} else {
		    		AtomicInteger i = new AtomicInteger(0); 
		    		for (String child : listChilds) {
		    			Map<String, Object> data = new HashMap<>();
						putEmptyData(data); 
						if (i.get() == 0){
							data.put("項目", parent);
						}
						data.put(sheet1_column2, child);
						if (parent.equals(TextResource.localize("KSM011_43"))) {
							if (scheFuncControl.getCompletedFuncCls().value == 0 && scheFuncControl.getHowToComplete().value == 1) {
								if (child.equals(TextResource.localize("KSM011_44")) && scheFuncControl.getExecutionMethod().value == 1 && scheFuncControl.getAlarmCheckCls().value == 0){
									List<ScheFuncCond> listScheFuncConds = scheFuncControl.getScheFuncCond();
									listScheFuncConds = listScheFuncConds.stream().sorted((object1, object2) -> object1.getConditionNo() - (object2.getConditionNo())).collect(Collectors.toList());
									String conditionText = null;
									if (listScheFuncConds.isEmpty()){
										conditionText = TextResource.localize("KSM011_75");
									} else {
										for (ScheFuncCond scheFuncCond : listScheFuncConds) {
											if (conditionText == null){
												conditionText = String.valueOf(scheFuncCond.getConditionNo()) + getShiftConditionName(scheFuncCond.getConditionNo(), listShipConditions);
											} else {
												conditionText = conditionText.concat(", ").concat(String.valueOf(scheFuncCond.getConditionNo())).concat(getShiftConditionName(scheFuncCond.getConditionNo(), listShipConditions));
											}
										}
									}
									data.put("値", conditionText);
								} else {
									String value = getScheFuncControlValue(parent, child, scheFuncControl);
									data.put("値", value);
								}
							} else {
								data.put("値", null);
							}
						} else if (child.equals(TextResource.localize("KSM011_50")) && parent.equals(TextResource.localize("KSM011_48"))){
							if (scheFuncControl.getSearchMethod().value == 0){
								data.put("値", null);
							} else {
								String value = getScheFuncControlValue(parent, child, scheFuncControl);
								data.put("値", value);
							}
						} else {
							String value = getScheFuncControlValue(parent, child, scheFuncControl);
							data.put("値", value);
						}
						datas.add(data);
						i.getAndIncrement();
					}
		    	}
		    }
		}
		
		if (!datas.isEmpty()){
			for (Map<String, Object> data : datas) {
				MasterData masterData = new MasterData(data, null, "");
				
				// set cell align
				masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(sheet1_column2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("値").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				listMasterDatas.add(masterData);
			}
		}
		return listMasterDatas;
	}
	
	/**
	 * get TextResource by scheFuncControl
	 * @param parent
	 * @param key
	 * @param scheFuncControl
	 * @return
	 */
	private String getScheFuncControlValue(String parent, String key, ScheFuncControl scheFuncControl){
		if (key == null || parent == null) return null;
		String value = null;
		if (key.equals(TextResource.localize("KSM011_7")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getAlarmCheckUseCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_10")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getConfirmedCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_11")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getPublicCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_12")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getOutputCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_13")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getWorkDormitionCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_14")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getTeamCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_15")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getRankCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_80")) && parent.equals(TextResource.localize("KSM011_76"))){
			switch (scheFuncControl.getStartDateInWeek().value) {
			case 0:
				value = TextResource.localize("KSM011_122");
				break;
			case 1:
				value = TextResource.localize("KSM011_123");
				break;
			case 2:
				value = TextResource.localize("KSM011_124");
				break;
			case 3:
				value = TextResource.localize("KSM011_125");
				break;
			case 4:
				value = TextResource.localize("KSM011_126");
				break;
			case 5:
				value = TextResource.localize("KSM011_127");
				break;
			case 6:
				value = TextResource.localize("KSM011_128");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_17")) && parent.equals(TextResource.localize("KSM011_16"))){
			switch (scheFuncControl.getShortNameDisp().value) {
			case 0:	
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_18")) && parent.equals(TextResource.localize("KSM011_16"))){
			switch (scheFuncControl.getTimeDisp().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_19")) && parent.equals(TextResource.localize("KSM011_16"))){
			switch (scheFuncControl.getSymbolDisp().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_21")) && parent.equals(TextResource.localize("KSM011_20"))){
			switch (scheFuncControl.getTwentyEightDaysCycle().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_22")) && parent.equals(TextResource.localize("KSM011_20"))){
			switch (scheFuncControl.getLastDayDisp().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_24")) && parent.equals(TextResource.localize("KSM011_23"))){
			switch (scheFuncControl.getIndividualDisp().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_25")) && parent.equals(TextResource.localize("KSM011_23"))){
			switch (scheFuncControl.getDispByDate().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_26")) && parent.equals(TextResource.localize("KSM011_23"))){
			switch (scheFuncControl.getIndicationByShift().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_28")) && parent.equals(TextResource.localize("KSM011_27"))){
			switch (scheFuncControl.getRegularWork().value) {
			case 0:
				value = TextResource.localize("KSM011_29");
				break;
			case 1:
				value = TextResource.localize("KSM011_30");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_31")) && parent.equals(TextResource.localize("KSM011_27"))){
			switch (scheFuncControl.getFluidWork().value) {
			case 0:
				value = TextResource.localize("KSM011_29");
				break;
			case 1:
				value = TextResource.localize("KSM011_30");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_32")) && parent.equals(TextResource.localize("KSM011_27"))){
			switch (scheFuncControl.getWorkingForFlex().value) {
			case 0:
				value = TextResource.localize("KSM011_29");
				break;
			case 1:
				value = TextResource.localize("KSM011_30");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_33")) && parent.equals(TextResource.localize("KSM011_27"))){
			switch (scheFuncControl.getOvertimeWork().value) {
			case 0:
				value = TextResource.localize("KSM011_29");
				break;
			case 1:
				value = TextResource.localize("KSM011_30");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_35")) && parent.equals(TextResource.localize("KSM011_34"))){
			switch (scheFuncControl.getNormalCreation().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_36")) && parent.equals(TextResource.localize("KSM011_34"))){
			switch (scheFuncControl.getSimulationCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_37")) && parent.equals(TextResource.localize("KSM011_34"))){
			switch (scheFuncControl.getCaptureUsageCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_39")) && parent.equals(TextResource.localize("KSM011_38"))){
			switch (scheFuncControl.getCompletedFuncCls().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_40")) && parent.equals(TextResource.localize("KSM011_38"))){
			if (scheFuncControl.getCompletedFuncCls().value == 0) {
				switch (scheFuncControl.getHowToComplete().value) {
				case 0:
					value = TextResource.localize("KSM011_41");
					break;
				case 1:
					value = TextResource.localize("KSM011_42");
					break;
				default:
					break;
				}
			}
		} else if (key.equals(TextResource.localize("KSM011_7")) && parent.equals(TextResource.localize("KSM011_43"))){
			if (scheFuncControl.getCompletedFuncCls().value == 0 && scheFuncControl.getHowToComplete().value == 1) {
				switch (scheFuncControl.getAlarmCheckCls().value) {
				case 0:
					value = TextResource.localize("KSM011_8");
					break;
				case 1:
					value = TextResource.localize("KSM011_9");
					break;
				default:
					break;
				}
			} else {
				value = TextResource.localize("KSM011_9");
			}
		} else if (key.equals(TextResource.localize("KSM011_77")) && parent.equals(TextResource.localize("KSM011_43"))){
			if (scheFuncControl.getCompletedFuncCls().value == 0 && scheFuncControl.getHowToComplete().value == 1 && scheFuncControl.getAlarmCheckCls().value == 0) {
				switch (scheFuncControl.getExecutionMethod().value) {
				case 0:
					value = TextResource.localize("KSM011_41");
					break;
				case 1:
					value = TextResource.localize("KSM011_42");
					break;
				default:
					break;
				}
			}
		} else if (key.equals(TextResource.localize("KSM011_46")) && parent.equals(TextResource.localize("KSM011_43"))){
			switch (scheFuncControl.getHandleRepairAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_47")) && parent.equals(TextResource.localize("KSM011_43"))){
			switch (scheFuncControl.getConfirm().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_49")) && parent.equals(TextResource.localize("KSM011_48"))){
			switch (scheFuncControl.getSearchMethod().value) {
			case 0:
				value = TextResource.localize("KSM011_9");
				break;
			case 1:
				value = TextResource.localize("KSM011_8");
				break;
			default:
				break;
			}
		} else if (key.equals(TextResource.localize("KSM011_50")) && parent.equals(TextResource.localize("KSM011_48"))){
			if (scheFuncControl.getSearchMethod().value == 1){
				switch (scheFuncControl.getSearchMethodDispCls().value) {
				case 0:
					value = TextResource.localize("KSM011_51");
					break;
				case 1:
					value = TextResource.localize("KSM011_52");
					break;
				default:
					break;
				}
			}
		}
		
		return value;
	}
	
	/* Sheet name */
	@Override
	public String mainSheetName() {
		return TextResource.localize("KSM011_129"); 
	} 
	
	/* put empty map */
	private void putEmptyData(Map<String, Object> data){
		data.put("項目","");
		data.put(sheet1_column2, "");
		data.put("値", "");
	}
}
