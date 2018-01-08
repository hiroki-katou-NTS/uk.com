package nts.uk.shr.infra.file.report.masterlist.webservice;

import lombok.Data;

@Data
public class MasterListExportQuery {

	private String domainId;
	
	private String domainType;

	private String languageId;
	
	private ReportType reportType;
	
	private Object option;
	
	private Object data;
}
