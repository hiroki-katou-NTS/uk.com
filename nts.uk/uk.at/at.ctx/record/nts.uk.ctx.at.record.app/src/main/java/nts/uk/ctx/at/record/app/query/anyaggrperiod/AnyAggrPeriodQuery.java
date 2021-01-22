package nts.uk.ctx.at.record.app.query.anyaggrperiod;


import lombok.val;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Query: 任意集計期間
 *
 * @author : chinh.hm
 */
@Stateless
public class AnyAggrPeriodQuery {
    @Inject
    private OptionalAggrPeriodRepository repository;

    public List<AggrPeriodDto> findAll() {
        val cid = AppContexts.user().companyId();
        return repository.findAll(cid).stream().map(e -> new AggrPeriodDto(
                e.getCompanyId(),
                e.getAggrFrameCode().v(),
                e.getOptionalAggrName().v(),
                e.getStartDate(),
                e.getEndDate()
        )).collect(Collectors.toList());
    }


}



