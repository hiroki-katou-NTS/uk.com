package nts.uk.ctx.pr.screen.app.report.qet002;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.screen.app.report.qet002.data.AccPaymentDataSource;


public interface AccPaymentReportGenerator {
	void generate(FileGeneratorContext generatorContext, AccPaymentDataSource dataSource);
	
}
