package nts.uk.ctx.at.request.template.request;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.template.export.TemplateExportGenerator;

@Stateless
public class TemplateExportService extends ExportService<TemplateExportQuery> {
    
    @Inject
    private TemplateExportGenerator generator;

    @Override
    protected void handle(ExportServiceContext<TemplateExportQuery> context) {
        TemplateExportQuery query = context.getQuery();
        
        generator.generate(context.getGeneratorContext(), query.fileID, query.fileName, query.approverName, GeneralDate.today(), query.status, query.isExcel);
    }

}
