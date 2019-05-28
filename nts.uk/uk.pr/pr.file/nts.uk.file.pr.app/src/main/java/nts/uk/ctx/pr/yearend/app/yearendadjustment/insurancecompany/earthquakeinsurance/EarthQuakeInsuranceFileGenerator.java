package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EarthQuakeInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, EarthQuakeInsuranceExportData data);
}
