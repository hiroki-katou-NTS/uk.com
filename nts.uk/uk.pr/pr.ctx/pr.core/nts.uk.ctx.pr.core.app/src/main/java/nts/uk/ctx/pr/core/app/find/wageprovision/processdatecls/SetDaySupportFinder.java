package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
 * 給与支払日設定
 */
public class SetDaySupportFinder {

    @Inject
    private SetDaySupportRepository finder;

    public List<SetDaySupportDto> getAllSetDaySupport() {
        return finder.getAllSetDaySupport().stream().map(item -> SetDaySupportDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public List<SetDaySupportDto> getListByCateNoAndCid(int processCateNo) {
        String cid = AppContexts.user().companyId();
        return finder.getSetDaySupportById(cid, processCateNo).stream().map(item -> SetDaySupportDto.fromDomain(item)).collect(Collectors.toList());
    }
}
