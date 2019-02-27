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
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.file.com.app.newlayout.NewLayoutExportData;
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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

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
	public static String value5= "value5";
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();

		List<MaintenanceLayoutData> listMaintenanceLayoutS = new ArrayList<>();
		List<MaintenanceLayoutData> listMaintenanceLayoutSv2 = new ArrayList<>();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<MaintenanceLayoutData> listMaintenanceLayout = maintenanceLayoutExportRepository.getAllMaintenanceLayout(companyId, contractCode);
		
		if(CollectionUtil.isEmpty(listMaintenanceLayout)){
			return null;
		}else{

			
			for (int i = 0; i < listMaintenanceLayout.size(); i++) {
				if(listMaintenanceLayout.get(i).getItemParentCD()!= null && listMaintenanceLayout.get(i).getLayoutItemType() ==1){
					listMaintenanceLayout.get(i).setItemParentCD(null);
				}
				
				if(listMaintenanceLayout.get(i).getItemParentCD()==null && listMaintenanceLayout.get(i).getItemCD()!=null){
					listMaintenanceLayoutSv2.add(listMaintenanceLayout.get(i));
				}
			}
			
			for(int n=0;n<listMaintenanceLayout.size();n++){
				if(listMaintenanceLayout.get(n).getItemParentCD()==null){
					listMaintenanceLayoutS.add(listMaintenanceLayout.get(n));
				}else{
					for(int m=0;m<listMaintenanceLayoutSv2.size();m++){
						if(listMaintenanceLayout.get(n).getItemParentCD().equals(listMaintenanceLayoutSv2.get(m).getItemCD())){
							listMaintenanceLayoutS.add(listMaintenanceLayout.get(n));
							break;
						}
						
					}
				}
			}
			
			
			
			
			//map layoutCD
			for (int i = 0; i < listMaintenanceLayoutS.size(); i++) { 
				Map<String, Object> data = new HashMap<>();
				putEmptyData(data);
				
				String layoutCdM = listMaintenanceLayoutS.get(i).getLayoutCd();
				String layoutNameM = listMaintenanceLayoutS.get(i).getLayoutName();
				String categoryNameM = listMaintenanceLayoutS.get(i).getCategoryName();
				String itemNameM = listMaintenanceLayoutS.get(i).getItemName();
				String itemNameC = listMaintenanceLayoutS.get(i).getItemNameC();
				String itemParentCD = listMaintenanceLayoutS.get(i).getItemParentCD();
				
				if(i==0){
					data.put(value1, layoutCdM);
					data.put(value2, layoutNameM);
					if(categoryNameM == null){
						data.put(value3, "----------");
						data.put(value4, "----------");
						data.put(value5, "----------");
					}else{
						data.put(value3, categoryNameM);
						data.put(value4, itemNameM);
						data.put(value5, "");
					}
				}else{
					if(layoutCdM.equals(listMaintenanceLayoutS.get(i-1).getLayoutCd())){
						data.put(value1, "");
						data.put(value2, "");
						if(categoryNameM == null){
							data.put(value3, "----------");
							data.put(value4, "----------");
							data.put(value5, "----------");
						}else{
							if (categoryNameM.equals(listMaintenanceLayoutS.get(i - 1).getCategoryName())) {
								data.put(value3, "");
								if (itemParentCD == null) {
									data.put(value4, itemNameM);
									data.put(value5, "");
								} else {
									data.put(value4, "");
									data.put(value5, itemNameC);
								}
							} else {
								data.put(value3, categoryNameM);
								if (itemParentCD == null) {
									data.put(value4, itemNameM);
									data.put(value5, "");
								} else {
									data.put(value4, "");
									data.put(value5, itemNameC);
								}
							}
						}
					}else{
						data.put(value1, layoutCdM);
						data.put(value2, layoutNameM);
						if(categoryNameM == null){
							data.put(value3, "----------");
							data.put(value4, "----------");
							data.put(value5, "----------");
						}else{
							data.put(value3, categoryNameM);
							data.put(value4, itemNameM);
							data.put(value5, "");
						}
					}
					
					
					
					
//					if(layoutCdM.equals(listMaintenanceLayoutS.get(i-1).getLayoutCd())){
//						data.put(value1, "");
//						data.put(value2, "");
//						if(categoryNameM == null){
//							data.put(value3, "----------");
//							data.put(value4, "----------");
//							data.put(value5, "----------");
//						}else{
//							if(categoryNameM.equals(listMaintenanceLayoutS.get(i-1).getCategoryName())){
//								data.put(value3, "");
//								if(itemParentCD ==null){
//									data.put(value4, itemNameM);
//									data.put(value5, "");
//								}else{
//									data.put(value4, "");
//									data.put(value5, itemNameC);
//								}
//							}else{
//								data.put(value3, categoryNameM);
//								if(itemParentCD ==null){
//									data.put(value4, itemNameM);
//									data.put(value5, "");
//								}else{
//									data.put(value4, "");
//									data.put(value5, itemNameC);
//								}
//							}
//						}
//					}else{
//						data.put(value1, layoutCdM);
//						data.put(value2, layoutNameM);
//						data.put(value3, categoryNameM);
//						data.put(value4, "");
//						data.put(value5, "");
//						
//						
//					}
				}
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
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
		columns.add(new MasterHeaderColumn(value5, TextResource.localize("CPS008_51"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	private void putEmptyData (Map<String, Object> data){
		data.put(value1, "");
		data.put(value2, "");
		data.put(value3, "");
		data.put(value4, "");
		data.put(value5, "");
	}
	
	@Override
	public String mainSheetName() {
		
		return TextResource.localize("CPS008_47");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}


}
