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
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.DisplayControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.SchePerInfoAtr;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheQualifySet;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.shr.com.context.AppContexts;
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
public class DisplayControlSheet {
	
	private static final String sheet2_column2 = "sheet2_column2";
	private static final String sheet2_column3 = "sheet2_column3";
	
	@Inject
	private DisplayControlRepository displayControlRepository;
	
	@Inject
	private FunctionControlRepository functionControlRepository;
	
	/* (non-Javadoc)
	 * 
	 */
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KSM011_120"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(sheet2_column2, "", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(sheet2_column3, "", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("値", TextResource.localize("KSM011_121"), ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	/* (non-Javadoc)
	 * 
	 */
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		prepareSeedData();
		List<MasterData> datas = new ArrayList<>();
		Optional<ScheDispControl> scheDispControlOptional = displayControlRepository.getScheDispControl(companyId);
		
		List<Map<String, Object>> listItems = prepareSeedData();
		if (!scheDispControlOptional.isPresent()){
			return null;
		} else {
			ScheDispControl scheDispControl = scheDispControlOptional.get();
			Optional<ScheFuncControl> scheFuncControlOptional = functionControlRepository.getScheFuncControl(companyId);
			if (!scheFuncControlOptional.isPresent()){
				return null;
			} else {
				ScheFuncControl scheFuncControl = scheFuncControlOptional.get();
				if (CollectionUtil.isEmpty(listItems)) {
					return null;
				} else {
					// loop listItems 
					if (scheFuncControl != null && scheDispControl != null) {
						for (Map<String, Object> mapItem : listItems) {
							datas.addAll(createRow(mapItem, scheDispControl, scheFuncControl));
						}
					}
				}
			}
		}
		return datas;
	}
	
	/**
	 * fixed data get from screens
	 * @return 
	 */
	private List<Map<String, Object>> prepareSeedData(){
		List<Map<String, Object>> listItems = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<String> listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_54"));
		listChilds.add(TextResource.localize("KSM011_60"));
		listChilds.add(TextResource.localize("KSM011_61"));
		map.put(TextResource.localize("KSM011_53"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_63"));
		listChilds.add(TextResource.localize("KSM011_64"));
		map.put(TextResource.localize("KSM011_62"), listChilds);
		listItems.add(map);
		
		map = new HashMap<>();
		listChilds = new ArrayList<String>();
		listChilds.add(TextResource.localize("KSM011_66"));
		listChilds.add(TextResource.localize("KSM011_67"));
		map.put(TextResource.localize("KSM011_65"), listChilds);
		listItems.add(map);
		return listItems;
	}
	
	/**
	 * @param String item
	 * @param String companyId
	 * @return instance of MasterData
	 */
	@SuppressWarnings("unchecked")
	private List<MasterData> createRow (Map<String, Object> item, ScheDispControl scheDispControl, ScheFuncControl scheFuncControl){
		if (item.isEmpty()) return null;
		List<MasterData> listMasterDatas = new ArrayList<>();
		List<Map<String, Object>> datas = new ArrayList<>();
		
		List<SchePerInfoAtr> schePerInfoAtrs = scheDispControl.getSchePerInfoAtr();
		List<ScheQualifySet> scheQualifySets = scheDispControl.getScheQualifySet();
		
		schePerInfoAtrs = schePerInfoAtrs.stream().sorted((object1, object2) -> object1.getPersonInfoAtr().compareTo(object2.getPersonInfoAtr())).collect(Collectors.toList());
		scheQualifySets = scheQualifySets.stream().sorted((object1, object2) -> object1.getQualifyCode().compareTo(object2.getQualifyCode())).collect(Collectors.toList());
		
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
						data.put(sheet2_column2, child);
						if (child.equals(TextResource.localize("KSM011_54")) && parent.equals(TextResource.localize("KSM011_53"))){
							
							data.put(sheet2_column3, TextResource.localize("KSM011_79"));
							String contentText = null;
							if (!schePerInfoAtrs.isEmpty()){
								for (SchePerInfoAtr info : schePerInfoAtrs) {
									if (contentText == null){
										if (getInfoDisplayName(info.getPersonInfoAtr().value, scheFuncControl) != null){
											contentText = getInfoDisplayName(info.getPersonInfoAtr().value, scheFuncControl);
										}
									} else {
										if (getInfoDisplayName(info.getPersonInfoAtr().value, scheFuncControl) != null){
											contentText = contentText.concat(",").concat(getInfoDisplayName(info.getPersonInfoAtr().value, scheFuncControl));
										}
									}
								}
							}
							
							data.put("値", contentText);
						} else if (child.equals(TextResource.localize("KSM011_61")) && parent.equals(TextResource.localize("KSM011_53"))){
							// The value of this screen is currently fixed to false and text value fixed to ""
							String contentText = null;
							/* if have values, code can be like that
							 if (!scheQualifySets.isEmpty()){
								for (ScheQualifySet qli : scheQualifySets) {
									if (contentText == null){
										contentText = getNameFromCode(qli.getQualifyCode());
									} else {
										contentText = contentText.concat(",").concat(getNameFromCode(qli.getQualifyCode()));
									}
								}
							}
							*/
							data.put("値", contentText);
						} else {
							data.put("値", getScheFuncControlValue(parent, child, scheDispControl));
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
				masterData.cellAt(sheet2_column2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(sheet2_column3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("値").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				listMasterDatas.add(masterData);
			}
		}
		return listMasterDatas;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	private String getInfoDisplayName(int code, ScheFuncControl scheFuncControl){
		String name = null;
		switch (code) {
		case 0:
			name = TextResource.localize("Com_Employment");
			break;
		case 1:
			name = TextResource.localize("Com_Workplace");
			break;
		case 2:
			name = TextResource.localize("Com_Class");
			break;
		case 3:
			name = TextResource.localize("Com_Jobtitle");
			break;
		case 4:
			name = TextResource.localize("KSM011_55");
			break;
		case 5:
			if (scheFuncControl.getTeamCls().value == 1){
				name = TextResource.localize("KSM011_56");
			}
			break;
		case 6:
			if (scheFuncControl.getRankCls().value == 1){
				name = TextResource.localize("KSM011_57");
			}
			break;
		case 7:
			name = TextResource.localize("KSM011_58");
			break;
		case 8:
			name = TextResource.localize("KSM011_59");
			break;

		default:
			break;
		}
		return name;
	}
	

	/**
	 * get TextResource by scheFuncControl
	 * @param parent
	 * @param key
	 * @param scheDispControl
	 * @return
	 */
	private String getScheFuncControlValue(String parent, String key, ScheDispControl scheDispControl){
		if (key == null || parent == null) return null;
		String value = null;
		if (key.equals(TextResource.localize("KSM011_60")) && parent.equals(TextResource.localize("KSM011_53"))){
			value = scheDispControl.getPersonSyQualify().v();
		}
		if (key.equals(TextResource.localize("KSM011_63")) && parent.equals(TextResource.localize("KSM011_62"))){
			switch (scheDispControl.getSymbolHalfDayAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_9");
				break;
			case 1:
				value = TextResource.localize("KSM011_8");
				break;
			default:
				break;
			}
		}
		if (key.equals(TextResource.localize("KSM011_64")) && parent.equals(TextResource.localize("KSM011_62"))){
			switch (scheDispControl.getSymbolAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_9");
				break;
			case 1:
				value = TextResource.localize("KSM011_8");
				break;
			default:
				break;
			}
		}
		if (key.equals(TextResource.localize("KSM011_66")) && parent.equals(TextResource.localize("KSM011_65"))){
			switch (scheDispControl.getPubHolidayExcessAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_9");
				break;
			case 1:
				value = TextResource.localize("KSM011_8");
				break;
			default:
				break;
			}
		}
		if (key.equals(TextResource.localize("KSM011_67")) && parent.equals(TextResource.localize("KSM011_65"))){
			switch (scheDispControl.getPubHolidayShortageAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_9");
				break;
			case 1:
				value = TextResource.localize("KSM011_8");
				break;
			default:
				break;
			}
		}
		return value;
	}
	
	/* put empty map */
	private void putEmptyData(Map<String, Object> data){
		data.put("項目","");
		data.put(sheet2_column2, "");
		data.put(sheet2_column3, "");
		data.put("値", "");
	}
}
