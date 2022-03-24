package nts.uk.file.com.app.approvalmanagement.workroot;

import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.TransactionAttribute;

import javax.ejb.Stateless;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApproversExportService extends ExportService<ApproversQuery> {

	@Inject
	private ApproversExportFileQuery fileQuery;

	@Override
	protected void handle(ExportServiceContext<ApproversQuery> context) {
		this.fileQuery.handle(context, context.getQuery());
	}
}
