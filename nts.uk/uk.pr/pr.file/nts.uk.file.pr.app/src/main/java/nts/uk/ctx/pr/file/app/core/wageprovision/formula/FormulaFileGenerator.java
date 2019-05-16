package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface FormulaFileGenerator {
    void generate(FileGeneratorContext fileContext, FormulaExportData data);
}
