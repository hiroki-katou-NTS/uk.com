package nts.uk.ctx.at.record.dom.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
@Stateless
@DomainID(value = "OperationSetting")
public class OperationExcelRepoImpl implements MasterListData {

	@Inject
	private OperationExcelRepo operationExcelRepo;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("項目", TextResource.localize("KDW006_x"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("comment", "",
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("値", TextResource.localize("KDW006_27"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<DaiPerformanceFun> daiPerformanceFunOpt = operationExcelRepo.getDaiPerformanceFunById(companyId);
		Optional<MonPerformanceFun> monPerformanceFunOpt = operationExcelRepo.getMonPerformanceFunById(companyId);
		Optional<FormatPerformance> formatPerformance = operationExcelRepo.getFormatPerformanceById(companyId);
		Map<String, Object> data = new HashMap<>();
		putDataEmptySetOperation(data);
				//put first row
				data.put("項目", TextResource.localize("KDW006_27"));
				data.put("comment", "");
				if(formatPerformance.isPresent()){
					if(formatPerformance.get().getSettingUnitType().value==0){
						data.put("値", "権限");
					}
					if(formatPerformance.get().getSettingUnitType().value==1){
						data.put("値", "勤務種別");
					}
				}
				datas.add(alignMasterData(data));
				//next row
				data.put("項目", TextResource.localize("KDW006_28"));
				data.put("comment", TextResource.localize("KDW006_62"));
				if(daiPerformanceFunOpt.isPresent()){
					if(daiPerformanceFunOpt.get().getComment()!=null){
						data.put("値", daiPerformanceFunOpt.get().getComment());
					}
				}
				datas.add(alignMasterData(data));
				//next row
				data.put("項目", "");
				data.put("comment", TextResource.localize("KDW006_63"));
				if(monPerformanceFunOpt.isPresent()){
					if(monPerformanceFunOpt.get().getComment()!=null){
						data.put("値", monPerformanceFunOpt.get().getComment());
					}
				}

					datas.add(alignMasterData(data));
						
		return datas;
	}
	public MasterData alignMasterData(Map<String, Object> data){
		MasterData masterData = new MasterData(data, null, "");
		masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("comment").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		masterData.cellAt("値").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		return masterData;
	}
	@Override
	public String mainSheetName() {
		return "共通_運用設定";
	}
	
	
	private void putDataEmptySetOperation(Map<String, Object> data){
		data.put("項目","");
		data.put("comment","");
		data.put("値","");
	}
}
