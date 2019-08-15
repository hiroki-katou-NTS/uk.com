package nts.uk.file.pr.infra.core.temp;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.temp.SocialInsurNotiCreateSetExportData;
import nts.uk.ctx.pr.file.app.core.temp.SocialInsurNotiCreateSetFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;

@Stateless
public class SocialInsurNotiCreateSetAposeFileGenerator extends AsposeCellsReportGenerator implements SocialInsurNotiCreateSetFileGenerator {

    @Override
    public void generate(FileGeneratorContext fileContext, SocialInsurNotiCreateSetExportData data) {

    }
}
