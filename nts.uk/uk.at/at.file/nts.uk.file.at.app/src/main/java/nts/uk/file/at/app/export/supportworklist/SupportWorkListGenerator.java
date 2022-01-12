package nts.uk.file.at.app.export.supportworklist;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface SupportWorkListGenerator {
    void generate(FileGeneratorContext context, SupportWorkListDataSource dataSource, boolean exportCsv);
}
