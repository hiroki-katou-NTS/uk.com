package nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface SalaryPerUnitFileGenerator {
    void generate(FileGeneratorContext fileContext, List<SalaryPerUnitSetExportData> exportData);
}
