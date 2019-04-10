package nts.uk.ctx.pr.file.app.core.socialinsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SocialInsuranceFileGenerator {
    void generate(FileGeneratorContext fileContext, SocialInsuranceExportData data);
}
