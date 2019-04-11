package nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceExportData;

public interface HealthInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, HealthInsuranceExportData data);
}
