package nts.uk.file.at.app.export.workledgeroutputitem;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface WorkLedgerOutputItemGenerator {
    void generate(FileGeneratorContext generatorContext, Object dataSource);
}
