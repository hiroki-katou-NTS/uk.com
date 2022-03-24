package nts.uk.file.com.app.approvalmanagement.workroot;

import nts.arc.layer.app.file.export.ExportServiceContext;

public interface ApproversExportFileQuery {

	void handle(ExportServiceContext<ApproversQuery> context, ApproversQuery query);
	ApproversExportDataSource getExportData(ApproversQuery query);
}
