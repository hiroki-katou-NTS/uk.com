package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface GuaByTheInsurExportExcelGenerator {
    void generate(FileGeneratorContext fileContext, GuaByTheInsurExportDto exportData);
}
