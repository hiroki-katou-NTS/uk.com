package nts.uk.file.at.app.export.workledgeroutputitem;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerExportDataSource;

public interface WorkLedgerOutputItemGenerator {
    void generate(FileGeneratorContext generatorContext, WorkLedgerExportDataSource dataSource);
}
