package nts.uk.ctx.pr.file.app.core.comlegalrecord;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CompanyStatutoryWriteExportService extends ExportService {

    @Inject
    private CompanyAdapter company;

    @Inject
    private CompanyStatutoryWriteExportGenerator generator;

    @Inject
    private CompanyStatutoryWriteExportRepository repo;

    @Override
    protected void handle(ExportServiceContext exportServiceContext) {

        Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
        String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
        String cid = AppContexts.user().companyId();
        List<CompanyStatutoryWriteExportData> exportData = repo.getByCid(cid).stream().map(CompanyStatutoryWriteExportData::fromDomain).collect(Collectors.toList());
        generator.generate(exportServiceContext.getGeneratorContext(),exportData,companyName);

    }
}
