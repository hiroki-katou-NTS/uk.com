package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface LifeInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, LifeInsuranceExportData data);
}
