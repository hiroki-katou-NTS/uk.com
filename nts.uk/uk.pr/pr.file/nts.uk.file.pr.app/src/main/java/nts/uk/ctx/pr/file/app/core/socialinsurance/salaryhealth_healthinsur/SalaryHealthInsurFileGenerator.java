package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur;


import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SalaryHealthInsurFileGenerator {
    void generate(FileGeneratorContext fileContext, SalaryHealthInsurExportData data);
}
