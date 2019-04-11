package nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceExportData;

public interface SocialInsuranceOfficeFileGenerator {
    void generate(FileGeneratorContext fileContext, SocialInsuranceExportData data);
}
