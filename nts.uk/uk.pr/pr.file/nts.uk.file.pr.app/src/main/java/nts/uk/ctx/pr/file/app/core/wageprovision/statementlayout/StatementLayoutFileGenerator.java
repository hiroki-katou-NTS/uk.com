package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface StatementLayoutFileGenerator {
    void generate(FileGeneratorContext fileContext, List<StatementLayoutSetExportData> exportData);
}
