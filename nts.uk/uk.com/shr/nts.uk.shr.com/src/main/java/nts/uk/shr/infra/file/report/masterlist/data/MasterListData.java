package nts.uk.shr.infra.file.report.masterlist.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

public interface MasterListData {

	public List<MasterData> getMasterDatas(MasterListExportQuery query);
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query);
	
	default public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query){
		return Collections.emptyMap();
	}
	
	default public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query){
		return Collections.emptyMap();
	}
	
	default public String mainSheetName(){
		return null;
	}
	
	default public List<SheetData> extraSheets(MasterListExportQuery query){
		return Collections.emptyList();
	}
}
