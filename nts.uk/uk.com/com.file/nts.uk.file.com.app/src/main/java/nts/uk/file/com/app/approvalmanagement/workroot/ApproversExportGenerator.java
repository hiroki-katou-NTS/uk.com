package nts.uk.file.com.app.approvalmanagement.workroot;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ApproversExportGenerator {

	void generate (FileGeneratorContext generatorContext, ApproversExportDataSource dataSource);
}
