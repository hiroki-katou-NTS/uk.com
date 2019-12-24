package nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface WelfarepensionInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, WelfarepensionInsuranceExportData data);
}
