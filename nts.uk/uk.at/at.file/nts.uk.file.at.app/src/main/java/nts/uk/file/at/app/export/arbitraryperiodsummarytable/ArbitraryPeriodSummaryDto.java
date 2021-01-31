package nts.uk.file.at.app.export.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.DetailOfArbitrarySchedule;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;

@AllArgsConstructor
@Getter
public class ArbitraryPeriodSummaryDto {

    private DetailOfArbitrarySchedule content ;
    private OutputSettingOfArbitrary ofArbitrary;
    private DatePeriod period;
    private CompanyBsImport companyInfo;
}
