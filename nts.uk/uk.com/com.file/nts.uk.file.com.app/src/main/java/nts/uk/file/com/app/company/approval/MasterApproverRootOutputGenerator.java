package nts.uk.file.com.app.company.approval;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface MasterApproverRootOutputGenerator {
	void generate(FileGeneratorContext generatorContext, MasterApproverRootOutputDataSource dataSource);
}
