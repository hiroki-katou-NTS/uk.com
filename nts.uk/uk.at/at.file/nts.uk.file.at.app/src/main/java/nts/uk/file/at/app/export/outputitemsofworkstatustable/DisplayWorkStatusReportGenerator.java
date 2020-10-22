package nts.uk.file.at.app.export.outputitemsofworkstatustable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;


public interface DisplayWorkStatusReportGenerator {
    void generate(FileGeneratorContext generatorContext, DisplayContentReportData dataSource);
}
