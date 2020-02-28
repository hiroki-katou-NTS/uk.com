package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

import java.util.List;

public interface InsuredNameChangedExportFileGenerator {
    void generate(FileGeneratorContext fileContext, List<InsuredNameChangedNotiExportData> data, SocialInsurNotiCreateSet socialInsurNotiCreateSet, CompanyInfor company);
}
