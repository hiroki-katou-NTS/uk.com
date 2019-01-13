package nts.uk.file.com.app.newlayout;

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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.NotUseAtr;
import nts.uk.file.com.app.maintenance.MaintenanceLayoutData;
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

@Stateless
@DomainID(value = "NewLayout")
public class NewLayoutExportImpl implements MasterListData{
	
	public static String value1= "value1";
	public static String value2= "value2";
	
	@Inject
	private NewLayoutExportRepository newLayoutExportRepository;

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
		List<NewLayoutExportData> listNewLayout = newLayoutExportRepository.getAllMaintenanceLayout(companyId, contractCode,
				forAttendance, forPayroll,forPersonnel);
		
		if(CollectionUtil.isEmpty(listNewLayout)){
			return null;
		}else{
			
			
			Map<Integer, List<NewLayoutExportData>> mapData = 
					listNewLayout.stream().collect(Collectors.groupingBy(NewLayoutExportData::getDispoder));
			
			mapData.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(c->{
				List<NewLayoutExportData> itemsForLayout = c.getValue();
				if (itemsForLayout.size() > 1) {
					itemsForLayout.stream().filter( x-> x.getCategoryName() != null).collect(Collectors.groupingBy(NewLayoutExportData::getCategoryName))
						.entrySet().stream().forEach(x ->{
							AtomicInteger rowCategory = new AtomicInteger(0);
							x.getValue().stream().forEach(y-> {
								datas.add(createMasterDateForRow(y, rowCategory));
							});
						});
				}else{
					if(itemsForLayout.size() ==1){
						NewLayoutExportData newLayoutExportData = itemsForLayout.get(0);
						Map<String, Object> data = new HashMap<>();
						putEmptyData(data);
						
						if (newLayoutExportData.getCategoryName() != null) {
							data.put(value1, newLayoutExportData.getCategoryName());
							data.put(value2, newLayoutExportData.getItemName());
						}
						MasterData masterData = new MasterData(data, null, "");
						masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
						
						datas.add(masterData);
					}
				}
			});
		}
		return datas;
	}
	
	private MasterData createMasterDateForRow(NewLayoutExportData row,  AtomicInteger rowCategory) {
		Map<String, Object> data = new HashMap<>();
		putEmptyData(data);

		
		if (rowCategory.get() == 0) {
			data.put(value1, row.getCategoryName());
		}
		data.put(value2, row.getItemName());
		
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		
		rowCategory.getAndIncrement();
		
		return masterData;
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn(value1, TextResource.localize("CPS007_22"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(value2, TextResource.localize("CPS007_23"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	private void putEmptyData (Map<String, Object> data){
		data.put(value1, "");
		data.put(value2, "");
	}
	
	@Override
	public String mainSheetName() {
		return TextResource.localize("CPS007_25");
	}
}
