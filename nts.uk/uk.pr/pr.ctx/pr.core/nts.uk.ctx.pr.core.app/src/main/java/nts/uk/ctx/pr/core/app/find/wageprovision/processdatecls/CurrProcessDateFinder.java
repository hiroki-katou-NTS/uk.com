package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
* 現在処理年月
*/
public class CurrProcessDateFinder
{

    @Inject
    private CurrProcessDateRepository finder;

    public List<CurrProcessDateDto> getAllCurrProcessDate(){
        return finder.getAllCurrProcessDate().stream().map(item -> CurrProcessDateDto.fromDomain(item))
                .collect(Collectors.toList());
    }


    public CurrProcessDateDto getCurrProcessDateByID(int processCateNo){
        String cid = AppContexts.user().companyId();
        return CurrProcessDateDto.fromDomain(finder.getCurrProcessDateByIdAndProcessCateNo(cid,processCateNo).orElse(null));
    }

}
