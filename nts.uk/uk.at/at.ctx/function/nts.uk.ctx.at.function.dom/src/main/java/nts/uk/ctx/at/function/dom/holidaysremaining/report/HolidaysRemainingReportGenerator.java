package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

public interface HolidaysRemainingReportGenerator {
	void generate(FileGeneratorContext generatorContext, HolidayRemainingDataSource dataSource);
}
