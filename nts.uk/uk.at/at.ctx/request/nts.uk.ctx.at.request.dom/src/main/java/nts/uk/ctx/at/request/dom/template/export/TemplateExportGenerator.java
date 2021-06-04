package nts.uk.ctx.at.request.dom.template.export;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;

public interface TemplateExportGenerator {

    void generate(FileGeneratorContext generatorContext, String fileID, String fileName, String approverName, GeneralDate date, String status, boolean isExcel);
}
