package nts.uk.shr.infra.file.report.masterlist.generator;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.ReportType;

@Data
@AllArgsConstructor
public class MasterListExportSource {

	private Map<String, String> headers;
	
	private ReportType reportType;
	
	private MasterListData datas;
	
	private MasterListExportQuery query;
}
