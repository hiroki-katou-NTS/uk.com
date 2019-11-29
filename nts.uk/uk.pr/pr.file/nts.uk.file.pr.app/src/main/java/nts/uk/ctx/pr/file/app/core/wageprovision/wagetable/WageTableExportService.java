package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class WageTableExportService extends ExportService<WageTableExportQuery> {

    @Inject
    private WageTableExportRepository mWageTableExportRepository;

    @Inject
    private WageTableFileGenerator mWageTableFileGenerator;

    @Override
    protected void handle(ExportServiceContext<WageTableExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        List<ItemDataNameExport> dataName = mWageTableExportRepository.getItemName(cid);
        List<ItemDataNameExport> dataNameMaster = mWageTableExportRepository.getItemNameMaster(cid);
        List<WageTablelData> mWageTablelData = mWageTableExportRepository.getWageTableExport(cid,exportServiceContext.getQuery().startDate);
        mWageTableFileGenerator.generate(exportServiceContext.getGeneratorContext(),mWageTablelData, dataName, dataNameMaster);
    }
}