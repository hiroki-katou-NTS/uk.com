package nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SocialInsuranceOfficeFileGenerator {
    void generate(FileGeneratorContext fileContext, SocialInsuranceOfficeExportData data);
}
