package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo.EmpInsGetQualifReport;
import nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo.EmpInsGetQualifRptFileGenerator;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EmpInsGetQualifAsposeFileGenerator extends AsposePdfReportGenerator implements EmpInsGetQualifRptFileGenerator {
    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsGetQualifReport> data) {

    }
}
