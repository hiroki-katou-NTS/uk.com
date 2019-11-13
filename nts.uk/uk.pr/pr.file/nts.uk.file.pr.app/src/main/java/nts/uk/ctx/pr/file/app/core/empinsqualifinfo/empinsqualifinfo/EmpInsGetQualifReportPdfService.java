package nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.Stateless;

@Stateless
public class EmpInsGetQualifReportPdfService  extends ExportService<EmpInsGetQualifReportQuery> {

    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {

    }
}
