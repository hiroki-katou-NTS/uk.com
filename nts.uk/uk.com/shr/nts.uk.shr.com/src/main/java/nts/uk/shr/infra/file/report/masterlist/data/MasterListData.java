package nts.uk.shr.infra.file.report.masterlist.data;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

public interface MasterListData {

	public List<MasterData> getMasterDatas(MasterListExportQuery query);
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query);
}
