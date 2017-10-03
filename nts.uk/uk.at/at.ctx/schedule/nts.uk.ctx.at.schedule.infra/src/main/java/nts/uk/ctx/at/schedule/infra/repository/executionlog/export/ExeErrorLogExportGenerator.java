package nts.uk.ctx.at.schedule.infra.repository.executionlog.export;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.schedule.app.find.executionlog.export.ExeErrorLogGenerator;
import nts.uk.ctx.at.schedule.app.find.executionlog.export.ExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
@Stateless
public class ExeErrorLogExportGenerator extends AsposeCellsReportGenerator implements ExeErrorLogGenerator{

	@Override
	public void generate(FileGeneratorContext fileContext, ExportData exportData) {
		// TODO Auto-generated method stub
		
	}

}
