package nts.uk.file.at.app.export.annualworkledger;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;


public interface DisplayAnnualWorkLedgerReportGenerator {
    void generate(FileGeneratorContext generatorContext, AnnualWorkLedgerExportDataSource dataSource);
}
