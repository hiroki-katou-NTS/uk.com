package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import java.util.List;

public interface StatementLayoutFileGenerator {
    void generate(List<StatementLayoutSetExportData> exportData);
}
