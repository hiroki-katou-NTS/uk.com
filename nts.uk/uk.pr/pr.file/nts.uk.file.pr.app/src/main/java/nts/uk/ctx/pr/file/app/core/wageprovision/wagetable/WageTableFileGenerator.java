package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitSetExportData;

import java.util.List;


public interface WageTableFileGenerator {
    void generate(FileGeneratorContext fileContext, List<WageTablelData> exportData, List<ItemDataNameExport> dataName, List<ItemDataNameExport> dataNameMaster);
}
