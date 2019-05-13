package nts.uk.ctx.pr.file.app.core.bank;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface BankExportFileGenerator {
    void generate(FileGeneratorContext fileContext, List<Object[]> exportData, String companyName);
}
