package nts.uk.ctx.workflow.dom.agent.report;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface AgentReportGenerator {
    void generate(FileGeneratorContext context, AgentReportDataSource dataSource);
}
