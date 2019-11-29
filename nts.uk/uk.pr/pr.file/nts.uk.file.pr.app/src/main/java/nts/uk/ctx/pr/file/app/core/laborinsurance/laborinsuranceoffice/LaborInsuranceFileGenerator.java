package nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface LaborInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, LaborInsuranceExportData data);
}
