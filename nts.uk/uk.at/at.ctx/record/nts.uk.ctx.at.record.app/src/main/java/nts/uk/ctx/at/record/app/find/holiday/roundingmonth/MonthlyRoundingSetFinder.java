package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MonthlyRoundingSetFinder {
    @Inject
    private RoundingSetOfMonthlyRepository repo;

    public RoundingSetOfMonthlyDto getSetting() {
        return repo.find(AppContexts.user().companyId()).map(RoundingSetOfMonthlyDto::new).orElse(null);
    }
}
