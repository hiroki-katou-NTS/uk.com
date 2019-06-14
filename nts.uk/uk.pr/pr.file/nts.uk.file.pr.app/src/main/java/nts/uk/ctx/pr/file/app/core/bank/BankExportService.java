package nts.uk.ctx.pr.file.app.core.bank;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@Stateless
public class BankExportService extends ExportService{

    @Inject
    private BankExportRepository repo;

    @Inject
    private CompanyAdapter company;

    @Inject
    private BankExportFileGenerator generator;

    @Override
    protected void handle(ExportServiceContext exportServiceContext) {

        Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
        String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
        String cid = AppContexts.user().companyId();
        List<Object[]> exportData = repo.getAllBankByCid(cid);
        generator.generate(exportServiceContext.getGeneratorContext(),exportData,companyName);

    }
}
