package nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;

@Stateless
public class PayrollUnitPriceExportService extends ExportService<PayrollUnitPriceExportQuery> {

    @Inject
    PayrollUnitPriceRepository repository;

    @Inject
    PayrollUnitPriceFileGenerator generator;

    @Override
    protected void handle(ExportServiceContext<PayrollUnitPriceExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        List<Object[]> exportData = repository.getAllPayrollUnitPriceSetByCID(cid);
        generator.generate(exportServiceContext.getGeneratorContext(),exportData);
    }
}
