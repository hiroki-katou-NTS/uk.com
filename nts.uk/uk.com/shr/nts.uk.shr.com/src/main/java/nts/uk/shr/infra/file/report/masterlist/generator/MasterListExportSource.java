package nts.uk.shr.infra.file.report.masterlist.generator;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.webservice.ReportType;

@Data
@AllArgsConstructor
public class MasterListExportSource {

	private Map<String, String> headers;
	
	private List<MasterHeaderColumn> headerColumns;
	
	private List<MasterData> masterList;
	
	private Map<String, List<MasterHeaderColumn>> extraHeaderColumns;
	
	private Map<String, List<MasterData>> extraMasterList;
	
	private ReportType reportType;
}
