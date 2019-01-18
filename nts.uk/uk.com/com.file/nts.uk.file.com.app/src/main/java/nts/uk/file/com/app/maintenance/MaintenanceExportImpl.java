package nts.uk.file.com.app.maintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Maintenance")
public class MaintenanceExportImpl implements MasterListData {
	
	@Inject
	private MaintenanceLayoutExportRepository maintenanceLayoutExportRepository;
	
	
	public static String value1= "value1";
	public static String value2= "value2";
	public static String value3= "value3";
	public static String value4= "value4";
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();


		
		List<MasterData> datas = new ArrayList<>();
		
		List<MaintenanceLayoutData> listMaintenanceLayout = maintenanceLayoutExportRepository.getAllMaintenanceLayout(companyId, contractCode);
		
		if(CollectionUtil.isEmpty(listMaintenanceLayout)){
			return null;
		}else{
			//map layoutCD
			for (int i = 0; i < listMaintenanceLayout.size(); i++) { 
				Map<String, Object> data = new HashMap<>();
				putEmptyData(data);
				
				String layoutCdM = listMaintenanceLayout.get(i).getLayoutCd();
				String layoutNameM = listMaintenanceLayout.get(i).getLayoutName();
				String categoryNameM = listMaintenanceLayout.get(i).getCategoryName();
				String itemNameM = listMaintenanceLayout.get(i).getItemName();
				
				if(i==0){
					data.put(value1, layoutCdM);
					data.put(value2, layoutNameM);
					if(categoryNameM == null){
						data.put(value3, "----------");
						data.put(value4, "----------");
					}else{
						data.put(value3, categoryNameM);
						data.put(value4, itemNameM);
					}
				}else{
					if(layoutCdM.equals(listMaintenanceLayout.get(i-1).getLayoutCd())){
						data.put(value1, "");
						data.put(value2, "");
						if(categoryNameM == null){
							data.put(value3, "----------");
							data.put(value4, "----------");
						}else{
							if(categoryNameM.equals(listMaintenanceLayout.get(i-1).getCategoryName())){
								data.put(value3, "");
							}else{
								data.put(value3, categoryNameM);
							}
							data.put(value4, itemNameM);
						}
					}else{
						data.put(value1, layoutCdM);
						data.put(value2, layoutNameM);
						if(categoryNameM == null){
							data.put(value3, "----------");
							data.put(value4, "----------");
						}else{
							if(categoryNameM.equals(listMaintenanceLayout.get(i-1).getCategoryName())){
								data.put(value3, "");
							}else{
								data.put(value3, categoryNameM);
							}
							data.put(value4, itemNameM);
						}
						
					}
				}
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				datas.add(masterData);
			}
		}
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn(value1, TextResource.localize("CPS008_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(value2, TextResource.localize("CPS008_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(value3, TextResource.localize("CPS008_45"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(value4, TextResource.localize("CPS008_19"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	private void putEmptyData (Map<String, Object> data){
		data.put(value1, "");
		data.put(value2, "");
		data.put(value3, "");
		data.put(value4, "");
	}
	
	@Override
	public String mainSheetName() {
		
		return TextResource.localize("CPS008_47");
	}
	


}
