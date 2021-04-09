package nts.uk.file.at.app.export.arbitraryperiodsummarytable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ArbitraryPeriodSummaryTableGenerator {
    void generate(FileGeneratorContext generatorContext, ArbitraryPeriodSummaryDto dataSource);
}
