package nts.uk.ctx.pr.file.app.core.rsdttaxpayee;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ResidentTexPayeeExportService extends ExportService<ResidentTexPayeeExportQuery> {

    @Inject
    private ResidentTexPayeeExportRepository mResidentTaxPayeeRepository;

    @Inject
    private ResidentTexPayeeFileGenerator mResidentTexPayeeFileGenerator;


    @Override
    protected void handle(ExportServiceContext<ResidentTexPayeeExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        List<Object[]> data = mResidentTaxPayeeRepository.getResidentTexPayeeByCompany(cid);
        List<ResidentTexPayeeExportData> dataExport = new ArrayList<>();
        for(int i = 0 ; i < data.size(); i++){
            Object[] e = data.get(i);
            dataExport.add(new ResidentTexPayeeExportData(
                    e[0] != null ? e[0].toString() : "",
                    e[1] != null ? e[1].toString() : "",
                    e[2] != null ? e[2].toString() : "",
                    e[3] != null ? Integer.parseInt(e[3].toString()) : 0,
                    e[4] != null ? e[4].toString() : "",
                    e[5] != null ? e[5].toString() : "",
                    e[6] != null ? e[6].toString() : "",
                    e[7] != null ? e[7].toString() : "",
                    e[8] != null ? e[8].toString() : "",
                    e[9] != null ? e[9].toString() : "",
                    e[10] != null ? e[10].toString() : "")
            );


        }
        mResidentTexPayeeFileGenerator.generate(exportServiceContext.getGeneratorContext(),dataExport);

    }
}