package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutHistFinder {
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;

    public List<YearMonthHistoryItemDto> getHistByCidAndCodeAndAfterDate(String code, int startYearMonth) {
        String cid = AppContexts.user().companyId();

        return statementLayoutHistRepo.getHistByCidAndCodeAndAfterDate(cid, code, startYearMonth).stream().
                map(i -> YearMonthHistoryItemDto.fromDomainToDto(i)).collect(Collectors.toList());
    }
}
