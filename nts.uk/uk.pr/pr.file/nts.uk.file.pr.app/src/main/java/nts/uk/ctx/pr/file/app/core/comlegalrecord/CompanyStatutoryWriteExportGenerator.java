package nts.uk.ctx.pr.file.app.core.comlegalrecord;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface CompanyStatutoryWriteExportGenerator {
    void generate(FileGeneratorContext fileContext, List<CompanyStatutoryWriteExportData> exportData, String companyName);
}
