package nts.uk.ctx.pr.file.app.core.temp;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SocialInsurNotiCreateSetFileGenerator {
    void generate(FileGeneratorContext fileContext, SocialInsurNotiCreateSetExportData data);
}
