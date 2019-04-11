package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SalaryHealthFileGenerator {
    void generate(FileGeneratorContext fileContext, SalaryHealthExportData data);
}
