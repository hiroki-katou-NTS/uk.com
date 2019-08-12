package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.comlegalrecord.CompanyStatutoryWriteExportData;

import java.util.List;

public interface GuaByTheInsurExportGenerator {
    void generate(FileGeneratorContext fileContext, List<CompanyStatutoryWriteExportData> exportData);
}
