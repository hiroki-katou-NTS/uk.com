package nts.uk.file.com.infra.approvalmanagement.workroot;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversExportDataSource;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeApproversReportGenerator extends AsposeCellsReportGenerator implements ApproversExportGenerator {

	@Override
	public void generate(FileGeneratorContext generatorContext, ApproversExportDataSource dataSource) {
		// TODO Auto-generated method stub

	}

}
