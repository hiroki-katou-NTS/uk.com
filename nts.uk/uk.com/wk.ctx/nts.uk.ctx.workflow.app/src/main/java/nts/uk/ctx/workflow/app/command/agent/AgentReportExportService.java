package nts.uk.ctx.workflow.app.command.agent;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportDataSource;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportGenerator;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;

@Stateless
public class AgentReportExportService extends ExportService<List<LinkedHashMap<String, String>>> {
    @Inject
    private AgentReportGenerator reportGenerator;

    @Override
    protected void handle(ExportServiceContext<List<LinkedHashMap<String, String>>> exportServiceContext) {
        String fileName = TextResource.localize("CMM044_1") + ".xlsx";
        AgentReportDataSource dataSource = new AgentReportDataSource(fileName, exportServiceContext.getQuery());
        reportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
