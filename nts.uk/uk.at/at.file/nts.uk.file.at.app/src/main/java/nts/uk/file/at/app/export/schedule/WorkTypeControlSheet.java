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

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDisRepository;
import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeQueryRepository;
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
public class WorkTypeControlSheet extends JpaRepository{
	
	private static final String sheet3_column2 = "sheet3_column2";
	
	@Inject
	private WorktypeDisRepository worktypeDisRepository;
	
	@Inject
	private WorkTypeQueryRepository workTypeQueryRepository;
	
	/* (non-Javadoc)
	 * 
	 */
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KSM011_120"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(sheet3_column2, "", ColumnTextAlign.LEFT, "", true));
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
		Optional<WorktypeDis> worktypeDis = worktypeDisRepository.findByCId(companyId);
		
		List<WorkTypeDto> workTypeDtos = workTypeQueryRepository.findAllWorkTypeDisp(companyId, 0);
		
		List<Map<String, Object>> listItems = prepareSeedData();
		if (!worktypeDis.isPresent()){
			return null;
		} else {
			WorktypeDis worktypeDisControl = worktypeDis.get();
			if (CollectionUtil.isEmpty(listItems)) {
				return null;
			} else {
				// loop listItems 
				listItems.stream().forEach(mapItem -> {
					datas.addAll(createRow(mapItem, worktypeDisControl, workTypeDtos));
				});
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
		listChilds.add(TextResource.localize("KSM011_69"));
		listChilds.add(TextResource.localize("KSM011_1"));
		map.put(TextResource.localize("KSM011_68"), listChilds);
		listItems.add(map);
		
		return listItems;
	}
	
	/**
	 * @param String item
	 * @param String companyId
	 * @return instance of MasterData
	 */
	@SuppressWarnings("unchecked")
	private List<MasterData> createRow (Map<String, Object> item, WorktypeDis worktypeDisControl, List<WorkTypeDto> workTypeDtos){
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
						data.put(sheet3_column2, child);
						data.put("値", getControlValue(parent, child, worktypeDisControl, workTypeDtos));
						
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
				masterData.cellAt(sheet3_column2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
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
	 * @param worktypeDisControl
	 * @return
	 */
	private String getControlValue(String parent, String key, WorktypeDis worktypeDisControl, List<WorkTypeDto> workTypeDtos){
		if (key == null || parent == null) return null;
		String value = null;
		if (key.equals(TextResource.localize("KSM011_69")) && parent.equals(TextResource.localize("KSM011_68"))){
			switch (worktypeDisControl.getUseAtr().value) {
			case 0:
				value = TextResource.localize("KSM011_8");
				break;
			case 1:
				value = TextResource.localize("KSM011_9");
				break;
			default:
				break;
			}
		}
		if (key.equals(TextResource.localize("KSM011_1")) && parent.equals(TextResource.localize("KSM011_68"))){
			List<WorkTypeDisplaySetting> listWorkTypeDisplaySettings = worktypeDisControl.getWorkTypeList();
			listWorkTypeDisplaySettings = listWorkTypeDisplaySettings.stream().sorted((object1, object2) -> object1.getWorkTypeCode().compareTo(object2.getWorkTypeCode())).collect(Collectors.toList());
			if (!listWorkTypeDisplaySettings.isEmpty()){
				for (WorkTypeDisplaySetting info : listWorkTypeDisplaySettings) {
					if (value == null){
						value = getWorkTypeName(info.getWorkTypeCode(), workTypeDtos);
					} else {
						if (getWorkTypeName(info.getWorkTypeCode(), workTypeDtos) != null){
							value = value.concat(",").concat(getWorkTypeName(info.getWorkTypeCode(), workTypeDtos));
						}
					}
				}
			} else {
				value = TextResource.localize("KSM011_75");
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @param workTypeCode
	 * @param workTypeDtos
	 * @return
	 */
	private String getWorkTypeName (String workTypeCode, List<WorkTypeDto> workTypeDtos){
		String name = null;
		if (workTypeCode != null && !workTypeDtos.isEmpty()){
			for (WorkTypeDto type : workTypeDtos) {
				if (type.getWorkTypeCode() == null) continue;
				if (type.getWorkTypeCode().equals(workTypeCode)){
					name = type.getName();
				}
			}
		}
		return name;
	}
	
	/* put empty map */
	private void putEmptyData(Map<String, Object> data){
		data.put("項目","");
		data.put(sheet3_column2, "");
		data.put("値", "");
	}
}
