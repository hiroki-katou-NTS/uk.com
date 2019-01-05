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

		int forAttendance = NotUseAtr.NOT_USE.value;
		int forPayroll = NotUseAtr.NOT_USE.value;
		int forPersonnel = NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				forAttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				forPayroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				forPersonnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}
		
		
		List<MasterData> datas = new ArrayList<>();
		
		List<MaintenanceLayoutData> listMaintenanceLayout = maintenanceLayoutExportRepository.getAllMaintenanceLayout(companyId, contractCode,
				String.valueOf(forAttendance), String.valueOf(forPayroll),String.valueOf(forPersonnel));
		
		if(CollectionUtil.isEmpty(listMaintenanceLayout)){
			return null;
		}else{
			//map layoutCD
			Map<String, List<MaintenanceLayoutData>> mapData = 
						listMaintenanceLayout.stream().collect(Collectors.groupingBy(MaintenanceLayoutData::getLayoutCd));

			mapData.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(c->{
				List<MaintenanceLayoutData> itemsForLayout = c.getValue();
				//each layout code
				AtomicInteger rowlayout = new AtomicInteger(0); 
				if (itemsForLayout.size() > 1) {
					itemsForLayout.stream().filter(x -> x.getCategoryName() != null).collect(Collectors.groupingBy(MaintenanceLayoutData::getCategoryName))
							.entrySet().stream().forEach(x -> {
								AtomicInteger rowCategory = new AtomicInteger(0);
								// each category
								x.getValue().stream().forEach(y -> {
									datas.add(createMasterDateForRow(y, rowlayout, rowCategory));
								});
							});
				}
				else {
					if(itemsForLayout.size() ==1){
						MaintenanceLayoutData maintenanceLayoutData = itemsForLayout.get(0);
						Map<String, Object> data = new HashMap<>();
						putEmptyData(data);
						
							data.put(value1, maintenanceLayoutData.getLayoutCd());
							data.put(value2, maintenanceLayoutData.getLayoutName());
						
						
						if (maintenanceLayoutData.getCategoryName() != null) {
							data.put(value3, maintenanceLayoutData.getCategoryName());
							data.put(value4, maintenanceLayoutData.getItemName());
						}
						
						
						
						MasterData masterData = new MasterData(data, null, "");
						masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						masterData.cellAt(value3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						masterData.cellAt(value4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						
						datas.add(masterData);
					}
					
					
				}
				
			});
			
		}
		return datas;
	}
	
	private MasterData createMasterDateForRow(MaintenanceLayoutData row, AtomicInteger rowlayout, AtomicInteger rowCategory) {
		Map<String, Object> data = new HashMap<>();
		putEmptyData(data);
		if(rowlayout.get() == 0) {
			data.put(value1, row.getLayoutCd());
			data.put(value2, row.getLayoutName());
		}
		
		if (rowCategory.get() == 0) {
			data.put(value3, row.getCategoryName());
		}
		
		data.put(value4, row.getItemName());
		
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt(value3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt(value4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		
		rowlayout.getAndIncrement();
		rowCategory.getAndIncrement();
		
		return masterData;
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
		
		return TextResource.localize("CPS008_46");
	}
	


}
