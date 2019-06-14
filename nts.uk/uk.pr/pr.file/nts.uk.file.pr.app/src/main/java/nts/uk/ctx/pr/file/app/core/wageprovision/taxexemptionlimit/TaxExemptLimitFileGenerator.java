package nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface TaxExemptLimitFileGenerator {
    void generate(FileGeneratorContext fileContext, List<TaxExemptLimitSetExportData> exportData);

}
