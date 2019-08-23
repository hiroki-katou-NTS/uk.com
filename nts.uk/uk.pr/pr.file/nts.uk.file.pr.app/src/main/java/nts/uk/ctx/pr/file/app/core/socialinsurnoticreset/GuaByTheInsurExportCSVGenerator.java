package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface GuaByTheInsurExportCSVGenerator {
    void generate(FileGeneratorContext generatorContext, ExportDataCsv dataSource);
}
