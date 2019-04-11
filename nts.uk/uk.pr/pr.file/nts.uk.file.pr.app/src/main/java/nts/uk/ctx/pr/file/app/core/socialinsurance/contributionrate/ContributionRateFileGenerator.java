package nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ContributionRateFileGenerator {
    void generate(FileGeneratorContext fileContext, ContributionRateExportData data);
}
