package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface HolidaysRemainingReportGenerator {
	void generate(FileGeneratorContext generatorContext, HolidayRemainingDataSource dataSource);
}
